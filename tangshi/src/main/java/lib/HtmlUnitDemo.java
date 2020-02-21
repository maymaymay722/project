package lib;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.File;
import java.io.IOException;
import java.util.List;

//列表页下提取Demo
public class HtmlUnitDemo {

    /*
    public static void main(String[] args) throws IOException {
        //HtmlUnit无界面的浏览器(HTTP 客户端)
        //用WebClient创建一个浏览器类
        //BrowserVersion.CHROME 模拟的是Chrome版本的浏览器
        WebClient webClient=new WebClient(BrowserVersion.CHROME);

        //关闭了浏览器的 js 执行引擎，不再执行网页中的 js 脚本
        webClient.getOptions().setJavaScriptEnabled(false);
        //关闭了浏览器的 css 执行引擎，不再执行网页中的 css 布局
        webClient.getOptions().setCssEnabled(false);

        //webClient.getPage(...) 可以对页面进行请求了
        //抛出 IO异常
        HtmlPage page=webClient.getPage("https://so.gushiwen.org/gushi/tangshi.aspx");
        System.out.println(page);
        //通过抓包确认/验证一下结果
        //通过 域名 得到 IP 地址
        //ping so.gushiwen.org(只能得到其中一个 IP 地址)
        //nslookup so.gushiwen.org(能得到所有 IP 地址)

        //page.save(new File("唐诗三百首\\列表页.html"));//save 只能存一遍
        File file=new File("唐诗三百首\\列表页.html");
        file.delete();
        page.save(file);


        //如何从 html 中提取我们需要的信息
        HtmlElement body=page.getBody();
        List<HtmlElement> elements=body.getElementsByAttribute(
                "div",
                "class",
                "typecont");

        for(HtmlElement element:elements){
            System.out.println(element);
        }

        HtmlElement divElement = elements.get(0);//取第一个
        List<HtmlElement> aElements = divElement.getElementsByAttribute(
                "a",
                "target",
                "_blank");

        for (HtmlElement e : aElements) {
            System.out.println(e);
        }
        System.out.println(aElements.size());
        System.out.println(aElements.get(0).getAttribute("href"));
    }
    */

    public static void main(String[] args) throws IOException {
        try (WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);

            String url = "https://so.gushiwen.org/gushi/tangshi.aspx";
            HtmlPage page = webClient.getPage(url);
            HtmlElement body = page.getBody();
            List<HtmlElement> elements = body.getElementsByAttribute(
                    "div",
                    "class",
                    "typecont"
            );

            int count = 0;
            for (HtmlElement element : elements) {
                List<HtmlElement> aElements = element.getElementsByTagName("a");//拿到a标签
                for (HtmlElement a : aElements) {
                    System.out.println(a.getAttribute("href"));
                    count++;
                }
            }
            System.out.println(count);
        }
    }
}
