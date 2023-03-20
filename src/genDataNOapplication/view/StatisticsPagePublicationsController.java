package genDataNOapplication.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import genDataNOapplication.Main;
import genDataNOapplication.Publication.Publication;
import genDataNOapplication.RV.RV;
import genDataNOapplication.User.User;
import genDataNOapplication.Utils.Utils;
import genDataNOapplication.model.AttributeModel;
import genDataNOapplication.model.ConfigurationModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;


public class StatisticsPagePublicationsController {
	//Reference to the main application
	private Main main;
		
	//Configuration
	protected ConfigurationModel configuration;
	double total;
	
	//@FXML
	//ButtonBar buttonsBar;	
	
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
	Button runButton;
	@FXML
	Button statisticsButton;
	@FXML
	Button statisticsPublicationsButton;
	@FXML
	Button outFileButton1;
	@FXML 
	Button outFileButton2;
	@FXML
	Button outFileButton3;
	@FXML
	Button outFileButton4;
	
	//GridPane
	@FXML
	GridPane chartsSection;
	
	//ChoiceBox
	@FXML
	ChoiceBox<String> whatToDisplay;
	
	//ChoiceBox profile id
	@FXML
	Label whatToDisplayProfileID;
	
	public StatisticsPagePublicationsController() {
		
	}
	
	@FXML
	public void initialize() {
	}
	
	//Is called by the main application to give a reference back to itself.
	public void setMainApp(Main main) {
		this.main = main;
	}
	
	//Is called to set a specific configuration
	public void setConfiguration(ConfigurationModel configuration) {
		this.configuration = configuration;
		total = 0;
		setup();
	}
	
