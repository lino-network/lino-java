package network.lino.core;

import network.lino.transport.Transport;
import network.lino.query.Query;
import network.lino.broadcast.Broadcast;

public class Lino {
  private Transport t;
  public Query query;
  public Broadcast broadcast;
  public static class Builder {
    private String chainId = "lino-testnet-upgrade5";
    private String maxFeeInCoin = "1000000";
    private String nodeUrl = "https://fullnode.lino.network";

    public Builder setChainId(String chainId) {
      this.chainId = chainId;
      return this;
    }

    public Builder setMaxFeeInCoin(String maxFeeInCoin) {
      this.maxFeeInCoin = maxFeeInCoin;
      return this;
    }

    public Builder setNodeUrl(String nodeUrl) {
      this.nodeUrl = nodeUrl;
      return this;
    }

    public Lino build() {
      Lino l = new Lino();
      l.t = new Transport(this.nodeUrl);
      l.broadcast = new Broadcast(l.t, this.chainId, this.maxFeeInCoin);
      l.query = new Query(l.t);
      return l;
    }
  }
}
