package recon.datafetcher;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Update_EI_Stmts {

//	Adding UTR's in bank statements contains EI in the UTR
	@Test
	public void fetch_utr_for_EI_and_updated() throws Exception {

		// Change the path accordingly
		String directoryPath = "C:/BRS/EOD/2024/11/24";

		// Paths to fetch the EI transaction from the utrTranslations file
		String stmsPath = directoryPath + "/in/actions_taken_to_close_eod/utrTranslations.json";

		Map<String, Object> utr_EI_stms = new LinkedHashMap<String, Object>();
		if (Files.exists(Paths.get(stmsPath))) {
			FileInputStream fis = null;

			try {
//				fis = new FileInputStream(stmsPath);
//				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
//
//				String unresovedStm = null;
//				while ((unresovedStm = reader.readLine()) != null) {
//
//					JsonObject payoutLog = JsonParser.parseString(unresovedStm).getAsJsonObject();
//					String transPr = payoutLog.get("transactionParticulars").getAsString();
//					payoutLog.addProperty("utrNumber", transPr.split("/")[2]);
//
//					utr_EI_stms.put(transPr.split("/")[2], payoutLog);
//
////						FileWriter write = new FileWriter(writeFile, true);
////
////						write.write(payoutLog.toString());
////						write.write(System.lineSeparator());
////						write.close();
//
//				}
//				reader.close();

				// Reading statement file and updating the updated statement in mentioned path
				// in write file
				String bankstmsPath = directoryPath + "/in/bank_statements.json";
				String writeFile = "D:/payout_wo_statement.json";

				// Truncating the file before writing it
				Files.newBufferedWriter(Path.of(writeFile), StandardOpenOption.TRUNCATE_EXISTING);

				Map<String, Object> bankstms = new LinkedHashMap<String, Object>();

				fis = new FileInputStream(bankstmsPath);
				BufferedReader reader_2 = new BufferedReader(new InputStreamReader(fis));

				String bankstatements = null;
				while ((bankstatements = reader_2.readLine()) != null) {

					JsonObject payoutLog = JsonParser.parseString(bankstatements).getAsJsonObject();

					String utr = null;

					if (payoutLog.get("utrNumber").getAsString().equals("EI")) {
						String transPr = payoutLog.get("transactionParticulars").getAsString();
						payoutLog.addProperty("utrNumber", transPr.split("/")[2]);
						utr = transPr.split("/")[2];
					} else {
						utr = payoutLog.get("utrNumber").getAsString();
					}

					FileWriter write = new FileWriter(writeFile, true);

					write.write(payoutLog.toString());
					write.write(System.lineSeparator());
					write.close();

				}
				reader_2.close();

			} catch (Exception e) {
				System.err
						.println("Error: at FileInputStream or readLine while Fetchin payoutws file " + e.getMessage());
			}
		} else {
			System.err.println("Updated the Actions taken at eod");
			throw new Exception();
		}
	}
}
