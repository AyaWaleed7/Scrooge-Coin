import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;

public class User {
	private Signature signer;

	private PrivateKey privateKey;

	private PublicKey publicKey;

	public String user_name;
	
	public ArrayList<Transaction> transactions_pending = new ArrayList<Transaction>();

	public User(String user_name) {

		this.user_name = user_name;

		DSA dsa = new DSA();
		signer = null;
		try {
			signer = Signature.getInstance("SHA256withDSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		privateKey = dsa.getPrivateKey();
		publicKey = dsa.getPublicKey();
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public byte[] sign(byte[] message) {
		try {
			signer.initSign(privateKey);
			signer.update(message);
			return signer.sign();
		} catch (SignatureException | InvalidKeyException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	public boolean verify(byte[] message, byte[] signature,PublicKey publicKey) {
		try {
			signer.initVerify(publicKey);
			signer.update(message);
			return signer.verify(signature);
		} catch (SignatureException | InvalidKeyException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}

	// Transfer Coin Method
	public void TransferCoins(int amt_coins, User recipient,Scrooge s) {
		ArrayList<Coin> user_coins = getcoins();
		if (user_coins.size() >= amt_coins) {
			ArrayList<Coin> coins_to_send = new ArrayList<Coin>();
			for (int i = 0; i < amt_coins; i++) {
				coins_to_send.add(user_coins.get(i));
			}
			Transaction t = new TransferCoin(null, amt_coins, recipient, this, coins_to_send);
			
			t.setSignature(sign(t.toString().getBytes()));
			s.ProcessTransaction((TransferCoin) t);
			recipient.verify_transaction(t);
		}
	}
	
	public boolean verify_transaction(Transaction t) {
		boolean b = false;
		if(Scrooge.working) {
			transactions_pending.add(t);
		}else {
			for (Transaction transaction : transactions_pending) {
				String hash = hashLib.toHexString(hashLib.getSHA(transaction.toString()));
				b = helper_verify(hash, Blockchain.return_trail(hash));
				transactions_pending = new ArrayList<Transaction>();
			}
		}
		return b;
	}
	
	public boolean helper_verify(String transaction_hash, ArrayList<Audit_Trail> trail) {
		String proof_until_now = transaction_hash;
		
		for (int i = 0; i < trail.size()-1; i++) {
			Audit_Trail at = trail.get(i);
			
			String hash = at.n;
			String pos = at.pos;
			
			if(pos.equals("l")) {
				proof_until_now = hashLib.toHexString(hashLib.getSHA((hash + proof_until_now)));
			}else if(pos.equals("r")) {
				proof_until_now = hashLib.toHexString(hashLib.getSHA((proof_until_now+hash)));
			}
		}
		
		String HashRoot = Blockchain.Merkle_Root_Node().hash;
		return HashRoot.equals(proof_until_now);
		
	}
	
	// GetCoins Method from Blockchain
	private ArrayList<Coin> getcoins() {
		ArrayList<Coin> user_coins = new ArrayList<Coin>();
		for (Block block : Blockchain.blocks) {
			for (Transaction t : block.T) {
				if (t instanceof CreateCoin) {
					CreateCoin tc = (CreateCoin) t;
					for (Coin coin : tc.coin) {
						if (coin.getUser().equals(this)) {
							if (Blockchain.check_coin(coin)) {
								user_coins.add(coin);
							}
						}
					}
				}
			}
		}
		
		for (Transaction t : Scrooge.ten_transactions) {
			if (t instanceof CreateCoin) {
				CreateCoin tc = (CreateCoin) t;
				for (Coin coin : tc.coin) {
					if (coin.getUser().equals(this)) {
							user_coins.add(coin);
					}
				}
			}
		}
		return user_coins;
	}
	
	public int get_coins_size() {
		return getcoins().size();
	}

	public String toString_s() {
		return "User [publicKey=" + publicKey.getEncoded() + " amount_Coins= "+ get_coins_size()+"]";
	}
	
	
}

