package test;

import http.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    private HttpParser httpParser;

    @BeforeAll
    public void beforeTest() {
        httpParser = new HttpParser();
    }

    @Test
    void parseHttpRequest() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(generateValidGETTestCase());
        } catch (HttpParsingException e) {
            fail(e); //Should fail for non implemented methods / bad methods
        }
        assertNotNull(request);
        assertEquals(request.getMethod(), HttpMethod.GET);
        assertEquals(request.getRequestTarget(), "/");
        assertEquals(request.getOriginalHttpVersion(), "HTTP/1.1");
        assertEquals(request.getBestCompatibleVersion(), HttpVersion.HTTP_1_1);
    }

    @Test
    void parseBadHttpRequest() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(generateBadMethodNameTestCase());
            fail(); //Should fail on the non capitalized GeT
        } catch (HttpParsingException e) {
            Assertions.assertEquals(e.getErrorCode(), HttpStatusCodes.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseBadHttpRequest2() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(generateBadMethodNameTestCase2());
            fail(); //Should fail on the length of method
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCodes.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseBadRequestLineNumberOfItems() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(generateBadRequestLineInvalidNumberOfItems());
            fail(); //Should fail on the length of method
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCodes.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    @Test
    void parseEmptyRequestLine() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(generateEmptyRequestLine());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCodes.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    @Test
    void parseOnlyCRnoLF() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(generateOnlyCRnoLF());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCodes.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    private InputStream generateValidGETTestCase() {
        String rawData = "GET / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "sec-ch-ua: \"Google Chrome\";v=\"141\", \"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"141\"\r\n" +
                "sec-ch-ua-mobile: ?0\r\n" +
                "sec-ch-ua-platform: \"Windows\"\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/141.0.0.0 Safari/537.36\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigater\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Accept-Encoding: gzip, deflate, br, zstd\r\n" +
                "Accept-Language: en-US,en;q=0.9\r\n"
                 + "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );
        return inputStream;
    }

    private InputStream generateBadMethodNameTestCase() {
        String rawData = "GeT / HTTP/1.1\r\n" + //GET is case senseitive should fail for GeT
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9\r\n"
                + "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );
        return inputStream;
    }

    private InputStream generateBadMethodNameTestCase2() {
        String rawData = "GETTTTT / HTTP/1.1\r\n" + //GET is case senseitive should fail for GeT
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9\r\n"
                + "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );
        return inputStream;
    }

    private InputStream generateBadRequestLineInvalidNumberOfItems() {
        String rawData = "GET / AAAAAAA HTTP/1.1\r\n" + //GET is case senseitive should fail for GeT
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9\r\n"
                + "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );
        return inputStream;
    }

    private InputStream generateEmptyRequestLine() {
        String rawData = "\r\n" + //GET is case senseitive should fail for GeT
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9\r\n"
                + "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );
        return inputStream;
    }

    private InputStream generateOnlyCRnoLF() {
        String rawData = "GET / AAAAAAA HTTP/1.1\r" + //NO '\n' new line feed included
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9\r\n"
                + "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );
        return inputStream;
    }
}