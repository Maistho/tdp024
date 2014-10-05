package se.liu.ida.tdp024.account.rest.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade.AccountLogicFacadeIllegalArgumentException;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerMonlog;

@Path("account")
public class AccountService {
    
    private final AccountLogicFacade accountLogicFacade = 
            new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    private final TransactionLogicFacade transactionLogicFacade = 
            new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());
    private final AccountLogger logger = 
            new AccountLoggerMonlog();

    @GET
    @Path("/")
    public Response root( )
    {
        logger.log(AccountLogger.AccountLoggerLevel.DEBUG, "Access /", "Requested '/'");
        return Response.ok().entity("Hello World!").build(); //TODO: change return
    }
  
    @GET
    @Path("/create")
    public Response create(
            @QueryParam("accounttype") String accounttype,
            @QueryParam("name") String name,
            @QueryParam("bank") String bank
    ) 
    {
        logger.log(AccountLogger.AccountLoggerLevel.DEBUG, "Access /create", "Requested '/create'");
        System.err.println("output" + accounttype);
        
        if (accounttype == null) { // || name == null || bank == null) {
            logger.log(AccountLogger.AccountLoggerLevel.WARNING, "Access /create",
                    String.format("Invalid argument accounttype: %s", accounttype));
            return Response.ok().entity("FAILED").build(); //TODO: change return
        }
        
        if (name == null) {
            logger.log(AccountLogger.AccountLoggerLevel.WARNING, "Access /create",
                    String.format("Invalid argument name: %s", name));
            return Response.ok().entity("FAILED").build(); //TODO: change return
        }
        
        if (bank == null) {
            logger.log(AccountLogger.AccountLoggerLevel.WARNING, "Access /create",
                    String.format("Invalid argument bank: %s", bank));
            return Response.ok().entity("FAILED").build(); //TODO: change return 
        }
        
        try 
        {
            accountLogicFacade.create(accounttype, name, bank);
            //TODO: Log success
            return Response.ok().entity("OK").build(); //TODO: change return?
        }
        catch (AccountLogicFacadeIllegalArgumentException e) 
        {
            logger.log(AccountLogger.AccountLoggerLevel.WARNING, "Failed creating account", e.getMessage());
            return Response.ok().entity("FAILED").build(); //TODO: change return
        }
    }   
    
    @GET
    @Path("/find/{name : [\\w(%20)]+}")
    public Response find(
            @PathParam("name") String name
    ) 
    {
        logger.log(AccountLogger.AccountLoggerLevel.DEBUG, "Access /find", "Requested /find/" + name);
                
        if (name == null) {
            logger.log(AccountLogger.AccountLoggerLevel.WARNING, "Access /find",
                    String.format("Invalid Argument name: %s", name));
            return Response.ok().entity("FAILED").build(); //TODO: change return
        }

        try 
        {
            String accounts = accountLogicFacade.find(name);
            return Response.ok().entity(accounts).build();
        } 
        catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException ex) 
        {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.ok().entity("FAILED").build(); //TODO: change return
        }
        
    }
    
    @GET
    @Path("/credit")
    public Response credit(
            @QueryParam("id") long id,
            @QueryParam("amount") long amount
    )
    {
        logger.log(AccountLogger.AccountLoggerLevel.DEBUG, "Access /credit", "Requested '/credit'");
        
        System.out.println(id);
        
        try {
            transactionLogicFacade.credit(id, amount);
            return Response.ok().entity("OK").build();
        } catch (Exception e) {
            //todo: catch more exceptions
            logger.log(AccountLogger.AccountLoggerLevel.DEBUG, "Failed credit", String.format("id: %d\namount: %d\nException message: %s\nStacktrace:\n", id, amount, e.getMessage(), e.getStackTrace().toString()));
            return Response.ok().entity("FAILED").build(); //TODO: change returne
        }
    }
    
    @GET
    @Path("/debit")
    public Response debit( 
            @QueryParam("id") long id,
            @QueryParam("amount") long amount    
    ) 
    {
        logger.log(AccountLogger.AccountLoggerLevel.DEBUG, "Access /debit", "Requested '/debit'");
        try {
            transactionLogicFacade.debit(id, amount);
            return Response.ok().entity("OK").build();
        } catch (Exception e) {
            //TODO: catch more exceptions
            logger.log(AccountLogger.AccountLoggerLevel.DEBUG, "Failed credit", String.format("id: %d\namount: %d\nException message: %s\nStacktrace:\n", id, amount, e.getMessage(), e.getStackTrace().toString()));
            return Response.ok().entity("FAILED").build();
        }
    }
    
    @GET
    @Path("/transactions")
    public Response transactions(
            @QueryParam("id") long id
    ) 
    {
        logger.log(AccountLogger.AccountLoggerLevel.DEBUG, "Access /transactions", "Requested '/transactions'");
        String response = transactionLogicFacade.findAllFromAccount(id);
        return Response.ok().entity(response).build();
    }
    
}
