/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
/**
 *
 * @author kitte
 */

@RunWith(Arquillian.class)
public class CreditCardTestX 
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
                .addAsWebInfResource(new File("src/main/setup/glassfish-resources.xml"), "glassfish-resources.xml")
                .addAsLibraries(dependencies);

        return webArchive;
    }
    

    @Test
    public void TestValidCreditCard() 
    {
        CreditCardValidator validator = new CreditCardFactory().getCreditCardValidtor(CreditCardType.AMEX);
        
        boolean test = validator.validate("342345678934534") && validator.validate("372345678934534");
        
        assertThat(test);
        
    }
    @Test
    public void TestInvalidCreditCardWithWrongStartingNumbers() 
    {
        CreditCardValidator validator = new CreditCardFactory().getCreditCardValidtor(CreditCardType.AMEX);
        
        boolean test = validator.validate("142345678934534") && validator.validate("372345678934534");
        
        assertThat(!test);
        
    }
    @Test
    public void TestInvalidCreditCardWithWronglength() 
    {
        CreditCardValidator validator = new CreditCardFactory().getCreditCardValidtor(CreditCardType.AMEX);
        
        boolean test = validator.validate("14238934534") && validator.validate("372345678934534");
        
        assertThat(!test);
        
    }
    @Test
    public void TestInvalidCreditCardWithNolength() 
    {
        CreditCardValidator validator = new CreditCardFactory().getCreditCardValidtor(CreditCardType.AMEX);
        
        boolean test = validator.validate("") && validator.validate("372345678934534");
        
        assertThat(!test);
        
    }   
}
