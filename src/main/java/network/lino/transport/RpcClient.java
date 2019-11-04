package network.lino.transport;

import java.util.Map;
import java.util.HashMap;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

class RequestBody {
  public String jsonrpc = "2.0";
  public String id = "jsonrpc-client";
  public String method;
  public Map<String, Object> params;

  protected RequestBody(String method, Map<String, Object> params) {
    Map<String, Object> mergedParams = new HashMap<>(params);
    mergedParams.put("height", "0");
    mergedParams.put("trusted", new Boolean(false));
    this.method = method;
    this.params = mergedParams;
  }
}

public class RpcClient implements RpcInterface {

  private String nodeUrl;
  private static int TIMEOUT_MS = 10000;

  public RpcClient(String nodeUrl) {
    this.nodeUrl = nodeUrl;
  }

  public String abciQuery(String path, String key) throws RpcException, IOException {
    Map<String, Object> params = new HashMap<String, Object>(){{
      put("path", path);
      put("data", key);
    }};
    RequestBody req = new RequestBody("abci_query", params);
    return this.post(req);
  }

  public String broadcastTxSync(byte[] tx) throws RpcException, IOException {
    Map<String, Object> params = new HashMap<String, Object>() {{
      put("tx", tx);
    }};
    return this.post(new RequestBody("broadcast_tx_sync", params));
  }

  public String tx(byte[] hash) throws RpcException, IOException {
    Map<String, Object> params = new HashMap<String, Object>() {{
      put("hash", hash);
    }};
    return this.post(new RequestBody("tx", params));
  }

  protected String post(RequestBody body) throws RpcException, IOException {
    String responseStr = null;
    IOException ioException = null;
    RpcException apiException = null;

    URL url = new URL(this.nodeUrl);
    HttpURLConnection conn = null;
    try {
      conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setInstanceFollowRedirects(false);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "text/json");
      conn.setReadTimeout(TIMEOUT_MS);
      ObjectMapper mapper = new ObjectMapper();
      // System.out.println(mapper.writeValueAsString(body));
      byte[] reqBody = mapper.writeValueAsString(body).getBytes("utf-8");
      OutputStream os = conn.getOutputStream();
      os.write(reqBody, 0, reqBody.length);
      if (conn.getResponseCode() != 200) {
        apiException = new RpcException(inputStreamToString(conn.getErrorStream()));
      } else {
        responseStr = inputStreamToString(conn.getInputStream());
      }
    } catch (IOException e) {
      ioException = e;
    } finally {
      try {
        if (apiException != null) {
          conn.getErrorStream().close();
        }
        conn.getInputStream().close();
      } catch (Exception ex) {
      }
      if (ioException != null){
        throw ioException;
      }
      if (apiException != null) {
        throw apiException;
      }
    }

    // System.out.println(responseStr);
    return responseStr;
  }

  // TODO: return byte array instead of String
  private static String inputStreamToString (InputStream is) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));

    StringBuilder responseStringBuilder = new StringBuilder();
    String line = "";

    while ((line = reader.readLine()) != null) {
      responseStringBuilder.append(line);
    }

    reader.close();

    return responseStringBuilder.toString();
  }
}

