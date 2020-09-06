/**
 * HospitalSystem - Hospital.java
 * @author Nathan Snyder
 */

import java.util.HashMap;
import java.util.HashSet;

public class Hospital {
	
	// A HashMap matching People to their passwords
	private static HashMap<Person, String> credentials = new HashMap<>();
	
	// A HashSet that holds all of the Departments in the Hosptial
	public static HashSet<Department> departments = new HashSet<>();
	
	// Adds a Person-password pair to the credentials HashMap
	public static void addCredentials(Person person, String password) {
		credentials.put(person, password);
	}
	
	// Removes a Person-password pair to the credentials HashMap
	public static void removeCredentials(Person person) {
		credentials.remove(person);
	}
	
	// Tests whether a user's credentials are valid
	public static Person areCredentialsValid(String role, String id, String password) {
		// Cycles through every person of type "role" in every department
		if (role.equals("Patient")) {
			for (Department department : departments) {
				for (Patient patient : department.getPatients()) {
					if (Integer.parseInt(id) == patient.getId())
						return credentials.get(patient).equals(password) ? patient : null;
				}
			}
		} else if (role.equals("Doctor")) {
			for (Department department : departments) {
				for (Doctor doctor : department.getDoctors()) {
					if (Integer.parseInt(id) == doctor.getId())
						return credentials.get(doctor).equals(password) ? doctor : null;
				}
			}
		} else if (role.equals("Nurse")) {
			for (Department department : departments) {
				for (Nurse nurse : department.getNurses()) {
					if (Integer.parseInt(id) == nurse.getId())
						return credentials.get(nurse).equals(password) ? nurse : null;
				}
			}
		}
		
		return null;
	}
}
