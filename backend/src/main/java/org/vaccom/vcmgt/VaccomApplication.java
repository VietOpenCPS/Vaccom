package org.vaccom.vcmgt;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.vaccom.vcmgt.property.FileStorageProperties;


import java.io.IOException;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class VaccomApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(VaccomApplication.class, args);
	}
}
