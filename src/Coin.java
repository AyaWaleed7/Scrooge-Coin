
public class Coin {
	// Coin_ID
	//Static ID
	static int id_num = 0;
	private int coin_id;
	// OwnerShip_ID
	private User User;
	// Transaction_ID
	private int Transaction_ID;
	
	public Coin(User User) {
		this.coin_id = id_num;
		this.User = User;
		Transaction_ID = -1;
		id_num++;
	}

	public int getCoin_id() {
		return coin_id;
	}

	public void setCoin_id(int coin_id) {
		this.coin_id = coin_id;
	}


	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	public int getTransaction_ID() {
		return Transaction_ID;
	}

	public void setTransaction_ID(int transaction_ID) {
		Transaction_ID = transaction_ID;
	}

	@Override
	public String toString() {
		return "Coin [coin_id=" + coin_id + ", User_ID=" + User+ ", Transaction_ID=" + Transaction_ID + "]";
	}
	
	
}
