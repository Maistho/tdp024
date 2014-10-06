package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import javax.persistence.EntityManager;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.util.FinalConstants;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerMonlog;

public class AccountEntityFacadeDB implements AccountEntityFacade {
    
    private AccountLogger logger = new AccountLoggerMonlog();
    
    @Override
    public void create(String accounttype, String name, String bank)
            throws
            AccountEntityFacadeIllegalArgumentException,
            AccountEntityFacadeStorageException
    {
        
        EntityManager em = EMF.getEntityManager();
        em.getTransaction().begin();
        
        Account account = new AccountDB();
        try {
            FinalConstants.AccountTypes accountEnum = FinalConstants.AccountTypes.valueOf(accounttype);
            account.setAccounttype(accountEnum);

        } catch (IllegalArgumentException e) {
            logger.log(AccountLogger.AccountLoggerLevel.WARNING, "AccountEntityFacade.create",
                    String.format("No such accounttype: %s\n%s", accounttype, e.getMessage()));
            throw new AccountEntityFacadeIllegalArgumentException("No such account type");
        }
        
        account.setName(name);
        account.setBank(bank);
        
        try {
            em.persist(account);
        } catch (IllegalArgumentException e) {
            logger.log(e);
            throw new AccountEntityFacadeStorageException("Error storing account");
        }

        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<Account> findAllByName(String key) {
         
        EntityManager em = EMF.getEntityManager();
        List<Account> results = em.createQuery("SELECT a FROM AccountDB a WHERE a.name = ?1")
                .setParameter(1, key).getResultList();

        em.close();
        return results;

    }
    
}
