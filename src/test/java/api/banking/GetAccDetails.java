package api.banking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Map;

import DataBase.FetchDataFromDB;
import simulator.resoucrse.Encode_DecodeUsingBase64;

public class GetAccDetails {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		String apiKey = null;
		try {
			apiKey = Files.readString(Paths.get("./ApiKey/Adarsh Enterprises.key"));
		} catch (IOException e) {
			System.err.println("Mention a correct api key file path: " + e.getMessage());
		}
		// Fetching ledger using apiKey
		String ledger = Encode_DecodeUsingBase64.decodeLedger(apiKey);
		Map<String,String> accDetails = FetchDataFromDB.getAccDetails(ledger);
		System.out.println("acc_num: " + accDetails.get("acc_no"));
		System.out.println("ifsc: "+accDetails.get("ifsc"));

	}

}
