package jwt

import java.security.KeyPairGenerator
import java.security.interfaces.{RSAPrivateKey, RSAPublicKey}

import com.google.gson.{GsonBuilder, JsonElement, JsonParser}
import com.nimbusds.jose.Algorithm
import com.nimbusds.jose.jwk.{JWKSet, KeyUse, RSAKey}

object JWKGenerator {

  def make(keySize: Integer, keyUse: KeyUse, keyAlg: Algorithm, keyId: String) = {
    val generator = KeyPairGenerator.getInstance("RSA")
    generator.initialize(keySize)
    val kp = generator.generateKeyPair()
    val publicKey = kp.getPublic().asInstanceOf[RSAPublicKey]
    val privateKey = kp.getPrivate().asInstanceOf[RSAPrivateKey]
    new RSAKey.Builder(publicKey)
      .privateKey(privateKey)
      .keyUse(keyUse)
      .algorithm(keyAlg)
      .keyID(keyId)
      .build()
  }

  /**
    * Generate a JWK Key Pair from a RSA key
    * @param rsaKey
    * @return
    */
  def generateJWKKeypair(rsaKey: RSAKey): JsonElement = {
    val jwkSet = new JWKSet(rsaKey)
    new JsonParser().parse(jwkSet.toJSONObject(false).toJSONString)
  }

  /**
    * Generate a JWK json from a RSA Key
    *
    * @param rsaKey
    * @return
    */
  def generateJWKJson(rsaKey: RSAKey): String = {
    val jsonElement  = generateJWKKeypair(rsaKey)
    val gson = new GsonBuilder().setPrettyPrinting().create()
    gson.toJson(jsonElement)
  }


}
