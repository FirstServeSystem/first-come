package first_come.first_come;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FirstComeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstComeApplication.class, args);
	}

}
