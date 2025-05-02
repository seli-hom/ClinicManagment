package org.example;

import javax.swing.*;
import java.awt.event.*;

import java.sql.Date;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;

public class ClinicController {
    private ClinicManagementGUI view;
    private SystemManager model;

    public ClinicController(ClinicManagementGUI view, SystemManager model) {
        this.view = view;
        this.model = model;

        setUpDoctorActions();
        setUpPatientActions();
        setUpAppointmentActions();
    }

    public void setUpDoctorActions() {
        view.getAddDoctorButton().addActionListener(e -> {
            String firstName = JOptionPane.showInputDialog(("First Name:"));
            String lastName = JOptionPane.showInputDialog(("Last Name:"));
            String specialty = JOptionPane.showInputDialog(("Specialty:"));
            String contact = JOptionPane.showInputDialog(("Contact:"));

            if (firstName == null || lastName == null || contact == null || specialty == null) {
                JOptionPane.showMessageDialog(view, "Make sure all fields are filled out.");
            }

            Doctor doctor = new Doctor(firstName, lastName, specialty, contact);
            model.registerDoctor(doctor);
            view.updateDoctorTable();

            System.out.println(doctor.getDoctorId());
        });

        view.getModifyDoctorButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter the ID of the doctor to modify:");
            if (id != null) {
                String newContact = JOptionPane.showInputDialog("Enter new contact:");
                model.modifyDoctor(id, newContact);
                view.updateDoctorTable();
            }
            else {
                JOptionPane.showMessageDialog(view, "No doctor with the given ID exists.");
            }

        });

        view.getFindDoctorButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter the ID of the doctor to find:");
            Doctor doctor = model.findDoctor(id);

            if (doctor != null) {
                JOptionPane.showMessageDialog(view, "Doctor found: " + doctor.getFirstName() + " " + doctor.getLastName());
            }
            else {
                JOptionPane.showMessageDialog(view, "No doctor with the given ID exists.");
            }
        });

        view.getFindDoctorBySpecialtyButton().addActionListener(e -> {
            String specialty = JOptionPane.showInputDialog("Specialty:");
            if (specialty != null) {
                List<Doctor> docs = model.findDoctorsBySpecialty(specialty);
                JOptionPane.showMessageDialog(view, "Found " + docs.size() + " doctors(s).");
            }
            else {
                JOptionPane.showMessageDialog(view, "No doctor with the given ID exists.");
            }
        });
    }

    public void setUpPatientActions() {
        view.getAddPatientButton().addActionListener(e -> {
            String firstName = JOptionPane.showInputDialog("First Name:");
            String lastName = JOptionPane.showInputDialog("Last Name:");
            String address = JOptionPane.showInputDialog("Address:");
            String contact = JOptionPane.showInputDialog("Contact:");
            String dobStr = JOptionPane.showInputDialog("Date of Birth (yyyy-mm-dd):");
            String sex = JOptionPane.showInputDialog("Sex (M/F/O):");
            String bloodType = JOptionPane.showInputDialog("Blood Type:");

            if (firstName == null || lastName == null || address == null || contact == null || dobStr == null || sex == null || bloodType == null) {
                JOptionPane.showConfirmDialog(view, "Make sure all fields are filled out.");
            }

            Date dob = Date.valueOf(dobStr);
            Patient patient = new Patient(firstName, lastName, address, contact, dob, sex, bloodType);
            model.registerPatient(patient);
            view.updatePatientTable();

            System.out.println(patient.getPatientId());
        });

        view.getModifyPatientButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter the ID of the patient to modify:");
            String newAddress = JOptionPane.showInputDialog("New Address:");
            String newContact = JOptionPane.showInputDialog("New Contact:");

            if (id != null) {
                if (newAddress != null || newContact != null) {
                    model.updatePatientInfo(id, newAddress, newContact);
                    view.updatePatientTable();
                }
                else {
                    JOptionPane.showMessageDialog(view, "Make sure all fields are filled out.");
                }
            }
            else {
                JOptionPane.showMessageDialog(view, "No patient with the given ID exists.");
            }
        });

        view.getFindPatientButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Patient ID:");
            Patient patient = model.findPatient(id);
            if (patient != null) {
                JOptionPane.showMessageDialog(view, "Patient found: " + patient.getFirstName() + " " + patient.getLastName());
            }
            else {
                JOptionPane.showMessageDialog(view, "No patient with the given ID exists.");
            }
        });
    }

    public void setUpAppointmentActions() {
        view.getBookAppointmentButton().addActionListener(e -> {
            String patientId = JOptionPane.showInputDialog("Patient ID:");
            String doctorId = JOptionPane.showInputDialog("Doctor ID:");
            String dateStr = JOptionPane.showInputDialog("Date (yyyy-mm-dd):");
            String timeStr = JOptionPane.showInputDialog("Time (HH:mm):");

            try {
                Date date = Date.valueOf(dateStr);
                Time time = Time.valueOf(timeStr + ":00");
                Appointment appointment = new Appointment(patientId, doctorId, date, time);
                model.bookAppointment(appointment);
                System.out.println(appointment.getAppointmentId());
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
            }
        });

        view.getRescheduleAppointmentButton().addActionListener(e -> {
            String aptId = JOptionPane.showInputDialog("Appointment ID:");
            String newDateStr = JOptionPane.showInputDialog("New Date (yyyy-mm-dd):");
            String newTimeStr = JOptionPane.showInputDialog("New Time (HH:mm):");

            try {
                Date newDate = Date.valueOf(newDateStr);
                Time newTime = Time.valueOf(newTimeStr + ":00");
                model.rescheduleAppointment(aptId, newDate, newTime);
                view.updateAppointmentTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
            }
        });

        view.getCancelAppointmentButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Appointment ID:");
            try {
                if (id != null) {
                    if (model.findAppointmentByID(id) != null) {
                        model.cancelAppointment(id);
                        view.updateAppointmentTable();
                        JOptionPane.showMessageDialog(view, "Appointment with ID " + id + " successfully cancelled");
                    }
                    else {
                        JOptionPane.showMessageDialog(view, "No appointment with the given ID exists.");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(view, "Make sure all fields are filled out.");
                }
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
            }
        });

        view.getFindAppointmentButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Appointment ID:");
            try {
                if (id != null) {
                    if (model.findAppointmentByID(id) != null) {
                        Appointment appointment = model.findAppointmentByID(id);
                        view.updateAppointmentTable();
                        JOptionPane.showMessageDialog(view, "Appointment " + appointment.getAppointmentId() + " found");
                    }
                    else {
                        JOptionPane.showMessageDialog(view, "No appointment with the given ID exists.");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(view, "Make sure all fields are filled out.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
            }
        });
    }

