import java.util.HashMap;

import java.util.HashMap;

public class MorseDecoder {

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
		if (bits.length() < 0)
			return ""; // Пустые данные не обрабатывать!

		char[] n = bits.toCharArray();
		String res = "";
		StringBuilder buffer = new StringBuilder();
		String buf;
		int N = n.length;
		int count = 0;
		int speed = 1;
		int length_shortest = 0;
		int length_longest = 0;

		int length_recent = 0;
		int last_num = 0;

		// Обнаружить "скорость"
		for (int i = 0; i < N; i++) {
			int numVal = Character.getNumericValue(n[i]);

			// System.out.println(String.format("%d) %d", i, numVal));

			if (i == 0) {
				last_num = numVal;
				length_recent = 0;
			} else if (numVal != last_num) {
				if (length_recent != 0) {
					if (length_recent > count) {
						length_shortest = count;
						length_longest = length_recent;
					} else if (length_recent < count) {
						length_shortest = length_recent;
						length_longest = count;
					}

					if (length_shortest != 0 && length_longest != 0) {
						// System.out.println(String.format("S: %d, L: %d\n", length_shortest,
						// length_longest));
						speed = length_longest / 3;
						// System.out.println(String.format("Found a speed: %d\n", speed));
						break; // Скорость найдена!
					}
				}

				// System.out.println(String.format("Found: %d x %d\n", count, last_num));
				length_recent = count;
				count = 0;
				last_num = numVal;
			}

			count++;
		}

		// Считать сами данные
		count = 0;
		for (int i = 0; i < N; i += speed) {
			if (Character.getNumericValue(n[i]) == 1) {
				while (Character.getNumericValue(n[i]) == 1) {
					count++;
					if (i == N - speed || Character.getNumericValue(n[i + speed]) != 1)
						break;
					i += speed;
				}

				if (count < 3) {
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
					if (i == N - speed || Character.getNumericValue(n[i + speed]) != 0)
						break;
					i += speed;
				}

				if (count < 3) {
					count = 0;

				} else if (count > 1 && count < 5) {
					buffer.append(' ');
					count = 0;

				} else {
					buf = buffer.toString();
					buffer.setLength(0);
					res = decodeMorse(buf);
					res += " ";
					count = 0;
				}
			}
		}

		buf = buffer.toString();
		return res + decodeMorse(buf);
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
				"111000000000111"));
	} // (THE QUICK) THE QUICK ->
		// 11111100000011001100110011000000110000000000000011111100111111001100111111000000110011001111110000001100110000001111110011001111110011000000111111001100111111

}
