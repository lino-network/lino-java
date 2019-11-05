package network.lino.utils;

import java.util.Base64;
import org.bitcoin.NativeSecp256k1;
import java.security.MessageDigest;
import org.apache.tuweni.crypto.SECP256K1;
import org.apache.tuweni.bytes.Bytes32;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.MutableBytes;
import org.apache.tuweni.units.bigints.UInt256;

public class Encoder {
  private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

  private static String PREFIX_PUB_KEY_SECP256K1 = "EB5AE98721";

  private static String PREFIX_PRIV_KEY_SECP256K1 = "E1B0F79B20";

  // https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
  public static String bytesToHex(byte[] bytes) {
    char[] hexChars = new char[bytes.length * 2];
    for (int j = 0; j < bytes.length; j++) {
        int v = bytes[j] & 0xFF;
        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
    }
    return new String(hexChars);
  }

  // https://stackoverflow.com/questions/140131/convert-a-string-representation-of-a-hex-dump-to-a-byte-array-using-java
  public static byte[] hexToBytes(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                             + Character.digit(s.charAt(i+1), 16));
    }
    return data;
  }

  public static String encodeBase64(byte[] data) {
    return Base64.getEncoder().encodeToString(data);
  }

  public static byte[] decodeBase64(String str) {
      return Base64.getDecoder().decode(str);
  }

  public static String decodeBase64ToHex(String str) {
    return bytesToHex(decodeBase64(str));
  }

  public static String encodePubKey(String pubKeyHex) {
    return String.format("%s%s", PREFIX_PUB_KEY_SECP256K1, pubKeyHex).toUpperCase();
  }

  public static String pubKeyFromBase64(String str) {
    return encodePubKey(decodeBase64ToHex(str));
  }

  private static String privKeyHexWithoutPrefix(String privKey) throws EncoderException {
    String prefix = privKey.substring(0, 10);
    if (!prefix.equals(PREFIX_PRIV_KEY_SECP256K1)) {
      throw new EncoderException("Invalid private key");
    }
    return privKey.substring(10);
  }

  public static byte[] decodePrivKey(String privKeyHex) throws EncoderException {
    return hexToBytes(privKeyHexWithoutPrefix(privKeyHex));
  }

  public static byte[] sha256(byte[] s) throws EncoderException {
    byte[] rst = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      rst = md.digest(s);
    } catch (Exception e) {
      throw new EncoderException(e.toString());
    }
    return rst;
  }

  public static SECP256K1.KeyPair keyPair(String privKeyHex) throws EncoderException {
    return SECP256K1.KeyPair.fromSecretKey(SECP256K1.SecretKey.fromBytes(Bytes32.wrap(decodePrivKey(privKeyHex))));
  }

  // https://github.com/apache/incubator-tuweni/blob/f8995ac8f58af5dad9537e3623df497d1af2f52c/crypto/src/main/java/org/apache/tuweni/crypto/SECP256K1.java#L930
  public static Bytes sigToBytes(SECP256K1.Signature sig) {
    MutableBytes signature = MutableBytes.create(64);
    UInt256.valueOf(sig.r()).toBytes().copyTo(signature, 0);
    UInt256.valueOf(sig.s()).toBytes().copyTo(signature, 32);
    return signature;
  }

  public static byte[] secp256k1Sign(byte[] data, SECP256K1.KeyPair kp) throws EncoderException {
    byte[] rst = null;
    try {
      SECP256K1.Signature sig = SECP256K1.signHashed(data, kp);
      rst = sigToBytes(sig).toArray();
    } catch (Exception e) {
      throw new EncoderException(e.toString());
    }
    return rst;
  }

  public static byte[] compressPubKey(byte[] uncompressed) {
    MutableBytes rst = MutableBytes.create(33);
    Bytes x = Bytes.wrap(uncompressed, 0, 32);
    Bytes y = Bytes.wrap(uncompressed, 32, 32);
    boolean yOdd = (y.get(21) & 0x1) == 1;
    byte prefix = (byte) (yOdd ? 3 : 2);
    byte[] prefixArray = { prefix };
    Bytes pre = Bytes.wrap(prefixArray);
    return Bytes.concatenate(pre, x).toArray();
  }

  public static Bech32.Bech32Data decodeAddr(String addr) throws EncoderException {
    Bech32.Bech32Data b = Bech32.decode(addr);
    return b;
  }
}
