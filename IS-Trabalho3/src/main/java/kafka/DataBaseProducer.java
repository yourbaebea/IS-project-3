package kafka;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import database.DataBase;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
public class DataBaseProducer {
    public static void main(String[] args) throws Exception{

        /*
        .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
        .\bin\windows\kafka-server-start.bat .\config\server.properties
        */
        DataBase bd = new DataBase();
        bd.connect();
        bd.lerFicheiro("tabelas.sql"); // Cria as tabelas da BD
        bd.lerFicheiro("tabelasDados.sql"); // Cria as tabelas da BD
        //Topic to where it writes
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
        props.put("value.serializer", "org.apache.kafka.common.serialization.LongSerializer");
        Producer<String, Long> producer = new KafkaProducer<>(props);

        //LER DA BD, receber a lista dos clientes e managers
        ArrayList<String> users = bd.getUsers();
        for (String s : users) {
            String[] user = s.split(";");
            //Envia para o tópico o id do user e o seu nome
            producer.send(new ProducerRecord<>(DBInfoTopic, user[1], Long.parseLong(user[0])));
            System.out.println("Enviei "+user[1]+" "+user[0]+"\n");
            if (user[2].equals("0")) { //É um cliente
                //Criar a thread cliente que faz o Payments e Credits
            }
        }
        //Wait for threads

        producer.close();
    }
}