package se.liu.ida.tdp024.account.data.api.facade;

import se.liu.ida.tdp024.account.data.api.exception.AccountInputParameterException;

public interface AccountEntityFacade {
    
    public void create(String accounttype, String name, String body)
            throws
            AccountInputParameterException;
}
