package com.rilo.hris;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.rilo.hris.property.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class HrisApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrisApplication.class, args);
	}

}
