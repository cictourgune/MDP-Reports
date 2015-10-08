package org.tourgune.mdp.reports.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.tourgune.mdp.reports.db.Database;
import org.tourgune.mdp.reports.db.TablesDB;
import org.tourgune.mdp.reports.db.TablesDB_new;
import org.tourgune.mdp.reports.db.TablesDB_old;
import org.tourgune.mdp.reports.mail.Mail;
import org.tourgune.mdp.reports.utils.Configuration;
import org.tourgune.mdp.reports.utils.Constants;

public class Core {
	
	static Configuration configClass = Configuration.getInstance();
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void summary(Database db, String env) throws Exception {
		
		String output = "<h2>RESUMEN</h2>" 
				+ "<table border='1'>"  
					+ "<tr>" 
						+ "<th>gm_country</th>" + "<th>accommodation_type</th>" + "<th>id_booking_date</th>" + "<th>alojamientos</th>" + "<th>correctos + pendientes</th>" 
						+ "<th>thresholds</th>" + "<th>fallidos</th>" + "<th>HDE_ND</th>" + "<th>pendientes</th>" + "<th>TOTALES</th>"
					+ "</tr>";
		int sumAlojamientos = 0;
		int sumCorrectosPendientes = 0;
		int sumThresholds = 0;
		int sumFallidos = 0;
		int sumHDE_ND = 0;
		int sumPendientes = 0;
		int sumTotales = 0;
		
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		System.out.println("[MDP-" + env + "] ReportSummary: Summary diario INICIADO");
		
		TablesDB tablesDB = null;
		if (Constants.oldPricing.equals(env)) {
			tablesDB = new TablesDB_old();
		}
		if (Constants.newPricing.equals(env)) {
			tablesDB = new TablesDB_new();
		}
		
		Connection con;
		try {
			con = db.connect();
			
			if (con != null) {
				// Consulta raw para lanzarla en MYSQL. Actualizar si se modifica la de abajo!!!
//				SELECT da.gm_country, dat.name, ftpp.id_booking_date, COUNT(DISTINCT(ftpp.id_accommodation)) alojamientos,
//		        	COUNT(CASE WHEN (ftpp.price_flag = 1 OR ftpp.price_flag = 0) THEN 1 ELSE NULL END) correctos_y_pendientes,
//					COUNT(CASE WHEN ftpp.price_flag = 2 THEN 1 ELSE NULL END) thresholds, 
//		        	COUNT(CASE WHEN ftpp.price_flag = -1 THEN 1 ELSE NULL END) fallidos,
//		        	COUNT(CASE WHEN ftpp.price_flag = -2 THEN 1 ELSE NULL END) HDE_ND,
//					COUNT(CASE WHEN ftpp.price_flag = 0 THEN 1 ELSE NULL END) pendientes,
//		        	COUNT(*) AS TOTALES
//		        FROM ft_product_price ftpp JOIN d_accommodation da ON ftpp.id_accommodation = da.id_accommodation 
//					JOIN d_accommodation_type dat ON dat.id_accommodation_type = da.id_accommodation_type
//		        WHERE ftpp.id_booking_date = CURDATE()
//		        GROUP BY da.gm_country, dat.name, ftpp.id_booking_date;
				sql	.append(" SELECT " + tablesDB.getDA_GM_COUNTRY() + ", " + tablesDB.getDAT_NAME() + ", " + tablesDB.getFTPP_ID_BOOKING_DATE() + ", " 
						+ " COUNT(DISTINCT(" + tablesDB.getFTPP_ID_ACCOMMODATION() + ")) alojamientos, "
						// Se incluyen con los correctos los pendientes porque antes los pendientes se consideraban precios normales
						// por lo que si no los incluímos da la sensación que hay menos registros correctos.
						+ " COUNT(CASE WHEN (" + tablesDB.getFTPP_PRICE_FLAG() + " = 1 OR  " + tablesDB.getFTPP_PRICE_FLAG() + " = 0) THEN 1 ELSE NULL END) correctos_y_pendientes, "
						+ " COUNT(CASE WHEN " + tablesDB.getFTPP_PRICE_FLAG() + " = 2 THEN 1 ELSE NULL END) thresholds, " 
						+ " COUNT(CASE WHEN " + tablesDB.getFTPP_PRICE_FLAG() + " = -1 THEN 1 ELSE NULL END) fallidos, "
						+ " COUNT(CASE WHEN " + tablesDB.getFTPP_PRICE_FLAG() + " = -2 THEN 1 ELSE NULL END) HDE_ND, "
						+ " COUNT(CASE WHEN " + tablesDB.getFTPP_PRICE_FLAG() + " = 0 THEN 1 ELSE NULL END) pendientes, "
						+ " COUNT(*) TOTALES ")
					.append(" FROM " + tablesDB.getTABLE_FT_PRODUCT_PRICE() + " JOIN " + tablesDB.getTABLE_D_ACCOMMODATION() 
							+ " ON " + tablesDB.getFTPP_ID_ACCOMMODATION() + " = " + tablesDB.getDA_ID_ACCOMMODATION()
							+ " JOIN " + tablesDB.getTABLE_D_ACCOMMODATION_TYPE() 
								+ " ON " + tablesDB.getDAT_ID_ACCOMMODATION_TYPE() + " = " + tablesDB.getDA_ID_ACCOMMODATION_TYPE())
					.append(" WHERE " + tablesDB.getFTPP_ID_BOOKING_DATE() + " = CURDATE() ")
					.append(" GROUP BY " + tablesDB.getDA_GM_COUNTRY() + ", " + tablesDB.getDAT_NAME() + ", " + tablesDB.getFTPP_ID_BOOKING_DATE());
				
				ps = con.prepareStatement(sql.toString());
				rs = ps.executeQuery();
				while (rs.next()) {
					String gm_country = rs.getString(tablesDB.getDA_GM_COUNTRY());
					String id_accommodation_name = rs.getString(tablesDB.getDAT_NAME());
					Date id_booking_date = rs.getDate(tablesDB.getFTPP_ID_BOOKING_DATE());
					int alojamientos = rs.getInt("alojamientos");
					int correctosPendientes = rs.getInt("correctos_y_pendientes");
					int thresholds = rs.getInt("thresholds");
					int fallidos = rs.getInt("fallidos");
					int HDE_ND = rs.getInt("HDE_ND");
					int pendientes = rs.getInt("pendientes");
					int TOTALES = rs.getInt("TOTALES");
					output += "<tr>" 
								+ "<td>" + gm_country + "</td><td>" + id_accommodation_name + "</td><td>" + id_booking_date 
								+ "</td><td>" + alojamientos + "</td><td>" + correctosPendientes + "</td><td>" + thresholds 
								+ "</td>";
					if (fallidos > 0)
						output+= "<td style='font-weight: bold; color: red;'>" + fallidos + "</td>";
					else
						output+= "<td>" + fallidos + "</td>";
					output += "<td>" + HDE_ND + "</td><td>" + pendientes	
								+ "</td><td style='font-weight: bold;'>" + TOTALES + "</td>"
							+ "</tr>";
					sumAlojamientos += alojamientos;
					sumCorrectosPendientes += correctosPendientes;
					sumThresholds += thresholds;
					sumFallidos += fallidos;
					sumHDE_ND += HDE_ND;
					sumPendientes += pendientes;
					sumTotales += TOTALES;
				}
				output += "<tr style='font-weight: bold; color: blue;'>"
							+ "<td>TOTALES</td>" + "</td><td></td><td>" + sdf.format(new Date()) + "</td><td>" + sumAlojamientos + "</td><td>" 
							+ sumCorrectosPendientes + "</td><td>" + sumThresholds + "</td>";
				if (sumFallidos > 0) 
					output += "<td style='font-weight: bold; color: red;'>" + sumFallidos + "</td>";
				else
					output += "<td>" + sumFallidos + "</td>";
				output += "<td>" + sumHDE_ND + "</td><td>" + sumPendientes + "</td><td>" + sumTotales + "</td>";
				output += "</tr>";  
				output += "</table>";
				rs.close();
				ps.close();	
				
				// Staging AREA: Cantidad de registros generados para ayer (porque el proceso de SA lo lanzamos más tarde que este de summary)
				if (Constants.newPricing.equals(env)) {
					output += "<br /><h2>STAGING AREA</h2>";
					sql = new StringBuffer();
					sql	.append(" SELECT COUNT(*) registros_agregados")
						.append(" FROM " + tablesDB.getTABLE_FTPP_AGGREGATED())
						.append(" WHERE " + tablesDB.getFTPPA_ID_CHECKIN_DATE() + " = CURDATE()-1 ");
					
					ps = con.prepareStatement(sql.toString());
					rs = ps.executeQuery();
					while (rs.next()) {
						int count = rs.getInt("registros_agregados");
						output += "<br />Registros agregados AYER: " + count;
					}
				}
				
			} else {
				throw new Exception("Database connection NULL");
			}
			
			con = db.disconnect();
			
			Mail.sendMail("[MDP-" + env + "] Report - Summary " + sdf.format(new Date()), output, configClass.getProperty(Constants.MAIL_ENCODING));
		} catch (Exception e) {
			try {
				con = db.disconnect();
			} catch (Exception e2) {
				throw e2;
			}
			throw e;
		}
		System.out.println("[MDP-" + env + "] ReportSummary: Summary diario generado y notificado por email satisfactoriamente");
	}
	
