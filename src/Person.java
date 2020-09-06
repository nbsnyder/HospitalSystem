/**
 * HospitalSystem - Person.java
 * @author Nathan Snyder
 * 
 * Contains fields and methods used by all people in the hospital, including doctors, nurses, and patients
 */

public class Person {

	// Person's individual id that links to confidential, patient-specific information
	protected final int id;
	
	// The Person's name
	protected final String name;
	
	// The Department that the person is in
	protected Department department;
	
	// Constructor for a Person
	public Person(int id, String name, Department department) {
		this.id = id;
		this.name = name;
		this.department = department;
	}
	
	// Returns the Person's id
	public int getId() {
		return this.id;
	}
	
	// Returns the Person's name
	public String getName() {
		return this.name;
	}

	// Returns the Person's Department
	public Department getDepartment() {
		return this.department;
	}
	
	// Changes the Person's department
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	// Gives a String representation of a Person
	@Override
	public String toString() {
		return "ID: " + Integer.toString(this.id) + 
			   "\nName: " + this.name +
			   "\nDepartment: " + this.department.toString();
	}
	
}
