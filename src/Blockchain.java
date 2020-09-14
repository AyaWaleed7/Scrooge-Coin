import java.util.ArrayList;

public class Blockchain {
	public static ArrayList<Block> blocks = new ArrayList<Block>();
	
	public static MTree mt = null;

	public Blockchain() {
		blocks = new ArrayList<Block>();
	}

	public static void addblock(Block block) {
		blocks.add(block);
		mt  = new MTree(array_Transactions_String());
	}

	public static boolean check_coin(Coin coin) {
		boolean coincreated = false;
		boolean coinconsumed = false;
		for (Block block : blocks) {
			for (Transaction t : block.T) {
				if (t instanceof CreateCoin) {
					CreateCoin cc = (CreateCoin) t;
					if (cc.coin.contains(coin)) {
						coincreated = true;
					}
				} else if (t instanceof TransferCoin) {
					TransferCoin tc = (TransferCoin) t;
					if (tc.coin.contains(coin)) {
						coinconsumed = true;
					}
				}
			}
		}

		return coincreated && !coinconsumed;
	}

	public static boolean check_coins(ArrayList<Coin> coins) {
		for (Coin coin : coins) {
			if (!check_coin(coin)) {
				return false;
			}
		}
		return true;
	}
	
	public static ArrayList<String> array_Transactions_String(){
		ArrayList<String> ts = new ArrayList<String>();
		for (Block block : blocks) {
			for (Transaction t : block.T) {
				ts.add(t.toString());
			}
		}
		return ts;
	}
	
	public static MNode Merkle_Root_Node() {
		return mt.Mroot;
	}
	
	public static ArrayList<Audit_Trail> return_trail(String hash){
		return mt.get_Trail(hash);
	}
	
	public static String Display_BC() {
		String out ="";
		for (Block block : blocks) {
			out+="\n"+"######################################"+"\n";
			out+="Block ID "+ block.block_id+"\n";
			for (Transaction t : block.T) {
				out+="----------------------------------------"+"\n";
				if(t instanceof CreateCoin) {
					CreateCoin cc = (CreateCoin)t;
					out+="Type: CC " + "T_ID "+t.trans_id + " amt_coins "+t.amountCoins + " Owner "+ cc.coin.get(0).getUser().user_name+"\n";
//					System.out.println("Type: CC " + "T_ID "+t.trans_id + " amt_coins "+t.amountCoins + " Owner "+ cc.coin.get(0).getUser().user_name);
				}else if(t instanceof TransferCoin) {
					TransferCoin tc = (TransferCoin)t;
					out+="Type: TC " + "T_ID "+t.trans_id + " amt_coins "+t.amountCoins + " Sender "+ tc.Sender.user_name+" Rec "+ tc.Recipient.user_name+"\n";
					//System.out.println("Type: TC " + "T_ID "+t.trans_id + " amt_coins "+t.amountCoins + " Sender "+ tc.Sender.user_name+" Rec "+ tc.Recipient.user_name);
				}
				
			}
		}
		return out;
	}
}
