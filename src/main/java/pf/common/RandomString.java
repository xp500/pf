package pf.common;

import java.util.Set;

public class RandomString {

	final Set<String> usedStrings;
	int counter = 1;

	public RandomString(final Set<String> usedStrings) {
		this.usedStrings = usedStrings;
	}

	public String getNext() {
		String next;
		do {
			next = toName(counter);
			counter++;
		} while (usedStrings.contains(next));
		return next;
	}

	private static String toName(int number) {
		StringBuilder sb = new StringBuilder();
		while (number-- > 0) {
			sb.append((char) ('A' + (number % 26)));
			number /= 26;
		}
		return sb.reverse().toString();
	}

}
