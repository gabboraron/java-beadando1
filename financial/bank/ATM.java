package financial.bank;

import financial.bank.Bank;

public class ATM{

	private Bank bank; 			// melyik bankhoz tartozik az automata
	private int amount;			// aktuálisan mennyi pénz van az automatában

	
	//KONSTRUKTOROK
	private ATM(Bank bank, int amount){
		this.bank = bank;
		this.amount = amount;
	}

	//METODUS																		
	public static ATM makeATM(String bankName, int amount){								// ellenőrzi a paramétereket, és amennyiben azok megfelelőek, akkor létrehozza, ha nem akkor null			
		try{
		 	if(amount > -1) return (new ATM(Bank.valueOf(bankName), amount));		// bankName-nek a Bank típus egyik lehetséges értékével kell egyeznie, egyenlegnek pozitívnak kell lennie
		} catch(IllegalArgumentException e) {
			return null;
		}
		return null;
	}

	public int getAmount(){
		return this.amount;
	}

	public void decreaseAmount(){
		--this.amount;
	}

	public void increaseAmount(){
		++this.amount;
	}

	public int calculateFee(Bank bank, int value){
		if(bank == this	.bank){																//ha az automata a pénzfelvevő saját bankjának automatája,		//Bank.valueOf(bank) == Bank.valueOf(this.bank)
			if(Math.ceil(value/100)>199) return (int)Math.ceil(value/100);					//akkor a díj az összeg 1%-a (felfelé kerekítve), 
			return 200;																		//de legalább 200 Ft
		}
		if(Math.ceil((value*3)/100)>499) return (int)Math.ceil((value*3)/100);				//idegen bank esetén a díj az összeg 3%-a (felfelé kerekítve), 
		return 500;																			//de legalább 500 Ft
	}
}