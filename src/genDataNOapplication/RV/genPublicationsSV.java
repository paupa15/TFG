package genDataNOapplication.RV;

//import other classes
import genDataNOapplication.User.User;
import genDataNOapplication.model.ConfigurationModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import genDataNOapplication.Publication.Publication;
import genDataNOapplication.RV.RV;

public class genPublicationsSV {
	public static void main(String arrg[], ConfigurationModel configuration) {
		
		List<Publication> publications = new LinkedList <Publication> ();
		List<String> topics_list = new LinkedList <String> ();
		Map<String, List<String>> publication_description_dict = new HashMap<String, List<String>>();

		double num_random;
		User actual_user;
		int num_users = RV.Users.size();
		int min_degree=10000;
		int max_degree = 0;
		
		System.out.println("YOU ARE IN THE STATIC VERSION");
		
		for (int i=0; i<num_users; i++) {
			actual_user = (User)RV.Users.get(i);
			num_random = Math.random();
			if (num_random<0.80) {num_random = Math.random();
				if (num_random<0.40) {num_random = Math.random();
					if (num_random<0.26) {num_random = Math.random();
						if (num_random<0.2) {num_random = Math.random();
							if (num_random<0.16) {num_random = Math.random();
								if (num_random<0.13) {num_random = Math.random();
									if (num_random<0.114) {num_random = Math.random();
										if (num_random<0.1) {
											actual_user.setNumPublications(8);
										}
										else {actual_user.setNumPublications(7);}
									}
									else {actual_user.setNumPublications(6);} 
								}
								else {actual_user.setNumPublications(5);}
							}
							else {actual_user.setNumPublications(4);}
						}
						else {actual_user.setNumPublications(3);}
					}
					else {actual_user.setNumPublications(2);}
				}
				else {actual_user.setNumPublications(1);}
			}
			else {actual_user.setNumPublications(0);}	
			
			if (actual_user.friends.size()<min_degree) {
				min_degree= actual_user.friends.size();
			}
			if (actual_user.friends.size()>max_degree) {
				max_degree= actual_user.friends.size();
			}
			
		
		}
		
		int id_new_publi=0;
		User user;
		int user_id;
		
		publication_description_dict = set_dict_pub_desc(configuration);
		topics_list = new ArrayList<String>(publication_description_dict.keySet());
		
		for(int i=0; i<num_users; i++) {
			user = (User)RV.Users.get(i);
			user_id = user.getUser();
			for(int j=0; j<user.getNumPublications(); j++) {
				//all the publications are created by the users
				String type = decide_type(configuration.getImage(), configuration.getVideo(), configuration.getText());
				String topic = decide_topic(topics_list, configuration.getTopicList());
				String description = decide_publication_desc(topic, publication_description_dict);
				Double quality = decide_quality();				
				
				Publication publi = new Publication();
				publi.setId(id_new_publi);
				publi.loadUser(user_id);
				publi.loadType(type);
				publi.loadTopic(topic);
				publi.loadPublicationDescription(description);
				publi.loadQuality(quality);	
				
				user.addPublication(id_new_publi);
				publications.add(publi);
				id_new_publi ++;
			}
		}
		
		System.out.println("Publications done by all the users");
		
		int total_publications = publications.size();
		for (int i=0; i<total_publications;i++) {//propagation of the interaction on all the publications
			Publication publi = publications.get(i);
			int publi_id = publi.getId();
			int user_pub_id = publi.getUser();
			User user_pub = (User)RV.Users.get(user_pub_id);
			
			for(int j=0; j<user_pub.friends.size(); j++) { //iterate over all the friends of the user who did the publication
				int friend_id = user_pub.friends.get(j);
				User friend = (User)RV.Users.get(friend_id);
				double score = calculate_score(publi, friend, user_pub, min_degree, max_degree);
				int interaction = interaction_function(score);
				//friend.scores_given_d1.put(publi_id, score);
				List<Double> list1 = Arrays.asList(score, Double.valueOf(interaction));
				friend.scores_given_d1.put(publi_id, list1);
				
				if (interaction ==2){
					friend.addPublicationLiked(publi_id);
					publi.addUserLike(friend_id);
					
					friend.addPublicationCommented(publi_id);
					publi.addUserComment(friend_id);
				}
				else if (interaction ==1){
					friend.addPublicationLiked(publi_id);
					publi.addUserLike(friend_id);
				}
			}
		}
		writeUSerPublications(configuration.getSta1());
		writeScoreGiven(configuration.getSta2());
		writeinfoPublications(publications, configuration.getSta3());
		writeLikePublications(publications,configuration.getSta4());
		writeCommentPublications(publications, configuration.getSta5());
		
		
	}	
	
