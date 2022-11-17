package com.yxb.warehouseservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients //开启feign功能
@MapperScan("com.yxb.warehouseservice.dao")
public class WarehouseserviceApplication {

	public static void main(String[] args) {

		SpringApplication.run(WarehouseserviceApplication.class, args);
	}


}
