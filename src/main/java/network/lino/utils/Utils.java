package network.lino.utils;

import network.lino.utils.Bech32;

public class Utils {
  private static String ADDR_PREFIX = "lino";
  private static int USERNAME_MINIMUM_LENGTH = 3;
  private static int USERNAME_MAXIMUM_LENGTH = 20;
  private static String USERNAME_VALID_REG = "^[a-z]([a-z0-9-\\.]){1,19}[a-z0-9]$";
  private static String USERNAME_INVALID_REG = "^[a-z0-9\\.-]*([-\\.]){2,}[a-z0-9\\.-]*$";

  public static boolean isValidAddress(String addr) throws EncoderException {
    Bech32.Bech32Data b = Encoder.decodeAddr(addr);
    return b.hrp.equals(ADDR_PREFIX);
  }

  public static boolean isValidUsername(String username) {
    if (username.length() < USERNAME_MINIMUM_LENGTH || username.length() > USERNAME_MAXIMUM_LENGTH) {
      return false;
    }
    if (!username.matches(USERNAME_VALID_REG)) {
      return false;
    }
    if (username.matches(USERNAME_INVALID_REG)) {
      return false;
    }
    return true;
  }
}
