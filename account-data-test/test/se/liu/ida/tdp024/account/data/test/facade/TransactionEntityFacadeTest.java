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
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

public class TransactionEntityFacadeTest {

    //---- Unit under test ----//
    private final TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB();
    private final StorageFacade storageFacade = new StorageFacadeDB();

    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }

    @Test
    public void testCreateSuccess() {
        try {
            {
                String name = "Lisa Lisasson";
                String bank = "Bankbanken";
                String accountType = "SAVINGS";
                transactionEntityFacade.create(accountType, name, bank);
            }
            {
                String name = "åäö åäööåäöûüius a    asdf";
                String bank = "åäöäöåöäöå ö äåöäåöä åöä ";
                String accountType = "SAVINGS";
                transactionEntityFacade.create(accountType, name, bank);
            }
            {
                String name = "Lisa Lisasson";
                String bank = "Bankbanken";
                String accountType = "CHECK";
                transactionEntityFacade.create(accountType, name, bank);
            }
        } catch (Exception e) {
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testCreateFailure() {
        try {
            // Test empty accounttype
            {
                String name = "Lisa Lisasson";
                String bank = "Bankbanken";
                String accountType = "";
                transactionEntityFacade.create(accountType, name, bank);
                Assert.fail("no exception");
            }
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException e) {
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException e) {
            Assert.fail("wrong exception");
        }
        try {
            // Test null accounttype
            {
                String name = "Lisa Lisasson";
                String bank = "Bankbanken";
                String accountType = null;
                transactionEntityFacade.create(accountType, name, bank);
                Assert.fail("no exception");
            }
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException e) {
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException e) {
            Assert.fail("wrong exception");
        }
        try {
            // Test invalid accounttype
            {
                String name = "Lisa Lisasson";
                String bank = "Bankbanken";
                String accountType = "BEEP";
                transactionEntityFacade.create(accountType, name, bank);
                Assert.fail("no exception");
            }
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException e) {
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException e) {
            Assert.fail("wrong exception");
        }
    }

    @Test
    public void testFindAllByName() {
        List<Account> results;

        // Test empty result
        try {
            results = transactionEntityFacade.findAllByName("Lisa Lisasson");
            Assert.assertEquals(0, results.size());
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException ex) {
            Assert.fail("Got exception");
        }
        //Create an account
        try {
            String name = "Lisa Lisasson";
            String bank = "Bankbanken";
            String accountType = "CHECK";
            transactionEntityFacade.create(accountType, name, bank);
        } catch (Exception e) {
            Assert.fail("Got exception");
        }
        //Test the created account
        try {
            results = transactionEntityFacade.findAllByName("Lisa Lisasson");
            Assert.assertEquals(1, results.size());
            Assert.assertEquals("CHECK", results.get(0).getAccounttype());
            Assert.assertEquals("Lisa Lisasson", results.get(0).getName());
            Assert.assertEquals("Bankbanken", results.get(0).getBank());
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException ex) {
            Assert.fail("Got exception");
        }
        //Test another empty result
        try {
            results = transactionEntityFacade.findAllByName("Lisa Lisas");
            Assert.assertEquals(0, results.size());
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException ex) {
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testCredit() {
        try {
            //Test nonexisting account

            transactionEntityFacade.credit(-1, 10);
            Assert.fail("No exception");
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException ex) {

        }

        long id = 0;

        try {
            transactionEntityFacade.create("SAVINGS", "a", "a");
            Account result = transactionEntityFacade.findAllByName("a").get(0);
            id = result.getId();
        } catch (Exception e) {
            Assert.fail("got exception");
        }

        try {
            //Test existing account
            transactionEntityFacade.credit(id, 100);
        } catch (Exception e) {
            Assert.fail("got exception");
        }

        try {
            //Test existing account with negative amount
            transactionEntityFacade.credit(id, -10);
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException ex) {
            Assert.fail("wrong exception");
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException ex) {
        }

        try {
            //test existing account with zero
            transactionEntityFacade.credit(id, 0);
        } catch (Exception e) {
            Assert.fail("got exception");
        }
    }

    @Test
    public void testDebit() {
        try {
            //Test nonexisting account

            transactionEntityFacade.debit(-1, 10);
            Assert.fail("No exception");
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountEntityFacade.AccountEntityFacadeInsufficientHoldingsException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException ex) {
        }

        long id = 0;

        try {
            transactionEntityFacade.create("SAVINGS", "a", "a");
            Account result = transactionEntityFacade.findAllByName("a").get(0);
            id = result.getId();
        } catch (Exception e) {
            Assert.fail("got exception");
        }

        try {
            //Test existing account without any money
            transactionEntityFacade.debit(id, 100);
            Assert.fail("No exception");
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException e) {
            Assert.fail("wrong exception");
        } catch (AccountEntityFacade.AccountEntityFacadeInsufficientHoldingsException e) {
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException e) {
            Assert.fail("wrong exception");
        }
        try {
            transactionEntityFacade.credit(id, 100);
        } catch (Exception ex) {
            Assert.fail("got exception");
        }
        try {
            //Test existing account with half the money
            transactionEntityFacade.debit(id, 50);
        } catch (Exception e) {
            Assert.fail("got exception");
        }

        try {
            //Test existing account with only the remaining money
            transactionEntityFacade.debit(id, 50);
        } catch (Exception e) {
            Assert.fail("got exception");
        }

        try {
            //Test existing account without any money
            transactionEntityFacade.debit(id, 1);
            Assert.fail("no exception");
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException e) {
            Assert.fail("wrong exception");
        } catch (AccountEntityFacade.AccountEntityFacadeInsufficientHoldingsException e) {
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException e) {
            Assert.fail("wrong exception");
        }

        try {
            //Test existing account with negative amount
            transactionEntityFacade.debit(id, -10);
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException ex) {
            Assert.fail("wrong exception");
        } catch (AccountEntityFacade.AccountEntityFacadeInsufficientHoldingsException ex) {
            Assert.fail("wrong exception");
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException ex) {
        }

        try {
            //test existing account with zero
            transactionEntityFacade.debit(id, 0);
        } catch (Exception e) {
            Assert.fail("got exception");
        }
    }
}
