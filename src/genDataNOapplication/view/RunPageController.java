package genDataNOapplication.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import genDataNOapplication.Controller;
import genDataNOapplication.Main;
import genDataNOapplication.model.ConfigurationModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class RunPageController {
	
	//Reference to the main application
	private Main main;
	
	//Configuration
	protected ConfigurationModel configuration;
	
	@FXML
	ButtonBar buttonsBar;
	
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
		Button outputFilesButtonTab;
		@FXML
		Button advancedButtonTab;
		@FXML
		Button runButton1;
		@FXML
		Button runButton2;
		@FXML
		Button runButton3;
		@FXML
		Button cancelButton;
		@FXML
		Button homePageButton;
		@FXML
		Button statisticsButton;
		@FXML
		Button statisticsPublicationsButton;
		@FXML
		Button openButton;
		@FXML
		Button helpButton;
		
	//Progress bar
		@FXML
		ProgressBar progressIndicator;
		
	//TextField	
		@FXML
		TextField
		numPub,
		numTime,
		maxTagged,	
		alpha,
		beta,
		mu, 
		qualRate,
		typeRate,
		video_prob,
		image_prob,
		text_prob,
		music,
		drink,
		tv,
		sports,
		animals,
		computer,
		news,
		food,
		travels,
		penalty2,
		penalty3,
		comm_pen,
		share_pen,
		image_weight,
		video_weight,
		text_weight,
		min_score;
		
		
		//Radio buttons
		@FXML
		RadioButton automatic;
		@FXML
		RadioButton manual;
		ToggleGroup configureButton = new ToggleGroup();
		
	private Controller programExecution;
	
	
	public RunPageController() {
		
	}
	
	@FXML
	public void initialize() {
			
		automatic.setToggleGroup(configureButton);
		automatic.setSelected(true);
		manual.setToggleGroup(configureButton);
		configureButton.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
	           public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
					
	               // Has selection.
	               if (configureButton.getSelectedToggle() != null) {
	            	   
	                   RadioButton button = (RadioButton) configureButton.getSelectedToggle();
	                   //System.out.println("Button: " + button.getText());
	                   switch(button.getText()) {
	                   case "Automatic":
	                	   configuration.setModeAM(0);
	                	   System.out.println("Mode automatic");
	                	   break;
	                   case "Manual":
	                	   configuration.setModeAM(1);
	                	   System.out.println("Mode manual");
	                	   break;
	                   }
	               }
	           }
		}); 
		
	}
	
	
	//Is called by the main application to give a reference back to itself.
	public void setMainApp(Main main) {
		this.main = main;
	}
	
	//Is called to set a specific configuration
	public void setConfiguration(ConfigurationModel configuration) {
		this.configuration = configuration;
		
		switch(this.configuration.getModeAM())
        {
	        case 0:
	     	   configureButton.selectToggle(automatic);
	     	   break;
	        case 1:
	        	configureButton.selectToggle(manual);
	        	break;
        }
		
	}
	
	@FXML
	public int handleParametersButton() {
		int numP, numT, maxT;
		double a, b, m, type, quality, vid, ima, tex,vid_w, ima_w, tex_w, mus, dri, tvs, spo, ani, com, neww, foo, tra, pen2, pen3, com_p, share_p, min_s;
		// Check input is parseable
				try {
					numP = Integer.parseInt(numPub.getText());
					numT = Integer.parseInt(numTime.getText());
					maxT = Integer.parseInt(maxTagged.getText());
					
			        a = Double.parseDouble(alpha.getText());
			        b = Double.parseDouble(beta.getText());
			        m = Double.parseDouble(mu.getText());
			        type = Double.parseDouble(typeRate.getText());
			        quality = Double.parseDouble(qualRate.getText());
			        
			        vid = Double.parseDouble(video_prob.getText());
			        ima = Double.parseDouble(image_prob.getText());
			        tex = Double.parseDouble(text_prob.getText());
			        
			        vid_w = Double.parseDouble(video_prob.getText());
			        ima_w = Double.parseDouble(image_prob.getText());
			        tex_w = Double.parseDouble(text_prob.getText());
			        
			        com_p = Double.parseDouble(comm_pen.getText());
			        share_p = Double.parseDouble(share_pen.getText());
			        min_s = Double.parseDouble(min_score.getText());
			        
			        mus = Double.parseDouble(music.getText());
			        dri = Double.parseDouble(drink.getText());
			        tvs = Double.parseDouble(tv.getText());
			        spo = Double.parseDouble(sports.getText());
			        ani = Double.parseDouble(animals.getText());
			        com = Double.parseDouble(computer.getText());
			        neww = Double.parseDouble(news.getText());
			        foo = Double.parseDouble(food.getText());
			        tra = Double.parseDouble(travels.getText());
			        
			        pen2 = Double.parseDouble(penalty2.getText());
			        pen3 = Double.parseDouble(penalty3.getText());
			        
			        
				}catch (Exception e) {
					Alert alarm = new Alert(Alert.AlertType.ERROR);
					alarm.setTitle("Error");
					alarm.setHeaderText("Invalid input");
					alarm.setContentText("Some input value for the configuration of dynamic version is invalid");
					alarm.showAndWait();
					e.printStackTrace();
					return 0;
				}

				// Check conditions are met
				String conditonsAlertText = "";
				if(numP<1) conditonsAlertText += "Number of publications cannot be negative or 0" + "\n"; 
				if(numT<1) conditonsAlertText += "Number of time cannot be negative or 0" + "\n"; 
				if(maxT <0) conditonsAlertText += "Number of Tagged cannot be negative" + "\n"; 
				
				this.configuration.setNumPublications(numP);
				this.configuration.setNumTime(numT);
				this.configuration.setMaxTagged(maxT);
				
				if(Math.abs(a+b+m+type+quality-1) > 0.1)	conditonsAlertText += "Probabilities configuration sum must be 1" + "\n";
				
				this.configuration.setAlpha(a);
				this.configuration.setBeta(b);
				this.configuration.setMu(m);
				this.configuration.setTypeRate(type);
				this.configuration.setQualRate(quality);
				
				
				if(Math.abs(mus+ dri+ tvs+ spo+ ani+ com+ neww+ foo+ tra-1) > 0.1)	conditonsAlertText += "Topics of publications sum must be 1" + "\n";
				else {
					List<Double> topics = Arrays.asList(ani, com, dri, foo, mus, neww, spo, tra, tvs);	
					this.configuration.setTopicList(topics);
				}
				
				if(Math.abs(vid+ima+tex-1) > 0.1)	conditonsAlertText += "Video + image + text sum must be 1" + "\n";
				else {
					this.configuration.setVideo(vid);
					this.configuration.setImage(ima);
					this.configuration.setText(tex);
				}
				
				if((vid_w>1) | (ima_w>1) | (tex_w>1)| (vid_w<0) | (ima_w<0) | (tex_w<0))	conditonsAlertText += "Video, image and text must be values between 0 and 1" + "\n";
				else {
					this.configuration.setVideoWeight(vid_w);
					this.configuration.setImageWeight(ima_w);
					this.configuration.setTextWeight(tex_w);
				}
				
				if((com_p>1) | (share_p>1) | (min_s>1)| (com_p<0) | (share_p<0) | (min_s<0))	conditonsAlertText += "Minimum score, commentary penalty and share penalty must be values between 0 and 1" + "\n";
				else {
					this.configuration.setCommmentPenalty(com_p);
					this.configuration.setSharePenalty(share_p);
					this.configuration.setMinScore(min_s);
				}
				
				
				if(pen2<0 && pen2>1) conditonsAlertText += "Penalty 2 should be a value between 0 and 1" + "\n";
				this.configuration.setPenalty2(pen2);
				this.configuration.setPenalty3(pen3);
				
				if(conditonsAlertText != "")
				{
					Alert alarm = new Alert(Alert.AlertType.ERROR);
					alarm.setTitle("Error");
					alarm.setHeaderText("Invalid input");
					alarm.setContentText(conditonsAlertText);
					alarm.showAndWait();
					return 0;
				}
				
				return 1;
				
	}
	
	@FXML
	public void handleHelpButton() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Help");
		alert.setHeaderText("Output Files");
		alert.setContentText("You have 3 options:\r\n"
				+ "1- Generate the data without interactions.\r\n"
				+ "2- Generate the data and use the static mode to produce interactions.\r\n"
				+ "3- Generate the data and use the dynamic mode to produce interactions.\r\n"
				+ "Also, you can configure the parameters that you see to calculate the score and change the way that data is propagated.\r\n"
				+ "The fields of sections: \"Penalties\", \"probabilities configuration\", and the specific fields \"name of time\" and \"Max users tagged\" are only used on Dynamic Interaction Mode.");
		
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("file:./resources/icons/info_icon.png"));
		Image icon = new Image("file:./resources/icons/info_icon.png");	
		alert.setGraphic(new ImageView(icon));
		alert.showAndWait();
	}
	
	//Stops the execution and re enables the buttons
	@FXML
	private void handleCancelButton() {
		programExecution.cancel(true);
		progressIndicator.setVisible(false);
		
		runButton1.setDisable(false);
		runButton2.setDisable(false);
		runButton3.setDisable(false);
		buttonsBar.setDisable(false);;
    	cancelButton.setVisible(false);
    	statisticsButton.setDisable(false);
    	statisticsPublicationsButton.setDisable(false);
    	homePageButton.setVisible(true);
    	openButton.setVisible(true);
	}
	
	@FXML
	public void handleStartApplicationButton_ni() {
		main.setConfiguration(configuration);
		startApplication("graph");
	}
	
	@FXML
	public void handleStartApplicationButton_sv() {
		if (handleParametersButton() == 1) {
			main.setConfiguration(configuration);
			startApplication("sv");
		}
	}
	
	@FXML
	public void handleStartApplicationButton_dv() {
		if (handleParametersButton() == 1) {
			main.setConfiguration(configuration);
			startApplication("dv");
		}
	}
	
	
	@FXML
	public void handleOpenButton() {
		try {
		File file = new File(configuration.getOutFile());
		String parent = file.getParent();
		File outDirectory = new File(parent);
		Desktop.getDesktop().open((outDirectory));
		} catch (IOException e) {
			 //TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Given a ConfigurationModel, starts the program with this configuration.
	@FXML
	public void startApplication(String mode) {
		try{
			runButton1.setDisable(true);
			runButton2.setDisable(true);
			runButton3.setDisable(true);
			buttonsBar.setDisable(true);
			cancelButton.setVisible(true);
			homePageButton.setVisible(false);
			statisticsButton.setDisable(true);
			statisticsPublicationsButton.setDisable(true);
			openButton.setVisible(false);
			progressIndicator.setVisible(true);
			progressIndicator.setProgress(-1);
			progressIndicator.progressProperty().unbind();
			
	
			configuration.setMode(mode);
			programExecution = new Controller();
			programExecution.setConfiguration(configuration);
			
			
			// When completed tasks
						programExecution.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, 
				                new EventHandler<WorkerStateEvent>() {
				
				                    @Override
				                    public void handle(WorkerStateEvent t) {				     
				                    	
				                    	
				                    	int nAssignedSuccessfully = programExecution.getValue();
				                    	int nTotal = configuration.getNumberOfNodes();
				                    	if(!programExecution.isCancelled()) {
				                    	progressIndicator.setVisible(false);
				                		Alert alert = new Alert(AlertType.INFORMATION);
				                    	alert.setTitle("Run Completed");
				                    	alert.setHeaderText("Assigned nodes from total: " + nAssignedSuccessfully + "/" + nTotal);
				                    	alert.setContentText("Execution complete. To see the results check the output files located in the directory ./resources/Output_files/ "
				                    			+ "\n You can run it again by pressing the \"Generate Data\" Button.");
				                		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				                		Image icon = new Image("file:./resources/icons/info_icon.png");
				                		stage.getIcons().add(icon);		
				                		alert.setGraphic(new ImageView(icon));
				                    	alert.showAndWait();
				                    	
				                    	runButton1.setDisable(false);
				                    	runButton2.setDisable(false);
				                    	runButton3.setDisable(false);
				                    	buttonsBar.setDisable(false);
				                    	cancelButton.setVisible(false);
				                    	statisticsButton.setDisable(false);
				                    	statisticsPublicationsButton.setDisable(false);
				                    	homePageButton.setVisible(false);
				                    	openButton.setVisible(false);
				                    	}
				                        
				                    }
				                });
						
						programExecution.addEventHandler(WorkerStateEvent.WORKER_STATE_CANCELLED, 
				                new EventHandler<WorkerStateEvent>() {
				
				                    @Override
				                    public void handle(WorkerStateEvent t) {
				                    	System.out.println("An error occurred");
				                        
				                    }
				                });

				        // Start the Task.
				        new Thread(programExecution).start();
		}catch(Throwable t) {
			System.out.println("Un error");
		}
	}
	
	@FXML
	public void handleMainPageButton() {
		main.showHomePage();
	}
	
	@FXML
	public void handleInputGeneratorButtonTab() {
		main.showInputFileGeneratorPage();
	}

	@FXML
	public void handleInputFilesButtonTab() {
		main.showSettingsPage();
	}
	
	@FXML
	public void handleAdvancedButtonTab() {
		main.showAdvancedSettingsPage();
	}
	
	@FXML
	public void handleUserParametersButtonTab() {
		main.showUserAttributesPage();
	}
	
	@FXML
	public void handleCommunitiesButtonTab() {
		main.showCommunitiesSettingsPage();
	}
	
	@FXML
	public void handleProfilesButtonTab() {
		main.showProfilesPage();
	}
	
	@FXML
	public void handleOutputFilesButtonTab() {
		main.showOutputFileSettingsPage();
	}
	
	@FXML
	public void handleRunButton() {
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