	public static void alertGeoInfo(Database db, String env) throws Exception {
		StringBuffer outputGeneral = new StringBuffer(
				"<html>"
					+ "<head>"
						+ "<meta charset='utf-8'>"
					+ "</head>"
					+ "<body>"
					+ "</body>"
				+ "</html>");
	
		String outputSpain = "<h2>ESPAÑA</h2>"; 
		String outputRest = "<br /><h2>RESTO PAÍSES</h2>";; 
		
		Connection con;
		
		System.out.println("[MDP-" + env + "] ReportGeoInfo: Información GEO INICIADA");
		
		try {
			con = db.connect();
			
			if (con != null) {
				outputSpain += getGeo(env, true, con);
				outputRest += getGeo(env, false, con);
				
			} else {
				throw new Exception("Database connection NULL");
			}
			
			con = db.disconnect();
			outputGeneral.insert(outputGeneral.indexOf("<body>") + "<body>".length(), outputSpain + outputRest);
			
			Mail.sendMail("[MDP-" + env + "] Report - Geo Info " + sdf.format(new Date()), outputGeneral.toString(), configClass.getProperty(Constants.MAIL_ENCODING));
			
		} catch (Exception e) {
			try {
				con = db.disconnect();
			} catch (Exception e2) {
				throw e2;
			}
			throw e;
		}
		System.out.println("[MDP-" + env + "] ReportGeoInfo: Información GEO correcta notificada por email satisfactoriamente");
	}
	
