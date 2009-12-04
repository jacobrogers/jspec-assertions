package com.intervicker.jspec;

import java.io.FileWriter;
import java.io.PrintWriter;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlBody;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.intervicker.jetty.JettyServer;

public class JspecIntegrationTest {
   private JettyServer server = new JettyServer();
   private JspecUnitTest jspecUnitTest = new JspecUnitTest();
   private WebClient  webclient = new WebClient();
   private final String warLocation;
   private final String contextPath;
   private final int port;
   
   public JspecIntegrationTest(int port, String contextPath, String warLocation) {
      this.port = port;
      this.contextPath = contextPath;
      this.warLocation = warLocation;
   }
   
   public void assertPasses(String pageUnderTestUrl, String fileToWriteBodyTo, String jspecTestLocationUrl) throws Exception {
      String body = getPageBody(pageUnderTestUrl);
      
      PrintWriter writer = new PrintWriter(new FileWriter(fileToWriteBodyTo));
      writer.write(body);
      writer.flush();
      writer.close();
      
      jspecUnitTest.assertPasses(jspecTestLocationUrl);
   }
   private String getPageBody(String url) throws Exception {
      server.start(port, contextPath, warLocation);
      webclient.setJavaScriptEnabled(false);

      HtmlPage page = webclient.getPage(url);
      server.stop();
      return ((HtmlBody)page.getElementsByTagName("body").item(0)).asXml();
   }

   public void setJettyServer(JettyServer mockServer) {
      this.server = mockServer;
   }

   protected void setWebClient(WebClient mockWebClient) {
	  this.webclient = mockWebClient;
   }

   protected void setJspecUnitTest(JspecUnitTest jspecUnitTest) {
	  this.jspecUnitTest = jspecUnitTest;
   }
}
   
   
