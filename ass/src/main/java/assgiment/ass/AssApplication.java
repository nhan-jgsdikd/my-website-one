package assgiment.ass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "assgiment.ass",
    "assgiment.ass.SecurityConfig",
    "assgiment.ass.Controller"
})
public class AssApplication {
    public static void main(String[] args) {
        SpringApplication.run(AssApplication.class, args);
    }
}