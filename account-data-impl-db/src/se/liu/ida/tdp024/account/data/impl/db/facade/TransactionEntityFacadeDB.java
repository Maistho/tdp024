package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import javax.persistence.EntityManager;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.FinalConstants;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerMonlog;

public class TransactionEntityFacadeDB implements TransactionEntityFacade {

    private final AccountLogger logger = new AccountLoggerMonlog();
    private final AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();

    private void createTransaction(long account, long amount, FinalConstants.TransactionTypes transactionType, boolean status)
            throws
            TransactionEntityFacadeIllegalArgumentException,
            TransactionEntityFacadeStorageException {

        if (amount < 0) {
            logger.log(AccountLogger.AccountLoggerLevel.WARNING, "Amount of debit less than 0",
                    String.format("Amount was '%d', less than 0", amount));
            throw new TransactionEntityFacadeIllegalArgumentException("Amount less than 0");
        }

        EntityManager em = EMF.getEntityManager();
        em.getTransaction().begin();

        Transaction transaction = new TransactionDB();
        try {
            Account obAccount = accountEntityFacade.findById(account);
            if (obAccount == null) {
                logger.log(AccountLogger.AccountLoggerLevel.WARNING, "No such account",
                        "Account was null");
                throw new TransactionEntityFacadeIllegalArgumentException("Account was null");
            } else {
                transaction.setAccount(obAccount);

            }
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException e) {
            logger.log(e);
            throw new TransactionEntityFacadeStorageException(e.getMessage());
        }
        transaction.setTransactionType(transactionType);
        transaction.setAmount(amount);
        transaction.setStatus(status);

        try {
            em.persist(transaction);
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.log(e);
            throw new TransactionEntityFacadeStorageException("Error storing transaction");
        } finally {
            em.close();
        }
    }

    @Override
    public void debit(long account, long amount, boolean status)
            throws
            TransactionEntityFacadeIllegalArgumentException,
            TransactionEntityFacadeStorageException {
        createTransaction(account, amount, FinalConstants.TransactionTypes.DEBIT, status);

    }

    @Override
    public void credit(long account, long amount)
            throws
            TransactionEntityFacadeIllegalArgumentException,
            TransactionEntityFacadeStorageException {
        createTransaction(account, amount, FinalConstants.TransactionTypes.CREDIT, true);
    }

    @Override
    public List<Transaction> findAllFromAccount(long account)
            throws
            TransactionEntityFacadeIllegalArgumentException {
        EntityManager em = EMF.getEntityManager();
        List<Transaction> results = null;
        try {
            results = em.createQuery("SELECT t FROM TransactionDB t WHERE t.account = ?1")
                    .setParameter(1, account).getResultList();
        } catch (IllegalArgumentException e) {
            logger.log(e);
            throw new TransactionEntityFacadeIllegalArgumentException(e.getMessage());
        } finally {
            em.close();
        }
        return results;
    }
}
