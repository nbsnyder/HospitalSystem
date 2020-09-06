/**
 * HospitalSystem - Main.java
 * @author Nathan Snyder
 * 
 * Starting point for the HospitalSystem application:
 *     - Sets up the demo in the main method
 *     - Launches the Login screen
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.lang.String;

public class Main extends Application {

	// Reusable stage that is used for login screen and the main screens
	public static Stage stage;
	
	// Elements on the Login screen
	public static AnchorPane logInScreen;
	public static Label logInLabel;
	public static Label roleLabel;
	public static Label idLabel;
	public static Label passwordLabel;
	public static ChoiceBox<String> roleInput;
	public static TextField idInput;
	public static PasswordField passwordInput;
	public static Button forgotPasswordButton;
	public static Button goButton;
	public static Label logInStatusLabel;
	
	// Sets up an example of a hospital's Radiology department as a demo
    public static void main(String[] args) {
    	
    	Department Radiology = new Department("Radiology");
    	Hospital.departments.add(Radiology);
    	
    	
    	
    	Doctor doctor6 = new Doctor(6, "Debbie Lambert", Radiology);
    	Hospital.addCredentials(doctor6, "password123");
    	
    	Doctor doctor7 = new Doctor(7, "Waleed Smyth", Radiology);
    	Hospital.addCredentials(doctor7, "password123");
    	
    	
    	
    	Nurse nurse8 = new Nurse(8, "Shannan Barker", Radiology);
    	Hospital.addCredentials(nurse8, "password123");
    	
    	Nurse nurse9 = new Nurse(9, "Ellis Peel", Radiology);
    	Hospital.addCredentials(nurse9, "password123");
    	
    	Nurse nurse10 = new Nurse(10, "Danika Luna", Radiology);
    	Hospital.addCredentials(nurse10, "password123");
    	
    	
    	
    	Patient patient1 = new Patient(1, "Ffion McArthur", Radiology);
    	Hospital.addCredentials(patient1, "password123");
    	
    	Patient patient2 = new Patient(2, "Ricardo Bonilla", Radiology);
    	Hospital.addCredentials(patient2, "password123");
    	
    	Patient patient3 = new Patient(3, "Raheem Akhtar", Radiology);
    	Hospital.addCredentials(patient3, "password123");
    	
    	Patient patient4 = new Patient(4, "Alexander Roberts", Radiology);
    	Hospital.addCredentials(patient4, "password123");
    	
    	Patient patient5 = new Patient(5, "Courtney Cameron", Radiology);
    	Hospital.addCredentials(patient5, "password123");
    	
    	
    	
    	patient1.encryptAndWrite(
        		"Age: 25" + System.lineSeparator() + 
        	    "Weight: 130" + System.lineSeparator() + 
        		"Date of birth: 05/24/1995" + System.lineSeparator() + 
        		"Blood type: A-" + System.lineSeparator() + 
        		"Allergies: none" + System.lineSeparator() + 
        		"Past surgical procedures: none" + System.lineSeparator() + 
        		"Illnesses and chronic diseases: diabetes" + System.lineSeparator() + 
        		"Family medical history: history of heart problems");
    	
    	patient2.encryptAndWrite(
        		"Age: 52" + System.lineSeparator() + 
        	    "Weight: 175" + System.lineSeparator() + 
        		"Date of birth: 02/11/1968" + System.lineSeparator() + 
        		"Blood type: O+" + System.lineSeparator() + 
        		"Allergies: Latex, Bees" + System.lineSeparator() + 
        		"Past surgical procedures: none" + System.lineSeparator() + 
        		"Illnesses and chronic diseases: none" + System.lineSeparator() + 
        		"Family medical history: thyroid problems on father's side");
    	
    	patient3.encryptAndWrite(
        		"Age: 37" + System.lineSeparator() + 
        	    "Weight: 143" + System.lineSeparator() + 
        		"Date of birth: 06/30/1983" + System.lineSeparator() + 
        		"Blood type: B-" + System.lineSeparator() + 
        		"Allergies: none" + System.lineSeparator() + 
        		"Past surgical procedures: endoscopy in 1995" + System.lineSeparator() + 
        		"Illnesses and chronic diseases: none" + System.lineSeparator() + 
        		"Family medical history: none");

    	patient4.encryptAndWrite(
        		"Age: 34" + System.lineSeparator() + 
        	    "Weight: 165" + System.lineSeparator() + 
        		"Date of birth: 04/03/1986" + System.lineSeparator() + 
        		"Blood type: A+" + System.lineSeparator() + 
        		"Allergies: Pollen, bananas" + System.lineSeparator() + 
        		"Past surgical procedures: none" + System.lineSeparator() + 
        		"Illnesses and chronic diseases: none" + System.lineSeparator() + 
        		"Family medical history: history of heart problems");

    	patient5.encryptAndWrite(
        		"Age: 28" + System.lineSeparator() + 
        	    "Weight: 125" + System.lineSeparator() + 
        		"Date of birth: 05/13/1992" + System.lineSeparator() + 
        		"Blood type: O-" + System.lineSeparator() + 
        		"Allergies: none" + System.lineSeparator() + 
        		"Past surgical procedures: none" + System.lineSeparator() + 
        		"Illnesses and chronic diseases: none" + System.lineSeparator() + 
        		"Family medical history: none");

    	
    	
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
    	Main.stage = stage;
    	startLogin();
    }
    
    // Sets up the Login screen and launches it
    public static void startLogin() {
    	
    	logInScreen = new AnchorPane();
    	logInScreen.setId("logInScreen");
    	
    	logInLabel = new Label();
    	logInLabel.setId("logInLabel");
    	logInLabel.setText("Log in");
    	logInLabel.setLayoutX(0);
    	logInLabel.setLayoutY(0);
    	logInLabel.setPrefHeight(80);
    	logInLabel.setPrefWidth(300);
		
		roleLabel = new Label();
		roleLabel.setId("roleLabel");
		roleLabel.setText("I am a:");
		roleLabel.setLayoutX(20);
		roleLabel.setLayoutY(80);
		roleLabel.setPrefHeight(40);
		roleLabel.setPrefWidth(70);
    	
		idLabel = new Label();
		idLabel.setId("idLabel");
		idLabel.setText("ID:");
		idLabel.setLayoutX(20);
		idLabel.setLayoutY(120);
		idLabel.setPrefHeight(40);
		idLabel.setPrefWidth(70);
		
		passwordLabel = new Label();
		passwordLabel.setId("passwordLabel");
		passwordLabel.setText("Password:");
		passwordLabel.setLayoutX(20);
		passwordLabel.setLayoutY(160);
		passwordLabel.setPrefHeight(40);
		passwordLabel.setPrefWidth(70);
		
		roleInput = new ChoiceBox<>();
		roleInput.setId("roleInput");
		roleInput.getItems().add("Patient");
		roleInput.getItems().add("Doctor");
		roleInput.getItems().add("Nurse");
		roleInput.setLayoutX(90);
		roleInput.setLayoutY(80);
		roleInput.setPrefHeight(30);
		roleInput.setPrefWidth(190);
		
		idInput = new TextField();
		idInput.setId("idInput");
		idInput.setPromptText("Demo: IDs are numbers 1-10");
		idInput.setLayoutX(90);
		idInput.setLayoutY(120);
		idInput.setPrefHeight(30);
		idInput.setPrefWidth(190);
		
		passwordInput = new PasswordField();
		passwordInput.setId("passwordInput");
		passwordInput.setText("password123");
		passwordInput.setLayoutX(90);
		passwordInput.setLayoutY(160);
		passwordInput.setPrefHeight(30);
		passwordInput.setPrefWidth(190);
		
		forgotPasswordButton = new Button();
		forgotPasswordButton.setId("forgotPasswordButton");
		forgotPasswordButton.setText("Forgot password?");
		forgotPasswordButton.setLayoutX(90);
		forgotPasswordButton.setLayoutY(200);
		forgotPasswordButton.setPrefHeight(20);
		forgotPasswordButton.setPrefWidth(120);
		forgotPasswordButton.setOnAction((event) -> {Controller.forgotPassword(event);});
		forgotPasswordButton.setMnemonicParsing(false);
		
		goButton = new Button();
		goButton.setId("goButton");
		goButton.setText("Go!");
		goButton.setLayoutX(90);
		goButton.setLayoutY(230);
		goButton.setPrefHeight(40);
		goButton.setPrefWidth(120);
		goButton.setOnAction((event) -> {Controller.logIn(event);});
		goButton.setMnemonicParsing(false);
		
		logInStatusLabel = new Label();
		logInStatusLabel.setId("logInStatusLabel");
		logInStatusLabel.setText("");
		logInStatusLabel.setLayoutX(20);
		logInStatusLabel.setLayoutY(285);
		logInStatusLabel.setPrefHeight(40);
		logInStatusLabel.setPrefWidth(260);
		
    	logInScreen.getChildren().add(logInLabel);
    	logInScreen.getChildren().add(roleLabel);
    	logInScreen.getChildren().add(idLabel);
    	logInScreen.getChildren().add(passwordLabel);
    	logInScreen.getChildren().add(roleInput);
    	logInScreen.getChildren().add(idInput);
    	logInScreen.getChildren().add(passwordInput);
    	logInScreen.getChildren().add(forgotPasswordButton);
    	logInScreen.getChildren().add(goButton);
    	logInScreen.getChildren().add(logInStatusLabel);
    	
    	logInScreen.getStylesheets().add(Main.class.getResource("LoginStyle.css").toExternalForm());
    	
    	Main.stage.setScene(new Scene(logInScreen, 300, 340));
    	Main.stage.setResizable(false);
    	Main.stage.setTitle("HospitalSystem: Login");
    	Main.stage.show();
    }
    
}
