package com.gsrk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.gsrk.model.PhoneContacts;
import com.gsrk.repository.PhoneRespository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PhoneServiceImpl implements IPhoneService{
	
	@Autowired
	private PhoneRespository phoneRespository;

	public PhoneContacts save(PhoneContacts phoneContacts) {
		// TODO Auto-generated method stub
		Optional<PhoneContacts> phoneContactsOpt = phoneRespository.findByName(phoneContacts.getName());
		if(phoneContactsOpt.isPresent()) {
			log.error("Phone contacts not found by name:{}",phoneContacts.getName());
			throw new HttpClientErrorException(HttpStatus.CONFLICT, "Phone contacts  found by name:"+phoneContacts.getName());
		}
		PhoneContacts dbPhone = phoneRespository.save(phoneContacts);
		return dbPhone;
	}

	public void update(PhoneContacts phoneContacts,long phoneId) {
		// TODO Auto-generated method stub
		
		Optional<PhoneContacts> phoneContactsOpt = phoneRespository.findByPhoneId(phoneId);
		if(!phoneContactsOpt.isPresent()) {
			log.error("Phone contacts not found by phone id:{}",phoneId);
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Phone contacts not found by phone id:"+phoneId);
		}
		PhoneContacts dbPC = phoneContactsOpt.get();
		dbPC.setEmailId(phoneContacts.getEmailId());
		dbPC.setName(phoneContacts.getName());
		dbPC.setMobileNumber(phoneContacts.getMobileNumber());
		dbPC.setWorkMobileNumber(phoneContacts.getWorkMobileNumber());
	}

	public void deleteByName(String name) {
		// TODO Auto-generated method stub
		Optional<PhoneContacts> phoneContactsOpt = phoneRespository.findByName(name);
		if(!phoneContactsOpt.isPresent()) {
			log.error("Phone contacts not found by name:{}",name);
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Phone contacts not found by name:"+name);
		}
		phoneRespository.delete(phoneContactsOpt.get());
	}
	
	public void deleteByPhoneId(long phoneId) {
		// TODO Auto-generated method stub
		Optional<PhoneContacts> phoneContactsOpt = phoneRespository.findByPhoneId(phoneId);
		if(!phoneContactsOpt.isPresent()) {
			log.error("Phone contacts not found by phone id:{}",phoneId);
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Phone contacts not found by phone id:"+phoneId);
		}
		phoneRespository.delete(phoneContactsOpt.get());
	}

	public List<PhoneContacts> getPhoneContacts(String name) {
		// TODO Auto-generated method stub
		
		List<PhoneContacts> phoneContactsList = phoneRespository.findByNameContaining(name);
		return phoneContactsList;
	}

}
