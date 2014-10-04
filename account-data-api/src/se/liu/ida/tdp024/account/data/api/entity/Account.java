package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

public interface Account extends Serializable {
    
    public String getAccounttype();
    
    public void setAccounttype(String accounttype);
    
    public String getName();
    public void setName(String name);
    
    public String getBank();
    public void setBank(String bank);
}
