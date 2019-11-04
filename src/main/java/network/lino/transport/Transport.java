package network.lino.transport;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Base64;

@JsonIgnoreProperties(ignoreUnknown = true)
class Response {
  public ResponseResult result;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ResponseResult {
  public ResponseResultResponse response;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ResponseResultResponse {
  public byte[] value;
  public int code;
  public String log;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class BroadcastResponse {
  public BroadcastResponseResult result;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class BroadcastResponseResult {
  public int code;
  public String log;
  public byte[] data;
  public String hash;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class TxResponse {
  public TxResponseResult result;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class TxResponseResult {
  public String hash;
  public String height;
  public String tx;
  @JsonProperty("tx_result")
  public TxResponseResultTxResult txResult;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class TxResponseResultTxResult {
  public int code;
  public String log;
}

public class Transport {
  private RpcClient rpc;
  private static ObjectMapper mapper = new ObjectMapper();

  public Transport(String nodeUrl) {
    this.rpc = new RpcClient(nodeUrl);
  }

  // Result value is decoded from base64
  public <T> T query(Class<T> clazz, String[] keys, String storeName, String subStoreName) throws RpcException, IOException {
    StringBuilder sb = new StringBuilder(String.format("/custom/%s/%s", storeName, subStoreName));
    for (String key : keys) {
      sb.append("/");
      sb.append(key);
    }
    String path = sb.toString();
    String respStr = this.rpc.abciQuery(path, "");
    Response resp = mapper.readValue(respStr, Response.class);
    ResponseResult rst = resp.result;
    if (rst == null) {
      throw new RpcException("query failed");
    }
    ResponseResultResponse response = rst.response;
    if (response.code != 0 || response.value == null) {
      throw new RpcException(response.log);
    }

    // byte[] values = Base64.getDecoder().decode(resp.result.response.value);
    return mapper.readValue(response.value, clazz);
  }

  public String broadcastSync(byte[] tx) throws RpcException, IOException {
    String respStr = this.rpc.broadcastTxSync(tx);
    BroadcastResponse resp = mapper.readValue(respStr, BroadcastResponse.class);
    BroadcastResponseResult result = resp.result;
    if (result == null) {
      throw new RpcException("transaction broadcast failed");
    }
    if (result.code != 0) {
      throw new RpcException(result.log);
    }
    return result.hash;
  }

  public boolean tx(byte[] hash) throws RpcException, IOException {
    String respStr = this.rpc.tx(hash);
    TxResponse resp = mapper.readValue(respStr, TxResponse.class);
    TxResponseResult rst = resp.result;
    if (rst == null) {
      throw new RpcException("query failed");
    }
    TxResponseResultTxResult r = rst.txResult;
    if (r.code != 0) {
      throw new RpcException(r.log);
    }
    return true;
  }
}
