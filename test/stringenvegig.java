
public class stringenvegig{
		
	public static void main(String[] args){
		for(String s : args){
			/*for(char c : s.toCharArray()){
				System.out.println(c);
			}*/
			//boolean hasDigit = s.matches(".*\\d+.*");
			boolean hasDigit = s.matches("[a-zA-Z]+");
			if(hasDigit){
				System.out.println("nincs szam");
			}else{
				System.out.println("van szam");
			}
		}
	}
}