package se.liu.ida.tdp024.account.logic.api.facade;

public interface AccountLogicFacade {

    class AccountLogicFacadeIllegalArgumentException extends Exception {

        public AccountLogicFacadeIllegalArgumentException(String message) {
            super(message);
        }
    }

    class AccountLogicFacadeConnectionException extends Exception {

        public AccountLogicFacadeConnectionException(String message) {
            super(message);
        }
    }

    class AccountLogicFacadeStorageException extends Exception {

        public AccountLogicFacadeStorageException(String message) {
            super(message);
        }
    }

    class AccountLogicFacadeInsufficientHoldingsException extends Exception {

        public AccountLogicFacadeInsufficientHoldingsException(String message) {
            super(message);
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
