package com.jsan.github.doc_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class DocManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocManagerApplication.class, args);
	}

}
