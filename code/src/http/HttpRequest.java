package http;

public class HttpRequest extends HttpMessage{

    private HttpMethod method;
    private String requestTarget;
    private String originalHttpVersion;
    private HttpVersion bestCompatibleVersion;

    public HttpRequest() { //Class level constructor

    }

    public HttpMethod getMethod() {
        return method;
    }

    void setMethod(String methodName) throws HttpParsingException {
        for (HttpMethod method : HttpMethod.values()) {
            if (methodName.equals(method.name())) {
                this.method = HttpMethod.valueOf(methodName);
                return;
            }
        }
        throw new HttpParsingException(HttpStatusCodes.SERVER_ERROR_501_NOT_IMPLEMENTED);
    }


    public String getRequestTarget() {
        return requestTarget;
    }

    void setRequestTarget(String requestTarget) throws HttpParsingException {
        if (requestTarget.isEmpty() || requestTarget.length() == 0) {
            throw new HttpParsingException(HttpStatusCodes.CLIENT_ERROR_400_BAD_REQUEST);
        }
        this.requestTarget = requestTarget;
    }

    public String getOriginalHttpVersion() {
        return originalHttpVersion;
    }

    public void setOriginalHttpVersion(String originalHttpVersion) {
        this.originalHttpVersion = originalHttpVersion;
    }

    public HttpVersion getBestCompatibleVersion() {
        return bestCompatibleVersion;
    }

    public void setHttpVersion(String originalHttpVersion) throws BadHttpVersionException, HttpParsingException {
        this.originalHttpVersion = originalHttpVersion;
        this.bestCompatibleVersion = HttpVersion.getBestCompatibleVersion(originalHttpVersion);

        if (this.bestCompatibleVersion == null) {
            throw new HttpParsingException(HttpStatusCodes.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);
        }
    }

}
