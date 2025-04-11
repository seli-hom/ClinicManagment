package org.example;

import java.sql.Time;
import java.util.Date;

public class Appointment {
    private static int count = 1;
    private String appointmentId;
    private Patient patient;
    private Doctor doctor;
    private Date date;
    private Time time;

    public Appointment(String appointmentId, Patient patient, Doctor doctor, Date date, Time time) {
        this.appointmentId = "A%03d" + count++;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
    }
}
