package com.perfm.finddoctorapp.kafkaengine


import com.perfm.finddoctorapp.model.Doctor
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import java.util.*

@EnableKafka
@Configuration
class KafkaConsumerConfig{

    @Value(value = "\${kafka.bootstrapAddress}")
    private val bootstrapAddress: String? = null

    fun doctorConsumerFactory(): ConsumerFactory<String?, Doctor?> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress!!
        props[ConsumerConfig.GROUP_ID_CONFIG] = "doctor"
        return DefaultKafkaConsumerFactory<String?, Doctor?>(props,StringDeserializer(), JsonDeserializer<Doctor>(Doctor::class.java))
    }

    @Bean
    fun doctorKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Doctor>? {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Doctor>()
        factory.consumerFactory = doctorConsumerFactory()
        return factory
    }
}