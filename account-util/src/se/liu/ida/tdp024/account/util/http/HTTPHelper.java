package se.liu.ida.tdp024.account.util.http;

public interface HTTPHelper {
    public class HTTPException extends Exception {
        public HTTPException(String message) {
            super(message);
        }
    }
    public String get(String endpoint, String... parameters) throws HTTPException;
    public String postJSON(String endpoint, String[] queryParameters, String[] dataParameters) throws HTTPException;
}
