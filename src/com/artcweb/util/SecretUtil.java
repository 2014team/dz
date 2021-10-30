
package com.artcweb.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SecretUtil {

	public static String getGenerateWord(Integer secretDigit) {
		String[] shuffle = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
						"r", "s", "t", "u", "v", "w", "x", "y", "z" };
//		String[] shuffle = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E",
//						"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
//						"Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
//						"r", "s", "t", "u", "v", "w", "x", "y", "z" };

		List<String> list = Arrays.asList(shuffle);
		Collections.shuffle(list);// 打乱顺序

		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			sb.append(s);
		}

		return sb.toString().substring(4, 4+secretDigit);
	}
	
	public static void main(String[] args) {
		System.out.println(getGenerateWord(32));
	}
}
