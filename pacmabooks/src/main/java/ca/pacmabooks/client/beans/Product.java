
package ca.pacmabooks.client.beans;

import ca.pacmabooks.client.backingbeans.CarousselBackingBean;
import ca.pacmabooks.client.business.controllers.util.CookieHelper;
import ca.pacmabooks.client.persistence.DaoImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Alexandre
 */
@Named
@SessionScoped
public class Product implements Serializable {

    private ArrayList<BookReview> reviews;

    private Book book;

    private String rate;

    @Inject
    DaoImpl dao;

    public Product() {
        book = null;
    }

    public String show(String isbn) {
        reviews = new ArrayList<>();
        try {
            book = dao.findBookByIsbn(isbn);
            ArrayList<BookReview> temp = null;
            temp = dao.findReviewByIsbn(isbn);
            for (BookReview review: temp){
                if (review.getStatus() == 'A'){
                    reviews.add(review);
                }
            }
            CookieHelper helper = new CookieHelper();
            helper.setCookie("genre", book.getGenre(), 24 * 60 * 60);
            System.err.println(book.getGenre());
        } catch (SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }
        double sum = 0;
        if (!reviews.isEmpty()) {
            for (BookReview rev : reviews) {
                sum += rev.getReviewScore();
            }
            rate = String.valueOf(sum / reviews.size());
        } else {
            rate = "0";
        }
        return "product.xhtml"/* + "?faces-redirect=true"*/;
    }

    public Book getBook() {
        return book;
    }

    public String getRate() {
        return rate;
    }
    
    public boolean isOnSale()
    {        
        return book.getSalePrice().signum() != 0;
    }
        
    public ArrayList<BookReview> getReviews() {
        return reviews;
    }

    public int getReviewNumber() {
        return reviews.size();
    }

    public boolean renderReviews() {
        return !reviews.isEmpty();
    }

    public ArrayList<Book> getBookSameGenre(int carousselPage) {
        ArrayList<Book> books = new ArrayList<>();
        try {
            if (carousselPage == 1) {
                books = dao.findBooksByLikeGenre(this.book.getGenre(), 0);
            } else if (carousselPage == 2) {
                books = dao.findBooksByLikeGenre(this.book.getGenre(), 4);
                if (books.isEmpty()){
                    books = dao.findBooksByLikeGenre(this.book.getGenre(), 0);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarousselBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return books;
    }
    
    public BigDecimal getPriceProduct() {
       return book.getSalePrice().signum() == 0 ? book.getListPrice() : book.getSalePrice();
    }
    
    public String getOff(){
        return String.valueOf(Math.round((1-(this.book.getSalePrice().doubleValue()/this.book.getListPrice().doubleValue()))*100)) +" % ";   
    }
    
}
