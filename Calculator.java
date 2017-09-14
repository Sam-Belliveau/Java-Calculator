import java.util.ArrayList;

public class Calculator {
	
	public boolean error = false;
	
	public boolean isDeg = false;
	
	private double answer;
	
	public String[] operations = {"(",")","+","-","*","/","^","%","!","sqrt","root","sin","cos","tan","abs","log","ln","pi","e","ans"};
	
	public Calculator(){
		
	}
	
	public double calculateString(String input){
		this.error = false;
		double out = calculateStringPrivate(input);
		this.answer = out;
		if(this.error) { System.out.print("(Invalid Calculation)"); }
		return out;
	}
	
	private double calculateStringPrivate(String input){
		try {
			ArrayList<String> equation = createList(input);
			
			/*** PARENTHESES ***/
			int pCounter = 0;
			boolean useIndex = false;
			int indexStart = 0;
			int indexEnd = 0;
			String section = "";
			
			boolean done = false;
			while(!done){
				done = true;
				
				/*** SINGLE OPERATOR ***/
				for(int i = 0; i < equation.size(); i++){
					switch(equation.get(i)){
					
					case "e":
					    equation.set(i, "2.718281828459045");
						done = false;
						break;
					
					case "pi":
					    equation.set(i, "3.141592653589793");
						done = false;
						break;
					
					case "ans":
					    equation.set(i, String.valueOf(this.answer));
						done = false;
						break;
						
					default :
						break;
					}
					if(indexStart < equation.size()-1){
						if(!(isOperator(equation.get(indexStart+1)) && equation.get(indexStart+1) != "pi" && equation.get(indexStart+1) != "e")){
						    equation.add(indexStart+1, "*");
						}
					}
					if (indexStart != 0){
						if (!(isOperator(equation.get(indexStart-1)) && equation.get(indexStart-1) != "pi" && equation.get(indexStart-1) != "e")){
							equation.add(indexStart, "*");
						}
					}
				}
			}
			done = false;
			while(!done) {
				done = false;
				pCounter = 0;
				useIndex = false;
				indexStart = 0;
				indexEnd = 0;
				section = "";
				
				for(int i = 0; i < equation.size(); i++){
					if(equation.get(i).equals("(")){
						if(pCounter == 0) { indexStart = i; }
						pCounter++;
					}
					if(equation.get(i).equals(")")){
						pCounter--;
						if(pCounter == 0){ indexEnd = i; useIndex = true; break; }
					}
				}
				if (useIndex){
					for(int i = indexStart+1; i < indexEnd; i++){
						section += equation.get(i);
					}
					equation.set(indexStart, Double.toString(calculateStringPrivate(section)));
					for(int i = 0; i < (indexEnd-indexStart); i++){
						equation.remove(indexStart+1);
					}
					if(indexStart < equation.size()-1){
						if(!(isOperator(equation.get(indexStart+1)) && equation.get(indexStart+1) != "pi" && equation.get(indexStart+1) != "e")){
						    equation.add(indexStart+1, "*");
						}
					}
					if (indexStart != 0){
						if (!(isOperator(equation.get(indexStart-1)) && equation.get(indexStart-1) != "pi" && equation.get(indexStart-1) != "e")){
							equation.add(indexStart, "*");
						}
					}
				} else { done = true; }
			}
			/*** OTHER STUFF ***/
			done = false;
			while(!done){
				done = true;
				
				/*** SINGLE OPERATOR ***/
				for(int i = 0; i < equation.size(); i++){
					switch(equation.get(i)){
					
					case "sqrt":
					    equation.set(i, Double.toString(Math.pow(Double.parseDouble(equation.get(i+1)), 0.5)));
						done = false;
						equation.remove(i+1);
						break;
					
					case "sin":
						if (this.isDeg){
							equation.set(i, Double.toString(Math.sin(Math.toRadians(Double.parseDouble(equation.get(i+1))))));
						} else {
							equation.set(i, Double.toString(Math.sin(Double.parseDouble(equation.get(i+1)))));
						}
						done = false;
						equation.remove(i+1);
						break;
					
					case "cos":
						if (this.isDeg){
							equation.set(i, Double.toString(Math.cos(Math.toRadians(Double.parseDouble(equation.get(i+1))))));
						} else {
							equation.set(i, Double.toString(Math.cos(Double.parseDouble(equation.get(i+1)))));
						}
						done = false;
						equation.remove(i+1);
						break;
					
					case "tan":
						if (this.isDeg){
							equation.set(i, Double.toString(Math.tan(Math.toRadians(Double.parseDouble(equation.get(i+1))))));
						} else {
							equation.set(i, Double.toString(Math.tan(Double.parseDouble(equation.get(i+1)))));
						}
						done = false;
						equation.remove(i+1);
						break;
					
					case "ln":
					    equation.set(i, Double.toString(Math.log(Double.parseDouble(equation.get(i+1)))));
						done = false;
						equation.remove(i+1);
						break;
					
					case "log":
					    equation.set(i, Double.toString(Math.log10(Double.parseDouble(equation.get(i+1)))));
						done = false;
						equation.remove(i+1);
						break;
						
					case "abs":
					    equation.set(i, Double.toString(Math.abs(Double.parseDouble(equation.get(i+1)))));
						done = false;
						equation.remove(i+1);
						break;
					
					case "!":
						equation.set(i, Double.toString(fac(Double.parseDouble(equation.get(i-1)))));
						done = false;
						equation.remove(i-1);
						i--;
						break;
					
					default :
						break;
					}
				}
			}
			done = false;
			while(!done){
				done = true;
				
				/*** MULTI OPERATOR ***/
				for(int i = 0; i < equation.size(); i++){
					switch(equation.get(i)){
					
					case "root":
					    equation.set(i, Double.toString(Math.pow(Double.parseDouble(equation.get(i+1)), (1/Double.parseDouble(equation.get(i-1))))));
						done = false;
						equation.remove(i-1);
						equation.remove(i);
						i -= 2;
						break;
					
					case "^":
					    equation.set(i, Double.toString(Math.pow(Double.parseDouble(equation.get(i-1)), Double.parseDouble(equation.get(i+1)))));
						done = false;
						equation.remove(i-1);
						equation.remove(i);
						i -= 2;
						break;
						
					case "!":
						equation.set(i, Double.toString(fac(Double.parseDouble(equation.get(i-1)))));
						done = false;
						equation.remove(i-1);
						i--;
						break;
					
					default :
						break;
					}
				}
			}
			done = false;
			while(!done){	
				done = true;
				/*** MULTIPLY/DIVIDE ***/
				for(int i = 0; i < equation.size(); i++){
					switch(equation.get(i)){
					
					case "*":
						equation.set(i, Double.toString(Double.parseDouble(equation.get(i-1))*Double.parseDouble(equation.get(i+1))));
						done = false;
						equation.remove(i-1);
						equation.remove(i);
						i -= 2;
						break;
					
					case "/":
						equation.set(i, Double.toString(Double.parseDouble(equation.get(i-1))/Double.parseDouble(equation.get(i+1))));
						done = false;
						equation.remove(i-1);
						equation.remove(i);
						i -= 2;
						break;
						
					case "%":
						equation.set(i, Double.toString(Double.parseDouble(equation.get(i-1))%Double.parseDouble(equation.get(i+1))));
						done = false;
						equation.remove(i-1);
						equation.remove(i);
						i -= 2;
						break;
					
					default :
						break;
					}
				}
			}
			done = false;
			while(!done){
				done = true;
				/*** ADD/SUBTRACT ***/
				for(int i = 1; i < equation.size(); i++){
					switch(equation.get(i)){
					
					case "+":
						equation.set(i, Double.toString(Double.parseDouble(equation.get(i-1))+Double.parseDouble(equation.get(i+1))));
						done = false;
						equation.remove(i-1);
						equation.remove(i);
						i -= 2;
						break;
					
					case "-":
						equation.set(i, Double.toString(Double.parseDouble(equation.get(i-1))-Double.parseDouble(equation.get(i+1))));
						done = false;
						equation.remove(i-1);
						equation.remove(i); // after removing a num, no need to add 1
						i -= 2;
						break;
						
					default :
						break;
					}
				}
			}
			if (equation.size() > 1) { 
		        this.error = true;
			}
			return Double.valueOf(equation.get(0));
		} catch(Exception e) { 
			this.error = true;
			return 0.0;
		} 
	}

	
	private boolean isOperator(String input){
		for(int o = 0; o < operations.length; o++){
			if (input.equals(operations[o])){
				return true;
			}
		}
		return false;
	}
	
