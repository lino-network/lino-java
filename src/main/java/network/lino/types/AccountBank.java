package network.lino.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

class Amount {
  public String amount;
}

class FrozenMoney {
  public Amount amount;
  @JsonProperty("start_at")
  public String startAt;
  public String times;
  public String interval;
}

public class AccountBank {
  public Amount saving;
  public String sequence;
  public String username;
  @JsonProperty("public_key")
  @JsonDeserialize(using = PubKeyDeserializer.class)
  public String publicKey;
  @JsonProperty("frozen_money_list")
  public FrozenMoney[] fronzeMoneyList;
}

