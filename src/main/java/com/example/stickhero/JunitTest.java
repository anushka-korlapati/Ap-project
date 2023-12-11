//package com.example.stickhero;
//
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class JunitTest {
//    final double lowerBound=100.00;
//    final double upperBound=200.00;
//    @Test
//    public void Test1(){
//        double stickLength=145.66;
//        boolean response=MainController.checkInBounds(stickLength,lowerBound,upperBound);
//        assertTrue(response);
//    }
//    @Test
//    public void Test2(){
//        double stickLength=255.78;
//        boolean response=MainController.checkInBounds(stickLength,lowerBound,upperBound);
//        assertFalse(response);
//    }
//    @Test
//    public void Test3(){
//        double height1=23.67;
//        double height2=300.00;
//        boolean response=MainController.checkHeightsEquality(height1,height2);
//        assertEquals(response,false);
//    }
//}