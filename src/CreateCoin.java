import java.util.ArrayList;

public class CreateCoin extends Transaction {
	public ArrayList<Coin> coin;

	public CreateCoin(String hpprevtrans, int amountCoins, ArrayList<Coin> coin) {
		super(hpprevtrans, amountCoins);
		this.coin = coin;
	}

	@Override
	public String toString() {
		return "CreateCoin [coin=" + coin + ", trans_id=" + trans_id + ", hpprevtrans=" + hpprevtrans + ", amountCoins="
				+ amountCoins + "]";
	}

	

	
	
		
	
	
	
	
}
