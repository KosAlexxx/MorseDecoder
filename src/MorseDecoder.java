import java.util.HashMap;

public class MorseDecoder {
	static int dot;
	static int dash;
	static int spaceBetweenSymbols;
	static int spaceBetweenWords;

	static HashMap<String, String> morzeMap = new HashMap<String, String>() {
		{
			put(" ", " ");
			put(".-", "a");
			put("-...", "b");
			put("-.-.", "c");
			put("-..", "d");
			put(".", "e");
			put("..-.", "f");
			put("--.", "g");
			put("....", "h");
			put("..", "i");
			put(".---", "j");
			put("-.-", "k");
			put(".-..", "l");
			put("--", "m");
			put("-.", "n");
			put("---", "o");
			put(".--.", "p");
			put("--.-", "q");
			put(".-.", "r");
			put("...", "s");
			put("-", "t");
			put("..-", "u");
			put("...-", "v");
			put(".--", "w");
			put("-..-", "x");
			put("-.--", "y");
			put("--..", "z");
			put(".----", "1");
			put("..---", "2");
			put("...--", "3");
			put("....-", "4");
			put(".....", "5");
			put("-....", "6");
			put("--...", "7");
			put("---..", "8");
			put("----.", "9");
			put("-----", "0");
			put("--..--", ",");
			put(".-.-.-", ".");
			put("..--..", "?");
			put("-.-.--", "!");
			put("...---...", "SOS");

			put("·−", "a");
			put("−···", "b");
			put("−·−·", "c");
			put("−··", "d");
			put("·", "e");
			put("··−·", "f");
			put("−−·", "g");
			put("····", "h");
			put("··", "i");
			put("·−−−", "j");
			put("−·−", "k");
			put("·−··", "l");
			put("−−", "m");
			put("−·", "n");
			put("−−−", "o");
			put("·−−·", "p");
			put("−−·−", "q");
			put("·−·", "r");
			put("···", "s");
			put("−", "t");
			put("··−", "u");
			put("···−", "v");
			put("·−−", "w");
			put("−··−", "x");
			put("−·−−", "y");
			put("−−··", "z");
			put("·−−−−", "1");
			put("··−−−", "2");
			put("···−−", "3");
			put("····−", "4");
			put("·····", "5");
			put("−····", "6");
			put("−−···", "7");
			put("−−−··", "8");
			put("−−−−·", "9");
			put("−−−−−", "0");
			put("−−··−−", ",");
			put("·−·−·−", "·");
			put("··−−··", "?");
			put("−·−·−−", "!");
			put("···−−−···", "SOS");
		}
	};

	public static String decodeBits(String bits) {
		char[] n = bits.toCharArray();
		checkBitrate(n);
		String res = "";
		StringBuilder buffer = new StringBuilder();
		String buf;
		int N = n.length;
		int count = 0;

		for (int i = 0; i < N; i++) {

			if (Character.getNumericValue(n[i]) == 1) {
				while (Character.getNumericValue(n[i]) == 1) {
					count++;

					if (i == N - 1 || Character.getNumericValue(n[i + 1]) != 1)	break;

					i++;
					
				}

				if (count <= dot) {
					buffer.append('.');
					count = 0;

				} else {
					buffer.append('-');
					count = 0;
				}
			}

			if (Character.getNumericValue(n[i]) == 0) {
				
				while (Character.getNumericValue(n[i]) == 0) {
					
					count++;

					if (i == N - 1 || Character.getNumericValue(n[i + 1]) != 0) break;

					i++;
				}

				if (count < spaceBetweenSymbols) {
					count = 0;

				} else if (count >= spaceBetweenSymbols && count < spaceBetweenWords) { // fix
					buffer.append(' ');
					count = 0;	
					
				}
				
				else if(count >= spaceBetweenSymbols && spaceBetweenWords == 0) {
					buffer.append(' ');
					count = 0;	
				}
				
				else {
					buf = buffer.toString();
					buffer.setLength(0);
					res += decodeMorse(buf);
					res += " ";
					count = 0;
				}
			}
		}

		buf = buffer.toString();
		res += decodeMorse(buf);
		return res.trim();
	}

