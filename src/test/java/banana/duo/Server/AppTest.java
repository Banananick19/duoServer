package banana.duo.Server;

import static org.junit.Assert.assertTrue;

import banana.duo.common.ActionType;
import banana.duo.common.Message;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void CheckJSONSerializeMessage()
    {
        Gson gson = new Gson();
        String json = gson.toJson(new Message(ActionType.MouseMove, new HashMap(Map.ofEntries(Map.entry("x", "10"), Map.entry("y", "10")))));
        System.out.println(json);
        Message message = gson.fromJson(json, Message.class);
        System.out.println(message.getContent());
    }
}
