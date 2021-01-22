package com.example.controllers;

import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.utils.DrlManager;

@RestController
@RequestMapping("drl")	
public class DrlsRestController {

	@GetMapping("/keys")
	public List<String> getDrlKeyList() {
		return DrlManager.getKeys();
	}
	
	@GetMapping("/")
	public Map<String,String> getDrls(){
		return DrlManager.getAllDrls();
	}
	
	@GetMapping("{ID}")
	public String getDrl(@PathVariable String ID) {
		return DrlManager.getDrl(ID);
	}
}
