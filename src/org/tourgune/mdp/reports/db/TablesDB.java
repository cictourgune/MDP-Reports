package org.tourgune.mdp.reports.db;

public class TablesDB {
	

	private String TABLE_FT_PRODUCT_PRICE 		= "ft_product_price ftpp";
	private String FTPP_ID_ACCOMMODATION 		= "ftpp.id_accommodation";
	private String FTPP_ID_BOOKING_DATE			= "ftpp.id_booking_date";
	private String FTPP_ID_CHECKIN_DATE			= "ftpp.id_checkin_date";
	private String FTPP_PRICE					= "ftpp.price";
	private String FTPP_PRICE_FLAG				= "ftpp.price_flag";
	
	private String TABLE_D_ACCOMMODATION 		= "d_accommodation da";
	private String DA_ID_ACCOMMODATION			= "da.id_accommodation";
	private String DA_NAME						= "da.name";
	private String DA_CATEGORY					= "da.category";
	private String DA_GM_COUNTRY				= "da.gm_country";
	private String DA_GM_AAL1					= "da.gm_aal1";
	private String DA_GM_AAL2					= "da.gm_aal2";
	private String DA_GM_AAL3					= "da.gm_aal3";
	private String DA_GM_AAL4					= "da.gm_aal4";
	private String DA_GM_LOCALITY				= "da.gm_locality";
	private String DA_LATITUDE					= "da.latitude";
	private String DA_LONGITUDE					= "da.longitude";
	private String DA_ID_ACCOMMODATION_TYPE		= "da.id_accommodation_type";
	
	private String TABLE_D_ACCOMMODATION_TYPE	= "d_accommodation_type dat";
	private String DAT_ID_ACCOMMODATION_TYPE	= "dat.id_accommodation_type";
	private String DAT_NAME						= "dat.name";
	
	private String TABLE_NM_ACCOMMODATION_CHANNEL	= "nm_accommodation_channel nmac";
	private String NMAC_ID_ACCOMMODATION			= "nmac.id_accommodation";
	private String NMAC_ID_ACCOMMODATION_CHANNEL	= "nmac.id_accommodation_channel";
	private String NMAC_ID_CHANNEL					= "nmac.id_channel";
	
	private String TABLE_FT_THRESHOLDS 			= "ft_thresholds ftt";
	private String FTT_ID_ACCOMMODATION_CHANNEL	= "ftt.id_accommodation_channel";
	private String FTT_ID_ACCOMMODATION			= "ftt.id_accommodation";
	private String FTT_MAX_PRICE				= "ftt.max_price";
	private String FTT_THRESHOLD				= "ftt.threshold";
	
	private String TABLE_D_INE					= "d_ine di";
	private String DI_AAL1						= "di.aal1";
	private String DI_AAL2						= "di.aal2";
	private String DI_LOCALITY					= "di.locality";
	
	private String TABLE_D_GEO					= "d_geo dgeo";
	private String DGEO_COUNTRY					= "dgeo.country";
	private String DGEO_AAL1					= "dgeo.aal1";
	private String DGEO_AAL2					= "dgeo.aal2";
	private String DGEO_AAL3					= "dgeo.aal3";
	private String DGEO_AAL4					= "dgeo.aal4";
	private String DGEO_LOCALITY				= "dgeo.locality";
	
	private String TABLE_FTPP_AGGREGATED		= "ftpp.aggregated ftppa";
	private String FTPPA_ID_CHECKIN_DATE		= "ftppa.id_checkin_date";
	
	private String TABLE_FT_ACCOMMODATION_RATING	= "ft_accommodation_rating ftara";
	private String FTARA_ID_ACCOMMODATION			= "ftara.id_accommodation";
	private String FTARA_ID_SCRAPING_DATE			= "ftara.id_scraping_date";
	
	private String TABLE_FT_ACCOMMODATION_REVIEW	= "ft_accommodation_review ftare";
	private String FTARE_ID_ACCOMMODATION			= "ftare.id_accommodation";
	private String FTARE_ID_SCRAPING_DATE			= "ftare.id_scraping_date";
	
