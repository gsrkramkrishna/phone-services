package com.gsrk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name="Phone_Contacts",indexes = {@Index(name = "phone_name_index",  columnList="name", unique = true),@Index(name = "phone_number_index",  columnList="mobile_number", unique = true)})
public class PhoneContacts {
	
	@Id
	@GeneratedValue
	@Column(name="phone_id")
	private long phoneId;
	@Column
	@NotEmpty
	private String name;
	@Column(name="mobile_number")
	private String mobileNumber;
	@Column(name="work_mobile_number")
	private String workMobileNumber;
	@Column(name="email_id")
	@Email
	private String emailId;
	
	
}
