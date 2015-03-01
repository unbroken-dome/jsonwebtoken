package org.unbrokendome.jsonwebtoken;

import static org.hamcrest.MatcherAssert.assertThat;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.Signature;
import java.util.Base64;
import java.util.Random;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class KeysTest {

	Random random;
	private byte[] keyBytes = new byte[32];
	private byte[] messageBytes = new byte[1024];


	@Before
	public void generateTestData() {
		random = new Random();
		random.nextBytes(keyBytes);
		random.nextBytes(messageBytes);
	}


	@Test
	@Ignore
	public void macTest() throws Exception {
		SecretKey key = new SecretKeySpec(keyBytes, "");

		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(key);
		mac.doFinal(messageBytes);
	}


	@Test
	@Ignore
	public void rsaTest() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		Signature sig = Signature.getInstance("SHA384withRSA");
		sig.initSign(keyPair.getPrivate());
		// sig.update(messageBytes);
		byte[] sigBytes = sig.sign();

		System.out.println(Base64.getUrlEncoder().encodeToString(sigBytes));

		sig.initVerify(keyPair.getPublic());
		// sig.update(messageBytes);
		assertThat("Signature is valid", sig.verify(new byte[0]));
	}


	@Test
	@Ignore
	public void keyStoreTest() throws Exception {
		KeyStore keyStore = KeyStore.getInstance("jceks");
		keyStore.load(null, "password1".toCharArray());

		KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
		SecretKey secretKey = keyGen.generateKey();

		keyStore.setEntry("alias", new KeyStore.SecretKeyEntry(secretKey),
				new KeyStore.PasswordProtection("password2".toCharArray()));

		keyStore.getKey("alias", "password2".toCharArray());
	}

}
