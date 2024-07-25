package api.banking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import DataBase.FetchDataFromDB;
import DataBase.UpdateDataIntoDB;
import simulator.resoucrse.Encode_DecodeUsingBase64;

public class LoadMoney {
	
	double amount;
	String mode;
	String load_accNo;
	String load_ifsc;
	String sender_accNo;
	String sender_ifsc;
	
	
	public LoadMoney() {
		
		amount=500;
		mode="IMPS";
		load_accNo="ESCROWKO";
		load_ifsc="ESCR0123456";
		sender_accNo="abcdefg";
		sender_ifsc="SBIN0012345";
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		LoadMoney loadDetails=new LoadMoney();
		String apiKey=null;
		try {
			apiKey = Files.readString(Paths.get("./ApiKey/Virat 18.key"));
		} catch (IOException e) {
			System.err.println("Mention a correct api key file path: " + e.getMessage());
		}
		// Fetching ledger using apiKey
        String ledger = Encode_DecodeUsingBase64.decodeLedger(apiKey);
        if(FetchDataFromDB.validateForMoneyLoad(ledger,loadDetails.load_accNo,loadDetails.load_ifsc)) {
        	UpdateDataIntoDB.updateAvlBalanceForLoad(loadDetails.amount, ledger);
        	System.out.println("Money Loaded successfully!");
        }else {
        	System.err.println("Provided Acc_no or ifsc incorrect!");
        }
	}

}
