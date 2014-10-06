package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountEntityFacade {
    
    class AccountEntityFacadeIllegalArgumentException extends Exception {
        public AccountEntityFacadeIllegalArgumentException(String message) {
            super(message);
        }
    }
    class AccountEntityFacadeStorageException extends Exception {
        public AccountEntityFacadeStorageException(String message) {
            super(message);
        }
    }
    
    
    void create(String accounttype, String name, String body)
            throws
            AccountEntityFacadeIllegalArgumentException,
            AccountEntityFacadeStorageException;
    
    List<Account> findAllByName(String key);
}
