package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface TransactionEntityFacade {

    public void debit(long account, long amount);
    
    public void credit(long account, long amount);
    
    public List<Transaction> findAllFromAccount(long account);    
}
