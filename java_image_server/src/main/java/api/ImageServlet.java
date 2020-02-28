package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.Image;
import dao.ImageDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ImageServlet extends HttpServlet {

    /**
     * 查看图片属性: 既能查看所有, 也能查看指定
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // req 对象中包含了请求中的所有信息
        // resp 对象要生成的结果就放到里面去
        // 当前这个 doGet 方法就是要根据要求，生成响应
        resp.setStatus(200);
        //这个代码就是把 hello 这个字符串放到 http 响应的 body 中了
        resp.getWriter().write("hello");
    }

    /**
     * 上传图片
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取图片的属性信息, 并且存入数据库
        //  a) 需要创建一个 factory 对象 和 upload 对象, 这是为了获取到图片属性做的准备工作
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        // b) 通过 upload 对象进一步解析请求(解析HTTP请求中奇怪的 body 中的内容)
        //    FileItem 就代表一个上传的文件对象.
        //    理论上来说, HTTP 支持一个请求中同时上传多个文件
        List<FileItem> items = null;
        try {
            items = upload.parseRequest(req);
        } catch (FileUploadException e) {
            // 出现异常说明解析出错!
            e.printStackTrace();
            // 告诉客户端出现的具体的错误是啥
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().write("{ \"ok\": false, \"reason\": \"请求解析失败\" }");
            return;
        }

        //  c) 把 FileItem 中的属性提取出来, 转换成 Image 对象, 才能存到数据库中
        //     当前只考虑一张图片的情况
        FileItem fileItem = items.get(0);
        Image image = new Image();
        image.setImageName(fileItem.getName());
        image.setSize((int)fileItem.getSize());
        // 手动获取一下当前日期, 并转成格式化日期, yyMMdd
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        image.setUploadTime(simpleDateFormat.format(new Date()));
        image.setContentType(fileItem.getContentType());
        image.setMd5(DigestUtils.md5Hex(fileItem.get()));
        // 自己构造一个路径来保存, 引入时间戳是为了让文件路径能够唯一
        image.setPath("./image/" + image.getMd5());
        // 存到数据库中
        ImageDao imageDao = new ImageDao();

        // 看看数据库中是否存在相同的 MD5 值的图片, 不存在, 返回 null
        Image existImage = imageDao.selectByMd5(image.getMd5());

        imageDao.insert(image);

        // 2. 获取图片的内容信息, 并且写入磁盘文件
        if (existImage == null) {
            File file = new File(image.getPath());
            try {
                fileItem.write(file);
            } catch (Exception e) {
                e.printStackTrace();

                resp.setContentType("application/json; charset=utf-8");
                resp.getWriter().write("{ \"ok\": false, \"reason\": \"写磁盘失败\" }");
                return;
            }
        }

        // 3. 给客户端返回一个结果数据
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().write("{ \"ok\": true }");
        //resp.sendRedirect("index.html");

        //resp.setContentType("text/html; charset=utf-8");
        //resp.setStatus(200);
        //resp.getWriter().write("加油");
    }

    /**
     * 删除指定图片
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}