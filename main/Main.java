package main;

import financial.bank.Bank;
import financial.bank.ATM;
import financial.person.Customer;
import financial.Simulator;

public class Main{
	public static void main(String[] args) {
		if ((args.length<3) || (args.length>3)) {
			System.out.println("Wrong parameters!");
		} else {
			try{
				Simulator(args[0],1000000,args[2]);
				Simulator.simulate(args[1]);
				Simulator.close();
			} catch (IOException e){
				System.err.println("An IO error occurred.");
			}
		}
	}
}