	private static void checkBitrate(char[] bits) {
		int unitLengthDot = 0;
		int unitLengthDash = 0;
		int zeroLengthBetweenSym = 0;
		int zeroLengthBetwenLett = 0;
		int zeroLengthBetweenWor = 0;
		
		int unitLengthCurrent = 0;
		int zeroLengthCurrent = 0;
		
		for(int i = 0; i < bits.length; i ++) {
			
			if(i != 0) i--;
			
			while(i == 0 && Character.getNumericValue(bits[i]) == 0) {
				while(Character.getNumericValue(bits[i]) == 0) {
					i++;
				}
			}
			
			if(unitLengthDot != 0 && unitLengthDash != 0 && zeroLengthBetweenSym != 0 && zeroLengthBetwenLett != 0 && zeroLengthBetweenWor != 0) {
				break;
			}
			
			while (Character.getNumericValue(bits[i]) == 1) {
				unitLengthCurrent++;
				if(i == bits.length - 1) break;
				i++;
			}

			while (Character.getNumericValue(bits[i]) == 0) {
				zeroLengthCurrent++;
				if(i == bits.length - 1) break;
				i++;
			}
			
			if (unitLengthDot == 0 && unitLengthCurrent != unitLengthDash) {
				unitLengthDot = unitLengthCurrent;
				if(zeroLengthCurrent != 0) {
					zeroLengthBetweenSym = zeroLengthCurrent;
				}
				unitLengthCurrent = 0;
				
			} else if (unitLengthDot > unitLengthCurrent) {
			
				unitLengthDash = unitLengthDot;
				unitLengthDot = unitLengthCurrent;
				unitLengthCurrent = 0;
				
			} else if (unitLengthCurrent > unitLengthDot && unitLengthDash == 0) {
				unitLengthDash = unitLengthCurrent;
				unitLengthCurrent = 0;
			}
			
			if (zeroLengthBetweenSym == 0) {
				if(zeroLengthCurrent == 0 || zeroLengthCurrent == 1) {
					zeroLengthBetweenSym = 0;
				} else if(zeroLengthCurrent < 3 && unitLengthDot > 3) {
					zeroLengthBetweenSym = zeroLengthCurrent;
					unitLengthDash = unitLengthDot;
					unitLengthDot = 0;
				} else if(unitLengthDot <= 3 && zeroLengthCurrent >= 9) {
					zeroLengthBetwenLett = zeroLengthCurrent;
				} else {
				zeroLengthBetweenSym = zeroLengthCurrent;
				zeroLengthCurrent = 0;
				}
				
			} else if (zeroLengthCurrent > zeroLengthBetweenSym && zeroLengthBetwenLett == 0) {
				zeroLengthBetwenLett = zeroLengthCurrent;
				zeroLengthCurrent = 0;
			} 
			
			else if (zeroLengthCurrent < zeroLengthBetweenSym && zeroLengthCurrent != 0) {
				zeroLengthBetwenLett = zeroLengthBetweenSym;
				zeroLengthBetweenSym = zeroLengthCurrent;
				zeroLengthCurrent = 0;
			}
	
			 else if (zeroLengthCurrent > zeroLengthBetwenLett && zeroLengthCurrent != zeroLengthBetweenSym) {
				 zeroLengthBetweenWor = zeroLengthCurrent;
				 zeroLengthCurrent = 0;
			 }
			
			  else if (zeroLengthCurrent > zeroLengthBetweenWor && zeroLengthBetweenWor != 0) {
				  zeroLengthBetweenSym = zeroLengthBetwenLett;
				  zeroLengthBetwenLett = zeroLengthBetweenWor;
				  zeroLengthBetweenWor = zeroLengthCurrent;
				  zeroLengthCurrent = 0;
				  
			} 	
			
			if (zeroLengthCurrent == 1 && unitLengthDot >= 3) {
				 if(Character.getNumericValue(bits[i]) == 0 && i == bits.length-1) {
						continue;
					}
				 
				zeroLengthBetweenSym = zeroLengthCurrent;
				unitLengthDash = unitLengthDot;
				unitLengthDot = 0;
			}
			
			unitLengthCurrent = 0;
			zeroLengthCurrent = 0;
			
			if(i == bits.length-1) {
				if(zeroLengthBetweenSym < 3 && zeroLengthBetweenSym != 0) {
					zeroLengthBetweenSym ++;
				}
			}
		}
		
		if(unitLengthDot < zeroLengthBetweenSym && zeroLengthBetwenLett == 0 && zeroLengthBetweenWor == 0) {
			dot = unitLengthDot;
			dash = unitLengthDash;
			spaceBetweenSymbols = zeroLengthBetweenSym;
			spaceBetweenWords = zeroLengthBetweenWor;
		}
		
		else if(unitLengthDot == zeroLengthBetweenSym) {
			dot = unitLengthDot;
			dash = unitLengthDash;
			spaceBetweenSymbols = zeroLengthBetweenSym + 1;
			spaceBetweenWords = zeroLengthBetweenWor;
		}
		else if(zeroLengthBetwenLett == 0 && zeroLengthBetweenWor == 0) {
			if(unitLengthDot >= 3 && zeroLengthBetweenSym < 3 && zeroLengthBetweenSym != 0 && zeroLengthBetweenWor != 0) {
				dash = unitLengthDot;
				dot = 0;
				spaceBetweenSymbols = zeroLengthBetweenSym + 1;
				spaceBetweenWords = zeroLengthBetweenWor;
			} else if (unitLengthDot >= 3 && zeroLengthBetweenSym < 3 && zeroLengthBetweenSym > 1) {
				dot = unitLengthDot;
				dash = 0;
				spaceBetweenSymbols = zeroLengthBetweenSym + 1;
				spaceBetweenWords = zeroLengthBetweenWor;
			} else if(unitLengthDot >=3 && zeroLengthBetweenSym > 1) {
				dot =  unitLengthDash;
				dash = unitLengthDot;
				spaceBetweenSymbols = zeroLengthBetweenSym +1;
				spaceBetweenWords = zeroLengthBetweenWor;
			}
			
			else {
			dot = unitLengthDot;
			dash = unitLengthDash;
			spaceBetweenSymbols = zeroLengthBetweenSym;
			spaceBetweenWords = zeroLengthBetweenWor;
			}
			
		} else {
		
		dot = unitLengthDot;
		dash = unitLengthDash;
		spaceBetweenSymbols = zeroLengthBetwenLett;
		spaceBetweenWords = zeroLengthBetweenWor;
		}
		
	}

