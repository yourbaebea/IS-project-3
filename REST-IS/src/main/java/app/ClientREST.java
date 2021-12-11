package app;
import tables.ResultsTopic;
import tables.Utilizador;

import tables.Currency;
import java.util.List;
import java.util.Scanner;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ClientREST {
    public static String address = "http://localhost:8080/project";
    static Client client;
    static WebTarget target;
    static Response response;
    static List<Currency> value; //just for debug
    public static final Scanner sc = new Scanner(System.in);

    public ClientREST(){}

    public static void main(String[] args) {
        connect();
        ClientREST admin = new ClientREST();
        admin.menu(admin);
    }

    public void menu(ClientREST admin){
        int option = 1;
        while(option!=0) {
            System.out.println("++++++++++++++ ADMIN CLI MENU +++++++++++++\n" +
                    "++                                       ++\n" +
                    "++            1 - List Clients           ++\n" +
                    "++            2 - List Managers          ++\n" +
                    "++            3 - List Currencies        ++\n" +
                    "++            4 - Insert Client          ++\n" +
                    "++            5 - Insert Manager         ++\n" +
                    "++            6 - Insert Currency        ++\n" +
                    "++            7 - List Credits p/client  ++\n" +
                    "++            8 - List Payments p/client ++\n" +
                    "++            9 - List Balance p/client  ++\n" +
                    "++            10 - List Total Credits    ++\n" +
                    "++            11 - List Total Payments   ++\n" +
                    "++            12 - List Total Balance    ++\n" +
                    "++            13 - List Bill Last Month  ++\n" +
                    "++            14 - List Last 2 Months    ++\n" +
                    "++            15 - List Biggest Debt     ++\n" +
                    "++            16 - List Highest Revenue  ++\n" +
                    "+++++++++++++++++++++++++++++++++++++++++++\n");

            option = Integer.parseInt(sc.nextLine());

            switch (option) {

                case 1:
                    admin.listClient();
                    break;
                case 2:
                    admin.listManager();
                    break;
                case 3:
                    admin.listCurrency();
                    break;
                case 4:
                    admin.addClient(new Utilizador("Hugo",0,1));
                    break;
                case 5:
                    admin.addManager(new Utilizador("Hugo",1,0));
                    break;
                case 6:
                    admin.addCurrency(new Currency("Metacais",7.2));
                    break;
                case 7:
                    admin.Credit();
                    break;
                case 8:
                    admin.Payments();
                    break;
                case 9:
                    admin.currentBalance();
                    break;
                case 10:
                    admin.totalCredit();
                    break;
                case 11:
                    admin.totalPayment();
                    break;
                case 12:
                    admin.totalBalance();
                    break;
                case 13:
                    admin.listLastMonth();
                    break;
                case 14:
                    admin.listWithoutPayments();
                    break;
                case 15:
                    admin.biggestDebt();
                    break;
                case 16:
                    admin.highestRevenue();
                    break;

            }
        }
    }


    public static void connect(){
        client = ClientBuilder.newClient();
    }


    public void addManager(Utilizador p){
        target = client.target(address + "/new/manager");
        Entity<Utilizador> input = Entity.entity(p, MediaType.APPLICATION_JSON);
        response = target.request().post(input);
        String value = response.readEntity(String.class);
        System.out.println("just for debug " + value);
        response.close();
    }


    public void addClient(Utilizador p){
        target = client.target(address + "/new/client");
        Entity<Utilizador> input = Entity.entity(p, MediaType.APPLICATION_JSON);
        response = target.request().post(input);
        String value = response.readEntity(String.class);
        System.out.println("just for debug " + value);
        response.close();
    }

    public void addCurrency(Currency c){
        target = client.target(address + "/new/currency");
        Entity<Currency> input = Entity.entity(c, MediaType.APPLICATION_JSON);
        response = target.request().post(input);
        String value = response.readEntity(String.class);
        System.out.println("just for debug " + value);
        response.close();
    }


    public void listManager(){
        target = client.target(address + "/list/manager");
        response = target.request().get();
        List <Utilizador>value = response.readEntity(new GenericType<List<Utilizador>>(){});
        System.out.println("just for debug " + value);
        System.out.println("just for debug " + value);
        response.close();
    }


    public void listClient(){
        target = client.target(address + "/list/client");
        response = target.request().get();
        List<Utilizador> value = response.readEntity(new GenericType<List<Utilizador>>(){});
        System.out.println("just for debug " + value);
        System.out.println("just for debug " + value);
        response.close();
    }

    public void listCurrency(){
        target = client.target(address + "/list/currency");
        response = target.request().get();
        value = response.readEntity(new GenericType<List<Currency>>(){});
        System.out.println("just for debug " + value);
        response.close();
    }


    public void Credit(){
        target = client.target(address + "/credit");
        response = target.request().get();
        String value2 = response.readEntity(String.class);
        System.out.println("just for debug " + value2);
        response.close();
    }

    public void Payments(){
        target = client.target(address + "/payments");
        response = target.request().get();
        List<String> value = response.readEntity(new GenericType<List<String>>(){});
        System.out.println(value);
        response.close();
    }

    public void currentBalance(){
        target = client.target(address + "/balance");
        response = target.request().get();
        String value2 = response.readEntity(String.class);
        System.out.println("just for debug " + value2);
        response.close();
    }

    public void totalCredit(){
        target = client.target(address + "/total/credit");
        response = target.request().get();
        String value = response.readEntity(String.class);
        System.out.println("just for debug " + value);
        response.close();
    }

    public void totalPayment(){
        target = client.target(address + "/total/payment");
        response = target.request().get();
        String value = response.readEntity(String.class);
        System.out.println("just for debug " + value);
        response.close();
    }

    public void totalBalance(){
        target = client.target(address + "/total/balance");
        response = target.request().get();
        String value = response.readEntity(String.class);
        System.out.println("just for debug " + value);
        response.close();
    }

    public void listLastMonth(){
        target = client.target(address + "/list/lastmonth");
        response = target.request().get();
        List <String> value = response.readEntity(new GenericType<List<String>>(){});
        System.out.println("just for debug " + value);
        response.close();
    }

    public void listWithoutPayments(){
        target = client.target(address + "/list/withoutpayments");
        response = target.request().get();
        List<String> value = response.readEntity(new GenericType<List<String>>(){});
        System.out.println("just for debug " + value);
        response.close();
    }

    public void biggestDebt(){
        target = client.target(address + "/outstandingdebt");
        response = target.request().get();
        List<String> value = response.readEntity(new GenericType<List<String>>(){});
        System.out.println("just for debug " + value);
        response.close();
    }

    public void highestRevenue(){
        target = client.target(address + "/highestrevenue");
        response = target.request().get();
        // value = response.readEntity(new GenericType<List<Utilizador>>(){});
        //System.out.println("just for debug " + value);
        response.close();
    }





}