package streams;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

public class Kafkastreams {
    public static void main(String[] args) throws InterruptedException, IOException {

        String DBInfoTopic = "DBInfoTopic";
        String ResultsTopic = "ResultsTopic";
        String CreditsTopic = "CreditsTopic";
        String PaymentsTopic = "PaymentsTopic";

        java.util.Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "Proj");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Long> DBinfo = builder.stream(DBInfoTopic);
        KStream<String, Long> Results = builder.stream(ResultsTopic);
        KStream<String, Long> Payments = builder.stream(PaymentsTopic);
        KStream<String, Long> Credits = builder.stream(CreditsTopic);

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
        System.out.println("Kafka Streams Ready");
    }

    //Credit per client
    public void creditPerClient(){

    }

    //Total credits
    public void totalCredits(){

    }

    //Balance of the client
    public void balanceOfClient(){

    }

    //Payments per client
    public void paymentsPerClient(){

    }

    //Total payments
    public void totalPayments(){

    }

    //Total balance
    public void totalBalance(){

    }

    //Bill of each client of the last month
    public void billLastMonth(){

    }

    //Clients without payments in the last 2 months
    public void withoutPaymentsTwoMonths(){

    }

    //Client with the bigger debt
    public void biggerDebt(){

    }

    //Manager with the highest revenue
    public void managerHighestRevenue(){

    }
}