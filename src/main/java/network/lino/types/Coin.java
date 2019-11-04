package network.lino.types;

public class Coin {
  public String denom;
  public String amount;
  public Coin(String denom, String amount) {
    this.denom = denom;
    this.amount = amount;
  }
  public Coin(String amount) {
    this.denom = "linocoin";
    this.amount = amount;
  }
}
