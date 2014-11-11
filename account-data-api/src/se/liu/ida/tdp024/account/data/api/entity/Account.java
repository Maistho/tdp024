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
    }

    long getId();

    List<Transaction> getTransactions();

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
