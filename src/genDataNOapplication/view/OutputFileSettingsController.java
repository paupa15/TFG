package genDataNOapplication.view;

import java.io.File;
import java.util.Optional;

import genDataNOapplication.Main;
import genDataNOapplication.Utils.FileUtils;
import genDataNOapplication.model.ConfigurationModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class OutputFileSettingsController {
	
	//Reference to the main application
		private Main main;
		
		//Configuration
		protected ConfigurationModel configuration;
		
		//Buttons
		@FXML
		Button inputGeneratorButtonTab;
		@FXML
		Button inputFilesButtonTab;
		@FXML
		Button userParametersButtonTab;
		@FXML
		Button profilesButtonTab;
		@FXML
		Button communitiesButtonTab;
		@FXML
		Button advancedButtonTab;
		@FXML
		Button runButtonTab;
		@FXML
		Button statisticsButton;
		@FXML
		Button statisticsPublicationsButton;
		@FXML
		Button browseOutFileButton;
		@FXML
		Button brwoseOutgFileButton;
		@FXML
		Button browseOut1FileButton;
		@FXML 
		Button browseOut2FileButton;
		@FXML
		Button resetButton;
		@FXML
		Button exportConfigurationButton;
		
		@FXML
		Button ButtonDin1;
		@FXML
		Button ButtonDin2;
		@FXML
		Button ButtonDin3;
		
		@FXML
		Button ButtonSta1;
		@FXML
		Button ButtonSta2;
		@FXML
		Button ButtonSta3;
		
		
		@FXML
		Button helpButton;
		
		//TextFields
		@FXML
		TextField outFileName;
		@FXML
		TextField outgFileName;
		@FXML
		TextField out1FileName;
		@FXML
		TextField out2FileName;
		
		@FXML
		TextField Din1;
		@FXML
		TextField Din2;
		@FXML
		TextField Din3;
		
		@FXML
		TextField Sta1;
		@FXML
		TextField Sta2;
		@FXML
		TextField Sta3;
		
		//Class constructor
		public OutputFileSettingsController() {
			
		}
		
		//Initializes the controller class. This method is automatically called
	    // after the fxml file has been loaded.
		@FXML
		private void initialize() {
			
		}
		
		//Is called by the main application to give a reference back to itself.
		public void setMainApp(Main main) {
			this.main = main;
		}
		
		//Is called to set a specific configuration
		public void setConfiguration(ConfigurationModel configuration) {
			this.configuration = configuration;
			outFileName.setText(configuration.getOutFile());
			outgFileName.setText(configuration.getOutgFile());
			out1FileName.setText(configuration.getOut1File());
			out2FileName.setText(configuration.getOut2File());
			
			Din1.setText(configuration.getDin1());
			Din2.setText(configuration.getDin2());
			Din3.setText(configuration.getDin3());

			
			Sta1.setText(configuration.getSta1());
			Sta2.setText(configuration.getSta2());
			Sta3.setText(configuration.getSta3());
			
		}
		
		//Browse button Handlers
		public void handleOutFileBrowseButton() { handleBrowseButton("outFile"); }
		public void handleOutgFileBrowseButton() { handleBrowseButton("outgFile"); }
		public void handleOut1FileBrowseButton() { handleBrowseButton("out1File"); }
		public void handleOut2FileBrowseButton() { handleBrowseButton("out2File"); }
		
		public void handleDin1() { handleBrowseButton("din1"); }
		public void handleDin2() { handleBrowseButton("din2"); }
		public void handleDin3() { handleBrowseButton("din3"); }
		
		public void handleSta1() { handleBrowseButton("sta1"); }
		public void handleSta2() { handleBrowseButton("sta2"); }
		public void handleSta3() { handleBrowseButton("sta3"); }

		
		//When browse button pressed, a file chooser is opened and when a file is selected its path is writen in the textfield
		private void handleBrowseButton(String field) {
			
	        FileChooser fileChooser = new FileChooser();
	        
	        File initialDirectory = new File("./resources/Output_files");
	        
	        fileChooser.setInitialDirectory(initialDirectory);

	        // Set extension filter
	        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
	                "Archivo de valores separados por comas de Microsoft Excel (.csv)", "*.csv");
	        fileChooser.getExtensionFilters().add(extFilter);

	        // Show save file dialog
	        File file = fileChooser.showSaveDialog(main.getPrimaryStage());

	        if (file != null) {
	            System.out.println(file.getName());
	            String filePath = file.getPath();
	            //String[] parts = filePath.split("(?<=\\\\)");
	           // String filename = parts[parts.length - 1];
	            String filename = filePath;
	            
	            switch(field) {
	            case "outFile":
	            	outFileName.setText(filename);
	            	break;
	            case "outgFile":
	            	outgFileName.setText(filename);
	            	break;
	            case "out1File":
	            	out1FileName.setText(filename);
	            	break;
	            case "out2File":
	            	out2FileName.setText(filename);
	            	break;
	            case "din1":
	            	Din1.setText(filename);
	            	break;
	            case "din2":
	            	Din2.setText(filename);
	            	break;
	            case "din3":
	            	Din3.setText(filename);
	            	break;
	            case "sta1":
	            	Sta1.setText(filename);
	            	break;
	            case "sta2":
	            	Sta2.setText(filename);
	            	break;
	            case "sta3":
	            	Sta3.setText(filename);
	            	break;
	            }
	            
	            
	        }

		}
		
		//Exports the current configuration to a xml file
		@FXML
		public void handleExportConfig() {
			//Saves the current page values to the configuration model
			save();
		
			//Opens a window for the user to choose the save location
	        FileChooser fileChooser = new FileChooser();   
	        File initialDirectory = new File("./config");
	        fileChooser.setInitialDirectory(initialDirectory);
	        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivo de origen XML (.xml)", "*.xml");
	        fileChooser.getExtensionFilters().add(extFilter);

	        //Show save file dialog
	        File file = fileChooser.showSaveDialog(main.getPrimaryStage());
	        
	        //Executes the export process and shows a message to the user depending on the result
	        boolean status = FileUtils.exportConfig(file, configuration);
	        
	        if(status) {
	    		Alert alert = new Alert(AlertType.INFORMATION);
	    		alert.setTitle("Export Successfull");
	    		alert.setHeaderText("The configuration has been successfully saved to file");
	    		alert.setContentText("The configuration has been saved to  " + file.getPath() + " ."
	    				 + "to load this file go to the initial page and click the button Load Config From File.");
	    		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	    		stage.getIcons().add(new Image("file:./resources/icons/info_icon.png"));
	    		alert.showAndWait();
	        }else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Exporting configuration");
				alert.setHeaderText("An error occurred while saving the configuration to a file.");
				alert.setContentText("The configuration couldn't be saved. Check that the directory " + file.getPath()
							+ " is valid and accessible. Try executing the application with Administrator rights or try another location.");
				alert.showAndWait();
	        }

		}
		
		private void save() {	
			if(!outFileName.getText().isEmpty()) { configuration.setOutFile(outFileName.getText()); }
			if(!outgFileName.getText().isEmpty()) { configuration.setOutgFile(outgFileName.getText()); }
			if(!out1FileName.getText().isEmpty()) { configuration.setOut1File(out1FileName.getText()); }
			if(!out2FileName.getText().isEmpty()) { configuration.setOut2File(out2FileName.getText()); }
			
			if(!Din1.getText().isEmpty()) { configuration.setDin1(Din1.getText()); }
			if(!Din2.getText().isEmpty()) { configuration.setDin2(Din2.getText()); }
			if(!Din3.getText().isEmpty()) { configuration.setDin3(Din3.getText()); }
			
			if(!Sta1.getText().isEmpty()) { configuration.setSta1(Sta1.getText()); }
			if(!Sta2.getText().isEmpty()) { configuration.setSta2(Sta2.getText()); }
			if(!Sta3.getText().isEmpty()) { configuration.setSta3(Sta3.getText()); }
			
			main.setConfiguration(configuration);
		}
		
		//Resets the default configuration. Asks user confirmation.
		@FXML
		public void handleResetButton() {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Reset Default");
			alert.setHeaderText("Reset parameters to default");
			alert.setContentText("Are you sure you want to reset all settings parameters to the default configuration?");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			Image icon = new Image("file:./resources/icons/confirmation_icon.png");
			stage.getIcons().add(icon);		
			alert.setGraphic(new ImageView(icon));
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				outFileName.clear();
				outgFileName.clear();
				out1FileName.clear();
				out2FileName.clear();
				Din1.clear();
				Din2.clear();
				Din3.clear();
				Sta1.clear();
				Sta2.clear();
				Sta3.clear();
				
			}

		}
		
		@FXML
		public void handleHelpButton() {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Help");
			alert.setHeaderText("Output Files");
			alert.setContentText("First, the out file is the most important one, as it contains the list of users created and their characteristics,"
					+ " this means the list of attributes with the value assigned to each attribute.\n\n "
					+ "Second, the outg file contains the links between users, this means the list of neighbors of each user and the corresponding link weight.\n\n"
			+ "Thirdly, the out1 file contains the statistics for the graph data: first ALL the graph then for each community in turn.\n\n"
			+ "Fourthly, the out2 file contains a list of communities and the number of users assigned to each.\n\n"
			+ "The rest of files correspond to the interaction part and use the following nomenclature: \n"
			+ "Din = dynamic, Sta=static");
			
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("file:./resources/icons/info_icon.png"));
			Image icon = new Image("file:./resources/icons/info_icon.png");	
			alert.setGraphic(new ImageView(icon));
			alert.showAndWait();
		}
		
		//Handles back button. If something has been modified asks for user confirmation.
		@FXML
		public void handleBackButton() {
			save();
			main.showCommunitiesSettingsPage();
		}
		
		@FXML
		public void handleInputGeneratorButtonTab() {
			save();
			main.showInputFileGeneratorPage();
		}
		
		@FXML
		public void handleInputFileButtonTab() {
			save();
			main.showSettingsPage();
		}
		
		@FXML
		public void handleCommunitiesButtonTab() {
			save();
			main.showCommunitiesSettingsPage();
		}
		
		@FXML
		public void handleUserParametersButtonTab() {
			save();
			main.showUserAttributesPage();
		}
		
		@FXML
		public void handleProfilesButtonTab() {
			save();
			main.showProfilesPage();
		}
		
		@FXML
		public void handleAdvancedButtonTab() {
			save();
			main.showAdvancedSettingsPage();
		}
		
		@FXML
		public void handleRunButtonTab() {
			save();
			main.showRunPage();
		}
		
		@FXML
		public void handleStatisticsButton() {
			main.showStatisticsPage();
		}
		
		@FXML
		public void handleStatisticsPublicationsButton() {
			main.showStatisticsPublicationsPage();
		}

}
