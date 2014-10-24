package se.liu.ida.tdp024.account.logic.api.facade;

public interface TransactionLogicFacade {

    public class TransactionLogicFacadeIllegalArgumentException extends Exception {

        public TransactionLogicFacadeIllegalArgumentException(String message) {
            super(message);
        }
    }

    public class TransactionLogicFacadeStorageException extends Exception {

        public TransactionLogicFacadeStorageException(String message) {
            super(message);
        }
    }
    

    public void debit(long account, long amount)
            throws
            TransactionLogicFacadeIllegalArgumentException,
            TransactionLogicFacadeStorageException;

    public void credit(long account, long amount)
            throws
            TransactionLogicFacadeIllegalArgumentException,
            TransactionLogicFacadeStorageException;

    public String findAllFromAccount(long account)
            throws
            TransactionLogicFacadeIllegalArgumentException;

}
