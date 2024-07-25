package Simulators;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import genricLibraries.PropertiesUtility;
import simulator.resoucrse.URL;

public class PushSub_MainBrachSimulator {

	public static boolean subQueueToMainBrachStm(String statement) throws IOException {

		boolean flag = false;
		PropertiesUtility property = new PropertiesUtility();
		property.propertiesInit(URL.PROPERTY_PATH);

		/*----------------------------------------------Creating Folder and files if not exists----------------------------------------------*/
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter yearFolNameFormat = DateTimeFormatter.ofPattern("yyyy");
		DateTimeFormatter monthFolNameFormat = DateTimeFormatter.ofPattern("yyyy_MM");
		DateTimeFormatter dayFolNameFormat = DateTimeFormatter.ofPattern("dd");
		DateTimeFormatter fileNameFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String yearFolderPath = URL.AUTORECON_FOL_PATH + date.format(yearFolNameFormat);
		if (!(Files.exists(Paths.get(yearFolderPath)))) {
			Files.createDirectories(Paths.get(yearFolderPath));
		}
		yearFolderPath += "/";

		String monthFolderPath = yearFolderPath + date.format(monthFolNameFormat);
		if (!(Files.exists(Paths.get(monthFolderPath)))) {
			Files.createDirectories(Paths.get(monthFolderPath));
		}
		monthFolderPath += "/";

		String dayFolderPath = monthFolderPath + date.format(dayFolNameFormat);
		if (!(Files.exists(Paths.get(dayFolderPath)))) {
			Files.createDirectories(Paths.get(dayFolderPath));
		}
		dayFolderPath += "/";

		String payloadFilePath = dayFolderPath + date.format(fileNameFormat) + URL.PAYLOAD_FILE_NAME;
		if (!(Files.exists(Paths.get(payloadFilePath)))) {
			Files.createFile(Paths.get(payloadFilePath));
			property.writeToProperties("payloadPath", payloadFilePath, URL.PROPERTY_PATH);
		}

		String payoutLogFilePath = dayFolderPath + date.format(fileNameFormat) + URL.PAYOUT_LOG_FILE_NAME;
		if (!(Files.exists(Paths.get(payoutLogFilePath)))) {
			Files.createFile(Paths.get(payoutLogFilePath));
			property.writeToProperties("payoutLogPath", payoutLogFilePath, URL.PROPERTY_PATH);
		}

		String stmFilePath = dayFolderPath + date.format(fileNameFormat) + URL.BANK_STM_FILE_NAME;
		if (!(Files.exists(Paths.get(stmFilePath)))) {
			Files.createFile(Paths.get(stmFilePath));
			property.writeToProperties("bankStmPath", stmFilePath, URL.PROPERTY_PATH);
		}

		/*-------------------------------Here checking the statement with previous date and closing the statement---------------------*/
		LocalDateTime stmDate = LocalDateTime.now();
		// Closing the statement file by changing serial_num and appending the suffix
		// part
		DateTimeFormatter formatterForFileCheck = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate currentDateminus_1 = LocalDate.parse(stmDate.minusDays(1).format(formatterForFileCheck),
				formatterForFileCheck);
		LocalDate previousDate = LocalDate.parse(property.readData("prvDayDate"), formatterForFileCheck);

		if (previousDate.equals(currentDateminus_1)) {

			String stmRemovedComma = null;

			DateTimeFormatter yearMonth = DateTimeFormatter.ofPattern("yyyy_MM");
			DateTimeFormatter day = DateTimeFormatter.ofPattern("dd");
			String preYearMonth = previousDate.format(yearMonth);
			String preDay = previousDate.format(day);
			String preFullDate = previousDate.format(formatterForFileCheck);

			String prvFilePath = URL.AUTORECON_FOL_PATH + preYearMonth + "/" + preDay + "/" + preFullDate
					+ URL.BANK_STM_FILE_NAME;

			if (Files.exists(Paths.get(prvFilePath))) {
				try {
					String stms = Files.readString(Paths.get(prvFilePath));
					stmRemovedComma = stms.substring(0, stms.length() - 1);
				} catch (Exception e) {
					System.err.println(
							"Provide correct bankstatment file path to remove comma at end: " + e.getMessage());
				}
				String sufixStm = stmRemovedComma + "]},\"message\":\"statements\",\"status\":200}";
				ObjectMapper json = new ObjectMapper();
				JsonNode stm = null;
				try {
					stm = json.readTree(sufixStm);
				} catch (Exception e1) {
					System.err.println("Error while reading the string(stm): " + e1.getMessage());
				}
				ObjectNode objectNode = (ObjectNode) stm;
				ObjectNode data = (ObjectNode) objectNode.get("data");
				data.put("rowsCount", Integer.parseInt(property.readData("serial_num")) - 1);
				objectNode.put("data", data);
				// data.put("openingBalance", null);
				FileWriter bankStm = null;

				try {
					bankStm = new FileWriter(prvFilePath, false);
					bankStm.write(json.writeValueAsString(objectNode));
				} catch (Exception e) {
					System.out.println("Provide correct bankstatment file path to add suffix: " + e.getMessage());
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

			//Updating the prvDayDate with current in properties file
			property.writeToProperties("prvDayDate", stmDate.format(formatterForFileCheck), URL.PROPERTY_PATH);
			//Updating the serail_num in properties filed as new bank statement is generating
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
					} catch (Exception e) {
						System.out.println("Error closing the file writer: " + e.getMessage());
					}
				}
			}
		}

		/*--------------------------------------------pushing the sub branch queue to main branch statement----------------------------------------------------*/
		// LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String statementDate = date.format(format);

		ObjectMapper mapper = new ObjectMapper();
		try {
			ObjectNode stmInJson = (ObjectNode) mapper.readTree(statement);
			int serialNumber = Integer.parseInt(property.readData("serial_num"));
			stmInJson.put("serialNumber", serialNumber);
			property.writeToProperties("serial_num", String.valueOf(++serialNumber), URL.PROPERTY_PATH);
			stmInJson.put("pstdDate", statementDate);
			stmInJson.put("valueDate", statementDate.split(" ")[0]);

			String stmFilePathFromPro = property.readData("bankStmPath");
			String finalStm = mapper.writeValueAsString(stmInJson);

			FileWriter bankStm = null;
			try {
				bankStm = new FileWriter(stmFilePathFromPro, true);
				bankStm.write(finalStm + ",");
				flag = true;
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
		} catch (IOException e) {
			System.out.println("Problem in parsing stm to json: " + e.getMessage());
		}
		return flag;
	}

}
