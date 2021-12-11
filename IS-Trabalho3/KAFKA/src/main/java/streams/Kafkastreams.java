package streams;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.*;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.kstream.internals.KTableAggregate;

public class Kafkastreams {

    public static void main(String[] args) throws InterruptedException, IOException {

        java.util.Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "Proj");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Double().getClass());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "Streams");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.DoubleDeserializer");

        String ResultsTopic = "resultstopic";
        String PaymentsTopic = "PaymentsTopic";
        String CreditsTopic = "CreditsTopic";
        String ResultsTopic2 = "resultstopic2";

        StreamsBuilder builder = new StreamsBuilder();

        /*********Ponto 7************/

        KStream<String, Double> Credits = builder.stream(CreditsTopic);
        KTable<String, Double> outlines2 = Credits.groupByKey().reduce((oldval, newval) -> oldval + newval);
        outlines2.mapValues((k,v) -> "{\"schema\":{\"type\":\"struct\",\"fields\":" +
                "[{\"type\":\"float\",\"optional\":false,\"field\":\"totalcredits\"}," +
                "{\"type\":\"int32\",\"optional\":false,\"field\":\"id\"}]," +
                "\"optional\":false}," +
                "\"payload\":{\"totalcredits\":"+v+",\"id\":"+Integer.parseInt(k)+"}}").toStream().to(ResultsTopic, Produced.with(Serdes.String(), Serdes.String()));

        /******************************/

        /**********Ponto 8 ************/

        KStream<String, Double> Payments = builder.stream(PaymentsTopic);
        KTable<String, Double> outlines = Payments.groupByKey().reduce((oldval, newval) -> oldval + newval);

        outlines.mapValues((k,v) -> "{\"schema\":{\"type\":\"struct\",\"fields\":" +
                        "[{\"type\":\"int32\",\"optional\":false,\"field\":\"id\"}," +
                        "{\"type\":\"int32\",\"optional\":false,\"field\":\"last2months\"}" +
                        "{\"type\":\"double\",\"optional\":false,\"field\":\"totalpayments\"}],\"optional\":false}," +
                        "\"payload\":{\"id\":"+Integer.parseInt(k)+",\"totalpayments\":"+v+",\"last2months\":0}}").
                        toStream().to(ResultsTopic, Produced.with(Serdes.String(), Serdes.String()));

        /********************************/

        /*************Ponto 9 *************/

        KTable<String, Double> balance = outlines.join(outlines2, (l , r) -> (r-l));
        balance.mapValues((k,v) -> "{\"schema\":{\"type\":\"struct\",\"fields\":" +
                "[{\"type\":\"int32\",\"optional\":false,\"field\":\"id\"}," +
                "{\"type\":\"double\",\"optional\":false,\"field\":\"totalbalance\"}],\"optional\":false}," +
                "\"payload\":{\"id\":"+Integer.parseInt(k)+",\"totalbalance\":"+v+"}}").
                toStream().to(ResultsTopic, Produced.with(Serdes.String(), Serdes.String()));

        /**********************************/

        /***********Ponto 10**************/

        KStream<String, Double> Credits2 = builder.stream(CreditsTopic);
        KTable<String, Double> outlines3 = Credits2.map((key, value) -> new KeyValue<>("1",value)).groupByKey().reduce((oldval, newval) -> oldval + newval);
        outlines3.mapValues((k,v) -> "{\"schema\":{\"type\":\"struct\",\"fields\":" +
                "[{\"type\":\"float\",\"optional\":true,\"field\":\"totalcredits\"}," +
                "{\"type\":\"float\",\"optional\":true,\"field\":\"totalpayments\"}," +
                "{\"type\":\"float\",\"optional\":true,\"field\":\"totalbalance\"}," +
                "{\"type\":\"float\",\"optional\":true,\"field\":\"biggestdebt\"}," +
                "{\"type\":\"int32\",\"optional\":false,\"field\":\"id\"}]," +
                "\"optional\":true,\"name\":\"resultstopic2\"}," +
                "\"payload\":{\"totalcredits\":"+v+",\"id\":"+Integer.parseInt(k)+"}}").toStream().to(ResultsTopic2, Produced.with(Serdes.String(), Serdes.String()));

        /**********************************/

        /***********Ponto 11**************/

        KStream<String, Double> Payments2 = builder.stream(PaymentsTopic);
        KTable<String, Double> outlines4 = Payments2.map((key, value) -> new KeyValue<>("1",value)).groupByKey().reduce((oldval, newval) -> oldval + newval);
        outlines4.mapValues((k,v) -> "{\"schema\":{\"type\":\"struct\",\"fields\":" +
                "[{\"type\":\"float\",\"optional\":true,\"field\":\"totalcredits\"}," +
                "{\"type\":\"float\",\"optional\":true,\"field\":\"totalpayments\"}," +
                "{\"type\":\"float\",\"optional\":true,\"field\":\"totalbalance\"}," +
                "{\"type\":\"float\",\"optional\":true,\"field\":\"biggestdebt\"}," +
                "{\"type\":\"int32\",\"optional\":false,\"field\":\"id\"}]," +
                "\"optional\":true,\"name\":\"resultstopic2\"}," +
                "\"payload\":{\"totalpayments\":"+v+",\"id\":"+Integer.parseInt(k)+"}}").toStream().to(ResultsTopic2, Produced.with(Serdes.String(), Serdes.String()));

        /**********************************/

        /*************Ponto 12 *************/

        KTable<String, Double> balanceTotal = outlines4.join(outlines3, (l , r) -> (r-l));
        balanceTotal.mapValues((k,v) -> "{\"schema\":{\"type\":\"struct\",\"fields\":" +
                "[{\"type\":\"float\",\"optional\":true,\"field\":\"totalcredits\"}," +
                "{\"type\":\"float\",\"optional\":true,\"field\":\"totalpayments\"}," +
                "{\"type\":\"float\",\"optional\":true,\"field\":\"totalbalance\"}," +
                "{\"type\":\"float\",\"optional\":true,\"field\":\"biggestdebt\"}," +
                "{\"type\":\"int32\",\"optional\":false,\"field\":\"id\"}]," +
                "\"optional\":true,\"name\":\"resultstopic2\"}," +
                "\"payload\":{\"totalbalance\":"+v+",\"id\":"+Integer.parseInt(k)+"}}").toStream().to(ResultsTopic2, Produced.with(Serdes.String(), Serdes.String()));

        /**********************************/

        /***********Ponto 13**************/
        KTable<Windowed<String>, Double> outlines5 = Payments.groupByKey()
                .windowedBy(TimeWindows.of(TimeUnit.DAYS.toMillis(31)))
                .reduce((oldval, newval) -> oldval + newval);

        outlines5.toStream((k,v)-> k.key()).
                        mapValues((k,v) ->
                        "{\"schema\":{\"type\":\"struct\",\"fields\":" +
                        "[{\"type\":\"float\",\"optional\":true,\"field\":\"totalcredits\"}," +
                        "{\"type\":\"float\",\"optional\":true,\"field\":\"totalpayments\"}," +
                        "{\"type\":\"float\",\"optional\":true,\"field\":\"totalbalance\"}," +
                        "{\"type\":\"float\",\"optional\":true,\"field\":\"billlastmonth\"}," +
                        "{\"type\":\"int32\",\"optional\":true,\"field\":\"last2months\"}," +
                        "{\"type\":\"int32\",\"optional\":false,\"field\":\"id\"}]," +
                        "\"optional\":true,\"name\":\"resultstopic\"}," +
                        "\"payload\":{\"billlastmonth\":"+v+",\"id\":"+Integer.parseInt(k)+"}}").to(ResultsTopic, Produced.with(Serdes.String(), Serdes.String()));

        /**********************************/

        /***********Ponto 14**************/

        KTable<Windowed<String>, Double> outlines6 = Payments.groupByKey()
                .windowedBy(TimeWindows.of(TimeUnit.MINUTES.toMillis(1)))
                .reduce((oldval, newval) -> oldval + newval);

        outlines6
                .toStream((k,v)-> k.key())
                .mapValues((k,v) -> "{\"schema\":{\"type\":\"struct\",\"fields\":" +
                "[{\"type\":\"float\",\"optional\":true,\"field\":\"totalcredits\"}," +
                "{\"type\":\"float\",\"optional\":true,\"field\":\"totalpayments\"}," +
                "{\"type\":\"float\",\"optional\":true,\"field\":\"totalbalance\"}," +
                "{\"type\":\"float\",\"optional\":true,\"field\":\"billlastmonth\"}," +
                "{\"type\":\"int32\",\"optional\":true,\"field\":\"last2months\"}," +
                "{\"type\":\"int32\",\"optional\":false,\"field\":\"id\"}]," +
                "\"optional\":true,\"name\":\"resultstopic\"}," +
                "\"payload\":{\"last2months\":1,\"id\":"+Integer.parseInt(k)+"}}").to(ResultsTopic, Produced.with(Serdes.String(), Serdes.String()));


        /**********************************/

        /*************Ponto 15 *************/
        /*KTable<String, Double> balance2 = outlines.
                join(outlines2, (l , r) -> (r-l)).


                ;
        balance2.mapValues((k,v) -> "{\"schema\":{\"type\":\"struct\",\"fields\":" +
                "[{\"type\":\"float\",\"optional\":true,\"field\":\"totalcredits\"}," +
                "{\"type\":\"float\",\"optional\":true,\"field\":\"totalpayments\"}," +
                "{\"type\":\"float\",\"optional\":true,\"field\":\"totalbalance\"}," +
                "{\"type\":\"float\",\"optional\":true,\"field\":\"biggestdebt\"}," +
                "{\"type\":\"int32\",\"optional\":false,\"field\":\"id\"}]," +
                "\"optional\":true,\"name\":\"resultstopic2\"}," +
                "\"payload\":{\"biggestdebt\":"+v+",\"id\":"+Integer.parseInt(k)+"}}").toStream().to(ResultsTopic2, Produced.with(Serdes.String(), Serdes.String()));
*/
        /**********************************/

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
        System.out.println("Kafka Streams Ready");
    }
}