	private void setup() {
		
		outFileButton1.setText("out1");	
		outFileButton2.setText("out2");
		outFileButton3.setText("out3");
		outFileButton4.setText("out4");
		
	
        whatToDisplay.getSelectionModel().selectedIndexProperty().addListener((ob, oldValue, newValue) -> 
        { 
            loadStatistics(whatToDisplay.getSelectionModel().selectedIndexProperty().intValue());
        });
        
		loadStatistics(whatToDisplay.getSelectionModel().selectedIndexProperty().intValue());
	}
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void loadStatistics(int comNum) {
		System.out.println("Displaying publications stats");
		
		chartsSection.getChildren().clear();
		
		//PAU
		//number of publications by time
		//Defining X axis
		NumberAxis xAxis = new NumberAxis(0, configuration.getNumTime(), 1); 
		xAxis.setLabel("Time"); 
		
		//Defining y axis 
		NumberAxis yAxis = new NumberAxis(0, configuration.getNumPublications(), 5); 
		yAxis.setLabel("No.of publications");
		
		//Creating the Line Chart
		LineChart linechart = new LineChart(xAxis, yAxis);
		
		//Preparing the Data
		XYChart.Series series = new XYChart.Series(); 
		series.setName("No of publications by time"); 
		
		for(int k=0; k<RV.num_pub_by_time.size();k++){
			series.getData().add(new XYChart.Data(k, RV.num_pub_by_time.get(k)));
		}
		
		//Setting the data to Line chart    
		chartsSection.add(linechart,0, 0);
		linechart.getData().add(series);
		
		//Number of likes for publication
		//Defining X axis
		CategoryAxis xAxis2 = new CategoryAxis(); 
		xAxis2.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("0-19", "20-39", "40-59", "60-79", "80+"))); 
		xAxis2.setLabel("Number of likes"); 
		//Defining the y axis 
		NumberAxis yAxis2 = new NumberAxis(); 
		yAxis2.setLabel("Number of publications");
		//Creating the Bar chart 
		BarChart<String, Number> barChart2 = new BarChart<>(xAxis2, yAxis2);  
		barChart2.setTitle("Number of likes"); 
		//Preparing the Data
		
		List<Integer> category_count = Arrays.asList(0,0,0,0,0);
		int temp;
		for(int k=0; k<RV.publications_RV.size();k++){
			if (RV.publications_RV.get(k).like_users.size()<20) {
				temp = category_count.get(0);
				category_count.set(0, temp+1);
			}
			else if(RV.publications_RV.get(k).like_users.size()<40) {
				temp = category_count.get(1);
				category_count.set(1, temp+1);
			}
			else if(RV.publications_RV.get(k).like_users.size()<60) {
				temp = category_count.get(2);
				category_count.set(2, temp+1);
			}
			else if(RV.publications_RV.get(k).like_users.size()<80) {
				temp = category_count.get(3);
				category_count.set(3, temp+1);
			}
			else {
				temp = category_count.get(4);
				category_count.set(4, temp+1);
			}
		}
		
		
		
		XYChart.Series<String, Number> series1 = new XYChart.Series<>(); 
		series1.getData().add(new XYChart.Data<>("0-19", category_count.get(0))); 
		series1.getData().add(new XYChart.Data<>("20-39", category_count.get(1))); 
		series1.getData().add(new XYChart.Data<>("40-59", category_count.get(2))); 
		series1.getData().add(new XYChart.Data<>("60-79", category_count.get(3)));   
		series1.getData().add(new XYChart.Data<>("80+", category_count.get(4)));   
		
			
		//Setting the data to Line chart    
		chartsSection.add(barChart2,0, 1);
		barChart2.getData().addAll(series1);
		
		
		//Number of comments for publication
		//Defining X axis
		CategoryAxis xAxis3 = new CategoryAxis(); 
		xAxis3.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("0-4", "5-9", "10-14", "15-19", "20+"))); 
		xAxis3.setLabel("Number of comments"); 
		
		//Defining the y axis 
		NumberAxis yAxis3 = new NumberAxis(); 
		yAxis3.setLabel("Number of publications");
		
		//Creating the Bar chart 
		BarChart<String, Number> barChart3 = new BarChart<>(xAxis3, yAxis3);  
		barChart3.setTitle("Number of comments"); 
		
		//Preparing the Data
		
		List<Integer> category_count2 = Arrays.asList(0,0,0,0,0);
		for(int k=0; k<RV.publications_RV.size();k++){
			if (RV.publications_RV.get(k).comment_users.size()<5) {
				temp = category_count2.get(0);
				category_count2.set(0, temp+1);
			}
			else if(RV.publications_RV.get(k).comment_users.size()<10) {
				temp = category_count2.get(1);
				category_count2.set(1, temp+1);
			}
			else if(RV.publications_RV.get(k).comment_users.size()<15) {
				temp = category_count2.get(2);
				category_count2.set(2, temp+1);
			}
			else if(RV.publications_RV.get(k).comment_users.size()<20) {
				temp = category_count2.get(3);
				category_count2.set(3, temp+1);
			}
			else {
				temp = category_count2.get(4);
				category_count2.set(4, temp+1);
			}
		}
		
		
		XYChart.Series<String, Number> series2 = new XYChart.Series<>(); 
		series2.getData().add(new XYChart.Data<>("0-4", category_count2.get(0))); 
		series2.getData().add(new XYChart.Data<>("5-9", category_count2.get(1))); 
		series2.getData().add(new XYChart.Data<>("10-14", category_count2.get(2))); 
		series2.getData().add(new XYChart.Data<>("15-19", category_count2.get(3)));   
		series2.getData().add(new XYChart.Data<>("20+", category_count2.get(4)));   
		
			
		//Setting the data to Line chart    
		chartsSection.add(barChart3, 1, 1);
		barChart3.getData().addAll(series2);
		
		
		//Number of share for publication
		//Defining X axis
		CategoryAxis xAxis4 = new CategoryAxis(); 
		xAxis4.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("0", "1", "2", "3-4", "5+"))); 
		xAxis4.setLabel("Number of shares"); 
		
		//Defining the y axis 
		NumberAxis yAxis4 = new NumberAxis(); 
		yAxis4.setLabel("Number of publications");
		
		//Creating the Bar chart 
		BarChart<String, Number> barChart4 = new BarChart<>(xAxis4, yAxis4);  
		barChart4.setTitle("Number of shares"); 
		
		//Preparing the Data
		
		List<Integer> category_count3 = Arrays.asList(0,0,0,0,0);
		for(int k=0; k<RV.publications_RV.size();k++){
			if (RV.publications_RV.get(k).share_users.size()<1) {
				temp = category_count3.get(0);
				category_count3.set(0, temp+1);
			}
			else if(RV.publications_RV.get(k).share_users.size()<2) {
				temp = category_count3.get(1);
				category_count3.set(1, temp+1);
			}
			else if(RV.publications_RV.get(k).share_users.size()<3) {
				temp = category_count3.get(2);
				category_count3.set(2, temp+1);
			}
			else if(RV.publications_RV.get(k).share_users.size()<5) {
				temp = category_count3.get(3);
				category_count3.set(3, temp+1);
			}
			else {
				temp = category_count3.get(4);
				category_count3.set(4, temp+1);
			}
		}
		
		
		XYChart.Series<String, Number> series3 = new XYChart.Series<>(); 
		series3.getData().add(new XYChart.Data<>("0", category_count3.get(0))); 
		series3.getData().add(new XYChart.Data<>("1", category_count3.get(1))); 
		series3.getData().add(new XYChart.Data<>("2", category_count3.get(2))); 
		series3.getData().add(new XYChart.Data<>("3-4", category_count3.get(3)));   
		series3.getData().add(new XYChart.Data<>("5+", category_count3.get(4)));   
		
			
		//Setting the data to Line chart    
		chartsSection.add(barChart4, 2, 1);
		barChart4.getData().addAll(series3);
		
		
		//Publications by topic
		List<Integer> category_count4 = Arrays.asList(0,0,0,0,0,0,0,0,0);
		Publication publication;
		for(int k=0; k<RV.publications_RV.size();k++){
			publication = RV.publications_RV.get(k);
			if(publication.getTopic().equals("Music Artist")) {
				category_count4.set(0, category_count4.get(0)+1);
			}
			else if(publication.getTopic().equals("Drink Brand")) {
				category_count4.set(1, category_count4.get(1)+1);
			}
			else if(publication.getTopic().equals("TV show")) {
				category_count4.set(2, category_count4.get(2)+1);
			}
			else if(publication.getTopic().equals("Sports")) {
				category_count4.set(3, category_count4.get(3)+1);
			}
			else if(publication.getTopic().equals("Animals")) {
				category_count4.set(4, category_count4.get(4)+1);
			}
			else if(publication.getTopic().equals("Computer games")) {
				category_count4.set(5, category_count4.get(5)+1);
			}
			else if(publication.getTopic().equals("News and present")) {
				category_count4.set(6, category_count4.get(6)+1);
			}
			else if(publication.getTopic().equals("Food")) {
				category_count4.set(7, category_count4.get(7)+1);
			}
			else {
				category_count4.set(8, category_count4.get(8)+1);
			}
		}
		
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList( 
	    new PieChart.Data("Music Artist", category_count4.get(0)), 
	    new PieChart.Data("Drink Brand", category_count4.get(1)), 
	    new PieChart.Data("TV show", category_count4.get(2)), 
	    new PieChart.Data("Sports", category_count4.get(3)),
		new PieChart.Data("Animals", category_count4.get(4)), 
	    new PieChart.Data("Computer games", category_count4.get(5)), 
	    new PieChart.Data("News and present", category_count4.get(6)), 
	    new PieChart.Data("Food", category_count4.get(7)),
	    new PieChart.Data("Travels", category_count4.get(8)));
	    
	    //Creating a Pie chart 
	    PieChart pieChart = new PieChart(pieChartData);
	    //Setting the title of the Pie chart 
	    pieChart.setTitle("Publications by topic");
	    //setting the direction to arrange the data 
	    pieChart.setClockwise(true);
	    //Setting the length of the label line 
	    pieChart.setLabelLineLength(50);
	    //Setting the labels of the pie chart visible  
	    pieChart.setLabelsVisible(true);
	    //Setting the start angle of the pie chart 
	    pieChart.setStartAngle(180); 
	    
	    chartsSection.add(pieChart, 1, 0);
	    
	    
	  //type of interactions
		List<Integer> category_count5 = Arrays.asList(0,0,0,0);
		User user;
		for(int k=0; k<RV.Users.size();k++){
			user = (User) RV.Users.get(k);
			category_count5.set(0, category_count5.get(0)+user.publications_ignored.size());
			category_count5.set(1, category_count5.get(1)+user.publications_liked.size());
			category_count5.set(2, category_count5.get(2)+user.publications_commented.size());
			category_count5.set(3, category_count5.get(3)+user.publications_shared.size());
		}
		
		ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList( 
	    new PieChart.Data("Publications ignored", category_count5.get(0)), 
	    new PieChart.Data("Publications liked", category_count5.get(1)), 
	    new PieChart.Data("Publications commented", category_count5.get(2)), 
	    new PieChart.Data("Publications shared", category_count5.get(3)));
		
		//Creating a Pie chart 
	    PieChart pieChart2 = new PieChart(pieChartData2);
	    //Setting the title of the Pie chart 
	    pieChart2.setTitle("Type of interactions");
	    //setting the direction to arrange the data 
	    pieChart2.setClockwise(true);
	    //Setting the length of the label line 
	    pieChart2.setLabelLineLength(20);
	    //Setting the labels of the pie chart visible  
	    pieChart2.setLabelsVisible(true);
	    //Setting the start angle of the pie chart 
	    pieChart2.setStartAngle(180); 
	    
	    chartsSection.add(pieChart2, 2, 0);
		
		
	}
	
	//Data class that holds data of an attribute parameter 
	public class TableRowData
	{
		private String atribute;
		private String profile;
		private String exact;
		private String modal_1;
		private String modal_2;
		
		public TableRowData(String atribute, String profile, String exact, String modal_1, String modal_2) {
			super();
			this.atribute = atribute;
			this.profile = profile;
			this.exact = exact; 
			this.modal_1 = modal_1;
			this.modal_2 = modal_2;
		}
		public String getAtribute() {	return atribute;	}
		public String getProfile() {	return profile;	}
		public String getExact() {	return exact;	}
		public String getModal_1() {	return modal_1;	}
		public String getModal_2() {	return modal_2;	}
		public void setAtribute(String atribute) {	this.atribute = atribute;	}
		public void setProfile(String profile) {	this.profile = profile;	}
		public void setExact(String exact) {	this.exact = exact;	}
		public void setModal_1(String modal_1) {	this.modal_1 = modal_1;	}
		public void setModal_2(String modal_2) {	this.modal_2 = modal_2;	}
	}	
	
	private List<Pair<String, Double>> getExpectedData(int comNum, int countAttr){
		List<Pair<String, Double>> expectedParamList = configuration.getUserAttrributesList().get(countAttr).getParameterList();

		if(comNum != 10) {
			int[] profComAssaign = configuration.getProfileCommunityAssaignment();
			int profileID = profComAssaign[comNum];
			List<Integer> profile = configuration.getProfileList().get(profileID);
			int paramID = profile.get(countAttr);
			Pair<String, Double> param = expectedParamList.get(paramID);
			List<Pair<String, Double>> newParamList = new ArrayList<Pair<String, Double>>();
			
			for(Pair<String, Double> currentParam : expectedParamList) {
				if(currentParam.getKey().equals(param.getKey())) {
					Pair<String, Double> newParam = new Pair<String, Double>(currentParam.getKey(), 1.0);
					newParamList.add(newParam);
				}else {
					Pair<String, Double> newParam = new Pair<String, Double>(currentParam.getKey(), 0.0);
					newParamList.add(newParam);
				}
					
			}
			expectedParamList = newParamList;
			
		}
		
		return expectedParamList;
	}
	@SuppressWarnings("unchecked")
	private List<Pair<String, Double>> getRealData(int comNum, int attributeId){
		List<Pair<String, Double>> realParamList = new ArrayList<Pair<String, Double>>();
		List<Integer> paramFreqList = new ArrayList<Integer>();
		List<String> paramNameList = new ArrayList<String>();
		for(Pair<String, Double> currentParam : configuration.getUserAttrributesList().get(attributeId).getParameterList()) {
			paramNameList.add(currentParam.getKey());
			paramFreqList.add(0);
		}
		Enumeration<?> en1 = RV.Users.keys();
		
		while (en1.hasMoreElements()){
			User nw = (User)RV.Users.get((Integer)en1.nextElement());
			if(comNum != 10) {
			Vector<Integer> communities =  nw.communities;
			if(!communities.contains(comNum))
				continue;
			}
			
			String attribute = new String();
			switch(attributeId) {
			case 0:
				attribute = nw.getAge();
				break;
			case 1:
				attribute = nw.getGender();
				break;
			case 2:
				attribute = nw.getResidence();
				break;
			case 3:
				attribute = nw.getReligion();
				break;
			case 4:
				attribute = nw.getMaritalStatus();
				break;
			case 5:
				attribute = nw.getProfession();
				break;
			case 6:
				attribute = nw.getPoliticalOrientation();
				break;
			case 7:
				attribute = nw.getSexualOrientation();
				break;
			case 8:
				attribute = nw.getLike(1);
				break;
			case 9:
				attribute = nw.getLike(2);
				break;
			case 10:
				attribute = nw.getLike(3);
				break;
			}
				
			int index = 0;
			for(String currentName : paramNameList) {
				if(attribute.equals(currentName)) {
					index = paramNameList.indexOf(currentName);
					int freq = paramFreqList.get(index);
					freq++;
					paramFreqList.set(index, freq);
					break;
				}
			}

		}
		int sum = 0;
		for(String currentName : paramNameList) {
			sum += paramFreqList.get(paramNameList.indexOf(currentName));
		}
		
		for(int value : paramFreqList) {
			double freq = (double) value / sum;
			String name = paramNameList.get(paramFreqList.indexOf(value));
			Pair<String, Double> param = new Pair<String, Double>(name, freq);
			realParamList.add(param);
		}
		return realParamList;
	}
	
	private void openFile(String fileName) {
		File file = new File(fileName);
		if(!Utils.openFile(file)){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error opening the file " + file.getPath());
			alert.showAndWait();
		}
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
	public void handleStatisticsButton() {
		main.showStatisticsPage();
	}	
	
	@FXML
	public void handleStatisticsPublicationsButton() {
		main.showStatisticsPublicationsPage();
	}	
	
	@FXML
	public void handleRunButton() {
		main.showRunPage();
	}
	
	@FXML
	public void handleOutFileButton1() {
		this.openFile(configuration.getDin1());
	}
	
	@FXML
	public void handleOutFileButton2() {
		this.openFile(configuration.getDin2());
	}
	
	@FXML
	public void handleOutFileButton3() {
		this.openFile(configuration.getDin3());
	}
	
	@FXML
	public void handleOutFileButton4() {
		this.openFile(configuration.getDin4());
	}
	
	@FXML
	public void handleBackButton() {
		main.showRunPage();
	}
}
