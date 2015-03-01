package org.unbrokendome.jsonwebtoken.encoding.text;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyString;

import java.util.Base64;

import org.junit.Test;
import org.unbrokendome.jsonwebtoken.BinaryData;

public class Base64TextEncodingTest {

	private static final byte[] TEST_DATA_BYTES = new byte[] { 18, 119, 101, -110, 23, 1, 53, 15, -27, 54, 21, 31, -33,
			-28, 90, 24, 7, 56, -44, 120 };
	private static final BinaryData TEST_DATA = BinaryData.of(TEST_DATA_BYTES);

	private TextEncoding encoding = Base64TextEncoding.BASE64_URL;


	@Test
	public void testEncode() {
		String expected = Base64.getUrlEncoder().encodeToString(TEST_DATA_BYTES);

		String actual = encoding.getEncoder().apply(TEST_DATA);

		assertThat(actual, equalTo(expected));
	}


	@Test
	public void testEncodeEmptyBytes() {
		String actual = encoding.getEncoder().apply(BinaryData.EMPTY);
		assertThat(actual, isEmptyString());
	}


	@Test
	public void testDecode() {
		String encoded = Base64.getUrlEncoder().encodeToString(TEST_DATA_BYTES);

		BinaryData actual = encoding.getDecoder().apply(encoded);

		assertThat(actual, equalTo(TEST_DATA));
	}


	@Test
	public void testDecodeEmptyString() {
		BinaryData actual = encoding.getDecoder().apply("");

		assertThat(actual, equalTo(BinaryData.EMPTY));
	}
}
