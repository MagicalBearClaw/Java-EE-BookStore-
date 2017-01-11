package ca.pacmabooks.test.persistence;

import ca.pacmabooks.client.backingbeans.AuthenticationBackingBean;
import ca.pacmabooks.client.beans.Banner;
import ca.pacmabooks.client.beans.Book;
import ca.pacmabooks.client.beans.BookReview;
import ca.pacmabooks.client.beans.Client;
import ca.pacmabooks.client.beans.Invoice;
import ca.pacmabooks.client.beans.InvoiceDetails;
import ca.pacmabooks.client.beans.RssFeed;
import ca.pacmabooks.client.beans.Survey;
import ca.pacmabooks.client.beans.Tax;
import ca.pacmabooks.client.business.controllers.util.LocaleChanger;
import ca.pacmabooks.client.persistence.DaoImpl;
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
 * JDBC DAO Text class.
 * All @test methods are created by Chris
 * Many edits have been made by Philip
 * 
 * @author Chris, Philip
 */
@RunWith(Arquillian.class)
public class DaoTest {

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
                .addPackage(DaoImpl.class.getPackage())
                .addPackage(Book.class.getPackage())
                .addPackage(AuthenticationBackingBean.class.getPackage())
                .addPackage(LocaleChanger.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/glassfish-resources.xml"), "glassfish-resources.xml")
                .addAsResource("pacmabooks.sql")
                .addAsLibraries(dependencies);

        return webArchive;
    }

    @Resource(name = "java:app/localDB")
    private DataSource ds;

    @Inject
    private DaoImpl daoImpl;

    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void create_a_book() throws SQLException {
        String isbn = "978-1234567890";
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle("blah");
        book.setAuthor("blah");
        book.setPublisher("blah");
        book.setNumOfPages(100);
        book.setGenre("blah");
        book.setFormats(new ArrayList<>(Arrays.asList("epub;mobi;pdf;".split(";"))));
        book.setWholeSalePrice(new BigDecimal(0.50).setScale(2, RoundingMode.HALF_UP));
        book.setListPrice(new BigDecimal(1.00).setScale(2, RoundingMode.HALF_UP));
        book.setSalePrice(new BigDecimal(0.20).setScale(2, RoundingMode.HALF_UP));
        book.setFileSize(565);
        book.setDescription("Blah blah blah");
        book.setDateAddedToInventory(LocalDateTime.now());
        book.setPublishDate(LocalDateTime.now());
        book.setDateRemoved(null);
        
        daoImpl.createBook(book);
        Book book2 = daoImpl.findBookByIsbn(isbn);
        System.out.println(book.getFormats());
        System.out.println(book2.getFormats());
        assertEquals(book, book2);
        
    }
    
