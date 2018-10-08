package com.qf.shop_order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import utils.loginAop;

@SpringBootApplication
@EnableTransactionManagement
public class ShopOrderApplication {

	public static void main(String[] args) {

		SpringApplication.run(ShopOrderApplication.class, args);
	}
	@Bean
	public loginAop getloginAop(){
		loginAop loginAop = new loginAop();
		return loginAop;
	}
}
