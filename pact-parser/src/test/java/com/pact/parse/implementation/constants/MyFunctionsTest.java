package com.pact.parse.implementation.constants;

import com.pact.parse.dto.HeaderObj;
import com.pact.parse.dto.InteractionDTO;
import com.pact.parse.implementation.PactParse;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyFunctionsTest {

    @Test
    public void testConvertMap(){
        JSONObject obj = new JSONObject("{\n" +
                "        \"Content-Type\": \"application/json\",\n" +
                "        \"Long-Type\": \"application/json\",\n" +
                "        \"Test-Type\": \"application/json\"\n" +
                "    }");
        Map actual = MyFunctions.convertMap.apply(obj);
        Map expected = new HashMap<String, List>(){{
           put("Content-Type", Collections.singletonList("application/json"));
           put("Long-Type", Collections.singletonList("application/json"));
           put("Test-Type", Collections.singletonList("application/json"));


        }};
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void testConvertMap1(){
        JSONObject obj = new JSONObject("{\n" +
                "        \"Content-Type\": \"application/json;test/xhtml\",\n" +
                "        \"Long-Type\": \"application/json\",\n" +
                "        \"Test-Type\": \"application/json;ter/iou\"\n" +
                "    }");
        Map actual = MyFunctions.convertMap.apply(obj);
        Map expected = new HashMap<String, List>(){{
            put("Content-Type", Collections.singletonList("application/json;test/xhtml"));
            put("Test-Type", Collections.singletonList("application/json;ter/iou"));
            put("Long-Type", Collections.singletonList("application/json"));


        }};
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void testConvertMap2(){
        JSONObject obj = new JSONObject("{}");
        Map actual = MyFunctions.convertMap.apply(obj);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void testGetRequestHeader(){
        JSONObject json = new JSONObject("{\"request\": {\n" +
                "        \"method\": \"GET\",\n" +
                "        \"path\": \"/data/name\",\n" +
                "        \"headers\": {\n" +
                "          \"Content-Type\": \"application/json\"\n" +
                "        }\n" +
                "      }}");
        HeaderObj actual = MyFunctions.getRequestHeader.apply(json);
        HeaderObj expected = new HeaderObj(new HashMap<String,List>(){{
            put("Content-Type", Collections.singletonList("application/json"));
        }});
        Assert.assertEquals(actual.getMaps(),expected.getMaps());

    }

    @Test
    public void testGetRequestHeader1(){
        JSONObject json = new JSONObject("{\"request\": {\n" +
                "        \"method\": \"GET\",\n" +
                "        \"path\": \"/data/name\"\n" +
                "      }}");
        HeaderObj actual = MyFunctions.getRequestHeader.apply(json);

        Assert.assertTrue(actual.getMaps().isEmpty());

    }

    @Test
    public void testGetRequestBody(){
        JSONObject json = new JSONObject("{\"request\": {\n" +
                "        \"method\": \"GET\",\n" +
                "        \"path\": \"/data/name\"\n" +
                "      }}");
        JSONObject actual = MyFunctions.getRequestBody.apply(json);

        Assert.assertNull(actual);

    }

    @Test
    public void testFinalTest(){
        PactParse pactParse = new PactParse();
        InteractionDTO dto = pactParse.getInteractions(pactParse.getJsonObj(new File("src/test/resources/test.json")));
        Assert.assertNotNull(dto);
    }



}
