package network.lino.query;

import network.lino.transport.Transport;
import java.io.IOException;
import network.lino.transport.RpcException;
import java.util.Base64;
import network.lino.utils.Encoder;
import network.lino.types.PubKeyDeserializer;
import network.lino.types.AccountBank;

public class Query {
  private Transport transport;

  public Query(Transport transport) {
    this.transport = transport;
  }

  public AccountBank getAccountBank(String username) throws IOException, RpcException {
    String[] keys = {username};
    return this.transport.query(AccountBank.class, keys, "account", "bank");
  }

  public long getSequenceNumber(String username) throws IOException, RpcException {
    // TODO: should use unsigned long
    return Long.parseLong(this.getAccountBank(username).sequence);
  }

  // Returns true if success, otherwise throws an RpcException
  public boolean isTxSuccessful(String txHash) throws IOException, RpcException {
    byte[] hash = Encoder.hexToBytes(txHash);
    return this.transport.tx(hash);
  }
}
