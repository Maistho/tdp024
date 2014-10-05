package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;
import se.liu.ida.tdp024.account.data.api.util.FinalConstants;


public interface Transaction extends Serializable{
    
    public long getId();
    
    public long getAccount();
    public long getAmount();
    public FinalConstants.TransactionTypes getTransactionType();
    
    public void setAccount(long account);
    public void setAmount(long amount);
    public void setTransactionType(FinalConstants.TransactionTypes transactionType);
    
}
