package eu.emsodev.observations;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@ComponentScan(basePackages = "io.swagger")
//@EnableOAuth2Sso
//@EnableResourceServer
public class ObservationsApplication {
	

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

}
