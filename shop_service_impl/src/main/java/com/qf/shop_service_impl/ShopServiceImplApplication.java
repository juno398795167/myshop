package com.qf.shop_service_impl;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan(value = "com.qf.shop_service_impl.com.qf.service")
@MapperScan("com.qf")
public class ShopServiceImplApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopServiceImplApplication.class, args);
	}
}
