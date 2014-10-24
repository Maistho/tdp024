/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;

/**
 *
 * @author maistho
 */
public class TransactionLogicFacadeImpl implements TransactionLogicFacade {

    private TransactionEntityFacade transactionEntityFacade;
    private AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
    private AccountLogicFacade accountLogicFacade;
    private AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();

    public TransactionLogicFacadeImpl(TransactionEntityFacade transactionEntityFacade, AccountLogicFacade accountLogicFacade) {
        this.transactionEntityFacade = transactionEntityFacade;
        this.accountLogicFacade = accountLogicFacade;
    }

    @Override
    public void debit(long account_id, long amount)
            throws
            TransactionLogicFacadeIllegalArgumentException,
            TransactionLogicFacadeStorageException {
        boolean status = true;
        try {
            accountLogicFacade.debit(account_id, amount);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
            Logger.getLogger(TransactionLogicFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Logger.getLogger(TransactionLogicFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccountLogicFacade.AccountLogicFacadeInsufficientHoldingsException ex) {
            status = false;
        }
        try {

            Account account = accountEntityFacade.findById(account_id);
            transactionEntityFacade.debit(account, amount, status);

        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException ex) {
            //TODO: replace logs
            Logger.getLogger(TransactionLogicFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransactionEntityFacade.TransactionEntityFacadeIllegalArgumentException ex) {
            Logger.getLogger(TransactionLogicFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransactionEntityFacade.TransactionEntityFacadeStorageException ex) {
            Logger.getLogger(TransactionLogicFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void credit(long account_id, long amount)
            throws
            TransactionLogicFacadeIllegalArgumentException,
            TransactionLogicFacadeStorageException {
        try {
            accountLogicFacade.credit(account_id, amount);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException e) {
            throw new TransactionLogicFacadeIllegalArgumentException(e.getMessage());
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException e) {
            throw new TransactionLogicFacadeStorageException(e.getMessage());
        }
        try {
            Account account = accountEntityFacade.findById(account_id);
            transactionEntityFacade.credit(account, amount);
        } catch (TransactionEntityFacade.TransactionEntityFacadeIllegalArgumentException e) {
            throw new TransactionLogicFacadeIllegalArgumentException(e.getMessage());
        } catch (TransactionEntityFacade.TransactionEntityFacadeStorageException e) {
            throw new TransactionLogicFacadeStorageException(e.getMessage());
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException ex) {
            //TODO: replace logger
            Logger.getLogger(TransactionLogicFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String findAllFromAccount(long account)
            throws
            TransactionLogicFacadeIllegalArgumentException {
        try {
            return jsonSerializer.toJson(transactionEntityFacade.findAllFromAccount(account));
        } catch (TransactionEntityFacade.TransactionEntityFacadeIllegalArgumentException e) {
            throw new TransactionLogicFacadeIllegalArgumentException(e.getMessage());
        }
    }

}
