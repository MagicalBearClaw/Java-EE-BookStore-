
package ca.pacmabooks.client.business;

import ca.pacmabooks.client.enums.CreditCardType;

/**
 * A Credit card factory to facilitate the creation of a credit card 
 * validation
 * @authour Michael Mcmahon
 */
public class CreditCardFactory 
{
    
    public CreditCardFactory()
    {
        
    }
    
    /**
     * return a credit card validator from the credit card type.
     * @authour Michael Mcmahon
     * @param ccType
     * @return 
     */
    public CreditCardValidator getCreditCardValidtor(CreditCardType ccType)
    {
        CreditCardValidator validator;
        switch(ccType)         
        {
            case AMEX:
                validator =  new CreditCardValidator(new String[]{"34","37"}, 15, 15);
                break;
            case VISA:
                 validator =  new CreditCardValidator(new String[]{"4"}, 13, 16);
                break;
            case MASTERCARD:
                 validator =  new CreditCardValidator(new String[]{"51", "52", "53", "54", "55"}, 16, 19);
                break;
            default:
                 validator =  new CreditCardValidator(new String[]{"4"}, 13, 16);
                break;
        }
        return validator;
    }
}
