package org.vaccom.vcmgt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.vaccom.vcmgt.property.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class VaccomApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaccomApplication.class, args);
	}

}
