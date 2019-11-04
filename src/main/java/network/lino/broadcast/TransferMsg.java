package network.lino.broadcast;

import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

@JsonSerialize(using = TransferMsgSerializer.class)
public class TransferMsg implements MessageInterface {
  public AccOrAddr sender;
  public AccOrAddr receiver;
  public String amount;
  public String memo;

  private static ObjectMapper mapper = new ObjectMapper();

  public TransferMsg(AccOrAddr sender, AccOrAddr receiver, String amount, String memo) {
    this.sender = sender;
    this.receiver = receiver;
    this.amount = amount;
    this.memo = memo;
  }
}


class TransferMsgSerializer extends StdSerializer<TransferMsg> {
  public TransferMsgSerializer() {
    this(null);
  }
  public TransferMsgSerializer(Class<TransferMsg> t) {
    super(t);
  }

  @Override
  public void serialize(TransferMsg msg, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    jgen.writeStartObject();
    jgen.writeStringField("type", "lino/transferv2");
		jgen.writeObjectFieldStart("value");
		jgen.writeStringField("amount", msg.amount);
		jgen.writeStringField("memo", msg.memo);
		jgen.writeObjectField("receiver", msg.receiver);
    jgen.writeObjectField("sender", msg.sender);
		jgen.writeEndObject();
		jgen.writeEndObject();
  }
}

