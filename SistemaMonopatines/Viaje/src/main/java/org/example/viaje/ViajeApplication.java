package org.example.viaje;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

//para poder accerde al swagger hay que activar este en vez del otro!!!
/*@SpringBootApplication(exclude = {
       org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})*/
@SpringBootApplication
@EnableFeignClients // anotación que le dice a Spring que busque las interfaces que tengan @FeignClient.
public class ViajeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViajeApplication.class, args);
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
		return builder -> builder.findModulesViaServiceLoader(true);
	}
}
