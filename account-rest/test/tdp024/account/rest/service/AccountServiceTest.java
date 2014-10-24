package tdp024.account.rest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    @Test
    public void testFind2() {
        {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "SAVINGS";
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals("OK", response.getEntity().toString());
        }

        String accountJson = accountService.find("Marcus Bendtsen").getEntity().toString();
        AccountDTO[] accountDTos = jsonSerializer.fromJson(accountJson, AccountDTO[].class);

        AccountDTO accountDTO = accountDTos[0];

        accountService.credit(accountDTO.getId(), 200);
        accountService.debit(accountDTO.getId(), 50);
        accountService.credit(accountDTO.getId(), 25);
        accountService.debit(accountDTO.getId(), 100);
        accountService.debit(accountDTO.getId(), 75);
        accountService.debit(accountDTO.getId(), 10);

        String transactionJson = accountService.transactions(accountDTO.getId()).getEntity().toString();

        TransactionDTO[] transactionsArray = jsonSerializer.fromJson(transactionJson, TransactionDTO[].class);
        List<TransactionDTO> transactions = new ArrayList<TransactionDTO>();
        for (TransactionDTO t : transactionsArray) {
            transactions.add(t);
        }

        Collections.sort(transactions, new Comparator<TransactionDTO>() {
            @Override
            public int compare(TransactionDTO t, TransactionDTO t1) {
                if (t.getId() > t1.getId()) {
                    return 1;
                } else if (t.getId() < t1.getId()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        Assert.assertEquals("OK", transactions.get(0).getStatus());
        Assert.assertEquals("OK", transactions.get(1).getStatus());
        Assert.assertEquals("OK", transactions.get(2).getStatus());
        Assert.assertEquals("OK", transactions.get(3).getStatus());
        Assert.assertEquals("OK", transactions.get(4).getStatus());
        Assert.assertEquals("FAILED", transactions.get(5).getStatus());

        Assert.assertEquals(200, transactions.get(0).getAmount());
        Assert.assertEquals(50, transactions.get(1).getAmount());
        Assert.assertEquals(25, transactions.get(2).getAmount());
        Assert.assertEquals(100, transactions.get(3).getAmount());
        Assert.assertEquals(75, transactions.get(4).getAmount());
        Assert.assertEquals(10, transactions.get(5).getAmount());

        Assert.assertEquals("CREDIT", transactions.get(0).getType());
        Assert.assertEquals("DEBIT", transactions.get(1).getType());
        Assert.assertEquals("CREDIT", transactions.get(2).getType());
        Assert.assertEquals("DEBIT", transactions.get(3).getType());
        Assert.assertEquals("DEBIT", transactions.get(4).getType());
        Assert.assertEquals("DEBIT", transactions.get(5).getType());

        Assert.assertEquals(accountDTO, transactions.get(0).getAccount());
        Assert.assertEquals(accountDTO, transactions.get(1).getAccount());
        Assert.assertEquals(accountDTO, transactions.get(2).getAccount());
        Assert.assertEquals(accountDTO, transactions.get(3).getAccount());
        Assert.assertEquals(accountDTO, transactions.get(4).getAccount());
        Assert.assertEquals(accountDTO, transactions.get(5).getAccount());

        Assert.assertNotNull(transactions.get(0).getCreated());
        Assert.assertNotNull(transactions.get(1).getCreated());
        Assert.assertNotNull(transactions.get(2).getCreated());
        Assert.assertNotNull(transactions.get(3).getCreated());
        Assert.assertNotNull(transactions.get(4).getCreated());
        Assert.assertNotNull(transactions.get(5).getCreated());

    }

    @Test
    public void testDebitConcurrency() {
        {
            String name = "Jakob Pogulis";
            String bank = "SWEDBANK";
            String accountType = "SAVINGS";
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals("OK", response.getEntity().toString());
            Assert.assertEquals(200, response.getStatus());
        }

        String accountJson = accountService.find("Jakob Pogulis").getEntity().toString();
        AccountDTO[] accountDTos = jsonSerializer.fromJson(accountJson, AccountDTO[].class
        );
        final AccountDTO accountDTO = accountDTos[0];

        //Initialize the account with a random amount
        long initialAmount = (long) (Math.random() * 100);

        accountService.credit(accountDTO.getId(), initialAmount);

        //Create lots of small removals
        int size = 1000;
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0;
                i < size;
                i++) {
            threads.add(new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.currentThread().sleep((long) (Math.random() * 100));
                        long amount = (long) (Math.random() * 10);
                        accountService.debit(accountDTO.getId(), amount);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        //Run the removals
        for (Thread thread : threads) {
            thread.start();
        }

        //Assume that it take 20 seconds to complete
        try {
            Thread.currentThread().sleep(20000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Now check the balance of the account
        {
            String checkJson = accountService.find("Jakob Pogulis").getEntity().toString();
            AccountDTO refreshedAccountDTO = jsonSerializer.fromJson(checkJson, AccountDTO[].class)[0];
            Assert.assertEquals(0, refreshedAccountDTO.getHoldings());
        }

    }

    @Test
    public void testCreditConcurrency() {
        {
            String name = "Zorro";
            String bank = "SWEDBANK";
            String accountType = "SAVINGS";
            Response response = accountService.create(accountType, name, bank);
            Assert.assertEquals("OK", response.getEntity().toString());
            Assert.assertEquals(200, response.getStatus());
        }

        String accountJson = accountService.find("Zorro").getEntity().toString();
        AccountDTO[] accountDTos = jsonSerializer.fromJson(accountJson, AccountDTO[].class
        );
        final AccountDTO accountDTO = accountDTos[0];

        //Create lots of small credits
        final int size = 1000;
        final int amount = 10;
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0;
                i < size;
                i++) {
            threads.add(new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.currentThread().sleep((long) (Math.random() * 100));
                        accountService.credit(accountDTO.getId(), amount);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        //Run the removals
        for (Thread thread : threads) {
            thread.start();
        }

        //Assume that it take 20 seconds to complete
        try {
            Thread.currentThread().sleep(20000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Now check the balance of the account
        {
            String checkJson = accountService.find("Zorro").getEntity().toString();
            AccountDTO refreshedAccountDTO = jsonSerializer.fromJson(checkJson, AccountDTO[].class)[0];
            Assert.assertEquals(size * amount, refreshedAccountDTO.getHoldings());
        }
    }
}
