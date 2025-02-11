package kr.kookmin.jeongo3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Jeongo3Application {

	public static void main(String[] args) {
		SpringApplication.run(Jeongo3Application.class, args);
	}

}
