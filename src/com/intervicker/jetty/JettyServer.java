package com.intervicker.jetty;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class JettyServer {
   Server server = new Server();
   
   public JettyServer() {}
   
   public void start(int port, String contextPath, String war) throws Exception {
      Connector connector = new SelectChannelConnector();
      connector.setPort(port);
      server.addConnector(connector);

      WebAppContext wac = new WebAppContext();
      wac.setContextPath(contextPath);
      wac.setWar(war); 
      server.setHandler(wac);

      server.setStopAtShutdown(true);

      server.start();
   }
   public void stop() throws Exception {
      server.stop();
   }
   protected void setServer(Server server) {
      this.server = server;
   }
}
