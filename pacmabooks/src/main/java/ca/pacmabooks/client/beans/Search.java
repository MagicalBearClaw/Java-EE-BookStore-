
package ca.pacmabooks.client.beans;

import ca.pacmabooks.client.business.controllers.util.RepeatPaginator;
import ca.pacmabooks.client.persistence.DaoImpl;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Alexandre, Andrew
 * 
 */
@Named
@SessionScoped
public class Search implements Serializable {

    private List<Book> books;
    private RepeatPaginator paginator;
    private String input;

    private String genre;

    private String search;

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Inject
    private DaoImpl dao;

    @Inject
    private Main main;

    @Inject
    private Product product;

    /*
    @PostConstruct
    public void init() {
        setInput(main.getSearchString());
        this.genre = main.getGenre();
        getAllBooks();
    }*/

    public String getGenre() {
        return genre;
    }

    public boolean displayBestSellers() {
        if (this.genre != null) {
            return true;
        }
        return false;
    }

    public ArrayList<Book> getBestSellers() {
        ArrayList<Book> books = null;
        try {
            books = dao.findBooksByLikeGenreBestSellers(this.genre);
        } catch (SQLException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
        main.setGenre(null);
        return books;
    }

    public String getAllBooks() {
        if ((getInput() == null) && (this.genre == null)) {
            try {
                books = dao.findAllBooksNotRemoved();
                paginator = new RepeatPaginator(this.books);
            } catch (SQLException ex) {
                Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
                books = null;
            }
        } else if (this.input != null) {
            try {
                books = dao.findBooksBySearch(getInput());
                paginator = new RepeatPaginator(this.books);
                if (books.size() == 1) {
                    product.show(this.books.get(0).getIsbn());
                    return "product.xhtml?faces-redirect=true";
                }
            } catch (SQLException ex) {
                Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
                books = null;
            } finally {
                this.search = this.input;
                input = null;
                genre = null;
            }
        } else {
            try {
                books = dao.findBooksByLikeGenreNoLimit(genre);
                paginator = new RepeatPaginator(this.books);
            } catch (SQLException ex) {
                Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
                books = null;
            } finally {
                input = null;
                this.search = this.genre;
            }
        }
        return "books.xhtml?faces-redirect=true";
    }

    public RepeatPaginator getPaginator() {
        return paginator;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getInput() {
        return this.input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getSearch() {
        return this.search;
    }

}
