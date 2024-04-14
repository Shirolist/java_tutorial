// Keypad.java
// Represents the keypad of the ATM

import java.util.Scanner;

public class Keypad
{
    @Deprecated
    private final Scanner input;
    private final InputHandler inputHandler;

    public Keypad()
    {
        input = new Scanner( System.in );
        inputHandler = new InputHandler();
    }

    public InputHandler getInputHandler()
    {
        return inputHandler;
    }

    // return an integer value entered by user
    @Deprecated
    public int getInput()
    {
        return input.nextInt();
    } // end method getInput

    @Deprecated
    // return a floating point value entered by user
    public double getFloatInput()
    {
        return input.nextDouble(); // we assume that user enters a floating point value
    } // end method getFloatInput

    @Deprecated
    // return a string entered by user
    public String getStringInput()
    {
        return input.next(); // we assume that user enters a string
    } // end method getStringInput

    @Deprecated
    // clean the input clean in case of exception
    public void cleanBuffer()
    {
        input.nextLine();
    } // end method cleanBuffer
} // end class Keypad