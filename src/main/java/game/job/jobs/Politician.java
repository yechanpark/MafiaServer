package game.job.jobs;

import game.job.jobtype.JobType;

public class Politician implements Job {

	private final String jobName = "POLITICIAN";
	
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
		return JobType.CIVIL;
	}
	
}
