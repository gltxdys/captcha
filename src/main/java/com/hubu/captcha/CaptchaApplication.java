package com.hubu.captcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/a")
public class CaptchaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaptchaApplication.class, args);
	}

	@GetMapping("/b")
	public String test(){
		return "success";
	}
}
