package ua.od.hackathon;

import org.eclipse.jetty.server.Server;
import ua.od.hackathon.config.AppContextConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationStarter {

    public static void main(String[] args) throws Exception{

        Server jettyServer;
        if (args != null && args.length > 0 && args[0].equals("deploy")) {
            DataBaseDeployer.createDb();
            DataBaseDeployer.executeSqlScripts();
            jettyServer = new Server(Integer.valueOf(System.getenv("PORT")));
        } else {
            jettyServer = new Server(9090);
        }

        boolean debug = false;
        jettyServer.setHandler(new AppContextConfig().getHandlersConfig());
        try {
            jettyServer.start();
            if(debug) jettyServer.dumpStdErr();
            jettyServer.join();
        } catch (Exception e) {
            Logger.getLogger(ApplicationStarter.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            jettyServer.destroy();
        }
//        boolean debug = false;
//        //jettyServer.setHandler(new AppContextConfig().getHandlersConfig());
//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
//        context.setContextPath("/");
//        jettyServer.setHandler(context);
//
//
//        ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
//        servletHolder.setInitOrder(0);
//        servletHolder.setInitParameter(PKG_PROVIDER, "controller");
//        try {
//            jettyServer.start();
//            if(debug) jettyServer.dumpStdErr();
//            jettyServer.join();
//        } catch (Exception e) {
//        } finally {
//            jettyServer.destroy();
//        }
    }
}
