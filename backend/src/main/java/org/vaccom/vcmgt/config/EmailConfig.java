package org.vaccom.vcmgt.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author TrinhNX
 * @since 8/29/2021
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "email")
@PropertySource(value = "classpath:email.yml", factory = YamlPropertySourceFactory.class)
public class EmailConfig {
    private String support;
    private EmailMeta createAccount;
    private EmailMeta accessAccount;
    private EmailMeta vaccinate;

    @Setter
    @Getter
    public static class EmailMeta {
        private String body;
        private String subject;
    }
}
