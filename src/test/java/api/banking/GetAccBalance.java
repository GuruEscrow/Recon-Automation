package api.banking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import DataBase.FetchDataFromDB;
import simulator.resoucrse.Encode_DecodeUsingBase64;

public class GetAccBalance {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		String apiKey=null;
		try {
			apiKey = Files.readString(Paths.get("./ApiKey/Virat 18.key"));
		} catch (IOException e) {
			System.err.println("Mention a correct api key file path: " + e.getMessage());
		}
		// Fetching ledger using apiKey
        String ledger = Encode_DecodeUsingBase64.decodeLedger(apiKey);
		double avl_blc=FetchDataFromDB.getAvlBlc(ledger);
		System.out.println("avl_blc: "+avl_blc);
	}

}
