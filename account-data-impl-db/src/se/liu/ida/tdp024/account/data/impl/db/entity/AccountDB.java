package se.liu.ida.tdp024.account.data.impl.db.entity;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

@Entity
public class AccountDB implements Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long holdings;

    @OneToMany(mappedBy = "account", targetEntity = TransactionDB.class)
    private List<Transaction> transactions;

    @Override
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactions;
    }

    private String personKey;
    private String bankKey;
    private AccountTypes accountType;

    @Override
    public String getAccountType() {
        return accountType.name();
    }

    @Override
    public void setAccountType(String accounttype)
            throws
            AccountIllegalArgumentException {
        try {
            this.accountType = Account.AccountTypes.valueOf(accounttype);

        } catch (IllegalArgumentException e) {
            throw new Account.AccountIllegalArgumentException("No such account type", e);
        }
    }

    @Override
    public String getPersonKey() {
        return personKey;
    }

    @Override
    public void setPersonKey(String name) {
        this.personKey = name;
    }

    @Override
    public String getBankKey() {
        return bankKey;
    }

    @Override
    public void setBankKey(String bank) {
        this.bankKey = bank;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setHoldings(long holdings) {
        this.holdings = holdings;
    }

    @Override
    public long getHoldings() {
        return holdings;
    }
}
