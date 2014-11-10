package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;
import java.util.List;

public interface Account extends Serializable {

    enum AccountTypes {

        SAVINGS,
        CHECK
    }

    public class AccountIllegalArgumentException extends Exception {

        public AccountIllegalArgumentException(String message) {
            super(message);
        }
    }

    public long getId();

    public List<Transaction> getTransactions();
    
    public String getAccountType();

    public void setAccountType(String accounttype)
            throws
            AccountIllegalArgumentException;

    public String getPersonKey();

    public void setPersonKey(String name);

    public String getBankKey();

    public void setBankKey(String bank);

    public long getHoldings();

    public void setHoldings(long holdings);
}
