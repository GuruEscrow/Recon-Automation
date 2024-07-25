package Simulators;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;

import DataBase.FetchDataFromDB;
import DataBase.UpdateDataIntoDB;
import genricLibraries.PropertiesUtility;
import simulator.resoucrse.URL;

public class TestCaseSimulator {

	public static void creditForFailedPayout(Map<String, Object> payLog, String timestamp, String payout_mode,
			String utr, String internalRef) {
		Map<String, Object> transaction = new LinkedHashMap<>();
		PropertiesUtility property = new PropertiesUtility();
		property.propertiesInit(URL.PROPERTY_PATH);

		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String statementDate = date.format(format);

		// Populate the transaction details
		int serialNumber = Integer.parseInt(property.readData("serial_num"));
		transaction.put("serialNumber", serialNumber);
		property.writeToProperties("serial_num", String.valueOf(++serialNumber), URL.PROPERTY_PATH);
		transaction.put("transactionDate", timestamp.split(",")[0].trim());
		transaction.put("pstdDate", statementDate);
		transaction.put("utrNumber", utr);

		// Storing ledger label in string
		String ledger_label = (String) payLog.get("ledger_label");

		// Getting account details from DB
		Map<String, String> accDetails = FetchDataFromDB.getAccDetails(ledger_label);
		String acc_no = (String) accDetails.get("acc_no");

		transaction.put("transactionParticulars", "REV:" + payout_mode + " " + acc_no + " Ref " + utr);
		transaction.put("chqNumber", " ");
		transaction.put("valueDate", statementDate.split(" ")[0]);
		transaction.put("amount", payLog.get("amount"));
		transaction.put("drcr", "CR");

		// Updating the balance in DB
		UpdateDataIntoDB.updateAvlBalanceForLoad((double) payLog.get("amount"), ledger_label);

		transaction.put("balance", FetchDataFromDB.getAvlBlc(ledger_label));
		transaction.put("paymentMode", payout_mode);

		transaction.put("internalReferenceNumber", internalRef);
		transaction.put("remittingBranch", " ");
		transaction.put("remittingBankName", "AXIS BANK");
		transaction.put("remittingAccountNumber", " ");
		transaction.put("remittingAccountName", " ");
		transaction.put("remittingIFSC", " ");
		transaction.put("benficiaryBranch", "JP NAGAR 5TH PHASE KT");
		transaction.put("benficiaryName", "PHEDORA TECHNOLOGIES PRIVATE LIMITED");
		transaction.put("benficiaryAccountNumber", "924020027157005");
		transaction.put("benficiaryIFSC", " ");
		transaction.put("channel", payout_mode);
		transaction.put("timeStamp", timestamp.split(",")[1].trim());
		transaction.put("remarks", "need to add the txn particulars");
		transaction.put("transactionCurrencyCode", "INR");

		// Writing the refund statement to file
		String bankStmPath=property.readData("bankStmPath");
		Gson json = new Gson();
		FileWriter bankStm = null;
		try {
			bankStm = new FileWriter(bankStmPath, true);
			bankStm.write(json.toJson(transaction) + ",");
			System.out.println("Amount Refunded and credit statement generated");
		} catch (IOException e) {
			System.out.println("Provide correct bankstatment file path: " + e.getMessage());
		} finally {
			if (bankStm != null) {
				try {
					bankStm.close();
				} catch (IOException e) {
					System.out.println("Error closing the file writer: " + e.getMessage());
				}
			}
		}

	}

}
