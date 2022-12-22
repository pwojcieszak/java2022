package pl.edu.uj.sender;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;

public class EmailMessage extends Message {

    final long emailMessageId;
    private final String messageTitle;
    private final String messageBody;
    final Timestamp creationDate;

    public EmailMessage(long emailMessageId, String messageTitle, String messageBody, Timestamp creationDate) {
        this.emailMessageId = emailMessageId;
        this.messageTitle = messageTitle;
        this.messageBody = messageBody;
        this.creationDate = creationDate;
    }

    public EmailMessage(String messageTitle, String messageBody) {
        this.emailMessageId = 0;
        this.messageTitle = messageTitle;
        this.messageBody = messageBody;
        this.creationDate = null;
    }

    @Override
    public String getMessageTitle() {
        return messageTitle;
    }

    @Override
    public String getMessageBody() {
        return messageBody;
    }


    public void validateMessage() throws SenderException {
        if (StringUtils.isEmpty(messageTitle) || StringUtils.isEmpty(messageBody))
            throw new SenderException("messsageTitle i/lub messageBody sa niepoprawne");
    }
}
