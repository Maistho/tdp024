package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.List;
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
            PersonDTO persondto = jsonSerializer.fromJson(
                    http.get(FinalConstants.PERSON_ENDPOINT + "find.name", "name", name),
                    PersonDTO.class);
            PersonDTO bankdto = jsonSerializer.fromJson(
                    http.get(FinalConstants.BANK_ENDPOINT + "find.name", "name", bank),
                    PersonDTO.class);
            if (persondto == null || bankdto == null) {
                throw new AccountLogicFacadeIllegalArgumentException("could not find person or bank");
            }
            String personKey = persondto.getKey();
            String bankKey = bankdto.getKey();
            accountEntityFacade.create(accounttype, personKey, bankKey);
        } catch (HTTPHelperConnectionException e) {
            logger.log(AccountLogger.AccountLoggerLevel.ALERT, "AccountLogicFacadeImpl.create",
                    String.format("%s", e.getMessage()));
            throw new AccountLogicFacadeConnectionException(e);
        } catch (HTTPHelper.HTTPHelperMalformedURLException e) {
            //TODO: do something
        } catch (AccountEntityFacadeIllegalArgumentException e) {
            //TODO: log
            throw new AccountLogicFacadeIllegalArgumentException(e); //TODO
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException e) {
            logger.log(AccountLogger.AccountLoggerLevel.CRITICAL, "AccountLogicFacadeImpl.create",
                    String.format("%s", e.getMessage()));
            throw new AccountLogicFacadeStorageException(e);
        }
    }

    @Override
    public String find(String name)
            throws
            AccountLogicFacadeIllegalArgumentException,
            AccountLogicFacadeStorageException {
        try {
            PersonDTO persondto = jsonSerializer.fromJson(
                    http.get(FinalConstants.PERSON_ENDPOINT + "find.name", "name", name),
                    PersonDTO.class);
            if (persondto == null) {
                throw new AccountLogicFacadeIllegalArgumentException("could not find person");
            }
            String personKey = persondto.getKey();
            List<Account> accounts = accountEntityFacade.findAllByName(personKey);
            return jsonSerializer.toJson(accounts);
        } catch (HTTPHelperConnectionException e) {
            logger.log(e);
            throw new AccountLogicFacadeIllegalArgumentException(e);
        } catch (HTTPHelper.HTTPHelperMalformedURLException e) {
            logger.log(e);
            throw new AccountLogicFacadeIllegalArgumentException("Malformed URL", e);
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException e) {
            logger.log(e);
            throw new AccountLogicFacadeStorageException(e);
        }
    }

    @Override
    public void credit(long account, long amount) throws AccountLogicFacadeIllegalArgumentException, AccountLogicFacadeStorageException {
        try {
            accountEntityFacade.credit(account, amount);
        } catch (AccountEntityFacadeIllegalArgumentException e) {
            logger.log(e);
            throw new AccountLogicFacadeIllegalArgumentException(e);
        } catch (AccountEntityFacade.AccountEntityFacadeStorageException e) {
            logger.log(e);
            throw new AccountLogicFacadeStorageException(e);
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
            throw new AccountLogicFacadeStorageException(e);
        } catch (AccountEntityFacade.AccountEntityFacadeInsufficientHoldingsException e) {
            logger.log(AccountLogger.AccountLoggerLevel.ALERT, "Not enough holdings on account",
                    String.format("Not enough holdings on account '%d' to make debit of '%d'", account, amount));
            throw new AccountLogicFacadeInsufficientHoldingsException(e);
        } catch (AccountEntityFacadeIllegalArgumentException e) {
            throw new AccountLogicFacadeIllegalArgumentException(e);
        }

    }
}
