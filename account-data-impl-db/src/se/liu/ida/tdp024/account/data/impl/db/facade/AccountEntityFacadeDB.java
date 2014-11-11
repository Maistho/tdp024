package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerMonlog;

public class AccountEntityFacadeDB implements AccountEntityFacade {

    private final AccountLogger logger = new AccountLoggerMonlog();

    @Override
    public void create(String accounttype, String name, String bank)
            throws
            AccountEntityFacadeIllegalArgumentException,
            AccountEntityFacadeStorageException {
        if (accounttype == null || name == null || bank == null) {
            throw new AccountEntityFacadeIllegalArgumentException("Null argument");
        }

        EntityManager em = EMF.getEntityManager();
        em.getTransaction().begin();

        Account account = new AccountDB();
        try {
            account.setAccountType(accounttype);
        } catch (Account.AccountIllegalArgumentException e) {

            logger.log(AccountLogger.AccountLoggerLevel.WARNING, "AccountEntityFacade.create",
                    String.format("No such accounttype: %s%n%s", accounttype, e.getMessage()));
            throw new AccountEntityFacadeIllegalArgumentException(e);
        }

        account.setPersonKey(name);
        account.setBankKey(bank);

        try {
            em.persist(account);
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.log(e);
            throw new AccountEntityFacadeStorageException("Could not store account", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Account> findAllByName(String key)
            throws
            AccountEntityFacadeStorageException {

        EntityManager em = EMF.getEntityManager();
        List<Account> results;
        try {
            results = em.createQuery("SELECT a FROM AccountDB a WHERE a.personKey = ?1")
                    .setParameter(1, key).getResultList();
        } catch (Exception e) {
            logger.log(e);
            throw new AccountEntityFacadeStorageException(e);
        }

        em.close();
        return results;

    }

    @Override
    public void credit(long id, long amount)
            throws
            AccountEntityFacadeStorageException,
            AccountEntityFacadeIllegalArgumentException {
        EntityManager em = EMF.getEntityManager();
        em.getTransaction().begin();

        try {
            AccountDB account = em.find(AccountDB.class, id); //, LockModeType.PESSIMISTIC_WRITE);
            if (account == null) {
                em.flush();
                throw new AccountEntityFacadeIllegalArgumentException("could not find account");
            }

            account.setHoldings(account.getHoldings() + amount);
            em.getTransaction().commit();

        } catch (IllegalArgumentException e) {
            logger.log(AccountLogger.AccountLoggerLevel.WARNING, "Account not found",
                    String.format("account with id '%d' was not found", id));
            throw new AccountEntityFacadeIllegalArgumentException("Account not found", e);
        } catch (Exception e) {
            logger.log(e);
            throw new AccountEntityFacadeStorageException("Couldn't save credit", e);
        } finally {
            if (em.getTransaction().isActive()) {
                em.flush();
            }
            em.close();
        }
    }

    @Override
    public void debit(long id, long amount)
            throws AccountEntityFacadeStorageException,
            AccountEntityFacadeInsufficientHoldingsException,
            AccountEntityFacadeIllegalArgumentException {
        EntityManager em = EMF.getEntityManager();
        em.getTransaction().begin();
        try {
            Account account = em.find(AccountDB.class, id); // LockModeType.PESSIMISTIC_WRITE);
            if (account == null) {
                throw new AccountEntityFacadeIllegalArgumentException("Could not find account");
            }

            long holdings = account.getHoldings();
            if (holdings < amount) {
                logger.log(AccountLogger.AccountLoggerLevel.WARNING, "Not enough holdings",
                        String.format("Not enough holdings on account with id '%d' to complete transaction", id));
                throw new AccountEntityFacadeInsufficientHoldingsException("Not enough holdings");
            }

            account.setHoldings(holdings - amount);
            em.getTransaction().commit();

        } catch (IllegalArgumentException e) {
            logger.log(AccountLogger.AccountLoggerLevel.WARNING, "Account not found",
                    String.format("Account with id '%d' was not found", id));
            throw new AccountEntityFacadeIllegalArgumentException("Account not found", e);
        } finally {
            if (em.getTransaction().isActive()) {
                em.flush();
            }
            em.close();
        }
    }

    @Override
    public Account findById(long account)
            throws
            AccountEntityFacadeIllegalArgumentException {
        EntityManager em = EMF.getEntityManager();
        try {
            return em.find(AccountDB.class, account);
        } catch (Exception e) {
            logger.log(e);
            throw new AccountEntityFacadeIllegalArgumentException(e);
        } finally {
            em.close();
        }

    }
}
