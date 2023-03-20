// Copyright (C) 2018-2021  David F. Nettleton (david.nettleton@upf.edu)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

package genDataNOapplication.User;
import java.util.*;

import genDataNOapplication.Publication.Publication;


public class User {
	private Integer User;
	public List<Integer> friends = new LinkedList <Integer> ();
	public Vector degrees = new Vector (); // are the corresponding degrees of the friend nodes
	public Vector weights = new Vector (); // are the corresponding weights of the friend nodes
 
	public Vector communities = new Vector ();

	private String residence;
	private String age; 
	private int age_lowerb; 
	private int age_upperb;
	private String gender;
	private String like1;
	private String like2;
	private String like3;
	
	private String religion;
	private String maritalstatus;
	private String profession;
	private String politicalorientation;
	private String sexualorientation;
	
	private int GROUP = -1;
	
	//start - added for publications interaction PAU
	public List<Integer> publications = new LinkedList <Integer> ();
	public List<Integer> publications_liked = new LinkedList <Integer> ();
	public List<Integer> publications_commented = new LinkedList <Integer> ();
	public List<Integer> publications_shared = new LinkedList <Integer> ();
	public List<Integer> publications_ignored = new LinkedList <Integer> ();
	private Integer numpublications;
	public List<Integer> tagged_in = new LinkedList <Integer> ();
	/*
	public Map<Integer, Double> scores_given_d1 = new HashMap<Integer, Double>();
	public Map<Integer, Double> scores_given_d2 = new HashMap<Integer, Double>();
	public Map<Integer, Double> scores_given_d3 = new HashMap<Integer, Double>();
	*/
	public Map<Integer, List<Double>> scores_given_d1 = new HashMap<Integer, List<Double>>();
	public Map<Integer, List<Double>> scores_given_d2 = new HashMap<Integer, List<Double>>();
	public Map<Integer, List<Double>> scores_given_d3 = new HashMap<Integer, List<Double>>();
	
	
	public void addPublication(Integer publication) {
		if (this.publications.contains(publication) == false) {
			this.publications.add(publication);
		}
	}
	
	public void addPublicationLiked(Integer publication_liked) {
		if (this.publications_liked.contains(publication_liked) == false) {
			this.publications_liked.add(publication_liked);
		}
	}
	
	public void addPublicationCommented(Integer publication_commented) {
		if (this.publications_commented.contains(publication_commented) == false) {
			this.publications_commented.add(publication_commented);
		}
	}
	
	public void addPublicationShared(Integer publications_shared) {
		if (this.publications_shared.contains(publications_shared) == false) {
			this.publications_shared.add(publications_shared);
		}
	}
	
	public void addPublicationIgnored(Integer publications_ignored) {
		if (this.publications_ignored.contains(publications_ignored) == false) {
			this.publications_ignored.add(publications_ignored);
		}
	}
	
	public void addTaggedIn(Integer publication) {
			this.tagged_in.add(publication);
	}
	
	public Integer getNumPublications() {
		if(this.numpublications == null) {
			return 0;
		}
		else {
			return this.numpublications;
		}
	}
	
	public void setNumPublications(int num) {
		this.numpublications = num;
	}
	
	public Double getWeight_friend(int friend) {
		int friend_position = this.friends.indexOf(friend);
		return (Double) weights.get(friend_position);
	}
	
	
	//end PAU
	
	
	
	public void loadCommunity(Integer comm){
		if (this.communities.contains(comm) == false)
			this.communities.add(comm);
	}
	public Integer getCommunity(Integer ixco){
		int community=0;
		if (ixco < this.communities.size())
			community = (Integer)this.communities.get(ixco);
		return community;
	}
	
	public void loadWeight(Double weight){
		this.weights.add(weight);
	}
	public void loadAge(String agei){
		if(agei == null)
			throw new NullPointerException("Load age null set");
		this.age = agei;
	}
	public void loadLike(String ilike, int i){
		if (i==1)
			this.like1 = ilike;
		else if (i==2)
			this.like2 = ilike;
		else if (i==3)
			this.like3 = ilike;
	}
	public void loadResidence(String residencei){
		this.residence = residencei;
	}
	public void loadGender(String genderi){
		this.gender = genderi;
	}
	
	public void loadReligion(String religioni){
		this.religion = religioni;
	}
	public void loadMaritalStatus(String maritalstatusi){
		this.maritalstatus = maritalstatusi;
	}
	public void loadProfession(String professioni){
		this.profession = professioni;
	}
	public void loadPoliticalOrientation(String politicalorientationi){
		this.politicalorientation = politicalorientationi;
	}
	public void loadSexualOrientation(String sexualorientationi){
		this.sexualorientation = sexualorientationi;
	}
		
	
	public void loadGROUP(int groupi){
		this.GROUP = groupi;
	}
	public Double getWeight(Integer ixwe){
		double weight=0;
		if (ixwe < this.weights.size())
			weight = (Double)this.weights.get(ixwe);
		return weight;
	}
	public String getAge(){
		return this.age;
	}
	public String getGender(){
		return this.gender;
	}
	public String getResidence(){
		return this.residence;
	}
	
	
	public String getLike(int i){
		if (i==1)
			return this.like1;
		else if (i==2)
			return this.like2;
		else
			return this.like3;
	}
	
