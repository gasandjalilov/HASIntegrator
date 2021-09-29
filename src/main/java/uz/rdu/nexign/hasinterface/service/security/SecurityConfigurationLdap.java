package uz.rdu.nexign.hasinterface.service.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

@Configuration
@Order(2)
public class SecurityConfigurationLdap extends WebSecurityConfigurerAdapter{
	
	private String ldapUrls="ldap://10.7.8.17:3268/";
	
	private String ldapBaseDc="dc=local,dc=domain";
	
	private String ldapSecurityPrincipal="blitz_admin@local.domain";
	
	private String ldapPrincipalPassword="K$yw0rds";
	
	private String ldapUserDnPattern;
	
	private String ldapEnabled;
	
	
	@Override
	 protected void configure(HttpSecurity http) throws Exception {
	http.
		authorizeRequests().
			antMatchers("/ui/**").authenticated()
			.and()
			.formLogin()
				.permitAll()
				.and()
			.logout()
				.permitAll();
		
	 http
	 .csrf().disable()
     .authorizeRequests()
       .antMatchers("/VAADIN/**","/PUSH/**", "/UIDL/**", "/vaadinServlet/UIDL/**", "/resources/**","/vaadinServlet/HEARTBEAT/**",
    		   "/login", "/login**", "/login/**", "/manifest.json", "/icons/**", "/images/**",
               "/frontend/**","/vaadinServlet/**",
               "/themes/**",
               "/resources/**",
       			"/CEOForm/",
               "/webjars/**",
               "/ceo/**",
               "/frontend-es5/**", "/frontend-es6/**").permitAll()
     .antMatchers("/HEARTBEAT/**").authenticated()
     .anyRequest().authenticated();
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
	
	 @Override
	 public void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.ldapAuthentication()
		 .userSearchBase("ou=New")
		 .userSearchFilter("sAMAccountName={0}") //sAMAccountName
         .contextSource()
        	.url(ldapUrls+ldapBaseDc)
         	.root(ldapBaseDc)
         	.managerDn("blitz_admin@local.domain")
         	.managerPassword("K$yw0rds");
	 }

}

