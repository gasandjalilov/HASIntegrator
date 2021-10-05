package uz.rdu.nexign.hasinterface.service.security;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Order(1)
@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
public class SecurityConfigurationKeycloak extends KeycloakWebSecurityConfigurerAdapter {

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable().authorizeRequests()
                .antMatchers("/ui/**").hasAuthority("front-office")
                .antMatchers("/VAADIN/**","/PUSH/**", "/UIDL/**", "/vaadinServlet/UIDL/**", "/resources/**","/vaadinServlet/HEARTBEAT/**",
                        "/login", "/login**", "/login/**", "/manifest.json", "/icons/**", "/images/**",
                        "/frontend/**","/vaadinServlet/**",
                        "/themes/**",
                        "/resources/**",
                        "/CEOForm/",
                        "/webjars/**",
                        "/ceo/**",
                        "/frontend-es5/**", "/frontend-es6/**").permitAll()
                .antMatchers("/HEARTBEAT/**").permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/VAADIN/**",
                        "/tools/**",
                        "/themes/**",
                        "/CEOForm/",
                        "/VAADIN/**", "/HEARTBEAT/**", "/UIDL/**", "/resources/**",
                        "/favicon.ico","/vaadinServlet/**",
                        "/manifest.json",
                        "/ceo/**",
                        "/icons/**",
                        "/resources/**",
                        "/images/**",
                        "/frontend/**",
                        "/webjars/**",
                        "*www.lex.uz*",
                        "/frontend-es5/**", "/frontend-es6/**");
    }

}
