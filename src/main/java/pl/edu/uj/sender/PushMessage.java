package pl.edu.uj.sender;

import org.apache.commons.lang3.StringUtils;

public class PushMessage extends Message {
    private final String messageTitle;
    private final String messageBody;

    public PushMessage(String messageTitle, String messageBody) {
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

    // TODO zaimplementuj validateMessage()
    //  Sprawdź, czy messageTitle i messageBody
    //  są poprawne, np. nie puste. Możesz użyć np StringUtils.isEmpty(...)
    //  W przypadku błędu, rzuć wyjątkiem.
    public void validateMessage() throws SenderException{
        if(StringUtils.isEmpty(messageTitle) || StringUtils.isEmpty(messageBody) || messageBody.length()>=256)
            throw new SenderException("messsageTitle i/lub messageBody sa niepoprawne");
    }

}
