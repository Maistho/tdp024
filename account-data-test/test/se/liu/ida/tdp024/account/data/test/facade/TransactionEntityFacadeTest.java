package se.liu.ida.tdp024.account.data.test.facade;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

public class TransactionEntityFacadeTest {

    //---- Unit under test ----//
    private final TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB();
    private final StorageFacade storageFacade = new StorageFacadeDB();
    private final AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();

    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }

    @Test
    public void testCreateTransactions() {
        try {
            //Test credit no account
            transactionEntityFacade.credit(null, 100);
            Assert.fail("No exception");
        } catch (TransactionEntityFacade.TransactionEntityFacadeIllegalArgumentException ex) {
        } catch (TransactionEntityFacade.TransactionEntityFacadeStorageException ex) {
            Assert.fail("wrong exception");
        }

        try {
            //Test debit no account
            transactionEntityFacade.debit(null, 10, true);
            Assert.fail("No exception");
        } catch (TransactionEntityFacade.TransactionEntityFacadeIllegalArgumentException ex) {
        } catch (TransactionEntityFacade.TransactionEntityFacadeStorageException ex) {
            Assert.fail("wrong exception");
        }
        Account account;
        try {
            //create account
            accountEntityFacade.create("SAVINGS", "Lisa", "Banken");
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException ex) {
            Assert.fail("Got Exception");
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException ex) {
            Assert.fail("Got Exception");
        }

        try {
            //Test debit no monies
            account = accountEntityFacade.findById(1);
            transactionEntityFacade.debit(account, 50, false);
        } catch (TransactionEntityFacade.TransactionEntityFacadeIllegalArgumentException ex) {
            Assert.fail("got exception");
        } catch (TransactionEntityFacade.TransactionEntityFacadeStorageException ex) {
            Assert.fail("got exception");
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException ex) {
            Assert.fail("got exception");
        }

        try {
            //Test credit with account
            account = accountEntityFacade.findById(1);
            transactionEntityFacade.credit(account, 100);
        } catch (TransactionEntityFacade.TransactionEntityFacadeIllegalArgumentException ex) {
            Assert.fail("Got Exception");
        } catch (TransactionEntityFacade.TransactionEntityFacadeStorageException ex) {
            Assert.fail("Got Exception");
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException ex) {
            Assert.fail("wrong exception");
        }

        try {
            //Test debit with account
            account = accountEntityFacade.findById(1);
            transactionEntityFacade.debit(account, 50, true);
        } catch (TransactionEntityFacade.TransactionEntityFacadeIllegalArgumentException ex) {
            Assert.fail("Got Exception");
        } catch (TransactionEntityFacade.TransactionEntityFacadeStorageException ex) {
            Assert.fail("Got Exception");
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException ex) {
            Assert.fail("wrong exception");
        }

        try {
            //Test debit with monies again
            account = accountEntityFacade.findById(1);
            transactionEntityFacade.debit(account, 50, true);
        } catch (TransactionEntityFacade.TransactionEntityFacadeIllegalArgumentException ex) {
            Assert.fail("Got Exception");
        } catch (TransactionEntityFacade.TransactionEntityFacadeStorageException ex) {
            Assert.fail("Got Exception");
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException ex) {
            Assert.fail("wrong exception");
        }

        try {
            //Test no more monies left
            account = accountEntityFacade.findById(1);
            transactionEntityFacade.debit(account, 1, false);
        } catch (TransactionEntityFacade.TransactionEntityFacadeIllegalArgumentException ex) {
            Assert.fail("got exception");
        } catch (TransactionEntityFacade.TransactionEntityFacadeStorageException ex) {
            Assert.fail("got exception");
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException ex) {
            Assert.fail("got exception");
        }
    }

}
