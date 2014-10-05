package se.liu.ida.tdp024.account.data.impl.db.entity;

import java.io.Serializable;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.util.FinalConstants;

@Entity
public class TransactionDB  implements Transaction{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    //TODO: should account be of type Account?
    private long account;
    private long amount;
    private FinalConstants.TransactionTypes transactionType;
    
    @Override
    public long getId() {
        return id;
    }

    @Override
    public long getAccount() {
        return account;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public void setAccount(long account) {
        this.account = account;
    }

    @Override
    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public FinalConstants.TransactionTypes getTransactionType() {
        return transactionType;
    }

    @Override
    public void setTransactionType(FinalConstants.TransactionTypes transactionType) {
        this.transactionType = transactionType;
    }
}
