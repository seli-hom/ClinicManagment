package org.example;

import java.util.Date;
import java.util.List;

public class Patient {
    private static int count = 1;
    private String patientId;
    private String firstName;
    private String lastName;
    private String address;
    private String contact;
    private Date birthDate;
    private Sex sex;
    private Doctor familyDoctor;
    private BloodType type;
    private double height;
    private double weight;
    private List<String> prescriptions;

    public Patient(String patientId, String firstName, String lastName, String address, String contact, Date birthDate, Sex sex, BloodType type, double height, double weight) {
        this.patientId = "P%03F" + count ++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.contact = contact;
        this.birthDate = birthDate;
        this.sex = sex;
        this.familyDoctor = null; //at creation patient does not have a doctor in the clinic
        this.type = type;
        this.height = height;
        this.weight = weight;
//        this.prescriptions = prescriptions;
    }

    public String getPatientId() {
        return patientId;
    }

    public static int getCount() {
        return count;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }


    public Date getBirthDate() {
        return birthDate;
    }

    public Sex getSex() {
        return sex;
    }

    public Doctor getFamilyDoctor() {
        return familyDoctor;
    }

    public BloodType getType() {
        return type;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

//    public List<String> getPrescriptions() {
//        return prescriptions;
//    }

//    public static void setCount(int count) {
//        Patient.count = count;
//    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


//    public void setBirthDate(Date birthDate) {
//        this.birthDate = birthDate;
//    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setFamilyDoctor(Doctor familyDoctor) {
        this.familyDoctor = familyDoctor;
    }

    public void setType(BloodType type) {
        this.type = type;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

//    public void setPrescriptions(List<String> prescriptions) {
//        this.prescriptions = prescriptions;
//    }
}