	public String getTABLE_FT_PRODUCT_PRICE() {
		return TABLE_FT_PRODUCT_PRICE;
	}
	public String getFTPP_ID_ACCOMMODATION() {
		return FTPP_ID_ACCOMMODATION;
	}
	public String getFTPP_ID_BOOKING_DATE() {
		return FTPP_ID_BOOKING_DATE;
	}
	public String getFTPP_ID_CHECKIN_DATE() {
		return FTPP_ID_CHECKIN_DATE;
	}
	public String getFTPP_PRICE() {
		return FTPP_PRICE;
	}
	public String getFTPP_PRICE_FLAG() {
		return FTPP_PRICE_FLAG;
	}
	public String getTABLE_D_ACCOMMODATION() {
		return TABLE_D_ACCOMMODATION;
	}
	public String getDA_ID_ACCOMMODATION() {
		return DA_ID_ACCOMMODATION;
	}
	public String getDA_NAME() {
		return DA_NAME;
	}
	public String getDA_CATEGORY() {
		return DA_CATEGORY;
	}
	public String getDA_GM_COUNTRY() {
		return DA_GM_COUNTRY;
	}
	public String getDA_GM_AAL1() {
		return DA_GM_AAL1;
	}
	public String getDA_GM_AAL2() {
		return DA_GM_AAL2;
	}
	public String getDA_GM_AAL3() {
		return DA_GM_AAL3;
	}
	public String getDA_GM_AAL4() {
		return DA_GM_AAL4;
	}
	public String getDA_GM_LOCALITY() {
		return DA_GM_LOCALITY;
	}
	public String getDA_LATITUDE() {
		return DA_LATITUDE;
	}
	public String getDA_LONGITUDE() {
		return DA_LONGITUDE;
	}
	public String getDA_ID_ACCOMMODATION_TYPE() {
		return DA_ID_ACCOMMODATION_TYPE;
	}
	public String getTABLE_D_ACCOMMODATION_TYPE() {
		return TABLE_D_ACCOMMODATION_TYPE;
	}
	public String getDAT_ID_ACCOMMODATION_TYPE() {
		return DAT_ID_ACCOMMODATION_TYPE;
	}
	public String getDAT_NAME() {
		return DAT_NAME;
	}
	public String getTABLE_NM_ACCOMMODATION_CHANNEL() {
		return TABLE_NM_ACCOMMODATION_CHANNEL;
	}
	public String getNMAC_ID_ACCOMMODATION() {
		return NMAC_ID_ACCOMMODATION;
	}
	public String getNMAC_ID_ACCOMMODATION_CHANNEL() {
		return NMAC_ID_ACCOMMODATION_CHANNEL;
	}
	public String getNMAC_ID_CHANNEL() {
		return NMAC_ID_CHANNEL;
	}
	public String getTABLE_FT_THRESHOLDS() {
		return TABLE_FT_THRESHOLDS;
	}
	public String getFTT_ID_ACCOMMODATION_CHANNEL() {
		return FTT_ID_ACCOMMODATION_CHANNEL;
	}
	public String getFTT_ID_ACCOMMODATION() {
		return FTT_ID_ACCOMMODATION;
	}
	public String getFTT_MAX_PRICE() {
		return FTT_MAX_PRICE;
	}
	public String getFTT_THRESHOLD() {
		return FTT_THRESHOLD;
	}	
	public String getTABLE_D_INE() {
		return TABLE_D_INE;
	}
	public String getDI_AAL1() {
		return DI_AAL1;
	}
	public String getDI_AAL2() {
		return DI_AAL2;
	}
	public String getDI_LOCALITY() {
		return DI_LOCALITY;
	}
	public String getTABLE_D_GEO() {
		return TABLE_D_GEO;
	}
	public String getDGEO_COUNTRY() {
		return DGEO_COUNTRY;
	}
	public String getDGEO_AAL1() {
		return DGEO_AAL1;
	}
	public String getDGEO_AAL2() {
		return DGEO_AAL2;
	}
	public String getDGEO_AAL3() {
		return DGEO_AAL3;
	}
	public String getDGEO_AAL4() {
		return DGEO_AAL4;
	}
	public String getDGEO_LOCALITY() {
		return DGEO_LOCALITY;
	}
	public String getTABLE_FTPP_AGGREGATED() {
		return TABLE_FTPP_AGGREGATED;
	}
	public String getFTPPA_ID_CHECKIN_DATE() {
		return FTPPA_ID_CHECKIN_DATE;
	}
	public String getTABLE_FT_ACCOMMODATION_RATING() {
		return TABLE_FT_ACCOMMODATION_RATING;
	}
	public String getFTARA_ID_ACCOMMODATION() {
		return FTARA_ID_ACCOMMODATION;
	}
	public String getFTARA_ID_SCRAPING_DATE() {
		return FTARA_ID_SCRAPING_DATE;
	}
	public String getTABLE_FT_ACCOMMODATION_REVIEW() {
		return TABLE_FT_ACCOMMODATION_REVIEW;
	}
	public String getFTARE_ID_ACCOMMODATION() {
		return FTARE_ID_ACCOMMODATION;
	}
	public String getFTARE_ID_SCRAPING_DATE() {
		return FTARE_ID_SCRAPING_DATE;
	}
}