   /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_all_books() throws SQLException {
        ArrayList<Book> books = daoImpl.findAllBooks();
        assertThat(books).hasSize(100);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_all_books_not_removed() throws SQLException {
        ArrayList<Book> books = daoImpl.findAllBooksNotRemoved();
        assertThat(books).hasSize(99);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_all_book_genres() throws SQLException {
        ArrayList<String> genres = daoImpl.findAllBookGenres();
        assertThat(genres).hasSize(24);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_all_book_authors() throws SQLException {
        ArrayList<String> authors = daoImpl.findAllBookAuthors();
        assertThat(authors).hasSize(68);
    }

    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_book_by_isbn() throws SQLException {
    String isbn = "978-0142414934";
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle("Paper Towns");
        book.setAuthor("John Green");
        book.setPublisher("Speak");
        book.setNumOfPages(321);
        book.setGenre("Romance");
        book.setFormats(new ArrayList<>(Arrays.asList("epub;mobi;pdf;".split(";"))));
        book.setWholeSalePrice(new BigDecimal(5.40).setScale(2, RoundingMode.HALF_UP));
        book.setListPrice(new BigDecimal(10.80).setScale(2, RoundingMode.HALF_UP));
        book.setSalePrice(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP));
        book.setFileSize(1608);
        book.setDescription("Quentin Jacobsen has spent a lifetime loving the magnificent Margo Roth Spiegelman from afar. So when she cracks open a window and climbs back into his lifesummoning him for an ingenious campaign of revengehe follows. When their all-nighter ends and a new day breaks, Margo has disappeared. But Q soon learns that there are cluesand theyre for him. Embarking on an exhilarating adventure to find her, the closer Q gets, the less he sees the girl he thought he knew");
        book.setDateAddedToInventory(LocalDateTime.now());
        book.setPublishDate(LocalDateTime.now());
        book.setDateRemoved(null);
        Book book2 = daoImpl.findBookByIsbn(isbn);
        assertEquals(book, book2);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_books_by_title() throws SQLException {
        String title = "Fifty";
        ArrayList<Book> books = daoImpl.findBookByTitle(title);
        assertThat(books).hasSize(3);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_books_by_author() throws SQLException {
        String author = "George R";
        ArrayList<Book> books = daoImpl.findBookByAuthor(author);
        assertThat(books).hasSize(5);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_books_by_Pages() throws SQLException {
        int pages = 722;
        ArrayList<Book> books = daoImpl.findBookByPages(722);
        assertThat(books).hasSize(1);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_books_by_genre() throws SQLException {
        String genre = "Science Fiction";
        ArrayList<Book> books = daoImpl.findBookByGenre(genre);
        assertThat(books).hasSize(20);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_books_by_FileSize() throws SQLException {
        int size = 4231;
        ArrayList<Book> books = daoImpl.findBookByFileSize(size);
        assertThat(books).hasSize(1);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_books_by_sale() throws SQLException {
        ArrayList<Book> books = daoImpl.findBookBySale();
        assertThat(books).hasSize(14);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_books_price_less() throws SQLException {
        BigDecimal price = new BigDecimal(5.00).setScale(2, RoundingMode.HALF_UP);
        ArrayList<Book> books = daoImpl.findBookByPriceLess(price);
        assertThat(books).hasSize(26);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_books_price_more() throws SQLException {
        BigDecimal price = new BigDecimal(5.00).setScale(2, RoundingMode.HALF_UP);
        ArrayList<Book> books = daoImpl.findBookByPriceMore(price);
        assertThat(books).hasSize(74);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_books_price_between() throws SQLException {
        BigDecimal low = new BigDecimal(0.50).setScale(2, RoundingMode.HALF_UP);
        BigDecimal high = new BigDecimal(8.00).setScale(2, RoundingMode.HALF_UP);
        ArrayList<Book> books = daoImpl.findBookByPriceBetween(low, high);
        assertThat(books).hasSize(46);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_books_date_published() throws SQLException {
        LocalDateTime time = LocalDateTime.of(2013, 1, 1, 0, 0);
        ArrayList<Book> books = daoImpl.findBookByDatePublished(time);
        System.out.println(time + " published.   " + books.get(0).getPublishDate());
        assertThat(books).hasSize(1);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_books_date_received() throws SQLException {
        LocalDateTime time = LocalDateTime.of(2013, 1, 8, 0, 0);
        System.out.println(time + "received");
        ArrayList<Book> books = daoImpl.findBookByDateAdded(time);
        assertThat(books).hasSize(1);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_books_date_removed() throws SQLException {
        LocalDateTime time = LocalDateTime.of(2016, 3, 8, 12, 30);
        System.out.println(time + "removed");
        ArrayList<Book> books = daoImpl.findBookByDateRemoved(time);
        assertThat(books).hasSize(1);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void update_book() throws SQLException {
        String isbn = "978-0142414934";
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle("Paper Towns");
        book.setAuthor("John Green");
        book.setPublisher("Speak");
        // changing the number of pages. it was 321 originally
        book.setNumOfPages(400);
        book.setGenre("Romance");
        book.setFormats(new ArrayList<>(Arrays.asList("epub;mobi;pdf;".split(";"))));
        book.setWholeSalePrice(new BigDecimal(5.40).setScale(2, RoundingMode.HALF_UP));
        book.setListPrice(new BigDecimal(10.80).setScale(2, RoundingMode.HALF_UP));
        book.setSalePrice(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP));
        book.setFileSize(1608);
        book.setDescription("Quentin Jacobsen has spent a lifetime loving the magnificent Margo Roth Spiegelman from afar. So when she cracks open a window and climbs back into his lifeï¿½summoning him for an ingenious campaign of revengeï¿½he follows. When their all-nighter ends and a new day breaks, Margo has disappeared. But Q soon learns that there are cluesï¿½and theyï¿½re for him. Embarking on an exhilarating adventure to find her, the closer Q gets, the less he sees the girl he thought he knew");
        book.setDateAddedToInventory(LocalDateTime.now());
        book.setPublishDate(LocalDateTime.now());
        book.setDateRemoved(null);
        daoImpl.updateBook(book);
        Book book2 = daoImpl.findBookByIsbn(isbn);
        assertEquals(book, book2);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void remove_book() throws SQLException {
        String isbn = "978-0142414934";
        LocalDateTime time = LocalDateTime.now().plusMinutes(1);
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle("Paper Towns");
        book.setAuthor("John Green");
        book.setPublisher("Speak");
        book.setNumOfPages(321);
        book.setGenre("Romance");
        book.setFormats(new ArrayList<>(Arrays.asList("epub;mobi;pdf;".split(";"))));
        book.setWholeSalePrice(new BigDecimal(5.40).setScale(2, RoundingMode.HALF_UP));
        book.setListPrice(new BigDecimal(10.80).setScale(2, RoundingMode.HALF_UP));
        book.setSalePrice(null);
        book.setFileSize(1608);
        book.setDescription("Quentin Jacobsen has spent a lifetime loving the magnificent Margo Roth Spiegelman from afar. So when she cracks open a window and climbs back into his lifeï¿½summoning him for an ingenious campaign of revengeï¿½he follows. When their all-nighter ends and a new day breaks, Margo has disappeared. But Q soon learns that there are cluesï¿½and theyï¿½re for him. Embarking on an exhilarating adventure to find her, the closer Q gets, the less he sees the girl he thought he knew");
        book.setDateAddedToInventory(LocalDateTime.now());
        book.setPublishDate(LocalDateTime.now());
        book.setDateRemoved(time);
        daoImpl.removeBook(book);
//        Book book2 = daoImpl.findBookByIsbn(isbn);
//        assertEquals(book.getDateRemoved(), book2.getDateRemoved());
        ArrayList<Book> books = daoImpl.findAllBooksNotRemoved();
        assertThat(books).hasSize(98);
    }
    
    // BookReview Section for testing
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void create_book_review() throws SQLException {
        BookReview bookReview = new BookReview();
        bookReview.setIsbn("978-0692283813");
        bookReview.setClientId(1);
        bookReview.setReviewDate(LocalDateTime.now().withNano(0));
        bookReview.setReviewScore(4);
        bookReview.setReview("Awesome book. I would gladly read it again and again.");
        bookReview.setStatus('P');
        
        daoImpl.createBookReview(bookReview);
        BookReview review = daoImpl.findReviewById(16);
        assertEquals(bookReview, review);
        
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_all_book_reviews() throws SQLException {
        ArrayList<BookReview> reviews = daoImpl.findAllReviews();
        assertThat(reviews).hasSize(15);
        
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_review_by_id() throws SQLException {
        BookReview bookReview = new BookReview();
        bookReview.setId(1);
        bookReview.setIsbn("978-0692283813");
        bookReview.setClientId(1);
        bookReview.setReviewDate(LocalDateTime.of(2015, 3, 16, 8, 36, 11));
        bookReview.setReviewScore(2);
        bookReview.setReview("Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est. Phasellus sit amet erat. Nulla tempus.");
        bookReview.setStatus('A');
        
        BookReview review = daoImpl.findReviewById(1);
        System.out.println("-------------- by id -------------");
        System.out.println(bookReview.getId());
        System.out.println(bookReview.getIsbn());
        System.out.println(bookReview.getClientId());
        System.out.println(bookReview.getReviewDate());
        System.out.println(bookReview.getReviewScore());
        System.out.println(bookReview.getReview());
        System.out.println(bookReview.getStatus());
        
        assertEquals(bookReview, review);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_review_by_isbn() throws SQLException {
        String isbn = "978-1519164377";
        ArrayList<BookReview> reviews = daoImpl.findReviewByIsbn(isbn);        
        assertThat(reviews).hasSize(2);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_review_by_client_id() throws SQLException {
        int clientId = 9;
        ArrayList<BookReview> reviews = daoImpl.findReviewByClientId(clientId);        
        assertThat(reviews).hasSize(1);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_review_by_date() throws SQLException {
        LocalDateTime time = LocalDateTime.of(2015, 3, 16, 8, 36, 11);
        ArrayList<BookReview> reviews = daoImpl.findReviewByDate(time);        
        assertThat(reviews).hasSize(1);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_review_by_rating() throws SQLException {
        int rating = 4;
        ArrayList<BookReview> reviews = daoImpl.findReviewByRating(rating);        
        assertThat(reviews).hasSize(4);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_review_by_status() throws SQLException {
        char status = 'A';
        ArrayList<BookReview> reviews = daoImpl.findReviewByStatus(status);        
        assertThat(reviews).hasSize(4);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void update_review() throws SQLException {
        BookReview bookReview = new BookReview();
        bookReview.setId(1);
        bookReview.setIsbn("978-0692283813");
        bookReview.setClientId(1);
        bookReview.setReviewDate(LocalDateTime.of(2015, 3, 16, 8, 36, 11));
        bookReview.setReviewScore(5);
        bookReview.setReview("Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est. Phasellus sit amet erat. Nulla tempus.");
        bookReview.setStatus('A');
        
        daoImpl.updateReview(bookReview);
        BookReview review = daoImpl.findReviewById(1);
        assertEquals(review, bookReview);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void delete_review() throws SQLException {
        BookReview bookReview = new BookReview();
        bookReview.setId(1);
        bookReview.setIsbn("978-0692283813");
        bookReview.setClientId(1);
        bookReview.setReviewDate(LocalDateTime.of(2015, 3, 16, 8, 36, 11));
        bookReview.setReviewScore(2);
        bookReview.setReview("Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est. Phasellus sit amet erat. Nulla tempus.");
        bookReview.setStatus('A');
        
        daoImpl.deleteReview(bookReview);
        // It will return an empty BookReview. IT has only been declared and initialized with new. SO default values are in the bean's fields
        BookReview review = daoImpl.findReviewById(1);
        // Default value of an int is 0. The record was not found so an empty bean was returned
        assertEquals(review.getId(), 0);
    }

    
    // Client Section for testing
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void create_client() throws SQLException {
        Client client = new Client(); 
        client.setTitle("Sir");
        client.setFirstName("Derp");
        client.setLastName("Derpington");
        client.setCompanyName("Derp Co.");
        client.setAddress1("6969 Avenue Derpy");
        client.setAddress2("ff");
        client.setCity("Derpy city");
        client.setProvince("Quebec");
        client.setCountry("Canada");
        client.setPostalCode("D3R4P5");
        client.setCellTelephoneNumber("1-(514)422-3152");
        client.setHomeTelephoneNumber("1-(514)752-8822");
        client.setEmail("derpyderp@gmail.com");
        client.setPassword("derp123derp456");
        client.setIsManager(true);
        
        daoImpl.createClient(client);
        Client client2 = daoImpl.findClientByEmail("derpyderp@gmail.com");
        assertEquals(client, client2);

    }
    
    /**
     * 
     * @throws SQLException
     */
    @Test
    public void find_client_by_id() throws SQLException{
        int id = 1;
        Client client = new Client();
        client.setId(id);
        client.setTitle("Dr");
        client.setFirstName("William");
        client.setLastName("Lawson");
        client.setCompanyName("Oyondu");
        client.setAddress1("American");
        client.setAddress2("Rieder");
        client.setCity("Logan Lake");
        client.setProvince("British Columbia");
        client.setCountry("Canada");
        client.setPostalCode("H6Y8B4");
        client.setCellTelephoneNumber("1-(754)494-7321");
        client.setHomeTelephoneNumber("1-(674)751-9676");
        client.setEmail("wlawson0@time.com");
        client.setPassword("abcdefghi");
        client.setIsManager(true);
        
        Client client2 = daoImpl.findClientById(id);
        assertEquals(client, client2);
    }
    
   /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_all_clients() throws SQLException {
        ArrayList<Client> clients = daoImpl.findAllClients();
        assertThat(clients).hasSize(50);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_client_by_first_name() throws SQLException {
        String name = "Frank";
        ArrayList<Client> clients = daoImpl.findClientByFName(name);
        assertThat(clients).hasSize(1);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_client_by_last_name() throws SQLException {
        String name = "Adams";
        ArrayList<Client> clients = daoImpl.findClientByLName(name);
        assertThat(clients).hasSize(1);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_client_by_email() throws SQLException {
        String email = "wlawson0@time.com";
        Client client = new Client();
        client.setTitle("Dr");
        client.setFirstName("William");
        client.setLastName("Lawson");
        client.setCompanyName("Oyondu");
        client.setAddress1("American");
        client.setAddress2("Rieder");
        client.setCity("Logan Lake");
        client.setProvince("British Columbia");
        client.setCountry("Canada");
        client.setPostalCode("H6Y8B4");
        client.setCellTelephoneNumber("1-(754)494-7321");
        client.setHomeTelephoneNumber("1-(674)751-9676");
        client.setEmail(email);
        client.setPassword("abcdefghi");
        client.setIsManager(true);
        Client client2 = daoImpl.findClientByEmail(email);
        assertEquals(client, client2);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void update_client() throws SQLException {
        Client client = new Client();
        // was initially titled Dr
        client.setId(1);
        client.setTitle("Miss");
        client.setFirstName("William");
        client.setLastName("Lawson");
        client.setCompanyName("Oyondu");
        client.setAddress1("American");
        client.setAddress2("Rieder");
        client.setCity("Logan Lake");
        client.setProvince("British Columbia");
        client.setCountry("Canada");
        client.setPostalCode("H6Y8B4");
        client.setCellTelephoneNumber("1-(754)494-7321");
        client.setHomeTelephoneNumber("1-(674)751-9676");
        client.setEmail("wlawson0@time.com");
        client.setPassword("abcdefghi");
        client.setIsManager(false);
        String email = "wlawson0@time.com";
        
        daoImpl.updateClient(client);
        Client client2 = daoImpl.findClientByEmail(email);
        assertEquals(client, client2);
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void delete_client() throws SQLException {
        Client client = new Client();
        int id = 1;
        client.setId(id);
        
        daoImpl.deleteClient(client);
        
        
        // It will return an empty Client. IT has only been declared and initialized with new. SO default values are in the bean's fields
        Client client2 = daoImpl.findClientById(id);
        // Default value of an int is 0. The record was not found so an empty bean was returned
        assertEquals(client2.getId(), 0);
    }
    
    // Invoice Section for testing
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void create_invoice() throws SQLException {
        Invoice invoice = new Invoice();
        //there are currently 20 invoices in the db
        invoice.setId(21);
        invoice.setClientId(22);
        invoice.setSaleDate(LocalDateTime.now().withNano(0));
        invoice.setTotalNetValueOfSale(new BigDecimal(10.00).setScale(2, RoundingMode.HALF_UP));
        invoice.setTotalGrossSale(new BigDecimal(11.50).setScale(2, RoundingMode.HALF_UP));
        
        daoImpl.createInvoice(invoice);
        Invoice invoice2 = daoImpl.findInvoiceById(21);
        assertEquals(invoice, invoice2);

    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_all_invoices() throws SQLException {
        ArrayList<Invoice> invoices = daoImpl.findAllInvoices();
        assertThat(invoices).hasSize(20);

    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_invoice_by_id() throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setId(1);
        invoice.setClientId(1);
        invoice.setSaleDate(LocalDateTime.of(2016, 2, 8, 12, 0, 0));
        invoice.setTotalNetValueOfSale(new BigDecimal(156.16).setScale(2, RoundingMode.HALF_UP));
        invoice.setTotalGrossSale(new BigDecimal(67.51).setScale(2, RoundingMode.HALF_UP));
        
        Invoice invoice2 = daoImpl.findInvoiceById(1);
        assertEquals(invoice, invoice2);

    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_invoice_by_sale_date() throws SQLException {
        LocalDateTime time  = LocalDateTime.of(2016, 2, 8, 12, 0, 0);  
        ArrayList<Invoice> invoices = daoImpl.findInvoiceBySaleDate(time);
        assertThat(invoices).hasSize(1);

    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_invoice_by_client_id() throws SQLException {
        int id = 1;
        ArrayList<Invoice> invoices = daoImpl.findInvoiceByClientId(id);
        assertThat(invoices).hasSize(1);

    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void update_invoice() throws SQLException {
        int id = 1;
        Invoice invoice = new Invoice();
        invoice.setId(id);
        invoice.setClientId(1);
        invoice.setSaleDate(LocalDateTime.of(2016, 2, 8, 12, 0, 0));
        invoice.setTotalNetValueOfSale(new BigDecimal(156.16).setScale(2, RoundingMode.HALF_UP));
        // net value was originally 67.51
        invoice.setTotalGrossSale(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP));
        
        daoImpl.updateInvoice(invoice);
        Invoice invoice2 = daoImpl.findInvoiceById(id);
        assertEquals(invoice, invoice2);

    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void delete_invoice() throws SQLException {
        int id = 1;
        Invoice invoice = new Invoice();
        invoice.setId(id);
        
        daoImpl.removeInvoice(invoice);
        
        Invoice invoice2 = daoImpl.findInvoiceById(id);
        assertThat(invoice2.getDateRemoved()).isNotNull();
    }
    
    // Details Section for testing
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void create_details() throws SQLException {
        InvoiceDetails details = new InvoiceDetails();
        //there are currently 20 details in the db
        details.setId(21);
        details.setInvoiceId(1);
        details.setIsbn("978-0553593716");
        details.setBookPrice(new BigDecimal(2.99).setScale(2, RoundingMode.HALF_UP));
        details.setGstTax(new BigDecimal(5.000).setScale(3, RoundingMode.HALF_UP));
        details.setPstTax(new BigDecimal(9.975).setScale(3, RoundingMode.HALF_UP));
        details.setHstTax(new BigDecimal(0.000).setScale(3, RoundingMode.HALF_UP));
        
        daoImpl.createDetails(details);
        InvoiceDetails details2 = daoImpl.findDetailsById(21);
        assertEquals(details, details2);

    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_all_details() throws SQLException {
        ArrayList<InvoiceDetails> details = daoImpl.findAllDetails();
        assertThat(details).hasSize(20);

    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_details_by_id() throws SQLException {
        InvoiceDetails details = new InvoiceDetails();
        details.setId(1);
        details.setInvoiceId(1);
        details.setIsbn("978-1940026046");
        details.setBookPrice(new BigDecimal(52.38).setScale(2, RoundingMode.HALF_UP));
        details.setGstTax(new BigDecimal(0.000).setScale(3, RoundingMode.HALF_UP));
        details.setPstTax(new BigDecimal(5.000).setScale(3, RoundingMode.HALF_UP));
        details.setHstTax(new BigDecimal(0.000).setScale(3, RoundingMode.HALF_UP));
        
        InvoiceDetails details2 = daoImpl.findDetailsById(1);
        assertEquals(details, details2);

    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void find_details_by_invoice_id() throws SQLException {
        
        ArrayList<InvoiceDetails> details = daoImpl.findDetailsByInvoiceId(1);
        assertThat(details).hasSize(1);

    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void update_details() throws SQLException {
        InvoiceDetails details = new InvoiceDetails();
        details.setId(1);
        details.setInvoiceId(1);
        details.setIsbn("978-1484015803");
        details.setBookPrice(new BigDecimal(52.38).setScale(2, RoundingMode.HALF_UP));
        details.setGstTax(new BigDecimal(5.000).setScale(3, RoundingMode.HALF_UP));
        details.setPstTax(new BigDecimal(0.000).setScale(3, RoundingMode.HALF_UP));
        details.setHstTax(new BigDecimal(5.000).setScale(3, RoundingMode.HALF_UP));
        
        daoImpl.updateDetails(details);
        InvoiceDetails details2 = daoImpl.findDetailsById(1);
        assertEquals(details, details2);

    }
    
    /**
     * 
     * @throws SQLException 
     */
    @Test
    public void delete_details() throws SQLException {
        InvoiceDetails details = new InvoiceDetails();
        details.setId(1);
        
        daoImpl.removeDetails(details);
        
        InvoiceDetails details2 = daoImpl.findDetailsById(1);
          assertThat(details2.getDateRemoved()).isNotNull();

    }
    
    // Banned Section
    
    @Test
    public void create_banner() throws SQLException{
        Banner banner = new Banner();
        banner.setUrl("https://s3.amazonaws.com/hiphopdx-production/2014/10/juicy-j-serait-il-le-pere-de-enfant-de-miley-cyrus-913625598.jpg");
        banner.setEnabled(false);
        banner.setId(5);
        
        daoImpl.createBanner(banner);
        Banner banner2 = daoImpl.findBannerById(5);
        assertEquals(banner, banner2);
    }
    
    @Test
    public void find_all_banner() throws SQLException{
        ArrayList<Banner> b = new ArrayList<>();
        b = daoImpl.findAllbanners();
        assertThat(b).hasSize(4);
    }
    
    @Test
    public void find_banner_by_id() throws SQLException{
        Banner banner = new Banner();
        banner.setUrl("http://www.naylor.com/wp-content/uploads/2015/08/AdvHere_HB.jpg");
        banner.setEnabled(true);
        banner.setId(1);
        
        Banner banner2 = daoImpl.findBannerById(1);
        assertEquals(banner, banner2);
    }
    
    @Test
    public void find_banner_by_url() throws SQLException{
        Banner banner = new Banner();
        banner.setUrl("http://www.naylor.com/wp-content/uploads/2015/08/AdvHere_HB.jpg");
        banner.setEnabled(true);
        banner.setId(1);
        
        Banner banner2 = daoImpl.findBannerByUrl("http://www.naylor.com/wp-content/uploads/2015/08/AdvHere_HB.jpg");
        assertEquals(banner, banner2);
    }
    
    @Test
    public void find_banner_by_enabled() throws SQLException{
        ArrayList<Banner> b = new ArrayList<>();
        b = daoImpl.findAllBannersEnabled();
        assertThat(b).hasSize(3);
    }
    
    @Test
    public void update_banner() throws SQLException{
        Banner banner = new Banner();
        banner.setUrl("ad3.jpg");
        banner.setEnabled(true);
        banner.setId(1);
        
        daoImpl.updateBanner(banner);
        Banner banner2 = daoImpl.findBannerById(1);
        assertEquals(banner, banner2);
    }
    
    @Test
    public void delete_banner() throws SQLException{
        Banner banner = new Banner();
        banner.setUrl("http://www.naylor.com/wp-content/uploads/2015/08/AdvHere_HB.jpg");
        banner.setEnabled(true);
        banner.setId(1);
        
        daoImpl.deleteBanner(banner);
        Banner banner2 = daoImpl.findBannerById(1);
        // Default value of an int is 0. The record was not found so an empty bean was returned
        assertEquals(banner2.getId(), 0);
    }
    
    // RssFeed Section
    
    @Test
    public void create_RssFeed() throws SQLException{
        RssFeed feed = new RssFeed();
        feed.setUrl("blue.com");
        feed.setEnabled(false);
        feed.setId(4);
        
        daoImpl.createRssFeed(feed);
        RssFeed feed2 = daoImpl.findRssFeedById(4);
        assertEquals(feed, feed2);
    }
    
    @Test
    public void find_all_feed() throws SQLException{
        ArrayList<RssFeed> r = new ArrayList<>();
        r = daoImpl.findAllRssFeed();
        assertThat(r).hasSize(3);
    }
    
    @Test
    public void find_feed_by_id() throws SQLException{
        RssFeed feed = new RssFeed();
        feed.setUrl("http://rss.cbc.ca/lineup/world.xml");
        feed.setEnabled(true);
        feed.setId(1);
        
        RssFeed feed2 = daoImpl.findRssFeedById(1);
        assertEquals(feed, feed2);
    }
    
    @Test
    public void find_feed_by_url() throws SQLException{
        RssFeed feed = new RssFeed();
        feed.setUrl("http://rss.cbc.ca/lineup/world.xml");
        feed.setEnabled(true);
        feed.setId(1);
        
        RssFeed feed2 = daoImpl.findRssFeedByUrl("http://rss.cbc.ca/lineup/world.xml");
        assertEquals(feed, feed2);
    }
    
    @Test
    public void find_feed_by_enabled() throws SQLException{
        ArrayList<RssFeed> r = new ArrayList<>();
        r = daoImpl.findAllRssFeedsEnabled();
        assertThat(r).hasSize(1);
    }
    
    @Test
    public void update_feed() throws SQLException{
        RssFeed feed = new RssFeed();
        // was http://rss.cbc.ca/lineup/world.xml
        feed.setUrl("blah.com");
        feed.setEnabled(true);
        feed.setId(1);
        
        daoImpl.updateRssFeed(feed);
        RssFeed feed2 = daoImpl.findRssFeedById(1);
        assertEquals(feed, feed2);
    }
    
    @Test
    public void delete_feed() throws SQLException{
        RssFeed feed = new RssFeed();
        // was ad1.jpg
        feed.setUrl("http://rss.cbc.ca/lineup/world.xml");
        feed.setEnabled(true);
        feed.setId(1);
        
        daoImpl.deleteRssFeed(feed);
        RssFeed feed2 = daoImpl.findRssFeedById(1);
        // Default value of an int is 0. The record was not found so an empty bean was returned
        assertEquals(feed2.getId(), 0);
    }
    
    // Tax Section
    
    @Test
    public void find_tax_by_province() throws SQLException{
        Tax tax  = new Tax();
        tax.setProvince("AB");
        tax.setCombinedRate(new BigDecimal(5.000).setScale(3, RoundingMode.HALF_UP));
        tax.setGst(new BigDecimal(5.000).setScale(3, RoundingMode.HALF_UP));
        tax.setPst(new BigDecimal(0.000).setScale(3, RoundingMode.HALF_UP));
        tax.setHst(new BigDecimal(0.000).setScale(3, RoundingMode.HALF_UP));
        tax.setId(1);
        
        Tax tax2 = daoImpl.findTaxByProvince("AB");
        assertEquals(tax, tax2);
    }
    
    // Survey Section
    
    @Test
    public void createSurvey() throws SQLException{
        Survey survey = new Survey();
        survey.setId(6);
        survey.setEnabled(true);
        survey.setQuestion("blah");
        survey.setAnswer1("blah1");
        survey.setAnswer2("blah2");
        survey.setAnswer3("blah3");
        survey.setAnswer1Count(0);
        survey.setAnswer2Count(0);
        survey.setAnswer3Count(0);
        
        daoImpl.createSurvey(survey);
        Survey survey2 = daoImpl.findSurveyById(6);
        assertEquals(survey, survey2);
    }
    
    @Test
    public void findAllSurveys() throws SQLException{
        ArrayList<Survey> s = new ArrayList<>();
        s = daoImpl.findAllSurveys();
        assertThat(s).hasSize(3);
    }
    
    @Test
    public void findSurveyById() throws SQLException{
        Survey survey = new Survey();
        survey.setId(1);
        survey.setEnabled(true);
        survey.setQuestion("Which series do you like the best?");
        survey.setAnswer1("Lord of the Rings");
        survey.setAnswer2("Harry Potter");
        survey.setAnswer3("Game of Thrones");
        survey.setAnswer1Count(2);
        survey.setAnswer2Count(8);
        survey.setAnswer3Count(3);
        
        Survey survey2 = daoImpl.findSurveyById(1);
        assertEquals(survey, survey2);
    }
    
    @Test
    public void updateSurvey() throws SQLException{
        Survey survey = new Survey();
        survey.setId(1);
          survey.setEnabled(false);
          survey.setQuestion("blah");
          survey.setAnswer1("blah");
          survey.setAnswer2("blah");
          survey.setAnswer3("blah");
          survey.setAnswer1Count(1);
          survey.setAnswer2Count(1);
          survey.setAnswer3Count(1);
        
        daoImpl.updateSurvey(survey);
        Survey survey2 = daoImpl.findSurveyById(1);
        assertEquals(survey, survey2);
    }
    
    @Test
    public void deleteSurvey() throws SQLException{
        Survey survey = new Survey();
        survey.setId(1);
        
        daoImpl.deleteSurvey(survey);
        Survey survey2 = daoImpl.findSurveyById(1);
        // Default value of an int is 0. The record was not found so an empty bean was returned
        assertEquals(survey2.getId(), 0);
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
