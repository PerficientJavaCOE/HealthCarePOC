package com.perfm.finddoctorapp.service

import com.perf.configuration.drools.droolsconfiguration.DroolsConfig
import com.perf.configuration.drools.droolsconfiguration.Response
import com.perfm.finddoctorapp.exception.DetailsNotFoundException
import com.perfm.finddoctorapp.exception.HospitalDetailNotValidException
import com.perfm.finddoctorapp.model.HospitalDetails
import com.perfm.finddoctorapp.repository.HospitalDetailsRepository
import com.perfm.finddoctorapp.util.BasicCrud
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class HospitalService(@Autowired val hospitalDetailsRepository: HospitalDetailsRepository):BasicCrud<String,HospitalDetails>,
        DroolsConfig() {
    private val log = LoggerFactory.getLogger(HospitalService::class.java)

    override fun getAll(pageable: Pageable): Page<HospitalDetails> {
        log.debug("Inside HospitalService::getAll()")
        return hospitalDetailsRepository.findAll(pageable)
    }

    override fun getById(id: String): Optional<HospitalDetails> {
        log.debug("Inside HospitalService::getById()")
        return if(hospitalDetailsRepository.existsById(id))
            hospitalDetailsRepository.findById(id)
        else
            throw DetailsNotFoundException("Hospital Detail for Hospital Id: $id Not Found")
    }

    @Throws(HospitalDetailNotValidException::class)
    override fun insert(obj: HospitalDetails): HospitalDetails {
        log.debug("Inside HospitalService::insert()")
        val responseMessage: Response = validateDoctorDetails(obj,"responseResult")
        if (!responseMessage.message.isEmpty()){
            log.info("responseResult code is ${responseMessage.code} and message is ${responseMessage.message}")
        }
        return if(responseMessage.message.isEmpty())
            hospitalDetailsRepository.insert(obj)
        else
            throw HospitalDetailNotValidException(responseMessage.message)
    }

    @Throws(Exception::class)
    override fun update(obj: HospitalDetails): HospitalDetails {
        log.debug("Inside HospitalService::update()")
        if (hospitalDetailsRepository.existsById(obj.id)) {
            return hospitalDetailsRepository.save(obj)
        } else {
            throw object : Exception("Hospital not found") {}
        }
    }

    fun upsert(obj: HospitalDetails): HospitalDetails {
        val responseMessage: Response = validateDoctorDetails(obj,"responseResult")
        return if(responseMessage.message.isEmpty()) {
            hospitalDetailsRepository.save(obj)
        }
        else
            throw HospitalDetailNotValidException(responseMessage.message)
    }

    override fun deleteById(id: String): Optional<HospitalDetails> {
        log.debug("Inside HospitalService::deleteById()")
        return if (hospitalDetailsRepository.existsById(id))
            hospitalDetailsRepository.findById(id).apply { this.ifPresent { hospitalDetailsRepository.delete(it) } }
        else
            throw DetailsNotFoundException("Hospital Detail for Hospital Id: $id Not Found")
    }

    fun deleteAllHospitalCollections(){
        log.debug("Inside HospitalService::()")
        hospitalDetailsRepository.deleteAll()
    }
}