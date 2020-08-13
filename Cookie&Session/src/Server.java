import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    // 端口号
    private static final int PORT = 9999;
    // 统一编码
    private static final String CHARSET = "UTF-8";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        ExecutorService POOL = Executors.newCachedThreadPool();
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                POOL.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Request request = Request.build(socket.getInputStream());
                            Response response = Response.build(socket.getOutputStream());

                            PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                                    socket.getOutputStream(), CHARSET), true);

                            if ("/307".equals(request.getUri())) {
                                response.setStatus(307);
                                response.setMessage("Temporary Redirect");
                                response.addHeader("Location", "http://45.40.254.164");
                                response.println("");
                            } else if ("/404".equals(request.getUri())) {
                                response.setStatus(404);
                                response.setMessage("Not Found");
                                response.println("<h1>没有找到资源</h1>");
                            } else if ("/setCookie".equals(request.getUri())) {
                                response.setStatus(200);
                                response.setMessage("OK");
                                response.addHeader("Set-Cookie", "studentid=" + UUID.randomUUID());
                                response.println("<h1>Set Cookie OK</h1>");
                            } else {
                                response.setStatus(200);
                                response.setMessage("OK");
                                response.println("<h1>My Http Server</h1>");
                            }
                            response.flush();
                            socket.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}