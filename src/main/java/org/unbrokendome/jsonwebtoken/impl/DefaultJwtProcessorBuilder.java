package org.unbrokendome.jsonwebtoken.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;
import org.unbrokendome.jsonwebtoken.JwtProcessor;
import org.unbrokendome.jsonwebtoken.JwtProcessorBuilder;
import org.unbrokendome.jsonwebtoken.encoding.JwsCompactEncoding;
import org.unbrokendome.jsonwebtoken.encoding.payload.ByteBufferPayloadSerializer;
import org.unbrokendome.jsonwebtoken.encoding.payload.DefaultPayloadSerializer;
import org.unbrokendome.jsonwebtoken.encoding.payload.PayloadSerializer;
import org.unbrokendome.jsonwebtoken.encoding.payload.StringPayloadSerializer;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.SigningKeyResolver;
import org.unbrokendome.jsonwebtoken.signature.VerificationKeyResolver;
import org.unbrokendome.jsonwebtoken.signature.Verifier;
import org.unbrokendome.jsonwebtoken.signature.provider.PoolConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

public class DefaultJwtProcessorBuilder implements JwtProcessorBuilder {

	private Supplier<ObjectMapper> objectMapperSupplier = ObjectMapper::new;
	private ImmutableList.Builder<PayloadSerializer<?>> payloadSerializers = ImmutableList.builder();
	private SignatureAlgorithm signingAlgorithm = SignatureAlgorithms.NONE;
	private SigningKeyResolver signingKeyResolver;
	private VerificationKeyResolver verificationKeyResolver;

	private Optional<PoolConfigurer> poolConfigurer = Optional.empty();
	private Map<String, Function<Optional<PoolConfigurer>, VerifierWithKeyResolver>> verifierBuilders = new HashMap<>();


	@Override
	public JwtProcessorBuilder setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapperSupplier = () -> objectMapper;
		return this;
	}


	@Override
	public JwtProcessorBuilder configurePool(int minSize, int maxIdle) {
		this.poolConfigurer = Optional.of(settings -> {
			settings.min(minSize).maxIdle(maxIdle);
		});
		return this;
	}


	@Override
	public JwtProcessorBuilder serializePayloadWith(PayloadSerializer<?> payloadSerializer) {
		payloadSerializers.add(payloadSerializer);
		return this;
	}


	@Override
	public JwtProcessorBuilder signAndVerifyWith(SignatureAlgorithm algorithm, SigningKeyResolver signingKeyResolver,
			VerificationKeyResolver verificationKeyResolver) {
		this.signingAlgorithm = algorithm;
		this.signingKeyResolver = signingKeyResolver;
		this.verificationKeyResolver = verificationKeyResolver;
		return this;
	}


	@Override
	public JwtProcessorBuilder verifyWith(SignatureAlgorithm algorithm, VerificationKeyResolver keyResolver) {
		verifierBuilders.put(algorithm.getJwaName(),
				poolConfigurer -> new VerifierWithKeyResolver(algorithm.createVerifier(poolConfigurer), keyResolver));
		return this;
	}


	@Override
	public JwtProcessor build() {
		ObjectMapper objectMapper = objectMapperSupplier.get();

		addDefaultPayloadSerializers(objectMapper);

		Map<String, VerifierWithKeyResolver> verifiers = new HashMap<>();

		Pair<Signer, Verifier> signerAndVerifier = signingAlgorithm.createSignerAndVerifier(poolConfigurer);
		Signer signer = signerAndVerifier.getLeft();
		verifiers.put(signingAlgorithm.getJwaName(), new VerifierWithKeyResolver(signerAndVerifier.getRight(),
				verificationKeyResolver));

		if (verifierBuilders != null) {
			verifiers.putAll(Maps.transformValues(verifierBuilders, (f) -> f.apply(poolConfigurer)));
		}

		return new DefaultJwtProcessor(payloadSerializers.build(), signingAlgorithm, signer, signingKeyResolver,
				verifiers, new JwsCompactEncoding(objectMapper));
	}


	private void addDefaultPayloadSerializers(ObjectMapper objectMapper) {
		payloadSerializers.add(StringPayloadSerializer.getInstance());
		payloadSerializers.add(ByteBufferPayloadSerializer.getInstance());
		payloadSerializers.add(new DefaultPayloadSerializer(objectMapper));
	}
}
