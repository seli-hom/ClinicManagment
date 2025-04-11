package org.example;

import java.util.Date;
import java.util.List;

public class Record {
    private Date birthDate;
    private Sex sex;
    private Doctor familyDoctor;
    private BloodType type;
    private double height;
    private double weight;
    private List<String> prescriptions;

    public Record(Date birthDate, Sex sex, Doctor familyDoctor, BloodType type, double height, double weight, List<String> prescriptions) {
        this.birthDate = birthDate;
        this.sex = sex;
        this.familyDoctor = familyDoctor;
        this.type = type;
        this.height = height;
        this.weight = weight;
        this.prescriptions = prescriptions;
    }
}
