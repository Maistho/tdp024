package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;
import se.liu.ida.tdp024.account.data.api.util.FinalConstants;

public interface Account extends Serializable {
    
    public long getId();
    
    public FinalConstants.AccountTypes getAccounttype();
    
    public void setAccounttype(FinalConstants.AccountTypes accounttype);
    
    public String getName();
    public void setName(String name);
    
    public String getBank();
    public void setBank(String bank);
}
