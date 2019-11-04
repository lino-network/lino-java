package network.lino.types;

import java.io.IOException;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PubKeySerializer extends StdSerializer<String> {
  public PubKeySerializer() {
    this(null);
  }
  public PubKeySerializer(Class<String> t) {
    super(t);
  }

  @Override
  public void serialize(String pubKey, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    jgen.writeStartObject();
    jgen.writeStringField("type", "tendermint/PubKeySecp256k1");
    jgen.writeStringField("value", pubKey);
    jgen.writeEndObject();
  }
}

