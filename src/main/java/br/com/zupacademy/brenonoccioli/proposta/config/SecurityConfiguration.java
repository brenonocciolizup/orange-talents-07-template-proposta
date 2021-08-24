package br.com.zupacademy.brenonoccioli.proposta.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests(authorizeRequests ->
            authorizeRequests
                    .antMatchers(HttpMethod.GET, "/api/propostas/**").hasAuthority("SCOPE_escopo-proposta")
                    .antMatchers(HttpMethod.GET, "/api/actuator/**").hasAuthority("SCOPE_escopo-proposta")
                    .antMatchers(HttpMethod.POST, "/api/propostas/**").hasAuthority("SCOPE_escopo-proposta")
                    .antMatchers(HttpMethod.POST, "/api/cartoes/**").hasAuthority("SCOPE_escopo-proposta")
                    .anyRequest().authenticated()
        ).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}
