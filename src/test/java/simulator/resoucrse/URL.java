package simulator.resoucrse;

public interface URL {
	
	String PROPERTY_PATH="./src/test/resources/data.properties";
	
	String SIGN_PAYLOAD_FILE_PATH="D:/Phedora/Eazypayouts/Automation Recon Log/Auto Signed_Payload.txt";
	
	String PAYOUT_LOG_FILEPATH="D:/Phedora/Eazypayouts/Automation Recon Log/Auto Payout_Log.json";
	
	String AUTORECON_FOL_PATH="D:/Phedora/Eazypayouts/Automation Recon Log/";
	
	String PAYLOAD_FILE_NAME="_payload.txt";
	String PAYOUT_LOG_FILE_NAME="_payoutLog.json";
	String BANK_STM_FILE_NAME="_bankStm.json";
	
//	---------------------Recon Test execution paths------------------------------------------
	String EOD_PATH="D:/Phedora/Eazypayouts/BRS Script/axis-brs/TestData/EOD/";
	
	//Output File names
	String brsComplete="brs_complete.json";
	String cowostatement="collects_wo_statement.json";
	String fpwmore2stm="failed_payout_with_more_than_2_statement.json";
	String invalidBeneDetails="bene_details_mismatch.json";
	String invalidSignature="invalid_signatures.json";
	String ppwmore1stm="passed_payout_with_more_than_1_statement.json";
	String payloadWoPayout="payload_wo_payout.json";
	String payloadWoStatement="payload_wo_statement.json";
	String payoutWoCredit="payout_wo_credit.json";
	String payoutWoDebit="payout_wo_debit.json";
	String payoutWoPayload="payout_wo_payload.json";
	String payoutWoStm="payout_wo_statement.json";
	String stmWithMissingFields="statements_with_missing_fields.json";
	String unresolvedStm="unresolved_statements.json";
	String beneDetailsAbsent="bene_details_absent.json";
	String payoutWiMisField="payouts_with_missing_fields.json";
	

}
