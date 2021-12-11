package kafka;
import java.util.Properties;

import database.DataBase;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class DataBaseProducer {
    public static void main(String[] args) throws Exception{

        /*
        .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
        .\bin\windows\kafka-server-start.bat .\config\server.properties
        .\bin\windows\connect-standalone.bat .\config\connect-standalone.properties config\connect-jdbc-source-IS3.properties .\config\connect-jdbc-sink-IS3.properties
        .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic DBInfoTopic --from-beginning
        .\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic ResultsTopic
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
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);

        //{"schema":{"type":"struct","fields":[{"type":"string","optional":false,"field":"name"},{"type":"float","optional":false,"field":"value"},{"type":"int32","optional":true,"field":"clientid"}],"optional":false,"name":"utilizadores"},"payload":{"name":"TotalCredits","value":100.0,"clientid":1}}

        producer.send(new ProducerRecord<String,String>("ResultsTopic","ola" , "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"string\",\"optional\":false,\"field\":\"name\"},{\"type\":\"float\",\"optional\":false,\"field\":\"value\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"clientid\"}],\"optional\":false,\"name\":\"utilizadores\"},\"payload\":{\"name\":\"TotalCredits\",\"value\":200.0,\"clientid\":1}}"));
        producer.close();
    }
}