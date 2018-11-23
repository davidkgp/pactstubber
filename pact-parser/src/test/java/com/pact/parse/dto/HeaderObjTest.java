package com.pact.parse.dto;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeaderObjTest {

    private static HeaderObj parentObj = null;

    @BeforeClass
    public static void setUp(){
        parentObj = new HeaderObj(null);
    }

    @Test
    public void checkLists(){
        List<String> actual = Arrays.asList("A","B","C","D","E");
        Assert.assertFalse(parentObj.checkLists(actual,null));
    }
    @Test
    public void checkLists1(){
        Assert.assertTrue(parentObj.checkLists(null,null));
    }

    @Test
    public void checkLists2(){
        List<String> actual = Arrays.asList("A","B","C","D","E");
        Assert.assertTrue(parentObj.checkLists(new ArrayList<String>(),new ArrayList<String>()));
    }

    @Test
    public void checkLists3(){
        List<String> actual = Arrays.asList("A","B","C","D","E");
        Assert.assertFalse(parentObj.checkLists(null,actual));
    }

    @Test
    public void checkLists4(){
        List<String> actual = Arrays.asList("A","B","C","D","E");
        List<String> expected = Arrays.asList("E","B","C","D","E");
        Assert.assertTrue(parentObj.checkLists(actual,expected));
    }

    @Test
    public void checkLists5(){
        List<String> actual = Arrays.asList("A","B","C","D","E","F");
        List<String> expected = Arrays.asList("E","B","C","D","E");
        Assert.assertTrue(parentObj.checkLists(actual,expected));
    }

    @Test
    public void checkLists6(){
        List<String> expected = Arrays.asList("A","B","C","D","E","F");
        List<String> actual = Arrays.asList("E","B","C","D","E");
        Assert.assertTrue(parentObj.checkLists(actual,expected));
    }

    @Test
    public void checkLists7(){
        List<String> expected = Arrays.asList("A","B","C");
        List<String> actual = Arrays.asList("B");
        Assert.assertTrue(parentObj.checkLists(actual,expected));
    }

    @Test
    public void checkLists8(){
        List<String> actual = Arrays.asList("A","B","C");
        List<String> expected = Arrays.asList("B");
        Assert.assertTrue(parentObj.checkLists(actual,expected));
    }

    @Test
    public void checkLists9(){
        List<String> actual = Arrays.asList("A","B","C");
        List<String> expected = Arrays.asList("E","B");
        Assert.assertFalse(parentObj.checkLists(actual,expected));
    }

    @Test
    public void checkLists10(){
        List<String> expected = Arrays.asList("A","B","C");
        List<String> actual = Arrays.asList("E","B");
        Assert.assertFalse(parentObj.checkLists(actual,expected));
    }

}
