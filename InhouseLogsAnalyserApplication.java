package com.loggertool.inhouselogsanalyser;

import java.io.FileNotFoundException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InhouseLogsAnalyserApplication {
	public static void main(String[] args) throws FileNotFoundException {
		
		SpringApplication.run(InhouseLogsAnalyserApplication.class, args);
		LogsProcessor reader = new LogsProcessor();
		reader.fetchFileContent();		
	}

}
