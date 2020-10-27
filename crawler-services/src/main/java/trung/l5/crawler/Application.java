package trung.l5.crawler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("trung.l5.crawler.model")
@EnableJpaRepositories(basePackages = { "trung.l5.crawler.repo" })
public class Application implements CommandLineRunner {

    @Value("${phantomjs.path}")
    String phantomjsPath;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.setProperty("phantomjs.binary.path", phantomjsPath);

        
    }
}
