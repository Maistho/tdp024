package se.liu.ida.tdp024.account.logic.impl.facade;

import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.data.api.exception.AccountInputParameterException;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
    }
    
    
    @Override
    public void create(
            String accounttype,
            String name,
            String bank)
            throws
            AccountInputParameterException
    {
        try {
            accountEntityFacade.create(accounttype, name, bank);
        } catch (AccountInputParameterException e) {
            throw e;
        }
    }
}
