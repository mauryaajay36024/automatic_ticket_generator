package AutomaticTG.utility;
import java.util.Scanner;

public class Menu {
	public int mainMenu() throws Exception{
		Scanner sc =new Scanner(System.in);
		System.out.println("_________Menu_________");
		System.out.println("1. Generate Ticket");
		System.out.println("2. Deallocate Slot");
		System.out.println("3. Searching");
		System.out.println();
		System.out.print("Enter Your Choice :");
		System.out.println();
		return sc.nextInt();			
	}
	public int searchMenu() throws Exception{
		Scanner sc =new Scanner(System.in);
		System.out.println("---Search Menu---");
		System.out.println("1.Search by Colour");
		System.out.println("2.Search by Registration No");
		System.out.println();
		System.out.print("Enter Your choice:");
		int ch1=sc.nextInt();
		sc.nextLine();
		return ch1;
	}
	public int colourMenu()  throws Exception{
		Scanner sc =new Scanner(System.in);
		System.out.println();
		System.out.println("---Search By Colour---");
		System.out.println();
		System.out.println("1.Registration numbers");
		System.out.println("2.Slot numbers ");
		System.out.println();
		System.out.print("Enter Your choice:");
		int ch2=sc.nextInt();
		sc.nextLine();
		return ch2;
	}
}
