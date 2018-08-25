package game.job.jobs;

import game.job.jobtype.JobType;

public class Mafia implements Job {

	private final String jobName = "MAFIA";
	
	@Override
	public void doNight() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getJobName() {
		return jobName;
	}

	@Override
	public JobType getJobType() {
		return JobType.MAFIA;
	}	
	
}
