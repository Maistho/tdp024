package se.liu.ida.tdp024.account.data.impl.db.entity;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class AccountDB implements Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long holdings;

    private String name;
    private String bank;
    private AccountTypes accounttype;

    @Override
    public String getAccounttype() {
        return accounttype.name();
    }

    @Override
    public void setAccounttype(String accounttype)
            throws
            AccountIllegalArgumentException {
        try {
            this.accounttype = Account.AccountTypes.valueOf(accounttype);

        } catch (IllegalArgumentException e) {
            throw new Account.AccountIllegalArgumentException("No such account type");
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getBank() {
        return bank;
    }

    @Override
    public void setBank(String bank) {
        this.bank = bank;
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
