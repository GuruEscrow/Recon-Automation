package simulator.resoucrse;

import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

import org.cryptacular.util.CertUtil;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class Encode_DecodeUsingBase64 {

	protected static String encodeLedger(String secretKey) {

		Algorithm algorithm = Algorithm.HMAC256("Guru");

		String token = JWT.create().withClaim("ledger", secretKey).sign(algorithm);
		return token;
	}

	public static String decodeLedger(String apiKey) {

		Algorithm algorithm = Algorithm.HMAC256("Guru");
		DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(apiKey);

		return decodedJWT.getClaims().get("ledger").asString();
	}

	public static Map verifySignature(String certificatePath, String signature, String jsonBody) {
		boolean isValid = false;
		X509Certificate certificate = null;
		Map certificateDetails=new LinkedHashMap();
		try {

			// Converting jsonBody which signed to bytes
			byte[] originalData = jsonBody.getBytes("UTF-8");

			// Load the public certificate
			FileInputStream fis = new FileInputStream(certificatePath);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			certificate = (X509Certificate) cf.generateCertificate(fis);
			fis.close();

			// Extract the public key from the certificate
			PublicKey publicKey = certificate.getPublicKey();

			// Decode the base64 encoded signature
			byte[] signatureBytes = Base64.getDecoder().decode(signature);

			// Initialize the Signature object for verification
			Signature verifySignature = Signature.getInstance("SHA256withRSA");
			verifySignature.initVerify(publicKey);
			verifySignature.update(originalData);

			// Verify the signature
			isValid = verifySignature.verify(signatureBytes);
			certificateDetails.put("name", CertUtil.subjectCN(certificate));
			certificateDetails.put("signatureValid", isValid);
			certificateDetails.put("prodPubCert", "-----BEGIN CERTIFICATE-----"+Base64.getEncoder().encodeToString(certificate.getEncoded())+"-----END CERTIFICATE-----");

		} catch (Exception e) {
			e.printStackTrace();
		}

		
			return certificateDetails;
	}

}
