import java.util.Scanner;

public class input {

	@SuppressWarnings("resource")
	public static void main(String args[]) {
		Calculator Calculator = new Calculator();
		Scanner input = new Scanner(System.in);
		String userInput = "";
		
		System.out.print("Supported Operations (" + Calculator.operations.length + "): ");
		for(int i = 0; i < Calculator.operations.length; i++) { 
			System.out.print(Calculator.operations[i]);
			if(i < Calculator.operations.length-1){
				System.out.print(", ");
			} else {
				System.out.print(". ");
			}
		}
		System.out.println("\n");
		
		System.out.println("Type \"deg\" To Set The Calculator To Degrees.");
		System.out.println("Type \"rad\" To Set The Calculator To Radians. (Default)\n");
		
		for(;;){
			System.out.print("Enter Equation: ");
			userInput = input.nextLine().toLowerCase();
			if(userInput.substring(0, Math.min(userInput.length(), 3)).equals("deg")){
				System.out.println("The Calculator Is Now In Degrees.\n");
				Calculator.isDeg = true;
			} else if (userInput.substring(0, Math.min(userInput.length(), 3)).equals("rad")) {
				System.out.println("The Calculator Is Now In Radians.\n");
				Calculator.isDeg = false;
			} else {
				System.out.println("				" + Calculator.calculateString(userInput) + "\n");
			}
		}
		
	}
	
}
