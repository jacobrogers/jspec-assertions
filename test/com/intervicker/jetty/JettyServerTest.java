package com.intervicker.jetty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

import com.intervicker.jetty.JettyServer;

public class JettyServerTest {
   private JettyServer server;
   private MockServer mockServer;
   
   @Before
   public void setup() {
      server = new JettyServer();
      mockServer = new MockServer();
      server.setServer(mockServer);
   }
   
   @Test
   public void setPortOnServer() throws Exception {
      server.start(1234, null, null);
      
      assertTrue(mockServer.connectorReceived instanceof SelectChannelConnector);
      assertEquals(1234, mockServer.connectorReceived.getPort());
   }
   
   @Test
   public void setContextInfoOnServer() throws Exception {
      server.start(1234, "contextPath", "war");
      
      assertTrue(mockServer.handlerReceived instanceof WebAppContext);
      assertEquals("contextPath", ((WebAppContext)mockServer.handlerReceived).getContextPath());
      assertEquals("war", ((WebAppContext)mockServer.handlerReceived).getWar());
   }
   @Test
   public void makeServerStopAtShutdown() throws Exception {
      server.start(1234, "contextPath", "war");
      
      assertTrue(mockServer.stopAtShutdown);
   }
   
   public static class MockServer extends Server {
      public Connector connectorReceived;
      public Handler handlerReceived;
      private boolean stopAtShutdown;
      
      @Override
      public void addConnector(Connector connector) {
         this.connectorReceived = connector;
      }
      @Override
      public void setHandler(Handler handler) {
         this.handlerReceived = handler;
      }
      @Override
      public void setStopAtShutdown(boolean stop) {
         this.stopAtShutdown = stop;
      }
   }
}
