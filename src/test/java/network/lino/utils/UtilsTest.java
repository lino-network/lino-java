package network.lino.utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class UtilsTest {
  @Test public void testIsValidAddress() {
    String[] validAddresses = { "lino1sp07rm9tldys3tkpx26tjuptm7gzpess7rjf2j", "lino1dk6w44dyh6fxm45sg8addxr2r0qqqk83n8fc92", "lino1722lj3a89nnmt8teadp98h5rkvrcsc4e2ulm9s" };
    for (String addr : validAddresses) {
      try {
        assertTrue(Utils.isValidAddress(addr));
      } catch (Exception e) {
        fail(e.toString());
      }
    }
    String[] invalidAddresses = { "hello, world!", "test1sp07rm9tldys3tkpx26tjuptm7gzpess7rjf2j" };
    for (String addr : invalidAddresses) {
      try {
        boolean rst = Utils.isValidAddress(addr);
      } catch (Exception e) {
        assertTrue(true);
      }
    }
  }
  @Test public void testIsValidUsername() {
    String[] validUsernames = { "register", "re.gister", "re-gister", "reg", "registerregisterregi" };
    String[] invalidUsernames = { "us", "useruseruseruseruseru", "register#", "_register", "-register", "reg@ister", 
                "re--gister", "reg*ister", "register!", "register()", "reg$ister", "reg ister", " register", "re_-gister",
                "reg=ister", "register^", "register.", "reg$ister,", "Register", "r__egister", "reGister",
                "r_--gister", "re.-gister", ".re-gister", "re-gister.", "register_", "register-", "a.2.2.-.-..2",
                ".register", "register..", "_.register", "123123", "reg--ster", "reg$ster", "re%gister", "regist\"er",
                "reg?ster", "reg:ster", "regi<ster", "regi>ster", "reg{ster", "regi}ster", "reg'ster", "reg`ster" };
    for (String validUsername : validUsernames) {
      assertTrue(Utils.isValidUsername(validUsername));
    }
    for (String invalidUsername : invalidUsernames) {
      assertFalse(Utils.isValidUsername(invalidUsername));
    }
  }
}
