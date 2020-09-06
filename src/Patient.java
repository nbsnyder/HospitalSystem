/**
 * HospitalSystem - Patient.java
 * @author Nathan Snyder
 */

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Base64;

public class Patient extends Person {
	
	// Status of the patient: "in surgery", "discharged", etc.
	private String status;
	
	// Alerts regarding the patient: "fall risk", "risk of harm to others"
	private String alerts;

	// The key that is used to encrypt and decrypt the Patient's confidential data
    private SecretKeySpec keySpec;

    // Reference to the Patient's current Doctor
    private Doctor doctor;
    
    // Reference to the Patient's current Nurse
    private Nurse nurse;
	
	// Constructor for Patient when only an id, name, and department are provided
	public Patient(int id, String name, Department department) {
		super(id, name, department);
		this.status = "unknown";
		this.alerts = "";
		department.addPatient(this);
		setKeySpec();
	}
	
	// Constructor for Patient when a status is also provided
	public Patient(int id, String name, Department department, String status) {
		super(id, name, department);
		this.setStatus(status);
		this.alerts = "";
		department.addPatient(this);
		setKeySpec();
	}
	
	// Constructor for Patient when a status and alerts are also provided
	public Patient(int id, String name, Department department, String status, String alerts) {
		super(id, name, department);
		this.setStatus(status);
		this.setAlerts(alerts);
		department.addPatient(this);
		setKeySpec();
	}
	
	// Returns the Patient's status
	public String getStatus() {
		return this.status;
	}
	
	// Returns the Patient's alerts
	public String getAlerts() {
		return this.alerts;
	}

	// Returns the Patient's current Doctor
	public Doctor getDoctor() {
		return this.doctor;
	}
	
	// Returns the Patient's current Nurse
	public Nurse getNurse() {
		return this.nurse;
	}
	
	// For the JavaFX Table only: returns the Patient's name
	public String getNameForTable() {
		return this.name;
	}
	
	// For the JavaFX Table only: returns the Patient's current Doctor
	public String getDoctorForTable() {
		return this.doctor.getName();
	}
	
	// For the JavaFX Table only: returns the Patient's current Nurse
	public String getNurseForTable() {
		return this.nurse.getName();
	}
	
	// Sets the Patient's status
	public void setStatus(String status) {
		this.status = (status == null) ? "unknown" : status;
	}
	
	// Sets the Patient's alerts
	public void setAlerts(String alerts) {
		this.alerts = (alerts == null) ? "unknown" : alerts;
	}
	
	// Sets the key that will be used for encryption and decryption
	public void setKeySpec() {
		KeyGenerator keyGenerator;
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			SecretKey key = keyGenerator.generateKey();
	        this.keySpec = new SecretKeySpec(key.getEncoded(), "AES");
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}
	
	// Changes the Patient's Doctor
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	// Changes the Patient's Nurse
	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}
	
	// Gives a String representation of a Patient
	@Override
	public String toString() {
		return "ID: " + Integer.toString(this.id) + 
			   "\nName: " + this.name +
			   "\nDepartment: " + this.department.toString() + 
			   "\nStatus: " + this.status + 
			   "\nAlerts: " + this.alerts;
	}
	
	// Discharge a Patient by removing all data
	public void discharge() {
		this.status = null;
		this.alerts = null;
		setDepartment(null);
		this.encryptAndWrite(""); // deletes all Patient data
	}

	// Retrieves one value from a Patient's personal data
	public String getPersonalData(String key) {
		// Get all the data from the file and split it by line into an array of Strings
		String[] data = readAndDecrypt().trim().split("\n");
		
		for (String line : data) {
			// This line prevents the next line from giving an error if lines shorter than the key are present
			if (line.length() < key.length()) continue;
			
			// The format is "key: value"
			else if (line.substring(0, key.length()).equals(key)) // if (key == key)
				return line.substring(key.length() + 2); // return value;
		}
		return null;
	}
	
	// Retrieves one value from a Patient's personal data
	public boolean changePersonalData(String key, String value) {
		// Get all the data from the file and split it by line into an array of Strings
		String[] data = readAndDecrypt().trim().split("\n");
		
		StringBuilder content = new StringBuilder();
		boolean keyHasBeenFound = false;
		
		// Iterates through the lines and copies them into a StringBuilder
		// Changes the line that needs to be changed, but keeps all the other ones the same
		for (String line : data) {
			if (line.length() >= key.length()) {
				if (line.substring(0, key.length()).equals(key)) {
					content.append(key + ": " + value + System.lineSeparator());
					keyHasBeenFound = true;
					continue;
				}
			}
			content.append(line + System.lineSeparator());
		}
		
		// If the key is not in the file, add it
		if (!keyHasBeenFound) {
			content.append(key + ": " + value + System.lineSeparator());
		}

		// Removes the final line separator from the StringBuilder
		content.setLength(content.length() - 1);
		
		return encryptAndWrite(content.toString());
	}
	
	// Encrypts the data using AES encryption and writes the newly-encrypted data to a Patient's text file
	public boolean encryptAndWrite(String s) {
		
		RandomAccessFile file = null;
		FileChannel channel = null;
		FileLock lock = null;
		
		try {
			//Encryption
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, this.keySpec);
			String encryptedText = Base64.getEncoder().encodeToString(cipher.doFinal(s.getBytes("UTF-8")));
			
			// Finds the correct file based on the Patient's id
			file = new RandomAccessFile("src/" + Integer.toString(this.id) + ".txt", "rw");
			
			// Sets up a lock so that no one else can access this file while it is being used here
			channel = file.getChannel();
			lock = channel.tryLock();
			
			// Delete everything that's already in the file and rewrite
			file.setLength(0);
			file.writeBytes(encryptedText);
			
			return true;
		} catch (Exception e) {
			System.out.println("Exception thrown: " + e);
			return false;
		} finally {
			// Try to close everything
			try{lock.release();} catch (Exception e) {}
			try{channel.close();} catch (Exception e) {}
			try{file.close();} catch (Exception e) {}
		}
	}
	
	// Decrypts the data in a Patient's file and returns it as a String
	public String readAndDecrypt() {
		RandomAccessFile file = null;
		FileChannel channel = null;
		FileLock lock = null;
		
		StringBuilder fileData = new StringBuilder();
		
		try {
			// Finds the correct file based on the Patient's id
			file = new RandomAccessFile("src/" + Integer.toString(this.id) + ".txt", "rw");
			
			// Sets up a lock so that no one else can access this file while it is being used here
			channel = file.getChannel();
			lock = channel.tryLock();
			
			// Iterates through every line in the file, appending it to fileData
			String tempString = null;
			while ((tempString = file.readLine()) != null) {
				fileData.append(tempString + System.lineSeparator());
			}
			
			// Removes the last character
			fileData.deleteCharAt(fileData.length()-1);
			
			// Decryption
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			byte[] decryptedText = cipher.doFinal(Base64.getDecoder().decode(fileData.toString()));
			
			return new String(decryptedText);
		} catch (Exception e) {
			System.out.println("Exception thrown: " + e);
			return null;
		} finally {
			// Try to close everything
			try{lock.release();} catch (Exception e) {}
			try{channel.close();} catch (Exception e) {}
			try{file.close();} catch (Exception e) {}
		}
	}
	
}
