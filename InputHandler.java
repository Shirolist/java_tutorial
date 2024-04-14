public class InputHandler
{
    private String buffer = null;
    private boolean boolBuffer;
    private Signal signal = Signal.PENDING;

//set method with getBuffer
    public String getBuffer()
    {
        return buffer;
    }

    public void setBuffer( String buffer )
    {
        this.buffer = buffer;
    }

    public boolean getBoolBuffer()
    {
        return boolBuffer;
    }

    public void setBoolBuffer( boolean boolBuffer )
    {
        this.boolBuffer = boolBuffer;
    }

    public Signal getSignal()
    {
        return signal;
    }

    public void setSignal( Signal signal )
    {
        this.signal = signal;
    }

    public void reset()
    {
        buffer = null;
        boolBuffer = false;
        signal = Signal.PENDING;
    }

    public enum Signal
    {
        INSERT_CARD,
        PROMPT_ACCOUNT_NUMBER,
        PROMPT_PIN,
        PROMPT_MENU_OPTION,
        PROMPT_WITHDRAWAL_MENU_OPTION,
        PROMPT_WITHDRAWAL_AMOUNT,
        PROMPT_TRANSFER_RECIPIENTS_ACCOUNT_NUMBER,
        PROMPT_TRANSFER_AMOUNT,
        PROMPT_CONFIRMATION,
        TAKE_CARD,
        TAKE_CASH,
        PENDING,
        ERROR
    }
}
