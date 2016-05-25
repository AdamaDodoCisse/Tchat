
import com.socket.component.SocketClient;
import com.socket.component.SocketServer;

public class Main {
    static int cpt = 0;

    public static void  main(String [] args) throws Exception{
        if (args.length > 0) {

            if (args[0].equalsIgnoreCase("-c"))
            {
                SocketClient client = new SocketClient("127.0.0.1", 8000);

                client.on("new.client", o -> {
                    cpt++;
                    System.out.println("client " + cpt);
                });

                client.run();
            } else {

                SocketServer s = new SocketServer(8000);
                s.addListener((server1, client) ->
                        server1.emitBroadcast("new.client", null, client)
                );
                s.launch();
            }
        }
    }
}
