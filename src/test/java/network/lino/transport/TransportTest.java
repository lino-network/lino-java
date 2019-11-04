package network.lino.transport;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import network.lino.types.AccountBank;
import network.lino.utils.Encoder;

public class TransportTest {
  static Transport t = new Transport("https://fullnode.lino.network");
  @Test public void testTransportQuerySuccess() {
    String testUsername = "ytu";
    String[] keys = {testUsername};
    try {
      AccountBank resp = t.query(AccountBank.class, keys, "account", "bank");
      assertEquals(resp.username, testUsername);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
  @Test public void testTransportQueryFail() {
    String[] keys = {"key1", "key2"};
    try {
      String resp = t.query(String.class, keys, "store", "subStore");
    } catch (Exception e) {
      assertEquals(e.toString(), "network.lino.transport.RpcException: {\"codespace\":\"sdk\",\"code\":6,\"message\":\"no custom querier found for route store\"}");
    }
  }
  @Test public void testTx() {
    try {
      String hashHex = "3D93E0392F2CD16C7F0BE4380010E9B83EE0947E21875742ED7AF89BBCFFA3BF";
      byte[] hash = Encoder.hexToBytes(hashHex);
      boolean resp = t.tx(hash);
      assertTrue(resp);
    } catch (Exception e) {
    }
  }
}
