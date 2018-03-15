package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.Map;

public class CarDiagnosticEngine {

	public void executeDiagnostics(Car car) {
		System.out.println("Executing Diagnostics");
		/*
		 * Implement basic diagnostics and print results to console.
		 *
		 * The purpose of this method is to find any problems with a car's data or parts.
		 *
		 * Diagnostic Steps:
		 *      First   - Validate the 3 data fields are present, if one or more are
		 *                then print the missing fields to the console
		 *                in a similar manner to how the provided methods do.
		 *
		 */
		 boolean baseConfigMissing = false;
         if (car.getMake() == null) {
         	System.out.print("Car is missing Make");
	        baseConfigMissing = true;
         }
		 if (car.getModel() == null) {
			System.out.print("Car is missing Model");
			baseConfigMissing = true;
		 }
		 if (car.getYear() == null) {
			System.out.print("Car is missing Year");
			baseConfigMissing = true;
		 }

		// Exit diagnosis early
		if (baseConfigMissing) return;

		 /*
		 *      Second  - Validate that no parts are missing using the 'getMissingPartsMap' method in the Car class,
		 *                if one or more are then run each missing part and its count through the provided missing part method.
		 */
		 boolean anyMissing = false;
		 Map<PartType, Integer> missingPartsMap = car.getMissingPartsMap();
		 if (missingPartsMap != null && missingPartsMap.size() > 0) {
			for (Map.Entry<PartType, Integer> missingPart : missingPartsMap.entrySet()) {
				printMissingPart(missingPart.getKey(), missingPart.getValue());
				anyMissing = true;
			}
		 }
		 // Exit diagnosis early
		 if (anyMissing) return;

		 /*      Third   - Validate that all parts are in working condition, if any are not
		 *                then run each non-working part through the provided damaged part method.
		 */
		 boolean allWorking = true;
		for (Part part : car.getParts()) {
			if (part.getCondition() != null && !part.isInWorkingCondition()) {
				allWorking = false;
				printDamagedPart(part.getType(), part.getCondition());
				break;
			}
		}

		if (allWorking) {
			System.out.println("All parts found to be working");
		}


		 /*      Fourth  - If validation succeeds for the previous steps then print something to the console informing the user as such.
		 * A damaged part is one that has any condition other than NEW, GOOD, or WORN.
		 *
		 * Important:
		 *      If any validation fails, complete whatever step you are actively one and end diagnostics early.
		 *
		 * Treat the console as information being read by a user of this application. Attempts should be made to ensure
		 * console output is as least as informative as the provided methods.
		 */


	}

	private void printMissingPart(PartType partType, Integer count) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (count == null || count <= 0) throw new IllegalArgumentException("Count must be greater than 0");

		System.out.println(String.format("Missing Part(s) Detected: %s - Count: %s", partType, count));
	}

	private void printDamagedPart(PartType partType, ConditionType condition) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

		System.out.println(String.format("Damaged Part Detected: %s - Condition: %s", partType, condition));
	}

	public static void diagnoseCar(String filename)  throws JAXBException {

		System.out.println("Diagnosing car - " + filename);
		// Load classpath resource
		InputStream xml = ClassLoader.getSystemResourceAsStream(filename);

		// Verify resource was loaded properly
		if (xml == null) {
			System.err.println("An error occurred attempting to load SampleCar.xml");
			System.exit(1);
		}

		// Build JAXBContext for converting XML into an Object
		JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Car car = (Car) unmarshaller.unmarshal(xml);

		// Build new Diagnostics Engine and execute on deserialized car object.

		CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();

		diagnosticEngine.executeDiagnostics(car);
	}


	public static void main(String[] args) {

		try {
			diagnoseCar("AllGoodCar.xml");
			diagnoseCar("MissingPartsCar.xml");
			diagnoseCar("SampleCar.xml");
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

}
