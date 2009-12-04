package com.intervicker.jspec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.intervicker.jetty.JettyServer;
import com.intervicker.jspec.JspecIntegrationTest;
import com.intervicker.jspec.JspecUnitTest;

public class JspecIntegrationTestTest {
   private JspecIntegrationTest jspecTest;
   private String contextPath = "/DealerBatchMonitor";
   private String war = "WebContent";
   private int port = 8883;
   private String pageUnderTest = "file:////d:/projects/jspec-assertions/WebContent/integrationTestPage.html";
   private JettyServer mockServer;
   private WebClient   mockWebClient;
   private JspecUnitTest mockJspecUnitTest;
   
   @Before
   public void setup() {
	   jspecTest = new JspecIntegrationTest(port, contextPath, war);
	   mockServer = mock(JettyServer.class);
	   mockWebClient = new WebClient();
	   mockJspecUnitTest = mock(JspecUnitTest.class);
	   jspecTest.setJettyServer(mockServer);
	   jspecTest.setWebClient(mockWebClient);
	   jspecTest.setJspecUnitTest(mockJspecUnitTest);
   }
   @Test
   public void testStartServer() throws Exception {
      jspecTest.assertPasses(pageUnderTest, "/test.txt", "jspecTestLocation");
      verify(mockServer).start(port, contextPath, war);
   }
   @Test
   public void disableJavascriptOnWebClient() throws Exception {
	   jspecTest.assertPasses(pageUnderTest, "/test.txt", "jspecTestLocation");
	   assertFalse(mockWebClient.isJavaScriptEnabled());
   }
   
   @Test
   public void writeBodyOfIntegrationTestPageToGivenLocation() throws Exception {
	   File integrationBodyFile = new File("/test.txt");
	   
	   if(integrationBodyFile.exists()) 
		   assertTrue(integrationBodyFile.delete());
	   
	   assertFalse(integrationBodyFile.exists());
	   
	   jspecTest.assertPasses(pageUnderTest, "/test.txt", "jspecTestLocation");
	   
	   assertTrue(integrationBodyFile.exists());
	   List<String> contents = FileUtils.readLines(integrationBodyFile);
	   assertTrue(5 == contents.size());
	   assertEquals("<body>", contents.get(0));
	   assertEquals("  <h1>", contents.get(1));
	   assertEquals("    This is the body!", contents.get(2));
	   assertEquals("  </h1>", contents.get(3));
	   assertEquals("</body>", contents.get(4));
   }
   @Test
   public void runJspecUnitTestForPageUnderTest() throws Exception {
	   jspecTest.assertPasses(pageUnderTest, "/test.txt", "jspecTestLocation");
	   verify(mockJspecUnitTest).assertPasses("jspecTestLocation");
   }
}
