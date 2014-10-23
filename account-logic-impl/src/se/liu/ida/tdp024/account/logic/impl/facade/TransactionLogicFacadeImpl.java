/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
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

    public TransactionLogicFacadeImpl(TransactionEntityFacade transactionEntityFacade, AccountLogicFacade accountLogicFacade) {
        this.transactionEntityFacade = transactionEntityFacade;
        this.accountLogicFacade = accountLogicFacade;
    }

    @Override
    public void debit(long account, long amount)
            throws
            TransactionLogicFacadeIllegalArgumentException,
            TransactionLogicFacadeStorageException {
        try {
            transactionEntityFacade.debit(account, amount);
        } catch (TransactionEntityFacade.TransactionEntityFacadeIllegalArgumentException e) {
            throw new TransactionLogicFacadeIllegalArgumentException(e.getMessage());
        } catch (TransactionEntityFacade.TransactionEntityFacadeStorageException e) {
            throw new TransactionLogicFacadeStorageException(e.getMessage());
        }
    }

    @Override
    public void credit(long account, long amount)
            throws
            TransactionLogicFacadeIllegalArgumentException,
            TransactionLogicFacadeStorageException {
        try {
            accountLogicFacade.credit(account, amount);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException e) {
            throw new TransactionLogicFacadeIllegalArgumentException(e.getMessage());
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException e) {
            throw new TransactionLogicFacadeStorageException(e.getMessage());
        }
        try {
            transactionEntityFacade.credit(account, amount);
        } catch (TransactionEntityFacade.TransactionEntityFacadeIllegalArgumentException e) {
            throw new TransactionLogicFacadeIllegalArgumentException(e.getMessage());
        } catch (TransactionEntityFacade.TransactionEntityFacadeStorageException e) {
            throw new TransactionLogicFacadeStorageException(e.getMessage());
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
