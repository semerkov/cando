package com.appspot.i_can_do.service;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.appspot.i_can_do.master.model.*;
import com.appspot.i_can_do.master.security.Permission;
import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.exceptions.LoginFailedException;
import com.appspot.i_can_do.service.exceptions.LoginNameExistException;
import com.appspot.i_can_do.service.exceptions.LoginNameNotFoundException;
import com.appspot.i_can_do.service.utils.Crypto;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class CanDOSecurityService implements ICanDOSecurityService {
	private static final EntityManager em = EMF.get().createEntityManager();
	private static CanDOSecurityService security;
	private static final Logger log = Logger
			.getLogger(CanDOSecurityService.class.getName());

	public static CanDOSecurityService instance() {
		if(security==null) security = new CanDOSecurityService();
		return security;
	}


	@Override
	public User addNewUser(User user, String password)
			throws LoginNameExistException {
		User userToAdd = null;
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			 userToAdd = findUser(user.getEmail());
			 if (userToAdd == null) {
				userToAdd = user;
				byte[] pHash = Crypto.hashPassword(password);
				userToAdd.setPasswordHash(pHash);
				em.persist(userToAdd);
				txn.commit();
				
				txn.begin();
				ProfileKeeper pKeeper = new ProfileKeeper(userToAdd.getKey(), userToAdd.getProfile().getKey(), Permission.Owner);
				em.persist(pKeeper);
				txn.commit();
				
				log.info("User created: "+user.getEmail());
			} else {
				throw new LoginNameExistException();
			}
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return userToAdd;
	}

	@Override
	public User findUser(String email) throws NoResultException{
		Query query = em.createNamedQuery("User.getUser");
		query.setParameter("userEmail", email);
		User u = null;
		try{
		u = (User) query.getSingleResult();
		} catch(NoResultException ex){}
		return u;	
	}
	
	public User findUserBykey(String key){
		Key k = KeyFactory.stringToKey(key);
		return em.find(User.class, k);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers() {
		return (List<User>) em.createNamedQuery("User.getUsers")
				.getResultList();
	}

	@Override
	public User saveUser(User user) {
        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            em.merge(user);
            txn.commit();
            }
        finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
		return user;
	}

	@Override
	public User login(String username, String password)
			throws LoginNameNotFoundException, LoginFailedException {
		log.info("Login Attempt: " + username);
		User user = doLogin(username, password);
		return user;
	}

	private User doLogin(String useremail, String password)
			throws LoginNameNotFoundException, LoginFailedException {
		User user = findUser(useremail);
		if (user != null) {
			byte[] hashPassword = Crypto.hashPassword(password);
			if (user.isDisabled()
					|| !Arrays.equals(hashPassword, user.getPasswordHash())) {
				log.info("Login Failed! " + useremail);
				throw new LoginFailedException();
			}
		} else {
			log.info("User not found: " + useremail);
			throw new LoginNameNotFoundException();
		}

		return user;
	}

    public void removeAddress(Profile profile, Key address){
        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            if(address != null){
                Address address1 = em.find(Address.class, address);
                profile.getAddresses().remove(address1);
                txn.commit();
                log.warning("Address removed: " + address1);
            }else{
                log.warning("Address removed key is null!!");
            }
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public void removeEmail(Profile profile, Key email){
        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            if(email != null){
                Email email1 = em.find(Email.class, email);
                profile.getEmails().remove(email1);
                txn.commit();
                log.warning("Email removed: " + email1);
            }else{
                log.warning("Email removed key is null!!");
            }
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public void removePhone(Profile profile, Key phone){
        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            if(phone != null){
                Phone phone1 = em.find(Phone.class, phone);
                profile.getPhones().remove(phone1);
                txn.commit();
                log.warning("Phone removed: " + phone1);
            }else{
                log.warning("Phone removed key is null!!");
            }
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

}
