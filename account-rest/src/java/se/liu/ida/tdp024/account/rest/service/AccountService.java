package se.liu.ida.tdp024.account.rest.service;

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

    private final AccountLogicFacade accountLogicFacade
            = new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    private final TransactionLogicFacade transactionLogicFacade
            = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB(), accountLogicFacade);
    private final AccountLogger logger
            = new AccountLoggerMonlog();

    @GET
    @Path("/")
    public Response root() {
        logger.log(AccountLogger.AccountLoggerLevel.DEBUG, "Access /", "Requested '/'");
        return Response.ok().entity("Hello World!").build(); //TODO: change return
    }

    @GET
    @Path("/create")
    public Response create(
            @QueryParam("accounttype") String accounttype,
            @QueryParam("name") String name,
            @QueryParam("bank") String bank
    ) {
        logger.log(AccountLogger.AccountLoggerLevel.DEBUG, "Access /create", "Requested '/create'");
        String reason = "";
        Boolean illegalArgument = false;
        if (accounttype == null || accounttype.equals("")) { // || name == null || bank == null) {
            logger.log(AccountLogger.AccountLoggerLevel.WARNING, "NULL value in /create",
                    String.format("Invalid argument accounttype: null"));
            reason += "AccountType is null, ";
            illegalArgument = true;
        }

        if (name == null || name.equals("")) {
            logger.log(AccountLogger.AccountLoggerLevel.WARNING, "NULL value in /create",
                    String.format("Invalid argument name: null"));
            reason += "Name is null, ";
            illegalArgument = true;
        }

        if (bank == null || bank.equals("")) {
            logger.log(AccountLogger.AccountLoggerLevel.WARNING, "NULL value in /create",
                    String.format("Invalid argument bank: null"));
            reason += "Bank is null";
            illegalArgument = true;
        }
        if (illegalArgument) {
            return Response.status(Response.Status.BAD_REQUEST).entity(reason).build();
        }
        try {
            accountLogicFacade.create(accounttype, name, bank);
            logger.log(AccountLogger.AccountLoggerLevel.INFO, "Created new account",
                    String.format("New Account with:\nAccountType: %s\nName: %s\nBank: %s", accounttype, name, bank));
            return Response.ok().entity("OK").build();

        } catch (AccountLogicFacadeIllegalArgumentException e) {
            logger.log(AccountLogger.AccountLoggerLevel.WARNING, "Failed creating account", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid arguments.").build(); //TODO: change return
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException e) {
            logger.log(AccountLogger.AccountLoggerLevel.CRITICAL, "Storage error",
                    String.format("Message: %s", e.getMessage()));
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("INTERNAL SERVER ERROR").build();
        } catch (AccountLogicFacade.AccountLogicFacadeConnectionException e) {
            logger.log(AccountLogger.AccountLoggerLevel.CRITICAL, "Error connecting to remote server",
                    String.format("Message: %s", e.getMessage()));
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("INTERNAL SERVER ERROR").build();
        }

    }

    @GET
    @Path("/find/{name : [\\w(%20)]+}")
    public Response find(
            @PathParam("name") String name
    ) {
        logger.log(AccountLogger.AccountLoggerLevel.DEBUG, "Access /find", "Requested /find/" + name);

        if (name == null) {
            logger.log(AccountLogger.AccountLoggerLevel.WARNING, "Access /find",
                    String.format("Invalid Argument name: %s", name));
            return Response.ok().entity("FAILED").build(); //TODO: change return
        }

        try {
            String accounts = accountLogicFacade.find(name);
            return Response.ok().entity(accounts).build();
        } catch (AccountLogicFacade.AccountLogicFacadeIllegalArgumentException e) {
            return Response.status(Response.Status.OK).entity("[]").build();
        } catch (AccountLogicFacade.AccountLogicFacadeStorageException e) {
            return Response.ok().entity(e.getMessage()).build(); //TODO: change return
        }

    }

    @GET
    @Path("/credit")
    public Response credit(
            @QueryParam("id") long id,
            @QueryParam("amount") long amount
    ) {
        logger.log(AccountLogger.AccountLoggerLevel.DEBUG, "Access /credit", "Requested '/credit'");

        try {
            transactionLogicFacade.credit(id, amount);
        } catch (TransactionLogicFacade.TransactionLogicFacadeIllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Illegal Argument").build();
        } catch (TransactionLogicFacade.TransactionLogicFacadeStorageException e) {
            logger.log(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("INTERNAL SERVER ERROR").build();
        }
        return Response.ok().entity("OK").build();
    }

    @GET
    @Path("/debit")
    public Response debit(
            @QueryParam("id") long id,
            @QueryParam("amount") long amount
    ) {
        logger.log(AccountLogger.AccountLoggerLevel.DEBUG, "Access /debit", "Requested '/debit'");

        try {
            transactionLogicFacade.debit(id, amount);
        } catch (TransactionLogicFacade.TransactionLogicFacadeIllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Illegal Arguement").build();
        } catch (TransactionLogicFacade.TransactionLogicFacadeStorageException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.ok().entity("OK").build();
    }

    @GET
    @Path("/transactions")
    public Response transactions(
            @QueryParam("id") long id
    ) {
        logger.log(AccountLogger.AccountLoggerLevel.DEBUG, "Access /transactions", "Requested '/transactions'");
        try {
            String response = transactionLogicFacade.findAllFromAccount(id);
            return Response.ok().entity(response).build();
        } catch (TransactionLogicFacade.TransactionLogicFacadeIllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("FAILED").build();

        }
    }

}
