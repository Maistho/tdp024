package se.liu.ida.tdp024.account.logic.api.facade;

public interface TransactionLogicFacade {

    class TransactionLogicFacadeIllegalArgumentException extends Exception {

        public TransactionLogicFacadeIllegalArgumentException(String message) {
            super(message);
        }
    }

    class TransactionLogicFacadeStorageException extends Exception {

        public TransactionLogicFacadeStorageException(String message) {
            super(message);
        }
    }
    

    void debit(long account, long amount)
            throws
            TransactionLogicFacadeIllegalArgumentException,
            TransactionLogicFacadeStorageException;

    void credit(long account, long amount)
            throws
            TransactionLogicFacadeIllegalArgumentException,
            TransactionLogicFacadeStorageException;

    String findAllFromAccount(long account)
            throws
            TransactionLogicFacadeIllegalArgumentException;

}
