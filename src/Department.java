/**
 * HospitalSystem - Department.java
 * @author Nathan Snyder
 */

import java.util.HashSet;
import java.util.HashMap;

public class Department {

	// Contains the name of the Department
	private String name;
	
	// Sets that contain all of the patients, doctors, and nurses in the Department
	private HashSet<Patient> patients;
	private HashSet<Doctor> doctors;
	private HashSet<Nurse> nurses;
	
	// Map containing the doctors' and nurses' assignments
	private HashMap<Patient, Person[]> assignments;
	
	// Constructor for the Department class
	public Department(String name) {
		this.name = name;
		this.patients = new HashSet<Patient>();
		this.doctors = new HashSet<Doctor>();
		this.nurses = new HashSet<Nurse>();
		this.assignments = new HashMap<Patient, Person[]>(); // values: [Doctor, Nurse]
		
		Hospital.departments.add(this);
	}
	
	// Returns the name of the Department
	public String getName() {
		return this.name;
	}
	
	// Returns a HashSet of Patients in the department
	public HashSet<Patient> getPatients() {
		return this.patients;
	}
		
	// Returns a HashSet of Doctors in the department
	public HashSet<Doctor> getDoctors() {
		return this.doctors;
	}
	
	// Returns a HashSet of Nurses in the department
	public HashSet<Nurse> getNurses() {
		return this.nurses;
	}
	
	// Returns a HashMap of all of the assignments of Doctors and Nurses to Patients
	public HashMap<Patient, Person[]> getAssignments() {
		return this.assignments;
	}
	
	// Adds a Patient to the HashSet of Patients
	public void addPatient(Patient patient) {
		this.patients.add(patient);
		assignDoctorTo(patient);
		assignNurseTo(patient);
	}
	
	// Removes a Patient from the HashSet of Patients
	public void removePatient(Patient patient) {
		this.patients.remove(patient);
		((Doctor) this.assignments.get(patient)[0]).removePatient(patient);
		((Nurse) this.assignments.get(patient)[1]).removePatient(patient);
		this.assignments.remove(patient);
	}
	
	// Adds a Doctor to the HashSet of Doctors
	public void addDoctor(Doctor x) {
		this.doctors.add(x);
	}
		
	// Removes a Doctor from the HashSet of Doctors
	public void removeDoctor(Doctor doctor) {
		this.doctors.remove(doctor);
		
		for (Patient patient : this.assignments.keySet()) {
			if (this.assignments.get(patient)[1] == doctor) {
				patient.setDoctor(null);
				this.assignments.put(patient, new Person[] {null, this.assignments.get(patient)[1]});
				assignDoctorTo(patient);
			}
		}
	}
	
	// Adds a Nurse to the HashSet of Nurses
	public void addNurse(Nurse x) {
		this.nurses.add(x);
	}
		
	// Removes a Nurse from the HashSet of Nurses
	public void removeNurse(Nurse nurse) {
		this.nurses.remove(nurse);
		
		for (Patient patient : this.assignments.keySet()) {
			if (this.assignments.get(patient)[1] == nurse) {
				patient.setNurse(null);
				this.assignments.put(patient, new Person[] {this.assignments.get(patient)[0], null});
				assignNurseTo(patient);
			}
		}
	}
	
	// toString method
	@Override
	public String toString() {
		return this.name;
	}
	
	// Returns the Doctor with the fewest patients
	public Doctor doctorWithFewestPatients() {
		Doctor fewestPatients = null;
		
		for (Doctor doctor : doctors) {
			if (fewestPatients == null)
				fewestPatients = doctor;
			else if (doctor.getPatients().size() < fewestPatients.getPatients().size())
				fewestPatients = doctor;
		}
		
		return fewestPatients;
	}
	
	// Returns the Nurse with the fewest patients
	public Nurse nurseWithFewestPatients() {
		Nurse fewestPatients = null;
		
		for (Nurse nurse : nurses) {
			if (fewestPatients == null)
				fewestPatients = nurse;
			else if (nurse.getPatients().size() < fewestPatients.getPatients().size())
				fewestPatients = nurse;
		}
		
		return fewestPatients;
	}
	
	// Assigns a Doctor to a specified patient in the Department
	private void assignDoctorTo(Patient patient) {
		Doctor fewestPatientsDoctor = doctorWithFewestPatients();
		
		fewestPatientsDoctor.addPatient(patient);
		patient.setDoctor(fewestPatientsDoctor);
		
		if (this.assignments.containsKey(patient))
			this.assignments.put(patient, new Person[] {fewestPatientsDoctor, this.assignments.get(patient)[1]});
		else
			this.assignments.put(patient, new Person[] {fewestPatientsDoctor, null});
	}
	
	// Assigns a Nurse to a specified patient in the Department
	private void assignNurseTo(Patient patient) {
		Nurse fewestPatientsNurse = nurseWithFewestPatients();
		
		fewestPatientsNurse.addPatient(patient);
		patient.setNurse(fewestPatientsNurse);
		
		if (this.assignments.containsKey(patient))
			this.assignments.put(patient, new Person[] {this.assignments.get(patient)[0], fewestPatientsNurse});
		else
			this.assignments.put(patient, new Person[] {null, fewestPatientsNurse});
	}

}
