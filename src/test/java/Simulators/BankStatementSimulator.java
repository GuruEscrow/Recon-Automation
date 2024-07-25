package Simulators;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

import DataBase.FetchDataFromDB;
import DataBase.UpdateDataIntoDB;
import genricLibraries.PropertiesUtility;
import simulator.resoucrse.URL;

public class BankStatementSimulator {

	public static Map sendToBank(Map<String, Object> payLog, String timestamp, String payout_Mode, int randomNum) {
		PropertiesUtility property = new PropertiesUtility();
		property.propertiesInit(URL.PROPERTY_PATH);

		LocalTime date = LocalTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");

		// Batch1 down time timings
		String batch1_startTime = property.readData("batch1_startTime");
		String batch1_endTime = property.readData("batch1_endTime");
		LocalTime batch1_downstartTime = LocalTime.parse(batch1_startTime, format);
		LocalTime batch1_downendTimeFrm = LocalTime.parse(batch1_endTime, format);

		Map<String, Object> stmStatus = null;
		if ((date.isAfter(batch1_downstartTime) && date.isBefore(batch1_downendTimeFrm))) {
			stmStatus = subBranch(payLog, timestamp, payout_Mode);

		} else {
			try {
				LocalDateTime stmDate = LocalDateTime.now();
				// Closing the statement file by changing serial_num and appending the suffix
				// part
				DateTimeFormatter formatterForFileCheck = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate currentDate = LocalDate.parse(stmDate.format(formatterForFileCheck), formatterForFileCheck);
				LocalDate previousDate = LocalDate.parse(property.readData("prvDayDate"), formatterForFileCheck);

				if (!(previousDate.isEqual(currentDate))) {
					DateTimeFormatter yearMonth = DateTimeFormatter.ofPattern("yyyy_MM");
					DateTimeFormatter day = DateTimeFormatter.ofPattern("dd");
					String preYearMonth = previousDate.format(yearMonth);
					String preDay = previousDate.format(day);
					String preFullDate = previousDate.format(formatterForFileCheck);

					String prvFilePath = URL.AUTORECON_FOL_PATH + preYearMonth + "/" + preDay + "/" + preFullDate
							+ URL.BANK_STM_FILE_NAME;

					if (Files.exists(Paths.get(prvFilePath))) {
						String stmRemovedComma = null;
						try {
							String stms = Files.readString(Paths.get(prvFilePath));
							stmRemovedComma = stms.substring(0, stms.length() - 1);
						} catch (IOException e) {
							System.out.println(
									"Provide correct bankstatment file path to remove comma at end: " + e.getMessage());
						}
						String sufixStm = stmRemovedComma + "]},\"message\":\"statements\",\"status\":200}";
						ObjectMapper json = new ObjectMapper();
						JsonNode stm = json.readTree(sufixStm);
						ObjectNode objectNode = (ObjectNode) stm;
						ObjectNode data = (ObjectNode) objectNode.get("data");
						data.put("rowsCount", Integer.parseInt(property.readData("serial_num")) - 1);
						objectNode.put("data", data);
						// data.put("openingBalance", null);

						FileWriter bankStm = null;
						try {
							bankStm = new FileWriter(prvFilePath, false);
							bankStm.write(json.writeValueAsString(objectNode));
						} catch (IOException e) {
							System.out
									.println("Provide correct bankstatment file path to add suffix: " + e.getMessage());
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

					//updating the current date in prvDayDate file of properties file
					property.writeToProperties("prvDayDate", stmDate.format(formatterForFileCheck), URL.PROPERTY_PATH);
					//Changing the serial_num as it's bankstatement folder got changed
					property.writeToProperties("serial_num", "1", URL.PROPERTY_PATH);
				}

				// Changing the subserial_1 when subserial_num not equal to 1
				if (!(property.readData("subserial_num").equals("1"))) {
					property.writeToProperties("subserial_num", "1", URL.PROPERTY_PATH);
				}
				// Adding the statement header for new file
				if (property.readData("serial_num").equals("1")) {
					String prefixOfStm = "{\"data\":{\"statusCode\":200,\"message\":\"OK\",\"requestTimeEpoch\":\"1719396231847\","
							+ "\"requestId\":\"a548beda-7cdc-4b42-98a3-959a3ba9d7c4\",\"rowsCount\":19,\"openingBalance\":15,"
							+ "\"accountNumber\":\"924020027157005\",\"currency\":\"INR\",\"data\":[";

					FileWriter bankStm = null;
					try {
						bankStm = new FileWriter(property.readData("bankStmPath"), true);
						bankStm.write(prefixOfStm);
					} catch (IOException e) {
						System.out.println("Provide correct bankstatment file path to add prefix: " + e.getMessage());
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
				// Fetching the bankStm path from properties file
				String bankStmPath = property.readData("bankStmPath");
				stmStatus = mainBranch(payLog, timestamp, payout_Mode, bankStmPath, randomNum);
			} catch (Exception e) {
				System.out.println("Exception at BankStatement Simulatory (Doing main branch): " + e.getMessage());
			}

		}

		return stmStatus;
	}

	public static Map<String, Object> mainBranch(Map<String, Object> payLog, String timestamp, String payout_Mode,
			String stmFilePath, int randomNum) throws ClassNotFoundException, SQLException {
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

		// Generating the utr number
		StringBuilder utr = new StringBuilder("4");
		Random random = new Random();
		for (int i = 0; i <= 11; i++) {
			utr.append(random.nextInt((10 - 1) + 1) + 1);
		}
		transaction.put("utrNumber", utr);

		// Generarting the internalRef number
		StringBuilder internalRef = new StringBuilder("4");
		for (int i = 0; i <= 11; i++) {
			internalRef.append(random.nextInt((10 - 1) + 1) + 1);
		}
		transaction.put("transactionParticulars", payout_Mode + "/" + utr + "/" + payLog.get("ledger_label")
				+ "/KKBK/X7005/" + payLog.get("enriched_transaction_note"));
		transaction.put("chqNumber", " ");
		transaction.put("valueDate", statementDate.split(" ")[0]);
		transaction.put("amount", payLog.get("amount"));
		transaction.put("drcr", "DR");
		transaction.put("balance", FetchDataFromDB.getAvlBlc((String) payLog.get("ledger_label")));
		transaction.put("paymentMode", "IMPS");

		transaction.put("internalReferenceNumber", internalRef);
		transaction.put("remittingBranch", "JP NAGAR 5TH PHASE KT");
		transaction.put("remittingBankName", "AXIS BANK");
		transaction.put("remittingAccountNumber", "924020027157005");
		transaction.put("remittingAccountName", "PHEDORA TECHNOLOGIES PRIVATE LIMITED");
		transaction.put("remittingIFSC", " ");
		transaction.put("benficiaryBranch", "KHARABWADI");
		transaction.put("benficiaryName", payLog.get("bene_name"));
		transaction.put("benficiaryAccountNumber", payLog.get("bene_ac_no"));
		transaction.put("benficiaryIFSC", payLog.get("bene_ifsc"));
		transaction.put("channel", payout_Mode);
		transaction.put("timeStamp", timestamp.split(",")[1].trim());
		transaction.put("remarks", payLog.get("ledger_label") + "/" + payLog.get("enriched_transaction_note"));
		transaction.put("transactionCurrencyCode", "INR");

		// Sending utr and the payout status to update in the payout log
		Map<String, Object> stmStatus = new LinkedHashMap<String, Object>();
		stmStatus.put("utr", utr);
		String status = null;
		if (randomNum == 1) {
			status = "failed";
		} else {
			status = "processed";
		}
		stmStatus.put("status", status);
		stmStatus.put("internalRef", internalRef);

		// Writing the bank statement to file
		Gson json = new Gson();
		FileWriter bankStm = null;
		try {
			bankStm = new FileWriter(stmFilePath, true);
			bankStm.write(json.toJson(transaction) + ",");
			System.out.println("Bank statment is generated");
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

		return stmStatus;
	}

	public static Map<String, Object> subBranch(Map<String, Object> payLog, String timestamp, String payout_Mode) {
		Map<String, Object> transaction = new LinkedHashMap<>();
		PropertiesUtility property = new PropertiesUtility();
		property.propertiesInit(URL.PROPERTY_PATH);

		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String statementDate = date.format(format);

		// Populate the transaction details
		int subserialNumber = Integer.parseInt(property.readData("subserial_num"));
		transaction.put("serialNumber", subserialNumber);
		property.writeToProperties("subserial_num", String.valueOf(++subserialNumber), URL.PROPERTY_PATH);
		transaction.put("transactionDate", timestamp.split(",")[0].trim());
		transaction.put("pstdDate", statementDate);

		StringBuilder utr = new StringBuilder("4");
		Random random = new Random();
		for (int i = 0; i <= 11; i++) {
			utr.append(random.nextInt((10 - 1) + 1) + 1);
		}
		transaction.put("utrNumber", utr);
		StringBuilder internalRef = new StringBuilder("4");
		for (int i = 0; i <= 11; i++) {
			internalRef.append(random.nextInt((10 - 1) + 1) + 1);
		}
		transaction.put("transactionParticulars", payout_Mode + "/" + utr.toString() + "/" + payLog.get("ledger_label")
				+ "/KKBK/X7005/" + payLog.get("enriched_transaction_note"));
		transaction.put("chqNumber", " ");
		transaction.put("valueDate", statementDate.split(" ")[0]);

		// Fetching amount and ledger label
		double amt = (double) payLog.get("amount");
		String ladger_label = (String) payLog.get("ledger_label");

		transaction.put("amount", amt);
		transaction.put("drcr", "DR");

		// Updating the available balance after pyaout
		UpdateDataIntoDB.updateAvlBalanceForPayout(amt, ladger_label);

		transaction.put("balance", FetchDataFromDB.getAvlBlc(ladger_label));
		transaction.put("paymentMode", "IMPS");
		transaction.put("utrNumber", utr.toString());
		transaction.put("internalReferenceNumber", internalRef);
		transaction.put("remittingBranch", "JP NAGAR 5TH PHASE KT");
		transaction.put("remittingBankName", "AXIS BANK");
		transaction.put("remittingAccountNumber", "924020027157005");
		transaction.put("remittingAccountName", "PHEDORA TECHNOLOGIES PRIVATE LIMITED");
		transaction.put("remittingIFSC", " ");
		transaction.put("benficiaryBranch", "KHARABWADI");
		transaction.put("benficiaryName", payLog.get("bene_name"));
		transaction.put("benficiaryAccountNumber", payLog.get("bene_ac_no"));
		transaction.put("benficiaryIFSC", payLog.get("bene_ifsc"));
		transaction.put("channel", payout_Mode);
		transaction.put("timeStamp", timestamp.split(",")[1].trim());
		transaction.put("remarks", payLog.get("ledger_label") + "/" + payLog.get("enriched_transaction_note"));
		transaction.put("transactionCurrencyCode", "INR");

		Gson json = new Gson();
		UpdateDataIntoDB.insertSubBranchStatement(utr.toString(), json.toJson(transaction));
		System.out.println("Statment updated to queue of sub branch");

		Map<String, Object> stmStatus = new LinkedHashMap<String, Object>();
		stmStatus.put("utr", utr.toString());
		stmStatus.put("status", "processed");

		return stmStatus;
	}
}
