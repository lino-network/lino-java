package network.lino.broadcast;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import network.lino.types.StdFee;

public class StdSignBytes {
  @JsonProperty("account_number")
  public String accountNumber = "0";
  @JsonProperty("chain_id")
  public String chainId;
  @JsonSerialize(using=ToStringSerializer.class)
  public long sequence;
  public MessageInterface[] msgs;
  public String memo = "";
  public StdFee fee;

  public StdSignBytes(String chainId, long seq, String fee, MessageInterface[] msgs) {
    this.chainId = chainId;
    this.sequence = seq;
    this.msgs = msgs;
    this.fee = new StdFee(fee);
  }
}
