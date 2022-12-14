package pl.edu.uj.sender;

import org.apache.commons.lang3.StringUtils;

public class EmailMessage extends Message {

    private final String messageTitle;
    private final String messageBody;

    public EmailMessage(String messageTitle, String messageBody) {
        this.messageTitle = messageTitle;
        this.messageBody = messageBody;
    }

    @Override
    public String getMessageTitle() {
        return messageTitle;
    }

    @Override
    public String getMessageBody() {
        return messageBody;
    }


    public void validateMessage() throws SenderException{
        if(StringUtils.isEmpty(messageTitle) || StringUtils.isEmpty(messageBody))
            throw new SenderException("messsageTitle i/lub messageBody sa niepoprawne");
    }
}
