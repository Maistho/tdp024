package se.liu.ida.tdp024.account.data.impl.db.entity;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import se.liu.ida.tdp024.account.data.api.util.FinalConstants;

@Entity
public class AccountDB implements Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String name;
    private String bank;
    private FinalConstants.AccountTypes accounttype;
    
    @Override
    public FinalConstants.AccountTypes getAccounttype() {
        return accounttype;
    }
    @Override
    public void setAccounttype(FinalConstants.AccountTypes accounttype) {
        this.accounttype = accounttype;
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
    
}
