package org.example.portTask;

import org.junit.Assert;
import org.junit.Test;

public class PortUtilTest {
    @Test
    public void testGetSequenceNumberSimpleOne(){
        String[] input = {"1-5,7,9-11"};
        int[][] expected = {{1,2,3,4,5,7,9,10,11}};
        Assert.assertArrayEquals(expected, PortUtil.getSequencesNumber(input));
    }

    @Test
    public void testGetSequenceNumberSimpleTwo(){
        String[] input = {"1,3,4,5", "3,4"};
        int[][] expected = {{1,3,4,5}, {3,4}};
        Assert.assertArrayEquals(expected, PortUtil.getSequencesNumber(input));
    }

    @Test
    public void testGetSequenceNumberFew(){
        String[] input = {"1,3-5", "2", "3-4"};
        int[][] expected = {{1,3,4,5}, {2}, {3,4}};
        Assert.assertArrayEquals(expected, PortUtil.getSequencesNumber(input));
    }

    @Test
    public void testGetSequenceNumberNull(){
        String[] input = null;
        int[][] expected = {{}};
        Assert.assertArrayEquals(expected, PortUtil.getSequencesNumber(input));
    }

    @Test
    public void testGetSequenceNumberEmpty(){
        String[] input = {""};
        int[][] expected = {{}};
        Assert.assertArrayEquals(expected, PortUtil.getSequencesNumber(input));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSequenceIllegalArgumentExceptionUnknownChar(){
        String[] input = {"1,3,5-7,8+9"};
        PortUtil.getSequencesNumber(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSequenceIllegalArgumentExceptionBeginNotDigit(){
        String[] input = {",3,5-7,8,9"};
        PortUtil.getSequencesNumber(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSequenceIllegalArgumentExceptionEndNotDigit(){
        String[] input = {"3,5-7,8,9-"};
        PortUtil.getSequencesNumber(input);
    }

    @Test
    public void testGetPairElementsSimpleOne(){
        int[][] input = {{1,3,4,5}};
        int[][] expected = {{1}, {3}, {4}, {5}};
        Assert.assertArrayEquals(expected, PortUtil.getPairElements(input));
    }

    @Test
    public void testGetPairElementsSimpleTwo(){
        int[][] input = {{1,3,4,5}, {3,4}};
        int[][] expected = {{1,3}, {1,4}, {3, 3}, {3, 4}, {4, 3}, {4, 4}, {5, 3}, {5, 4}};
        Assert.assertArrayEquals(expected, PortUtil.getPairElements(input));
    }

    @Test
    public void testGetPairElementsFew(){
        int[][] input = {{1,3,4,5}, {2}, {3,4}};
        int[][] expected = {{1, 2, 3}, {1, 2, 4}, {3, 2, 3}, {3, 2, 4}, {4, 2, 3}, {4, 2, 4}, {5, 2, 3}, {5, 2, 4}};
        Assert.assertArrayEquals(expected, PortUtil.getPairElements(input));
    }

    @Test
    public void testGetPairElementsNull(){
        int[][] input = null;
        int[][] expected = {{}};
        Assert.assertArrayEquals(expected, PortUtil.getPairElements(input));
    }

    @Test
    public void testGetPairElementsEmpty(){
        int[][] input = {{}};
        int[][] expected = {{}};
        Assert.assertArrayEquals(expected, PortUtil.getPairElements(input));
    }
}
