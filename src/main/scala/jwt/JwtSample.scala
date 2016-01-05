package jwt

import java.time.{LocalDateTime, ZoneOffset}
import java.util.Date

import com.nimbusds.jose._
import com.nimbusds.jose.crypto._
import com.nimbusds.jose.jwk.{JWKSet, RSAKey}
import com.nimbusds.jose.util.Base64
import com.nimbusds.jwt.JWTClaimsSet.Builder
import com.nimbusds.jwt._

object JwtSample {
  def main(args: Array[String]): Unit = {
    val jwkSet = JWKSet.load(JwtSample.getClass.getResource("/sample.json").toURI.toURL)
    val jwk = jwkSet.getKeyByKeyId("sample").asInstanceOf[RSAKey]

    val publicKey = jwk.toRSAPublicKey
    val privateKey = jwk.toRSAPrivateKey


    println("-----BEGIN RSA PRIVATE KEY-----")
    println(Base64.encode(privateKey.getEncoded))
    println("-----END RSA PRIVATE KEY-----")
    println
    println("-----BEGIN PUBLIC KEY-----")
    println(Base64.encode(publicKey.getEncoded))
    println("-----END PUBLIC KEY-----")


    println


    // Producer
    val claimsSetBuilder = new Builder()
      .subject("samplesubject")
      .claim("name", "John Doe")
      .claim("admin", true)
      .issuer("sampleissueer")
      .expirationTime(Date.from(LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.UTC)))

    val signer = new RSASSASigner(privateKey)


    val signedJWT: SignedJWT = new SignedJWT(
      new JWSHeader(JWSAlgorithm.RS512),
      claimsSetBuilder.build())

    signedJWT.sign(signer)

    val s = signedJWT.serialize()
    println(s)


    // Consumer
    val cSignedJWT = SignedJWT.parse(s)

    val verifier = new RSASSAVerifier(publicKey)

    println(cSignedJWT.verify(verifier))
    println(signedJWT.getJWTClaimsSet().getSubject())
  }
}
