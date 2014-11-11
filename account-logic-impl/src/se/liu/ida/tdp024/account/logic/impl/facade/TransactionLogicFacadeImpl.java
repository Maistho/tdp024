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
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerMonlog;

/**
 *
 * @author maistho
 */
public class TransactionLogicFacadeImpl implements TransactionLogicFacade {

    private final TransactionEntityFacade transactionEntityFacade;
    private final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
    private final AccountLogicFacade accountLogicFacade;
    private final AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    private final AccountLogger logger = new AccountLoggerMonlog();

    public TransactionLogicFacadeImpl(TransactionEntityFacade transactionEntityFacade, AccountLogicFacade accountLogicFacade) {
        this.transactionEntityFacade = transactionEntityFacade;
        this.accountLogicFacade = accountLogicFacade;
    }

    @Override
    public void debit(long accountID, long amount)
            throws
            TransactionLogicFacadeIllegalArgumentException,
            TransactionLogicFacadeStorageException {
        boolean status = true;
        try {
            accountLogicFacade.debit(accountID, amount);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
            //TODO: logs remove them
            Logger.getLogger(TransactionLogicFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Logger.getLogger(TransactionLogicFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccountLogicFacade.AccountLogicFacadeInsufficientHoldingsException ex) {
            status = false;
        }
        try {

            Account account = accountEntityFacade.findById(accountID);
            if (account != null) {
                transactionEntityFacade.debit(accountID, amount, status);
            } else {
                throw new TransactionLogicFacadeIllegalArgumentException("No such account");
            }

        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException e) {
            //TODO: log stuff
            throw new TransactionLogicFacadeIllegalArgumentException(e);
        } catch (TransactionEntityFacade.TransactionEntityFacadeIllegalArgumentException e) {
            throw new TransactionLogicFacadeIllegalArgumentException(e);
        } catch (TransactionEntityFacade.TransactionEntityFacadeStorageException e) {
            throw new TransactionLogicFacadeStorageException(e);
        }
    }

    @Override
    public void credit(long accountID, long amount)
            throws
            TransactionLogicFacadeIllegalArgumentException,
            TransactionLogicFacadeStorageException {

        try {
            Account account = accountEntityFacade.findById(accountID);
            if (account == null) {
                //TODO: Log
                throw new TransactionLogicFacadeIllegalArgumentException(("No such account"));
            }
            transactionEntityFacade.credit(accountID, amount);

        } catch (TransactionEntityFacade.TransactionEntityFacadeIllegalArgumentException e) {
            //TODO: Log
            throw new TransactionLogicFacadeIllegalArgumentException(e);
        } catch (TransactionEntityFacade.TransactionEntityFacadeStorageException e) {
            logger.log(e);
            throw new TransactionLogicFacadeStorageException(e);
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException ex) {
            //TODO: replace logger
            Logger.getLogger(TransactionLogicFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            accountLogicFacade.credit(accountID, amount);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException e) {
            //TODO: Log
            throw new TransactionLogicFacadeIllegalArgumentException(e);
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException e) {
            logger.log(e);
            throw new TransactionLogicFacadeStorageException(e);
        }

    }

    @Override
    public String findAllFromAccount(long account)
            throws
            TransactionLogicFacadeIllegalArgumentException {
        try {
            return jsonSerializer.toJson(transactionEntityFacade.findAllFromAccount(account));
        } catch (TransactionEntityFacade.TransactionEntityFacadeIllegalArgumentException e) {
            throw new TransactionLogicFacadeIllegalArgumentException(e);
        }
    }

}
