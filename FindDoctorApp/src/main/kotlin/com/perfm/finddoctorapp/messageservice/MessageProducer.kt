package com.perfm.finddoctorapp.messageservice


import com.perfm.finddoctorapp.exception.ProducerException
import com.perfm.finddoctorapp.model.Doctor
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import org.springframework.util.concurrent.ListenableFutureCallback

@Service
class MessageProducer {


    private val log = LoggerFactory.getLogger(MessageProducer::class.java)

    @Autowired
    private var doctorKafkaTemplate: KafkaTemplate<String, Doctor>? = null

    @Value(value = "\${doctor.topic.name}")
    private val doctorTopicName: String? = null

    fun sendMessage(doctor: Doctor) {
        val future = doctorKafkaTemplate!!.send(doctorTopicName!!, doctor)
        future.addCallback(object : ListenableFutureCallback<SendResult<String?, Doctor?>?> {
            override fun onSuccess(result: SendResult<String?, Doctor?>?) {
                println("Sent message=[$doctor] with offset=[" + result!!.recordMetadata
                        .offset() + "]")
            }

            override fun onFailure(ex: Throwable) {
                println("Unable to send message=[" + doctor + "] due to : " + ex.message)
            }
        })
    }

    fun sendDoctorDetails(doctor: Doctor) : String {
        log.debug("Inside sendDoctorDetails : Doctor details $doctor")
        try {
            doctorKafkaTemplate!!.send(doctorTopicName!!, doctor)
            return "Record Published successfully"
        } catch (ex: Exception) {
            log.debug("An error occurred! ${ex.message}")
        }
        return "Record failed to Publish"
    }


}