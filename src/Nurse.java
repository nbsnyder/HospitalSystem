/**
 * HospitalSystem - Nurse.java
 * @author Nathan Snyder
 */

import java.util.HashSet;

public class Nurse extends Person {
	
	// HashSet containing all of the patients that the nurse currently has
	private HashSet<Patient> patients;
	
	// Constructor for Nurse when only an id, name, and department are provided
	public Nurse(int id, String name, Department department) {
		super(id, name, department);
		patients = new HashSet<Patient>();
		department.addNurse(this);
	}

	// Returns a HashSet of the Nurse's patients
	public HashSet<Patient> getPatients() {
		return this.patients;
	}
		
	// Adds a patient to the Nurse's HashSet of patients
	public boolean addPatient(Patient x) {
		return this.patients.add(x);
	}
		
	// Removes a patient from the Nurse's HashSet of patients
	public boolean removePatient(Patient x) {
		return this.patients.remove(x);
	}
	
}
