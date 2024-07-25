package Simulators;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;

import com.google.gson.Gson;

import genricLibraries.PropertiesUtility;
import simulator.resoucrse.URL;

public class PayoutLogSimulator {

	public static void logThePayout(String ledgerLabel, String payout_ref, double amt, String txn_note, String accNo,
			String ifsc, String beneName, String txnCode, String timeStamp, String payout_Mode, int randomNum) {
		PropertiesUtility property = new PropertiesUtility();
		property.propertiesInit(URL.PROPERTY_PATH);
		Map<String, Object> dataMap = new LinkedHashMap<>();

		// Generating CRN
		String crn = txnCode + RandomStringUtils.randomAlphabetic(9).toUpperCase();
		dataMap.put("crn", crn);
		dataMap.put("ledger_label", ledgerLabel);
		dataMap.put("payout_ref", payout_ref);
		dataMap.put("utr", "");
		dataMap.put("amount", amt);
		dataMap.put("transaction_note", txn_note);
		dataMap.put("enriched_transaction_note", txnCode + txn_note.substring(0, 5));
		dataMap.put("bene_name", beneName);
		dataMap.put("bene_ac_no", accNo);
		dataMap.put("bene_ifsc", ifsc);
		dataMap.put("payout_status", "");

		Gson json = new Gson();

		FileWriter writePayoutLog = null;
		try {
			writePayoutLog = new FileWriter(property.readData("payoutLogPath"), true);
			Map payoutStatus = BankStatementSimulator.sendToBank(dataMap, timeStamp, payout_Mode, randomNum);

			// Updating UTR and payout status into payout log
			String utr = String.valueOf(payoutStatus.get("utr"));
			String internalRef = String.valueOf(payoutStatus.get("internalRef"));
			dataMap.put("utr", utr);
			dataMap.put("payout_status", payoutStatus.get("status"));

			// writing payout log into payout log file
			writePayoutLog.write(json.toJson(dataMap));
			writePayoutLog.write(System.lineSeparator());
			System.out.println("Payout Log Uploaded");

			// creating refund if payout got failed
			if (payoutStatus.get("status").equals("failed")) {
				TestCaseSimulator.creditForFailedPayout(dataMap, timeStamp, payout_Mode, utr,internalRef);
			}
			System.out.println("--------------------------------------------------------------");
		} catch (IOException e) {
			System.err.println("Mention correct payout log file path: " + e.getMessage());
		} finally {
			if (writePayoutLog != null)
				try {
					writePayoutLog.close();
				} catch (IOException e) {
					System.err.println("Problem in closing payoutlog file: " + e.getMessage());
				}
		}
	}

}
