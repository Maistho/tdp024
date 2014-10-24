package se.liu.ida.tdp024.account.logic.test.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;

public class TransactionLogicFacadeTest {

    //--- Unit under test ---//
    public AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    public TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB(), accountLogicFacade);
    public StorageFacade storageFacade = new StorageFacadeDB();

    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }

    @Test
    public void testCreateTransactions() {
        try {
            accountLogicFacade.create("SAVINGS", "Lisa Lisasson", "SWEDBANK");
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
            Logger.getLogger(TransactionLogicFacadeTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Logger.getLogger(TransactionLogicFacadeTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Logger.getLogger(TransactionLogicFacadeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //Test debit
            transactionLogicFacade.debit(1, 100);
        } catch (TransactionLogicFacade.TransactionLogicFacadeIllegalArgumentException ex) {
            Assert.fail("Got exception");
        } catch (TransactionLogicFacade.TransactionLogicFacadeStorageException ex) {
            Assert.fail("Got exception");
        }

        try {
            //Test credit
            transactionLogicFacade.credit(1, 100);
        } catch (TransactionLogicFacade.TransactionLogicFacadeIllegalArgumentException ex) {
            Assert.fail("Got exception");
        } catch (TransactionLogicFacade.TransactionLogicFacadeStorageException ex) {
            Assert.fail("Got exception");
        }

        try {
            //Test debit with holdings
            transactionLogicFacade.debit(1, 50);
        } catch (TransactionLogicFacade.TransactionLogicFacadeIllegalArgumentException ex) {
            Assert.fail("Got exception");
        } catch (TransactionLogicFacade.TransactionLogicFacadeStorageException ex) {
            Assert.fail("Got exception");
        }

        try {
            //Test the same thing again
            transactionLogicFacade.debit(1, 50);
        } catch (TransactionLogicFacade.TransactionLogicFacadeIllegalArgumentException ex) {
            Assert.fail("Got exception");
        } catch (TransactionLogicFacade.TransactionLogicFacadeStorageException ex) {
            Assert.fail("Got exception");
        }

        try {
            //Test negative enough cash
            transactionLogicFacade.debit(1, -1);
            Assert.fail("No Exception");
        } catch (TransactionLogicFacade.TransactionLogicFacadeIllegalArgumentException ex) {
        } catch (TransactionLogicFacade.TransactionLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }

    }

    //Test findallfromaccount
}
