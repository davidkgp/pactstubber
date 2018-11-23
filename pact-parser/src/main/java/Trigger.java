import org.json.JSONObject;
import org.json.JSONPointer;

public class Trigger {


    public static void main(String[] args) {
        String jsonPayload="{\n" +
                "    \"headers\": {\n" +
                "        \"Content-Type\": \"application/json\",\n" +
                "        \"Long-Type\": \"application/json\",\n" +
                "        \"Test-Type\": \"application/json\"\n" +
                "    },\n" +
                "    \"method\": \"GET\",\n" +
                "    \"path\": \"/data/name\"\n" +
                "}";

        JSONObject json = new JSONObject(jsonPayload);
        JSONPointer jsonPointer = new JSONPointer("/headers");
        System.out.println(jsonPointer.queryFrom(json).toString());
    }
}