	private static String getGeo(String env, boolean spain, Connection con) throws Exception {
		String output = "<table border='1'>"  
				+ "<tr>" 
					+ "<th>id_accommodation</th>" + "<th>name</th>" + "<th>gm_country</th>" + "<th>gm_aal1</th>" + "<th>gm_aal2</th>" 
					+ "<th>gm_aal3</th>" + "<th>gm_aal4</th>" + "<th>gm_locality</th>"
				+ "</tr>"; 
		
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;

		TablesDB tablesDB = null;
		if (Constants.oldPricing.equals(env)) {
			tablesDB = new TablesDB_old();
		}
		if (Constants.newPricing.equals(env)) {
			tablesDB = new TablesDB_new();
		}
		
		sql = new StringBuffer();

		// Se seleccionan las tuplas que no se encuentran detectadas en la tabla "d_ine"
		sql	.append(" SELECT DISTINCT(" + tablesDB.getDA_ID_ACCOMMODATION() + "), "+ tablesDB.getDA_NAME() + ", " + tablesDB.getDA_GM_COUNTRY()+ ", " + tablesDB.getDA_GM_AAL1()
					+ ", " + tablesDB.getDA_GM_AAL2() + ", " + tablesDB.getDA_GM_AAL3() + ", " + tablesDB.getDA_GM_AAL4() + ", " + tablesDB.getDA_GM_LOCALITY())
			.append(" FROM " +  tablesDB.getTABLE_D_ACCOMMODATION());
//		SELECT DISTINCT(da.id_accommodation), da.name, da.gm_country, da.gm_aal1, da.gm_aal2, da.gm_aal3, da.gm_aal4, da.gm_locality
//		FROM pricing_DB.d_accommodation da
		
		// Si es old, hay que filtrar sólo hoteles de id_canal = 1 y añadimos
		// tabla ft_scraping para mirar sólo hoteles que hayan generado precios hoy
		if (Constants.oldPricing.equals(env)) {
			sql	.append(" JOIN " + tablesDB.getTABLE_FT_PRODUCT_PRICE() 
					+ " ON " + tablesDB.getFTPP_ID_ACCOMMODATION() + " = " + tablesDB.getDA_ID_ACCOMMODATION())
				.append(" JOIN " + tablesDB.getTABLE_NM_ACCOMMODATION_CHANNEL()
					+ " ON " + tablesDB.getNMAC_ID_ACCOMMODATION() + " = " + tablesDB.getDA_ID_ACCOMMODATION());
		}
//			JOIN ft_scraping fts ON dh.id_hotel = fts.id_hotel
//			JOIN nm_id_hotel_canal nmhc ON dh.id_hotel = nmhc.id_hotel
		
		if (spain) {
//			LEFT JOIN pricing_DB.d_ine di ON (
//				BINARY di.aal1 = BINARY da.gm_aal1
//				AND BINARY di.aal2 = BINARY da.gm_aal2
//				AND BINARY di.locality = BINARY da.gm_locality
//			) 
//			WHERE di.aal1 IS NULL AND di.aal2 IS NULL AND di.locality IS NULL
//			AND COALESCE(da.gm_country, '') = 'Spain'
			sql	.append(" LEFT JOIN " + tablesDB.getTABLE_D_INE() + " ON (")
					.append(" BINARY " + tablesDB.getDI_AAL1() + " = BINARY " + tablesDB.getDA_GM_AAL1())
					.append(" AND BINARY " + tablesDB.getDI_AAL2() + " = BINARY " + tablesDB.getDA_GM_AAL2())
					.append(" AND BINARY " + tablesDB.getDI_LOCALITY() + " = BINARY " + tablesDB.getDA_GM_LOCALITY())
				.append(")")
				.append(" WHERE " + tablesDB.getDI_AAL1() + " IS NULL AND " + tablesDB.getDI_AAL2() + " IS NULL AND " + tablesDB.getDI_LOCALITY() + " IS NULL ")				
				.append(" AND COALESCE(" + tablesDB.getDA_GM_COUNTRY() + ", '') = 'Spain' ");	
		
		} else {
			
//			LEFT JOIN pricing_DB.d_geo dgeo ON (
//				BINARY COALESCE(dgeo.country, '') = BINARY COALESCE(da.gm_country, '')
//				AND BINARY COALESCE(dgeo.aal1, '') = BINARY COALESCE(da.gm_aal1, '')
//				AND BINARY COALESCE(dgeo.aal2, '') = BINARY COALESCE(da.gm_aal2, '')
//				AND BINARY COALESCE(dgeo.aal3, '') = BINARY COALESCE(da.gm_aal3, '')
//				AND BINARY COALESCE(dgeo.aal4, '') = BINARY COALESCE(da.gm_aal4	, '')
//			)
//			WHERE dgeo.country IS NULL AND dgeo.aal1 IS NULL AND dgeo.aal2 IS NULL AND dgeo.aal3 IS NULL AND dgeo.aal4 IS NULL
//			AND COALESCE(da.gm_country, '') <> 'Spain'
			sql	.append(" LEFT JOIN " + tablesDB.getTABLE_D_GEO() + " ON (")
					.append(" BINARY COALESCE(" + tablesDB.getDGEO_COUNTRY() + ", '') = BINARY COALESCE(" + tablesDB.getDA_GM_COUNTRY() + ", '')")
					.append(" AND BINARY COALESCE(" + tablesDB.getDGEO_AAL1() + ", '') = BINARY COALESCE(" + tablesDB.getDA_GM_AAL1() + ", '')")
					.append(" AND BINARY COALESCE(" + tablesDB.getDGEO_AAL2() + ", '') = BINARY COALESCE(" + tablesDB.getDA_GM_AAL2() + ", '')")
					.append(" AND BINARY COALESCE(" + tablesDB.getDGEO_AAL3() + ", '') = BINARY COALESCE(" + tablesDB.getDA_GM_AAL3() + ", '')")
					.append(" AND BINARY COALESCE(" + tablesDB.getDGEO_AAL4() + ", '') = BINARY COALESCE(" + tablesDB.getDA_GM_AAL4() + ", '')")
				.append(")")
				.append(" WHERE " + tablesDB.getDGEO_COUNTRY() + " IS NULL ")
					.append(" AND " + tablesDB.getDGEO_AAL1() + " IS NULL ")
					.append(" AND " + tablesDB.getDGEO_AAL2() + " IS NULL ")
					.append(" AND " + tablesDB.getDGEO_AAL3() + " IS NULL ")
					.append(" AND " + tablesDB.getDGEO_AAL4() + " IS NULL ")
				.append(" AND COALESCE(" + tablesDB.getDA_GM_COUNTRY() + ", '') <> 'Spain' ");	
		}

		// SI es OLD, filtramos sólo los id_canal = 1 y revisamos los hoteles SIN GEO que tengan precios hoy
		if (Constants.oldPricing.equals(env)) {
			sql	.append(" AND " + tablesDB.getDA_LATITUDE() + " IS NOT NULL AND " + tablesDB.getDA_LONGITUDE() + " IS NOT NULL ")
				.append(" AND " + tablesDB.getFTPP_ID_BOOKING_DATE() + " = CURDATE() ")
				.append(" AND " + tablesDB.getNMAC_ID_CHANNEL() + " = 1 ");
		}
		
		sql	.append(" ORDER BY "+ tablesDB.getDA_GM_COUNTRY() + ", " + tablesDB.getDA_GM_AAL1() + ", " + tablesDB.getDA_GM_AAL2() 
					+ ", " + tablesDB.getDA_GM_AAL3() + ", " + tablesDB.getDA_GM_AAL4() + ", " + tablesDB.getDA_GM_LOCALITY());
//			ORDER BY da.gm_country, da.gm_aal1, da.gm_aal2, da.gm_aal3, da.gm_aal4, da.gm_locality
		
		ps = con.prepareStatement(sql.toString());
		rs = ps.executeQuery();
		while (rs.next()) {
			int idAccommodation = rs.getInt(tablesDB.getDA_ID_ACCOMMODATION());
			String name = rs.getString(tablesDB.getDA_NAME());
			String gm_country = rs.getString(tablesDB.getDA_GM_COUNTRY());
			String gm_aal1 = rs.getString(tablesDB.getDA_GM_AAL1());
			String gm_aal2 = rs.getString(tablesDB.getDA_GM_AAL2());
			String gm_aal3 = rs.getString(tablesDB.getDA_GM_AAL3());
			String gm_aal4 = rs.getString(tablesDB.getDA_GM_AAL4());
			String gm_locality = rs.getString(tablesDB.getDA_GM_LOCALITY());
			output += "<tr>" 
						+ "<td>" + idAccommodation + "</td><td>" + name + "</td><td>" + gm_country + "</td><td>" 
						+ gm_aal1 + "</td><td>" + gm_aal2 + "</td><td>" + gm_aal3 + "</td><td>" + gm_aal4 + "</td><td>" 
						+ gm_locality + "</td>"
					+ "</tr>";
		}
		output += "</table>";
		rs.close();
		ps.close();	
		
		return output;
	}
	
