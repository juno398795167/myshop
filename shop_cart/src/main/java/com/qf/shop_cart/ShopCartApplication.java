package com.qf.shop_cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import utils.loginAop;

@SpringBootApplication
public class ShopCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopCartApplication.class, args);
	}
	@Bean
	public loginAop getloginAop(){
		loginAop loginAop = new loginAop();
		return loginAop;
	}
}
