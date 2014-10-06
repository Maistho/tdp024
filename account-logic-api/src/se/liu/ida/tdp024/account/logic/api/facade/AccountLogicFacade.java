package se.liu.ida.tdp024.account.logic.api.facade;


public interface AccountLogicFacade {
    
    public class AccountLogicFacadeIllegalArgumentException extends Exception {
        public AccountLogicFacadeIllegalArgumentException(String message) {
            super(message);
        }
    }
    
    public class AccountLogicFacadeConnectionException extends Exception {
        public AccountLogicFacadeConnectionException(String message) {
            super(message);
        }
    }
    
    public class AccountLogicFacadeStorageException extends Exception {
        public AccountLogicFacadeStorageException(String message) {
            super(message);
        }
    }
    
    
    public void create(String accounttype, String name, String bank)
            throws 
            AccountLogicFacadeIllegalArgumentException,
            AccountLogicFacadeConnectionException,
            AccountLogicFacadeStorageException;
    
    public String find(String name)
            throws 
            AccountLogicFacadeIllegalArgumentException;
}