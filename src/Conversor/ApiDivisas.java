package Conversor;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;



public class ApiDivisas {

	private static final String MONEDAS_PUNTOFINAL="https://openexchangerates.org/api/currencies.json";
	private static final String PUNTOFINAL_TASASDECAMBIOUSD = "https://openexchangerates.org/api/latest.json";
	private static final String APP_ID= "?app_id=c9ea60784fbf46f29798643c82851c9d";
	private static final String SOLICITUD_TASASDECAMBIOUSD = PUNTOFINAL_TASASDECAMBIOUSD + APP_ID;
	
	private static final Map<String, String> MONEDAS;
	private static final List<String> NOMBRES;
	private static final Map<String, Double> TASASDECAMBIOUSD;
	
	static {
		MONEDAS = recuperarMonedas();
		NOMBRES= obtenerNombresDeMoneda();
		TASASDECAMBIOUSD= recuperarTasasDeCambioUSD();
	}
	
	
	private static List<String> obtenerNombresDeMoneda(){
		return MONEDAS.keySet().stream().sorted().collect(Collectors.toList());
	}
	public static InputStream recuperarDatos(String URLDelPuntoFinal) {
		
		InputStream resultado = null;
		
		try {
			URL url = new URL(URLDelPuntoFinal);
			HttpURLConnection conexion = (HttpURLConnection)url.openConnection();
			conexion.setRequestMethod("GET");
			
			if(conexion.getResponseCode() !=200) throw new IOException("conexion fallida");
			resultado = conexion.getInputStream();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public static Map<String, String> recuperarMonedas() {
		Map<String, String> monedas = null;
		
		InputStream resultado=recuperarDatos(MONEDAS_PUNTOFINAL);
		ObjectMapper json = new ObjectMapper();
		
		try {
			
			monedas = json.readValue(resultado, new TypeReference<Map<String, String>>() {});
			monedas= monedas.entrySet().stream().collect(
					Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey)
					);
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return monedas;
	}
	public static Map<String, Double> recuperarTasasDeCambioUSD(){
		Map<String, Double> tasasDeCambioUSD = null;
		
		InputStream resultado = recuperarDatos(SOLICITUD_TASASDECAMBIOUSD);
		ObjectMapper json = new ObjectMapper();
		
		try {
			tasasDeCambioUSD = json.readValue(resultado, TasasDeCambio.class).getValores();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return tasasDeCambioUSD;
	}
	
	public static List<String> getNombres() {
		return NOMBRES;
		
	}
	public static Map<String, Double> getTasasdecambiousd() {
		TASASDECAMBIOUSD.get("MXN");
		TASASDECAMBIOUSD.get("COP");
		return TASASDECAMBIOUSD;
	}
	
	private static double recuperarTasaDeCambioUSD(String moneda) {
		String codigo= MONEDAS.get(moneda);
		return TASASDECAMBIOUSD.get(codigo);
	}
	
	public static double recuperarTasaDeCambio(String moneda1, String moneda2) {
	
	
	double tasaDeCambioUSD1= recuperarTasaDeCambioUSD(moneda1);
	double tasaDeCambioUSD2= recuperarTasaDeCambioUSD(moneda2);
	
	
	return tasaDeCambioUSD2/tasaDeCambioUSD1;
	}
	
}
