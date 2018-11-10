import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class EmbeddedJettyMain {

    public static void main(String[] args) {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler(server,"/result");
        handler.addServlet(MyServlet.class,"/");
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}