package com.archiva.tagtaxonomy.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.archiva.tagtaxonomy")
@EntityScan(basePackages = "com.archiva.tagtaxonomy")
public class TagTaxonomyApplication {
    public static void main(String[] args) {
        SpringApplication.run(TagTaxonomyApplication.class, args);
    }
}
