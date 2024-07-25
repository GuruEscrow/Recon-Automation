package Simulators;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;

import genricLibraries.ExcelUtility;
import genricLibraries.UtilitiesPath;

public class ClientDetailsSimulator {
	
	public static Map<String,Object> detailsDoc(String ledgerlabel, String apikey,String publicCrt) {
		
		ExcelUtility excel=new ExcelUtility();
		excel.excelInit(UtilitiesPath.EXCEL_PATH);
		
		String ledger_label=null;
		String trans_code=null;
		String gstNo=null;
		String companyName=null;
		String mailID=null;
		String pan=null;
		String mobileNum=null;
		String name=null;
		for(int i=0;i<excel.getLastRowNum("Sheet1");i++) {
			
			if(excel.readDataFromExcel("Sheet1", i, 0).equals(ledgerlabel)) {
				ledger_label=excel.readDataFromExcel("Sheet1", i, 0);
				trans_code=excel.readDataFromExcel("Sheet1", i, 1);
				gstNo=excel.readDataFromExcel("Sheet1", i, 2);
				companyName=excel.readDataFromExcel("Sheet1", i, 3);
				mailID=excel.readDataFromExcel("Sheet1", i, 4);
				pan=excel.readDataFromExcel("Sheet1", i, 5);
				mobileNum=excel.readDataFromExcel("Sheet1", i, 6);
				name=excel.readDataFromExcel("Sheet1", i, 7);
				break;
			}
			
		}
		excel.closeExcel();
		
		Map<String, Object> apiClientData = new LinkedHashMap<>();

        // Populate the LinkedHashMap with updated values
        apiClientData.put("ledger_label", ledger_label);
        apiClientData.put("tran_code", trans_code);
        apiClientData.put("gst_no", gstNo);
        apiClientData.put("name", companyName);
        apiClientData.put("registered_email", mailID);
        apiClientData.put("pan", pan);
        
        Map<String, Object> adminUser = new LinkedHashMap<>();
        adminUser.put("mobile", mobileNum);
        adminUser.put("name", name);
        adminUser.put("email", mailID);
        
        apiClientData.put("admin_user", adminUser);
        apiClientData.put("status", "active");

        Map<String, Object> config = new LinkedHashMap<>();
        config.put("ledger_label", ledger_label);
        config.put("collect_restricted_to_whitelisted_accounts", true);

        Map<String, Object> webhook = new LinkedHashMap<>();
        webhook.put("production", "https://updated.pout.paynpro.com/payout/v1/callback/eazypayout/response");
        webhook.put("sandbox", "https://updated.dk.nodalaccount.com/payout/v1/callback/eazypayout/response");
        webhook.put("sandbox_live", "not configured");
        
        config.put("webhook", webhook);
        config.put("prodPubCert", publicCrt);

        Map<String, Object> collectMethods = new LinkedHashMap<>();
        
        Map<String, Object> creditCard = new LinkedHashMap<>();
        creditCard.put("fee_collect_method", "deduct_from_collects_and_payouts");
        creditCard.put("fee_amount", 0);
        creditCard.put("fee_type", "flat");
        creditCard.put("enabled", false);
        
        collectMethods.put("credit_card", creditCard);

        Map<String, Object> eNach = new LinkedHashMap<>();
        eNach.put("fee_collect_method", "deduct_from_collects_and_payouts");
        eNach.put("fee_amount", 0);
        eNach.put("fee_type", "flat");
        
        collectMethods.put("eNach", eNach);

        Map<String, Object> virtualAccountNo = new LinkedHashMap<>();
        virtualAccountNo.put("fee_collect_method", "deduct_from_collects_and_payouts");
        virtualAccountNo.put("fee_amount", 0);
        virtualAccountNo.put("fee_type", "flat");
        virtualAccountNo.put("enabled", true);
        virtualAccountNo.put("prefix_string", "KNNET");
        
        collectMethods.put("virtual_account_no", virtualAccountNo);

        Map<String, Object> netBanking = new LinkedHashMap<>();
        netBanking.put("fee_collect_method", "deduct_from_collects_and_payouts");
        netBanking.put("fee_amount", 60);
        netBanking.put("min_transaction_amount", 15000);
        netBanking.put("fee_type", "flat");
        netBanking.put("enabled", false);
        
        collectMethods.put("net_banking", netBanking);

        Map<String, Object> debitCard = new LinkedHashMap<>();
        debitCard.put("fee_collect_method", "deduct_from_collects_and_payouts");
        debitCard.put("fee_amount", 1);
        debitCard.put("fee_type", "flat");
        debitCard.put("enabled", false);
        
        collectMethods.put("debit_card", debitCard);

        Map<String, Object> upi = new LinkedHashMap<>();
        upi.put("fee_collect_method", "deduct_from_collects_and_payouts");
        upi.put("fee_amount", 0.2);
        upi.put("fee_type", "percentage");
        upi.put("enabled", false);
        
        collectMethods.put("upi", upi);

        config.put("collect_methods", collectMethods);

        Map<String, Object> escrow = new LinkedHashMap<>();
        escrow.put("opening_fee_amount", 0);
        
        config.put("escrow", escrow);

        Map<String, Object> services = new LinkedHashMap<>();
        
        Map<String, Object> kycAadhar = new LinkedHashMap<>();
        kycAadhar.put("fee_amount", 10);
        kycAadhar.put("enabled", true);
        
        services.put("kyc_aadhar", kycAadhar);

        Map<String, Object> kycPan = new LinkedHashMap<>();
        kycPan.put("fee_amount", 10);
        kycPan.put("enabled", true);
        
        services.put("kyc_pan", kycPan);

        Map<String, Object> eNachRegistration = new LinkedHashMap<>();
        eNachRegistration.put("fee_amount", 0);
        eNachRegistration.put("enabled", false);
        
        services.put("eNach_registration", eNachRegistration);

        Map<String, Object> gstVerification = new LinkedHashMap<>();
        gstVerification.put("fee_amount", 0);
        gstVerification.put("enabled", false);
        
        services.put("gst_verification", gstVerification);

        Map<String, Object> vpaVerification = new LinkedHashMap<>();
        vpaVerification.put("fee_amount", 10);
        vpaVerification.put("enabled", true);
        
        services.put("vpa_verification", vpaVerification);

        Map<String, Object> bankAccountVerification = new LinkedHashMap<>();
        bankAccountVerification.put("fee_amount", 10);
        bankAccountVerification.put("enabled", true);
        
        services.put("bank_account_verification", bankAccountVerification);

        Map<String, Object> panVerification = new LinkedHashMap<>();
        panVerification.put("fee_amount", 10);
        panVerification.put("enabled", true);
        
        services.put("pan_verification", panVerification);

        config.put("services", services);

        Map<String, Object> payoutMethods = new LinkedHashMap<>();
        
        Map<String, Object> imps = new LinkedHashMap<>();
        imps.put("fee_collect_method", "collect_fees_from_escrow_separately");
        imps.put("fee_amount", 0);
        
        JSONArray impsTiers=new JSONArray();
        
        Map<String, Object> impsTier1 = new LinkedHashMap<>();
        impsTier1.put("fee_amount", 2.6);
        impsTier1.put("less_then_or_equal_to", 999);
        impsTier1.put("fee_type", "flat");
        impsTiers.add(impsTier1);
        
        Map<String, Object> impsTier2 = new LinkedHashMap<>();
        impsTier2.put("fee_amount", 3.6);
        impsTier2.put("less_then_or_equal_to", 25000);
        impsTier2.put("fee_type", "flat");
        impsTiers.add(impsTier2);
        
        Map<String, Object> impsTier3 = new LinkedHashMap<>();
        impsTier3.put("fee_amount", 7.5);
        impsTier3.put("less_then_or_equal_to", "max");
        impsTier3.put("fee_type", "flat");
        impsTiers.add(impsTier3);
        
        imps.put("tiers", impsTiers);
        imps.put("fee_type", "tiered");
        imps.put("enabled", true);
        payoutMethods.put("imps", imps);

        Map<String, Object> neft = new LinkedHashMap<>();
        neft.put("fee_collect_method", "collect_fees_from_escrow_separately");
        neft.put("fee_amount", 3);
        neft.put("fee_type", "flat");
        neft.put("enabled", false);
        payoutMethods.put("neft", neft);

        Map<String, Object> rtgs = new LinkedHashMap<>();
        rtgs.put("fee_collect_method", "collect_fees_from_escrow_separately");
        rtgs.put("fee_amount", 3);
        rtgs.put("fee_type", "flat");
        rtgs.put("enabled", true);
        payoutMethods.put("rtgs", rtgs);

        Map<String, Object> upiPayout = new LinkedHashMap<>();
        upiPayout.put("fee_collect_method", "collect_fees_from_escrow_separately");
        upiPayout.put("fee_amount", 0);
        
        JSONArray upiTiers=new JSONArray();
        
        Map<String, Object> upiTier1 = new LinkedHashMap<>();
        upiTier1.put("fee_amount", 2.6);
        upiTier1.put("less_then_or_equal_to", 999);
        upiTier1.put("fee_type", "flat");
        upiTiers.add(upiTier1);
        
        Map<String, Object> upiTier2 = new LinkedHashMap<>();
        upiTier2.put("fee_amount", 3.6);
        upiTier2.put("less_then_or_equal_to", 25000);
        upiTier2.put("fee_type", "flat");
        upiTiers.add(upiTier2);
        
        Map<String, Object> upiTier3 = new LinkedHashMap<>();
        upiTier3.put("fee_amount", 7.5);
        upiTier3.put("less_then_or_equal_to", "max");
        upiTier3.put("fee_type", "flat");
        upiTiers.add(upiTier3);
        
        upiPayout.put("tiers", upiTiers);
        upiPayout.put("fee_type", "tiered");
        upiPayout.put("enabled", true);
        payoutMethods.put("upi", upiPayout);

        config.put("payout_methods", payoutMethods);
        config.put("sandboxPubCert",publicCrt);
        config.put("payout_restricted_to_whitelisted_accounts", false);

        apiClientData.put("config", config);
        apiClientData.put("env","test");
        apiClientData.put("apikey", apikey);
        
        return apiClientData;

	}

}
