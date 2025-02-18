package recon.datafetcher;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import simulator.resoucrse.URL;

public class ResolveInvalidSignature {

	@Test
	public void fetchUTRForIS_fromPoLog() {

		// Dates
		String startDate = "2024-12-18";

		// Formatting the start date and adding the plus 1 day for end date
		DateTimeFormatter pstDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(startDate, formatter);
		// String endDate = date.plusDays(1).format(formatter);

		/*----------------------------------------------Creating Folder and files if not exists----------------------------------------------*/
		DateTimeFormatter yearFolNameFormat = DateTimeFormatter.ofPattern("yyyy");
		DateTimeFormatter monthFolNameFormat = DateTimeFormatter.ofPattern("MM");
		DateTimeFormatter dayFolNameFormat = DateTimeFormatter.ofPattern("dd");
		DateTimeFormatter fileNameFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String year = "/" + yearFolNameFormat.format(date);
		String month = "/" + monthFolNameFormat.format(date);
		String day = "/" + dayFolNameFormat.format(date);
		String inFolder = "/in/";
		String outFolder = "/out/";

		// -------------------Fetching Current day payouts log and storing in the map
		String payoutLogPath = URL.EOD_PATH + year + month + day + inFolder + URL.payoutLog;
		Map<String, String> payoutsMap = new LinkedHashMap<String, String>();
		if (Files.exists(Paths.get(payoutLogPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(payoutLogPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String payout = null;
				while ((payout = reader.readLine()) != null) {
					ObjectMapper mapper = new ObjectMapper();
					JsonNode payoutLog = mapper.readTree(payout);

					String utr = payoutLog.get("utr").asText();
					String amt = payoutLog.get("amount").asText();
					String beneAccNo = payoutLog.get("bene_ac_no").asText();
					String crn = payoutLog.get("crn").asText();
					String payout_status = payoutLog.get("payout_status").asText();
					String payout_ref = payoutLog.get("payout_ref").asText();

					payoutsMap.put(payout_ref, utr);

				}
				reader.close();
			} catch (Exception e) {
				System.err.println(
						"Error: at FileInputStream or readLine while Fetchin unresolvedStm file " + e.getMessage());
			}
		} else {
			System.out.println("File doesnot exits: --> path: " + payoutLogPath);
		}

		String invalidSignaturefilePath = URL.EOD_PATH + year + month + day + outFolder + URL.invalidSignature;
		Set<String> invalidSignPayoutRef = new HashSet<String>();
		if (Files.exists(Paths.get(invalidSignaturefilePath))) {

			FileInputStream fis = null;

			try {
				fis = new FileInputStream(invalidSignaturefilePath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String insPayload = null;
				while ((insPayload = reader.readLine()) != null) {
					int startIndex = insPayload.indexOf("{");
					ObjectMapper mapper = new ObjectMapper();
					JsonNode jsonPayload = mapper.readTree(insPayload.substring(startIndex));
					ArrayNode arrayNode = (ArrayNode) jsonPayload.get("payouts");
					JsonNode payoutDetails = arrayNode.get(0);
					String payout_ref = payoutDetails.get("payout_ref").asText();

					if (payoutsMap.containsKey(payout_ref)) {
						if(!invalidSignPayoutRef.contains(payout_ref))
						//System.out.println(payout_ref + " " + payoutsMap.get(payout_ref));
						System.out.println(payoutsMap.get(payout_ref));
						
						invalidSignPayoutRef.add(payout_ref);
					} else {
						System.out.println(payout_ref + " No_payoutlog");
					}
				}
				reader.close();
			} catch (Exception e) {
				System.err.println("Error: at FileInputStream or readLine while Fetchin invalid signature details file "
						+ e.getMessage());
			}

		} else {
			System.out.println("File doesnot exits: --> path: " + invalidSignaturefilePath);
		}
	}
}
