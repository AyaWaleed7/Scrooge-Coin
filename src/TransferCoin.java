import java.util.ArrayList;

public class TransferCoin extends Transaction {
	public User Recipient;
	public User Sender;
	public ArrayList<Coin> coin;

	public TransferCoin(String hpprevtrans, int amountCoins, User recipient,
			User sender, ArrayList<Coin> coin) {
		super(hpprevtrans, amountCoins);
		Recipient = recipient;
		Sender = sender;
		this.coin = coin;
	}

	
	
	@Override
	public String toString() {
		return "TransferCoin [Recipient=" + Recipient + ", Sender=" + Sender + ", coin=" + coin + ", trans_id="
				+ trans_id + ", hpprevtrans=" + hpprevtrans + ", amountCoins=" + amountCoins + "]";
	}

	
	
	
}