package se.liu.ida.tdp024.account.logic.api.facade;

import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;

public interface AccountLogicFacade {

    class AccountLogicFacadeIllegalArgumentException extends Exception {

        public AccountLogicFacadeIllegalArgumentException(String message) {
            super(message);
        }

        public AccountLogicFacadeIllegalArgumentException(Exception e) {
            super(e);
        }

        public AccountLogicFacadeIllegalArgumentException(String message, Exception e) {
            super(message, e);
        }
    }

    class AccountLogicFacadeConnectionException extends Exception {

        public AccountLogicFacadeConnectionException(String message) {
            super(message);
        }

        public AccountLogicFacadeConnectionException(Exception e) {
            super(e);
        }
    }

    class AccountLogicFacadeStorageException extends Exception {

        public AccountLogicFacadeStorageException(String message) {
            super(message);
        }

        public AccountLogicFacadeStorageException(Exception e) {
            super(e);
        }
    }

    class AccountLogicFacadeInsufficientHoldingsException extends Exception {

        public AccountLogicFacadeInsufficientHoldingsException(String message) {
            super(message);
        }

        public AccountLogicFacadeInsufficientHoldingsException(Exception e) {
            super(e);
        }
    }

    void create(String accounttype, String name, String bank)
            throws
            AccountLogicFacadeIllegalArgumentException,
            AccountLogicFacadeConnectionException,
            AccountLogicFacadeStorageException;

    String find(String name)
            throws
            AccountLogicFacadeIllegalArgumentException,
            AccountLogicFacadeStorageException;

    void credit(long account, long amount)
            throws
            AccountLogicFacadeIllegalArgumentException,
            AccountLogicFacadeStorageException;

    void debit(long account, long amount)
            throws
            AccountLogicFacadeIllegalArgumentException,
            AccountLogicFacadeStorageException,
            AccountLogicFacadeInsufficientHoldingsException;
}
