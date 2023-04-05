package Conversor;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TasasDeCambio {
	@JsonProperty("rates")
	private Map<String, Double> valores;

	public Map<String, Double> getValores() {
		return valores;
	}

	public void setValores(Map<String, Double> valores) {
		this.valores = valores;
	}
}
