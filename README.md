# JSON Web Token

This library is a simple and lightweight implementation of JSON Web Token (JWT) in Java.

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

Use the `Jwts.claims()` to construct a `Claims` object using a fluent syntax:

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
