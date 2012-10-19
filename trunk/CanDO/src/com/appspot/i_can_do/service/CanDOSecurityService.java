package com.appspot.i_can_do.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.appspot.i_can_do.master.security.User;

public class CanDOSecurityService implements ICanDOSecurityService {
	private static final EntityManagerFactory fcr = Persistence
			.createEntityManagerFactory("CanDO");;
	private static final EntityManager em = fcr.createEntityManager();
	private static final CanDOSecurityService security = new CanDOSecurityService();

	public CanDOSecurityService instance() {
		return security;
	}

	@Override
	public void addUser(User user) {
		if (!user.equals(User.NULL_USER)) {
			if (!"".equals(user.getEmail())) {
				if (!"".equals(user.getPassword())) {
					if (!getUser(user.getEmail(), user.getPassword()).equals(
							User.NULL_USER)) {
						em.persist(user);
					}
				} else {

				}
			} else {

			}
		} else {

		}

	}

	@Override
	public User getUser(String email, String password) {
		Query query = em.createNamedQuery("User.getUser");
		query.setParameter("email", email);
		query.setParameter("password", password);
		User u = (User) query.getSingleResult();
		return u != null ? u : User.NULL_USER;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers() {
		return (List<User>) em.createNamedQuery("User.getUsers")
				.getResultList();
	}

}
