package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;
import java.util.List;

public interface Account extends Serializable {

    enum AccountTypes {

        SAVINGS,
        CHECK
    }

    class AccountIllegalArgumentException extends Exception {

        public AccountIllegalArgumentException(String message) {
            super(message);
        }

        public AccountIllegalArgumentException(String message, Exception e) {
            super(message, e);
        }
    }

    long getId();

    List<Transaction> getTransactions();

    void setTransactions(List<Transaction> transactions);

    void addTransaction(Transaction transaction);

    String getAccountType();

    void setAccountType(String accounttype)
            throws
            AccountIllegalArgumentException;

    String getPersonKey();

    void setPersonKey(String name);

    String getBankKey();

    void setBankKey(String bank);

    long getHoldings();

    void setHoldings(long holdings);
}
