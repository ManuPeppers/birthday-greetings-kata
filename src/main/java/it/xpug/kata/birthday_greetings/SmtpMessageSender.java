package it.xpug.kata.birthday_greetings;

public class SmtpMessageSender {

    private final int smtpPort;
    private final String smtpHost;

    public SmtpMessageSender(String smtpHost, int smtpPort) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public String getSmtpHost() {
        return smtpHost;
    }
}
