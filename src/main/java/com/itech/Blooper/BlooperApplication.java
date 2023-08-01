package com.itech.Blooper;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

	}

}
