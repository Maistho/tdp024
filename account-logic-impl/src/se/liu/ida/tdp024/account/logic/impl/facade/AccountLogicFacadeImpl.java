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
            AccountLogicFacadeStorageException
    {
        try
        {
            String personKey = jsonSerializer.fromJson(
                    http.get(FinalConstants.PERSON_ENDPOINT+"find.name", "name", name),
                    PersonDTO.class).getKey();
            String bankKey = jsonSerializer.fromJson(
                    http.get(FinalConstants.BANK_ENDPOINT+"find.name", "name", bank),
                    PersonDTO.class).getKey();
            
            accountEntityFacade.create(accounttype, personKey, bankKey);
            
        }
        catch (NullPointerException e)
        {
            logger.log(AccountLogger.AccountLoggerLevel.WARNING, "AccountLogicFAcadeImpl.create",
                    String.format("No such user or bank"));
            throw new AccountLogicFacadeIllegalArgumentException("No such user or bank");
        }
        catch (HTTPHelperConnectionException e)
        {
            logger.log(AccountLogger.AccountLoggerLevel.ALERT, "AccountLogicFAcadeImpl.create",
                    String.format("%s", e.getMessage()));
            throw new AccountLogicFacadeConnectionException(e.getMessage());
        }
        catch (HTTPHelper.HTTPHelperMalformedURLException e)
        {
                
        }
        catch (AccountEntityFacadeIllegalArgumentException e)
        {
            //TODO: log
            throw new AccountLogicFacadeIllegalArgumentException(e.toString()); //TODO
        }
        catch (AccountEntityFacade.AccountEntityFacadeStorageException e)
        {
            logger.log(AccountLogger.AccountLoggerLevel.CRITICAL, "AccountLogicFAcadeImpl.create",
                    String.format("%s", e.getMessage()));
            throw new AccountLogicFacadeStorageException(e.getMessage());
        }
    }

    @Override
    public String find(String name) 
            throws 
            AccountLogicFacadeIllegalArgumentException 
    {
        try
        {
            String personKey = jsonSerializer.fromJson(
                    http.get(FinalConstants.PERSON_ENDPOINT+"find.name", "name", name),
                    PersonDTO.class).getKey();
            List<Account> accounts = accountEntityFacade.findAllByName(personKey);
            return jsonSerializer.toJson(accounts);
        } 
        catch (NullPointerException e) {
            //TODO: do something
            throw new AccountLogicFacadeIllegalArgumentException("no such user");
        }
        catch (HTTPHelperConnectionException e) 
        {
            //TODO: log
            throw new AccountLogicFacadeIllegalArgumentException(e.toString());
        } catch (HTTPHelper.HTTPHelperMalformedURLException e) {
            throw new AccountLogicFacadeIllegalArgumentException("malformedurl");
        }
    }

    @Override
    public void credit(long account, long amount) throws AccountLogicFacadeIllegalArgumentException, AccountLogicFacadeStorageException {
        accountEntityFacade.credit(account, amount);
    }

    @Override
    public void debit(long account, long amount) throws AccountLogicFacadeIllegalArgumentException, AccountLogicFacadeStorageException, AccountLogicFacadeInsufficientHoldingsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
