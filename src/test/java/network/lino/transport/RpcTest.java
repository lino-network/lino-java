package network.lino.transport;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;

public class RpcTest {
  @Test public void testRpcPost() {
    RpcClient client = new RpcClient("https://postman-echo.com/post");
    Map<String, Object> params = new HashMap<String, Object>() {{
      put("testKey", "testValue");
    }};
    RequestBody r = new RequestBody("test", params);
    try {
      String resp = client.post(r);
      assertTrue(resp.contains("\"data\":\"{\\\"jsonrpc\\\":\\\"2.0\\\",\\\"id\\\":\\\"jsonrpc-client\\\",\\\"method\\\":\\\"test\\\",\\\"params\\\":{\\\"trusted\\\":false,\\\"testKey\\\":\\\"testValue\\\",\\\"height\\\":\\\"0\\\"}}\""));
    } catch (Exception e) {
      fail(e.toString());
    }
  }
  @Test public void testAbciQuery() {
    RpcClient client = new RpcClient("https://postman-echo.com/post");
    Map<String, Object> params = new HashMap<String, Object>() {{
      put("testKey", "testValue");
    }};
    try {
      String resp = client.abciQuery("testPath", "testKey");
      assertTrue(resp.contains("\"data\":\"{\\\"jsonrpc\\\":\\\"2.0\\\",\\\"id\\\":\\\"jsonrpc-client\\\",\\\"method\\\":\\\"abci_query\\\",\\\"params\\\":{\\\"path\\\":\\\"testPath\\\",\\\"data\\\":\\\"testKey\\\",\\\"trusted\\\":false,\\\"height\\\":\\\"0\\\"}}\""));
    } catch (Exception e) {
      fail(e.toString());
    }
  }
}
