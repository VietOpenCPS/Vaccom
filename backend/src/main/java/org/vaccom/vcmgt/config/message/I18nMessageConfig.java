package org.vaccom.vcmgt.config.message;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * TO support i18n message based on key and the messages folder is in /resources/languages.xxx
 *
 * @author TrinhNX
 * @since 8/27/2021
 */
@Configuration
public class I18nMessageConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("language");
        source.setFallbackToSystemLocale(false);
        return source;
    }
}
