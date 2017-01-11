/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.pacmabooks.client.persistence;

import ca.pacmabooks.client.beans.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.inject.Named;

/**
 * DAO interface
 * 
 * @author Christoffer Baur 1336394
 */

@Named
//@RequestScoped
public interface Dao {
    
    public ArrayList<Book> findBooksBySearch(String search) throws SQLException;
    
    // Books Crud Methods
    // Create
    public int createBook(Book book) throws SQLException;
    // Read
    public ArrayList<Book> findAllBooks() throws SQLException;
    public ArrayList<Book> findAllBooksNotRemoved() throws SQLException;
    public ArrayList<String> findAllBookGenres() throws SQLException;
    public ArrayList<String> findAllBookAuthors() throws SQLException;
    public Book findBookByIsbn(String isbn) throws SQLException;
    public ArrayList<Book> findBookByTitle(String title) throws SQLException;
    public ArrayList<Book> findBookByAuthor(String author) throws SQLException;
    public ArrayList<Book> findBookByPages(int pages) throws SQLException;
    public ArrayList<Book> findBookByGenre(String genre) throws SQLException;
    public ArrayList<Book> findBookByFileSize(int fileSize) throws SQLException;
    public ArrayList<Book> findBookBySale() throws SQLException;
    public ArrayList<Book> findBookByPriceLess(BigDecimal price) throws SQLException;
    public ArrayList<Book> findBookByPriceMore(BigDecimal price) throws SQLException;
    public ArrayList<Book> findBookByPriceBetween(BigDecimal lowPrice, BigDecimal highPrice) throws SQLException;
    public ArrayList<Book> findBookByDatePublished(LocalDateTime date) throws SQLException;
    public ArrayList<Book> findBookByDateAdded(LocalDateTime date) throws SQLException;
    public ArrayList<Book> findBookByDateRemoved(LocalDateTime date) throws SQLException;
    public ArrayList<Book> findBookOrderByDate(int limit) throws SQLException;
    // Update
    public int updateBook(Book book) throws SQLException;
    // Delete
    public int removeBook(Book book) throws SQLException;
    
    // Review CRUD methods
    // Create
    public int createBookReview(BookReview bookReview) throws SQLException;
    // Read
    public ArrayList<BookReview> findAllReviews() throws SQLException;
    public BookReview findReviewById(int id) throws SQLException;
    public ArrayList<BookReview> findReviewByIsbn(String isbn) throws SQLException;
    public ArrayList<BookReview> findReviewByClientId(int clientId) throws SQLException;
    public ArrayList<BookReview> findReviewByDate(LocalDateTime date) throws SQLException;
    public ArrayList<BookReview> findReviewByRating(int rating) throws SQLException;
    public ArrayList<BookReview> findReviewByStatus(char status) throws SQLException;
    // Update
    public int updateReview(BookReview review) throws SQLException;
    // Delete
    public int deleteReview(BookReview review) throws SQLException;
    
    
    // Client CRUD methods
    // Create
    public int createClient(Client client) throws SQLException;
    // Read
    public ArrayList<Client> findAllClients() throws SQLException;
    public Client findClientById(int id) throws SQLException;
    public ArrayList<Client> findClientByFName(String firstName) throws SQLException;
    public ArrayList<Client> findClientByLName(String lastName) throws SQLException;
    public Client findClientByEmail(String email) throws SQLException;
    // Update
    public int updateClient(Client client) throws SQLException;
    // Delete
    public int deleteClient(Client client) throws SQLException;
    
    
    // Invoice CRUD methods
    // Create
    public int createInvoice(Invoice invoice) throws SQLException;
    // Read
    public ArrayList<Invoice> findAllInvoices() throws SQLException;
    public Invoice findInvoiceById(int id) throws SQLException;
    public ArrayList<Invoice> findInvoiceBySaleDate(LocalDateTime date) throws SQLException;
    public ArrayList<Invoice> findInvoiceByClientId(int clientId) throws SQLException;
    // Update
    public int updateInvoice(Invoice invoice) throws SQLException;
    // Delete
    public int removeInvoice(Invoice invoice) throws SQLException;
    
    
    // Details CRUD methods
    // Create
    public int createDetails(InvoiceDetails details) throws SQLException;
    // Read
    public ArrayList<InvoiceDetails> findAllDetails() throws SQLException;
    public InvoiceDetails findDetailsById(int id) throws SQLException;
    public ArrayList<InvoiceDetails> findDetailsByInvoiceId(int invoiceId) throws SQLException;
    // Update
    public int updateDetails(InvoiceDetails details) throws SQLException;
    // Delete
    public int removeDetails(InvoiceDetails details) throws SQLException;
    
    
    // banners CRUD methods
    
    public int createBanner(Banner banner) throws SQLException;
    
    public ArrayList<Banner> findAllbanners() throws SQLException;
    public Banner findBannerById(int id) throws SQLException;
    public Banner findBannerByUrl(String url) throws SQLException;
    public ArrayList<Banner> findAllBannersEnabled() throws SQLException;
    
    public int updateBanner(Banner banner) throws SQLException;
    
    public int deleteBanner(Banner banner) throws SQLException;
    
    // RssFeed CRUD methods
    
    public int createRssFeed(RssFeed rssFeed) throws SQLException;
    
    public ArrayList<RssFeed> findAllRssFeed() throws SQLException;
    public RssFeed findRssFeedById(int id) throws SQLException;
    public RssFeed findRssFeedByUrl(String url) throws SQLException;
    public ArrayList<RssFeed> findAllRssFeedsEnabled() throws SQLException;
    
    public int updateRssFeed(RssFeed rssFeed) throws SQLException;
    
    public int deleteRssFeed(RssFeed rssFeed) throws SQLException;
    
    //Tax CRUD methods
    
    public Tax findTaxByProvince(String province) throws SQLException;
    
    // Survey CRUD methods
    
    public int createSurvey(Survey survey) throws SQLException;
    
    public ArrayList<Survey> findAllSurveys() throws SQLException;
    public Survey findSurveyById(int id) throws SQLException;
    public ArrayList<Survey> findAllSurveysEnabled() throws SQLException;
    public int updateSurvey(Survey survey) throws SQLException;
    
    public int deleteSurvey(Survey survey) throws SQLException;   
}
