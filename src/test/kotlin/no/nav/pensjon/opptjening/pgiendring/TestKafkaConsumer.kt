package no.nav.pensjon.opptjening.pgiendring

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch


@Component
class TestKafkaConsumer {
    val latch = CountDownLatch(1)
    private val consumedRecords: MutableList<ConsumerRecord<String, String>> = mutableListOf()

    @KafkaListener(topics = ["\${KAFKA_PGI_ENDRING_TOPIC}"], groupId = "pensjonopptjening", containerFactory = "testConsumer")
    fun receive(consumerRecord: ConsumerRecord<String, String>) {
        println("${TestKafkaConsumer::class.simpleName} received record: $consumerRecord")
        consumedRecords.add(consumerRecord)
        latch.countDown()
    }

    internal fun getLastConsumedRecord() = consumedRecords.last()
}