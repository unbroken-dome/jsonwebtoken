# JSON Web Token

This library is a simple and lightweight implementation of JSON Web Token (JWT) in Java.

## Using the library

The library is available in [JCenter](https://jcenter.bintray.com). In Gradle, add the following
to your build script:

```groovy
repositories { jcenter() }

dependencies {
    implementation 'org.unbroken-dome.jsonwebtoken:jwt:1.5.0'
}
```


## Usage

The central interface to the library is the `JwtProcessor`, which defines the workflow of your
JWT processing. You can easily configure an instance using the `Jwt` facade:

```java
SecretKey key = KeyGenerator.getInstance("HmacSHA256").generateKey();

JwtProcessor processor = Jwt.processor()
		.signAndVerifyWith(SignatureAlgorithms.HS256, signingKey)
		.build();
```

The `JwtProcessor` instance is stateless, and can safely be used by multiple threads.

## Encoding claims as JWT

Use the `Jwt.claims()` to construct a `Claims` object using a fluent syntax:

```java
Claims claims = Jwt.claims()
		.setSubject("Till")
		.setIssuedAt(Instant.now())
		.setExpiration(Instant.now().plusSeconds(300))
		.build();
```

Call the `encode` method on `JwtProcessor` to build a JSON Web Token. The header and
signature parts are automatically added by the processor.

```java
String token = processor.encode(claims);
```


## Decoding a JWT

Call the `decode` method on `JwtProcessor` to decode a string and extract the claims.
The processor will verify the signature and throw an exception if it does not match.

```java
Claims claims = processor.decodeClaims(jwt);
```


## Using multiple signing keys

For added security, it may be desirable to use multiple cryptographic keys for signing and
verification. JWT allows a key id to be stored in the token header, in the `kid` field. The
`signWith` and `verifyWith` overloads that take a `SigningKeyResolver` or a `VerificationKeyResolver`
may be used for this purpose.

The following example creates an array of 10 keys, then configures the JWT processor to select one
at random each time a token is signed, and looks up the correct key for verification:

```java
KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
SecretKey[] keys = new SecretKey[10];
for (int i = 0; i < 10; i++) {
    keys[i] = keyGenerator.generateKey();
}

Random random = new Random();

JwtProcessor jwtProcessor = Jwt.processor()
        .signAndVerifyWith(SignatureAlgorithms.HS256,
            // The SigningKeyResolver selects a random key and stores it in the header
            (header, payload) -> {
                int keyIndex = random.nextInt(10);
                header.setKeyId(String.valueOf(keyIndex));
                return keys[keyIndex];
            },
            // The VerificationKeyResolver looks up the correct key from the index
            (header, payload) -> {
                int keyIndex = Integer.parseInt(header.getKeyId());
                return keys[keyIndex];
            })
        .build();
```


## Spring Support

In addition to the core `jwt` library, there is another library called `jwt-spring` that includes some useful tools
for managing a JWT processor in a Spring context.

Include the library on the classpath:

```groovy
repositories { jcenter() }

dependencies {
    implementation 'org.unbroken-dome.jsonwebtoken:jwt-spring:1.5.0'
}
```


### Configuring a JWT Processor explicitly

Given that the `JwtProcessor` is immutable and all the configuration is done in the respective builder classes,
defining one as a Spring bean is straightforward:

```java
@Configuration
public class JwtProcessorConfig {

    @Bean
    public JwtProcessor jwtProcessor() {
        return Jwt.processor()
                .signAndVerifyWith(SignatureAlgorithms.HS256, signingKey)
                .build();
    }
}
```


### Configuring a JWT Processor using Annotations

By placing the `@EnableJwtProcessing` annotation on a Spring configuration class, you can have an instance of
`JwtProcessor` set up automatically. Without further configuration (like supported encryption algorithms) the
processor is not very useful, so you should also implement `JwtProcessorConfigurer` to perform further setup:

```java
@Configuration
@EnableJwtProcessing
public class JwtProcessorConfig implements JwtProcessorConfigurer<JwtProcessorBuilder> {

    @Override
    public void configure(JwtProcessorBuilder builder) {
        builder.signAndVerifyWith(SignatureAlgorithms.HS256, signingKey)
    }
}
```

Note that the `JwtProcessorConfigurer` implementation does not have to be the same class on which the `@EnableJwtProcessing`
annotation is placed. In fact, you can have multiple `JwtProcessorConfigurer` implementations in the application context, and
they will be all picked up, honoring the standard Spring ordering rules (`@Order` annotation or `Ordered` interface). This allows
for a more modular configuration of supported algorithms and keys if desired.

If you only need encoding or decoding, you can pass the `mode` parameter to the annotation. In this case you need to use a
different builder type as the generic type argument to `JwtProcessorConfigurer`:

```java
@Configuration
@EnableJwtProcessing(mode = JwtProcessorMode.ENCODE_ONLY)
public class JwtEncodeOnlyConfig implements JwtProcessorConfigurer<JwtEncodeOnlyProcessorBuilder> {

    @Override
    public void configure(JwtEncodeOnlyProcessorBuilder builder) {
        builder.signWith(SignatureAlgorithms.HS256, signingKey)
    }
}
```

And similarly, for a decode-only configuration:

```java
@Configuration
@EnableJwtProcessing(mode = JwtProcessorMode.DECODE_ONLY)
public class JwtDecodeOnlyConfig implements JwtProcessorConfigurer<JwtDecodeOnlyProcessorBuilder> {

    @Override
    public void configure(JwtDecodeOnlyProcessorBuilder builder) {
        builder.verifyWith(SignatureAlgorithms.HS256, verificationKey)
    }
}
```


### Using Spring Boot Auto-Configuration

In a Spring Boot application, you can take advantage of the Spring Boot auto-configuration that comes with the
`jwt-spring` library.

If you have `jwt-spring` on the classpath of a Spring Boot application, and you do not define a `JwtProcessor`
yourself (like described above), the auto-configuration will create one. You can fine-tune the processor using
Spring Boot configuration properties (e.g. in the application.yaml file):

```yaml
jwt:
  # FULL is the default, you can use ENCODE_ONLY or DECODE_ONLY just like with the annotation
  mode: FULL

  # Specify a signing key
  signing:
    algorithm: ES256
    key-resource: file:/app/signing-key.pem

  # Allow a FULL processor to use signing parameters also for verification. Set to false to disable this.
  verify-with-signing-algorithm: true

  # verification is a list, so multiple verification strategies can be configured
  verification:
    - algorithm: ES256
      key-resource: file:/app/verification-key.pem
```

Key material can be specified by the `key-resource` property, like in the example, where the value
is a Spring `Resource`. Usually the value will look like a URL starting with `file:` or `classpath:`.

Alternatively, you can use the `key` property to specify the key directly in the configuration property, as a
Base-64 encoded string.
