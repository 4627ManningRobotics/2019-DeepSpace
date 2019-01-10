package frc.robot;
/**
 * Utilities is designed to be a class for generic methods used across the 
 * various commands. Not all methods will be used each year.
 */
public class Utilities {
	
	public static double constrain(double value, double min, double max) {
		if(value < min) {
			return min;
		}else if(value > max) {
			return max;
		}
		return value;
	}
	
	public static double constrain(double value, double max) {
		if(value < 0) {
			return 0;
		}else if(value > max) {
			return max;
		}
		return value;
	}
	
	public static double withinValue(double value, double min, double max) {
		if(value < max && value > min) {
			return value;
		}else {
			return 0;
		}
	}
	
	public static double notWithinValue(double value, double min, double max) {
		if(value < max && value > min) {
			return 0;
		}else {
			return value;
		}
	}
	
	public static boolean within(double value, double min, double max) {
		return (value < max && value > min);
	}
	
	public static boolean notWithin(double value, double min, double max) {
		return (value > max || value < min);
	}
	
	public static double scale(double value, double scalar) {
		return value * scalar;
	}

	public static double AddSub(double valueA, double valueB) { // add B to A if A is positive, subtract B from A if a is negative
		if(valueA >= 0) {
			return valueA + valueB;
		}else{
			return valueA - valueB;
		}
	}
}