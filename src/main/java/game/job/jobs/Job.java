package game.job.jobs;

import game.job.jobtype.JobType;

public interface Job {
	public void doNight();
	public String getJobName();
	public JobType getJobType();
}
