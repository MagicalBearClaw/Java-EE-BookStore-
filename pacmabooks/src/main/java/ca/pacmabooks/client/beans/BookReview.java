
package ca.pacmabooks.client.beans;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This is the bean data representation.
 * @author Micahel Mcmahon
 */
@Named(value = "bookReview")
@RequestScoped
public class BookReview implements Serializable 
{
    private int id;
    private String isbn;
    private LocalDateTime reviewDate;
    private int clientId;
    private int reviewScore;
    private String review;
    private char status;
    /**
     * Creates a new instance of BookReview
     */
    public BookReview() 
    {
    }

    public BookReview(int id, String isbn, LocalDateTime reviewDate, String clientFirstName, String clientLastName, int clientId, int reviewScore, String review, char status) {
        this.id = id;
        this.isbn = isbn;
        this.reviewDate = reviewDate;
        this.clientId = clientId;
        this.reviewScore = reviewScore;
        this.review = review;
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.isbn);
        hash = 59 * hash + Objects.hashCode(this.reviewDate);
        hash = 59 * hash + this.clientId;
        hash = 59 * hash + this.reviewScore;
        hash = 59 * hash + Objects.hashCode(this.review);
        hash = 59 * hash + this.status;
//        System.out.println("hash");
        return hash;
    }
    /*
     * @author Micahel Mcmahon
    */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            System.out.println("same");
            return true;
        }
        if (obj == null) {
            System.out.println("null");
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            System.out.println("class" + getClass().getName() + "  " + obj.getClass().getName());
            return false;
        }
        final BookReview other = (BookReview) obj;
        if (this.clientId != other.clientId) {
            System.out.println("client");
            return false;
        }
        if (this.reviewScore != other.reviewScore) {
            System.out.println("score");
            return false;
        }
        if (this.status != other.status) {
            System.out.println("status");
            return false;
        }
        if (!Objects.equals(this.isbn, other.isbn)) {
            System.out.println("isbn");
            return false;
        }
        if (!Objects.equals(this.review, other.review)) {
            System.out.println("review");
            return false;
        }
        if (!Objects.equals(this.reviewDate, other.reviewDate)) {
            System.out.println("date");
            return false;
        }
        System.out.println("all");
        return true;
    }
    /*
     * @author Micahel Mcmahon
    */
    public int getId() {
        return id;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setId(int id) {
        this.id = id;
    }
    /*
     * @author Micahel Mcmahon
    */
    public String getIsbn() {
        return isbn;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    /*
     * @author Micahel Mcmahon
    */
    public LocalDateTime getReviewDate() {
        return reviewDate;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    /*
     * @author Micahel Mcmahon
    */
    public int getClientId() {
        return clientId;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    /*
     * @author Micahel Mcmahon
    */
    public int getReviewScore() {
        return reviewScore;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setReviewScore(int reviewScore) {
        this.reviewScore = reviewScore;
    }
    /*
     * @author Micahel Mcmahon
    */
    public String getReview() {
        return review;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setReview(String review) {
        this.review = review;
    }
    /*
     * @author Micahel Mcmahon
    */
    public char getStatus() {
        return status;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setStatus(char status) {
        this.status = status;
    }
    
}