	public static String decodeMorse(String morseCode) {
		String t = morseCode.trim();
		StringBuilder buffer = new StringBuilder();
		StringBuilder ret = new StringBuilder();

		for (int i = 0; i < t.length(); i++) {
			char c = t.charAt(i);
			if (c == ' ') {
				if (buffer.length() == 0) {
					buffer.append(c);
					continue;
				}
				ret.append(parseBuffer(buffer.toString()));
				buffer.setLength(0);
				continue;
			}
			buffer.append(c);
		}

		if (buffer.length() > 0) {
			ret.append(parseBuffer(buffer.toString()));
		}

		return ret.toString().toUpperCase();
	}

	private static Object parseBuffer(String buffer) {
		if (morzeMap.containsKey(buffer)) {
			return morzeMap.get(buffer);
		}
		return "*";
	}

	public static void main(String[] args) {
		System.out.println(decodeBits(
				"1100110011001100000011000000111111001100111111001111110000000000000011001111110011111100111111000000110011001111110000001111110011001100000011"));
	}
		// 11111111111111100000000000000011111000001111100000111110000011111000000000000000111110000000000000000000000000000000000011111111111111100000111111111111111000001111100000111111111111111000000000000000111110000011111000001111111111111110000000000000001111100000111110000000000000001111111111111110000011111000001111111111111110000011111000000000000000111111111111111000001111100000111111111111111000000000000000000000000000000000001111111111111110000011111000001111100000111110000000000000001111100000111111111111111000001111100000000000000011111111111111100000111111111111111000001111111111111110000000000000001111100000111111111111111000001111111111111110000000000000001111111111111110000011111000000000000000000000000000000000001111100000111110000011111111111111100000111110000000000000001111111111111110000011111111111111100000111111111111111000000000000000111111111111111000001111100000111110000011111111111111100000000000000000000000000000000000111110000011111111111111100000111111111111111000001111111111111110000000000000001111100000111110000011111111111111100000000000000011111111111111100000111111111111111000000000000000111110000011111111111111100000111111111111111000001111100000000000000011111000001111100000111110000000000000000000000000000000000011111111111111100000111111111111111000001111111111111110000000000000001111100000111110000011111000001111111111111110000000000000001111100000000000000011111000001111111111111110000011111000000000000000000000000000000000001111111111111110000000000000001111100000111110000011111000001111100000000000000011111000000000000000000000000000000000001111100000111111111111111000001111100000111110000000000000001111100000111111111111111000000000000000111111111111111000001111111111111110000011111000001111100000000000000011111111111111100000111110000011111111111111100000111111111111111000000000000000000000000000000000001111111111111110000011111000001111100000000000000011111111111111100000111111111111111000001111111111111110000000000000001111111111111110000011111111111111100000111110000000000000001111100000111111111111111000001111100000111111111111111000001111100000111111111111111
		// 00011100010101010001000000011101110101110001010111000101000111010111010001110101110000000111010101000101110100011101110111000101110111000111010000000101011101000111011101110001110101011100000001011101110111000101011100011101110001011101110100010101000000011101110111000101010111000100010111010000000111000101010100010000000101110101000101110001110111010100011101011101110000000111010100011101110111000111011101000101110101110101110
		// 000000011100000 - E
		// 01110 - E 						
		// 111000111000111 - S
		// 111000000000111 - EE
		// 11111100111111 - M	
		// 111110000011111 - I	
		// 111000111 - I
		// 110011 - I	
		// 1111111 - E 			
		// 111 - E				
		// 1110111 - M		
		// 10111 - A
		// 10001 - EE
		// 101 - I
		// 1 - E
		// 1100110011001100000011000000111111001100111111001111110000000000000011001111110011111100111111000000110011001111110000001111110011001100000011
}
