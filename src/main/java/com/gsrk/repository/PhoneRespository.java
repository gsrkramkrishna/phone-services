package com.gsrk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gsrk.model.PhoneContacts;

@Repository
public interface PhoneRespository extends CrudRepository<PhoneContacts, String>{
	
	Optional<PhoneContacts> findByName(String name);
	Optional<PhoneContacts> findByPhoneId(long phoneId);
	List<PhoneContacts> findByNameContaining(String name);

}
