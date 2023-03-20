package genDataNOapplication.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import genDataNOapplication.model.AttributeModel;
import javafx.util.Pair;

//Class that stores all the customizable settings for the program execution
public class ConfigurationModel {
	
	//Input and output file paths
	private String inputFile1;
	private String inputFile2;
	private String inputFile3;
	private String outFile;
	private String outgFile;
	private String outs1File;
	private String out1File;
	private String out2File;
	private String din1, din2, din3, din4, din5, din6;
	private String sta1, sta2, sta3, sta4, sta5; 
	
	//Number of communities
	private int numCommunities;
	
	//Proportion of nodes that are seeds from the total node count of the graph 
	private double seedPercentage;
	
	private int randomness;
	
	private int numPublications;
	
	private int numTime;
	
	private int maxTagged;
	
	private int modeAM = 0;
	
	private double alpha, beta, mu, typeRate, qualRate;
	
	private double imageRate, videoRate, textRate, imageWeight, videoWeight, textWeight;
	
	private double minScore, commentPenalty, sharePenalty;
	
	
	private double penalty2, penalty3;
	//List of User Attributes
	private List<AttributeModel> userAttrributesList;
	
	private List<Double> topics_rate;
	
	//List of Profiles
	private List<List<Integer>> profileList;
	
	//Each Profile Assigned to one community
	int profileCommunityAssaign[] = new int[10];	
	
	/**
	 * List of communities frequencies (relative to the count of number of nodes 
	 * of each community from the communities file). The total sum must be 1000.
	 * 
	 * This data is generated from the nodes communities file and doesn't 
	 * need to be exported or imported.
	 */
	private int communitiesFrequencies[] = new int[10];
	
	private String mode;
	
	//Class constructor
	public ConfigurationModel() { }
	
	//Setters and getters
	public String getInputFile1() { return inputFile1; }
	public void setInputFile1(String inputFile1) { this.inputFile1 = inputFile1; }
	
	public String getInputFile2() { return inputFile2; }
	public void setInputFile2(String inputFile2) { this.inputFile2 = inputFile2; }
	
	public String getInputFile3() { return inputFile3; }
	public void setInputFile3(String inputFile3) { this.inputFile3 = inputFile3; }
	
	public String getOutFile() { return outFile; }
	public void setOutFile(String outFile) { this.outFile = outFile; }
	
	public String getOutgFile() { return outgFile; }
	public void setOutgFile(String outgFile) { this.outgFile = outgFile; }
	
	public String getOuts1File() { return outs1File; }
	public void setOuts1File(String outs1File) { this.outs1File = outs1File; }
	
	public String getOut1File() { return out1File; }
	public void setOut1File(String out1File) { this.out1File = out1File; }
	
	public String getOut2File() { return out2File; }
	public void setOut2File(String out2File) { this.out2File = out2File; }
	
	public String getDin1() { return din1; }
	public void setDin1(String din) { this.din1 = din; }
	
	public String getDin2() { return din2; }
	public void setDin2(String din) { this.din2 = din; }

	public String getDin3() { return din3; }
	public void setDin3(String din) { this.din3 = din; }
	
	public String getDin4() { return din4; }
	public void setDin4(String din) { this.din4 = din; }
	
	public String getDin5() { return din5; }
	public void setDin5(String din) { this.din5 = din; }
	
	public String getDin6() { return din6; }
	public void setDin6(String din) { this.din6 = din; }
	
	public String getSta1() { return sta1; }
	public void setSta1(String sta) { this.sta1 = sta; }
	
	public String getSta2() { return sta2; }
	public void setSta2(String sta) { this.sta2 = sta; }
	
	public String getSta3() { return sta3; }
	public void setSta3(String sta) { this.sta3 = sta; }
	
	public String getSta4() { return sta4; }
	public void setSta4(String sta) { this.sta4 = sta; }
	
	public String getSta5() { return sta5; }
	public void setSta5(String sta) { this.sta5 = sta; }	
	
	public int getNumCommunities() { return numCommunities; }
	public void setNumCommunities(int numCommunities) { this.numCommunities = numCommunities; }
	
	public double getSeedPercentage() { return seedPercentage; }
	public void setSeedPercentage(double seedPercentage) { this.seedPercentage = seedPercentage; }
	
	public int getRandomness() { return randomness; }
	public void setRandomness(int randomness) { this.randomness = randomness; }
	
	public int getNumPublications() { return numPublications; }
	public void setNumPublications(int numPub) { this.numPublications = numPub; }
	
	public int getNumTime() { return numTime; }
	public void setNumTime(int numT) { this.numTime = numT; }
	
	public int getMaxTagged() { return maxTagged; }
	public void setMaxTagged(int maxT) { this.maxTagged = maxT; }
	
	public double getAlpha() { return alpha; }
	public void setAlpha(double a) { this.alpha = a; }
	