	public static double calculate_score(Publication publication, User friend, User user_pub, int min_degree, int max_degree) {
		double degree, weight, quality, type, degree_rate, weight_rate, quality_rate, type_rate, relation_rate, score;
		int relation_pub_user;
		String age;
		degree = degree_norm(friend, min_degree, max_degree);
		weight = user_pub.getWeight_friend(friend.getUser());
		quality = publication.getQuality();
		type = type_weight(publication);
		relation_pub_user= relation_friend_pub(friend, publication);
		age = user_pub.getAge();
		
		if (age=="18-25"){
			degree_rate = 0.15;
			weight_rate = 0.35;
			quality_rate = 0.05;
			type_rate = 0.1;
			relation_rate = 0.35;
		}
		else if(age=="26-35" || age=="36-45") {
			degree_rate = 0.15;
			weight_rate = 0.25;
			quality_rate = 0.1;
			type_rate = 0.05;
			relation_rate = 0.45;
		}
		else {
			degree_rate = 0.15;
			weight_rate = 0.2;
			quality_rate = 0.14;
			type_rate = 0.01;
			relation_rate = 0.5;
		}
		score = degree_rate*degree + weight_rate*weight + quality_rate*quality + type_rate*type + relation_rate*relation_pub_user;
		return score;
	}
	
	public static int interaction_function(double score) {
		double num_random = Math.random();
		if(score>=num_random) {
			if(score-0.2>=num_random) {
				return 2;}
			else {
				return 1;}
		}
		else {
			return 0;}
	}
	
	public static double degree_norm(User user, int min_degree, int max_degree) {
		return 1 - ((double)(user.friends.size()-min_degree)/(double)(max_degree-min_degree)); 
	}
	
	public static double type_weight(Publication publication) {
		if (publication.getType() == "Image") {
			return 1;
		}
		else if(publication.getType() == "Video") {
			return 0.8;
		}
		else {return 0.6;}
	}
	
	public static int relation_friend_pub(User user, Publication publication) {
		if (user.getLike(1)==publication.getTopic() || user.getLike(2)==publication.getTopic() || user.getLike(3)==publication.getTopic()) {
			return 1;
		}
		else {return 0;}
	}
	
	public static String decide_type(double image_rate, double video_rate, double text_rate) {
		double num_random = Math.random();
		if(num_random <image_rate) {return "Image";}
		else if(num_random<image_rate+video_rate) {return "Video";}
		else {return "Text";}
	}
	
