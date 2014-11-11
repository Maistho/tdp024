package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface TransactionEntityFacade {

    class TransactionEntityFacadeIllegalArgumentException extends Exception {

        public TransactionEntityFacadeIllegalArgumentException(String message) {
            super(message);
        }

        public TransactionEntityFacadeIllegalArgumentException(Exception e) {
            super(e);
        }
    }

    class TransactionEntityFacadeStorageException extends Exception {

        public TransactionEntityFacadeStorageException(String message) {
            super(message);
        }

        public TransactionEntityFacadeStorageException(String message, Exception e) {
            super(message, e);
        }

        public TransactionEntityFacadeStorageException(Exception e) {
            super(e);
        }
    }

    void debit(long account, long amount, boolean status)
            throws
            TransactionEntityFacadeIllegalArgumentException,
            TransactionEntityFacadeStorageException;

    void credit(long account, long amount)
            throws
            TransactionEntityFacadeIllegalArgumentException,
            TransactionEntityFacadeStorageException;

    List<Transaction> findAllFromAccount(long account)
            throws
            TransactionEntityFacadeIllegalArgumentException;

}