	public String getReligion(){
		return this.religion;
	}
	public String getMaritalStatus(){
		return this.maritalstatus;
	}
	public String getProfession(){
		return this.profession;
	}
	public String getPoliticalOrientation(){
		return this.politicalorientation;
	}
	public String getSexualOrientation(){
		return this.sexualorientation;
	}
	
	
	
	
	public int getGROUP(){
		return this.GROUP;
	}
	
	
	private float numFriends;
	private float numFriendsEnComun;
	private int chosen=0;
	private int tested=0;
	private int anonymized=0;
	private float cc;
	private float apl;
	
	private double numEdges;
	private double normEdges;
	
	private float Auth;
	private int Mod;
	private float betweennessCentrality; /* A Calculated in Gephi & read in */
	private float bridgingCoefficient;   /* B Calculated in Java */
	private float bridgingCentrality;    /* A * B */
	
	
	private double normDegree;
	private double normCC;
	private double avgDegrees;
	private double normavgDegrees;
	private double stdevDegrees;
	private double normstdevDegrees;
	private double factor;
	private double previousFactor;
	
	public Integer getUser() {
		return User;
	}
	public void setUser(Integer user) {
		User = user;
	}

	public float getNumFriends() {
		return numFriends;
	}
	public int getChosen() {
		return chosen;
	}
	public void setChosen() {
		this.chosen = 1;
	}
	public int getTested() {
		return tested;
	}
	public void setTested() {
		this.tested = 1;
	}
	public void resetTested() {
		this.tested = 0;
	}
	public int getAnon() {
		return anonymized;
	}
	public void setAnon() {
		this.anonymized = 1;
	}
	public void resetAnon() {
		this.anonymized = 0;
	}
	public float getNumFriendsEnComun() {
		return numFriendsEnComun;
	}
	public float getCC() {
		return cc;
	}
	public float getapl() {
		return apl;
	}
	
	public double getnumEdges() {
		return numEdges;
	}
	
	public double getNormEdges() {
		return normEdges;
	}
	public double getNormDegree() {
		return normDegree;
	}
	public void setNormDegree(double ndeg) {
		this.normDegree = ndeg;
	}
	
	public double getNormAvgNdeg() {
		return normavgDegrees;
	}
	public double getNormStdevNdeg() {
		return normstdevDegrees;
	}
	public double getNormCC() {
		return normCC;
	}
	
	public float getAUTH() {
		return Auth;
	}
	public int getMOD() {
		return Mod;
	}
	public float getBECE() {
		return betweennessCentrality;
	}
	public float getBRCO() {
		return bridgingCoefficient;
	}
	public float getBRCE() {
		return bridgingCentrality;
	}
	
	public void setNumFriends() {
		this.numFriends = this.friends.size();
	}
	public void setNumFriendsEnComun(float fec) {
		this.numFriendsEnComun = fec;
		this.cc = fec;
	}
	public void setCC(float ccc) {
		this.cc = ccc;
	}
	public void setapl(float apale) {
		this.apl = apale;
	}
	
	public void setnumEdges(float nue) {
		this.numEdges = nue;
	}
	
	public void setAUTH(float aauth) {
		this.Auth = aauth;
	}
	public void setMOD(int mmod) {
		this.Mod = mmod;
	}
	public void setBECE(float bc) {
		this.betweennessCentrality = bc;
	}
	public void setBRCO(float bco) {
		this.bridgingCoefficient = bco;
	}
	public void setBRCE(float brc) {
		this.bridgingCentrality = brc;
	}
	
	public User(Integer user){
		setUser(user);
	}

	public void afegirFriend(Integer friend){
		if (this.friends.contains(friend) == false){
			this.friends.add(friend);
			setNumFriends();
		}
	}
	public void esborrarFriend(Integer friend){
		if (this.friends.contains(friend) == true){
			this.friends.remove(friend);
			setNumFriends();
		}
	}
	

	public void loadDegree(Integer degree){
		this.degrees.add(degree);
	}
	public void unloadDegree(Integer degree){
		this.degrees.remove(degree);
	}
	public void sortDegrees(){
		Collections.sort(this.degrees);
	}
	
	public void setAvgDegrees(){
		double avg=0; int sum=0;
		int i=0;
		for (i=0;i<this.degrees.size();++i)
			sum = sum + (Integer)degrees.get(i);
		avg = (double)sum / (double)this.degrees.size();
		this.avgDegrees = avg;
	}
	public double getAvgDegrees(){
		return avgDegrees;
	}
	
	public void setDists(double min_degree, double max_degree, double min_cc, double max_cc){
		double ffactor=0;
		double d     = this.getNumFriends();
		double cc    = this.getCC();

		double deg_normalized   = (d - min_degree)    / (max_degree - min_degree);
		double cc_normalized    = (cc - min_cc)       / (max_cc - min_cc);

		this.normDegree       = deg_normalized;
		this.normCC           = cc_normalized;

    }

	public static double Round(double Rval, int Rpl) {
		  double p = (double)Math.pow(10,Rpl);
		  Rval = Rval * p;
		  double tmp = Math.round(Rval);
		  return (double)tmp/p;
		    }
	
}
