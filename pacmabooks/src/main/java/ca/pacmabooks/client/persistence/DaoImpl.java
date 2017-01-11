// testing git work
package ca.pacmabooks.client.persistence;

import ca.pacmabooks.client.beans.Banner;
import ca.pacmabooks.client.beans.Book;
import ca.pacmabooks.client.beans.BookReview;
import ca.pacmabooks.client.beans.Client;
import ca.pacmabooks.client.beans.Invoice;
import ca.pacmabooks.client.beans.InvoiceDetails;
import ca.pacmabooks.client.beans.RssFeed;
import ca.pacmabooks.client.beans.Survey;
import ca.pacmabooks.client.beans.Tax;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 * DAO persistence class using JDBC
 *
 * @author Christoffer Baur 1336394
 * @author Michael McMahon(1330815)
 */
@Named
@RequestScoped
public class DaoImpl implements Dao {

    @Resource(name = "java:app/jdbc/g5w16bookstore")
    private DataSource bookSource;

    /**
     * This method allows the insertion of a new banner in the database.
     *
     * @author Michael McMahon (1330815)
     * @param banner
     * @return the number of rows affected
     * @throws SQLException
     */
    @Override
    public int createBanner(Banner banner) throws SQLException {
        int result = 0;
        String createQuery;
        createQuery = "INSERT INTO banners (url, enabled) VALUES (?,?)";

        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(createQuery)) {
            ps.setString(1, banner.getUrl());
            ps.setBoolean(2, banner.getEnabled());

            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     * This method allows the retrieval of all banners from the database.
     *
     * @author Michael McMahon (1330815)
     * @return an arraylist of banners
     * @throws SQLException
     */
    @Override
    public ArrayList<Banner> findAllbanners() throws SQLException {
        ArrayList<Banner> rows = new ArrayList<>();
        String query = "SELECT id, url, enabled FROM Banners";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBannerBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * This method allows the retrieval of a banner by its id from the database.
     *
     * @author Michael McMahon (1330815)
     * @param id
     * @return the banner matching the id
     * @throws SQLException
     */
    @Override
    public Banner findBannerById(int id) throws SQLException {
        Banner banner = new Banner();
        String query = "SELECT id, url, enabled FROM banners WHERE id = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    banner = createBannerBean(resultSet);
                }
            }
        }
        return banner;
    }

    /**
     * This method allows the retrieval of a banner by its url from the
     * database.
     *
     * @author Michael McMahon (1330815)
     * @param url
     * @return the banner matching the url
     * @throws SQLException
     */
    @Override
    public Banner findBannerByUrl(String url) throws SQLException {
        Banner banner = new Banner();
        String query = "SELECT id, url, enabled FROM banners WHERE url = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, url);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    banner = createBannerBean(resultSet);
                }
            }
        }
        return banner;
    }

    /**
     * This method allows the retrieval of a banner by its enabled from the
     * database.
     *
     * @author Christoffer Baur 1336394, Michael Mcmahon
     * @return all banner that are enabled
     * @throws SQLException
     * 
     */
    @Override
    public ArrayList<Banner> findAllBannersEnabled() throws SQLException {
        ArrayList<Banner> banner = new ArrayList<>();
        String query = "SELECT id, url, enabled FROM banners WHERE enabled = 1";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    banner.add(createBannerBean(resultSet));
                }
            }
        }
        return banner;
    }

    /**
     * This method allows a banner from the database to be updated with the
     * information from the banner parameter.
     *
     * @author Michael McMahon (1330815)
     * @param banner
     * @return the number of rows affected.
     * @throws SQLException
     */
    @Override
    public int updateBanner(Banner banner) throws SQLException {
        int result = 0;
        String query = "UPDATE banners SET url=?, enabled=? WHERE id=?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, banner.getUrl());
            ps.setBoolean(2, banner.getEnabled());
            ps.setInt(3, banner.getId());
            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     * Allows the deletion of a banner by its id from the database.
     *
     * @author Michael McMahon (1330815)
     * @param banner
     * @return the number of rows affected
     * @throws SQLException
     */
    @Override
    public int deleteBanner(Banner banner) throws SQLException {
        int result = 0;
        String query = "DELETE FROM banners WHERE id=?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, banner.getId());

            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     *
     * Allows the creation of a new rssfeed row in the database from the rssfeed
     * object pass in as a parameter.
     *
     * @author Michael McMahon (1330815)
     *
     * @param rssFeed
     * @return the number of rows affected.
     * @throws SQLException
     */
    @Override
    public int createRssFeed(RssFeed rssFeed) throws SQLException {
        int result = 0;
        String createQuery;
        createQuery = "INSERT INTO rssfeeds (url, enabled) VALUES (?, ?)";

        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(createQuery)) {
            ps.setString(1, rssFeed.getUrl());
            ps.setBoolean(2, rssFeed.getEnabled());

            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     * This method allows the retrieval of a rssfeed records from the database.
     *
     * @author Michael McMahon (1330815)
     * @return an arraylist of the found rssfeed records
     * @throws SQLException
     */
    @Override
    public ArrayList<RssFeed> findAllRssFeed() throws SQLException {
        ArrayList<RssFeed> rows = new ArrayList<>();
        String query = "SELECT id, url, enabled FROM rssfeeds";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createRssFeedBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * This method allows the retrieval of a rssfeed record by its id.
     *
     * @author Michael McMahon (1330815)
     * @param id
     * @return the rssfeed record that was found
     * @throws SQLException
     */
    @Override
    public RssFeed findRssFeedById(int id) throws SQLException {
        RssFeed rssFeed = new RssFeed();
        String query = "SELECT id, url, enabled FROM rssfeeds WHERE id = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    rssFeed = createRssFeedBean(resultSet);
                }
            }
        }
        return rssFeed;
    }

    /**
     * This method allows the retrieval of a rssfeed record by its matching url.
     *
     * @author Michael McMahon (1330815)
     * @param url
     * @return the matching rssfeed record, else an empty object.
     * @throws SQLException
     */
    @Override
    public RssFeed findRssFeedByUrl(String url) throws SQLException {
        RssFeed rssFeed = new RssFeed();
        String query = "SELECT id, url, enabled FROM rssfeeds WHERE url = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, url);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    rssFeed = createRssFeedBean(resultSet);
                }
            }
        }
        return rssFeed;
    }

    /**
     * This method allows the retrieval of a rssfeed record by its matching
     * enabled.
     *
     * @author Christoffer Baur 1336394
     * @param enabled
     * @return the matching rssfeed record, else an empty object.
     * @throws SQLException
     */
    @Override
    public ArrayList<RssFeed> findAllRssFeedsEnabled() throws SQLException {
        ArrayList<RssFeed> rssFeed = new ArrayList<>();
        String query = "SELECT id, url, enabled FROM rssfeeds WHERE enabled = true";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rssFeed.add(createRssFeedBean(resultSet));
                }
            }
        }
        return rssFeed;
    }

    @Override
    public int updateRssFeed(RssFeed rssFeed) throws SQLException {
        int result = 0;
        String query = "UPDATE rssfeeds SET url=?, enabled=? WHERE id=?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, rssFeed.getUrl());
            ps.setBoolean(2, rssFeed.getEnabled());
            ps.setInt(3, rssFeed.getId());
            result = ps.executeUpdate();
        }
        return result;
    }

    @Override
    public int deleteRssFeed(RssFeed rssFeed) throws SQLException {
        int result = 0;
        String query = "DELETE FROM rssfeeds WHERE id=?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, rssFeed.getId());

            result = ps.executeUpdate();
        }
        return result;
    }

    @Override
    public int createSurvey(Survey survey) throws SQLException {
        int result = 0;
        String createQuery;
        createQuery = "INSERT INTO surveys (enabled, question, answer1, answer2, answer3, answer1_count, answer2_count, answer3_count) VALUES (?,?,?,?,?,?,?,?)";

        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(createQuery)) {
            ps.setBoolean(1, survey.isEnabled());
            ps.setString(2, survey.getQuestion());
            ps.setString(3, survey.getAnswer1());
            ps.setString(4, survey.getAnswer2());
            ps.setString(5, survey.getAnswer3());
            ps.setInt(6, survey.getAnswer1Count());
            ps.setInt(7, survey.getAnswer2Count());
            ps.setInt(8, survey.getAnswer3Count());
            result = ps.executeUpdate();
        }
        return result;
    }

    @Override
    public ArrayList<Survey> findAllSurveys() throws SQLException {
        ArrayList<Survey> rows = new ArrayList<>();
        String query = "SELECT id, enabled, question, answer1, answer2, answer3, answer1_count, answer2_count, answer3_count FROM surveys";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createSurveyBean(resultSet));
                }
            }
        }
        return rows;
    }

    @Override
    public Survey findSurveyById(int id) throws SQLException {
        Survey survey = new Survey();
        String query = "SELECT id, enabled, question, answer1, answer2, answer3, answer1_count, answer2_count, answer3_count FROM surveys WHERE id = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    survey = createSurveyBean(resultSet);
                }
            }
        }
        return survey;
    }
    
    @Override
    public ArrayList<Survey> findAllSurveysEnabled() throws SQLException{
        ArrayList<Survey> rows = new ArrayList<>();
        String query = "SELECT id, enabled, question, answer1, answer2, answer3, answer1_count, answer2_count, answer3_count FROM surveys WHERE enabled = true";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createSurveyBean(resultSet));
                }
            }
        }
        return rows;
    }

    @Override
    public int updateSurvey(Survey survey) throws SQLException {
        int result = 0;
        String query = "UPDATE surveys SET enabled=?, question=?, answer1=?, answer2=?, answer3=?, answer1_count=?, answer2_count=?, answer3_count=? WHERE id=?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setBoolean(1, survey.isEnabled());
            ps.setString(2, survey.getQuestion());
            ps.setString(3, survey.getAnswer1());
            ps.setString(4, survey.getAnswer2());
            ps.setString(5, survey.getAnswer3());
            ps.setInt(6, survey.getAnswer1Count());
            ps.setInt(7, survey.getAnswer2Count());
            ps.setInt(8, survey.getAnswer3Count());
            ps.setInt(9, survey.getId());
            result = ps.executeUpdate();
        }
        return result;
    }

    @Override
    public int deleteSurvey(Survey survey) throws SQLException {
        int result = 0;
        String query = "DELETE FROM surveys WHERE id=?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, survey.getId());

            result = ps.executeUpdate();
        }
        return result;
    }
    
    /**
     * Creates a Book in the books table of the database
     *
     * @author Christoffer Baur 1336394
     * @param book Book to use to populate the table
     * @return result the number of rows created (1)
     * @throws java.sql.SQLException
     */
    @Override
    public int createBook(Book book) throws SQLException {
        int result = 0;
        String createQuery;
        createQuery = "INSERT INTO books (isbn, title, author, publisher,"
                + " pages, genre, formats, description, wholesale_price, list_price, "
                + "sale_price, file_size, date_published, date_received, date_removed) VALUES (?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(createQuery)) {
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setString(4, book.getPublisher());
            ps.setInt(5, book.getNumOfPages());
            ps.setString(6, book.getGenre());
            ps.setString(7, convertArrayList(book.getFormats()));
            ps.setString(8, book.getDescription());
            ps.setBigDecimal(9, book.getWholeSalePrice().setScale(2, RoundingMode.HALF_UP));
            ps.setBigDecimal(10, book.getListPrice().setScale(2, RoundingMode.HALF_UP));
            if (book.getSalePrice() != null) {
                ps.setBigDecimal(11, book.getSalePrice().setScale(2, RoundingMode.HALF_UP));
            } else {
                ps.setBigDecimal(11, new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP));
            }
            ps.setInt(12, book.getFileSize());
            ps.setTimestamp(13, Timestamp.valueOf(book.getPublishDate()));
            ps.setTimestamp(14, Timestamp.valueOf(book.getDateAddedToInventory()));
            if (book.getDateRemoved() != null) {
                ps.setTimestamp(15, Timestamp.valueOf(book.getDateRemoved()));
            } else {
                ps.setTimestamp(15, null);
            }

            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     * Finds all the books
     *
     * @author Christoffer Baur 1336394
     * @return rows ArrayList of books
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Book> findAllBooks() throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all the books that have not been removed
     *
     * @author Christoffer Baur 1336394
     * @return rows ArrayList of books
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Book> findAllBooksNotRemoved() throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE date_removed IS NULL";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all the book genres
     *
     * @author Christoffer Baur 1336394
     * @return rows ArrayList of genres
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<String> findAllBookGenres() throws SQLException {
        ArrayList<String> rows = new ArrayList<>();
        String query = "SELECT DISTINCT genre FROM books";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(resultSet.getString("genre"));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all the book authors
     *
     * @author Christoffer Baur 1336394
     * @return rows ArrayList of authors
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<String> findAllBookAuthors() throws SQLException {
        ArrayList<String> rows = new ArrayList<>();
        String query = "SELECT DISTINCT author FROM books";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(resultSet.getString("author"));
                }
            }
        }
        return rows;
    }
    
    /**
     * @Andrew
     * 
     * @return
     * @throws SQLException 
     */
    public ArrayList<String> findAllBookPublishers() throws SQLException {
        ArrayList<String> rows = new ArrayList<>();
        String query = "SELECT DISTINCT publisher FROM books";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(resultSet.getString("publisher"));
                }
            }
        }
        return rows;
    }
    
    /**
     * @author andrew
     * 
     * @return
     * @throws SQLException 
     */
    public ArrayList<Integer> findAllClientsAsId() throws SQLException {
        ArrayList<Integer> rows = new ArrayList<>();
        String query = "SELECT id FROM clients";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(resultSet.getInt("id"));
                }
            }
        }
        return rows;
    }

    /**
     * Finds a book by its Isbn
     *
     * @author Christoffer Baur 1336394
     * @param isbn String Isbn
     * @return book a Book
     * @throws java.sql.SQLException
     */
    @Override
    public Book findBookByIsbn(String isbn) throws SQLException {
        Book book = new Book();
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE isbn = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, isbn);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    book = createBookBean(resultSet);
                }
            }
        }
        return book;
    }

    /**
     * Finds all the books with a certain title
     *
     * @author Christoffer Baur 1336394
     * @param title String title
     * @return rows ArrayList of books
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Book> findBookByTitle(String title) throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String t = "%" + title + "%";
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE title LIKE ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, t);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all the books with a certain author
     *
     * @author Christoffer Baur 1336394
     * @param author String author
     * @return rows ArrayList of books
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Book> findBookByAuthor(String author) throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String a = "%" + author + "%";
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE author LIKE ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, a);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }
    
    /**
     * @author andrew
     * @param pub
     * @return
     * @throws SQLException 
     */
    public ArrayList<Book> findBookByPublisher(String pub) throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String a = "%" + pub + "%";
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE publisher LIKE ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, a);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }
    
    /**
     * Finds all the books with a certain number of pages
     *
     * @author Christoffer Baur 1336394
     * @param pages number of pages
     * @return rows ArrayList of books
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Book> findBookByPages(int pages) throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE pages = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, pages);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all the books with a certain genre
     *
     * @author Christoffer Baur 1336394
     * @param genre String genre
     * @return rows ArrayList of books
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Book> findBookByGenre(String genre) throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE genre = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, genre);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all the books with a certain file size
     *
     * @author Christoffer Baur 1336394
     * @param fileSize size of the file
     * @return rows ArrayList of books
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Book> findBookByFileSize(int fileSize) throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE file_size = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, fileSize);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all the books on sale
     *
     * @author Christoffer Baur 1336394
     * @return rows ArrayList of books
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Book> findBookBySale() throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE sale_price != 0.000";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }

    public ArrayList<Book> findBookBySaleLimit(int limit) throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE sale_price != 0.000 LIMIT ?,4 ";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, limit);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }
    
    /**
     * Finds all the books with a price less than specified
     *
     * @author Christoffer Baur 1336394
     * @param price highest price it can be
     * @return rows ArrayList of books
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Book> findBookByPriceLess(BigDecimal price) throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE list_price <= ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setBigDecimal(1, price);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all the books with a price more than specified
     *
     * @author Christoffer Baur 1336394
     * @param price lowest price it can be
     * @return rows ArrayList of books
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Book> findBookByPriceMore(BigDecimal price) throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE list_price >= ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setBigDecimal(1, price);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all the books with a price between what's specified
     *
     * @author Christoffer Baur 1336394
     * @param lowPrice lowest price it can be
     * @param highPrice highest price it can be
     * @return rows ArrayList of books
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Book> findBookByPriceBetween(BigDecimal lowPrice, BigDecimal highPrice) throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE list_price BETWEEN ? AND ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setBigDecimal(1, lowPrice);
            ps.setBigDecimal(2, highPrice);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all the books with a certain date published
     *
     * @author Christoffer Baur 1336394
     * @param date the publish date
     * @return rows ArrayList of books
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Book> findBookByDatePublished(LocalDateTime date) throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE date_published = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setTimestamp(1, Timestamp.valueOf(date));
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }
    
    /**
     * 
     * @param limit
     * @return
     * @throws SQLException 
     */
    @Override
    public ArrayList<Book> findBookOrderByDate(int limit) throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books order by date_published DESC LIMIT ?,4";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, limit);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all the books with a certain date added
     *
     * @author Christoffer Baur 1336394
     * @param date the date it got added to inventory
     * @return rows ArrayList of books
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Book> findBookByDateAdded(LocalDateTime date) throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE date_received = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setTimestamp(1, Timestamp.valueOf(date));
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all the books with a certain date removed
     *
     * @author Christoffer Baur 1336394
     * @param date the date it got removed from inventory
     * @return rows ArrayList of books
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Book> findBookByDateRemoved(LocalDateTime date) throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE date_removed = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setTimestamp(1, Timestamp.valueOf(date));
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Updates a book in the Books table
     *
     * @author Christoffer Baur 1336394
     * @param book the book to update
     * @return int number of rows updated
     * @throws java.sql.SQLException
     */
    @Override
    public int updateBook(Book book) throws SQLException {
        BigDecimal zero = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
        int result = 0;
        String query = "UPDATE books SET title=?, author=?, publisher=?, pages=?, genre=?, formats=?, description=?, wholesale_price=?, list_price=?, "
                + "sale_price=?, file_size=?, date_published=?, date_received=?, date_removed=? WHERE isbn=?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getPublisher());
            ps.setInt(4, book.getNumOfPages());
            ps.setString(5, book.getGenre());
            ps.setString(6, convertArrayList(book.getFormats()));
            ps.setString(7, book.getDescription());
            ps.setBigDecimal(8, book.getWholeSalePrice());
            ps.setBigDecimal(9, book.getListPrice());
            if (book.getSalePrice() != null) {
                ps.setBigDecimal(10, book.getSalePrice().setScale(2, RoundingMode.HALF_UP));
            } else {
                ps.setBigDecimal(10, zero);
            }
            ps.setInt(11, book.getFileSize());
            ps.setTimestamp(12, Timestamp.valueOf(book.getPublishDate()));
            ps.setTimestamp(13, Timestamp.valueOf(book.getDateAddedToInventory()));
            if (book.getDateRemoved() != null) {
                ps.setTimestamp(14, Timestamp.valueOf(book.getDateRemoved()));
            } else {
                ps.setTimestamp(14, null);
            }
            ps.setString(15, book.getIsbn());

            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     * Removed a Book in the Books table by setting the removed date
     *
     * @author Christoffer Baur 1336394
     * @param book the book to remove
     * @return int number of rows updated
     * @throws java.sql.SQLException
     */
    @Override
    public int removeBook(Book book) throws SQLException {
        int result = 0;
        String query = "UPDATE books set date_removed=? WHERE isbn=?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(2, book.getIsbn());

            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     * Creates a BookReview in the reviews table
     *
     * @author Christoffer Baur 1336394
     * @param bookReview the book review
     * @return int number of rows updated
     * @throws java.sql.SQLException
     */
    @Override
    public int createBookReview(BookReview bookReview) throws SQLException {
        int result = 0;
        String createQuery;
        createQuery = "INSERT INTO reviews (isbn, client_id, review_date,"
                + " rating, text, status) VALUES (?,?,?,"
                + "?,?,?)";

        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(createQuery)) {
            ps.setString(1, bookReview.getIsbn());
            ps.setInt(2, bookReview.getClientId());
            ps.setTimestamp(3, Timestamp.valueOf(bookReview.getReviewDate()));
            ps.setInt(4, bookReview.getReviewScore());
            ps.setString(5, bookReview.getReview());
            ps.setString(6, Character.toString(bookReview.getStatus()));

            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     * Finds all the BookReviews
     *
     * @author Christoffer Baur 1336394
     * @return rows ArrayList of BookReviews
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<BookReview> findAllReviews() throws SQLException {
        ArrayList<BookReview> rows = new ArrayList<>();
        String query = "SELECT id, isbn, client_id, review_date, rating, text, "
                + "status FROM reviews";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookReviewBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds a BookReview by id
     *
     * @author Christoffer Baur 1336394
     * @param id the id of the book review
     * @return bookReview a BookReview
     * @throws java.sql.SQLException
     */
    @Override
    public BookReview findReviewById(int id) throws SQLException {
        BookReview bookReview = new BookReview();
        String query = "SELECT id, isbn, client_id, review_date, rating, text, "
                + "status FROM reviews WHERE id = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    bookReview = createBookReviewBean(resultSet);
                }
            }
        }
        return bookReview;
    }

    /**
     * Finds all BookReviews by Isbn
     *
     * @author Christoffer Baur 1336394
     * @param isbn the Isbn of the book reviews
     * @return rows ArrayList of BookReview
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<BookReview> findReviewByIsbn(String isbn) throws SQLException {
        ArrayList<BookReview> rows = new ArrayList<>();
        String query = "SELECT id, isbn, client_id, review_date, rating, text, "
                + "status FROM reviews WHERE isbn = ? ORDER by review_date";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, isbn);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookReviewBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all BookReviews by client id
     *
     * @author Christoffer Baur 1336394
     * @param clientId the client id of the book review
     * @return rows ArrayList of BookReview
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<BookReview> findReviewByClientId(int clientId) throws SQLException {
        ArrayList<BookReview> rows = new ArrayList<>();
        String query = "SELECT id, isbn, client_id, review_date, rating, text, "
                + "status FROM reviews WHERE client_id = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, clientId);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookReviewBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all BookReviews by date
     *
     * @author Christoffer Baur 1336394
     * @param date the date of the book reviews
     * @return rows ArrayList of BookReview
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<BookReview> findReviewByDate(LocalDateTime date) throws SQLException {
        ArrayList<BookReview> rows = new ArrayList<>();
        String query = "SELECT id, isbn, client_id, review_date, rating, text, "
                + "status FROM reviews WHERE review_date = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookReviewBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all BookReviews by rating
     *
     * @author Christoffer Baur 1336394
     * @param rating the number of stars
     * @return rows ArrayList of BookReview
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<BookReview> findReviewByRating(int rating) throws SQLException {
        ArrayList<BookReview> rows = new ArrayList<>();
        String query = "SELECT id, isbn, client_id, review_date, rating, text, "
                + "status FROM reviews WHERE rating = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, rating);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookReviewBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all BookReviews by status
     *
     * @author Christoffer Baur 1336394
     * @param status the status of the review (A or P)
     * @return rows ArrayList of BookReview
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<BookReview> findReviewByStatus(char status) throws SQLException {
        ArrayList<BookReview> rows = new ArrayList<>();
        String query = "SELECT id, isbn, client_id, review_date, rating, text, "
                + "status FROM reviews WHERE status = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, Character.toString(status));
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookReviewBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Updates a BookReview in the reviews table
     *
     * @author Christoffer Baur 1336394
     * @param review the BookReview
     * @return int number of rows updated
     * @throws java.sql.SQLException
     */
    @Override
    public int updateReview(BookReview review) throws SQLException {
        int result = 0;
        String query = "UPDATE reviews SET isbn=?, client_id=?, review_date=?, "
                + "rating=?, text=?, status=? WHERE id=?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, review.getIsbn());
            ps.setInt(2, review.getClientId());
            ps.setTimestamp(3, Timestamp.valueOf(review.getReviewDate()));
            ps.setInt(4, review.getReviewScore());
            ps.setString(5, review.getReview());
            ps.setString(6, String.valueOf(review.getStatus()));
            ps.setInt(7, review.getId());

            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     * Deletes a BookReview in the reviews table
     *
     * @author Christoffer Baur 1336394
     * @param review the BookReview
     * @return int number of rows updated
     * @throws java.sql.SQLException
     */
    @Override
    public int deleteReview(BookReview review) throws SQLException {
        int result = 0;
        String query = "DELETE FROM reviews WHERE id=?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, review.getId());

            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     * Creates a Client in the clients table
     *
     * @author Christoffer Baur 1336394
     * @param client the Client to create
     * @return int number of rows updated
     * @throws java.sql.SQLException
     */
    @Override
    public int createClient(Client client) throws SQLException {
        int result = 0;
        String createQuery;
        createQuery = "INSERT INTO clients (title, first_name, last_name, company,"
                + " address_1, address_2, city, province, country, postal_code, home_phone,"
                + " cell_phone, email, password, isManager) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(createQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, client.getTitle());
            ps.setString(2, client.getFirstName());
            ps.setString(3, client.getLastName());
            ps.setString(4, client.getCompanyName());
            ps.setString(5, client.getAddress1());
            ps.setString(6, client.getAddress2());
            ps.setString(7, client.getCity());
            ps.setString(8, client.getProvince());
            ps.setString(9, client.getCountry());
            ps.setString(10, client.getPostalCode());
            ps.setString(11, client.getHomeTelephoneNumber());
            ps.setString(12, client.getCellTelephoneNumber());
            ps.setString(13, client.getEmail());
            ps.setString(14, client.getPassword());
            ps.setBoolean(15, client.isIsManager());
            
            result = ps.executeUpdate();
            ResultSet set = ps.getGeneratedKeys();
            if(set.next())
                client.setId(set.getInt(1));
            

        }
        return result;
    }

    /**
     * Finds all clients
     *
     * @author Christoffer Baur 1336394
     * @return rows ArrayList of Client
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Client> findAllClients() throws SQLException {
        ArrayList<Client> rows = new ArrayList<>();
        String query = "SELECT id, title, first_name, last_name, company, address_1, address_2, "
                + "city, province, country, postal_code, home_phone, cell_phone, email, password, isManager FROM clients";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createClientBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds a CLient by id
     *
     * @author Christoffer Baur 1336394
     * @param id the id of the client
     * @return client a Client
     * @throws java.sql.SQLException
     */
    @Override
    public Client findClientById(int id) throws SQLException {
        Client client = new Client();
        String query = "SELECT id, title, first_name, last_name, company, address_1, address_2, "
                + "city, province, country, postal_code, home_phone, cell_phone, email, password, isManager FROM clients WHERE id = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    client = createClientBean(resultSet);
                }
            }
        }
        return client;
    }

    /**
     * Finds all clients by first name
     *
     * @author Christoffer Baur 1336394
     * @param firstName the first name of the client
     * @return rows ArrayList of Client
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Client> findClientByFName(String firstName) throws SQLException {
        ArrayList<Client> rows = new ArrayList<>();
        String query = "SELECT id, title, first_name, last_name, company, address_1, address_2, "
                + "city, province, country, postal_code, home_phone, cell_phone, email, password, isManager FROM clients"
                + " WHERE first_name = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, firstName);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createClientBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all clients by last name
     *
     * @author Christoffer Baur 1336394
     * @param lastName the last name of the client
     * @return rows ArrayList of Client
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Client> findClientByLName(String lastName) throws SQLException {
        ArrayList<Client> rows = new ArrayList<>();
        String query = "SELECT id, title, first_name, last_name, company, address_1, address_2, "
                + "city, province, country, postal_code, home_phone, cell_phone, email, password, isManager FROM clients"
                + " WHERE last_name = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, lastName);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createClientBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds Client by email
     *
     * @author Christoffer Baur 1336394
     * @param email the email of the client
     * @return client a Client
     * @throws java.sql.SQLException
     */
    @Override
    public Client findClientByEmail(String email) throws SQLException {
        Client client = null;
        String query = "SELECT id, title, first_name, last_name, company, address_1, address_2, "
                + "city, province, country, postal_code, home_phone, cell_phone, email, password, isManager FROM clients"
                + " WHERE email = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    client = createClientBean(resultSet);
                }
            }
        }
        return client;
    }

    /**
     * Updates a Client in the clients table
     *
     * @author Christoffer Baur 1336394
     * @param client the client to update
     * @return int number of rows updated
     * @throws java.sql.SQLException
     */
    @Override
    public int updateClient(Client client) throws SQLException {
        int result = 0;
        String query = "UPDATE clients SET title=?, first_name=?, last_name=?, company=?, address_1=?, address_2=?, "
                + "city=?, province=?, country=?, postal_code=?, home_phone=?, cell_phone=?, email=?, password=?, isManager=? WHERE id=?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, client.getTitle());
            ps.setString(2, client.getFirstName());
            ps.setString(3, client.getLastName());
            if (client.getCompanyName() != null) {
                ps.setString(4, client.getCompanyName());
            }
            ps.setString(5, client.getAddress1());
            if (client.getAddress2() != null) {
                ps.setString(6, client.getAddress2());
            }
            ps.setString(7, client.getCity());
            ps.setString(8, client.getProvince());
            ps.setString(9, client.getCountry());
            ps.setString(10, client.getPostalCode());
            if (client.getHomeTelephoneNumber() != null) {
                ps.setString(11, client.getHomeTelephoneNumber());
            }
            if (client.getCellTelephoneNumber() != null) {
                ps.setString(12, client.getCellTelephoneNumber());
            }
            ps.setString(13, client.getEmail());
            ps.setString(14, client.getPassword());
            ps.setBoolean(15, client.isIsManager());
            ps.setInt(16, client.getId());

            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     * Deletes a Client in the clients table
     *
     * @author Christoffer Baur 1336394
     * @param client the client to delete
     * @return int number of rows updated
     * @throws java.sql.SQLException
     */
    @Override
    public int deleteClient(Client client) throws SQLException {
        int result = 0;
        String query = "DELETE FROM clients WHERE id=?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, client.getId());

            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     * Creates ab Invoice in the invoices table
     *
     * @author Christoffer Baur 1336394
     * @param invoice the Invoice to create
     * @return int number of rows updated
     * @throws java.sql.SQLException
     */
    @Override
    public int createInvoice(Invoice invoice) throws SQLException {
        int result = 0;
        String createQuery = "INSERT INTO invoices (client_id, sale_date, net_value, gross_value, date_removed) VALUES (?,?,?,?,?)";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(createQuery,PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, invoice.getClientId());
            ps.setTimestamp(2, Timestamp.valueOf(invoice.getSaleDate()));
            ps.setBigDecimal(3, invoice.getTotalNetValueOfSale());
            ps.setBigDecimal(4, invoice.getTotalGrossSale());
            if (invoice.getDateRemoved() != null) {
                ps.setTimestamp(5, Timestamp.valueOf(invoice.getDateRemoved()));
            } else {
                ps.setTimestamp(5, null);
            }
           
            result = ps.executeUpdate();
            ResultSet set = ps.getGeneratedKeys();
            if(set.next())
                invoice.setId(set.getInt(1));
            System.out.println("the databse invoice id is: " + invoice.getId());

        }
        return result;

    }

    /**
     * Finds all invoices
     *
     * @author Christoffer Baur 1336394
     * @return rows ArrayList of Client
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Invoice> findAllInvoices() throws SQLException {
        ArrayList<Invoice> rows = new ArrayList<>();
        String query = "SELECT id, client_id, sale_date, net_value, gross_value, date_removed FROM invoices";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createInvoiceBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all invoices by id
     *
     * @author Christoffer Baur 1336394
     * @param id the id of the Invoice
     * @return invoice the Invoice
     * @throws java.sql.SQLException
     */
    @Override
    public Invoice findInvoiceById(int id) throws SQLException {
        Invoice invoice = new Invoice();
        String query = "SELECT id, client_id, sale_date, net_value, gross_value, date_removed FROM invoices WHERE id = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    invoice = createInvoiceBean(resultSet);
                }
            }
        }
        return invoice;
    }
    
        /**
     * Finds all invoices by id
     *
     * @author Andrew Villavera
     * @param id the id of the Invoice
     * @return invoice the Invoice
     * @throws java.sql.SQLException
     */
    public List<Invoice> findInvoiceByIdWithDate(int id, Date date1, Date date2) throws SQLException {
        List<Invoice> invoice = new ArrayList<Invoice>();
        String query = "SELECT id, client_id, sale_date, net_value, gross_value, date_removed FROM invoices WHERE id = ? and sale_date between ? and ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setDate(2, date1);
            ps.setDate(3, date2);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                   invoice.add(createInvoiceBean(resultSet));
                }
            }
        }
        return invoice;
    }

    /**
     * Finds all invoices by sale date
     *
     * @author Christoffer Baur 1336394
     * @param date the date of the sale
     * @return rows ArrayList of Invoice
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Invoice> findInvoiceBySaleDate(LocalDateTime date) throws SQLException {
        ArrayList<Invoice> rows = new ArrayList<>();
        String query = "SELECT id, client_id, sale_date, net_value, gross_value, date_removed FROM invoices WHERE sale_date = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setTimestamp(1, Timestamp.valueOf(date));
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createInvoiceBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all invoices by client id
     *
     * @author Christoffer Baur 1336394
     * @param clientId the client id of the Invoice
     * @return rows ArrayList of Invoice
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Invoice> findInvoiceByClientId(int clientId) throws SQLException {
        ArrayList<Invoice> rows = new ArrayList<>();
        String query = "SELECT id, client_id, sale_date, net_value, gross_value, date_removed FROM invoices WHERE client_id = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, clientId);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createInvoiceBean(resultSet));
                }
            }
        }
        return rows;
    }
    
    /**
     * author andrew
     * 
     * @param clientId
     * @param date1
     * @param date2
     * @return
     * @throws SQLException 
     */
    public List<Invoice> findInvoiceByClientIdWithDate(int clientId, Date date1, Date date2) throws SQLException {
        List<Invoice> invoice = new ArrayList<Invoice>();
        String query = "SELECT id, client_id, sale_date, net_value, gross_value, date_removed FROM invoices WHERE client_id = ? and sale_date between ? and ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, clientId);
            ps.setDate(2, date1);
            ps.setDate(3, date2);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                   invoice.add(createInvoiceBean(resultSet));
                }
            }
        }
        return invoice;
    }

    /**
     * Updates an Invoice in the invoices table
     *
     * @author Christoffer Baur 1336394
     * @param invoice the Invoice to update
     * @return int number of rows updated
     * @throws java.sql.SQLException
     */
    @Override
    public int updateInvoice(Invoice invoice) throws SQLException {
        int result = 0;
        String query = "UPDATE invoices SET client_id=?, sale_date=?, net_value=?, gross_value=?, date_removed=? "
                + "WHERE id = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, invoice.getClientId());
            ps.setTimestamp(2, Timestamp.valueOf(invoice.getSaleDate()));
            ps.setBigDecimal(3, invoice.getTotalNetValueOfSale());
            ps.setBigDecimal(4, invoice.getTotalGrossSale());
            if (invoice.getDateRemoved() != null) {
                ps.setTimestamp(5, Timestamp.valueOf(invoice.getDateRemoved()));
            } else {
                ps.setTimestamp(5, null);
            }
            ps.setInt(6, invoice.getId());

            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     * Removes an invoice from the invoices table. Sets the date removed.
     *
     * @author Christoffer Baur 1336394
     * @param invoice the invoice to delete
     * @return int number of rows updated
     * @throws java.sql.SQLException
     */
    @Override
    public int removeInvoice(Invoice invoice) throws SQLException {
        int result = 0;
        String query = "UPDATE invoices SET date_removed=? WHERE id = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(2, invoice.getId());
            

            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     * Creates a InvoiceDetails in the details table
     *
     * @author Christoffer Baur 1336394
     * @param details the InvoiceDetails to create
     * @return int number of rows updated
     * @throws java.sql.SQLException
     */
    @Override
    public int createDetails(InvoiceDetails details) throws SQLException {
        int result = 0;
        String createQuery = "INSERT INTO details (invoice_id, isbn, price, PST, GST, HST, date_removed) VALUES (?,?,?,?,?,?,?)";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(createQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, details.getInvoiceId());
            ps.setString(2, details.getIsbn());
            ps.setBigDecimal(3, details.getBookPrice());
            ps.setBigDecimal(4, details.getPstTax());
            ps.setBigDecimal(5, details.getGstTax());
            ps.setBigDecimal(6, details.getHstTax());
            if (details.getDateRemoved() != null) {
                ps.setTimestamp(7, Timestamp.valueOf(details.getDateRemoved()));
            } else {
                ps.setTimestamp(7, null);
            }
            result = ps.executeUpdate();
            
            ResultSet set = ps.getGeneratedKeys();
            if(set.next())
                details.setId(set.getInt(1));
            

        }
        return result;
    }

    /**
     * Finds all InvoiceDetails
     *
     * @author Christoffer Baur 1336394
     * @return rows ArrayList of InvoiceDetails
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<InvoiceDetails> findAllDetails() throws SQLException {
        ArrayList<InvoiceDetails> rows = new ArrayList<>();
        String query = "SELECT id, invoice_id, isbn, price, PST, GST, HST, date_removed FROM details";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createDetailsBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Finds all InvoiceDetails by id
     *
     * @author Christoffer Baur 1336394
     * @param id the id of the InvoiceDetails
     * @return invoiceDetails the InvoiceDetails
     * @throws java.sql.SQLException
     */
    @Override
    public InvoiceDetails findDetailsById(int id) throws SQLException {
        InvoiceDetails details = new InvoiceDetails();
        String query = "SELECT id, invoice_id, isbn, price, PST, GST, HST, date_removed FROM details WHERE id = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    details = createDetailsBean(resultSet);
                }
            }
        }
        return details;
    }

    /**
     * Finds all InvoiceDetails by invoice id
     *
     * @author Christoffer Baur 1336394
     * @param invoiceId the invoice id of the InvoiceDetails
     * @return rows ArrayList of InvoiceDetails
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<InvoiceDetails> findDetailsByInvoiceId(int invoiceId) throws SQLException {
        ArrayList<InvoiceDetails> rows = new ArrayList<>();
        String query = "SELECT id, invoice_id, isbn, price, PST, GST, HST, date_removed FROM details WHERE invoice_id = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, invoiceId);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createDetailsBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     * Updates an InvoiceDetails in the details table
     *
     * @author Christoffer Baur 1336394
     * @param details the InvoiceDetails to update
     * @return int number of rows updated
     * @throws java.sql.SQLException
     */
    @Override
    public int updateDetails(InvoiceDetails details) throws SQLException {
        int result = 0;
        String query = "UPDATE details SET invoice_id=?, isbn=?, price=?, PST=?, GST=?, HST=?, date_removed=? WHERE id = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, details.getInvoiceId());
            ps.setString(2, details.getIsbn());
            ps.setBigDecimal(3, details.getBookPrice());
            ps.setBigDecimal(4, details.getPstTax());
            ps.setBigDecimal(5, details.getGstTax());
            ps.setBigDecimal(6, details.getHstTax());
            if (details.getDateRemoved() != null) {
                ps.setTimestamp(7, Timestamp.valueOf(details.getDateRemoved()));
            } else {
                ps.setTimestamp(7, null);
            }
            ps.setInt(8, details.getId());

            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     * Removes an InvoiceDetals from the details table. Sets the date removed.
     *
     * @author Christoffer Baur 1336394
     * @param details the InvoiceDetails to delete
     * @return int number of rows updated
     * @throws java.sql.SQLException
     */
    @Override
    public int removeDetails(InvoiceDetails details) throws SQLException {
        int result = 0;
        String query = "UPDATE details SET date_removed=? WHERE id = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(2, details.getId());

            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     * Finds the Tax by province
     *
     * @author Christoffer Baur 1336394
     * @param province the province
     * @return int number of rows updated
     * @throws java.sql.SQLException
     */
    @Override
    public Tax findTaxByProvince(String province) throws SQLException {
        Tax tax = new Tax();
        String query = "SELECT id, province, gst, pst, hst, combined_rate FROM taxes WHERE province = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, province);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    tax = createTaxBean(resultSet);
                }
            }
        }
        return tax;
    }

    /**
     * Finds the Tax by province
     *
     * @author Christoffer Baur 1336394
     * @param province the province
     * @return int number of rows updated
     * @throws java.sql.SQLException
     *//*
    @Override
    public Tax findTaxByProvince(String province) throws SQLException {
        Tax tax = new Tax();
        String query = "SELECT id, province, gst, pst, hst, combined_rate FROM taxes WHERE province = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, province);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    tax = createTaxBean(resultSet);
                }
            }
        }
        return tax;
    }*/

    /**
     * Creates a Book Bean
     *
     * @param resultSet the ResultSet from db query
     * @return a Book bean
     * @throws SQLException
     * @author Christoffer Baur(1336394)
     */
    private Book createBookBean(ResultSet resultSet) throws SQLException {
        Book book = new Book();
//        System.out.println(resultSet.getString("isbn"));
//        System.out.println(resultSet.getString("title"));
//        System.out.println(resultSet.getString("author"));
//        System.out.println(resultSet.getString("publisher"));
//        System.out.println(resultSet.getInt("pages"));
//        System.out.println(resultSet.getString("genre"));
//        System.out.println((Arrays.asList(resultSet.getString("formats").split(";"))));
//        System.out.println(resultSet.getString("description"));
//        System.out.println(resultSet.getBigDecimal("wholesale_price"));
//        System.out.println(resultSet.getBigDecimal("list_price"));
//        System.out.println(resultSet.getBigDecimal("sale_price"));
//        System.out.println(resultSet.getInt("file_size"));
//        System.out.println(resultSet.getTimestamp("date_published").toLocalDateTime());
//        System.out.println(resultSet.getTimestamp("date_received").toLocalDateTime());
//        System.out.println(resultSet.getTimestamp("date_removed"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setPublisher(resultSet.getString("publisher"));
        book.setNumOfPages(resultSet.getInt("pages"));
        book.setGenre(resultSet.getString("genre"));
        book.setFormats(new ArrayList<>(Arrays.asList(resultSet.getString("formats").split(";"))));
        book.setDescription(resultSet.getString("description"));
        book.setWholeSalePrice((resultSet.getBigDecimal("wholesale_price")).setScale(2, RoundingMode.HALF_UP));
        book.setListPrice((resultSet.getBigDecimal("list_price")).setScale(2, RoundingMode.HALF_UP));
        if (resultSet.getBigDecimal("sale_price") != null) {
            book.setSalePrice((resultSet.getBigDecimal("sale_price")).setScale(2, RoundingMode.HALF_UP));
        } else {
            book.setSalePrice(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP));
        }

        book.setFileSize(resultSet.getInt("file_size"));
        book.setPublishDate(resultSet.getTimestamp("date_published").toLocalDateTime());
        book.setDateAddedToInventory(resultSet.getTimestamp("date_received").toLocalDateTime());
        if (resultSet.getTimestamp("date_removed") != null) {
            book.setDateRemoved(resultSet.getTimestamp("date_removed").toLocalDateTime());
        } else {
            book.setDateRemoved(null);
        }

        return book;
    }

    /**
     * Creates a BookReview bean
     *
     * @param resultSet the ResultSet from db query
     * @return a BookReview bean
     * @throws SQLException
     * @author Christoffer Baur(1336394)
     */
    private BookReview createBookReviewBean(ResultSet resultSet) throws SQLException {
//        System.out.println(resultSet.getInt("id"));
//        System.out.println(resultSet.getString("isbn"));
//        System.out.println(resultSet.getInt("client_id"));
//        System.out.println(resultSet.getTimestamp("review_date").toLocalDateTime());
//        System.out.println(resultSet.getString("client_fname"));
//        System.out.println(resultSet.getString("client_lname"));
//        System.out.println(resultSet.getInt("rating"));
//        System.out.println(resultSet.getString("text"));
//        System.out.println(resultSet.getString("status"));

        BookReview bookReview = new BookReview();
        bookReview.setId(resultSet.getInt("id"));
        bookReview.setIsbn(resultSet.getString("isbn"));
        bookReview.setClientId(resultSet.getInt("client_id"));
        bookReview.setReviewDate(resultSet.getTimestamp("review_date").toLocalDateTime());
        bookReview.setReviewScore(resultSet.getInt("rating"));
        bookReview.setReview(resultSet.getString("text"));
        bookReview.setStatus(resultSet.getString("status").charAt(0));

        return bookReview;
    }

    /**
     * Creates a Client Bean
     *
     * @param resultSet the ResultSet from db query
     * @return a Client bean
     * @throws SQLException
     * @author Christoffer Baur(1336394)
     */
    private Client createClientBean(ResultSet resultSet) throws SQLException {
        Client client = new Client();
        client.setId(resultSet.getInt("id"));
        client.setTitle(resultSet.getString("title"));
        client.setFirstName(resultSet.getString("first_name"));
        client.setLastName(resultSet.getString("last_name"));
        client.setCompanyName(resultSet.getString("company"));
        client.setAddress1(resultSet.getString("address_1"));
        client.setAddress2(resultSet.getString("address_2"));
        client.setCity(resultSet.getString("city"));
        client.setProvince(resultSet.getString("province"));
        client.setCountry(resultSet.getString("country"));
        client.setPostalCode(resultSet.getString("postal_code"));
        client.setHomeTelephoneNumber(resultSet.getString("home_phone"));
        client.setCellTelephoneNumber(resultSet.getString("cell_phone"));
        client.setEmail(resultSet.getString("email"));
        client.setPassword(resultSet.getString("password"));
        client.setIsManager(resultSet.getBoolean("isManager"));

        return client;
    }

    /**
     * Creates a Invoice Bean
     *
     * @param resultSet the ResultSet from db query
     * @return an Invoice bean
     * @throws SQLException
     * @author Christoffer Baur(1336394)
     */
    private Invoice createInvoiceBean(ResultSet resultSet) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setId(resultSet.getInt("id"));
        invoice.setClientId(resultSet.getInt("client_id"));
        invoice.setSaleDate(resultSet.getTimestamp("sale_date").toLocalDateTime());
        invoice.setTotalNetValueOfSale(resultSet.getBigDecimal("net_value").setScale(2, RoundingMode.HALF_UP));
        invoice.setTotalGrossSale(resultSet.getBigDecimal("gross_value").setScale(2, RoundingMode.HALF_UP));
        if (resultSet.getTimestamp("date_removed") != null) {
            invoice.setDateRemoved(resultSet.getTimestamp("date_removed").toLocalDateTime());
        } else {
            invoice.setDateRemoved(null);
        }
        return invoice;
    }

    /**
     * Creates an InvoiceDetails Bean
     *
     * @param resultSet the ResultSet from db query
     * @return an InvoiceDetails bean
     * @throws SQLException
     * @author Christoffer Baur(1336394)
     */
    private InvoiceDetails createDetailsBean(ResultSet resultSet) throws SQLException {
        InvoiceDetails details = new InvoiceDetails();
        details.setId(resultSet.getInt("id"));
        details.setInvoiceId(resultSet.getInt("invoice_id"));
        details.setIsbn(resultSet.getString("isbn"));
        details.setBookPrice(resultSet.getBigDecimal("price").setScale(2, RoundingMode.HALF_UP));
        details.setPstTax(resultSet.getBigDecimal("PST").setScale(3, RoundingMode.HALF_UP));
        details.setGstTax(resultSet.getBigDecimal("GST").setScale(3, RoundingMode.HALF_UP));
        details.setHstTax(resultSet.getBigDecimal("HST").setScale(3, RoundingMode.HALF_UP));
        if (resultSet.getTimestamp("date_removed") != null) {
            details.setDateRemoved(resultSet.getTimestamp("date_removed").toLocalDateTime());
        } else {
            details.setDateRemoved(null);
        }
        
        return details;
    }

    /**
     *
     * @param resultSet the ResultSet from db query
     * @return an InvoiceDetails bean
     * @throws SQLException
     * @author Christoffer Baur(1336394)
     */
    private Tax createTaxBean(ResultSet resultSet) throws SQLException {
        Tax tax = new Tax();
        tax.setId(resultSet.getInt("id"));
        tax.setProvince(resultSet.getString("province"));
        tax.setGst(resultSet.getBigDecimal("gst").setScale(3, RoundingMode.HALF_UP));
        tax.setPst(resultSet.getBigDecimal("pst").setScale(3, RoundingMode.HALF_UP));
        tax.setHst(resultSet.getBigDecimal("hst").setScale(3, RoundingMode.HALF_UP));
        tax.setCombinedRate(resultSet.getBigDecimal("combined_rate").setScale(3, RoundingMode.HALF_UP));

        return tax;
    }

    private Banner createBannerBean(ResultSet set) throws SQLException {
        Banner banner = new Banner();
        banner.setId(set.getInt("id"));
        banner.setUrl(set.getString("url"));
        banner.setEnabled(set.getBoolean("enabled"));
        return banner;
    }

    private RssFeed createRssFeedBean(ResultSet set) throws SQLException {
        RssFeed feed = new RssFeed();
        feed.setId(set.getInt("id"));
        feed.setUrl(set.getString("url"));
        feed.setEnabled(set.getBoolean("enabled"));
        return feed;
    }

    private Survey createSurveyBean(ResultSet set) throws SQLException {
        Survey survey = new Survey();
        survey.setId(set.getInt("id"));
        survey.setEnabled(set.getBoolean("enabled"));
        survey.setQuestion(set.getString("question"));
        survey.setAnswer1(set.getString("answer1"));
        survey.setAnswer2(set.getString("answer2"));
        survey.setAnswer3(set.getString("answer3"));
        survey.setAnswer1Count(set.getInt("answer1_count"));
        survey.setAnswer2Count(set.getInt("answer2_count"));
        survey.setAnswer3Count(set.getInt("answer3_count"));
        return survey;
    }

    /**
     * 
     * Andrew
     * @param search
     * @return 
     * @throws java.sql.SQLException
     */
    @Override
    public ArrayList<Book> findBooksBySearch(String search) throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE date_removed is null and (title like ? OR author like ? OR description like ? OR publisher like ?)";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, "%" + search + "%");
            ps.setString(2, "%" + search + "%");
            ps.setString(3, "%" + search + "%");
            ps.setString(4, "%" + search + "%");
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     *
     * Alexandre
     * @param genre
     * @param limit
     * @return 
     * @throws java.sql.SQLException
     */
    public ArrayList<Book> findBooksByLikeGenre(String genre, int limit) throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE genre like ? LIMIT ?,4";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, "%" + genre + "%");
            ps.setInt(2, limit);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }
    
    /**
     *
     * Alexandre
     * @param genre
     * @param limit
     * @return 
     * @throws java.sql.SQLException
     */
    public ArrayList<Book> findBooksByLikeGenreBestSellers(String genre) throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String query = "SELECT b.isbn, title, author, publisher, pages, genre, formats, description,"
                +" wholesale_price, list_price, sale_price, file_size, date_published, date_received, b.date_removed,"
                +" count(*) avgRating FROM books b left join details d on b.isbn = d.isbn WHERE genre like ? group by b.isbn order by avgRating desc LIMIT 0,4";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + genre + "%");
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     *
     * Alexandre. Andrew
     * @param genre
     * @return 
     * @throws java.sql.SQLException
     */
    public ArrayList<Book> findBooksByLikeGenreNoLimit(String genre) throws SQLException {
        ArrayList<Book> rows = new ArrayList<>();
        String query = "SELECT isbn, title, author, publisher, pages, genre, formats, description, wholesale_price, list_price, sale_price, "
                + "file_size, date_published, date_received, date_removed FROM books WHERE genre like ? and date_removed is null";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, "%" + genre + "%");
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createBookBean(resultSet));
                }
            }
        }
        return rows;
    }

    /**
     *
     * Alexandre
     * @return 
     * @throws java.sql.SQLException
     */
    public ArrayList<String> findUniqueGenre() throws SQLException {
        ArrayList<String> rows = new ArrayList<>();
        String query = "SELECT DISTINCT genre FROM books ORDER BY RAND()";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(resultSet.getString(1));
                }
            }
        }
        return rows;
    }
    /**
     * author andrew
     * 
     * @param isbn
     * @return
     * @throws SQLException 
     */
    public ArrayList<InvoiceDetails> findDetailsByIsbn(String isbn) throws SQLException{
        ArrayList<InvoiceDetails> details = new ArrayList<>();
        String query = "SELECT id, invoice_id, isbn, price, PST, GST, HST, date_removed FROM details WHERE isbn = ?";
        try (Connection connection = bookSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, isbn);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    details.add(createDetailsBean(resultSet));
                }
            }
        }
        return details;
    }
    

    /**
     * To help convert Lists from database and etc. when using the to string
     *
     * @param list
     * @return
     * @author Christoffer Baur 1336394
     */
    private String convertArrayList(ArrayList<String> list) {
        return list.toString().replaceAll("\\[|\\]|[ ]", "").replace(',', ';');
    }
}
