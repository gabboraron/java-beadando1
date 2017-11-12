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
	public static Customer makeCustomer(String name, int birthYear, String bankName){ // ellenőrzi a paramétereket / létrehozza a megfelelő Customer objektumot és visszaadja
		boolean everyCriteriaIsOK = true;
		
		boolean hasntDigit = name.matches("[a-zA-Z]+");					//A név csak az angol abc betűiből és szóközből állhat
		if(hasntDigit){
			int nrOfSpaces = 0;											// pontosan egy szóköz választja el egymástól
			for (int i = 0; i < name.length(); i++) {
				if (name.charAt(i) == ' ') {
					++nrOfSpaces;
				}
			}
			
			if(nrOfSpaces<2){
				String[] parts = name.split(" ");
				if((parts.length>1) && (parts.length<5)){				//A név legalább 2, legfeljebb 4 részből áll
					boolean notEnoughChars = false;
					for(String part : parts){
						if((part.length() < 3) || (Character.isLowerCase(part.charAt(0)))){		//minden rész legalább három karakter hosszú, nagybetűvel kezdődik
							notEnoughChars = true;
						}
						if(!notEnoughChars){
							for (int i = 1; i < part.length(); i++) {
								if(Character.isUpperCase(part.charAt(i))){						//utána csak kisbetű áll
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
		
		if((birthYear<1918) || (1998<birthYear)){							//A születési évnek 1918 és 1998 közöttinek kell lennie, az 1918 és az 1998 értéket is felveheti
			everyCriteriaIsOK = false;
		}
		
		if(everyCriteriaIsOK) {
			try{
				return new Customer(name, birthYear, Bank.valueOf(bankName));
			} catch (IllegalArgumentException e) {
				System.err.println("Bad arguments.");
			}
		}
		return null;
	}

	//GETTEREK
	public String getName(){
		return name;
	}

	public Bank getBank(){
		return bank;
	}

	public int getAmount(){
		return amount;
	}

	public void decreaseAmount(){
		--amount;
	}

	public void increaseAmount(){
		++amount;
	}

	public String toString(){
		return (name + ": " + amount);
	}

	/*
	public static void main(String[] args){
		System.out.println("AVAVAVA");
	}
	*/
}