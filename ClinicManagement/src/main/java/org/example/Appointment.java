package org.example;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;

public class Appointment {
    private static int count = 1;
    private String appointmentId;
    private Patient patient;
    private Doctor doctor;
    private Date date;
    private Time time;

    public Appointment(Patient patient, Doctor doctor, Date date, Time time) {
        this.appointmentId = "A%03d" + count++;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public static int getCount() {
        return count;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public static void setCount(int count) {
        Appointment.count = count;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
