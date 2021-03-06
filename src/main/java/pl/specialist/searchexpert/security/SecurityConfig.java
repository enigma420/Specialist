package pl.specialist.searchexpert.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.specialist.searchexpert.services.auth.CustomUserDetailsService;

import static pl.specialist.searchexpert.security.SecurityConstants.SIGN_UP_AND_LOGIN_URLS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint unAuthorizedHandler;

    private final CustomUserDetailsService customUserDetailsService;



    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint unAuthorizedHandler, CustomUserDetailsService customUserDetailsService) {
        this.unAuthorizedHandler = unAuthorizedHandler;
        this.customUserDetailsService = customUserDetailsService;
    }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unAuthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/specialist/**").permitAll()
                .antMatchers("/api/customer/**").permitAll()
                .antMatchers("/api/opinion/**").permitAll()
                .antMatchers("/api/commission/**").permitAll()
                .antMatchers(SIGN_UP_AND_LOGIN_URLS).permitAll()
                .anyRequest().authenticated();
                http.addFilterAt(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}


//.access("hasAuthority('CUSTOMER') or hasAuthority('SPECIALIST')")
//        ccess("hasAuthority('CUSTOMER') or hasAuthority('SPECIALIST')")
//        cess("hasAuthority('CUSTOMER')")
//        .access("hasAuthority('CUSTOMER') or hasAuthority('SPECIALIST')")