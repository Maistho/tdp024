package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface TransactionEntityFacade {

    public class TransactionEntityFacadeIllegalArgumentException extends Exception {

        public TransactionEntityFacadeIllegalArgumentException(String message) {
            super(message);
        }
    }

    public class TransactionEntityFacadeStorageException extends Exception {

        public TransactionEntityFacadeStorageException(String message) {
            super(message);
        }
    }

    public void debit(long account, long amount, boolean status)
            throws
            TransactionEntityFacadeIllegalArgumentException,
            TransactionEntityFacadeStorageException;

    public void credit(long account, long amount)
            throws
            TransactionEntityFacadeIllegalArgumentException,
            TransactionEntityFacadeStorageException;

    public List<Transaction> findAllFromAccount(long account)
            throws
            TransactionEntityFacadeIllegalArgumentException;

}
