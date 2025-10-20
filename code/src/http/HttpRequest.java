package http;

public class HttpRequest extends HttpMessage{

    private HttpMethod method;
    private String requestTarget;
    private String httpVersion;

    HttpRequest() { //Class level contstructor

    }

    public HttpMethod getMethod() {
        return method;
    }

    void setMethod(String methodName) {
        this.method = HttpMethod.valueOf(methodName);
    }
}
