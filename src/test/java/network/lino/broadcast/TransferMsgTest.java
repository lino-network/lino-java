package network.lino.broadcast;

import org.junit.Test;
import static org.junit.Assert.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.MapperFeature;

public class TransferMsgTest {
  @Test public void testSerialize() {
    AccOrAddr sender = new AccOrAddr.Builder().setAddr("address").setAccountKey("account key").build();
    AccOrAddr receiver = new AccOrAddr.Builder().setAddr("receiver-address").build();
    TransferMsg msg = new TransferMsg(sender, receiver, "100", "memo");

    ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);

    try {
      String rst = mapper.writeValueAsString(msg);
      assertEquals(rst, "{\"type\":\"lino/transferv2\",\"value\":{\"amount\":\"100\",\"memo\":\"memo\",\"receiver\":{\"addr\":\"receiver-address\",\"is_addr\":true},\"sender\":{\"account_key\":\"account key\"}}}");
    } catch (Exception e) {
      fail(e.toString());
    }
  }
}
