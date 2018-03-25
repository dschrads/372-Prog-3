package gui;

import trial.ClinicalTrial;
import trial.FileAdapter;
import views.*;

/**
 * Driver class having the main method.
 */
public class Driver {

    public static void main(String[] args) {
    	//Models
    	FileAdapter fileAdapter = new FileAdapter();
    	ClinicalTrial clinicalTrial = fileAdapter.loadState();
    	
    	//views
    	MainMenuView mainMenuView = new MainMenuView();
    	AddPatientView addPatientView = new AddPatientView();
    	SystemSettingsView systemSettingsView = new SystemSettingsView(clinicalTrial);
    	DisplayPatientListView displayPatientListView = new DisplayPatientListView(clinicalTrial);
    	DisplayPatientInfoView displayPatientInfoView = new DisplayPatientInfoView();
    	
 
    	
    	GuiController guiController =new GuiController(mainMenuView, addPatientView
    			, systemSettingsView, fileAdapter, clinicalTrial, displayPatientListView, displayPatientInfoView);
    	
    	mainMenuView.setMenuBar(PanelAndFrame.supplyMenuBar(guiController));
    	addPatientView.setMenuBar(PanelAndFrame.supplyMenuBar(guiController));
    	systemSettingsView.setMenuBar(PanelAndFrame.supplyMenuBar(guiController));
    	displayPatientListView.setMenuBar(PanelAndFrame.supplyMenuBar(guiController));
    	mainMenuView.setupFrame(clinicalTrial);
    	guiController.run(mainMenuView);
    }
}

