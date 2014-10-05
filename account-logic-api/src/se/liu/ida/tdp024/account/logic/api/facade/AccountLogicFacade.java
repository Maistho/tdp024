package se.liu.ida.tdp024.account.logic.api.facade;


public interface AccountLogicFacade {
    
    public class AccountLogicFacadeIllegalArgumentException extends Exception {
        public AccountLogicFacadeIllegalArgumentException(String message) {
            super(message);
        }
    }
    
    public void create(String accounttype, String name, String bank)
            throws 
            AccountLogicFacadeIllegalArgumentException;
    
    public String find(String name)
            throws 
            AccountLogicFacadeIllegalArgumentException;
}