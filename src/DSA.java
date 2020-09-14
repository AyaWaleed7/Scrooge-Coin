import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class DSA {
	private KeyPair pair;
	
	
	
	public DSA() {
		this.pair = kpgen();
	}

	private KeyPair kpgen() {
		KeyPairGenerator keyGen = null;
		try {
			keyGen = KeyPairGenerator.getInstance("DSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		KeyPair pair = keyGen.generateKeyPair();
		
		return pair;
	}
	
	public PrivateKey getPrivateKey() {
		return this.pair.getPrivate();
	}
	
	public PublicKey getPublicKey() {
		return this.pair.getPublic();
	}
}