//    public ClinicController(ClinicManagementGUI view, SystemManager model) {
//        this.view = view;
//        this.model = model;
//        DBConnection database = DBConnection.getInstance();
//        database.getInstance().getConnection();
//        database.createNewDoctorsTable();
//        database.createNewPatientsTable();
//        database.createNewAppointmentsTable();
//
//        class AddDoctorListener implements ActionListener {
//            public void actionPerformed(ActionEvent e) {
//                String firstName = JOptionPane.showInputDialog("Messages.getMessage(");
//                String lastName = JOptionPane.showInputDialog("Messages.getMessage(");
//                String speciality = JOptionPane.showInputDialog("Messages.getMessage(");
//                String contact = JOptionPane.showInputDialog("Messages.getMessage(");
//
//                Doctor doctor = new Doctor(firstName, lastName, speciality, contact);
//                try {
//                    model.registerDoctor(doctor);
//                    view.updateDoctorTable();
//                }
//                catch (Exception ex) {
//                    JOptionPane.showMessageDialog(view, ex.getMessage());
//                }
//            }
//        }
//
//        this.view.getAddDoctorButton().addActionListener(new AddDoctorListener());
//
//        class ModifyDoctorContactListener implements ActionListener {
//            public void actionPerformed(ActionEvent e) {
//                String doctorID = JOptionPane.showInputDialog("jajd");
//                String newContact = JOptionPane.showInputDialog("lala");
//
//                try {
//                    model.modifyDoctor(doctorID, newContact);
//                    view.updateDoctorTable();
//                }
//                catch(Exception ex) {
//                    JOptionPane.showMessageDialog(view, ex.getMessage());
//                }
//            }
//        }
//
//       this.view.getModifyDoctorButton().addActionListener(new ModifyDoctorContactListener());
//
//        class FindDoctorListener implements ActionListener {
//            public void actionPerformed(ActionEvent e) {
//                String doctorID = JOptionPane.showInputDialog("DoctorID");
//
//                try{
//                    model.findDoctor(doctorID);
//                    view.updateDoctorTable();
//                }
//                catch(Exception ex) {
//                    JOptionPane.showMessageDialog(view, ex.getMessage());
//                }
//            }
//        }
//        this.view.getFindDoctorButton().addActionListener(new FindDoctorListener());
//
//        class FindDoctorBySpecialityListener implements ActionListener {
//            public void actionPerformed(ActionEvent e) {
//                String speciality = JOptionPane.showInputDialog("Speciality");
//
//                try{
//                    model.findDoctorsBySpecialty(speciality);
//                    view.updateDoctorTable();
//                }
//                catch(Exception ex) {
//                    JOptionPane.showMessageDialog(view, ex.getMessage());
//                }
//            }
//        }
//        this.view.getFindDoctorBySpecialtyButton().addActionListener(new FindDoctorBySpecialityListener());
//
//        class AddPatientListener implements ActionListener {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String fname = JOptionPane.showInputDialog("enter first name");
//                String lname = JOptionPane.showInputDialog("enter last name");
//                String address = JOptionPane.showInputDialog("enter adress");
//                String contact = JOptionPane.showInputDialog("enter contact information");
//                JSpinner dateForDob = new JSpinner(new SpinnerDateModel());
//                JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateForDob, "dd/MM/yyyy");
//                dateForDob.setEditor(dateEditor);
//                Date dob = (Date) dateForDob.getValue();
//                JComboBox<String> sex = new JComboBox<>();
//                sex.addItem("Male");
//                sex.addItem("Female");
//                sex.addItem("Other");
//                JComboBox<String> bloodType = new JComboBox<>();
//                bloodType.addItem("O+");
//                bloodType.addItem("A+");
//                bloodType.addItem("B+");
//                bloodType.addItem("AB+");
//                bloodType.addItem("O-");
//                bloodType.addItem("A-");
//                bloodType.addItem("B-");
//                bloodType.addItem("AB-");
//
//                Patient newPatient = new Patient(fname, lname, address, contact, dob, sex.getSelectedItem().toString(), bloodType.getSelectedItem().toString());
//
//                try {
//                    model.registerPatient(newPatient);
//                    view.updatePatientTable();
//                }catch (Exception ex) {
//                    JOptionPane.showMessageDialog(view, ex.getMessage());
//                }
//            } //action performed method
//
//        } // add Patient class
//        this.view.getAddPatientButton().addActionListener(new AddPatientListener());
//
//        //        addPatientButton.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                // Open a dialog to add a new patient
////                String firstName = JOptionPane.showInputDialog("Enter patient's first name:");
////                String lastName = JOptionPane.showInputDialog("Enter patient's last name:");
////                String address = JOptionPane.showInputDialog("Enter patient's address:");
////                String contact = JOptionPane.showInputDialog("Enter patient's contact information:");
////                String dob = JOptionPane.showInputDialog("Enter patient's date of birth:");
////                String sex = JOptionPane.showInputDialog("Enter patient's sex:");
////                String bloodType = JOptionPane.showInputDialog("Enter patient's blood type:");
////                Patient patient = new Patient(firstName, lastName, address, contact, dob, sex, bloodType);
////
////                systemManager.registerPatient(patient);
////                updatePatientTable();
////            }
////        });
//        class ModifyPatientListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String id = JOptionPane.showInputDialog("enter id");
//            String newAdress = JOptionPane.showInputDialog("enter new address");
//            String newContact = JOptionPane.showInputDialog("enter new contact");
//
//            try {
//                model.updatePatientInfo(id, newAdress, newContact);
//                view.updatePatientTable();
//            }
//            catch (Exception ex) {
//                JOptionPane.showMessageDialog(view, ex.getMessage());
//            }
////        }  @Override
////        public void actionPerformed(ActionEvent e) {
////            // Modify patient details
////            int selectedRow = view.getPatientTable.getSelectedRow();
////            if (selectedRow >= 0) {
////                int patientId = (int) patientTable.getValueAt(selectedRow, 0);
////                Patient patient = patientDAO.getPatientById(patientId);
////
////                String newName = JOptionPane.showInputDialog("Enter new name:", patient.getName());
////                int newAge = Integer.parseInt(JOptionPane.showInputDialog("Enter new age:", patient.getAge()));
////                patient.setName(newName);
////                patient.setAge(newAge);
////
////                if (PatientDAO.modifyPatient(patient)) {
////                    updatePatientTable();
////                } else {
////                    JOptionPane.showMessageDialog(null, "Failed to modify patient.");
////                }
////            } else {
////                JOptionPane.showMessageDialog(null, "No patient selected.");
////            }
////        }
//        }
//    }
//        this.view.getModifyPatientButton().addActionListener(new ModifyPatientListener());
//
//    //        findPatientButton.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                // Find patient by id
////                String searchId = JOptionPane.showInputDialog("Enter patient id:");
////                if (patientDAO.getPatientCache().equals(searchId)) {
////                    patientTable.add(patientDAO.getPatientCache())
////                    updatePatientTable();
////                } else {
////                    JOptionPane.showMessageDialog(null, "No patient found.");
////                    updatePatientTable(patients);
////                }
////            }
////        });
//        class FindPatientListener implements ActionListener {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String id = JOptionPane.showInputDialog("enter id");
//
//                try {
//                    model.findPatient(id);
//                    view.updatePatientTable();
//                }
//                catch (Exception ex) {
//                    JOptionPane.showMessageDialog(view, ex.getMessage());
//                }
//             }
//        }
//        this.view.getFindPatientButton().addActionListener(new FindPatientListener());
//
////        bookAppointmentButton.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                // Book appointment with a doctor
////                int selectedPatientRow = patientTable.getSelectedRow();
////                int selectedDoctorRow = doctorTable.getSelectedRow();
////
////                if (selectedPatientRow >= 0 && selectedDoctorRow >= 0) {
////                    int patientId = (int) patientTable.getValueAt(selectedPatientRow, 0);
////                    int doctorId = (int) doctorTable.getValueAt(selectedDoctorRow, 0);
////
////                    String dateTime = JOptionPane.showInputDialog("Enter appointment date and time (yyyy-MM-dd HH:mm):");
////                    AppointmentDAO bookAppt = new AppointmentDAO(patientId, doctorId, dateTime);
////
////                    if (bookAppt.bookAppointment()) {
////                        JOptionPane.showMessageDialog(null, "Appointment booked successfully!");
////                        updateAppointmentTable();
////                    } else {
////                        JOptionPane.showMessageDialog(null, "Failed to book appointment.");
////                    }
////                }
////            }
////        });
//        class BookAppointmentListener implements ActionListener {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String patientid = JOptionPane.showInputDialog("enter patient id");
//                String doctorid = JOptionPane.showInputDialog("enter doctor id");
//                JSpinner datForApt = new JSpinner(new SpinnerDateModel());
//                JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(datForApt, "dd/MM/yyyy");
//                datForApt.setEditor(dateEditor);
//                java.util.Date date = (java.util.Date) datForApt.getValue();
//                Date sqlDate = (Date) date;
//                JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
//                JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
//                timeSpinner.setEditor(timeEditor);
//                timeSpinner.setValue(new Date(date.getTime()));
//                java.util.Date time = (java.util.Date) timeSpinner.getValue();
//                Time sqlTime = new Time(time.getTime());
//
////                Appointment apt = new Appointment(patientid,doctorid,date,time);
//                try {
//                    model.bookAppointment(patientid, doctorid, sqlDate, sqlTime);
//                    view.updateAppointmentTable();
//                } catch (Exception ex) {
//                    JOptionPane.showMessageDialog(view, ex.getMessage());
//                }
//            }
//        }
//
//        this.view.getBookAppointmentButton().addActionListener(new BookAppointmentListener());
//
////        rescheduleAppointmentButton.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                String inputDate = JOptionPane.showInputDialog("Enter the new date of the appointment (yyyy-MM-dd):");
////                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
////
////                String time = JOptionPane.showInputDialog("Enter the new time of the appointment (HH:mm):");
////                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
////                try {
////                    java.util.Date date = java.util.Date.parse(inputDate, dateFormatter.toString());
////                    System.out.println("Parsed date: " + inputDate);
////
////
////                }
////                if (date != null && time != null) {
////                    appointmentDAO.updateSchedule(date, time);
////                }
////
////                // Reschedule appointment
////                int selectedRow = appointmentTable.getSelectedRow();
////                if (selectedRow >= 0) {
////                    int appointmentId = (int) appointmentTable.getValueAt(selectedRow, 0);
////                    String newDateTime = JOptionPane.showInputDialog("Enter new appointment date and time (yyyy-MM-dd HH:mm):");
////
////                    if (AppointmentDAO.rescheduleAppointment(appointmentId, newDateTime)) {
////                        JOptionPane.showMessageDialog(null, "Appointment rescheduled.");
////                        updateAppointmentTable();
////                    } else {
////                        JOptionPane.showMessageDialog(null, "Failed to reschedule appointment.");
////                    }
////                }
////            }
////        });
//        class RescheduleAppointmentListener implements ActionListener {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    String aptId = JOptionPane.showInputDialog("enter apt id");
//                    JSpinner datForApt = new JSpinner(new SpinnerDateModel());
//                    JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(datForApt, "dd/MM/yyyy");
//                    datForApt.setEditor(dateEditor);
//
//                    Date newDate = (Date) datForApt.getValue();
//
//                    JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
//                    JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
//                    timeSpinner.setEditor(timeEditor);
//                    timeSpinner.setValue(new Date(newDate.getTime()));
//
//                    Time newTime = (Time)timeSpinner.getValue();
//
//                    try{
//                        model.rescheduleAppointment(aptId,newDate,newTime);
//                        view.updateAppointmentTable();
//                    }
//                    catch (Exception ex){
//                        JOptionPane.showMessageDialog(view, ex.getMessage());
//                    }
//                }
//        }
//
//        this.view.getRescheduleAppointmentButton().addActionListener(new RescheduleAppointmentListener());
////
////        cancelAppointmentButton.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                String id = JOptionPane.showInputDialog("Enter the id of the appointment to cancel:");
////                if (id != null) {
////                    appointmentDAO.cancelAppointment(id);
////                    JOptionPane.showMessageDialog(null, "Appointment canceled.");
////                }
////                else {
////                    JOptionPane.showMessageDialog(null, "Please fill out id field");
////                }
////            }
////        });
//
//        class CancelAppointmentListener implements ActionListener {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String id = JOptionPane.showInputDialog("Enter the id of the appointment to cancel:");
//                if (id != null) {
//                    try{
//                        model.cancelAppointment(id);
//                        view.updateAppointmentTable();
//                        JOptionPane.showMessageDialog(view, "Appointment canceled.");
//
//                    }catch (Exception ex){
//                        JOptionPane.showMessageDialog(view, ex.getMessage());
//                    }
//                }
//                else {
//                    JOptionPane.showMessageDialog(view, "Please fill out id field");
//                }
//            }
//        }
//        this.view.getCancelAppointmentButton().addActionListener(new CancelAppointmentListener());
//
////        findAppointmentButton.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                String id = JOptionPane.showInputDialog("Enter the id of the appointment to find:");
////                if (id != null) {
////                    appointmentDAO.getAppointmentById(id);
////                    JOptionPane.showMessageDialog(null, "Appointment found");
////                    updatePatientTable();
////                } else {
////                    JOptionPane.showMessageDialog(null, "Please fill out id field");
////                }
////            }
////        });
//        class FindAppointmentListener implements ActionListener {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String patientid = JOptionPane.showInputDialog("Enter the id of the patient");
//                String doctorID = JOptionPane.showInputDialog("Enter the id of the doctor");
//                if (patientid != null && doctorID != null) {
//                    try{
//                        model.findAppointment(patientid,doctorID);
//                        view.updateAppointmentTable();
//
//                    }catch (Exception ex){
//                        JOptionPane.showMessageDialog(view, ex.getMessage());
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(view, "Please fill out id field");
//                }
//            }
//        }
//        this.view.getFindAppointmentButton().addActionListener(new FindAppointmentListener());
//    }//end of constructor

}