	private double fac(double a){
		a = Math.round(a);
		if (a > 1){
			return a*fac(a-1);
		} else{ return 1; }
	}
	
	private ArrayList<String> createList(String input){
		ArrayList<String> output = new ArrayList<String>();
		String temp = null;
		
		input += " ";
		
		for (int i = 0; i < input.length(); i++){
			temp = "";
			
			boolean numFound = false;
			while(i < input.length()){
				if (isNum(input.charAt(i)) || input.charAt(i) == '.' || input.charAt(i) == '-'){
					temp += String.valueOf(input.charAt(i));
					i++;
					numFound = true;
				} else if (numFound) { i--; output.add(temp); break; }
				else { break; }
			}
			
			for(int o = 0; o < operations.length; o++){
			    for(int j = 0; j < operations[o].length(); j++){
    				if (input.charAt(i) == operations[o].charAt(j)){
    					temp += String.valueOf(operations[o].charAt(j));
    					if(!(j < operations[o].length()-1)) { o = operations.length; output.add(temp); break; }
    					i++;
    				} else { temp = ""; i -= j; break; }
			    }
			}
		}
		
		return output;
	}
	
	private boolean isNum(char num){
		for(int i = 0; i < 10; i++){
			if (num == Character.forDigit(i, 10)){
				return true;
			}
		}
		return false;
	}
}