	public static String decide_topic(List<String> topics_list, List<Double> topics_rate) {
		double randomGenerator = Math.round(Math.random());
		if (randomGenerator  < topics_rate.get(0)) {
			return topics_list.get(0);
		}
		else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)){
			return topics_list.get(1);
		}
		else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)){
			return topics_list.get(2);
		}
		else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)){
			return topics_list.get(3);
		}
		else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)){
			return topics_list.get(4);
		}
		else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)+topics_rate.get(5)){
			return topics_list.get(5);
		}
		else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)+topics_rate.get(5)+topics_rate.get(6)){
			return topics_list.get(6);
		}
		else if(randomGenerator  <topics_rate.get(0)+topics_rate.get(1)+topics_rate.get(2)+topics_rate.get(3)+topics_rate.get(4)+topics_rate.get(5)+topics_rate.get(6)+topics_rate.get(6)){
			return topics_list.get(7);
		}
		else {
			return topics_list.get(8);
		}
	}
	
	public static String decide_publication_desc(String topic, Map<String, List<String>> publication_description_dict) {
		List<String> descriptions_list = publication_description_dict.get(topic);
		Random randomGenerator = new Random();
		int desc_id = randomGenerator.nextInt(descriptions_list.size());
		return descriptions_list.get(desc_id);
	}
	
	public static Double decide_quality() {
		double num_random = Math.random();
		return num_random;
	}
	
	public static Map<String, List<String>> set_dict_pub_desc(ConfigurationModel configuration) {
		Map<String, List<String>> publication_description_dict = new HashMap<String, List<String>>();
		try {  
            String encoding = "UTF-8";  
            File file = new File(configuration.getInputFile3());  
            if (file.isFile() && file.exists()) { 
                	InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); 
                	BufferedReader br = new BufferedReader(read); 
                	
                	for(String line = br.readLine(); line !=null; line = br.readLine()) {
                		
                		String linedata[] = line.split(";");
                		String topic = linedata[0];
                		String description = linedata[1];
                		if (publication_description_dict.containsKey(topic)){
                			publication_description_dict.get(topic).add(description);
                		}
                		else {
                			List<String> list = Arrays.asList(description);
                			List<String> arraylist = new ArrayList<>(list);
                			publication_description_dict.put(topic, arraylist);
                		}
     
                    }
                	br.close();
                	}
		 }
         catch (Exception e) {  
            System.out.println("Fail with input file topic and description");  
            e.printStackTrace();  
        }  
		
		return publication_description_dict;
		
	}
	
	
	public static void writeUSerPublications(String path) {
		try (PrintWriter writer = new PrintWriter(path)) {
			StringBuilder sb = new StringBuilder();
			sb.append("User");
		    sb.append(',');
		    sb.append("Publications");
		    sb.append('\n');
	        int num_users = RV.Users.size();
	        User actual_user;
	        Integer numpub;
	        for(int i=0;i<num_users;i++) {
	        	actual_user = (User)RV.Users.get(i);
	        	sb.append(actual_user.getUser());
	        	sb.append(',');
	        	for (int j=0; j< actual_user.publications.size(); j++) {
	        		sb.append(actual_user.publications.get(j));
	        		sb.append(',');
	        	}
	        	sb.append('\n');
	        }
	        writer.write(sb.toString());
	        System.out.println("file user_publications done");
	        writer.close();
	        
		}
		catch (FileNotFoundException e) {
		    System.out.println(e.getMessage());
	    }
	}	
	
	public static void writeLikePublications(List<Publication> publications, String path) {
		Publication publi;
		int publi_id;
		try (PrintWriter writer = new PrintWriter(path)) {
			StringBuilder sb = new StringBuilder();
			sb.append("publication");
		    sb.append(',');
		    sb.append("userLike");
		    sb.append('\n');
	        for(int i=0;i<publications.size();i++) {
	        	publi = publications.get(i);
	        	publi_id = publi.getId();
	        	for(int j=0; j<publi.like_users.size(); j++) {
	        		sb.append(publi_id);
		        	sb.append(',');
	        		sb.append(publi.like_users.get(j));
	        		sb.append('\n');
	        	}
	        }
	        writer.write(sb.toString());
	        System.out.println("file like_publications done");
	        writer.close();
		}
		catch (FileNotFoundException e) {
		    System.out.println(e.getMessage());
	    }
	}	
	
	public static void writeCommentPublications(List<Publication> publications, String path) {
		Publication publi;
		int publi_id;
		try (PrintWriter writer = new PrintWriter(path)) {
			StringBuilder sb = new StringBuilder();
			sb.append("publication");
		    sb.append(',');
		    sb.append("userComment");
		    sb.append('\n');
	        for(int i=0;i<publications.size();i++) {
	        	publi = publications.get(i);
	        	publi_id = publi.getId();
	        	for(int j=0; j<publi.comment_users.size(); j++) {
	        		sb.append(publi_id);
		        	sb.append(',');
	        		sb.append(publi.comment_users.get(j));
		        	sb.append('\n');
	        	}
	        }
	        writer.write(sb.toString());
	        System.out.println("file comment_publications done");
	        writer.close();
		}
		catch (FileNotFoundException e) {
		    System.out.println(e.getMessage());
	    }
	}	
	
	public static void writeinfoPublications(List<Publication> publications, String path) {
		Publication publi;
		int publi_id;
		try (PrintWriter writer = new PrintWriter(path)) {
			StringBuilder sb = new StringBuilder();
			sb.append("publication, user, type, topic, publication_description, quality, num_likes, num_comments");
		    sb.append('\n');
	        for(int i=0;i<publications.size();i++) {
	        	publi = publications.get(i);
	        	publi_id = publi.getId();
	        	sb.append(publi_id);
	        	sb.append(',');
	        	sb.append(publi.getUser());
	        	sb.append(',');
	        	sb.append(publi.getType());
	        	sb.append(',');
	        	sb.append(publi.getTopic());
	        	sb.append(',');
	        	sb.append(publi.getPublicationDescription());
	        	sb.append(',');
	        	sb.append(publi.getQuality());
	        	sb.append(',');
	        	sb.append(publi.getNumLikes());
	        	sb.append(',');
	        	sb.append(publi.getNumComments());
	        	sb.append(',');
	        }
	        writer.write(sb.toString());
	        System.out.println("file info_publications done");
	        writer.close();
		}
		catch (FileNotFoundException e) {
		    System.out.println(e.getMessage());
	    }
	}
	
	public static void writeScoreGiven(String path) {
		User user;
		try (PrintWriter writer = new PrintWriter(path)) {
			StringBuilder sb = new StringBuilder();
			sb.append("user");
		    sb.append(',');
		    sb.append("publication");
		    sb.append(',');
		    sb.append("score");
		    sb.append('\n');
			for(int i=0; i<RV.Users.size();i++) {
				user = (User) RV.Users.get(i);
				for (Integer key : user.scores_given_d1.keySet()) {
					sb.append(user.getUser());
					sb.append(',');
					sb.append(key);
					sb.append(',');
					sb.append(user.scores_given_d1.get(key));
					sb.append('\n');	
				}

			}
			writer.write(sb.toString());
	        System.out.println("file scoreGiven_publications done");
	        writer.close();
		}
		catch (FileNotFoundException e) {
	    System.out.println(e.getMessage());
		}				
	}
	
}