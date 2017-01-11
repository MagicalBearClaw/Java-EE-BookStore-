package ca.pacmabooks.client.backingbeans;

import ca.pacmabooks.client.beans.Book;
import ca.pacmabooks.client.beans.Main;
import ca.pacmabooks.client.business.controllers.util.CookieHelper;
import ca.pacmabooks.client.persistence.DaoImpl;
import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Alexandre
 */
@Named
@RequestScoped
public class CarousselBackingBean implements Serializable{
    
    @Inject
    private DaoImpl dao;
    
    @Inject
    private Main main;

    public ArrayList<Book> getLastestBooks(int carousselPage) {
        ArrayList<Book> books = new ArrayList<>();
        try {
            if (carousselPage == 1) {
                books = dao.findBookOrderByDate(0);
            } else if (carousselPage == 2) {
                books = dao.findBookOrderByDate(4);
            } else if (carousselPage == 3) {
                books = dao.findBookOrderByDate(8);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarousselBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return books;
    }

    public boolean displayRecomanded() {
        CookieHelper helper = new CookieHelper();
        Cookie cookie = null;
        cookie = helper.getCookie("genre");
        if (cookie != null) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Book> getRecomandedBook(int carousselPage) {
        ArrayList<Book> books = new ArrayList<>();
        CookieHelper helper = new CookieHelper();
        Cookie cookie = null;
        cookie = helper.getCookie("genre");
        String genre = "";
        if (cookie != null) {
            genre = cookie.getValue();
        }
        try {
            if (carousselPage == 1) {
                books = dao.findBooksByLikeGenre(genre,0);
            } else if (carousselPage == 2) {
                books = dao.findBooksByLikeGenre(genre,4);
                if (books.isEmpty()){
                    books = dao.findBooksByLikeGenre(genre,0);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarousselBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return books;
    }
    
    public ArrayList<Book> getSalesBook(int carousselPage) {
        ArrayList<Book> books = new ArrayList<>();
        try {
            if (carousselPage == 1) {
                books = dao.findBookBySaleLimit(0);
            } else if (carousselPage == 2) {
                books = dao.findBookBySaleLimit(4);
                if (books.isEmpty()){
                    books = dao.findBookBySaleLimit(0);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarousselBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return books;
    }
    
    
    public ArrayList<Book> getRandomGenreBook(int carousselPage) {
        ArrayList<Book> books = new ArrayList<>();
        String genre = main.getRandomGenre();
        try {
            if (carousselPage == 1) {
                books = dao.findBooksByLikeGenre(genre,0);
            } else if (carousselPage == 2) {
                books = dao.findBooksByLikeGenre(genre,4);
                if (books.isEmpty()){
                    books = dao.findBooksByLikeGenre(genre,0);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarousselBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return books;
    }
}
