package com.example.springbootproject.model;

import java.util.List;

public class VehicleFeature {
	
	private List<String> Exterior;
	private List<String> Interior;
	
	public List<String> getExterior() {
		return Exterior;
	}
	public void setExterior(List<String> exterior) {
		Exterior = exterior;
	}
	public List<String> getInterior() {
		return Interior;
	}
	public void setInterior(List<String> interior) {
		Interior = interior;
	}

}
