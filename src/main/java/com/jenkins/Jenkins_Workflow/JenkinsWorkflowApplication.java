package com.jenkins.Jenkins_Workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JenkinsWorkflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(JenkinsWorkflowApplication.class, args);
	}

	/*

📌 Steps in This Workflow
✅ Runs on push and pull request events
✅ Checks out the code
✅ Sets up Java & Maven
✅ Builds the project (mvn clean package)
✅ Runs Unit & Integration Tests (mvn test)
✅ Performs Static Code Analysis (Checkstyle + OWASP Security Scan)
✅ Blocks Merge if tests fail

	* */
	public void Test(){
		System.out.println("CI/CD Pipeline Example");
	}

}
