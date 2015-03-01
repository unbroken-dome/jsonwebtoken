package org.unbrokendome.jsonwebtoken.signature.impl;

import static org.mockito.Mockito.*;

import java.security.Key;

import javax.crypto.KeyGenerator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureMismatchException;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.Verifier;

public class SignerVerifierTest {

	private static final BinaryData SIGNATURE_1 = BinaryData.of(0xDEADBEEF);
	private static final BinaryData SIGNATURE_2 = BinaryData.of(0x0BADF00D);

	private static final BinaryData TEST_HEADER = BinaryData.of(0x039ABF3C23AF034AL);
	private static final BinaryData TEST_PAYLOAD = BinaryData.of(0x572B49AC03991E98L);
	private static Key TEST_KEY;

	private Signer signer = mock(Signer.class);
	private Verifier verifier = new SignerVerifier(signer);


	@BeforeClass
	public static void generateTestKey() throws Exception {
		TEST_KEY = KeyGenerator.getInstance("HmacSHA256").generateKey();
	}


	@Before
	public void setUp() {
		reset(signer);
	}


	@Test
	public void testSignMatch() throws Exception {
		when(signer.sign(TEST_HEADER, TEST_PAYLOAD, TEST_KEY)).thenReturn(SIGNATURE_1);

		verifier.verify(TEST_HEADER, TEST_PAYLOAD, SIGNATURE_1, TEST_KEY);
	}


	@Test(expected = JwsSignatureMismatchException.class)
	public void testSignMismatch() throws Exception {
		when(signer.sign(TEST_HEADER, TEST_PAYLOAD, TEST_KEY)).thenReturn(SIGNATURE_1);

		verifier.verify(TEST_HEADER, TEST_PAYLOAD, SIGNATURE_2, TEST_KEY);
	}
}
