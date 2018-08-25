package game.job.jobs;

import game.job.jobtype.JobType;

public class Reporter implements Job {

	private final String jobName = "REPORTER";
	
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
