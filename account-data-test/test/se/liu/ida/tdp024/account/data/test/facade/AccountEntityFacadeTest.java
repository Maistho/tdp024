package se.liu.ida.tdp024.account.data.test.facade;

import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

public class AccountEntityFacadeTest {

    //---- Unit under test ----//
    private final AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
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
                accountEntityFacade.create(accountType, name, bank);
            }
            {
                String name = "åäö åäööåäöûüius a    asdf";
                String bank = "åäöäöåöäöå ö äåöäåöä åöä ";
                String accountType = "SAVINGS";
                accountEntityFacade.create(accountType, name, bank);
            }
            {
                String name = "Lisa Lisasson";
                String bank = "Bankbanken";
                String accountType = "CHECK";
                accountEntityFacade.create(accountType, name, bank);
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
                accountEntityFacade.create(accountType, name, bank);
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
                accountEntityFacade.create(accountType, name, bank);
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
                accountEntityFacade.create(accountType, name, bank);
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
            results = accountEntityFacade.findAllByName("Lisa Lisasson");
            Assert.assertEquals(0, results.size());
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException ex) {
            Assert.fail("Got exception");
        }
        //Create an account
        try {
            String name = "Lisa Lisasson";
            String bank = "Bankbanken";
            String accountType = "CHECK";
            accountEntityFacade.create(accountType, name, bank);
        } catch (Exception e) {
            Assert.fail("Got exception");
        }
        //Test the created account
        try {
            results = accountEntityFacade.findAllByName("Lisa Lisasson");
            Assert.assertEquals(1, results.size());
            Assert.assertEquals("CHECK", results.get(0).getAccountType());
            Assert.assertEquals("Lisa Lisasson", results.get(0).getPersonKey());
            Assert.assertEquals("Bankbanken", results.get(0).getBankKey());
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException ex) {
            Assert.fail("Got exception");
        }
        //Test another empty result
        try {
            results = accountEntityFacade.findAllByName("Lisa Lisas");
            Assert.assertEquals(0, results.size());
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException ex) {
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testCredit() {
        try {
            //Test nonexisting account

            accountEntityFacade.credit(-1, 10);
            Assert.fail("No exception");
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException ex) {

        }

        long id = 0;

        try {
            accountEntityFacade.create("SAVINGS", "a", "a");
            Account result = accountEntityFacade.findAllByName("a").get(0);
            id = result.getId();
        } catch (Exception e) {
            Assert.fail("got exception");
        }

        try {
            //Test existing account
            accountEntityFacade.credit(id, 100);
        } catch (Exception e) {
            Assert.fail("got exception");
        }

        try {
            //Test existing account with negative amount
            accountEntityFacade.credit(id, -10);
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException ex) {
            Assert.fail("wrong exception");
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException ex) {
        }

        try {
            //test existing account with zero
            accountEntityFacade.credit(id, 0);
        } catch (Exception e) {
            Assert.fail("got exception");
        }
    }

    @Test
    public void testDebit() {
        try {
            //Test nonexisting account

             accountEntityFacade.debit(-1, 10);
            Assert.fail("No exception");
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountEntityFacade.AccountEntityFacadeInsufficientHoldingsException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException ex) {
        }

        long id = 0;

        try {
            accountEntityFacade.create("SAVINGS", "a", "a");
            Account result = accountEntityFacade.findAllByName("a").get(0);
            id = result.getId();
        } catch (Exception e) {
            Assert.fail("got exception");
        }

        try {
            //Test existing account without any money
            accountEntityFacade.debit(id, 100);
            Assert.fail("No exception");
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException e) {
            Assert.fail("wrong exception");
        } catch (AccountEntityFacade.AccountEntityFacadeInsufficientHoldingsException e) {
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException e) {
            Assert.fail("wrong exception");
        }
        try {
            accountEntityFacade.credit(id, 100);
        } catch (Exception ex) {
            Assert.fail("got exception");
        }
        try {
            //Test existing account with half the money
            accountEntityFacade.debit(id, 50);
        } catch (Exception e) {
            Assert.fail("got exception");
        }

        try {
            //Test existing account with only the remaining money
            accountEntityFacade.debit(id, 50);
        } catch (Exception e) {
            Assert.fail("got exception");
        }

        try {
            //Test existing account without any money
            accountEntityFacade.debit(id, 1);
            Assert.fail("no exception");
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException e) {
            Assert.fail("wrong exception");
        } catch (AccountEntityFacade.AccountEntityFacadeInsufficientHoldingsException e) {
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException e) {
            Assert.fail("wrong exception");
        }

        try {
            //Test existing account with negative amount
            accountEntityFacade.debit(id, -10);
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException ex) {
            Assert.fail("wrong exception");
        } catch (AccountEntityFacade.AccountEntityFacadeInsufficientHoldingsException ex) {
            Assert.fail("wrong exception");
        } catch (AccountEntityFacade.AccountEntityFacadeIllegalArgumentException ex) {
        }

        try {
            //test existing account with zero
            accountEntityFacade.debit(id, 0);
        } catch (Exception e) {
            Assert.fail("got exception");
        }
    }
}
