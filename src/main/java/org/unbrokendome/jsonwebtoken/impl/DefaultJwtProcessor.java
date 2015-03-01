package org.unbrokendome.jsonwebtoken.impl;

import java.security.Key;
import java.util.List;
import java.util.Map;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.JoseHeader;
import org.unbrokendome.jsonwebtoken.JoseHeaderBuilder;
import org.unbrokendome.jsonwebtoken.Jws;
import org.unbrokendome.jsonwebtoken.JwtProcessor;
import org.unbrokendome.jsonwebtoken.encoding.JwsEncoding;
import org.unbrokendome.jsonwebtoken.encoding.JwtMalformedTokenException;
import org.unbrokendome.jsonwebtoken.encoding.payload.PayloadSerializer;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureException;
import org.unbrokendome.jsonwebtoken.signature.JwsUnsupportedAlgorithmException;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.SigningKeyResolver;
import org.unbrokendome.jsonwebtoken.signature.VerificationKeyResolver;
import org.unbrokendome.jsonwebtoken.signature.Verifier;

public class DefaultJwtProcessor implements JwtProcessor {

	private final List<PayloadSerializer<?>> payloadSerializers;
	private final SignatureAlgorithm signingAlgorithm;
	private final Signer signer;
	private final SigningKeyResolver signingKeyResolver;
	private final Map<String, VerifierWithKeyResolver> verifiers;
	private final JwsEncoding jwsEncoding;


	public DefaultJwtProcessor(List<PayloadSerializer<?>> payloadSerializers, SignatureAlgorithm signingAlgorithm,
			Signer signer, SigningKeyResolver signingKeyResolver, Map<String, VerifierWithKeyResolver> verifiers,
			JwsEncoding jwsEncoding) {
		this.payloadSerializers = payloadSerializers;
		this.signingAlgorithm = signingAlgorithm;
		this.signer = signer;
		this.signingKeyResolver = signingKeyResolver;
		this.verifiers = verifiers;
		this.jwsEncoding = jwsEncoding;
	}


	@Override
	public String encode(Object payload) throws JwsSignatureException {
		JoseHeaderBuilder header = new DefaultJoseHeaderBuilder();
		signingAlgorithm.createHeader(header);

		Key signingKey = signingKeyResolver.getSigningKey(header, payload);

		BinaryData headerBytes = jwsEncoding.serializeHeader(header);
		BinaryData payloadBytes = serializePayload(payload);
		BinaryData signature = signer.sign(headerBytes, payloadBytes, signingKey);

		return jwsEncoding.encode(headerBytes, payloadBytes, signature);
	}


	private BinaryData serializePayload(Object payload) {
		PayloadSerializer<?> serializer = findPayloadSerializerForType(payload.getClass());
		return serializer.serialize(payload);
	}


	@SuppressWarnings("unchecked")
	private <T> PayloadSerializer<T> findPayloadSerializerForType(Class<T> payloadType) {
		PayloadSerializer<?> serializer = payloadSerializers
				.stream()
				.filter(ps -> ps.supports(payloadType))
				.findFirst()
				.orElseThrow(
						() -> new IllegalStateException("No payload serializer could handle the payload of type: "
								+ payloadType));
		return (PayloadSerializer<T>) serializer;
	}


	@Override
	public <T> T decode(String encodedToken, Class<T> payloadType) throws JwtMalformedTokenException,
			JwsUnsupportedAlgorithmException, JwsSignatureException {
		Jws jws = jwsEncoding.decode(encodedToken);

		JoseHeader header = jwsEncoding.deserializeHeader(jws.getHeader());
		T payload = deserializePayload(jws.getPayload(), payloadType);

		verifySignature(jws, header, payload);

		return payload;
	}


	private <T> T deserializePayload(BinaryData payloadBytes, Class<T> payloadType) {
		PayloadSerializer<T> payloadSerializer = findPayloadSerializerForType(payloadType);
		return payloadSerializer.deserialize(payloadBytes, payloadType);
	}


	private void verifySignature(Jws jws, JoseHeader header, Object payload) throws JwsUnsupportedAlgorithmException,
			JwsSignatureException {
		VerifierWithKeyResolver verifierWithKeyResolver = getVerifierAndKeyResolver(header);

		VerificationKeyResolver verificationKeyResolver = verifierWithKeyResolver.getVerificationKeyResolver();
		Verifier verifier = verifierWithKeyResolver.getVerifier();
		Key verificationKey = verificationKeyResolver.getVerificationKey(header, payload);

		verifier.verify(jws.getHeader(), jws.getPayload(), jws.getSignature(), verificationKey);
	}


	private VerifierWithKeyResolver getVerifierAndKeyResolver(JoseHeader header)
			throws JwsUnsupportedAlgorithmException {
		String algorithmName = header.getAlgorithm();
		VerifierWithKeyResolver verifierWithKeyResolver = verifiers.get(algorithmName);
		if (verifierWithKeyResolver == null) {
			throw new JwsUnsupportedAlgorithmException(algorithmName);
		}
		return verifierWithKeyResolver;
	}
}