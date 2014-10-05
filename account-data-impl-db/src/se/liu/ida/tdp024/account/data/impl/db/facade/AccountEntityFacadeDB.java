package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.exception.AccountInputParameterException;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.util.FinalConstants;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;

public class AccountEntityFacadeDB implements AccountEntityFacade {
    
    @Override
    public void create(String accounttype, String name, String bank)
            throws
            AccountInputParameterException
    {
        
        if (accounttype == null || name == null || bank == null
                || accounttype.trim().equals("") || name.trim().equals("")
                || bank.trim().equals("")
                ) {
            throw new AccountInputParameterException("Somethings null or empty");
        }
        
        EntityManager em = EMF.getEntityManager();
        try {
            FinalConstants.AccountTypes accountEnum = FinalConstants.AccountTypes.valueOf(accounttype);

            em.getTransaction().begin();
            
            Account account = new AccountDB();
            account.setAccounttype(accountEnum);
            account.setName(name);
            account.setBank(bank);
            
            em.persist(account);
            em.getTransaction().commit();
            em.close();
        } catch (IllegalArgumentException e) {
            throw new AccountInputParameterException("No such accounttype!");
        }
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
