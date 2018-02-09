package xingoo.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import xingoo.test.conf.AppConfig;

/**
 * Spring Boot 应用入口
 */
@SpringBootApplication
@Import(AppConfig.class)
@MapperScan("xingoo.test.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}