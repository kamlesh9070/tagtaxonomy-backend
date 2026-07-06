package com.archiva.tagtaxonomy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.archiva.tagtaxonomy")
public class TagTaxonomyApplication {
    public static void main(String[] args) {
        SpringApplication.run(TagTaxonomyApplication.class, args);
    }
}
