package org.vaccom.vcmgt.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Bypass the web security of main application
 *
 * @author TrinhNX
 * @since 8/27/2021
 */
@TestConfiguration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DisableSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // Disable CSRF
        httpSecurity.csrf().disable()
                // Permit all requests without authentication
                .authorizeRequests().anyRequest().permitAll();
    }
}
