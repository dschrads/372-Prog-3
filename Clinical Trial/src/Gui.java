/**
 * GGui class to show options to add, show patients list, and get input file from the user.
 */

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Gui extends JPanel implements ActionListener {
	JFrame frame;
	ArrayList<String> patientsID = new ArrayList<>(); //Corrected the name 
	
	//Adding buttons to the GUI interface; these buttons are later called in actionPerformed event.
	JButton buttonAdd = new JButton("Add");
	JButton buttonPatientsList = new JButton("Show Patient info");
	JButton buttonUploadFile = new JButton("Upload File");
	
	
	//Initializing 
	JComboBox comboBoxPatientsIds; 
	JCheckBox checkbox;
	
	JTextField userInputNewPatientID = new JTextField(16); //Corrected the name
	JTextField addPatientState = new JTextField("Click Add button to add new Patient");
	FileHandler fh = new FileHandler();
	
	
	public void mainMenu(){
		if(!patientsID.contains("New patient")){
			patientsID.add("New patient");
		}

		String [] readingTypes = new String[] {"Weight", "Steps", "Temp", "Blood Pressuer"};
		JComboBox comboBoxRadingType = new JComboBox(readingTypes);
		
		JButton buttonAddReading = new JButton("Add");
		
		//Creates labels and user input textFeild 
		JLabel addReading = new JLabel("Add a new reading:");
		JLabel id = new JLabel("PatientID:");
		JLabel date = new JLabel("Date:");
		JLabel type = new JLabel("Type:");
		JLabel value = new JLabel("Value:");
		JTextField valueInput = new JTextField(16);
		JTextField idInput = new JTextField(16);
		JTextField dateInput = new JTextField(16);
		JLabel pastReading = new JLabel("Past readings:");
		JTextField pastReadingDisplay = new JTextField(16);
		
		//Set all labels not editable
		pastReadingDisplay.setEditable(false);
		
		//Create menu components 
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		
		//Add menus to menu bar
		menuBar.add(menu);
		
		//Create and add menuItems to menu
		JMenuItem patientInfo = menu.add("Patient Info");
		JMenuItem manageFile = menu.add("Manage Files");
		
		patientInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		manageFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		comboBoxPatientsIds = new JComboBox(patientsID.toArray());
		
		JButton buttonStartTrail = new JButton("Start Patient Trail");
		
		buttonStartTrail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBoxPatientsIds.getSelectedItem().toString() == "New patient") {
					frame.dispose();
					//addPatient();
				} else {
					if (ClinicalTrial.findPatient(comboBoxPatientsIds.getSelectedItem().toString()).isActive()) {
						JOptionPane.showMessageDialog(null, "This Patient is already active in trail");
					} else {
						ClinicalTrial.findPatient(comboBoxPatientsIds.getSelectedItem().toString()).setActive(true);
						JOptionPane.showMessageDialog(null, "This patient is set to actve and started trial");
					}
				}
			}
		});
		
		//Create JPanels
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		JPanel panel6 = new JPanel();
		JPanel panel7 = new JPanel();
		
		panel1.setLayout(new GridLayout(0, 3, 10, 10));
		panel2.setLayout(new GridLayout(0, 3, 10, 10));
		panel3.setLayout(new GridLayout(0, 3, 10, 10));
		panel4.setLayout(new GridLayout(0, 3, 10, 10));
		panel5.setLayout(new GridLayout(0, 3, 10, 10));
		panel6.setLayout(new GridLayout(0, 3, 10, 10));
		panel7.setLayout(new GridLayout(0, 3, 10, 10));
		
		panel1.add(comboBoxPatientsIds);
		panel1.add(buttonStartTrail);		
		panel2.add(addReading);
		panel3.add(id);
		panel3.add(idInput);
		panel4.add(date);
		panel4.add(dateInput);
		panel5.add(type);
		panel5.add(comboBoxRadingType);
		panel6.add(value);
		panel6.add(valueInput);
		panel7.add(buttonAddReading);
		
		//Frame setup
		frame = new JFrame();
		frame.setLayout(new GridLayout(10, 10, 50, 50));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menuBar);
		
		frame.add(panel1);
		frame.add(panel2);
		frame.add(panel3);
		frame.add(panel4);
		frame.add(panel5);
		frame.add(panel6);
		frame.add(panel7);
		
		frame.pack();
		frame.setLocation(150, 150);
		frame.setVisible(true);
	}
	
	
	
	
	/**
	 * uploadFile gets called from actionPerformed method, gets the input file and passes the filePath to readJsonFile in FileHandler class
	 */
	public void uploadFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(".")); //Opens the file selection dialog at the current project directory
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile(); //Get the file
			String filePath = selectedFile.getAbsolutePath();  //Get the file path
			fh.readJsonFile(filePath); //Pass to FileHandler class method
			
			for (Patient patient : ClinicalTrial.getAllPatients()) {
				patientsID.add(patient.getPatientId());
				frame.dispose();
				displayPatientList();
			}
		}
	}

	public void addPatient() {		
		//Creating textField and set it to read-only
		JTextField inputText = new JTextField("Select the input file");
		inputText.setEditable(false);

		//Creating JPanels
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();

		panel1.setLayout(new GridLayout(0, 3, 10, 10));
		panel2.setLayout(new GridLayout(0, 3, 10, 10));
		
		//Adding the textField and upload button to the panels
		panel1.add(inputText);
		panel2.add(buttonUploadFile);

		buttonAdd.addActionListener(this);
		buttonPatientsList.addActionListener(this);
		buttonUploadFile.addActionListener(this);


	}

	/**
	 * Need clarification if we need this or not?
	 * @param patientID
	 */
	private void addPatientsToTrial(String patientID) {
		Patient patient = new Patient(patientID);
		ClinicalTrial.getAllPatients().add(patient);
	}

	/**
	 * displayPatientList shows the list of patients present in the Clinical Trial 
	 * and gives option to show info or start a trial for a patient
	 */
	public void displayPatientList() {
		comboBoxPatientsIds = new JComboBox(patientsID.toArray()); //Fill the comboBox with patients id
		JTextField textField = new JTextField("Patients List: Select a Patient");
		textField.setEditable(false); //added this to make the textField uneditable
		
		JButton buttonShowInfo = new JButton("Show Patient's Info");
		JButton buttonStartTrial = new JButton("Start Patient Trial");
		JButton buttonExportReadings = new JButton("Export All Readings");
		JButton buttonEndTrial = new JButton("End Patient Trial");
		
		//JPanel for the text
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(0, 3, 10, 10));
		panel1.add(textField);

		//JPanel for the comboBox and button
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(0, 3, 10, 10));
		panel2.add(comboBoxPatientsIds); 
		panel2.add(buttonShowInfo);
		panel2.add(buttonStartTrial);
		panel2.add(buttonEndTrial);
		panel2.add(buttonExportReadings);

		
		//Select a patient id from the dropdown menu and display the corresponding patient info
		buttonShowInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				//Print the info of selected patient id
				displayPatientInfo(ClinicalTrial.getAllPatients().get(comboBoxPatientsIds.getSelectedIndex()).getPatientId());
			};
		});
		
		//Open save dialog box, find filepath of selected file, use gson_lib to write patients to JSON file
		buttonExportReadings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName;
				frame.dispose();
				JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
				int returnValue = jfc.showSaveDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					fileName = jfc.getSelectedFile().getAbsolutePath();
					if(fileName.lastIndexOf(".") != -1) {fileName = fileName.substring(0, fileName.lastIndexOf('.'))+".json";}
					else {fileName = fileName+".json";}
					System.out.println(fileName);

					try {
						Writer writer = new FileWriter(fileName);
						Gson gson = new GsonBuilder().create();
					    gson.toJson(ClinicalTrial.getAllPatients(), writer);	
						writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	
				}
			};
		});
		
		//Start the patient Trial by calling the startPatientTrial() method
		buttonStartTrial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				//Start trial
				startPatientTrial(ClinicalTrial.getAllPatients().get(comboBoxPatientsIds.getSelectedIndex()).getPatientId());
			};
		});
	
		//sets active to false on click, displays confirmation message
		buttonEndTrial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Start trial
				ClinicalTrial.findPatient(ClinicalTrial.getAllPatients().get(comboBoxPatientsIds.getSelectedIndex()).getPatientId()).setActive(false);
				JOptionPane.showMessageDialog(null, "Patient ID: " + ClinicalTrial.getAllPatients().get(comboBoxPatientsIds.getSelectedIndex()).getPatientId()+"\nTrial has ended");
			};
		});
		
		//Create a frame and add the two panels created
		frame = new JFrame();
		frame.setLayout(new GridLayout(5, 1, 10, 10));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel1);
		frame.add(panel2);
		frame.pack();
		frame.setLocation(150, 150);
		frame.setVisible(true);
	}

	/**
	 * displayPatientInfo shows the patient info of the corresponding patient id 
	 * @param selectedPatient patientID of the called patient
	 */
	public void displayPatientInfo(String selectedPatient) {
		JButton buttonBack = new JButton("Back");
		//checkbox = new JCheckBox("Check to Active");
		//checkbox.setSelected(true);
		
		//Two textfields to show the PatientID and the patient info
		JTextField patientID = new JTextField(16);
		JTextField reading = new JTextField();
		reading.setSize(200, 200);

		//set textfields
		patientID.setText("PatientID: " + selectedPatient);
		reading.setText(ClinicalTrial.findPatient(selectedPatient).getReadings().toString());
		patientID.setEditable(false); //Textfield is read-only
		reading.setEditable(false); //Textfield is read-only
		//Create two panels
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(0, 3, 10, 10));
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(0, 3, 10, 10));

		//Add elements to the panels
		panel1.add(patientID);
		//panel1.add(checkbox);
		panel1.add(buttonBack);
		panel2.add(reading);
		
		//Go back to previous menu to show list of patient ids if pressed back button
		buttonBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				displayPatientList();
			}
		});
		
		
		/** This needs to be done inside the START TRIAL MENU. NOT NECESSARILY NEEDED AS AN ACTION as we can set it to active once a patient enters
		 * a trail. !!
		//Set checkbox to either true or false
		checkbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkbox.isSelected()) {
					ClinicalTrial.findPatient(selectedPatient).setActive(true);
				} else {
					ClinicalTrial.findPatient(selectedPatient).setActive(false);
				}
			};
		});
		**/

		//Create the frame and add the panels to it
		frame = new JFrame();
		frame.setLayout(new GridLayout(3, 1, 10, 10));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel1);
		frame.add(panel2);
		frame.pack();
		frame.setLocation(150, 150);
		frame.setVisible(true);
	}

	/**
	 * Starts the patient trial by allowing user to add new reading type and value, and adds them to the patient's reading info
	 * @param selectedPatient
	 */
	public void startPatientTrial(String selectedPatient) {
		ClinicalTrial.findPatient(selectedPatient).setActive(true); //set patient as active during the trial
		
		JButton addReadingButton = new JButton("Add new readings");
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(0, 3, 10, 10));
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(0, 3, 10, 10));
		
		//Added all reading values for new readings
		JTextField readingIdText = new JTextField(16);
		JTextField readingTypeText = new JTextField(16);
		JTextField readingValueText = new JTextField(16);
		JTextField readingDateText = new JTextField(16);
		readingValueText.setSize(200, 200);

		//set textfields.
		readingIdText.setText("Enter readingId");
		readingTypeText.setText("Enter reading type");
		readingValueText.setText("Enter reading value");
		readingDateText.setText("Enter date of reading");
		
		
		
		//Add elements to the panels
		panel1.add(readingIdText);
		panel1.add(readingTypeText);
		panel1.add(readingValueText);
		panel1.add(readingDateText);
		panel2.add(addReadingButton);
		
		addReadingButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
				//Get the new entered values in the textfield
				String readingId = readingIdText.getText();
				System.out.println("New Reading Type: =" + readingId); //DELETE
				String readingType = readingTypeText.getText();
				System.out.println("New Reading Type: =" + readingType); //DELETE
				String readingValue = readingValueText.getText();
				System.out.println("New Reading Value: =" + readingValue); //DELETE
				String readingDate = readingDateText.getText();
				System.out.println("New Reading Type: =" + readingDate); //DELETE
				long date = Long.parseLong(readingDate); //Change date from String to long
				
				
				Patient patient = ClinicalTrial.findPatient(selectedPatient); //Get the Patient
				patient.addNewReadings(readingId, readingType, readingValue, date); //add the new readings to the patient
				
				//***************PLEASE LOOK INTO THIS AND ADD WHATEVER YOU FEEL MIGHT GET US CORRECTLY
				
				System.out.println(patient.getReadings().toString());
				
				JOptionPane.showMessageDialog(null,
						"New Records have been added!!"); //Prompt the user that the new readings are added
				
				displayPatientList(); //GO BACK TO PREVIOUS MENU TO SHOW UPDATED INFO ;; NEED TO DO THIS TO DISPLAY END OF TRIAL WHILE KEEP ON ADDING 
				//NEED TO PASS TO ANOTHER METHOD WHICH WILL HAVE FRAME TO KEEP ADDING NEW RECORDS ALONG WITH END TRIAL BUTTON
			}
		});
		
		//Create the frame and add the panels to it
		frame = new JFrame();
		frame.setLayout(new GridLayout(3, 1, 10, 10));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel1);
		frame.add(panel2);
		frame.pack();
		frame.setLocation(150, 150);
		frame.setVisible(true);		
	}
	/**
	 * actionPerformed gets the user action from the GUI and calls further methods based on user choice.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		/** This condition is NOT-NEEDED as we are not adding any new patient; but we could use codes present in this
		//Condition for Add button
		if (e.getSource() == buttonAdd) {
			String tempId = userInputNewPatientID.getText(); //assign the PatientId that user inputs
			if (ClinicalTrial.findPatient(tempId) == null) {
				addPatientsToTrial(tempId); //add the Patient to trial if it is not in trial
				addPatientState.setText("Patient Added !! Ready for next patient. ");
			} else { //notify if patient is already present in trial
				addPatientState.setText("Patient is already enrolled for the trial.");
			}
		}
		**/

		/** NOT-NEEDED as this is done on the next menu after the FILE is UPLOADED.
		//Condition to show list of Patients
		if (e.getSource() == buttonPatientsList) {
			if (ClinicalTrial.getAllPatients().size() <= 0) { //Notify if no patient in the trial
				JOptionPane.showMessageDialog(null,
						"No patient record in this clinical trial. Please add new patient first!");
			} else { 
				frame.dispose();
				displayPatientList(); //Display the patients list
			}
		}
		**/
		
		//Condition for upload button; call uploadFile method which gets the input file.
		if (e.getSource() == buttonUploadFile) {
			uploadFile();
		}
		
	}
}
