package test;

import game.job.jobs.Job;
import game.job.jobs.Mafia;
import game.job.jobs.Reporter;

public class Test {
	
	public static void main(String[] args) {
		test(Reporter.class);
	}
	public static void test(Class<?> searchType) {
		Job job = new Reporter();
		Class<?> type = job.getClass();
		
		if(job.getClass() == searchType ) {
			System.out.println("true");
		}
	} 
}
