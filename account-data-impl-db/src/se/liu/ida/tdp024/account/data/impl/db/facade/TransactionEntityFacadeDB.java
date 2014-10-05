/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.print.attribute.standard.Finishings;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.FinalConstants;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

/**
 *
 * @author maistho
 */
public class TransactionEntityFacadeDB implements TransactionEntityFacade{

    private void createTransaction(long account, long amount, FinalConstants.TransactionTypes transactionType) {
                    
        EntityManager em = EMF.getEntityManager();
        
        try {
            em.getTransaction().begin();
            
            Transaction transaction = new TransactionDB();
            transaction.setAccount(account);
            transaction.setAmount(amount);
            transaction.setTransactionType(transactionType);
            
            em.persist(transaction);
            em.getTransaction().commit();
            em.close();
            
        } catch (Exception e) {
            
        }
}
    
    @Override
    public void debit(long account, long amount) {
        createTransaction(account, amount, FinalConstants.TransactionTypes.DEBIT);
    }
    

    @Override
    public void credit(long account, long amount) {
        createTransaction(account, amount, FinalConstants.TransactionTypes.CREDIT);
    }

    @Override
    public List<Transaction> findAllFromAccount(long account) {
        EntityManager em = EMF.getEntityManager();
        List<Transaction> results = em.createQuery("SELECT t FROM TransactionDB t WHERE t.account = ?1")
                .setParameter(1, account).getResultList();   
        em.close();
        return results;
    }
    
}
