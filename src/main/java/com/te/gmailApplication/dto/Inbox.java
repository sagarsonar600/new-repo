package com.te.gmailApplication.dto;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Inbox implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int message_Id;
	private String message;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_Id")
	private Account account;
}
