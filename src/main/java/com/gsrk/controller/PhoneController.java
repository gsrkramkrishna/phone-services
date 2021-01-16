package com.gsrk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsrk.model.PhoneContacts;
import com.gsrk.service.IPhoneService;

@RestController
@RequestMapping("/v1")
public class PhoneController {

	@Autowired
	private IPhoneService phoneService;
	
	@PostMapping("/phone")
	public ResponseEntity<PhoneContacts> save(@RequestBody PhoneContacts phoneContacts){
		PhoneContacts ph = phoneService.save(phoneContacts);
		return ResponseEntity.ok(ph);
	}
	
	@PutMapping("/phone/{phoneId}")
	public ResponseEntity<Object> updateByPhoneId(@RequestBody PhoneContacts phoneContacts,@PathVariable long phoneId){
		phoneService.update(phoneContacts,phoneId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/phone/{name}")
	public ResponseEntity<Object> deleteByName(@PathVariable String name){
		phoneService.deleteByName(name);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@DeleteMapping("/phone/{phoneId}")
	public ResponseEntity<Object> deleteByPhoneId(@PathVariable long phoneId){
		phoneService.deleteByPhoneId(phoneId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@GetMapping("/phone/{name}")
	public ResponseEntity<List<PhoneContacts>> getPhoneContacts(@PathVariable String name){
		List<PhoneContacts> phoneContacts = phoneService.getPhoneContacts(name);
		return ResponseEntity.ok(phoneContacts);
	}
}