	public static void alertCategories(Database db, String env) throws Exception {
		String output = "<table border='1'>"  
							+ "<tr>" +
								"<th>id_accommodation</th>" + "<th>name</th>" + "<th>category</th>" + "<th>gm_country</th>" 
								+ "<th>gm_aal1</th>" + "<th>gm_aal2</th>" + "<th>gm_aal3</th>" + "<th>gm_aal4</th>" + "<th>gm_locality</th>"
								+ "<th>lat</th>" + "<th>lon</th>"  
							+ "</tr>";
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		System.out.println("[MDP-" + env + "] ReportCategories: Categories INICIADO");
		
		TablesDB tablesDB = null;
		if (Constants.oldPricing.equals(env)) {
			tablesDB = new TablesDB_old();
		}
		if (Constants.newPricing.equals(env)) {
			tablesDB = new TablesDB_new();
		}
		Connection con;
		
		try {
			con = db.connect();
			
			if (con != null) {
				sql	.append(" SELECT " + tablesDB.getDA_ID_ACCOMMODATION() + ", " + tablesDB.getDA_NAME()  + ", " + tablesDB.getDA_CATEGORY() + ", "	
						+ tablesDB.getDA_GM_COUNTRY() + ", " + tablesDB.getDA_GM_AAL1() + ", " + tablesDB.getDA_GM_AAL2() + ", " + tablesDB.getDA_GM_AAL3()
						+ ", " + tablesDB.getDA_GM_AAL4() + ", " + tablesDB.getDA_GM_LOCALITY() + ", " + tablesDB.getDA_LATITUDE() + ", " + tablesDB.getDA_LONGITUDE())
					.append(" FROM " + tablesDB.getTABLE_D_ACCOMMODATION())
					.append(" WHERE " + tablesDB.getDA_ID_ACCOMMODATION_TYPE() + " = 204 ")
					.append(" AND ("+ tablesDB.getDA_CATEGORY() + " = -1 ")
					.append(" OR " + tablesDB.getDA_CATEGORY() + " = 0)")
					.append(" ORDER BY " + tablesDB.getDA_GM_COUNTRY() + ", " + tablesDB.getDA_GM_AAL1() + ", "
							+ tablesDB.getDA_GM_AAL2() + ", " + tablesDB.getDA_GM_AAL3() + ", "
							+ tablesDB.getDA_GM_AAL4() + ", " + tablesDB.getDA_GM_LOCALITY());
				
				// Consulta raw para lanzarla en MYSQL. Actualizar si se modifica la de arriba
//					SELECT da.id_accommodation, da.name, da.category, da.gm_country, da.gm_aal1, da.gm_aal2, da.gm_aal3, da.gm_aal4, da.gm_locality, da.latitude, da.longitude 
//					FROM pricing_DB.d_accommodation da 
//					WHERE da.id_accommodation_type = 204 
//					AND (da.category = -1 OR da.category = 0)
//					ORDER BY da.gm_country, da.gm_aal1, da.gm_aal2, da.gm_aal3, da.gm_aal4, da.gm_locality
				ps = con.prepareStatement(sql.toString());
				rs = ps.executeQuery();
				while (rs.next()) {
					int idAccommodation = rs.getInt(tablesDB.getDA_ID_ACCOMMODATION());
					String name = rs.getString(tablesDB.getDA_NAME());
					int category = rs.getInt(tablesDB.getDA_CATEGORY());
					String gm_country = rs.getString(tablesDB.getDA_GM_COUNTRY());
					String gm_aal1 = rs.getString(tablesDB.getDA_GM_AAL1());
					String gm_aal2 = rs.getString(tablesDB.getDA_GM_AAL2());
					String gm_aal3 = rs.getString(tablesDB.getDA_GM_AAL3());
					String gm_aal4 = rs.getString(tablesDB.getDA_GM_AAL4());
					String gm_locality = rs.getString(tablesDB.getDA_GM_LOCALITY());
					Float latitude = rs.getFloat(tablesDB.getDA_LATITUDE());
					Float longitude = rs.getFloat(tablesDB.getDA_LONGITUDE());
					output += "<tr>" 
								+ "<td>" + idAccommodation + "</td><td>" + name + "</td><td>" + category + "</td><td>" + gm_country 
								+ "</td><td>" + gm_aal1 + "</td><td>" + gm_aal2 + "</td><td>" + gm_aal3 + "</td><td>" + gm_aal4 
								+ "</td><td>" + gm_locality + "</td><td>" + latitude + "</td><td>" + longitude + "</td>"
							+ "</tr>";
				}
				output += "</table>";
				rs.close();
				ps.close();	
			} else {
				throw new Exception("Database connection NULL");
			}
			
			con = db.disconnect();
			
			Mail.sendMail("[MDP-" + env + "] Report - Categories " + sdf.format(new Date()), output, configClass.getProperty(Constants.MAIL_ENCODING));
		} catch (Exception e) {
			try {
				con = db.disconnect();
			} catch (Exception e2) {
				throw e2;
			}
			throw e;
		}
		System.out.println("[MDP-" + env + "] ReportCategories: Categorías comprobadas y notificados por email satisfactoriamente");
		
	}
	
