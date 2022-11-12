package com.yxb.warehouseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients //开启feign功能
public class WarehouseserviceApplication {

	public static void main(String[] args) {

		SpringApplication.run(WarehouseserviceApplication.class, args);
	}


}
