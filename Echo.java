import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

import static com.sun.net.httpserver.HttpServer.create;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class Echo {

    public static void main(String[] args) throws IOException {
        final var httpServer = create(new InetSocketAddress("localhost", 8080), 0);

        httpServer.createContext("/echo", new EchoHandler());
        httpServer.setExecutor(newFixedThreadPool(10));
        httpServer.start();
    }

    public static class EchoHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                final var response = exchange.getRequestURI().toString();
                final var outStream = exchange.getResponseBody();

                exchange.sendResponseHeaders(200, response.length());
                outStream.write(response.getBytes());
                outStream.flush();
                outStream.close();
            }
        }

    }

}
