package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;
import java.util.Date;
import se.liu.ida.tdp024.account.data.api.util.FinalConstants;

public interface Transaction extends Serializable {

    long getId();

    Account getAccount();

    long getAmount();

    FinalConstants.TransactionTypes getTransactionType();

    Date getCreated();

    boolean getStatus();

    void setAccount(Account account);

    void setAmount(long amount);

    void setTransactionType(FinalConstants.TransactionTypes transactionType);

    void setStatus(boolean status);

}
