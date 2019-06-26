package com.jsan.github.doc_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DocManagerApplication {


//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(DocManagerApplication.class);
//    }


    public static void main(String[] args) {
        SpringApplication.run(DocManagerApplication.class, args);
    }

}
