package com.appspot.i_can_do.service.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class Crypto {
	private static final Logger log = Logger.getLogger(Crypto.class.getName());
	/**
	 * Uses the SHA-256 algorithm to calculate a secure password hash
	 *
	 * @param password
	 * @return
	 */
	public static byte[] hashPassword(String password) {

		try {
			MessageDigest md5 = MessageDigest.getInstance("SHA-256");
			md5.update(password.getBytes());
			return md5.digest();
		}
		catch (NoSuchAlgorithmException nsae) {
			log.severe(nsae.toString());
			throw new IllegalArgumentException(nsae);
		}
	}

}