import java.util.ArrayList;

public class Block {
		static int id=0;
		int block_id;
		ArrayList<Transaction> T;
		String Hashpointer_block;
		String Hashpointer_prevblock;
		public Block(ArrayList<Transaction> t, String hashpointer_prevblock) {
			super();
			this.block_id = id;
			id++;
			T = t;
			Hashpointer_prevblock = hashpointer_prevblock;
		}
		
		@Override
		public String toString() {
			return "Block [block_id=" + block_id + ", T=" + T + ", Hashpointer_prevblock=" + Hashpointer_prevblock
					+ "]";
		}

		public String getHashpointer_block() {
			return Hashpointer_block;
		}

		public void setHashpointer_block(String hashpointer_block) {
			Hashpointer_block = hashpointer_block;
		}
		
		
		
		

		
		
		
}
