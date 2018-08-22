package user;

import job.jobs.Job;
import protocol.MessageSender;

public class User {
	private String id;
	private String password;
	
	private Job job;
	
	private MessageSender messageSender;
}
