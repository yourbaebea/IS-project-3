package streams;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

public class Kafkastreams {

    private KStream<String, Long> Credits;
    private KStream<String, Long> Results;

    public static void main(String[] args) throws InterruptedException, IOException {


        java.util.Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "Proj");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Double().getClass());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "Streams");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.DoubleDeserializer");

        String ResultsTopic = "ResultsTopic";
        String PaymentsTopic = "PaymentsTopic";

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Double> Payments = builder.stream(PaymentsTopic);
        KTable<String, Double> outlines = Payments.groupByKey().reduce((oldval, newval) -> oldval + newval);

        outlines.mapValues((k,v) -> "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"string\",\"optional\":false,\"field\":\"name\"},{\"type\":\"float\",\"optional\":false,\"field\":\"value\"},{\"type\":\"int32\",\"optional\":true,\"field\":\"clientid\"}],\"optional\":false,\"name\":\"utilizadores\"},\"payload\":{\"name\":\"CreditsperClient\",\"value\":"+v+"}}").toStream().to(ResultsTopic, Produced.with(Serdes.String(), Serdes.String()));

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
        System.out.println("Kafka Streams Ready");

    }

    //Credit per client
    public void creditPerClient(){
        String ResultsTopic = "ResultsTopic";
        String CreditsTopic = "CreditsTopic";

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Long> Credits = builder.stream(CreditsTopic);
        KTable<String, Long> outlines = Credits.groupByKey().reduce((oldval, newval) -> oldval + newval);

        outlines.mapValues((k, v) -> k + " => " + v).toStream().to(ResultsTopic,
                Produced.with(Serdes.String(), Serdes.String()));
    }

    //Total credits
    public void totalCredits(){
        String ResultsTopic = "ResultsTopic";
        String CreditsTopic = "CreditsTopic";

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Long> Credits = builder.stream(CreditsTopic);
        KTable<String, Long> outlines = Credits.groupByKey().reduce((oldval, newval) -> oldval + newval);

        outlines.mapValues((k, v) -> k + " => " + v).toStream().to(ResultsTopic,
                Produced.with(Serdes.String(), Serdes.String()));

    }

    //Balance of the client
    public void balanceOfClient(){

    }

    //Payments per client
    public void paymentsPerClient(){
        String ResultsTopic = "ResultsTopic";
        String PaymentsTopic = "PaymentsTopic";

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Double> Credits = builder.stream(PaymentsTopic);
        KTable<String, Double> outlines = Credits.groupByKey().reduce((oldval, newval) -> oldval + newval);

        outlines.mapValues((k, v) -> k + " => " + v).toStream().to(ResultsTopic, Produced.with(Serdes.String(), Serdes.String()));

    }

    //Total payments
    public void totalPayments(){
        String ResultsTopic = "ResultsTopic";
        String PaymentsTopic = "PaymentsTopic";

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Long> Credits = builder.stream(PaymentsTopic);
        KTable<String, Long> outlines = Credits.groupByKey().reduce((oldval, newval) -> oldval + newval);

        outlines.mapValues((k, v) -> k + " => " + v).toStream().to(ResultsTopic, Produced.with(Serdes.String(), Serdes.String()));

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