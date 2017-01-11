/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.pacmabooks.client.backingbeans;

import ca.pacmabooks.client.beans.BookReview;
import ca.pacmabooks.client.beans.Product;
import ca.pacmabooks.client.persistence.DaoImpl;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;

/**
 *
 * @author Alexandre
 */
@Named
@RequestScoped
public class ReviewBackingBean {
    
    private boolean displaySucess;
    
    private boolean displayError;

    @Inject
    private DaoImpl dao;

    @Inject
    private AuthenticationBackingBean login;

    @Inject
    private Product product;

    private BookReview review;

    public BookReview getReview() {
        return review;
    }

    public void setReview(BookReview review) {
        this.review = review;
    }

    @PostConstruct
    private void init() {
        this.displayError = false;
        this.displaySucess = false;
        this.review = new BookReview();
    }

    public void add() {
        if (!(this.review.getReview().equals(""))) {
            if (0 <= this.review.getReviewScore() && this.review.getReviewScore() <= 5) {
                review.setIsbn(product.getBook().getIsbn());
                review.setClientId(login.getClient().getId());
                review.setReviewDate(LocalDateTime.now());
                review.setStatus('P');
                try {
                    dao.createBookReview(this.review);
                    this.displaySucess = true;
                } catch (SQLException ex) {
                    Logger.getLogger(ReviewBackingBean.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
                //product.show(product.getBook().getIsbn());
                this.review.setReviewScore(0);
                this.review.setReview("");
                
            }else{
                this.displayError = true;
            }

        }else{
            this.displayError = true;
        }
    }

    public boolean isDisplaySucess() {
        return displaySucess;
    }

    public boolean isDisplayError() {
        return displayError;
    }
    
    

    public void delete(BookReview review) {
        try {
            dao.deleteReview(review);
            product.show(product.getBook().getIsbn());
        } catch (SQLException ex) {
            Logger.getLogger(ReviewBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean displayDelete(BookReview review) {
        if (login.getClient() != null) {
            if (review.getClientId() == login.getClient().getId()) {
                return true;
            }
        }
        return false;
    }

}
