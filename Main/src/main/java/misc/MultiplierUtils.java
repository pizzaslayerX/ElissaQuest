package misc;

import java.util.Collections;

public class MultiplierUtils {

	public static String tuple(int n) {
		if(n < 2) return "";
		String[] firsts = new String[]{"double", "triple", "quadruple", "quintuple", "sextuple", "septuple", "octuple", "nonuple"};
		if(n < 10) return firsts[n - 2] + " ";
		String[] ones = new String[]{"", "un", "duo", "tre", "quattuor", "quin", "sex", "septen", "octo", "novem"};
		String[] tens = new String[]{"decuple", "vigintuple", "trigintuple", "quadragintuple", "quinquagintuple", "sexagintuple", "septuagintuple", "octogintuple", "nongentuple"};
		return ones[n % 10] + tens[n/10 - 1] + " ";
	}
	
	public static String romanNumeral(int a) {
		if(a == 0) return "";
		if(a >= 4000) return " " + a;
		String numerals = "IVXLCDM";
		String str = "";
		int b = a;
		for(int i = 0; b > 0; i += 2) {
			int c = b % 10;
			switch(c) {
				case 1: case 2: case 3:
					str = String.join("", Collections.nCopies(c, numerals.substring(i, i + 1))) + str;
				case 4:
					str = numerals.substring(i, i + 2) + str;
					break;
				case 5: case 6: case 7: case 8:
					str = numerals.substring(i + 1, i + 2) + String.join("", Collections.nCopies(c - 5, numerals.substring(i, i + 1))) + str;
					break;
				case 9:
					str = numerals.substring(i, i + 1) + numerals.substring(i + 2, i + 3) + str;
			}
			b /= 10;
		}
		return " " + str;
	}
}
