package kafka;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;

import database.DataBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ClientProducer {
    public static void main(String[] args) throws Exception{

        /*
        .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
        .\bin\windows\kafka-server-start.bat .\config\server.properties
        */



        //Topic to where it writes
        String PaymentsTopic = "PaymentsTopic";
        String CreditsTopic = "CreditsTopic";
        String DBInfoTopic = "DBInfoTopic";

        // create instance for properties to access producer configs
        Properties props = new Properties();
        //Assign localhost id
        props.put("bootstrap.servers", "localhost:9092");
        //Set acknowledgements for producer requests.
        props.put("acks", "all");
        //If the request fails, the producer can automatically retry,
        props.put("retries", 0);
        //Specify buffer size in config
        props.put("batch.size", 16384);
        //Reduce the no of requests less than 0
        props.put("linger.ms", 1);
        //The buffer.memory controls the total amount of memory available to the producer for

        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.DoubleSerializer");

        Producer<String, Double> producer = new KafkaProducer<>(props);


        //SO PRA TESTAR
        ArrayList<String> currencies = new ArrayList<>();
        currencies.add("Euro;1.0");
        currencies.add("Metacais;3.72");
        currencies.add("Libra;1.5");


        Random gerador = new Random();
        String credit;
        Double price;
        while(true){

            //Escolhe uma currency Random
            credit = currencies.get(gerador.nextInt(currencies.size()));
            String [] creditfinal = credit.split(";");

            //Gera um double e multiplica pelo exchange rate da currency selecionada
            //Assim o preço já está em euros
            price = (gerador.nextDouble() * Double.parseDouble(creditfinal[1]));
            System.out.println(price +" "+ creditfinal[0]);

            //Escreve nos Payments
            producer.send(new ProducerRecord<>(PaymentsTopic,args[0]+";"+creditfinal[0],price));

            //Escreve nos Credits
            producer.send(new ProducerRecord<>(CreditsTopic,args[0]+";"+creditfinal[0],price));

            Thread.sleep(10000);
        }
    }
}
