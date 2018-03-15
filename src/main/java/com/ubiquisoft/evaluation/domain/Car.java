package com.ubiquisoft.evaluation.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {

	private String year;
	private String make;
	private String model;

	private List<Part> parts;

	public Map<PartType, Integer> getMissingPartsMap() {
		/*
		 * Return map of the part types missing.
		 *
		 * Each car requires one of each of the following types:
		 *      ENGINE, ELECTRICAL, FUEL_FILTER, OIL_FILTER
		 * and four of the type: TIRE
		 *
		 * Example: a car only missing three of the four tires should return a map like this:
		 *
		 *      {
		 *          "TIRE": 3
		 *      }
		 */

		 Map<PartType, Integer> missingParts = new HashMap<PartType, Integer>();

		 boolean engine = false;
		 boolean electrical = false;
		 boolean fuelFilter = false;
		 boolean oilFilter = false;
		 int tires = 0;

		 // Check for required components
		 // I feel there is a much more efficient way to do this
		 for (Part part : parts) {
			 engine = (part.getType() == PartType.ENGINE) ? true : engine;
			 electrical = (part.getType() == PartType.ELECTRICAL) ? true : electrical;
			 fuelFilter = (part.getType() == PartType.FUEL_FILTER) ? true : fuelFilter;
			 oilFilter = (part.getType() == PartType.OIL_FILTER) ? true : oilFilter;
			 if (part.getType() == PartType.TIRE) {
			 	tires++;
			 }
		 }

		 // Require 4 tires
		 int tiresRequired = 4;
		 int tiresMissing = tiresRequired - tires;

		 // Create map of missing parts (require 1 each besides tires)
		 if (!engine) missingParts.put(PartType.ENGINE, 1);
		 if (!electrical) missingParts.put(PartType.ELECTRICAL, 1);
		 if (!fuelFilter) missingParts.put(PartType.FUEL_FILTER, 1);
		 if (!oilFilter) missingParts.put(PartType.OIL_FILTER, 1);
		 if (tiresMissing > 0) missingParts.put(PartType.TIRE, tiresMissing);

		return missingParts;
	}

	@Override
	public String toString() {
		return "Car{" +
				       "year='" + year + '\'' +
				       ", make='" + make + '\'' +
				       ", model='" + model + '\'' +
				       ", parts=" + parts +
				       '}';
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters *///region
	/* --------------------------------------------------------------------------------------------------------------- */

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters End *///endregion
	/* --------------------------------------------------------------------------------------------------------------- */

}
