package se.liu.ida.tdp024.account.data.impl.db.facade;

import javax.persistence.EntityManager;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.exception.AccountInputParameterException;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.data.api.entity.Account;
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
            throw new AccountInputParameterException("Somethings null");
        }
        
        EntityManager em = EMF.getEntityManager();
        
        try {
            em.getTransaction().begin();
            
            Account account = new AccountDB();
            account.setAccounttype(accounttype);
            account.setName(name);
            account.setBank(bank);
            
        } catch (Exception e) {
            
        }
    }
    
}
