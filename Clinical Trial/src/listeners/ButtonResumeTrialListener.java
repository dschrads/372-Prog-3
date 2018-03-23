package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import gui.GuiController;

public class ButtonResumeTrialListener implements ActionListener{
	GuiController guiController; //Initialize
	
	/**
	 * Constructor 
	 * @param guiController the controller of the GUI components
	 */
	public ButtonResumeTrialListener(GuiController guiController){
		this.guiController = guiController;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		try { // Handle the exception if no patient is selected to
			// resume the trial.
		guiController.getClinicalTrial().findPatient(
				guiController.getClinicalTrial().getAllPatients().get(guiController.getDisplayPatientListView().getComboBoxPatientsIds().getSelectedIndex()).getPatientId())
				.setActive(true);
		JOptionPane.showMessageDialog(null, "Patient ID: "
				+ guiController.getClinicalTrial().getAllPatients().get(guiController.getDisplayPatientListView().getComboBoxPatientsIds().getSelectedIndex()).getPatientId()
				+ "\nTrial has been activated");
	} catch (ArrayIndexOutOfBoundsException ex) {
		JOptionPane.showMessageDialog(null,
				"Please select a patient from the list. Add a patient if list is empty.");
		//displayPatientList(); // Go back to the display frame again
	}
	}
	
}
