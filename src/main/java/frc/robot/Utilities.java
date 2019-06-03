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

	public static double addSub(double valueA, double valueB) { // add B to A if A is positive, subtract B from A if a is negative
		if(valueA >= 0) {
			return valueA + valueB;
		}else{
			return valueA - valueB;
		}
	}

	public static double avg(double[] valArr){
		double sum = 0;
		for(double i: valArr){
			sum += i; 
		}
		return sum / ((double) valArr.length);
	}

	public static void push(double val, double[] valArr){
		for(int i = valArr.length - 1; i > 0; i--){
			valArr[i] = valArr[i - 1];
		}
		valArr[0] = val;
	}

	public static double metersToInches(double meters){
		return meters * 39.3701;
	}

	public static double inchesToMeters(double inches){
		return inches * 0.0254;
	}

	public static class Counter{
		
		private final int limit;
		private int counts = 0;
		private Func function;

		public Counter(int limit, Func func){
			this.limit = limit;
			this.function = func;
		}

		public int getLimit(){
			return this.limit;
		}

		public void count(){
			if(!this.isDoneCounting()){
				this.counts += 1;
				this.function.calc();
			}else{
				this.reset();
			}
		}

		public int getCount(){
			return this.counts;
		}

		public void reset(){
			this.counts = 0;
		}

		public boolean isDoneCounting(){
			return this.counts > this.limit;
		}

	}

	@FunctionalInterface
	public static interface Func{
		
		void calc();
	}
}