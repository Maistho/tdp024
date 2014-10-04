package se.liu.ida.tdp024.account.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;

@Path("account")
public class AccountService {

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
        return Response.ok().entity("accounttype is "
        + accounttype
        + "\r\n name is"
        + name
        + "\r\n bank is"
        + bank).build();
    }    
    @GET
    @Path("/find/{name : [\\w(%20)]+}")
    public Response find( @PathParam("name") String name)
    {
        return Response.ok().entity("Hello " + name).build();
    }    
    @GET
    @Path("/credit")
    public Response credit( )
    {
        return Response.ok().entity("Hello World!").build();
    }
    
    @GET
    @Path("/debit")
    public Response debit( )
    {
        return Response.ok().entity("Hello World!").build();
    }
    
    @GET
    @Path("/transactions")
    public Response transactions( )
    {
        return Response.ok().entity("Hello World!").build();
    }    
    
}
