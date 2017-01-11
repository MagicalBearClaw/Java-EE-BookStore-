package ca.pacmabooks.test.persistence;

import ca.pacmabooks.client.jpa.beans.BooksAction;
import ca.pacmabooks.client.jpa.beans.util.JsfUtil;
import ca.pacmabooks.client.jpa.controllers.BannersController;
import ca.pacmabooks.client.jpa.controllers.BooksController;
import ca.pacmabooks.client.jpa.controllers.ClientsController;
import ca.pacmabooks.client.jpa.controllers.DetailsController;
import ca.pacmabooks.client.jpa.controllers.InvoicesController;
import ca.pacmabooks.client.jpa.controllers.ReviewsController;
import ca.pacmabooks.client.jpa.controllers.RssfeedsController;
import ca.pacmabooks.client.jpa.controllers.SurveysController;
import ca.pacmabooks.client.jpa.entities.Banners;
import ca.pacmabooks.client.jpa.entities.Books;
import ca.pacmabooks.client.jpa.entities.Clients;
import ca.pacmabooks.client.jpa.entities.Details;
import ca.pacmabooks.client.jpa.entities.Invoices;
import ca.pacmabooks.client.jpa.entities.Reviews;
import ca.pacmabooks.client.jpa.entities.Rssfeeds;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


/**
 * JPA DAO Text class.
 * All @test methods are created by Chris
 * 
 * @author Chris, Philip
 */
@RunWith(Arquillian.class)
public class ActionBeanTest {

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
                .addPackage(Books.class.getPackage())
                .addPackage(BooksAction.class.getPackage())
                .addPackage(BooksController.class.getPackage())
                .addPackage(JsfUtil.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/glassfish-resources.xml"), "glassfish-resources.xml")
                .addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
                .addAsResource("pacmabooks.sql")
                .addAsLibraries(dependencies);

        return webArchive;
    }

    @Resource(name = "java:app/localDB")
    private DataSource ds;

    @Inject
    private BooksController bkf;
    
    @Inject
    private ClientsController cc;
    
    @Inject
    private BannersController bc;
    
    @Inject
    private DetailsController dc;
    
    @Inject
    private InvoicesController ic;
    
    @Inject
    private ReviewsController rc;
    
    @Inject
    private RssfeedsController rssc;
    
    @Inject
    private SurveysController sc;
    
    @Test
    public void find_all_books() throws SQLException {
        List<Books> books = bkf.findAll();
        assertThat(books).hasSize(100);
    }
    
    @Test
    public void find_book_by_isbn() throws SQLException {
    String isbn = "978-0142414934";
        Books book = new Books();
        book.setIsbn(isbn);
        book.setTitle("Paper Towns");
        book.setAuthor("John Green");
        book.setPublisher("Speak");
        book.setPages((short)321);
        book.setGenre("Romance");
        book.setFormats(new ArrayList<String>(Arrays.asList("epub;mobi;pdf;".split(";"))).toString());
        book.setWholesalePrice(new BigDecimal(5.40).setScale(2, RoundingMode.HALF_UP));
        book.setListPrice(new BigDecimal(10.80).setScale(2, RoundingMode.HALF_UP));
        book.setSalePrice(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP));
        book.setFileSize(1608);
        book.setDescription("Quentin Jacobsen has spent a lifetime loving the magnificent Margo Roth Spiegelman from afar. So when she cracks open a window and climbs back into his lifesummoning him for an ingenious campaign of revengehe follows. When their all-nighter ends and a new day breaks, Margo has disappeared. But Q soon learns that there are cluesand theyre for him. Embarking on an exhilarating adventure to find her, the closer Q gets, the less he sees the girl he thought he knew");
        book.setDateRemoved(null);
        Books book2 = bkf.findBookByIsbn(isbn);
        assertEquals(book, book2);
    }
    
    @Test
    public void find_all_clients() throws SQLException {
        List<Clients> clients = cc.findAll();
        assertThat(clients).hasSize(50);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_client_by_email() throws SQLException {
        String email = "wlawson0@time.com";
        Clients client = new Clients();
        client.setId(1);
        client.setTitle("Dr");
        client.setFirstName("William");
        client.setLastName("Lawson");
        client.setCompany("Oyondu");
        client.setAddress1("American");
        client.setAddress2("Rieder");
        client.setCity("Logan Lake");
        client.setProvince("British Columbia");
        client.setCountry("Canada");
        client.setPostalCode("H6Y8B4");
        client.setCellPhone("1-(754)494-7321");
        client.setHomePhone("1-(674)751-9676");
        client.setEmail(email);
        client.setPassword("abcdefghi");
        client.setIsManager(true);
        Clients client2 = cc.findClientByEmail(email);
        assertEquals(client, client2);
    }
    
    @Test
    public void find_all_banners() throws SQLException {
        List<Banners> banners = bc.findAll();
        assertThat(banners).hasSize(4);
    }
    
    @Test
    public void find_all_details() throws SQLException {
        List<Details> details = dc.findAll();
        assertThat(details).hasSize(20);
    }
    
    @Test
    public void find_all_invoices() throws SQLException {
        List<Invoices> invoices = ic.findAll();
        assertThat(invoices).hasSize(20);
    }
    
    @Test
    public void find_all_reviews() throws SQLException {
        List<Reviews> reviews = rc.findAll();
        assertThat(reviews).hasSize(15);
    }
    
    @Test
    public void find_all_rssfeeds() throws SQLException {
        List<Rssfeeds> rss = rssc.findAll();
        assertThat(rss).hasSize(3);
    }
    
    /**
     * This routine is courtesy of Bartosz Majsak who also solved my Arquillian
     * remote server problem
     */
    @Before
    public void seedDatabase() {
        final String seedDataScript = loadAsString("pacmabooks.sql");

        try (Connection connection = ds.getConnection()) {
            for (String statement : splitStatements(new StringReader(
                    seedDataScript), ";")) {
                connection.prepareStatement(statement).execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("--------------------------------------------------- Seeding DID NOT WOKK");
            e.printStackTrace();
            throw new RuntimeException("Failed seeding database", e);
        }
        System.out.println("Seeding works");

    }

     /**
     * The following methods support the seedDatabse method
     */
    private String loadAsString(final String path) {
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(path)) {
            return new Scanner(inputStream).useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close input stream.", e);
        }
    }

    private List<String> splitStatements(Reader reader,
            String statementDelimiter) {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final StringBuilder sqlStatement = new StringBuilder();
        final List<String> statements = new LinkedList<>();
        try {
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || isComment(line)) {
                    continue;
                }
                sqlStatement.append(line);
                if (line.endsWith(statementDelimiter)) {
                    statements.add(sqlStatement.toString());
                    sqlStatement.setLength(0);
                }
            }
            return statements;
        } catch (IOException e) {
            throw new RuntimeException("Failed parsing sql", e);
        }
    }

    private boolean isComment(final String line) {
        return line.startsWith("--") || line.startsWith("//")
                || line.startsWith("/*");
    }
}
