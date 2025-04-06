package org.digitalcrafting.transactionenrichment;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.test.TestRecord;
import org.junit.jupiter.api.*;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionEnrichmentAppTest {

    private TopologyTestDriver testDriver;
    private TestInputTopic<String, String> transactionsInput;
    private TestInputTopic<String, String> accountsInput;
    private TestOutputTopic<String, String> outputTopic;

    @BeforeEach
    void setup() {
        StreamsBuilder builder = new StreamsBuilder();

        // Build the same topology as your main app
        KStream<String, String> transactions = builder.stream("transactions");
        KTable<String, String> accounts = builder.table("accounts");
        KStream<String, String> enriched = transactions.join(
                accounts,
                (txn, acc) -> "{\"transaction\": \"" + txn + "\", \"account\": \"" + acc + "\"}"
        );
        enriched.to("enriched-transactions");

        // Create properties
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        testDriver = new TopologyTestDriver(builder.build(), props);

        transactionsInput = testDriver.createInputTopic("transactions", Serdes.String().serializer(), Serdes.String().serializer());
        accountsInput = testDriver.createInputTopic("accounts", Serdes.String().serializer(), Serdes.String().serializer());
        outputTopic = testDriver.createOutputTopic("enriched-transactions", Serdes.String().deserializer(), Serdes.String().deserializer());
    }

    @Test
    void testEnrichment() {
        accountsInput.pipeInput("123", "Alice");
        transactionsInput.pipeInput("123", "txn-456");

        TestRecord<String, String> result = outputTopic.readRecord();
        assertEquals("123", result.key());
        assertTrue(result.value().contains("txn-456"));
        assertTrue(result.value().contains("Alice"));
    }

    @AfterEach
    void tearDown() {
        testDriver.close();
    }
}
