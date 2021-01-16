package com.gsrk.service;

import java.util.List;

import com.gsrk.model.PhoneContacts;

public interface IPhoneService {
	
	PhoneContacts save(PhoneContacts phoneContacts);
	void update(PhoneContacts phoneContacts,long phoneId);
	void deleteByName(String name);
	void deleteByPhoneId(long phoneId);
	List<PhoneContacts> getPhoneContacts(String name);
}
