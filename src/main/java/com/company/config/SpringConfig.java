package com.company.config;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Authentication
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
       /* auth.inMemoryAuthentication()
                .withUser("Ali").password("{bcrypt}$2a$10$V93CWoH3NxAPC7VzPd9ouuU8PWvZWYdoo94H3HOZ8kFSkBAvYssEe").roles("ADMIN")
                .and()
                .withUser("Vali").password("{noop}valish123").roles("PROFILE");*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Authorization
        http.authorizeRequests()
                .antMatchers("/api/v1/auth/*").permitAll()
                .antMatchers("/api/v1/report/*").permitAll()
                .antMatchers("/api/v1/report/*").hasRole("ADMIN")

                .antMatchers("/api/v1/email/verification/*").permitAll()

                .antMatchers("/api/v1/profile/change/*").hasAnyRole("USER","ADMIN")
                .antMatchers("api/v1/profile/adm/*","api/v1/profile/adm/**").hasRole("ADMIN")

                .antMatchers("/api/v1/category/adm/*","api/v1/category/adm/**").hasRole("ADMIN")
                .antMatchers("/api/v1/category/public/*").permitAll()

                .antMatchers("/api/v1/tag/public/*").hasAnyRole("USER","ADMIN","MODERATOR")
                .antMatchers("/api/v1/tag/adm/*").hasAnyRole("ADMIN","MODERATOR")

                .antMatchers("/api/v1/attach/*","/api/v1/attach/**")
                .permitAll()

                .antMatchers("/api/v1/attach/adm/**").hasRole("ADMIN")

                .antMatchers("/api/v1/video/public/**").
                hasAnyRole("USER","ADMIN","MODERATOR")

                .antMatchers("/api/v1/channel/public/**").
                hasAnyRole("USER","ADMIN","MODERATOR")

                .antMatchers("/api/v1/channel/change/**").
                hasAnyRole("USER","ADMIN")

                .antMatchers("/api/v1/playlist/public/**",
                "/api/v1/playlist/*").
                hasAnyRole("USER","ADMIN","MODERATOR")

                .antMatchers("/api/v1/playlist/video/public/**").
                hasAnyRole("USER","ADMIN","MODERATOR")

                .antMatchers("/api/v1/playlist/adm/**").
                hasRole("ADMIN")



                .anyRequest().authenticated().and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors().disable().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String md5 = MD5Util.getMd5(rawPassword.toString());
                return md5.equals(encodedPassword);
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
