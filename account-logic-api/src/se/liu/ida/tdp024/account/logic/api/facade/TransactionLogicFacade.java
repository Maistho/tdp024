package se.liu.ida.tdp024.account.logic.api.facade;

import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;

public interface TransactionLogicFacade {

    class TransactionLogicFacadeIllegalArgumentException extends Exception {

        public TransactionLogicFacadeIllegalArgumentException(String message) {
            super(message);
        }

        public TransactionLogicFacadeIllegalArgumentException(Exception e) {
            super(e);
        }
    }

    class TransactionLogicFacadeStorageException extends Exception {

        public TransactionLogicFacadeStorageException(String message) {
            super(message);
        }

        public TransactionLogicFacadeStorageException(Exception e) {
            super(e);
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
