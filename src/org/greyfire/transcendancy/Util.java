package org.greyfire.transcendancy;

public abstract class Util {
	private static int[]    romanNumerals_values  = {  1,   5,  10,  50, 100, 500, 1000};
	private static String[] romanNumerals_symbols = {"I", "V", "X", "L", "C", "D",  "M"};
	public static String romanNumerals(int n) {
		if(n<=0) throw new IllegalArgumentException("argument must be positive");
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<romanNumerals_values.length; i++) {
			while(n>=romanNumerals_values[i]) {
				sb.append(romanNumerals_symbols[i]);
				n -= romanNumerals_values[i];
			}
		}
		return sb.toString();
	}
}
