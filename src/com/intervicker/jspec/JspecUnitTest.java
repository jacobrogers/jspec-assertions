package com.intervicker.jspec;

import static org.junit.Assert.fail;

import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlEmphasis;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

public class JspecUnitTest {
   private WebClient webclient = new WebClient();

   public JspecUnitTest() {}
   
   public void assertPasses(String testLocation) throws Exception {
      HtmlPage page = webclient.getPage(testLocation);
      System.out.println(page.asXml());
      if(!page.asText().contains("Failures: 0")) {
         fail(buildErrorMessage(page));
      }
   }
   @SuppressWarnings("unchecked")
   private String buildErrorMessage(HtmlPage page) {
      String failureCount = ((HtmlEmphasis)page.getByXPath("//span[@class=\"failures\"]/em").get(0)).asText();
      List<HtmlTableDataCell> failureCells = (List<HtmlTableDataCell>) page.getByXPath("//td[@class=\"fail\"]");
      String errorMessage = "JSpec Test has " +failureCount+ " errors:";
      for(HtmlTableDataCell failureCell : failureCells) {
         errorMessage += "\n" + failureCell.asText();
      }
      return errorMessage;
   }

   protected void setWebClient(WebClient webclient) {
      this.webclient = webclient;
   }
}
