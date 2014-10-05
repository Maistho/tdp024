package se.liu.ida.tdp024.account.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.data.api.exception.AccountInputParameterException;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
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
        logger.log(AccountLogger.TodoLoggerLevel.DEBUG, "Access /", "Requested '/'");
        return Response.ok().entity("Hello World!").build();
    }
  
    @GET
    @Path("/create")
    public Response create(
    @QueryParam("accounttype") String accounttype,
    @QueryParam("name") String name,
    @QueryParam("bank") String bank
    ) {
        logger.log(AccountLogger.TodoLoggerLevel.DEBUG, "Access /create", "Requested '/create'");
        try {
            accountLogicFacade.create(accounttype, name, bank);
            return Response.ok().entity("OK").build();
        } catch (AccountInputParameterException e) {
        logger.log(AccountLogger.TodoLoggerLevel.WARNING, "Failed creating account", e.getMessage());
        return Response.ok().entity("FAILED").build();
        }
       
    }    
    @GET
    @Path("/find/{name : [\\w(%20)]+}")
    public Response find(
            @PathParam("name") String name
    ) {
        logger.log(AccountLogger.TodoLoggerLevel.DEBUG, "Access /find", "Requested /find/" + name);
        String accounts = accountLogicFacade.find(name);
        return Response.ok().entity(accounts).build();
    }
    
    @GET
    @Path("/credit")
    public Response credit(
            @QueryParam("id") long id,
            @QueryParam("amount") long amount
    ) {
        logger.log(AccountLogger.TodoLoggerLevel.DEBUG, "Access /credit", "Requested '/credit'");
        try {
            transactionLogicFacade.credit(id, amount);
            return Response.ok().entity("OK").build();
        } catch (Exception e) {
            //todo: catch more exceptions
            logger.log(AccountLogger.TodoLoggerLevel.DEBUG, "Failed credit", String.format("id: %d\namount: %d\nException message: %s\nStacktrace:\n", id, amount, e.getMessage(), e.getStackTrace().toString()));
            return Response.ok().entity("FAILED").build();
        }
    }
    
    @GET
    @Path("/debit")
    public Response debit( 
            @QueryParam("id") long id,
            @QueryParam("amount") long amount    
    ) {
        logger.log(AccountLogger.TodoLoggerLevel.DEBUG, "Access /debit", "Requested '/debit'");
        try {
            transactionLogicFacade.debit(id, amount);
            return Response.ok().entity("OK").build();
        } catch (Exception e) {
            //TODO: catch more exceptions
            logger.log(AccountLogger.TodoLoggerLevel.DEBUG, "Failed credit", String.format("id: %d\namount: %d\nException message: %s\nStacktrace:\n", id, amount, e.getMessage(), e.getStackTrace().toString()));
            return Response.ok().entity("FAILED").build();
        }
    }
    
    @GET
    @Path("/transactions")
    public Response transactions(
            @QueryParam("id") long id
    ) {
        logger.log(AccountLogger.TodoLoggerLevel.DEBUG, "Access /transactions", "Requested '/transactions'");
        String response = transactionLogicFacade.findAllFromAccount(id);
        return Response.ok().entity(response).build();
    }
    
}
