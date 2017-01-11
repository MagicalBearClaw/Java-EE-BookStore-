package ca.pacmabooks.client.business;

import ca.pacmabooks.client.business.interfaces.ICreditCardValidator;


/**
 * THis class is used to validate a credit card number using the mod 10 luth
 * algorithm.
 * 
 * @author Michael McMahon(1330815)
 * @version 1.0
 * @since 1.8
 * 1/30/16
 */
public class CreditCardValidator implements ICreditCardValidator
{
    private String[] startingNumbers;
    private int minLength;
    private int maxLength;
    
    /**
     * Creates a new Credit card validator.
     * @authour Michael Mcmahon
     * @param startingNumbers the starting numbers from a credit card.
     * @param minLength the minimum length for a credit card number
     * @param maxLength  the maximum length of a credit card.
     */
    public CreditCardValidator(String[] startingNumbers, int minLength, int maxLength)
    {
        this.startingNumbers = startingNumbers;
        this.maxLength = maxLength;
        this.minLength = minLength;
    }
    
    /**
     * validates a credit card number.
     * @authour Michael Mcmahon
     * @param number
     * @return 
     */
    @Override
    public boolean validate(String number) 
    {
        if(number == null)
            return false;
        
        if(number.equals(""))
            return false;
        

        
        String trimed = number.trim();
        trimed = number.replaceAll("\\s+", "");
        
        int numberLength = trimed.length();
        
        if(!isNumberInValidRange(trimed, minLength, maxLength))
            return false;
        
        
        int[] creditNumbers = new int[numberLength];
        long cc = 0;
        try
        {
           cc = Long.parseLong(trimed);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        int cnt = 0;
        while(cc > 10)
        {
           creditNumbers[cnt] = (int)(cc % 10);
           cc /= 10;
           cnt++;
        }
        creditNumbers[cnt] = (int)(cc % 10);

        int[] reversed = reverseArray(creditNumbers);
        
        if(!isNumberStartingWithNumbers(trimed , startingNumbers))
            return false;        
        return checkLastDigit(reversed);
               
    }
    
    /**
     * Determines if a credit card number is within the 
     * valid range of characters.
     * @param number
     * @param min
     * @param max
     * @return 
     */
    private boolean isNumberInValidRange(String number, int min, int max)
    {
        return number.length() <= max || number.length() > min;
        
    }
    
    /**
     * Depending on the type of credit card, the starting numbers will be different
     * this method determines if those number are indeed the first numbers in the 
     * number provided.
     * @authour Michael Mcmahon
     * @param number
     * @param numbers
     * @return 
     */
    private boolean isNumberStartingWithNumbers(String number, String[] numbers)
    {
        int lenght = startingNumbers.length;
        boolean result = false;
        
        for (int i = 0; i < lenght; i++) 
        {
            if(number.startsWith(numbers[i]))
            {
                result = true;
                break;
            }
        }
                
        return result;
    }
    
    /**
     * Determines if the last digit is the same as the
     * result from the algorithm.
     * @authour Michael Mcmahon
     * @param numbers
     * @return 
     */
    private boolean checkLastDigit(int[] numbers)
    {
        int lastDigit =  numbers[numbers.length -1];
        int[] reversed = reverseArray(numbers);
        
        int digitsLenght = reversed.length;
        int total = 0;
        boolean timesTwo = true;
        for (int i = 1; i < digitsLenght; i++) 
        {
            if(timesTwo)
            {
                reversed[i] =  reversed[i] * 2;

            }
                if(reversed[i] > 9)
                    reversed[i] =  reversed[i] - 9;
            
            total += reversed[i];
            timesTwo = !timesTwo;
        }
        int foundLastDigit = ((total* 9) % 10);
        return lastDigit == foundLastDigit;
    }
    
    /**
     * Reverses an int array
     * @authour Michael Mcmahon
     * @param array
     * @return 
     */
    private int[] reverseArray(int[] array)
    {
        int reverse[] =  new int[array.length];
        int start = array.length -1;
        for (int i = start, j = 0; i >= 0; i--, j++) 
        {
            reverse[j] = array[i];
        }
        return reverse;
    }
    
}
