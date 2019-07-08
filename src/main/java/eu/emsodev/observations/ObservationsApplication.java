package eu.emsodev.observations;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@ComponentScan(basePackages = "io.swagger")
//@EnableOAuth2Sso
//@EnableResourceServer
public class ObservationsApplication{
	

	public static void main(String[] args) {
		SpringApplication.run(ObservationsApplication.class, args);
	}
	
	
	
	class ExitException extends RuntimeException implements ExitCodeGenerator {
		private static final long serialVersionUID = 1L;

		@Override
		public int getExitCode() {
			return 10;
		}

	}
	
	
//	//@EnableOAuth2Sso
//	@Configuration
//	public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//	    @Override
//	    protected void configure(HttpSecurity http) throws Exception {
//	        http
//	                .csrf()
//	                    .disable()
//	                .antMatcher("/**")
//	                .authorizeRequests()
//	                .antMatchers("/", "/index.html","/swagger-ui.html")
//	                    .permitAll()
//	                .anyRequest()
//	                    .authenticated();
//	    }
//
//	}

}
