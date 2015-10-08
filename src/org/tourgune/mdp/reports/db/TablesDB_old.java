package org.tourgune.mdp.reports.db;


public class TablesDB_old extends TablesDB {
	
	@Override
	public String getTABLE_FT_PRODUCT_PRICE() {
		// TODO Auto-generated method stub
		return "ft_scraping fts";
	}
	@Override
	public String getFTPP_ID_ACCOMMODATION() {
		return "fts.id_hotel";
	}
	@Override
	public String getFTPP_ID_BOOKING_DATE() {
		// TODO Auto-generated method stub
		return "fts.id_fecha_gen";
	}
	@Override
	public String getFTPP_ID_CHECKIN_DATE() {
		return "fts.id_fecha_dato";
	}
	@Override
	public String getFTPP_PRICE() {
		// TODO Auto-generated method stub
		return "fts.precio_HDE";
	}
	public String getFTPP_PRICE_FLAG() {
		return "ftpp.price_flag";
	}
	@Override
	public String getTABLE_D_ACCOMMODATION() {
		// TODO Auto-generated method stub
		return "d_hotel dh";
	}
	@Override
	public String getDA_ID_ACCOMMODATION() {
		// TODO Auto-generated method stub
		return "dh.id_hotel";
	}
	public String getDA_NAME() {
		// TODO Auto-generated method stub
		return "dh.nombre";
	}
	@Override
	public String getDA_CATEGORY() {
		// TODO Auto-generated method stub
		return "dh.categoria";
	}
	@Override
	public String getDA_GM_COUNTRY() {
		// TODO Auto-generated method stub
		return "dh.gm_country";
	}
	@Override
	public String getDA_GM_AAL1() {
		return "dh.gm_aal1";
	}
	@Override
	public String getDA_GM_AAL2() {
		return "dh.gm_aal2";
	}
	@Override
	public String getDA_GM_AAL3() {
		return "dh.gm_aal3";
	}
	@Override
	public String getDA_GM_AAL4() {
		return "dh.gm_aal4";
	}
	@Override
	public String getDA_GM_LOCALITY() {
		return "dh.gm_locality";
	}
	@Override
	public String getDA_LATITUDE() {
		return "dh.latitud";
	}
	@Override
	public String getDA_LONGITUDE() {
		return "dh.longitud";
	}
	public String getTABLE_NM_ACCOMMODATION_CHANNEL() {
		return "nm_id_hotel_canal nmhc";
	}
	public String getNMAC_ID_ACCOMMODATION() {
		return "nmhc.id_hotel";
	}
	public String getNMAC_ID_ACCOMMODATION_CHANNEL() {
		return "nmhc.id_hotel_canal";
	}
	public String getNMAC_ID_CHANNEL() {
		return "nmhc.id_canal";
	}
	@Override
	public String getTABLE_FT_THRESHOLDS() {
		// TODO Auto-generated method stub
		return "ft_thresholds ftt";
	}
	public String getFTT_ID_ACCOMMODATION() {
		// TODO Auto-generated method stub
		return "ftt.id_hotel";
	}
	public String getFTT_ID_ACCOMMODATION_CHANNEL() {
		// TODO Auto-generated method stub
		return "ftt.hotel_canal_key";
	}
	@Override
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
	
}
