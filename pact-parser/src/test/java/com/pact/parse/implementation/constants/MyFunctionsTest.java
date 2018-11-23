package com.pact.parse.implementation.constants;

import com.pact.parse.dto.HeaderObj;
import com.pact.parse.dto.InteractionDTO;
import com.pact.parse.dto.payload.JSONObjEx;
import com.pact.parse.implementation.PactParse;
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
        JSONObjEx obj = new JSONObjEx("{\n" +
                "        \"Content-Type\": \"application/json\",\n" +
                "        \"Long-Type\": \"application/json\",\n" +
                "        \"Test-Type\": \"application/json\"\n" +
                "    }");
        Map actual = MyFunctions.convertMap.apply(obj);
        Map expected = new HashMap<String, List>(){{
           put("Content-Type".toUpperCase(), Collections.singletonList("application/json"));
           put("Long-Type".toUpperCase(), Collections.singletonList("application/json"));
           put("Test-Type".toUpperCase(), Collections.singletonList("application/json"));


        }};
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void testConvertMap1(){
        JSONObjEx obj = new JSONObjEx("{\n" +
                "        \"Content-Type\": \"application/json;test/xhtml\",\n" +
                "        \"Long-Type\": \"application/json\",\n" +
                "        \"Test-Type\": \"application/json;ter/iou\"\n" +
                "    }");
        Map actual = MyFunctions.convertMap.apply(obj);
        Map expected = new HashMap<String, List>(){{
            put("Content-Type".toUpperCase(), Collections.singletonList("application/json;test/xhtml"));
            put("Test-Type".toUpperCase(), Collections.singletonList("application/json;ter/iou"));
            put("Long-Type".toUpperCase(), Collections.singletonList("application/json"));


        }};
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void testConvertMap2(){
        JSONObjEx obj = new JSONObjEx("{}");
        Map actual = MyFunctions.convertMap.apply(obj);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void testGetRequestHeader(){
        JSONObjEx json = new JSONObjEx("{\"request\": {\n" +
                "        \"method\": \"GET\",\n" +
                "        \"path\": \"/data/name\",\n" +
                "        \"headers\": {\n" +
                "          \"Content-Type\": \"application/json\"\n" +
                "        }\n" +
                "      }}");
        HeaderObj actual = MyFunctions.getRequestHeader.apply(json);
        HeaderObj expected = new HeaderObj(new HashMap<String,List>(){{
            put("Content-Type".toUpperCase(), Collections.singletonList("application/json"));
        }});
        Assert.assertEquals(actual.getMaps(),expected.getMaps());

    }

    @Test
    public void testGetRequestHeader1(){
        JSONObjEx json = new JSONObjEx("{\"request\": {\n" +
                "        \"method\": \"GET\",\n" +
                "        \"path\": \"/data/name\"\n" +
                "      }}");
        HeaderObj actual = MyFunctions.getRequestHeader.apply(json);

        Assert.assertTrue(actual.getMaps().isEmpty());

    }

    @Test
    public void testGetRequestBody(){
        JSONObjEx json = new JSONObjEx("{\"request\": {\n" +
                "        \"method\": \"GET\",\n" +
                "        \"path\": \"/data/name\"\n" +
                "      }}");
        JSONObjEx actual = MyFunctions.getRequestBody.apply(json);

        Assert.assertNull(actual);

    }

    /*@Test
    public void testRequestBody(){
        JSONObjEx json = new JSONObjEx("{\"request\": {\n" +
                "        \"method\": \"GET\",\n" +
                "        \"path\": \"/data/name\"\n" +
                "      }}");
        JSONObjEx actual = new JSONObjEx("{\"request\": {\n" +
                "        \"method\": \"GET\",\n" +
                "        \"path\": \"/data/name\"\n" +
                "      }}");

        Assert.assertEquals(json,actual);

    }*/

    @Test
    public void testFinalTest(){
        PactParse pactParse = new PactParse();
        InteractionDTO dto = pactParse.getInteractions(pactParse.getJsonObj(new File("src/test/resources/test.json")));
        Assert.assertNotNull(dto);
    }



}
