package network.lino.broadcast;

import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import network.lino.types.StdFee;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;

@JsonSerialize(using = StdTxSerializer.class)
public class StdTx {
  public MessageInterface[] msg;
  public StdSig[] signatures;
  public StdFee fee;
  public String memo = "";

  public StdTx(MessageInterface[] msg, StdSig[] signatures, String fee) {
    this.msg = msg;
    this.signatures = signatures;
    this.fee = new StdFee(fee);
  }
}

class StdTxSerializer extends StdSerializer<StdTx> {
  public StdTxSerializer() {
    this(null);
  }

  public StdTxSerializer(Class<StdTx> t) {
    super(t);
  }

  @Override
  public void serialize(StdTx tx, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    jgen.writeStartObject();
    jgen.writeStringField("type", "auth/StdTx");
    jgen.writeObjectFieldStart("value");
    jgen.writeObjectField("msg", tx.msg);
    jgen.writeObjectField("signatures", tx.signatures);
    jgen.writeObjectField("fee", tx.fee);
    jgen.writeObjectField("memo", tx.memo);
    jgen.writeEndObject();
    jgen.writeEndObject();
  }
}
