package financial.person;

import financial.bank.Bank;


public class Customer{
	
	//ADATTAGOK
	private String name;													  // a személy nevét tárolja
	private int birthYear;													  // a személy születési éve
	private Bank bank;														  // azt tárolja, hogy melyik banknál van a személy (egyetlen) folyószámlája
	private int amount;														  // a személy aktuális egyenlege a folyószámláján
	
	//KONSTRUKTOROK
	private Customer(String name, int birthYear, Bank bank){				  // ellenőrzés nélkül beállítja az adattagokat a megadott értékekre
		this.amount = 0;
		this.name = name;
		this.birthYear = birthYear;
		this.bank = bank;
	}
	
	//METÓDUSOK
	public Customer makeCustomer(String name, int birthYear, String bankName){// ellenőrzi a paramétereket / létrehozza a megfelelő Customer objektumot és visszaadja
		boolean everyCriteriaIsOK = true;
		
		boolean hasntDigit = name.matches("[a-zA-Z]+");
		if(hasntDigit){
			int nrOfSpaces = 0;
			for (int i = 0; i < name.length(); i++) {
				if (name.charAt(i) == ' ') {
					++nrOfSpaces;
				}
			}
			
			if(nrOfSpaces<2){
				String[] parts = name.split(" ");
				if((parts.length>1) && (parts.length<5)){
					boolean notEnoughChars = false;
					for(String part : parts){
						if((part.length() < 3) || (Character.isLowerCase(part.charAt(0)))){
							notEnoughChars = true;
						}
						if(!notEnoughChars){
							for (int i = 1; i < part.length(); i++) {
								if(Character.isUpperCase(part.charAt(i))){
									notEnoughChars = true;
								}
							}
						}
						if(notEnoughChars){
							everyCriteriaIsOK = false;
						}
					}
				} else {
					everyCriteriaIsOK = false;
				}					
			} else {
				everyCriteriaIsOK = false;
			}
			
		} else {
			everyCriteriaIsOK = false;
		}
		
		if((birthYear<1918) || (1998<birthYear)){
			everyCriteriaIsOK = false;
		}
		return new Customer(name, birthYear, Bank.valueOf(bankName));
	}
}