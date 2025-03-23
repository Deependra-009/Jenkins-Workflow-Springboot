package com.jenkins.Jenkins_Workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JenkinsWorkflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(JenkinsWorkflowApplication.class, args);
	}

	public void Test(){
		System.out.println("Hello Mr");
	}

}
