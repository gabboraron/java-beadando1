import financial.bank.Bank;

public class ATM{

	private Bank bank; 			// melyik bankhoz tartozik az automata
	private int amount;			// aktuálisan mennyi pénz van az automatában

	
	//KONSTRUKTOROK
	private ATM(Bank bank, int amount){
		this.bank = bank;
		this.amount = amount;
	}

	//METODUS													// ellenőrzi a paramétereket, és amennyiben azok megfelelőek, akkor létrehozza, ha nem akkor null
	public ATM makeATM(String bankName, int amount){											
		try{
		 	if(amount > -1) return (new ATM(Bank.valueOf(bankName), amount));		 // bankName-nek a Bank típus egyik lehetséges értékével kell egyeznie, egyenlegnek pozitívnak kell lennie
		} catch(IllegalArgumentException e) {
			return null;
		}
		return null;
	}

	public int getAmount(){
		return this.amount;
	}

	public int decreaseAmount(){
		--this.amount;
	}

	public int increaseAmount(){
		++this.amount;
	}

	public int calculateFee(Bank bank, int value){
		if(Bank.valueOf(bank) == this.bank){								//ha az automata a pénzfelvevő saját bankjának automatája, 
			if(Math.ceil(value/100)>199) return Math.ceil(value/100);		//akkor a díj az összeg 1%-a (felfelé kerekítve), 
			return 200;														//de legalább 200 Ft
		}
		if(Math.ceil((value*3)/100)>499) return Math.ceil((value*3)/100);
		return 500;
	}
}