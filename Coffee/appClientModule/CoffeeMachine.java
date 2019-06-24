
//package machine;
import java.util.*;

public class CoffeeMachine {
	int money = 550, water = 400, milk = 540, coffe = 120, cups = 9, coffeType = 0;
	String command = "";
	Scanner sc = new Scanner(System.in);
	MachStates currentState;
	public boolean isWorking;

	enum MachStates {
		MAIN_MENU, BUY_MENU, FILL_WATER, FILL_MILK, FILL_COFFEE, FILL_CUPS;
	}

	public CoffeeMachine() {
		super();
		this.isWorking = true;
		this.currentState = MachStates.MAIN_MENU;
		startMachine("");
	}

	public void shutDown() {
		this.isWorking = false;
	}
	
	public String startMachine(String input){                
    	switch (this.currentState) {
		case MAIN_MENU:
			if (input.equals("buy")) {
				this.currentState = MachStates.BUY_MENU;
				return "\n\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ";
			}else if (input.equals("fill")) {
				this.currentState = MachStates.FILL_WATER;
				return "\n\nWrite how many ml of water do you want to add: ";
			}else if (input.equals("take")) {
				int temp = this.money;
				this.money = 0;		
				this.currentState = MachStates.MAIN_MENU;
				return "\nI gave you $" + temp + "\n\n" + startMachine(""); 		        				
			}else if (input.equals("remaining")) {
				return ("" + showIdleState(money,  water,  milk,  coffe,  cups) + "\n" + startMachine(""));				
			}else if (input.equals("exit")) {
				shutDown();
				return "";
			}else {
				return "Write action (buy, fill, take, remaining, exit): "; 
			}
					
		case BUY_MENU:
			if (input.equals("back")) {
				this.currentState = MachStates.MAIN_MENU;
				return startMachine("");
			}else if (input.equals("1") || input.equals("2") || input.equals("3")) {
				this.currentState = MachStates.MAIN_MENU;
				return canBuy(water, milk, coffe, cups, input) + "\n\n" + startMachine("") ; 
			}
		case FILL_WATER:
			this.water += Integer.parseInt(input);
			this.currentState = MachStates.FILL_MILK;
			return "\nWrite how many ml of milk do you want to add: "; 
		case FILL_MILK:
			this.milk += Integer.parseInt(input);
			this.currentState = MachStates.FILL_COFFEE;
			return "\nWrite how many grams of coffee beans do you want to add: "; 
		case FILL_COFFEE:
			this.coffe += Integer.parseInt(input);
			this.currentState = MachStates.FILL_CUPS;
			return "\nWrite how many disposable cups of coffee do you want to add: ";
		case FILL_CUPS:
			this.cups += Integer.parseInt(input);
			this.currentState = MachStates.MAIN_MENU;
			return startMachine("");
			//return "Write how many disposable cups of coffee do you want to add: ";
		}     
    	return "Smth goes wrong";
    }

	private String canBuy(int water, int milk, int coffe, int cups, String cofeType) {

		String resourses = "";
		boolean hasMilk = true, hasWater = true, hasCoffe = true, hasCups = true;

		switch (cofeType) {
		case "1":
			// hasMilk = milk >= 250;
			hasWater = water >= 250;
			hasCoffe = coffe >= 16;
			hasCups = cups >= 1;
			break;
		case "2":
			hasMilk = milk >= 75;
			hasWater = water >= 350;
			hasCoffe = coffe >= 20;
			hasCups = cups >= 1;
			break;
		case "3":
			hasMilk = milk >= 250;
			hasWater = water >= 200;
			hasCoffe = coffe >= 16;
			hasCups = cups >= 1;
			break;
		}
		if (!hasWater) {
			resourses = "water";
		} else if (!hasMilk) {
			resourses = "milk";
		} else if (!hasCoffe) {
			resourses = "coffee beans";
		} else if (!hasCups) {
			resourses = "cups";
		}

		if (hasWater && hasMilk && hasCoffe && hasCups) {
			// System.out.println("I have enough resources, making you a coffee!");
			// System.out.println();
			switch (cofeType) {
			case "1":

				this.water -= 250;
				this.coffe -= 16;
				this.cups -= 1;
				this.money += 4;

				break;
			case "2":

				this.water -= 350;
				this.milk -= 75;
				this.coffe -= 20;
				this.money += 7;
				this.cups -= 1;

				break;
			case "3":

				this.water -= 200;
				this.milk -= 100;
				this.coffe -= 12;
				this.money += 6;
				this.cups -= 1;

				break;

			}
			return "\nI have enough resources, making you a coffee!";
		} else {
			// System.out.println("Sorry, not enoughs "+resourses+"!" );
			// System.out.println();
			return "\nSorry, not enough " + resourses + "!";
		}

	}

	public String switchOn() {
		this.isWorking = true;
		return startMachine("");
	}
	// $550, 1200 ml of water, 540 ml of milk, 120 g of coffee beans, and 9
	// disposable cups.
	private static String showIdleState(int money, int water, int milk, int coffe, int cups) {
		// System.out.println();
		return "\n\nThe coffee machine has:\n" + water + " of water\n" + milk + " of milk\n" + coffe + " of coffee beans\n"
				+ cups + " of disposable cups\n" + "$" + money + " of money\n";

	}

	public static void main(String[] args) {

		CoffeeMachine mach = new CoffeeMachine();
		Scanner sc = new Scanner(System.in);
		System.out.print(mach.switchOn());
		while (mach.isWorking) {
			System.out.print(mach.startMachine(sc.next())) ;
		}
		sc.close();
		/*
		 * System.out.print(mach.startMachine("remaining"));
		 * System.out.print(mach.startMachine("buy"));
		 * System.out.print(mach.startMachine("2"));
		 * System.out.print(mach.startMachine("remaining"));
		 * System.out.print(mach.startMachine("buy"));
		 * System.out.print(mach.startMachine("2"));
		 * System.out.print(mach.startMachine("fill"));
		 * System.out.print(mach.startMachine("1000"));
		 * System.out.print(mach.startMachine("0"));
		 * System.out.print(mach.startMachine("0"));
		 * System.out.print(mach.startMachine("0"));
		 * System.out.print(mach.startMachine("remaining"));
		 * System.out.print(mach.startMachine("2"));
		 * System.out.print(mach.startMachine("remaining"));
		 * System.out.print(mach.startMachine("take"));
		 * System.out.print(mach.startMachine("remaining"));
		 * System.out.print(mach.startMachine("exit"));
		 */
		
		
		

	}

}