package se.liu.ida.tdp024.account.util.logger;

public interface AccountLogger {

    enum AccountLoggerLevel {

        DEBUG,
        INFO,
        NOTIFY,
        WARNING,
        ERROR,
        CRITICAL,
        ALERT,
        EMERGENCY
    }

    public void log(Throwable throwable);

    public void log(AccountLoggerLevel todoLoggerLevel, String shortMessage, String longMessage);

}
