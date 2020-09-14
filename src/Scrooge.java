import java.security.PublicKey;
import java.util.ArrayList;

public class Scrooge extends User {
	String HashpointerLast;
	private String lastTransactionhash = null;
	private String lastBlockhash = null;
	private byte[] lastBlockhashSignature = null;
	public static ArrayList<Transaction> ten_transactions = new ArrayList<Transaction>();
	public static boolean working = false;
	public Scrooge(String user_name) {
		super(user_name);
	}

	// Create Coin for Scrooge
	public void CreateCoin(int amt) {
		ArrayList<Coin> coins = new ArrayList<Coin>();
		for (int i = 0; i < amt; i++) {
			Coin c = new Coin(this);
			coins.add(c);
		}

		Transaction t = new CreateCoin(lastTransactionhash, amt, coins);
		for (Coin coin : coins) {
			coin.setTransaction_ID(t.trans_id);
		}
		t.setSignature(this.sign(t.toString().getBytes()));
		lastTransactionhash = hashLib.toHexString(hashLib.getSHA(t.toString()));
		ten_transactions.add(t);
		add_ten_transaction_to_bc();
		
//		System.out.println("Current Transaction:       ");
//		CreateCoin cc = (CreateCoin) t;
//		System.out.println("Type: CC " + "T_ID "+t.trans_id + " amt_coins "+t.amountCoins + " Owner "+ cc.coin.get(0).getUser().user_name);
	}

	public void CreateCoin_for_Another(int amt, User user) {
		ArrayList<Coin> coins = new ArrayList<Coin>();
		for (int i = 0; i < amt; i++) {
			Coin c = new Coin(user);
			coins.add(c);
		}

		Transaction t = new CreateCoin(lastTransactionhash, amt, coins);
		for (Coin coin : coins) {
			coin.setTransaction_ID(t.trans_id);
		}
		t.setSignature(this.sign(t.toString().getBytes()));
		lastTransactionhash = hashLib.toHexString(hashLib.getSHA(t.toString()));
		ten_transactions.add(t);
		add_ten_transaction_to_bc();
//		System.out.println("Current Transaction:       ");
//		CreateCoin cc = (CreateCoin) t;
//		System.out.println("Type: CC " + "T_ID "+t.trans_id + " amt_coins "+t.amountCoins + " Owner "+ cc.coin.get(0).getUser().user_name);
	}

	public byte[] getlastblockhashsignature() {
		return lastBlockhashSignature;
	}
	public void add_ten_transaction_to_bc() {
		working = true;
		if (ten_transactions.size() == 10) {
			Block block = new Block(ten_transactions, lastBlockhash);
			block.setHashpointer_block(hashLib.toHexString(hashLib.getSHA(block.toString())));
			lastBlockhash = block.getHashpointer_block();
			lastBlockhashSignature = sign(lastBlockhash.getBytes());
			Blockchain.addblock(block);
			working = false;
			ten_transactions = new ArrayList<Transaction>();
		}
	}

	// Process Payment
	public void ProcessTransaction(TransferCoin t) {
		byte[] Signature = t.getSignature();
		PublicKey sender_pk = t.Sender.getPublicKey();

		if (this.verify(t.toString().getBytes(), Signature, sender_pk)) {
			ArrayList<Coin> coins = t.coin;
			if (Blockchain.check_coins(coins)||check_coins_in_ten_transactions(coins)) {
				t.setHpprevtrans(lastTransactionhash);
				lastTransactionhash = hashLib.toHexString(hashLib.getSHA(t.toString()));
				ten_transactions.add(t);
				add_ten_transaction_to_bc();
				CreateCoin_for_Another(coins.size(), t.Recipient);
			}
		}
//		System.out.println("Current Transaction:      ");
//		TransferCoin tc = (TransferCoin)t;
//		System.out.println("Type: TC " + "T_ID "+t.trans_id + " amt_coins "+t.amountCoins + " Sender "+ tc.Sender.user_name+" Rec "+ tc.Recipient.user_name);;
	}

	public boolean check_coins_in_ten_transactions(ArrayList<Coin> coin) {
		int coinsfound = 0;
		for (Coin coin2 : coin) {
			for (Transaction transaction : ten_transactions) {
				if (transaction instanceof CreateCoin) {
					CreateCoin cc = (CreateCoin) transaction;
					if(cc.coin.contains(coin2)) {
						coinsfound++;
						break;
					}
				}
			}
		}
		return coin.size() == coinsfound;
	}

}
