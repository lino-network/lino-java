package network.lino.core;

import org.junit.Test;
import static org.junit.Assert.*;
import network.lino.broadcast.AccOrAddr;

public class LinoTest {
  static Lino lino = new Lino.Builder().build();
  @Test public void transfer() {
    // try {
    //   long seq = lino.query.getSequenceNumber("ytu");
    //   String privKey = "";
    //   AccOrAddr s = new AccOrAddr.Builder().setAccountKey("ytu").build();
    //   AccOrAddr r = new AccOrAddr.Builder().setAddr("lino1722lj3a89nnmt8teadp98h5rkvrcsc4e2ulm9s").build();
    //   String rst = lino.broadcast.transfer(s, r, "1", "memo", privKey, seq);
    //   System.out.println(rst);
    // } catch (Exception e) {
    //   System.out.println(e);
    //   fail(e.toString());
    // }
  }
  @Test public void isTxSuccessful() {
    try {
      boolean re = lino.query.isTxSuccessful("EA42E903B6F92085EE2F69D577045AA0E251779A0D1C705EC15EE4584EB6E54A");
      assertTrue(re);
    } catch (Exception e) {
    }
  }
}
