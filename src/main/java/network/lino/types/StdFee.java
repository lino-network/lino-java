package network.lino.types;

public class StdFee {
  public Coin[] amount;
  public String gas = "0";
  public StdFee(String amount) {
    Coin a = new Coin(amount);
    Coin[] as = { a };
    this.amount = as;
  }
}
