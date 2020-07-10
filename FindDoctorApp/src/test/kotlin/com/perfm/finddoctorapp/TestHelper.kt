package com.perfm.finddoctorapp

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.SerializationFeature
import com.perfm.finddoctorapp.model.*
import com.perfm.finddoctorapp.util.Objects
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class TestHelper {

    /*
    *
    * val neurologist = Specialization(id = "S1000", specializationName = "neurologist")
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
    * */

    fun getDoctorD1003(): Doctor{
        val h1002 = HospitalDetails(id = "H1002", hospitalName = "Fremont Medical Center", address = "39400 Paseo Padre Parkway", city = "CA", zipCode = "94538", country = "USA")
        val neurologist = Specialization(id = "S1000", specializationName = "neurologist")
        val doctorSpecialization = DoctorSpecialization(id = "DS1000", doctorId = "D1003", specializationId = "S1000")
        val q1000 = Qualification(id = "Q1000", doctorId = "D1003", qualificationName = "MD Neurology",
                institutionName = "Northwestern University Feinberg School of Medicine",
                procurementYear = getFormattedDate("20-12-2018"))
        val Fremont = HospitalAffiliation(id = "HA1001", doctorId = "D1003", hospitalDetails = h1002,
                startDate = getFormattedDate("20-12-2018"),
                endDate = getFormattedDate("20-12-2018"))
        val doctor = Doctor(id = "D1003", doctorSpecialization = doctorSpecialization,
                firstName = "Edward", lastName = "Joseph", hospitalAffiliation = Fremont,
                practicingFrom = getFormattedDate("20-12-2018"),
                professionalStatement = "I believe that the brain is a powerful organ at the interface between the mind, " +
                        "body and soul. As a neurohospitalist, I help guide patients through the journey of having an acute neurological illness, " +
                        "and prescribe the best available evidence-based treatments. As a Christian physician, I strive to be source of compassion and " +
                        "honest counseling, especially in conditions for which there is little treatment."
                , qualification = q1000, specialization = neurologist)
        return doctor
    }

    fun getHospitalDetailH1002(): HospitalDetails = HospitalDetails(id = "H1002", hospitalName = "Fremont Medical Center", address = "39400 Paseo Padre Parkway", city = "CA", zipCode = "94538", country = "USA")
    fun getHospitalDetailH1003(): HospitalDetails = HospitalDetails(id = "H1003", hospitalName = "Redwood City Medical Center", address = "1190 Veterans Blvd", city = "CA", zipCode = "94063", country = "USA")
    fun getHospitalDetailH1004(): HospitalDetails = HospitalDetails(id = "H1004", hospitalName = "Indian Hill Medical Offices", address = "250 W San Jose Ave", city = "CA", zipCode = "91711", country = "USA")
    fun getHospitalDetailH1005(): HospitalDetails = HospitalDetails(id = "H1005", hospitalName = "Bv Camarillo Medical Offices", address = "2620 Las Posas Rd", city = "CA", zipCode = "93010", country = "USA")

    fun getHospitalDetailsList(): List<HospitalDetails>{
        val h1 = HospitalDetails(id = "H1000", hospitalName = "Santa Clara Homestead Med Center", address = "710 Lawrence Expy", city = "CA", zipCode = "95051", country = "USA")
        val h2 = HospitalDetails(id = "H1001", hospitalName = "San Jose Medical Center", address = "625 Lincoln Ave", city = "CA", zipCode = "95126", country = "USA")
        val h3 = HospitalDetails(id = "H1002", hospitalName = "Fremont Medical Center", address = "39400 Paseo Padre Parkway", city = "CA", zipCode = "94538", country = "USA")
        val hospitalDetails = listOf(
                h1,h2,h3,getHospitalDetailH1003(), getHospitalDetailH1004(),getHospitalDetailH1005()
        )
        return hospitalDetails
    }

    fun requestJson(h1:HospitalDetails):String{
       // val h1 = HospitalDetails(id = "H1000", hospitalName = "Santa Clara Homestead Med Center", city = "CA", country = "USA")
        val mapper = ObjectMapper()
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false)
        val ow: ObjectWriter = mapper.writer().withDefaultPrettyPrinter()
        return ow.writeValueAsString(h1)
    }

    private fun getFormattedDate(date: String): LocalDate {
        return LocalDate.parse(date, Objects.dateFormat)
    }


    fun requestDoctorJson(doctor: Doctor):String{
        // val h1 = HospitalDetails(id = "H1000", hospitalName = "Santa Clara Homestead Med Center", city = "CA", country = "USA")
        val mapper = ObjectMapper()
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false)
        val ow: ObjectWriter = mapper.writer().withDefaultPrettyPrinter()
        return ow.writeValueAsString(doctor)
    }



}