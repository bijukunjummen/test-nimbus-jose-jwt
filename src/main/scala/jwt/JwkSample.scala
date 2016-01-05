package jwt

import com.nimbusds.jose._
import com.nimbusds.jose.jwk.KeyUse

object JwkSample {
  def main(args: Array[String]): Unit = {
    val kp = JWKGenerator.make(1024, KeyUse.SIGNATURE, JWSAlgorithm.RS512, "sample")
    println(JWKGenerator.generateJWKJson(kp))
  }
}
