package com.poly.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.*;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {
	@Id
	String username;
	String password;
	String fullname;
	String email;
	String photo;
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Order> orders;

	@JsonIgnore
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
	List<Authority> authorities;
}
