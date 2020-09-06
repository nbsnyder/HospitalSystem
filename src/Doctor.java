/**
 * HospitalSystem - Doctor.java
 * @author Nathan Snyder
 */

import java.util.HashSet;
import java.util.HashMap;

public class Doctor extends Person {

	// String that includes a doctor's specialty (if they have one)
	private String specialty;
	
	// HashSet containing all of the patients that the Doctor currently has
	private HashSet<Patient> patients;
	
	// A HashMap that records all of the Doctor's notes
	private HashMap<Patient, String> notes;
	
	// Constructor for Doctor when only an id, name, and department are provided
	public Doctor(int id, String name, Department department) {
		super(id, name, department);
		this.specialty = "";
		patients = new HashSet<Patient>();
		notes = new HashMap<Patient, String>();
		department.addDoctor(this);
	}
	
	// Constructor for Patient when a specialty is also provided
	public Doctor(int id, String name, Department department, String specialty) {
		super(id, name, department);
		this.setSpecialty(specialty);
		patients = new HashSet<Patient>();
		notes = new HashMap<Patient, String>();
		department.addDoctor(this);
	}
		
	// Returns the Doctor's specialty
	public String getSpecialty() {
		return this.specialty;
	}
	
	// Changes the Doctor's specialty
	public void setSpecialty(String specialty) {
		this.specialty = (specialty == null) ? "" : specialty;
	}
	
	// Returns a HashSet of the Doctor's patients
	public HashSet<Patient> getPatients() {
		return this.patients;
	}
	
	// Adds a patient to the Doctor's HashSet of patients
	public boolean addPatient(Patient patient) {
		return this.patients.add(patient);
	}
		
	// Removes a patient from the Doctor's HashSet of patients
	public boolean removePatient(Patient patient) {
		return this.patients.remove(patient);
	}
	
	// Returns a HashMap of the Doctor's notes
	public HashMap<Patient, String> getNotes() {
		return this.notes;
	}
	
	// Adds a note to the Doctor's HashMap of notes
	public void addNote(Patient patient, String note) {
		this.notes.put(patient, note);
	}
		
	// Removes a patient from the Doctor's HashSet of patients
	public void removeNote(Patient patient) {
		this.notes.remove(patient);
	}
	
	// Gives a String representation of a Doctor
	@Override
	public String toString() {
		return "ID: " + Integer.toString(this.id) + 
			   "\nName: " + this.name +
			   "\nDepartment: " + this.department.toString() + 
			   "\nSpecialty: " + this.specialty;
	}

}
