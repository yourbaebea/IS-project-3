package app;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import tables.Currency;
import tables.Utilizador;
import database.DataBase;

@Path("/project")
@Produces(MediaType.APPLICATION_JSON)

public class MyService {
    public static DataBase db = new DataBase();

    @POST
    @Path("/new/manager")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response method1(@QueryParam("name") String name) throws SQLException {
        db.connect();
        db.AdminInsertManager(name);
        String str = "Client added : " + name;
        return Response.status(Status.OK).entity(str).build();
    }

    @POST
    @Path("/new/client")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response method2(@QueryParam("name") String name, @QueryParam("managerid") int managerid) throws SQLException {
        db.connect();
        db.AdminInsertUser(name,managerid);
        String str = "Client added : " + name;
        System.out.println(str);
        return Response.status(Status.OK).entity(str).build();
    }

    @POST
    @Path("/new/currency")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response method3(@QueryParam("currency") String currency, @QueryParam("exchange") int exchange) throws SQLException {
        db.connect();
        db.AdminInsertCurrency(currency, exchange);
        String str = "Currency added : " + currency;
        return Response.status(Status.OK).entity(str).build();
    }

    @GET
    @Path("/list/manager")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response method4() throws SQLException {
        db.connect();
        ArrayList<HashMap<String, String>> list = db.AdminGetManagers();
        return Response.ok().entity(list).build();
    }

    @GET
    @Path("/list/client")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response method5() throws SQLException {
        db.connect();
        ArrayList<HashMap<String, String>> list = db.AdminGetClients();
        return Response.ok().entity(list).build();
    }

    @GET
    @Path("/list/currency")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response method6() throws SQLException {
        db.connect();
        ArrayList<HashMap<String, String>> list = db.AdminGetCurrencies();
        return Response.ok().entity(list).build();
    }

    @GET
    @Path("/credit")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response method7() throws SQLException {
        db.connect();
        List<String> value = db.getUserCredit();
        return Response.status(Status.OK).entity(value).build();
    }

    @GET
    @Path("/payments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response method8() throws SQLException {
        db.connect();
        List<String> list = db.getUserPayments();
        return Response.status(Status.OK).entity(list).build();
    }

    @GET
    @Path("/balance")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response method9() throws SQLException {
        db.connect();
        List<String> value = db.getUserBalance();
        return Response.status(Status.OK).entity(value).build();
    }

    @GET
    @Path("/total/balance")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response method10() throws SQLException {
        db.connect();
        String value = db.getTotalUsersBalance();
        return Response.status(Status.OK).entity(value).build();
    }

    @GET
    @Path("/total/credit")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response method15() throws SQLException {
        db.connect();
        String value = db.getTotalUsersCredits();
        return Response.status(Status.OK).entity(value).build();
    }

    @GET
    @Path("/total/payment")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response method16() throws SQLException {
        db.connect();
        String value = db.getTotalUsersPayments();
        return Response.status(Status.OK).entity(value).build();
    }

    @GET
    @Path("/list/lastmonth")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response method11() throws SQLException {
        db.connect();
        List<String> list = db.getUserLastMonth();
        return Response.status(Status.OK).entity(list).build();
    }

    @GET
    @Path("/list/withoutpayments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response method12() throws SQLException {
        db.connect();
        List<String> list = db.getWithoutPayments();
        return Response.status(Status.OK).entity(list).build();
    }

    /*@GET
    @Path("/outstandingdebt")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response method13() throws SQLException {
        db.connect();
        Utilizador p = db.getBiggestDebt();
        return Response.status(Status.OK).entity(p).build();
    }


    @GET
    @Path("/highestrevenue")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response method14() throws SQLException {
        db.connect();
        Utilizador p = db.getHighestRevenue();
        return Response.status(Status.OK).entity(p).build();
    }*/
}

