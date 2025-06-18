package com.example.demo.crud.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"user\"")
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;

	private String name;
	private String email;
	private String address;
	private String age;
	private String gender;
	private String imageLocation;
	private String resumeLocation;

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		this.ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	public String getResumeLocation() {
		return resumeLocation;
	}

	public void setResumeLocation(String resumeLocation) {
		this.resumeLocation = resumeLocation;
	}

	@Override
	public String toString() {
		return "UserDetails [ID=" + ID + ", name=" + name + ", email=" + email + ", address=" + address + ", age=" + age
				+ ", gender=" + gender + ", imageLocation=" + imageLocation + ", resumeLocation=" + resumeLocation
				+ "]";
	}

}
