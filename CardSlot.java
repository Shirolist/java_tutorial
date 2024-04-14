// CardSlot.java represents the card slot of the ATM

public class CardSlot
{
    private boolean hasCard = false;

    // simulate card validation after card insertion
    public boolean checkCardValidity()
    {
        return true;
    }

    // return if valid card is inserted
    public boolean hasCard()
    {
        return hasCard;
    }

    // sets boolean isInserted
    public void setHasCard( boolean hasCard )
    {
        this.hasCard = hasCard;
    }

    // resets the states of the card slot
    public void reset()
    {
        hasCard = false;
    }
}