package se.liu.ida.tdp024.account.util.logger;

/*
 * 
 * This is an extremly simple implemenation of logger,
 * one should really consider writing a new one where
 * you implement a much bettern way of persisting logs.
 * An example would be using REST calls to Monlog.
 * 
 */
public class AccountLoggerImpl implements AccountLogger {

    @Override
    public void log(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void log(AccountLoggerLevel todoLoggerLevel, String shortMessage, String longMessage) {
        if (todoLoggerLevel == AccountLoggerLevel.CRITICAL || todoLoggerLevel == AccountLoggerLevel.ERROR) {
            System.err.println(shortMessage);
            System.err.println(longMessage);
        } else {
            System.out.println(shortMessage);
            System.out.println(longMessage);
        }
    }
}
