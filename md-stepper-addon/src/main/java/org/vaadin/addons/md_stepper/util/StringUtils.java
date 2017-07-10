package org.vaadin.addons.md_stepper.util;

/**
 * Helper methods for working with strings.
 * <p>
 * Prevent dependency to e.g. apache commons.
 */
public final class StringUtils {

  private StringUtils() {
    // Prevent instantiation
  }

  /**
   * Check if the given string is blank.
   * <p>
   * A string is blank if it is <code>null</code>, has a length of 0 or consists only of whitespace
   * characters.
   *
   * @param str
   *     The string to check
   *
   * @return <code>true</code> if the string is blank, <code>false</code> else
   */
  public static boolean isBlank(String str) {
    int strLen;
    if (str == null || (strLen = str.length()) == 0) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if ((!Character.isWhitespace(str.charAt(i)))) {
        return false;
      }
    }
    return true;
  }

}
