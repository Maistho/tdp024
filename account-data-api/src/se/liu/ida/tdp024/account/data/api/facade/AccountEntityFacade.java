package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountEntityFacade {
    
    public class AccountEntityFacadeIllegalArgumentException extends Exception {
        public AccountEntityFacadeIllegalArgumentException(String message) {
            super(message);
        }
    }
    public void create(String accounttype, String name, String body)
            throws
            AccountEntityFacadeIllegalArgumentException;
    
    public List<Account> findAllByName(String key);
}
