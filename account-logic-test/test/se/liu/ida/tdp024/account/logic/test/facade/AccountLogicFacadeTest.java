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
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;

public class AccountLogicFacadeTest {

    //--- Unit under test ---//
    public AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    public StorageFacade storageFacade = new StorageFacadeDB();

    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }

    @Test
    public void testCreateFailure() {

        try {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "CREDITCARD";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }

        try {
            String name = "Marcus";
            String bank = "SWEDBANK";
            String accountType = "CHECK";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }
        try {
            String name = "Marcus Bendtsen";
            String bank = "LEHMAN";
            String accountType = "CHECK";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }
        try {
            String name = "Marcus Bendtsen";
            String bank = "";
            String accountType = "CHECK";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }
        try {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }
        try {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }
        try {
            String name = "";
            String bank = "SWEDBANK";
            String accountType = "CHECK";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }
        try {
            String name = "";
            String bank = "SWEDBANK";
            String accountType = "CHECK";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }
        try {
            String name = "";
            String bank = "";
            String accountType = "CHECK";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }
        try {
            String name = "";
            String bank = "";
            String accountType = "";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }
        try {
            String name = "";
            String bank = "";
            String accountType = "CHECK";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }
        try {
            String name = "";
            String bank = "";
            String accountType = "CREDITCARD";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }
        try {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "SAVING";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }
        try {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "SAVINGS";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }
        try {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "SAVINGS";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }
        try {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "SAVINGS";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }
        try {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "SAVINGS";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }
        try {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "SAVINGS";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }
        try {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "SAVINGS";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }
        try {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "SAVINGS";
            accountLogicFacade.create(accountType, name, bank);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("Wrong exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("Wrong exception");
        }

    }

    @Test
    public void testCreateSuccessAllCombos() {

        List<String> personNames = new ArrayList<String>();
        List<String> bankNames = new ArrayList<String>();
        List<String> accountTypes = new ArrayList<String>();

        personNames.add("Jakob Pogulis");
        personNames.add("Xena");
        personNames.add("Marcus Bendtsen");
        personNames.add("Zorro");
        personNames.add("Q");

        bankNames.add("SWEDBANK");
        bankNames.add("IKANOBANKEN");
        bankNames.add("JPMORGAN");
        bankNames.add("NORDEA");
        bankNames.add("CITIBANK");
        bankNames.add("HANDELSBANKEN");
        bankNames.add("SBAB");
        bankNames.add("HSBC");
        bankNames.add("NORDNET");

        accountTypes.add("CHECK");
        accountTypes.add("SAVINGS");

        for (String personName : personNames) {
            for (String bankName : bankNames) {
                for (String accountType : accountTypes) {
                    try {
                        accountLogicFacade.create(accountType, personName, bankName);
                    } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
                        Assert.fail("got exception");
                    } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
                        Assert.fail("got exception");
                    } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
                        Assert.fail("got exception");
                    }
                }
            }
        }
    }

    @Test
    public void testFind() {
        try {
            //Test a nonexisting account
            Assert.assertEquals("[]", accountLogicFacade.find("Lisa Lisasson"));
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
            Assert.fail("got exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("got exception");
        }
        try {
            accountLogicFacade.create("SAVINGS", "Lisa Lisasson", "SWEDBANK");
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
            Assert.fail("got exception");
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException ex) {
            Assert.fail("got exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Assert.fail("got exception");
        }
        try {
            String accounts = accountLogicFacade.find("Lisa Lisasson");
            Assert.assertTrue(accounts.length() > 2);
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) {
            Assert.fail("got exception");
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException ex) {
            Logger.getLogger(AccountLogicFacadeTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
