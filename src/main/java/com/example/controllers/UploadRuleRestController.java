package com.example.controllers;

import com.example.config.DroolsBeanFactory;
import com.example.utils.DrlManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("upload")
public class UploadRuleRestController {

	@PostMapping("/")
	public void addRule(@RequestParam("file") MultipartFile file) throws IOException {

		String drl = new DroolsBeanFactory().getDrlFromExcel(file.getBytes());
		DrlManager.insertDrl("rule1", drl);

	}

}
