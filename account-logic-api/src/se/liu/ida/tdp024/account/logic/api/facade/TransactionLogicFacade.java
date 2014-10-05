/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.api.facade;

/**
 *
 * @author maistho
 */
public interface TransactionLogicFacade {
    
    public void debit(long account, long amount);
    
    public void credit(long account, long amount);
    
    public String findAllFromAccount(long account);
    
}
