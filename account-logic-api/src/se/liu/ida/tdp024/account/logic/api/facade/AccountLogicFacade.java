package se.liu.ida.tdp024.account.logic.api.facade;

import se.liu.ida.tdp024.account.data.api.exception.AccountInputParameterException;

public interface AccountLogicFacade {
    
    public void create(String accounttype, String name, String bank)
        throws
        AccountInputParameterException;
}