package network.lino.broadcast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_DEFAULT)
public class AccOrAddr {
  @JsonProperty("is_addr")
  public boolean isAddr;
  public String addr;
  @JsonProperty("account_key")
  public String accountKey;

  public static class Builder {
    private boolean isAddr;
    private String addr;
    public String accountKey;
    public Builder() {
      this.isAddr = false;
    }
    public Builder setAddr(String addr) {
      this.isAddr = true;
      this.addr = addr;
      this.accountKey = null;
      return this;
    }
    public Builder setAccountKey(String accountKey) {
      this.isAddr = false;
      this.accountKey = accountKey;
      this.addr = null;
      return this;
    }
    public AccOrAddr build() {
      AccOrAddr a = new AccOrAddr();
      a.isAddr = this.isAddr;
      a.accountKey = this.accountKey;
      a.addr = this.addr;
      return a;
    }
  }
}

