# JSON Web Token

This library is a simple and lightweight implementation of JSON Web Token (JWT) in Java.

## Using the library

The library is available in [JCenter](https://jcenter.bintray.com). In Gradle, add the following
to your build script:

```groovy
repositories { jcenter() }

dependencies {
    compile 'org.unbroken-dome.jsonwebtoken:jsonwebtoken:1.2.0'
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
