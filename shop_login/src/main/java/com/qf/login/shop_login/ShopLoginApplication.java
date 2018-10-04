package com.qf.login.shop_login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ShopLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopLoginApplication.class, args);
	}
}
