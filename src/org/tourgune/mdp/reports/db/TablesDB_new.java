package org.tourgune.mdp.reports.db;


public class TablesDB_new extends TablesDB {


	@Override
	public String getTABLE_FT_PRODUCT_PRICE() {
		// TODO Auto-generated method stub
		return "ft_product_price ftpp";
	}
	@Override
	public String getFTPP_ID_ACCOMMODATION() {
		return "ftpp.id_accommodation";
	}
	@Override
	public String getFTPP_ID_BOOKING_DATE() {
		// TODO Auto-generated method stub
		return "ftpp.id_booking_date";
	}
	@Override
	public String getFTPP_ID_CHECKIN_DATE() {
		// TODO Auto-generated method stub
		return "ftpp.id_checkin_date";
	}
	@Override
	public String getFTPP_PRICE() {
		// TODO Auto-generated method stub
		return "ftpp.price";
	}
	public String getFTPP_PRICE_FLAG() {
		return "ftpp.price_flag";
	}
	@Override
	public String getTABLE_D_ACCOMMODATION() {
		// TODO Auto-generated method stub
		return "d_accommodation da";
	}
	@Override
	public String getDA_ID_ACCOMMODATION() {
		// TODO Auto-generated method stub
		return "da.id_accommodation";
	}
	@Override
	public String getDA_NAME() {
		// TODO Auto-generated method stub
		return "da.name";
	}
	@Override
	public String getDA_CATEGORY() {
		// TODO Auto-generated method stub
		return "da.category";
	}
	@Override
	public String getDA_GM_COUNTRY() {
		// TODO Auto-generated method stub
		return "da.gm_country";
	}
	@Override
	public String getDA_GM_AAL1() {
		return "da.gm_aal1";
	}
	@Override
	public String getDA_GM_AAL2() {
		return "da.gm_aal2";
	}
	@Override
	public String getDA_GM_AAL3() {
		return "da.gm_aal3";
	}
	@Override
	public String getDA_GM_AAL4() {
		return "da.gm_aal4";
	}
	@Override
	public String getDA_GM_LOCALITY() {
		return "da.gm_locality";
	}
	@Override
	public String getDA_LATITUDE() {
		return "da.latitude";
	}
	public String getDA_ID_ACCOMMODATION_TYPE() {
		return "da.id_accommodation_type";
	}
	@Override
	public String getDA_LONGITUDE() {
		return "da.longitude";
	}
	public String getTABLE_D_ACCOMMODATION_TYPE() {
		return "d_accommodation_type dat";
	}
	public String getDAT_ID_ACCOMMODATION_TYPE() {
		return "dat.id_accommodation_type";
	}
	public String getDAT_NAME() {
		return "dat.name";
	}
	public String getTABLE_NM_ACCOMMODATION_CHANNEL() {
		return "nm_accommodation_channel nmac";
	}
	public String getNMAC_ID_ACCOMMODATION() {
		return "nmac.id_accommodation";
	}
	public String getNMAC_ID_ACCOMMODATION_CHANNEL() {
		return "nmac.id_accommodation_channel";
	}
	public String getNMAC_ID_CHANNEL() {
		return "nmac.id_channel";
	}
	public String getTABLE_FT_THRESHOLDS() {
		// TODO Auto-generated method stub
		return "ft_thresholds ftt";
	}
	public String getFTT_ID_ACCOMMODATION() {
		// TODO Auto-generated method stub
		return "ftt.id_accommodation";
	}
	public String getFTT_ID_ACCOMMODATION_CHANNEL() {
		// TODO Auto-generated method stub
		return "ftt.id_accommodation_channel";
	}
	public String getFTT_MAX_PRICE() {
		// TODO Auto-generated method stub
		return "ftt.max_price";
	}
	@Override
	public String getFTT_THRESHOLD() {
		// TODO Auto-generated method stub
		return "ftt.threshold";
	}
	@Override
	public String getTABLE_D_INE() {
		return "d_ine di";
	}
	@Override
	public String getDI_AAL1() {
		// TODO Auto-generated method stub
		return "di.aal1";
	}
	@Override
	public String getDI_AAL2() {
		// TODO Auto-generated method stub
		return "di.aal2";
	}
	@Override
	public String getDI_LOCALITY() {
		return "di.locality";
	}
	@Override
	public String getTABLE_D_GEO() {
		// TODO Auto-generated method stub
		return "d_geo dgeo";
	}
	@Override
	public String getDGEO_COUNTRY() {
		// TODO Auto-generated method stub
		return "dgeo.country";
	}
	@Override
	public String getDGEO_AAL1() {
		// TODO Auto-generated method stub
		return "dgeo.aal1";
	}
	@Override
	public String getDGEO_AAL2() {
		// TODO Auto-generated method stub
		return "dgeo.aal2";
	}
	@Override
	public String getDGEO_AAL3() {
		// TODO Auto-generated method stub
		return "dgeo.aal3";
	}
	@Override
	public String getDGEO_AAL4() {
		// TODO Auto-generated method stub
		return "dgeo.aal4";
	}
	@Override
	public String getDGEO_LOCALITY() {
		// TODO Auto-generated method stub
		return "dgeo.locality";
	}
	
	public String getTABLE_FTPP_AGGREGATED() {
		return "ftpp_aggregated ftppa";
	}
	public String getFTPPA_ID_CHECKIN_DATE() {
		return "ftppa.id_checkin_date";
	}
	public String getTABLE_FT_ACCOMMODATION_RATING() {
		return "ft_accommodation_rating ftara";
	}
	public String getFTARA_ID_ACCOMMODATION() {
		return "ftara.id_accommodation";
	}
	public String getFTARA_ID_SCRAPING_DATE() {
		return "ftara.id_scraping_date";
	}
	public String getTABLE_FT_ACCOMMODATION_REVIEW() {
		return "ft_accommodation_review ftare";
	}
	public String getFTARE_ID_ACCOMMODATION() {
		return "ftare.id_accommodation";
	}
	public String getFTARE_ID_SCRAPING_DATE() {
		return "ftare.id_scraping_date";
	}
}
