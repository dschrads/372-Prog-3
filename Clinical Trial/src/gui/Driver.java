package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import trial.ClinicalTrial;
import trial.FileAdapter;
import views.*;

/**
 * Driver class having the main method.
 */
public class Driver {

    public static void main(String[] args) {
    	//Models
    	ClinicalTrial clinicalTrial = new ClinicalTrial();
    	FileAdapter fileAdapter = new FileAdapter();
    	
    	//views
    	MainMenuView mainMenuView = new MainMenuView();
    	AddPatientView addPatientView = new AddPatientView();
    	ManageFileView manageFileView = new ManageFileView();
    	DisplayPatientListView displayPatientListView = new DisplayPatientListView();
    	DisplayPatientInfoView displayPatientInfoView = new DisplayPatientInfoView();
    	
 
    	
    	GuiController guiController =new GuiController(mainMenuView, addPatientView
    			, manageFileView, fileAdapter, clinicalTrial, displayPatientListView, displayPatientInfoView);
    	
    	mainMenuView.setMenuBar(PanelAndFrame.supplyMenuBar(guiController));
    	addPatientView.setMenuBar(PanelAndFrame.supplyMenuBar(guiController));
    	manageFileView.setMenuBar(PanelAndFrame.supplyMenuBar(guiController));
    	displayPatientListView.setMenuBar(PanelAndFrame.supplyMenuBar(guiController));
    	mainMenuView.setupFrame(clinicalTrial);
    	guiController.run(mainMenuView);
    }
}

