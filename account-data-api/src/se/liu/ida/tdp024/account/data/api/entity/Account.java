package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;
import se.liu.ida.tdp024.account.data.api.util.FinalConstants;

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

    public String getAccounttype();

    public void setAccounttype(String accounttype)
            throws
            AccountIllegalArgumentException;

    public String getName();

    public void setName(String name);

    public String getBank();

    public void setBank(String bank);

    public long getHoldings();

    public void setHoldings(long holdings);
}
