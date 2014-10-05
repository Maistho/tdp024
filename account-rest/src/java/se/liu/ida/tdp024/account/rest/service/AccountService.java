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

@Path("account")
public class AccountService {
    
    private final AccountLogicFacade accountLogicFacade = 
            new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    private final TransactionLogicFacade transactionLogicFacade = 
            new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());

    @GET
    @Path("/")
    public Response root( )
    {
        return Response.ok().entity("Hello World!").build();
    }
  
    @GET
    @Path("/create")
    public Response create(
    @QueryParam("accounttype") String accounttype,
    @QueryParam("name") String name,
    @QueryParam("bank") String bank
    ) {
        try {
             accountLogicFacade.create(accounttype, name, bank);
        return Response.ok().entity("OK").build();
        } catch (AccountInputParameterException e) {
        return Response.ok().entity("FAILED").build();
        }
       
    }    
    @GET
    @Path("/find/{name : [\\w(%20)]+}")
    public Response find(
            @PathParam("name") String name
    ) {
        String accounts = accountLogicFacade.find(name);
        return Response.ok().entity(accounts).build();
    }
    
    @GET
    @Path("/credit")
    public Response credit(
            @QueryParam("id") long id,
            @QueryParam("amount") long amount
    ) {
        try {
            transactionLogicFacade.credit(id, amount);
            return Response.ok().entity("OK").build();
        } catch (Exception e) {
            return Response.ok().entity("FAILED").build();
        }
    }
    
    @GET
    @Path("/debit")
    public Response debit( 
            @QueryParam("id") long id,
            @QueryParam("amount") long amount    
    ) {
        try {
            transactionLogicFacade.debit(id, amount);
            return Response.ok().entity("OK").build();
        } catch (Exception e) {
            return Response.ok().entity("FAILED").build();
        }
    }
    
    @GET
    @Path("/transactions")
    public Response transactions(
            @QueryParam("id") long id
    ) {
            String response = transactionLogicFacade.findAllFromAccount(id);
            return Response.ok().entity(response).build();
    }
    
}
