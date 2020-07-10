package com.perfm.finddoctorapp

import com.perfm.finddoctorapp.model.*
import com.perfm.finddoctorapp.repository.DoctorRepository
import com.perfm.finddoctorapp.repository.HospitalDetailsRepository
import com.perfm.finddoctorapp.service.DoctorServiceImpl
import com.perfm.finddoctorapp.service.HospitalService
import com.perfm.finddoctorapp.util.Objects
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.LocalDate

@SpringBootApplication
class FindDoctorAppApplication(private val doctorServiceImpl: DoctorServiceImpl, private val hospitalService: HospitalService,
                                private val hospitalDetailsRepository: HospitalDetailsRepository,
								private val doctorRepository: DoctorRepository) : ApplicationRunner{

    private val log = LoggerFactory.getLogger(FindDoctorAppApplication::class.java)

    override fun run(args: ApplicationArguments?) {
        val hospitalDetailH1003: HospitalDetails = getHospitalDetailH1003()
        val hospitalDetailH1004: HospitalDetails = getHospitalDetailH1004()
//        val hospitalDetails = listOf(
//                hospitalDetailH1003, hospitalDetailH1004
//        )
//        hospitalDetailsRepository.insert(hospitalDetails)
        if (doctorRepository.count()<1)	this.createDoctorDetails()
    }


    private fun createDoctorDetails() {
        this.cleanCollections()

        val h1 = HospitalDetails(id = "H1000", hospitalName = "Santa Clara Homestead Med Center", address = "710 Lawrence Expy", city = "CA", zipCode = "95051", country = "USA")
        val h2 = HospitalDetails(id = "H1001", hospitalName = "San Jose Medical Center", address = "625 Lincoln Ave", city = "CA", zipCode = "95126",  country = "USA")
        val h3 = HospitalDetails(id = "H1002", hospitalName = "Fremont Medical Center", address = "39400 Paseo Padre Parkway", city = "CA", zipCode = "94538", country = "USA")
		val hospitalDetails = listOf(
				h1, h2, h3
		)
		if (hospitalDetailsRepository.count()<1) hospitalDetailsRepository.insert(hospitalDetails)

        val neurologist = Specialization(id = "S1000", specializationName = "neurologist")
        val doctorSpecialization = DoctorSpecialization(id = "DS1000", doctorId = "D1000", specializationId = "S1000")
        val q1000 = Qualification(id = "Q1000", doctorId = "D1000", qualificationName = "MD Neurology",
                institutionName = "Northwestern University Feinberg School of Medicine",
                procurementYear = getFormattedDate("20-12-2018"))
        val santaClara = HospitalAffiliation(id = "HA1000", doctorId = "D1000", hospitalDetails = h1,
                startDate = getFormattedDate("20-12-2018"),
                endDate = getFormattedDate("20-12-2018"))
        val doctor = Doctor(id = "D1000", doctorSpecialization = doctorSpecialization,
                firstName = "Philip", lastName = "Chang", hospitalAffiliation = santaClara,
                practicingFrom = getFormattedDate("20-12-2018"),
                professionalStatement = "I believe that the brain is a powerful organ at the interface between the mind, " +
                        "body and soul. As a neurohospitalist, I help guide patients through the journey of having an acute neurological illness, " +
                        "and prescribe the best available evidence-based treatments. As a Christian physician, I strive to be source of compassion and " +
                        "honest counseling, especially in conditions for which there is little treatment."
                , qualification = q1000, specialization = neurologist)

		val savedDoctor:Doctor = doctorRepository.insert(doctor)
		println("Doctor details is inserted $savedDoctor ")
    }

    private fun getFormattedDate(date: String): LocalDate {
        return LocalDate.parse(date, Objects.dateFormat)
    }

    private fun cleanCollections() {
        doctorServiceImpl.deleteAllDoctorCollections()
        hospitalService.deleteAllHospitalCollections()
    }
    fun getHospitalDetailH1003(): HospitalDetails = HospitalDetails(id = "H1003", hospitalName = "Redwood City Medical Center", address = "1190 Veterans Blvd", city = "CA", zipCode = "94063", country = "USA")
    fun getHospitalDetailH1004(): HospitalDetails = HospitalDetails(id = "H1004", hospitalName = "Indian Hill Medical Offices", address = "250 W San Jose Ave", city = "CA", zipCode = "91711", country = "USA")
    fun getHospitalDetailH1005(): HospitalDetails = HospitalDetails(id = "H1005", hospitalName = "Bv Camarillo Medical Offices", address = "2620 Las Posas Rd", city = "CA", zipCode = "93010", country = "USA")
}

fun main(args: Array<String>) {
    runApplication<FindDoctorAppApplication>(*args)
}
