package com.authlete.loyalty;

import com.authlete.loyalty.entity.Account;
import com.authlete.loyalty.entity.AccountDAO;
import com.authlete.loyalty.entity.Customer;
import com.authlete.loyalty.entity.CustomerDAO;
import com.authlete.loyalty.entity.Transaction;
import com.authlete.loyalty.entity.TransactionDAO;
import com.authlete.simpleauth.LoginUtils;
import com.authlete.simpleauth.UserAccount;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.Instant;
import java.util.Set;

@Path("/api")
public class LoyaltyResource {
    @GET
    @Path("/currentCustomer")
    @Produces({MediaType.APPLICATION_JSON})
    public Customer getCurrentCustomer(@Context HttpServletRequest request) {
        CustomerDAO dao = new CustomerDAO();
        UserAccount user = LoginUtils.getAuthenticatedUser(request.getSession());
        return (user != null) ? dao.getByUsername(user.getUsername()) : null;
    }

    // Shouldn't be called by a customer!
    @GET
    @Path("/customers")
    @Produces({MediaType.APPLICATION_JSON})
    public Set<Customer> getAllCustomers() {
        CustomerDAO dao = new CustomerDAO();
        return dao.getAll();
    }

    @GET
    @Path("/customers/{customerId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Customer getCustomer(@PathParam("customerId") String customerId) {
        CustomerDAO dao = new CustomerDAO();
        return dao.get(customerId);
    }

    @GET
    @Path("/accounts")
    @Produces({MediaType.APPLICATION_JSON})
    public Set<Account> getAllAccounts() {
        AccountDAO dao = new AccountDAO();
        return dao.getAll();
    }

    @GET
    @Path("/accounts/{accountNumber}")
    @Produces({MediaType.APPLICATION_JSON})
    public Account getAccount(@PathParam("accountNumber") String accountNumber) {
        AccountDAO dao = new AccountDAO();
        return dao.get(accountNumber);
    }

    @POST
    @Path("/accounts/{accountNumber}/transactions")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createTransaction(@PathParam("accountNumber") String accountNumber, Transaction transaction, @Context UriInfo uriInfo) {
        transaction.setTimestamp(Instant.now().toString());

        TransactionDAO dao = new TransactionDAO();
        dao.create(accountNumber, transaction);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(transaction.getId())).build();
        return Response.status(Response.Status.CREATED).location(uri).build();
    }

    @GET
    @Path("/accounts/{accountNumber}/transactions/{transactionId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Transaction getTransactionFromAccount(
        @PathParam("accountNumber") String accountNumber,
        @PathParam("transactionId") String transactionId
    ) {
        // Get transaction via account rather than directly,
        // to validate that it is on the correct account
        AccountDAO dao = new AccountDAO();
        return dao.getTransactionFromAccount(accountNumber, transactionId);
    }
}