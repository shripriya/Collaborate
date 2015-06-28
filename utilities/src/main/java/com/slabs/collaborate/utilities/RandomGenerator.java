package com.slabs.collaborate.utilities;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomGenerator {

	private static final Logger L = LoggerFactory.getLogger(RandomGenerator.class);

	private static final String DEFAULT_RANDOM_TEXT = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static final int DEFAULT_LENGTH = 6;

	private static Random random = new Random();

	/**
	 * This method generates random 6 digit alpha numeric code.
	 * 
	 * @return {@link String}
	 */
	public static String generateRandomText() {

		StringBuilder str = new StringBuilder(DEFAULT_LENGTH);
		for (int i = 0; i < DEFAULT_LENGTH; i++) {
			str.append(DEFAULT_RANDOM_TEXT.charAt(random.nextInt(DEFAULT_RANDOM_TEXT.length())));
		}
		return str.toString();
	}

	/**
	 * This method generates a random number based on the input baseInput and
	 * the length of the generated random code.
	 * 
	 * @param baseInput
	 * @param length
	 * @return {@link String}
	 */
	public static String generateRandomText(String baseInput, int length) {

		StringBuilder str = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			str.append(baseInput.charAt(random.nextInt(baseInput.length())));
		}
		return str.toString();
	}

}
