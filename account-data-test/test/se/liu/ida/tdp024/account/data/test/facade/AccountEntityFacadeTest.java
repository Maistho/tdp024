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
        results = accountEntityFacade.findAllByName("Lisa Lisasson");
        Assert.assertEquals(0, results.size());
        try {
            String name = "Lisa Lisasson";
            String bank = "Bankbanken";
            String accountType = "CHECK";
            accountEntityFacade.create(accountType, name, bank);
        } catch (Exception e) {
            Assert.fail("Got exception");
        }
        results = accountEntityFacade.findAllByName("Lisa Lisasson");
        Assert.assertEquals(1, results.size());
        Assert.assertEquals("CHECK", results.get(0).getAccounttype());
        Assert.assertEquals("Lisa Lisasson", results.get(0).getName());
        Assert.assertEquals("Bankbanken", results.get(0).getBank());
    }
}
