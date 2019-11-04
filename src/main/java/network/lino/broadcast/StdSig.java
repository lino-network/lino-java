package network.lino.broadcast;

import network.lino.types.PubKeySerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class StdSig {
  @JsonProperty("pub_key")
  @JsonSerialize(using = PubKeySerializer.class)
  public String pubKey;
  public byte[] signature;

  public StdSig(String pubKey, byte[] signature) {
    this.pubKey = pubKey;
    this.signature = signature;
  }
}
