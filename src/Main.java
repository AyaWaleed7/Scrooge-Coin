import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
	private static volatile boolean keepRunning = true;
	
	public static synchronized void stopRunning() {
		keepRunning = false;
	}
	
	public static void someProcess(ArrayList<User> users, Scrooge s) {
		new Thread() {
			public void run() {
				try (Scanner scanner = new Scanner(System.in)) {
					boolean keepWaiting = true;
					while (keepWaiting) {
						String userInput = scanner.next();
						if ("s".equals(userInput)) {
							stopRunning();
							keepWaiting = false;
						}
					}
				}
			}
		}.start();

		while (keepRunning) {
			int userA = ThreadLocalRandom.current().nextInt(0, users.size());
			int userB = 0;
			while (true) {
				userB = ThreadLocalRandom.current().nextInt(0, users.size());
				if (userB != userA) {
					break;
				}
			}
			int useracoins = users.get(userA).get_coins_size();
			int sumtransfer = 0;
			if (useracoins == 1) {
				sumtransfer = 1;
			} else if (useracoins == 0) {
				sumtransfer = 0;
			} else {
				sumtransfer = ThreadLocalRandom.current().nextInt(1, users.get(userA).get_coins_size() + 1);
			}

			if (sumtransfer != 0) {
				users.get(userA).TransferCoins(sumtransfer, users.get(userB), s);
			}
		}
	}

	
	public static void main(String[] args) {
		Scrooge s = new Scrooge("Scrooge");
		ArrayList<User> users = new ArrayList<User>();
		for (int i = 0; i < 100; i++) {
			users.add(new User("User " + i));
		}

		s.CreateCoin(users.size() * 10);

		for (User user : users) {
			s.TransferCoins(10, user, s);
		}
		
		String out="";
		for (User user : users) {
			out+= "\n"+ user.toString_s()+"\n";
			System.out.println(user.toString_s());
		}
		
		System.out.println();
		System.out.println("Press charachter 's' and then Enter to terminate");
		System.out.println();
		
		someProcess(users, s);
		out+="\n"+Blockchain.Display_BC()+"\n";
		System.out.println(Blockchain.Display_BC());
		
		PrintWriter filewriter;
		try {
			filewriter = new PrintWriter("Output.txt");
			filewriter.println(out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
