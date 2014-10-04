package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.exception.AccountInputParameterException;
import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountEntityFacade {
    
    public void create(String accounttype, String name, String body)
            throws
            AccountInputParameterException;
    
    public List<Account> findAllByName(String key);
}
