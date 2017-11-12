package financial;

import financial.bank.Bank;
import financial.bank.ATM;
import financial.person.Customer;
import java.io.*;
import java.util.*;

public class Simulator{
	private ATM atm;
	//ArrayList<String> obj = new ArrayList<String>();
	ArrayList<Customer> customers = new ArrayList<Customer>();
	//private Customer[] customers;
	private PrintWriter pwLog;

	//KONSTRUKTOROK
	//public Simulator(String bankName, int initAmount, String outputFileName) throws IllegalArgumentException {
	public Simulator(String bankName, int initAmount, String outputFileName) {
		atm = ATM.makeATM(bankName, initAmount);									//Ha a paraméterek megfelelőek 
		if (atm == null) {
			throw new IllegalArgumentException("Wrong bankName or initAmount!");	//Ha az adatok nem megfelelőek, akkor a metódus dobjon IllegalArgumentException kivételt
		}
		//this.customers = new Customer[0];											//A Customer objektumokat tartalmazó adatszerkezet kezdetben ne tartalmazzon egyetlen objektumot sem
		try{
			pwLog = new PrintWriter(outputFileName);								//A PrintWriter objektumot hozzuk létre a megadott fájlnévhez
		} catch (IOException e){
			System.err.println("An IO error occurred.");							//ha a fájl nem hozható létre, vagy nem nyitható meg íráshoz, akkor a metódus engedje ki a keletkező kivételt	
		}																			
	}

	//METODUSOK
	private Customer getCustomerByName(String searchParameter){						//Legyen egy rejtett Customer visszatérési értékű szöveges paraméterű getCustomerByName metódus
		boolean found = false;
		int i = 0;
		while(i<customers.size() && found == false){
			//if(customers[i].getName() == searchParameter){
			if(customers.get(i).getName() == searchParameter){
				found = true;
			}
			++i;
		}

		if(found){
			--i;
			//return customers[i]; 													// amely visszaadja a customers adatszerkezetből annak a Customer objektumnak a referenciáját, amelynek a neve megegyezik a paraméterben megadott szöveggel
			return customers.get(i); 													// amely visszaadja a customers adatszerkezetből annak a Customer objektumnak a referenciáját, amelynek a neve megegyezik a paraméterben megadott szöveggel
		}
		return null;																//Ha az adatszerkezetben nincs megfelelő objektum, akkor a metódus adjon vissza null referenciát.
	}

	public void insertCustomer(String customerName, int birthYear, String bankName) {										//Legyen egy publikus void visszatérési értékű insertCustomermetódus, amellyel egy Customer objektumot lehet felvinni a nyilvántartásba
		if((Customer.makeCustomer(customerName,birthYear,bankName) != null) && (getCustomerByName(customerName) != null)){	// A személy akkor vehető fel, ha ilyen nevű még nem szerepel az adatszerkezetben, továbbá ha a személy adatai megfelelőek
			customers.add(Customer.makeCustomer(customerName,birthYear,bankName));											// Ha a személy már szerepel a nyilvántartásban, vagy valamelyik paraméter nem megfelelő, akkor a Customer objektumokat tároló adatszerkezet maradjon változatlan.
		}
	}

	public void withdrawCash(String customerName, int amount){													//Legyen egy publikus void visszatérési értékű withdrawCash metódus, amellyel készpénzt lehet felvenni az automatából.	//Ha ilyen nevű személy nem szerepel a nyilvántartásban (használd a getCustomerByName metódust), vagy ha az automatában nincs elég készpénz, vagy a felvenni kívánt összeg 0 vagy negatív, akkor a metódus nem csinál semmit
		if((getCustomerByName(customerName) != null) && (getCustomerByName(customerName).getAmount() >= amount) && (amount > 0)){				
			//int tmp = ATM.calculateFee(getCustomerByName(customerName).getBank(), amount);
			//ATM atmTmp = new ATM(getCustomerByName(customerName).getBank(), amount);
			//ATM atmTmp = new ATM.ATM(getCustomerByName(customerName).getBank(),amount);
			//atmTmp = atmTmp.calculateFee(getCustomerByName(customerName).getBank(), amount);
			if(ATM.calculateFee(getCustomerByName(customerName).getBank(), amount)<=getCustomerByName(customerName).getAmount){
				int fromCustomer = ATM.calculateFee(getCustomerByName(customerName).getBank(), amount);
				for (int i=0; i<fromCustomer; ++i) {
				 	getCustomerByName(customerName).Customer.decreaseAmount();
				}

				for (int i=0; i<amount; ++i) {
					getCustomerByName(customerName).ATM.decreaseAmount();
				}

				try ( PrintWriter pw = new PrintWriter(this.pwLog) ) {
					pw.println(customerName);
				} catch (FileNotFoundException e) {
					System.err.println("File cannot be opened.");
				}		
			}
		}
	}

	public void  depositCash(String customerName, int amount){
		if((getCustomerByName(customerName) != null) && (getCustomerByName(customerName).getAmount() >= amount) && (amount > 0)){

			for (int i=0; i<amount; ++i) {
			 	getCustomerByName(customerName).Customer.increaseAmount();
			}
			for (int i=0; i<amount; ++i) {
				getCustomerByName(customerName).ATM.increaseAmount();
			}			

			try ( PrintWriter pw = new PrintWriter(this.pwLog) ) {
				pw.println(customerName);
			} catch (FileNotFoundException e) {
				System.err.println("File cannot be opened.");
			}		
		}
	}

	public String CheckForSimulate(String line){
		String[] parts = line.split(":");
		if (parts.length()>2) return null;
		String[] CustomerData = parts[1].split(",");
		if (((parts[0] == "GET") || (parts[0] == "PUT")) && (CustomerData.length()>2)) return null; 
		if ((parts[0] == "REG") && (CustomerData.length()>3)) return null; 
		if (((parts[0] == "GET") || (parts[0] == "PUT")) && (!isNumeric(CustomerData[1]))) return null;
		if (parts[0] == "REG") && (!isNumeric(CustomerData[1]))) return null;
		return "OK";
	}

	public static boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray()){
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}

	public void simulate(String inputFileName){
		try ( BufferedReader br = new BufferedReader(new FileReader(inputFileName)) ) {
			String line;
			for (line = br.readLine(); line != null; line = br.readLine()) {
				if (CheckForSimulate(line) == "OK") {
					String[] parts = line.split(":");
					if (parts[0] == "REG") {
						String[] CustomerData = parts[1].split(",");
						insertCustomer(CustomerData[0],CustomerData[1],CustomerData[2]);				
					}

					if (parts[0] == "GET") {
						String[] CustomerData = parts[1].split(",");
						withdrawCash(CustomerData[0],CustomerData[1]);
					}

					if (parts[0] == "PUT") {
						String[] CustomerData = parts[1].split(",");
						depositCash(CustomerData[0],CustomerData[1]);
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("File does not exist.");
		} catch (IOException e) {
			System.err.println("An IO error occurred.");	
		}
	}

	public void close(){
		pwLog.close();
	}
}