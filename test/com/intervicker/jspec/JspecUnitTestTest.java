package com.intervicker.jspec;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.intervicker.jspec.JspecUnitTest;

public class JspecUnitTestTest {
   @Test
   public void testShowNumberOfErrorsAndFailingTests() throws Exception {
      String url = "file:////d:/projects/jspec-assertions/WebContent/failingTest.html";
      JspecUnitTest unitTest = new JspecUnitTest();
      
      try {
    	  unitTest.assertPasses(url);
      } catch(AssertionError e) {
    	  assertTrue("Number of errors message is incorrect", e.getMessage().contains("JSpec Test has 2 errors:"));
    	  assertTrue("Failing test not found", e.getMessage().contains("should load calendar"));
    	  assertTrue("Failing test not found", e.getMessage().contains("should select the first dealer in the list"));
      }
   }
   
   @Test
   public void passWhenNoFailingTests() throws Exception {
	  String url = "file:////d:/projects/jspec-assertions/WebContent/passingTest.html";
      JspecUnitTest unitTest = new JspecUnitTest();
      
      try {
    	  unitTest.assertPasses(url);
      }catch(AssertionError e) {
    	  fail("assertion should not have failed");
      }
   }
}
