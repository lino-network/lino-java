package network.lino.broadcast;

import network.lino.transport.Transport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import network.lino.utils.Encoder;
import network.lino.utils.EncoderException;
import org.apache.tuweni.crypto.SECP256K1;
import network.lino.transport.RpcException;
import java.io.IOException;

public class Broadcast {
  private Transport transport;
  private String chainId;
  private String maxFeeInCoin;
  private static ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
  public Broadcast(Transport transport, String chainId, String maxFeeInCoin) {
    this.transport = transport;
    this.chainId = chainId;
    this.maxFeeInCoin = maxFeeInCoin;
  }

  public String transfer(AccOrAddr sender, AccOrAddr receiver, String amount, String memo, String privKeyHex, long seq) throws JsonProcessingException, EncoderException, RpcException, IOException {
    TransferMsg msg = new TransferMsg(sender, receiver, amount, memo);
    byte[] tx = this.signAndBuild(msg, privKeyHex, seq);
    return this.transport.broadcastSync(tx);
  }

  public byte[] signAndBuild(MessageInterface msg, String privKeyHex, long seq) throws JsonProcessingException, EncoderException {
    SECP256K1.KeyPair kp = Encoder.keyPair(privKeyHex);
    MessageInterface[] msgs = { msg };

    // Get signature from sign bytes.
    StdSignBytes s = new StdSignBytes(this.chainId, seq, this.maxFeeInCoin, msgs);
    byte[] digest = Encoder.sha256(mapper.writeValueAsBytes(s));
    byte[] sig = Encoder.secp256k1Sign(digest, kp);

    // create signature field with public key and signature from above step.
    byte[] publicKey = Encoder.compressPubKey(kp.publicKey().bytesArray());
    StdSig stdSig = new StdSig(Encoder.encodeBase64(publicKey), sig);

    StdSig[] sigs = { stdSig };
    StdTx tx = new StdTx(msgs, sigs, this.maxFeeInCoin);

    return mapper.writeValueAsBytes(tx);
  }
}
