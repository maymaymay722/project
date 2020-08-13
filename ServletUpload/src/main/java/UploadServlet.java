import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 文件上传，不能用 GET 方法
        System.out.println("上传文件只能用 POST 方法！");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 具体上传上来的文件放在什么地方，由自己决定
        File path=new File("E:\\Code\\Java\\hellobitidea\\");
        // 获取文件，文件在 html 中的 name 是“file”
        Part img = req.getPart("file");
        //制作文件全路径
        String filePath = path.getPath()+File.separator + img.getSubmittedFileName();
        //获取成功之后，写入指定路径
        img.write(filePath);
        //显示到标准输出
        System.out.println("file Upload: " + filePath);
        //同样的信息，显示给用户浏览器
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("file Upload: " + filePath);
    }
}
