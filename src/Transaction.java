public class Transaction {
	static int id=0;
	int trans_id;
	String hpprevtrans;
	int amountCoins;
	byte[] signature;
	
	public Transaction(String hpprevtrans, int amountCoins) {
		super();
		this.trans_id = id;
		this.hpprevtrans = hpprevtrans;
		this.amountCoins = amountCoins;
		id++;
	}

	
	
	public String getHpprevtrans() {
		return hpprevtrans;
	}



	public void setHpprevtrans(String hpprevtrans) {
		this.hpprevtrans = hpprevtrans;
	}



	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}
	
	
	
	
	
}
