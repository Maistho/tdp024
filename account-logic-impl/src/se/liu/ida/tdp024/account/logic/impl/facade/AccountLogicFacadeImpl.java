package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade.AccountEntityFacadeIllegalArgumentException;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.logic.api.util.FinalConstants;
import se.liu.ida.tdp024.account.logic.api.util.PersonDTO;
import se.liu.ida.tdp024.account.util.http.HTTPHelper.HTTPHelperConnectionException;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerMonlog;

public class AccountLogicFacadeImpl implements AccountLogicFacade {

    private AccountEntityFacade accountEntityFacade;
    private AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
    private HTTPHelper http = new HTTPHelperImpl();
    private AccountLogger logger = new AccountLoggerMonlog();

    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
    }

    @Override
    public void create(
            String accounttype,
            String name,
            String bank)
            throws
            AccountLogicFacadeIllegalArgumentException,
            AccountLogicFacadeConnectionException,
            AccountLogicFacadeStorageException {
        try {
            String personKey = jsonSerializer.fromJson(
                    http.get(FinalConstants.PERSON_ENDPOINT + "find.name", "name", name),
                    PersonDTO.class).getKey();
            String bankKey = jsonSerializer.fromJson(
                    http.get(FinalConstants.BANK_ENDPOINT + "find.name", "name", bank),
                    PersonDTO.class).getKey();

            accountEntityFacade.create(accounttype, personKey, bankKey);

        } catch (NullPointerException e) {
            logger.log(AccountLogger.AccountLoggerLevel.WARNING, "AccountLogicFacadeImpl.create",
                    String.format("No such user or bank"));
            throw new AccountLogicFacadeIllegalArgumentException("No such user or bank");
        } catch (HTTPHelperConnectionException e) {
            logger.log(AccountLogger.AccountLoggerLevel.ALERT, "AccountLogicFacadeImpl.create",
                    String.format("%s", e.getMessage()));
            throw new AccountLogicFacadeConnectionException(e.getMessage());
        } catch (HTTPHelper.HTTPHelperMalformedURLException e) {

        } catch (AccountEntityFacadeIllegalArgumentException e) {
            //TODO: log
            throw new AccountLogicFacadeIllegalArgumentException(e.toString()); //TODO
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException e) {
            logger.log(AccountLogger.AccountLoggerLevel.CRITICAL, "AccountLogicFacadeImpl.create",
                    String.format("%s", e.getMessage()));
            throw new AccountLogicFacadeStorageException(e.getMessage());
        }
    }

    @Override
    public String find(String name)
            throws
            AccountLogicFacadeIllegalArgumentException,
            AccountLogicFacadeStorageException {
        try {
            String personKey = jsonSerializer.fromJson(
                    http.get(FinalConstants.PERSON_ENDPOINT + "find.name", "name", name),
                    PersonDTO.class).getKey();
            List<Account> accounts = accountEntityFacade.findAllByName(personKey);
            return jsonSerializer.toJson(accounts);
        } catch (NullPointerException e) {
            logger.log(e);
            throw new AccountLogicFacadeIllegalArgumentException("No such Person");
        } catch (HTTPHelperConnectionException e) {
            logger.log(e);
            throw new AccountLogicFacadeIllegalArgumentException(e.toString());
        } catch (HTTPHelper.HTTPHelperMalformedURLException e) {
            logger.log(e);
            throw new AccountLogicFacadeIllegalArgumentException("Malformed URL");
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException e) {
            logger.log(e);
            throw new AccountLogicFacadeStorageException(e.getMessage());
        }
    }

    @Override
    public void credit(long account, long amount) throws AccountLogicFacadeIllegalArgumentException, AccountLogicFacadeStorageException {
        try {
            accountEntityFacade.credit(account, amount);
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException e) {
            logger.log(e);
            throw new AccountLogicFacadeStorageException(e.getMessage());
        } catch (AccountEntityFacadeIllegalArgumentException e) {
            logger.log(e);
            throw new AccountLogicFacadeIllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void debit(long account, long amount)
            throws
            AccountLogicFacadeIllegalArgumentException,
            AccountLogicFacadeStorageException,
            AccountLogicFacadeInsufficientHoldingsException {
        try {
            accountEntityFacade.debit(account, amount);
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException e) {
            throw new AccountLogicFacadeStorageException(e.getMessage());
        } catch (AccountEntityFacade.AccountEntityFacadeInsufficientHoldingsException e) {
            logger.log(AccountLogger.AccountLoggerLevel.ALERT, "Not enough holdings on account",
                    String.format("Not enough holdings on account '%d' to make debit of '%d'", account, amount));
            throw new AccountLogicFacadeInsufficientHoldingsException(e.getMessage());
        } catch (AccountEntityFacadeIllegalArgumentException e) {
            throw new AccountLogicFacadeIllegalArgumentException(e.getMessage());
        }

    }
}
