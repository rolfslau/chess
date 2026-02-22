import server.Server;
//import service.Service;
import dataaccess.MemoryUserDAO;

public class Main {
    public static void main(String[] args) {

        MemoryUserDAO dataAccess = new MemoryUserDAO();
        // variable for service
        // var service = new Service(dataAccess);
        Server server = new Server();
        // var server = new Server(service).run(port);
        server.run(8080);

        System.out.println("♕ 240 Chess Server");
    }
}

