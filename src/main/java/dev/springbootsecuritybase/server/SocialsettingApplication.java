package dev.springbootsecuritybase.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class SocialsettingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialsettingApplication.class, args);
		System.out.println(SpringVersion.getVersion());
	}

}
