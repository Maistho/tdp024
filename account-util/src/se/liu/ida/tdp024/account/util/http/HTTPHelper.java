package se.liu.ida.tdp024.account.util.http;

public interface HTTPHelper {
    public class HTTPHelperConnectionException extends Exception {
        public HTTPHelperConnectionException(String message) {
            super(message);
        }
    }
    public class HTTPHelperMalformedURLException extends Exception {
        public HTTPHelperMalformedURLException(String message) {
            super(message);
        }
    }
    public String get(String endpoint, String... parameters)
            throws HTTPHelperConnectionException,
            HTTPHelperMalformedURLException;
    public String postJSON(String endpoint, String[] queryParameters, String[] dataParameters)
            throws HTTPHelperConnectionException,
            HTTPHelperMalformedURLException;
}
