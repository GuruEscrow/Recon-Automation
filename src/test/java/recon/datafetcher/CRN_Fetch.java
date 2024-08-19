package recon.datafetcher;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import genricLibraries.ExcelUtility;
import genricLibraries.UtilitiesPath;

public class CRN_Fetch {

	static Map<String, Object> payout_logMap; // Key is payout_ref

	@Test
	public static void fetch_crn_payoutlog() {

		// ------------Fetching payout wo statement details File and storing into
		// payoutWSMap
		payout_logMap = new LinkedHashMap<>();
		String payoutwsPath = "C:/Users/Guruprasad V/Downloads/payout_log.json";
		if (Files.exists(Paths.get(payoutwsPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(payoutwsPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String payoutwsPayoutlog = null;
				while ((payoutwsPayoutlog = reader.readLine()) != null) {
					ObjectMapper mapper = new ObjectMapper();
					JsonNode payoutLog = mapper.readTree(payoutwsPayoutlog);
					String utr = payoutLog.get("utr").asText();
					String payout_ref = payoutLog.get("payout_ref").asText();
					String crn=payoutLog.get("crn").asText();

					payout_logMap.put(utr, crn);
				}
				reader.close();
			} catch (Exception e) {
				System.err
						.println("Error: at FileInputStream or readLine while Fetchin payoutws file " + e.getMessage());
			}
		}
		
		ExcelUtility excel = new ExcelUtility();
		excel.excelInit(UtilitiesPath.EXCEL_PATH);
		
		for(int i=0; i<excel.getLastRowNum("CRN fetch");i++) {
		
			String utr=excel.readDataFromExcel("CRN fetch", i, 0).split(",")[0];
			System.out.println(utr);
			if(utr.equals("")) {
				break;
			}
			
			if(payout_logMap.containsKey(utr)) {
				String crn=(String)payout_logMap.get(utr);
				excel.writeToExcel("CRN fetch", i, 1,crn , UtilitiesPath.EXCEL_PATH);
			}else {
				excel.writeToExcel("CRN fetch", i, 1,"No payout log" , UtilitiesPath.EXCEL_PATH);
			}
		}
		
		excel.closeExcel();
		
	}

}
