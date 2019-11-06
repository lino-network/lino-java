package network.lino.query;

import network.lino.transport.Transport;
import org.junit.Test;
import static org.junit.Assert.*;
import network.lino.types.AccountBank;

public class QueryTest {
  static Transport t = new Transport("https://fullnode.lino.network");
  static String testUsername = "ytu";
  static Query q = new Query(t);
  @Test public void testQueryGetAccountBank() {
    try{
      AccountBank resp = q.getAccountBank(testUsername);
      assertEquals(resp.username, testUsername);
      assertEquals(resp.publicKey, "EB5AE98721029F73CC16FB33E91CD1221CAE25B9B59038C591FB00DBCBE3381360EA0258FDB1");
    } catch (Exception e) {
      fail(e.toString());
    }
  }
  @Test public void testQueryGetSequence() {
    try{
      long resp = q.getSequenceNumber(testUsername);
      assertTrue(resp > 0);
    } catch (Exception e) {
      fail(e.toString());
    }
  }
  @Test public void testIsTxSuccessful() {
    try {
      boolean rst = q.isTxSuccessful("3D93E0392F2CD16C7F0BE4380010E9B83EE0947E21875742ED7AF89BBCFFA3BF");
      assertTrue(rst);
    } catch (Exception e) {
    }
  }
}

