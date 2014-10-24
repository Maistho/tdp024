package se.liu.ida.tdp024.account.data.impl.db.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.util.FinalConstants;

@Entity
public class TransactionDB implements Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private final Date created;
    private boolean status;

    private long account;
    
    private long amount;
    private FinalConstants.TransactionTypes transactionType;

    public TransactionDB() {
        this.created = new Date();
    }

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

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public boolean getStatus() {
        return status;
    }

    @Override
    public void setStatus(boolean status) {
        this.status = status;
    }
}
