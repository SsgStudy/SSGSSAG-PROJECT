package com.ssg.ssgssag;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ssg.ssgssag.mapper")
public class SsgssagApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsgssagApplication.class, args);
	}

}
