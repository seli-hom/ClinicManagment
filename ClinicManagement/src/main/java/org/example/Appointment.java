package org.example;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Appointment {
    private static int count = 1;
    private String appointmentId;
    private Patient patient;
    private Doctor doctor;
    private LocalDate date;
    private LocalTime time;

    public Appointment(String appointmentId, Patient patient, Doctor doctor, LocalDate date, LocalTime time) {
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

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
