package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import javax.persistence.EntityManager;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.util.FinalConstants;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;

public class AccountEntityFacadeDB implements AccountEntityFacade {
    
    @Override
    public void create(String accounttype, String name, String bank)
            throws
            AccountEntityFacadeIllegalArgumentException
    {
        
        
        
        EntityManager em = EMF.getEntityManager();
        em.getTransaction().begin();
        
        try {
            FinalConstants.AccountTypes accountEnum = FinalConstants.AccountTypes.valueOf(accounttype);
            Account account = new AccountDB();
            account.setAccounttype(accountEnum);
            account.setName(name);
            account.setBank(bank);
            em.persist(account);
        } catch (IllegalArgumentException e) {
            //TODO: log
            throw new AccountEntityFacadeIllegalArgumentException("No such account type");
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
