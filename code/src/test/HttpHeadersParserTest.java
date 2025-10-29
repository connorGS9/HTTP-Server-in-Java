package test;

import http.HttpParser;
import http.HttpRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HttpHeadersParserTest {

    private HttpParser httpParser;
    private Method parseHeadersMethod;

    @BeforeAll
    public void beforeClass() throws NoSuchMethodException{
        httpParser = new HttpParser();
        Class<HttpParser> cls = HttpParser.class;
        parseHeadersMethod = cls.getDeclaredMethod("parseHeaders", InputStreamReader.class, HttpRequest.class);
        parseHeadersMethod.setAccessible(true);
    }

    @Test
    public void testPrivateMethod() throws InvocationTargetException, IllegalAccessException {
        parseHeadersMethod.invoke(httpParser, new InputStreamReader(new ByteArrayInputStream("".getBytes())), new HttpRequest());
    }

    @Test
    public void testSimpleSingleHeader() throws InvocationTargetException, IllegalAccessException {
        HttpRequest request = new HttpRequest();
        parseHeadersMethod.invoke(httpParser, generateSimpleSingleHeaderMessage(), request);
    }


    private InputStreamReader generateSimpleSingleHeaderMessage() {
        String rawData = "Host: localhost8080\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        return reader;
    }
}
