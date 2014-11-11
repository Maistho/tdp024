package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountEntityFacade {

    class AccountEntityFacadeIllegalArgumentException extends Exception {

        public AccountEntityFacadeIllegalArgumentException(String message) {
            super(message);
        }

        public AccountEntityFacadeIllegalArgumentException(Exception e) {
            super(e);
        }

        public AccountEntityFacadeIllegalArgumentException(String message, Exception e) {
            super(message, e);
        }
    }

    class AccountEntityFacadeStorageException extends Exception {

        public AccountEntityFacadeStorageException(String message) {
            super(message);
        }

        public AccountEntityFacadeStorageException(Exception e) {
            super(e);
        }

        public AccountEntityFacadeStorageException(String message, Exception e) {
            super(message, e);
        }
    }

    class AccountEntityFacadeInsufficientHoldingsException extends Exception {

        public AccountEntityFacadeInsufficientHoldingsException(String message) {
            super(message);
        }
    }

    void create(String accounttype, String name, String bank)
            throws
            AccountEntityFacadeIllegalArgumentException,
            AccountEntityFacadeStorageException;

    List<Account> findAllByName(String key)
            throws
            AccountEntityFacadeStorageException;

    void credit(long account, long amount)
            throws
            AccountEntityFacadeStorageException,
            AccountEntityFacadeIllegalArgumentException;

    void debit(long account, long amount)
            throws
            AccountEntityFacadeStorageException,
            AccountEntityFacadeInsufficientHoldingsException,
            AccountEntityFacadeIllegalArgumentException;

    Account findById(long account)
            throws
            AccountEntityFacadeIllegalArgumentException;
}
