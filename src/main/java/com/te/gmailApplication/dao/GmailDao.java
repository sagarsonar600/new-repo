package com.te.gmailApplication.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.te.gmailApplication.dto.Account;
import com.te.gmailApplication.dto.Inbox;

public class GmailDao {

	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("gmail");
	private static EntityManager manager;
	private static EntityTransaction transaction;
	private static String username;
	private static String pass;
	private static String email;

	public static void register(String username, String pass, String email) {
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		transaction.begin();
		Account account = new Account();
		account.setUser_Name(username);
		account.setPassword(pass);
		account.setEmail(email);
		manager.persist(account);
		transaction.commit();
	}

	public static Account login(String email, String password) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("gmail");
		manager = factory.createEntityManager();
		String fetchAccount = "from Account where email= :emailId and password= :password";
		Query query = manager.createQuery(fetchAccount);
		query.setParameter("emailId", email);
		query.setParameter("password", password);
		Account account = (Account) query.getSingleResult();
		System.out.println("Welcome " + account.getUser_Name());
		return account;
	}

	public static void compose(String f_message, String s_message, Account account) {
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		transaction.begin();
		Account account2 = manager.find(Account.class, account.getUser_Id());
		if (account2 != null)
			manager.merge(account2);
		// first message
		Inbox inbox = new Inbox();
		inbox.setMessage(f_message);
		inbox.setAccount(account2);
		// second message
		Inbox inbox2 = new Inbox();
		inbox2.setMessage(s_message);
		inbox2.setAccount(account2);
		// list of messages
		List<Inbox> listOfMessages = new ArrayList<Inbox>();
		listOfMessages.add(inbox);
		listOfMessages.add(inbox2);

		account2.setListOfMails(listOfMessages);
		manager.persist(account2);
		System.out.println("Successfully saved....");
		transaction.commit();
	}

	public static List<Inbox> fetchAllMails(Account account) {
		manager = factory.createEntityManager();
		int id= account.getUser_Id();
		String query3= "from Inbox where user_Id= :userid";
		Query query4= manager.createQuery(query3);
		query4.setParameter("userid", id);
		List<Inbox> list= query4.getResultList();
		account.setListOfMails(list);
		return list;
	}
}
