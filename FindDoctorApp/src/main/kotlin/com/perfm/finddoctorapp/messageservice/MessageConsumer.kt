package com.perfm.finddoctorapp.messageservice

import com.perfm.finddoctorapp.model.Doctor
import com.perfm.finddoctorapp.service.DoctorServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.util.concurrent.CountDownLatch

@Service
class MessageConsumer(private val doctorServiceImpl: DoctorServiceImpl){

    private val log = LoggerFactory.getLogger(MessageConsumer::class.java)
    val latch = CountDownLatch(3)

    val Latch1 = CountDownLatch(1)

    @KafkaListener(topics = ["\${doctor.topic.name}"], containerFactory = "doctorKafkaListenerContainerFactory")
    fun doctorListener(doctor: Doctor) {
        log.debug("Received message: $doctor")
        val savedDoctorDetails : Doctor = doctorServiceImpl.upsert(doctor)
        log.debug("Doctor details persisted in MongoDB: $savedDoctorDetails")
        Latch1.countDown()
    }
}