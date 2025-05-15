package com.capgemini.equipment_rental.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@NotBlank(message = "Name is required")
	@Size(min = 2, message = "Name must have at least 2 characters")
	@Column(name = "name")
	private String name;

	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	@Column(name = "email", unique = true)
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password must be at least 8 characters long")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,}$", message = "Password must have 1 uppercase, 1 lowercase, and 1 special character.")
	@Column(name = "password")
	private String password;

	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "^[7-9]\\d{9}$", message = "Phone number must be 10 digits and start with 7-9")
	@Column(name = "phone", length = 10)
	private String phone;

	public enum UserType {
		USER, SUPPLIER
	}

	@NotNull(message = "UserType is required")
	@Enumerated(EnumType.STRING)
	@Column(name = "user_type")
	private UserType userType;

	public Users() {
		super();
	}

	public Users(Long userId,
			@NotBlank(message = "Name is required") @Size(min = 2, message = "Name must have at least 2 characters") String name,
			@NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email,
			@NotBlank(message = "Password is required") @Size(min = 8, message = "Password must be at least 8 characters long") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,}$", message = "Password must have 1 uppercase, 1 lowercase, and 1 special character.") String password,
			@NotBlank(message = "Phone number is required") @Pattern(regexp = "^[7-9]\\d{9}$", message = "Phone number must be 10 digits and start with 7-9") String phone,
			@NotBlank(message = "UserType is required") UserType userType) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.userType = userType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password + ", phone="
				+ phone + ", userType=" + userType + "]";
	}
}
