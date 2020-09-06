/**
 * HospitalSystem - Controller.java
 * @author Nathan Snyder
 * 
 * Controller for the HospitalSystem application:
 *     - Contains all of the functionality of the Login screen
 *     - Launches the main screen and contains all of the functionality for it
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller {
	
	// Contains the current user of the system
	public static Person currentUser;
	
	// If the current user is a Doctor or a Nurse, this links to the patient that they're viewing
	public static Patient currentPatient;
	
	// Hierarchy of elements in the Doctors' and Nurses' main screens
	public static Pane root;
		public static AnchorPane leftControl;
			public static Button logOutButton;
			public static Label selectPatientLabel;
			public static ListView<String> patientList;
			public static Label allPatientsLabel;
			public static TableView<Patient> tableView;
				public static TableColumn<Patient, ?> patientColumn;
				public static TableColumn<Patient, ?> doctorColumn;
				public static TableColumn<Patient, ?> nurseColumn;
		public static AnchorPane rightControl;
			public static Label patientInfoLabel;
			public static TextArea patientInfoText;
			public static Label patientNotesLabel;
			public static TextArea patientNotesText;
			public static Button saveButton;
		public static Separator separator;
	
	// Extra elements for the Patients' main screen
	public static Label helloLabel;
	public static Label doctorLabel;
	public static Label nurseLabel;
	public static Label infoFromDoctorLabel;
	public static TextArea notesArea;
	public static Button payBillButton;
	
	// This method is triggered when the user hits the "Forgot password?" button on the login screen
	public static void forgotPassword(ActionEvent event) {
		
		if (Main.roleInput.getValue() == null) {							// No role was entered
			Main.logInStatusLabel.setText("Please enter a valid role (Patient, Doctor, or Nurse).");
		} else if (Main.idInput.getText().equals("")) {						// No ID was entered
			Main.logInStatusLabel.setText("Please enter a valid ID.");
		} else {															// Both were entered
			Main.logInStatusLabel.setText("If that ID and role exist, an email will be sent with instructions on how to reset the password.");
			// In actual implementation, an email would need to be sent: sendResetPasswordEmail(usernameInput.getText());
		}
	
	}
	
	// This method is triggered when the user hits the "Go!" button on the login screen
	public static void logIn(ActionEvent event) {
		
		if (Main.roleInput.getValue() == null) {							// No role was entered
			Main.logInStatusLabel.setText("Please enter a valid role (Patient, Doctor, or Nurse).");
			return;
		} else if (Main.idInput.getText().equals("")) {						// No ID was entered
			Main.logInStatusLabel.setText("Please enter a valid ID.");
			return;
		} else if (Main.passwordInput.getText().equals("")) {				// No password was entered
			Main.logInStatusLabel.setText("Please enter a valid password.");
			return;
		}
		
		// Sets the currentUser if the user's credentials are valid
		currentUser = Hospital.areCredentialsValid(Main.roleInput.getValue(), Main.idInput.getText(), Main.passwordInput.getText());
		
		if (currentUser == null) {											// The values entered were not valid --> access denied
			Main.logInStatusLabel.setText("Access Denied");
		} else {															// The values entered were valid --> access granted
			accessGranted();
		}
		
	}
	
	// This method is called when the user's credentials match so they are granted access
	private static void accessGranted() {
		
		Main.stage.close();
		Main.stage = new Stage();
		
		// Doctor and Nurse interfaces are almost the same except nurses don't have a notes section
    	if (Main.roleInput.getValue().equals("Patient")) {
    		startPatient();
    	}
    	else if (Main.roleInput.getValue().equals("Doctor")) {
    		startDoctorOnly();
    		startDoctorAndNurse();
    	}
    	else if (Main.roleInput.getValue().equals("Nurse")) {
    		startNurseOnly();
    		startDoctorAndNurse();
    	}
    	
		Scene scene = new Scene(root, 900, 600);
		
		Main.stage.setTitle("HospitalSystem: " + Main.roleInput.getValue() + " Portal");
		Main.stage.setScene(scene);
		Main.stage.setResizable(false);
		Main.stage.show();
		
	}
	
	// Sets up all of the elements on the Patients' portal
	public static void startPatient() {
		
		root = new AnchorPane();
		root.setId("root");
		
		doctorLabel = new Label();
		doctorLabel.setText("Your doctor is: " + ((Patient) currentUser).getDoctor().getName());
		doctorLabel.setLayoutX(20);
		doctorLabel.setLayoutY(240);
		
		nurseLabel = new Label();
		nurseLabel.setText("Your nurse is: " + ((Patient) currentUser).getNurse().getName());
		nurseLabel.setLayoutX(20);
		nurseLabel.setLayoutY(260);
		
		infoFromDoctorLabel = new Label();
		infoFromDoctorLabel.setText("Information from your doctor:");
		infoFromDoctorLabel.setLayoutX(300);
		infoFromDoctorLabel.setLayoutY(20);
		
		logOutButton = new Button();
		logOutButton.setText("Log out");
		logOutButton.setLayoutX(20);
		logOutButton.setLayoutY(20);
		logOutButton.setMnemonicParsing(false);
		logOutButton.setOnAction((event) -> {Controller.logOut(event);});
		
		notesArea = new TextArea();
		notesArea.setText(((Patient) currentUser).getDoctor().getNotes().get((Patient) currentUser));
		notesArea.setLayoutX(300);
		notesArea.setLayoutY(60);
		notesArea.setPrefWidth(600);
		notesArea.setPrefHeight(500);
		notesArea.setWrapText(true);
		notesArea.setEditable(false);
		
		helloLabel = new Label();
		helloLabel.setText("Hello, " + currentUser.getName() + "!");
		helloLabel.setLayoutX(110);
		helloLabel.setLayoutY(180);
		
		payBillButton = new Button();
		payBillButton.setText("Pay Your Bill");
		payBillButton.setLayoutX(100);
		payBillButton.setLayoutY(500);
		
		root.getChildren().add(doctorLabel);
		root.getChildren().add(nurseLabel);
		root.getChildren().add(infoFromDoctorLabel);
		root.getChildren().add(logOutButton);
		root.getChildren().add(notesArea);
		root.getChildren().add(helloLabel);
		root.getChildren().add(payBillButton);
		
	}
	
	// Sets up the elements that are on both the Doctors' and the Nurses' portals
	public static void startDoctorAndNurse() {
		
		// Set up the split-screen layout
		leftControl = new AnchorPane();
		leftControl.setId("leftControl");
        leftControl.setPrefHeight(600);
        leftControl.setPrefWidth(900 * 0.5);
        
		rightControl = new AnchorPane();
		rightControl.setId("rightControl");
        rightControl.setPrefHeight(600);
        rightControl.setPrefWidth(900 * 0.5);
        
		separator = new Separator(Orientation.VERTICAL);

		root = new HBox(leftControl, separator, rightControl);
		root.setId("root");

		
		
		// Left Side:
		logOutButton = new Button();
		logOutButton.setText("Logout");
		logOutButton.setLayoutX(1);
		logOutButton.setLayoutY(1);
		logOutButton.setOnAction((event) -> {Controller.logOut(event);});
		
		selectPatientLabel = new Label();
		selectPatientLabel.setText("Select a patient:");
		selectPatientLabel.setLayoutX(20);
		selectPatientLabel.setPrefWidth(410);
		selectPatientLabel.setLayoutY(48);
		selectPatientLabel.setPrefHeight(40);
		
		patientList.setId("patientList");
		patientList.setLayoutX(20);
		patientList.setLayoutY(88);
		patientList.setPrefWidth(410);
		
		allPatientsLabel = new Label();
		allPatientsLabel.setText("All patients in the " + currentUser.department + " department:");
		allPatientsLabel.setLayoutX(20);
		allPatientsLabel.setLayoutY(patientList.getLayoutY() + 
								    patientList.getPrefHeight() + 20);
		allPatientsLabel.setPrefHeight(40);
		allPatientsLabel.setPrefWidth(410);
    	
		tableView = new TableView<>();
		tableView.setLayoutX(20);
		tableView.setLayoutY(allPatientsLabel.getLayoutY() + 
							 allPatientsLabel.getPrefHeight());
		tableView.setPrefWidth(410);
    	
    	patientColumn = new TableColumn<>("Patient");
    	patientColumn.setCellValueFactory(new PropertyValueFactory<>("nameForTable"));
    	patientColumn.setPrefWidth(410 / 3);
    	
    	doctorColumn = new TableColumn<>("Doctor");
    	doctorColumn.setCellValueFactory(new PropertyValueFactory<>("doctorForTable"));
    	doctorColumn.setPrefWidth(410 / 3);
    	
    	nurseColumn = new TableColumn<>("Nurse");
    	nurseColumn.setCellValueFactory(new PropertyValueFactory<>("nurseForTable"));
    	nurseColumn.setPrefWidth(410 / 3);
    	
    	tableView.getColumns().add(patientColumn);
    	tableView.getColumns().add(doctorColumn);
    	tableView.getColumns().add(nurseColumn);
    	
    	// Adds all of the patients in the department to the table
    	int i = 1;
    	for (Patient patient : currentUser.department.getPatients()) {
    		tableView.getItems().add(patient);
    		i++;
    	}
    	tableView.setPrefHeight((25 * i) + 3);
	    
    	leftControl.getChildren().add(logOutButton);
    	leftControl.getChildren().add(selectPatientLabel);
    	leftControl.getChildren().add(patientList);
    	leftControl.getChildren().add(allPatientsLabel);
    	leftControl.getChildren().add(tableView);
    	
    	
    	
    	// Right Side:
    	patientInfoLabel = new Label();
    	patientInfoLabel.setLayoutX(20);
    	patientInfoLabel.setPrefWidth(410);
    	patientInfoLabel.setLayoutY(20);
    	patientInfoLabel.setPrefHeight(40);

    	patientInfoText.setLayoutX(20);
    	patientInfoText.setPrefWidth(410);
    	patientInfoText.setLayoutY(60);
    	patientInfoText.setWrapText(true);
    	
	}
	
	// Reveals the right portion of the doctor's portal
	public static void openRightControlDoctor(int patientNumber) {
		
		// Don't do anything if the patient number is invalid
		if ((patientNumber < 1) || (patientNumber > ((Doctor) currentUser).getPatients().size()))
			return;
		
		rightControl.getChildren().clear();
		
		currentPatient = (Patient) ((Doctor) currentUser).getPatients().toArray()[patientNumber-1];
		
		patientInfoLabel.setText("Data for " + currentPatient.getName() + ":");
		patientInfoText.setText(currentPatient.readAndDecrypt());
		
		patientNotesLabel.setText("Notes for " + currentPatient.getName() + ":");
		patientNotesText.setText(((Doctor) currentUser).getNotes().get(currentPatient));
		
		rightControl.getChildren().add(patientInfoLabel);
    	rightControl.getChildren().add(patientInfoText);
    	rightControl.getChildren().add(patientNotesLabel);
    	rightControl.getChildren().add(patientNotesText);
    	rightControl.getChildren().add(saveButton);
	}
	
	// Reveals the right portion of the Nurse's portal
	public static void openRightControlNurse(int patientNumber) {
		
		// Don't do anything if the patient number is invalid
		if ((patientNumber < 1) || (patientNumber > ((Nurse) currentUser).getPatients().size()))
			return;
		
		rightControl.getChildren().clear();
		
		currentPatient = (Patient) ((Nurse) currentUser).getPatients().toArray()[patientNumber-1];
		
		patientInfoLabel.setText("Data for " + currentPatient.getName() + ":");
		patientInfoText.setText(currentPatient.readAndDecrypt());
		
		rightControl.getChildren().add(patientInfoLabel);
    	rightControl.getChildren().add(patientInfoText);
	}
	
	// Saves the doctor's changes to the patient's data
	public static void savePatientText(ActionEvent event) {
		// Write the patient's information into an encrypted file
		currentPatient.encryptAndWrite(patientInfoText.getText());
		
		// Save the doctor's notes into a HashMap
		((Doctor) currentUser).addNote(currentPatient, patientNotesText.getText());
	}
	
	// Sets up the elements that are only on the doctors' portal
	public static void startDoctorOnly() {
    	
		patientList = new ListView<>();
		
	    // Add all of the Doctor's patients to the table
		int i = 0;
		for (Patient patient : ((Doctor) currentUser).getPatients()) {
			patientList.getItems().add(patient.getName());
			i += 1;
		}
		
		// Determines the height of the list based on the number of patients
		patientList.setPrefHeight((25 * i) + 3);
		
		// When the doctor clicks on the list, open the right portion of the portal
		patientList.setOnMouseClicked(
		    (EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		            openRightControlDoctor((int) Math.ceil((event.getY() - 3) / 25));
		        }
		    });
		
		
		
		patientInfoText = new TextArea();
		patientInfoText.setPrefHeight(200);
		
		patientNotesLabel = new Label();
    	patientNotesLabel.setLayoutX(20);
    	patientNotesLabel.setPrefWidth(410);
    	patientNotesLabel.setLayoutY(280);
    	patientNotesLabel.setPrefHeight(40);

    	patientNotesText = new TextArea();
    	patientNotesText.setLayoutX(20);
    	patientNotesText.setPrefWidth(410);
    	patientNotesText.setLayoutY(320);
    	patientNotesText.setPrefHeight(200);
    	patientNotesText.setWrapText(true);
    	
    	saveButton = new Button();
    	saveButton.setText("Submit changes");
    	saveButton.setLayoutX(171.75);
    	saveButton.setLayoutY(553);
    	saveButton.setOnAction((event) -> {Controller.savePatientText(event);});
    	
	}
	
	// Sets up the elements that are only on the Nurses' portal
	public static void startNurseOnly() {
		
		patientList = new ListView<>();
		
		// Add all of the Nurse's patients to the table
		int i = 0;
		for (Patient patient : ((Nurse) currentUser).getPatients()) {
			patientList.getItems().add(patient.getName());
			i += 1;
		}
		
		// Determines the height of the list based on the number of patients
		patientList.setPrefHeight((25 * i) + 3);
		
		// When the Nurse clicks on the list, open the right portion of the portal
		patientList.setOnMouseClicked(
		    (EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		            openRightControlNurse((int) Math.ceil(event.getY() / 28));
		        }
		    });
		
		
		
		patientInfoText = new TextArea();
		patientInfoText.setPrefHeight(500);
		
	}
	
	// Logs the user out by closing this screen and reopening the Login screen
	public static void logOut(ActionEvent event) {
		Main.stage.close();
		Main.stage = new Stage();
		
		Main.startLogin();
	}
	
}
