package com.garage.test.conf;

import com.garage.config.ApplicationConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ruslan Yaniuk
 * @date June 2016
 */
@Configuration
@ComponentScan("com.garage.test.utils")
public class TestApplicationConfig extends ApplicationConfig {
}
