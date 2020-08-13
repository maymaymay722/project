import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Request {
    // 请求方法
    private String method;
    // 请求路径
    private String uri;
    // HTTP版本
    private String version;
    // 请求头
    private Map<String,String> headers=new HashMap<>();
    // 请求参数
    private Map<String,String> parameters=new HashMap<>();
    // 统一编码
    private static final String CHARSET="UTF-8";

    private Request(){

    }

    // 解析 Http 请求并生成请求对象
    public static Request build(InputStream is) throws Exception {
        Request request=new Request();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is,CHARSET));

        // 处理请求行
        String[] requestLineArray = reader.readLine().split(" ");
        request.setMethod(requestLineArray[0]);
        request.setUri(requestLineArray[1]);
        request.setVersion(requestLineArray[2]);

        // 处理请求头
        String requestHeader;
        while ((requestHeader = reader.readLine()) != null && requestHeader.length() != 0){
            String[] headerArray = requestHeader.split(":");
            request.addHeader(headerArray[0].trim(), headerArray[1].trim());
        }
        return request;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addParameter(String key, String value) {
        parameters.put(key, value);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static String getCHARSET() {
        return CHARSET;
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", version='" + version + '\'' +
                ", headers=" + headers +
                ", parameters=" + parameters +
                '}';
    }
}
