package org.tourgune.mdp.reports.main;

import java.util.ArrayList;
import java.util.List;

import org.tourgune.mdp.reports.db.Database;

public class Reports {

	private static List<String> envOptions = new ArrayList<String>(); //"old/new"
	private static List<String> taskOptions = new ArrayList<String>(); //"summary/geo/categories/ratings/reviews"
	private static List<String> accTypeNeeded = new ArrayList<String>(); //"ratings/reviews"
			
	public static void main(String[] args) {
		fillEnvOptions();
		fillTaskOptions();
		fillAccTypeNeeded();
		try {
			String env = null;
			String task = null;
			String accType = null;
			try {
				env = args[0];
				task = args[1];
				accType = args[2];
				if (!envOptions.contains(env) || !taskOptions.contains(task)) {
					System.out.println("[MDP-Reports] FATAL ERROR - Main. Arguments 'env' " + envOptions + ", 'task' " + taskOptions + " are needed.");
					System.exit(0);
				} 
			} catch (ArrayIndexOutOfBoundsException aioobe) { // Si no se pasan params pega una excepción
				if (task == null || !taskOptions.contains(task)) {
					System.out.println("[MDP-Reports] FATAL ERROR - Main. Arguments 'env' " + envOptions + ", 'task' " + taskOptions + " are needed.");
					System.exit(0);
				}
				if (accType == null && accTypeNeeded.contains(task)) {
					System.out.println("[MDP_Reports] FATAL ERROR - Main. Argument 'accType' is needed in conjuntion with 'task' = " + task);
					System.exit(0);
				}
			}
			if (accType == null && accTypeNeeded.contains(task)) {
				System.out.println("[MDP_Reports] FATAL ERROR - Main. Argument 'accType' is needed in conjuntion with 'task' = " + task);
				System.exit(0);
			}
			Database db = new Database(env);
			
			if (task.equals("summary"))
				Core.summary(db, env);
			if (task.equals("geo"))
				Core.alertGeoInfo(db, env);
			if (task.equals("categories"))
				Core.alertCategories(db, env);
			if (task.equals("ratings"))
				Core.alertRatings(db, env, accType);
			if (task.equals("reviews"))
				Core.alertReviews(db, env, accType);
//			if (task.equals("thresholds"))
//				Core.alertThresholds(db, env);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void fillEnvOptions() {
		envOptions.add("old");
		envOptions.add("new");
	}
	private static void fillTaskOptions() {
		taskOptions.add("summary");
		taskOptions.add("geo");
		taskOptions.add("categories");
		taskOptions.add("ratings");
		taskOptions.add("reviews");
//		taskOptions.add("thresholds");
	}
	private static void fillAccTypeNeeded() {
		accTypeNeeded.add("ratings");
		accTypeNeeded.add("reviews");
	}
}
