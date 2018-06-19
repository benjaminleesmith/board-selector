package org.benjaminsmith.boardselector.trustedreviewserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private boolean httpsDisabled;
    private String adminUsername;
    private String adminPassword;

    public SecurityConfiguration(
            @Value("${https.disabled}") boolean httpsDisabled,
            @Value("${admin.username}") String adminUsername,
            @Value("${admin.password}") String adminPassword
    ) {
        this.httpsDisabled = httpsDisabled;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/admin/trusted_reviews").hasRole("USER")
                .and().httpBasic()
                .and().csrf().disable();

        if(!httpsDisabled) {
            http.requiresChannel().anyRequest().requiresSecure();
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser("admin").password("password").roles("USER");
    }
}
