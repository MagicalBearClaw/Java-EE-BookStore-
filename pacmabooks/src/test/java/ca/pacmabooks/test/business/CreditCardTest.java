
package ca.pacmabooks.test.business;

import ca.pacmabooks.client.business.CreditCardFactory;
import ca.pacmabooks.client.business.CreditCardValidator;
import ca.pacmabooks.client.enums.CreditCardType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Test the Credit Card validation class  
 * @author michael McMahon
 */
@RunWith(Arquillian.class)
public class CreditCardTest 
{
        @Deployment
    public static WebArchive deploy() {

        // Use an alternative to the JUnit assert library called AssertJ
        // Need to reference MySQL driver as it is not part of either
        // embedded or remote
        final File[] dependencies = Maven
                .resolver()
                .loadPomFromFile("pom.xml")
                .resolve("mysql:mysql-connector-java",
                        "org.assertj:assertj-core").withoutTransitivity()
                .asFile();

        // The webArchive is the special packaging of your project
        // so that only the test cases run on the server or embedded
        // container
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addPackage(CreditCardFactory.class.getPackage())
                .addPackage(CreditCardValidator.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/glassfish-resources.xml"), "glassfish-resources.xml")
                .addAsResource("pacmabooks.sql")
                .addAsLibraries(dependencies);

        return webArchive;
    }
    
    @Inject
    CreditCardFactory cFactory;
    
    /**
     * Test a valid visa credit card
     *@author michael McMahon
     */
    @Test
    public void testValidVisaCreditCard()
    {
        CreditCardValidator validator = cFactory.getCreditCardValidtor(CreditCardType.VISA);
        boolean isValid = validator.validate("4532342813182586");
        System.out.println("the test is: " + isValid);
        assertThat(isValid);
    }
    
        
    /**
     * Test a valid mastercard credit card
     *@author michael McMahon
     */
    @Test
    public void testValidMasterCardCreditCard()
    {
        CreditCardValidator validator = cFactory.getCreditCardValidtor(CreditCardType.MASTERCARD);
        boolean isValid = validator.validate("5586804387894914");
        
        assertThat(isValid);
    }
    
        
    /**
     * Test a valid amex credit card
     *@author michael McMahon
     */
    @Test
    public void testValidAmexCreditCard()
    {
        CreditCardValidator validator = cFactory.getCreditCardValidtor(CreditCardType.AMEX);
        boolean isValid = validator.validate("376602381270213");
        assertThat(isValid);
    }
    
     /**
     * Test an invalid amex credit card
     *@author michael McMahon
     */
    @Test
    public void testinValidAmexCreditCard()
    {
        CreditCardValidator validator = cFactory.getCreditCardValidtor(CreditCardType.AMEX);
        boolean isValid = validator.validate("344454931157225");
        
        assertThat(!isValid);
    }
     /**
     * Test an invalid mastercard credit card
     *@author michael McMahon
     */
    @Test
    public void testInvalidMastercardCreditCard()
    {
        CreditCardValidator validator = cFactory.getCreditCardValidtor(CreditCardType.AMEX);
        boolean isValid = validator.validate("5586404387894954");
        
        assertThat(!isValid);
    }
     /**
     * Test an invalid visa credit card
     *@author michael McMahon
     */
    @Test
    public void testinValidVisaCreditCard()
    {
        CreditCardValidator validator = cFactory.getCreditCardValidtor(CreditCardType.VISA);
        boolean isValid = validator.validate("4556809921755160");
        
        assertThat(!isValid);
    }
    
     /**
     * Test a valid credit card number, but with the wrong type of card
     *@author michael McMahon
     */
    @Test
    public void testValidCreditCardNumberWithWrongCreditCardType()
    {
        CreditCardValidator validator = cFactory.getCreditCardValidtor(CreditCardType.MASTERCARD);
        boolean isValid = validator.validate("4556809926755160");
        
        assertThat(!isValid);
    }
    
}
