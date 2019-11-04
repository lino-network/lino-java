package network.lino.broadcast;

import org.junit.Test;
import static org.junit.Assert.*;
import network.lino.transport.Transport;
import network.lino.query.Query;

public class BroadcastTest {
  static String testPrivKeyHex = "E1B0F79B20DF31D0E415FCC95D02B3B9B30EF112363344AF7BC1C26217116FB15A3C508F94";
  static AccOrAddr sender = new AccOrAddr.Builder().setAddr("address").setAccountKey("sender").build();
  static AccOrAddr receiver = new AccOrAddr.Builder().setAddr("lino1jcx68wxhhk43njw8tur62fxekn5mm79p7jlep0").build();
  static TransferMsg msg = new TransferMsg(sender, receiver, "100", "memo");
  static Transport t = new Transport("https://fullnode.lino.network");
  static String chainId = "lino-testnet-upgrade5";
  static String maxFeeInCoin = "1000000";
  static Broadcast b = new Broadcast(t, chainId, maxFeeInCoin);

  @Test public void testSignAndBuild() {
    try {
      byte[] rst = b.signAndBuild(msg, testPrivKeyHex, 10);
      assertEquals(new String(rst), "{\"type\":\"auth/StdTx\",\"value\":{\"msg\":[{\"type\":\"lino/transferv2\",\"value\":{\"amount\":\"100\",\"memo\":\"memo\",\"receiver\":{\"addr\":\"lino1jcx68wxhhk43njw8tur62fxekn5mm79p7jlep0\",\"is_addr\":true},\"sender\":{\"account_key\":\"sender\"}}}],\"signatures\":[{\"pub_key\":{\"type\":\"tendermint/PubKeySecp256k1\",\"value\":\"AhcvTEXeNlhw9ZPGT9qJmmtSjeOmZYPLiiZE/EjHwmKV\"},\"signature\":\"pWt9BtTTaOaTwNbBo+ClSYxR12f9w5EtdfhILx8hkb9RrzS+THGt7pWrVTmfp1O22e2VYWCEx+FmppO4j5VR9g==\"}],\"fee\":{\"amount\":[{\"amount\":\"1000000\",\"denom\":\"linocoin\"}],\"gas\":\"0\"},\"memo\":\"\"}}");
    } catch (Exception e) {
      fail(e.toString());
    }
  }

  @Test public void testTransfer() {
    try {
      // Query q = new Query(t);
      // long seq = q.getSequenceNumber("ytu");
      // String privKey = "";
      // AccOrAddr s = new AccOrAddr.Builder().setAccountKey("ytu").build();
      // AccOrAddr r = new AccOrAddr.Builder().setAddr("lino1722lj3a89nnmt8teadp98h5rkvrcsc4e2ulm9s").build();
      // String rst = b.transfer(s, r, "1", "memo", privKey, seq);
      // System.out.println(rst);
    } catch (Exception e) {
      System.out.println(e);
      fail(e.toString());
    }
  }
}
