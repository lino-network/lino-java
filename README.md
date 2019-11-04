# Lino-java
Java sdk for lino blockchain

## Dependencies
You need to add BouncyCastle as a security provider following either of these links
* https://www.bouncycastle.org/wiki/display/JA1/Provider+Installation (Installign the provider Statically)
* https://tomee.apache.org/bouncy-castle.html


## Example
```
import network.lino.core;
import network.lino.broadcast.AccOrAddr;

...

try {
  long seq = lino.query.getSequenceNumber("<username>");
  String privKey = "<privateKey-hexString>";
  // Could use either address or account for sender and receiver
  AccOrAddr s = new AccOrAddr.Builder().setAccountKey("<username>").build();
  AccOrAddr r = new AccOrAddr.Builder().setAddr("<bech32-address>").build();
  String txHash = lino.broadcast.transfer(s, r, "1", "<memo>", privKey, seq);
  System.out.println(txHash);
} catch (Exception e) {
  System.out.println(e);
}
```
