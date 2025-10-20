package http;

import core.HttpConnectionWorkerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {
    private Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);
    private static int SP = 0x20; //Hexadecimal for space, carriage return, and line feed
    private static int CR = 0x0D;
    private static int LF = 0x0A;

    public HttpRequest parseHttpRequest(InputStream inputStream) {
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII); //Encodes byte stream into char stream
        HttpRequest request = new HttpRequest(); //Model our request object
        try {
            parseRequestLine(reader, request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        parseHeaders(reader, request);
        parseBody(reader, request);

        return request;
    }

    private void parseRequestLine(InputStreamReader reader, HttpRequest request) throws IOException {
        StringBuilder processingDataBuffer = new StringBuilder();
        int _byte;
        boolean methodParsed = false, requestTargetParsed = false;

        while ( (_byte = reader.read()) >= 0) { //Read the request
            if (_byte == CR) { //If we find a carriage return char (hexa integer)
                _byte = reader.read(); //Read the next hexa char
                if (_byte == LF) { //If we then find a line feed, return
                    LOGGER.info("HTTP Version: {}", processingDataBuffer.toString());
                    return;
                }
            }
            if (_byte == SP) {
                if (!methodParsed) {
                    LOGGER.info("Request line METHOD to process: {}", processingDataBuffer.toString());
                    request.setMethod(processingDataBuffer.toString());
                    methodParsed = true;
                } else if (!requestTargetParsed) {
                    LOGGER.info("Request line REQUEST TARGET to process: {}", processingDataBuffer.toString());
                    requestTargetParsed = true;
                }
                processingDataBuffer.delete(0, processingDataBuffer.length());
            } else { //Append the curr char
                processingDataBuffer.append((char)_byte);
            }
        }
    }

    private void parseHeaders(InputStreamReader reader, HttpRequest request) {

    }

    private void parseBody(InputStreamReader reader, HttpRequest request) {

    }



}
