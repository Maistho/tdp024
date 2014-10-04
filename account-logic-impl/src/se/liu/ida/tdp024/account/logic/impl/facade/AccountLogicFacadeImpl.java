package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.data.api.exception.AccountInputParameterException;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    private AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
    
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

    @Override
    public String find(String name) {
        List<Account> results = accountEntityFacade.findAllByName(name);
        return "list is :" + jsonSerializer.toJson(results) + " and has length: " + results.size();
    }
    
}
