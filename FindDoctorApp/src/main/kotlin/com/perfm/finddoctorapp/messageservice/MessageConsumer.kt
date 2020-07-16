package com.perfm.finddoctorapp.messageservice

import com.perfm.finddoctorapp.exception.DoctorDetailNotValidExceptions
import com.perfm.finddoctorapp.model.Doctor
import com.perfm.finddoctorapp.service.DoctorServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.util.concurrent.CountDownLatch

@Service
class MessageConsumer(private val doctorServiceImpl: DoctorServiceImpl){

    private val log = LoggerFactory.getLogger(MessageConsumer::class.java)


    @KafkaListener(topics = ["\${doctor.topic.name}"], containerFactory = "doctorKafkaListenerContainerFactory")
    fun doctorListener(doctor: Doctor) {
        log.debug("Received message: $doctor")
        try{
            val savedDoctorDetails : Doctor = doctorServiceImpl.upsert(doctor)
            log.debug("Doctor details persisted in MongoDB: $savedDoctorDetails")
        }catch (ex: DoctorDetailNotValidExceptions){
            log.debug("An error occurred and Doctor Details not persisted in MongoDB: ${ex.message}")
        }
    }
}