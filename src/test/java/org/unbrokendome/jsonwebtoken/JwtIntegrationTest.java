package org.unbrokendome.jsonwebtoken;

import java.time.Instant;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.junit.Before;
import org.junit.Test;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms;

public class JwtIntegrationTest {

	JwtProcessor processor;


	@Before
	public void setup() throws Exception {
		SecretKey key = KeyGenerator.getInstance("HmacSHA256").generateKey();

		processor = Jwts.processor().signAndVerifyWith(SignatureAlgorithms.HS256, key)
		// .configurePool(3, 10)
				.build();
	}


	@Test
	public void test() throws Exception {
		Claims claims = Jwts.claims().setSubject("Till").setIssuedAt(Instant.now())
				.setExpiration(Instant.now().plusSeconds(300)).build();

		String jwt = processor.encode(claims);
		processor.decodeClaims(jwt);
	}
}
