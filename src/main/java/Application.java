import server.TCPServer;

public class Application {
    public static void main(String[] args) throws Exception {
        new TCPServer().start();
    }
}
