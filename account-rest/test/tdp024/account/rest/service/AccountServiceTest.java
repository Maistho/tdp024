package tdp024.account.rest.service;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.rest.service.AccountService;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

public class AccountServiceTest {

    private static final HTTPHelper httpHelper = new HTTPHelperImpl();
    private static final String ENDPOINT = "localhost:8080/account-rest/";
    private static final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();

    //-- Units under test ---//
    private final AccountService accountService = new AccountService();
    private final StorageFacade storageFacade = new StorageFacadeDB();

    @After
    public void tearDown() {
        storageFacade.emptyStorage();

    }

    // @Test
    public void createSuccessAllCombos() {

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
                    Response response = accountService.create(accountType, personName, bankName);
                    Assert.assertEquals(200, response.getStatus());
                }
            }
        }
    }

    @Test
    public void createFailure() {
        {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "CREDITCARD";
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals(400, response.getStatus());
        }
        {
            String name = "Marcus";
            String bank = "SWEDBANK";
            String accountType = "CHECK";
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals(400, response.getStatus());
        }
        {
            String name = "Marcus Bendtsen";
            String bank = "LEHMAN";
            String accountType = "CHECK";
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals(400, response.getStatus());
        }
        {
            String name = "Marcus Bendtsen";
            String bank = "";
            String accountType = "CHECK";
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals(400, response.getStatus());
        }
        {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "";
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals(400, response.getStatus());
        }
        {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "";
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals(400, response.getStatus());
        }
        {
            String name = "";
            String bank = "SWEDBANK";
            String accountType = "CHECK";
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals(400, response.getStatus());
        }
        {
            String name = "";
            String bank = "SWEDBANK";
            String accountType = "CHECK";
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals(400, response.getStatus());
        }
        {
            String name = "";
            String bank = "";
            String accountType = "CHECK";
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals(400, response.getStatus());
        }
        {
            String name = "";
            String bank = "";
            String accountType = "";
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals(400, response.getStatus());
        }
        {
            String name = "";
            String bank = "";
            String accountType = "CHECK";
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals(400, response.getStatus());
        }
        {
            String name = "";
            String bank = "";
            String accountType = "CREDITCARD";
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals(400, response.getStatus());
        }
        {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "SAVING"; //It should be SAVINGS
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals(400, response.getStatus());
        }

    }

    @Test
    public void testFind() {
        {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "SAVINGS";
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals("OK", response.getEntity().toString());
        }
        {
            String name = "Marcus Bendtsen";
            String bank = "NORDEA";
            String accountType = "SAVINGS";
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals("OK", response.getEntity().toString());
        }
        {
            String name = "Marcus Bendtsen";
            String bank = "JPMORGAN";
            String accountType = "CHECK";
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals("OK", response.getEntity().toString());
        }

        Response response = accountService.find("Marcus Bendtsen");
        String json = response.getEntity().toString();
        AccountDTO[] accountDTos = jsonSerializer.fromJson(json, AccountDTO[].class);
            Assert.assertTrue(accountDTos.length > 2);


    }

    @Test
    public void testFindFailure() {
        Response response = accountService.find("Marcus");
        String json = response.getEntity().toString();

        Assert.assertEquals("[]", json);

    }
}