	public double getBeta() { return beta; }
	public void setBeta(double b) { this.beta = b; }
	
	public double getMu() { return mu; }
	public void setMu(double m) { this.mu = m; }
	
	public double getQualRate() { return qualRate; }
	public void setQualRate(double q) { this.qualRate = q; }
	
	public double getTypeRate() { return typeRate; }
	public void setTypeRate(double t) { this.typeRate = t; }
	
	public double getVideo() { return videoRate; }
	public void setVideo(double v) { this.videoRate = v; }
	
	public double getImage() { return imageRate; }
	public void setImage(double i) { this.imageRate = i; }
	
	public double getText() { return textRate; }
	public void setText(double t) { this.textRate = t; }
	
	public double getVideoWeight() { return videoWeight; }
	public void setVideoWeight(double v) { this.videoWeight = v; }
	
	public double getImageWeight() { return imageWeight; }
	public void setImageWeight(double i) { this.imageWeight = i; }
	
	public double getTextWeight() { return textWeight; }
	public void setTextWeight(double t) { this.textWeight = t; }
	
	public double getMinScore() { return minScore; }
	public void setMinScore(double s) { this.minScore = s; }
	
	public double getCommmentPenalty() { return commentPenalty; }
	public void setCommmentPenalty(double c) { this.commentPenalty = c; }
	
	public double getSharePenalty() { return sharePenalty; }
	public void setSharePenalty(double s) { this.sharePenalty = s; }
	
	public double getPenalty2() { return penalty2; }
	public void setPenalty2(double p2) { this.penalty2 = p2; }
	
	public double getPenalty3() { return penalty3; }
	public void setPenalty3(double p3) { this.penalty3 = p3; }
	
	public int getModeAM() { return modeAM; }
	public void setModeAM(int m) { this.modeAM = m; }
	
	public void setTopicList(List<Double> topicList) {this.topics_rate = topicList;}
	public List<Double> getTopicList() {return this.topics_rate;}

	public List<AttributeModel> getUserAttrributesList() { return userAttrributesList; }
	
	public List<List<Integer>> getProfileList() { return profileList; }
	public void setProfileList(List<List<Integer>> profileList) { this.profileList = profileList; }
	
	public int[] getProfileCommunityAssaignment() { return profileCommunityAssaign; }
	public void setProfileCommunityAssaignment(int[] profileCommunityAssaign) { this.profileCommunityAssaign = profileCommunityAssaign; }
	
	public String getMode() {return mode;}
	public void setMode(String mode_) {this.mode=mode_;}
	
	//Add user attributes
	public void setAttributeList(List<AttributeModel> attributeList) {this.userAttrributesList = attributeList; }
	
	
	// Calculates number of nodes from the current communities file.
	public int getNumberOfNodes()
	{	
		int count = 0;
		try 
		{
	    	BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(this.inputFile2)), "UTF-8"));
	    	while (br.readLine() != null)
	    		count += 1;
	    	br.close();
		}
		catch (Exception e)
		{
            System.out.println("Issue with communities file: " + this.inputFile2);  
            e.printStackTrace();
        }
		
		return count;
	}
	
	// Calculates the this.communitiesFrequency from the current communities file
	public void loadCommunitiesFrequencies()
	{
		Arrays.fill(this.communitiesFrequencies, 0);		
		try 
		{
			int ncount=0;
	    	BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(this.inputFile2)), "UTF-8"));
	    	for(String line = br.readLine(); line != null; line = br.readLine())
	    	{
	    		String linedata[] = line.split(";");
	    		int communityId = Integer.parseInt(linedata[1]);
	    		this.communitiesFrequencies[communityId] += 1;
	    		++ncount;
	        }
	    	br.close();
	        
	        int sum = Arrays.stream(this.communitiesFrequencies).sum();        
	        double ratio = ncount/sum;
	        
	        sum = 0;
	        for(int i = 0; i < this.communitiesFrequencies.length - 1; i++) {
	        	this.communitiesFrequencies[i] *= ratio;
	        	sum += this.communitiesFrequencies[i];
	        }
	        
	        // Last one is the difference to make sure sum reaches ncount exactly
	        this.communitiesFrequencies[this.communitiesFrequencies.length-1] = ncount - sum;
		}
		catch (Exception e)
		{
            System.out.println("Issue with communities file: " + this.inputFile2);  
            e.printStackTrace();
        }  
	}

	// This can change if the communities file changes
	public int[] getCommunitiesFrequencies() {  return communitiesFrequencies;  }
	
	// This can change if the communities file changes or the profile assignment changes
	public int[] getCurrentProfilesFrequencies()
	{
		int profileFrequencies[] = new int[this.profileCommunityAssaign.length];
		for(int i=0; i < this.profileCommunityAssaign.length ; i++)
			profileFrequencies[profileCommunityAssaign[i]] = communitiesFrequencies[i]; 
		
		return profileFrequencies;		
	}
}
