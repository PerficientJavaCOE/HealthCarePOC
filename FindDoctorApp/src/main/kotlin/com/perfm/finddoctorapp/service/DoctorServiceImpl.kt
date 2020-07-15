package com.perfm.finddoctorapp.service

import com.perf.configuration.drools.droolsconfiguration.DroolsConfig
import com.perf.configuration.drools.droolsconfiguration.Response
import com.perfm.finddoctorapp.exception.DetailsNotFoundException
import com.perfm.finddoctorapp.exception.DoctorDetailNotValidExceptions
import com.perfm.finddoctorapp.model.Doctor
import com.perfm.finddoctorapp.repository.DoctorRepository
import com.perfm.finddoctorapp.repository.HospitalDetailsRepository
import com.perfm.finddoctorapp.util.BasicCrud
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class DoctorServiceImpl(val doctorRepository: DoctorRepository, val hospitalDetailsRepository: HospitalDetailsRepository):BasicCrud<String, Doctor>,
        DroolsConfig() {
    private val log = LoggerFactory.getLogger(DoctorServiceImpl::class.java)
    override fun getAll(pageable: Pageable): Page<Doctor> {
        log.debug("Inside DoctorServiceImpl::getAll()")
        return doctorRepository.findAll(pageable)
    }
    override fun getById(id: String): Optional<Doctor> {
        log.debug("Inside DoctorServiceImpl::getById()")
        return if(doctorRepository.existsById(id))
            doctorRepository.findById(id)
        else
            throw DetailsNotFoundException("Doctor Detail for Doctor Id: $id Not Found")

    }
    override fun insert(obj: Doctor): Doctor {
        val responseMessage: Response = validateDoctorDetails(obj,"responseResult")
        return if(responseMessage.message.isEmpty())
            doctorRepository.insert(obj.apply { this.hospitalAffiliation.hospitalDetails=hospitalDetailsRepository.findById(obj.hospitalAffiliation.hospitalDetails.id).get()})
        else
            throw DoctorDetailNotValidExceptions(responseMessage.message)
    }
    override fun update(obj: Doctor): Doctor {
        log.debug("Inside DoctorServiceImpl::update()")
        return if (doctorRepository.existsById(obj.id))
            doctorRepository.save(obj.apply { this.hospitalAffiliation.hospitalDetails = hospitalDetailsRepository.findById(obj.hospitalAffiliation.hospitalDetails.id).get()})
        else
            throw DetailsNotFoundException("Doctor Detail for Doctor Id: ${obj.id} Not Found")
    }

    fun upsert(obj: Doctor): Doctor {
        val responseMessage: Response = validateDoctorDetails(obj,"responseResult")
        return if(responseMessage.message.isEmpty()) {
            doctorRepository.save(obj.apply { this.hospitalAffiliation.hospitalDetails = hospitalDetailsRepository.findById(obj.hospitalAffiliation.hospitalDetails.id).get() })
        }
        else
            throw DoctorDetailNotValidExceptions(responseMessage.message)
    }

    override fun deleteById(id: String): Optional<Doctor> {
        log.debug("Inside DoctorServiceImpl::deleteById()")
        return doctorRepository.findById(id).apply { this.ifPresent{doctorRepository.delete(it)} }
    }
    fun deleteAllDoctorCollections(){
        log.debug("Inside DoctorServiceImpl::deleteAllDoctorCollections()")
        doctorRepository.deleteAll()
    }
}