	/**
	 * Sólo para NEW PRICING
	 */
	public static void alertRatings(Database db, String env, String accType) throws Exception {
		// Si no es entorno NEW no hay ratings
		if (!Constants.newPricing.equals(env)) {
			throw new Exception("ERROR: Ratings task is only valid for 'env = new'");
		} else {
			String output = "<table border='1'>"  
					+ "<tr>" +
						"<th>gm_country</th>" + "<th>acc_type</th>" + "<th>ratings</th>"
					+ "</tr>";
			StringBuffer sql = new StringBuffer();
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			System.out.println("[MDP-" + env + "] ReportRatings: Ratings INICIADO");
			
			TablesDB tablesDB = new TablesDB_new();
			
			Connection con;
			
			try {
				con = db.connect();
				
				if (con != null) {
			//		Consulta raw para lanzarla en MYSQL. Actualizar si se modifica la de arriba
			//		SELECT da.gm_country, dat.name, COUNT(*) 
			//		FROM pricing_DB.ft_accommodation_rating ftara JOIN pricing_DB.d_accommodation da ON ftara.id_accommodation = da.id_accommodation
			//			JOIN pricing_DB.d_accommodation_type dat ON dat.id_accommodation_type = da.id_accommodation_type						
			//		WHERE ftara.id_scraping_date = CURDATE()
			//		GROUP BY da.gm_country, dat.name;
					sql	.append(" SELECT " + tablesDB.getDA_GM_COUNTRY() + ", " + tablesDB.getDAT_NAME() + ", COUNT(*) ratings")
						.append(" FROM " + tablesDB.getTABLE_FT_ACCOMMODATION_RATING() + " JOIN " + tablesDB.getTABLE_D_ACCOMMODATION())
							.append(" ON " + tablesDB.getFTARA_ID_ACCOMMODATION()  + " = " + tablesDB.getDA_ID_ACCOMMODATION())
							.append(" JOIN " + tablesDB.getTABLE_D_ACCOMMODATION_TYPE() + " ON " + tablesDB.getDAT_ID_ACCOMMODATION_TYPE() + " = " + tablesDB.getDA_ID_ACCOMMODATION_TYPE())
						.append(" WHERE " + tablesDB.getFTARA_ID_SCRAPING_DATE() + " = CURDATE() ")
						.append(" GROUP BY " + tablesDB.getDA_GM_COUNTRY() + ", " + tablesDB.getDAT_NAME());
			
					ps = con.prepareStatement(sql.toString());
					rs = ps.executeQuery();
					while (rs.next()) {
						String gm_country = rs.getString(tablesDB.getDA_GM_COUNTRY());
						String accTypeName = rs.getString(tablesDB.getDAT_NAME());
						int ratings = rs.getInt("ratings");
						output += "<tr>" 
									+ "<td>" + gm_country + "</td><td>" + accTypeName + "</td><td>" + ratings + "</td>"
								+ "</tr>";
					}
					output += "</table>";
					rs.close();
					ps.close();	
				} else {
					throw new Exception("Database connection NULL");
				}
				
				con = db.disconnect();
				
				Mail.sendMail("[MDP-" + env + "] Report - Ratings " + sdf.format(new Date()), output, configClass.getProperty(Constants.MAIL_ENCODING));
			} catch (Exception e) {
				try {
					con = db.disconnect();
				} catch (Exception e2) {
					throw e2;
				}
				throw e;
			}
			System.out.println("[MDP-" + env + "] ReportRatings: Ratings recogidos y notificados por email satisfactoriamente");
		}
		
		
	}
	
