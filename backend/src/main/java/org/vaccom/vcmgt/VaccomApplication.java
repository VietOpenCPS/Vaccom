package org.vaccom.vcmgt;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.vaccom.vcmgt.action.NguoiDungAction;
import org.vaccom.vcmgt.action.impl.NguoiDungActionImpl;
import org.vaccom.vcmgt.entity.NguoiDung;
import org.vaccom.vcmgt.property.FileStorageProperties;


import java.io.IOException;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class VaccomApplication {

	@Autowired
	private NguoiDungAction nguoiDungAction;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(VaccomApplication.class, args);
	}
}
