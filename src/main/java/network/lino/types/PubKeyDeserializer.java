package network.lino.types;

import java.io.IOException;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import network.lino.utils.Encoder;

public class PubKeyDeserializer extends StdDeserializer<String> {
  public PubKeyDeserializer() {
    this(null);
  }
  public PubKeyDeserializer(Class<?> vc) {
    super(vc);
  }
  @Override
  public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    JsonNode node = jp.getCodec().readTree(jp);
    String value = node.get("value").asText();
    return Encoder.pubKeyFromBase64(value);
  }
}
