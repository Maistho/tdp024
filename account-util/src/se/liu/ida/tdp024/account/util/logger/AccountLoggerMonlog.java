package se.liu.ida.tdp024.account.util.logger;

import java.util.Date;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelper.HTTPHelperConnectionException;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;

public class AccountLoggerMonlog implements AccountLogger {

    private static final HTTPHelper httpHelper = new HTTPHelperImpl();

    //-- Set your API key here ----------//
    private static final String apikey = "4c938a78d76a0c5559ecd32e2cedf69ab913cf92";
    //-----------------------------------//
    private static final String endpoint = "http://www.ida.liu.se/~TDP024/monlog/api/log/";

    @Override
    public void log(Throwable throwable) {

        StringBuilder stackTrace = new StringBuilder();
        for (StackTraceElement ste : throwable.getStackTrace()) {
            stackTrace.append(ste.toString()).append("\n");
        }

        try {
            httpHelper.postJSON(
                    endpoint,
                    new String[]{"api_key", apikey, "format", "json"},
                    new String[]{
                        "severity", "5",
                        "short_desc", throwable.getMessage(),
                        "long_desc", stackTrace.toString(),
                        "timestamp", new Date().getTime() + ""});
        } catch (HTTPHelperConnectionException e) {

        } catch (HTTPHelper.HTTPHelperMalformedURLException e) {

        }
        System.out.println(throwable.getMessage() + stackTrace.toString());

    }

    @Override
    public void log(AccountLoggerLevel todoLoggerLevel, String shortMessage, String longMessage) {

        try {
            httpHelper.postJSON(
                    endpoint,
                    new String[]{"api_key", apikey, "format", "json"},
                    new String[]{
                        "severity", todoLoggerLevel.ordinal() + "",
                        "short_desc", shortMessage,
                        "long_desc", longMessage,
                        "timestamp", new Date().getTime() + ""});
        } catch (HTTPHelperConnectionException e) {

        } catch (HTTPHelper.HTTPHelperMalformedURLException e) {

        }
        System.out.println(shortMessage + longMessage);
    }
}
