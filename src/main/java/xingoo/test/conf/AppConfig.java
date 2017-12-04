package xingoo.test.conf;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 应用主配置，为所有的请求增加跨域配置
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("xingoo.test")
public class AppConfig {
}