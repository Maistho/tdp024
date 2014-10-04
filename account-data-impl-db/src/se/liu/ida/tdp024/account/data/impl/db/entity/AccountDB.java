package se.liu.ida.tdp024.account.data.impl.db.entity;

import se.liu.ida.tdp024.account.data.api.entity.Account;

public class AccountDB implements Account {
    
    private String accounttype;
    private String bank;
    private String name;
    
    @Override
    public String getAccounttype() {
        return accounttype;
    }
    @Override
    public void setAccounttype(String accounttype) {
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
    
}
