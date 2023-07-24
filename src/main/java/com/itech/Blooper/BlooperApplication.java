package com.itech.Blooper;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
//import springfox.documentation.RequestHandler;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication

@OpenAPIDefinition
		(
				info = @Info
						(
								title = "iTech Blooper of Sensitives",
								version = "1.0.0",
								description = "Sensitive Words Blooper Project",
								contact = @Contact(
										name = "iTech Development",
										url = "www.itech.co.za",
										email = "support@itech.co.za"
								),
								license = @License(
										name = "iTech Development license",
										url = "www.itech.co.za/licensing"
								)
						)
		)

public class BlooperApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlooperApplication.class, args);
		System.setProperty("java.library.path", "bin/mssql-jdbc_auth-12.2.0.x64.dll");

	}

}