	/**
	 * Sólo para NEW PRICING
	 */
	public static void alertReviews(Database db, String env, String accType) throws Exception {
		// Si no es entorno NEW no hay reviews
		if (!Constants.newPricing.equals(env)) {
			throw new Exception("ERROR: Ratings task is only valid for 'env = new'");
		} else {
			String output = "<table border='1'>"  
					+ "<tr>" +
						"<th>gm_country</th>" + "<th>acc_type</th>" + "<th>reviews</th>"
					+ "</tr>";
			StringBuffer sql = new StringBuffer();
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			System.out.println("[MDP-" + env + "] ReportReviews: Ratings INICIADO");
			
			TablesDB tablesDB = new TablesDB_new();
			
			Connection con;
			
			try {
				con = db.connect();
				
				if (con != null) {
			//		Consulta raw para lanzarla en MYSQL. Actualizar si se modifica la de arriba
			//		SELECT da.gm_country, dat.name, COUNT(*) 
			//		FROM pricing_DB.ft_accommodation_review ftare JOIN d_accommodation da ON ftare.id_accommodation = da.id_accommodation
			//			JOIN pricing_DB.d_accommodation_type dat ON dat.id_accommodation_type = da.id_accommodation_type
			//		WHERE ftare.id_scraping_date = CURDATE()
			//		GROUP BY da.gm_country, dat.name;
					sql	.append(" SELECT " + tablesDB.getDA_GM_COUNTRY() + ", " + tablesDB.getDAT_NAME() + ", COUNT(*) reviews")
						.append(" FROM " + tablesDB.getTABLE_FT_ACCOMMODATION_REVIEW() + " JOIN " + tablesDB.getTABLE_D_ACCOMMODATION())
							.append(" ON " + tablesDB.getFTARE_ID_ACCOMMODATION()  + " = " + tablesDB.getDA_ID_ACCOMMODATION())
							.append(" JOIN " + tablesDB.getTABLE_D_ACCOMMODATION_TYPE() + " ON " + tablesDB.getDAT_ID_ACCOMMODATION_TYPE() + " = " + tablesDB.getDA_ID_ACCOMMODATION_TYPE())
						.append(" WHERE " + tablesDB.getFTARE_ID_SCRAPING_DATE() + " = CURDATE() ")
						.append(" GROUP BY " + tablesDB.getDA_GM_COUNTRY() + ", " + tablesDB.getDAT_NAME());
			
					ps = con.prepareStatement(sql.toString());
					rs = ps.executeQuery();
					while (rs.next()) {
						String gm_country = rs.getString(tablesDB.getDA_GM_COUNTRY());
						String accTypeName = rs.getString(tablesDB.getDAT_NAME());
						int reviews = rs.getInt("reviews");
						output += "<tr>" 
									+ "<td>" + gm_country + "</td><td>" + accTypeName + "</td><td>" + reviews + "</td>"
								+ "</tr>";
					}
					output += "</table>";
					rs.close();
					ps.close();	
				} else {
					throw new Exception("Database connection NULL");
				}
				
				con = db.disconnect();
				
				Mail.sendMail("[MDP-" + env + "] Report - Reviews " + sdf.format(new Date()), output, configClass.getProperty(Constants.MAIL_ENCODING));
			} catch (Exception e) {
				try {
					con = db.disconnect();
				} catch (Exception e2) {
					throw e2;
				}
				throw e;
			}
			System.out.println("[MDP-" + env + "] ReportReviews: Reviews recogidos y notificados por email satisfactoriamente");
		}
		
		
	}
	
//	public static void alertThresholds(Database db, String env) throws Exception {
//		String output = "<table border='1'>"  
//							+ "<tr>" +
//								"<th>id_accommodation_channel</th>" + "<th>id_accommodation</th>" + "<th>name</th>" + "<th>category</th>" + "<th>gm_country</th>" 
//								+ "<th>id_booking_date</th>" + "<th>id_checkin_date</th>" + "<th>price</th>" + "<th>max_price</th>" + "<th>threshold</th>"
//							+ "</tr>";
//		StringBuffer sql = new StringBuffer();
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		
//		System.out.println("[MDP-" + env + "] ReportThresholds: Thresholds INICIADO");
//		
//		TablesDB tablesDB = null;
//		if (Constants.oldPricing.equals(env)) {
//			tablesDB = new TablesDB_old();
//		}
//		if (Constants.newPricing.equals(env)) {
//			tablesDB = new TablesDB_new();
//		}
//		Connection con;
//		
//		try {
//			con = db.connect();
//			
//			if (con != null) {
//				sql	.append(" SELECT " + tablesDB.getFTT_ID_ACCOMMODATION_CHANNEL() + ", "+ tablesDB.getDA_ID_ACCOMMODATION() + ", " + tablesDB.getDA_NAME()  
//						+ ", " + tablesDB.getDA_CATEGORY() + ", "	+ tablesDB.getDA_GM_COUNTRY()
//						+ ", " + tablesDB.getFTPP_ID_BOOKING_DATE() + ", " + tablesDB.getFTPP_ID_CHECKIN_DATE() + ", " + tablesDB.getFTPP_PRICE()
//						  + ", " + tablesDB.getFTT_MAX_PRICE() + ", " + tablesDB.getFTT_THRESHOLD())
//					.append(" FROM " + tablesDB.getTABLE_FT_PRODUCT_PRICE() + " JOIN " + tablesDB.getTABLE_D_ACCOMMODATION() 
//							+ " ON " + tablesDB.getFTPP_ID_ACCOMMODATION() + " = " + tablesDB.getDA_ID_ACCOMMODATION())
//					.append(" JOIN " + tablesDB.getTABLE_FT_THRESHOLDS() + " ON " + tablesDB.getDA_ID_ACCOMMODATION() + " = " + tablesDB.getFTT_ID_ACCOMMODATION())
//					.append(" WHERE " + tablesDB.getFTPP_PRICE_FLAG() + " = 0 ") // price_flag = 0 indica que hay que revisar a mano el precio
//					.append(" AND " + tablesDB.getFTPP_ID_BOOKING_DATE() + " = CURDATE() ");
//				
//				// Consulta raw para lanzarla en MYSQL. Actualizar si se modifica la de arriba
////					SELECT ftt.id_accommodation_channel, da.id_accommodation, da.name, da.category, da.gm_country, ftpp.id_booking_date, ftpp.id_checkin_date, 
////						ftpp.price, ftt.max_price, ftt.threshold
////					FROM pricing_DB.ft_product_price ftpp JOIN pricing_DB.d_accommodation da ON ftpp.id_accommodation = da.id_accommodation
////						JOIN pricing_DB.ft_thresholds ftt ON da.id_accommodation = ftt.id_accommodation
////					WHERE ftpp.price_flag = 0  #price_flag = 0 indica que hay que revisar a mano el precio
////						AND ftpp.id_booking_date = CURDATE();
//				ps = con.prepareStatement(sql.toString());
//				rs = ps.executeQuery();
//				while (rs.next()) {
//					String idAccommodationChannel = rs.getString(tablesDB.getFTT_ID_ACCOMMODATION_CHANNEL());
//					int idAccommodation = rs.getInt(tablesDB.getDA_ID_ACCOMMODATION());
//					String name = rs.getString(tablesDB.getDA_NAME());
//					int category = rs.getInt(tablesDB.getDA_CATEGORY());
//					String gm_country = rs.getString(tablesDB.getDA_GM_COUNTRY());
//					Date id_booking_date = rs.getDate(tablesDB.getFTPP_ID_BOOKING_DATE());
//					Date id_checkin_date = rs.getDate(tablesDB.getFTPP_ID_CHECKIN_DATE());
//					float price = rs.getFloat(tablesDB.getFTPP_PRICE());
//					float max_price = rs.getFloat(tablesDB.getFTT_MAX_PRICE());
//					float threshold = rs.getFloat(tablesDB.getFTT_THRESHOLD());
//					output += "<tr>" 
//								+ "<td>" + idAccommodationChannel + "</td><td>" + idAccommodation + "</td><td>" + name + "</td><td>" 
//								+ category + "</td><td>" + gm_country + "</td><td>" + id_booking_date + "</td><td>" + id_checkin_date + "</td><td>" 
//								+ price + "</td><td>" + max_price + "</td><td>" + threshold + "</td>"
//							+ "</tr>";
//				}
//				output += "</table>";
//				rs.close();
//				ps.close();	
//			} else {
//				throw new Exception("Database connection NULL");
//			}
//			
//			con = db.disconnect();
//			
//			Mail.sendMail("[MDP-" + env + "] Report - Thresholds " + sdf.format(new Date()), output, configClass.getProperty(Constants.MAIL_ENCODING));
//		} catch (Exception e) {
//			try {
//				con = db.disconnect();
//			} catch (Exception e2) {
//				throw e2;
//			}
//			throw e;
//		}
//		System.out.println("[MDP-" + env + "] ReportThresholds: Thresholds obtenidos y notificados por email satisfactoriamente");
//		
//	}
}
