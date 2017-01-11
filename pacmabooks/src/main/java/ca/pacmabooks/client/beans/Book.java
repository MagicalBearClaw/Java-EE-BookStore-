package ca.pacmabooks.client.beans;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This is the data bean representing a book.
 * 
 * @author Michael McMahon(1330815)
 * @author Christoffer Baur(1336394)
 * @version 1.0
 * @since 1.8
 * 1/30/16
 */
public class Book 
{
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private LocalDateTime publishDate;
    private int numOfPages;
    private String genre;
    private String description;
    private ArrayList<String> formats;
    private BigDecimal wholeSalePrice;
    private BigDecimal listPrice;
    private BigDecimal salePrice;
    private int fileSize;
    private LocalDateTime dateAddedToInventory;
    private LocalDateTime dateRemoved;
    
    /**
     *  Create a new empty book;
     */
    public Book()
    {
        super();
        formats =  new ArrayList<>();
    }
    
    /**
     * Creates a new book with information.
     * @param title the title of the book
     * @param author the author of the book
     * @param isbn the isbn of the book
     * @param publisher the publisher of the book
     * @param pubDate the published date of the book
     * @param dateAdded the date the book was added
     * @param dateRemoved the date the book was removed
     * @param numOfPages the number of pages of the book
     * @param genre the genre of the book
     * @param description the description of the book
     * @param status  the status of the book
     * 
     * @author Michael McMahon(1330815)
     * @author Christoffer Baur(1336394)
     */
    public Book(String title, String author, String isbn, String publisher, LocalDateTime pubDate, LocalDateTime dateAdded, LocalDateTime dateRemoved,
            int numOfPages, String genre, String description)//, BookStatus status)
    {
        this();
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publishDate = pubDate;
        this.dateAddedToInventory = dateAdded;
        this.dateRemoved = dateRemoved;
        this.numOfPages = numOfPages;
        this.genre = genre;
        this.description = description;
//        this.status = status;
    }
    /*
     * @author Micahel Mcmahon
    */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.isbn);
        hash = 47 * hash + Objects.hashCode(this.title);
        hash = 47 * hash + Objects.hashCode(this.author);
        hash = 47 * hash + Objects.hashCode(this.publisher);
        hash = 47 * hash + this.numOfPages;
        hash = 47 * hash + Objects.hashCode(this.genre);
        hash = 47 * hash + Objects.hashCode(this.description);
        hash = 47 * hash + Objects.hashCode(this.formats);
        hash = 47 * hash + Objects.hashCode(this.wholeSalePrice);
        hash = 47 * hash + Objects.hashCode(this.listPrice);
        hash = 47 * hash + Objects.hashCode(this.salePrice);
        hash = 47 * hash + Objects.hashCode(this.fileSize);
        return hash;
    }
    /*
     * @author Micahel Mcmahon
    */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (this.numOfPages != other.numOfPages) {
            //System.out.println("pages");
            return false;
        }
        if (!Objects.equals(this.isbn, other.isbn)) {
            //System.out.println("isbn");
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            //System.out.println("title");
            return false;
        }
        if (!Objects.equals(this.author, other.author)) {
            //System.out.println("author");
            return false;
        }
        if (!Objects.equals(this.publisher, other.publisher)) {
            //System.out.println("publisher");
            return false;
        }
        if (!Objects.equals(this.genre, other.genre)) {
            //System.out.println("genre");
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            //System.out.println("description");
            return false;
        }
        if (!Objects.equals(this.formats, other.formats)) {
            //System.out.println("formats");
            return false;
        }
        if (!Objects.equals(this.wholeSalePrice, other.wholeSalePrice)) {
            //System.out.println("whole");
            return false;
        }
        if (!Objects.equals(this.listPrice, other.listPrice)) {
            //System.out.println("list");
            return false;
        }
        if (!Objects.equals(this.salePrice, other.salePrice)) {
            //System.out.println("sale");
            return false;
        }
        if (!Objects.equals(this.fileSize, other.fileSize)) {
            //System.out.println("file");
            return false;
        }
        return true;
    }

    /**
     * @return the isbn
     * @author Michael McMahon(1330815)
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     * @author Michael McMahon(1330815)
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * @return the title
     * @author Michael McMahon(1330815)
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     * @author Michael McMahon(1330815)
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the Author
     * @author Michael McMahon(1330815)
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param Author the Author to set
     * @author Michael McMahon(1330815)
     */
    public void setAuthor(String Author) {
        this.author = Author;
    }
    
    /**
     * @return the Author
     * @author Christoffer Baur(1336394)
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * @param publisher the Publisher to set
     * @author Christoffer Baur(1336394)
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * @return the publishDae
     * @author Michael McMahon(1330815)
     */
    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    /**
     * @param publishDae the publishDae to set
     * @author Michael McMahon(1330815)
     */
    public void setPublishDate(LocalDateTime publishDae) {
        this.publishDate = publishDae;
    }

    /**
     * @return the numOfPages
     * @author Michael McMahon(1330815)
     */
    public int getNumOfPages() {
        return numOfPages;
    }

    /**
     * @param numOfPages the numOfPages to set
     * @author Michael McMahon(1330815)
     */
    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }

    /**
     * @return the genre
     * @author Michael McMahon(1330815)
     */
    public String getGenre() {
        return genre;
    }

    /**
     * @param genre the genre to set
     * @author Michael McMahon(1330815)
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * @return the description
     * @author Michael McMahon(1330815)
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     * @author Michael McMahon(1330815)
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the formats
     * @author Michael McMahon(1330815)
     */
    public ArrayList<String> getFormats() {
        return formats;
    }

    /**
     * @param formats the formats to set
     * @author Michael McMahon(1330815)
     */
    public void setFormats(ArrayList<String> formats) {
        this.formats = formats;
    }

    /**
     * @return the wholeSalePrice
     * @author Michael McMahon(1330815)
     */
    public BigDecimal getWholeSalePrice() {
        return wholeSalePrice;
    }

    /**
     * @param wholeSalePrice the wholeSalePrice to set
     * @author Michael McMahon(1330815)
     */
    public void setWholeSalePrice(BigDecimal wholeSalePrice) {
        this.wholeSalePrice = wholeSalePrice;
    }

    /**
     * @return the listPrice
     * @author Michael McMahon(1330815)
     */
    public BigDecimal getListPrice() {
        return listPrice;
    }

    /**
     * @param listPrice the listPrice to set
     * @author Michael McMahon(1330815)
     */
    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    /**
     * @return the salePrice
     * @author Michael McMahon(1330815)
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * @param salePrice the salePrice to set
     * @author Michael McMahon(1330815)
     */
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }
    
    /**
     * @return the fileSize
     * @author Christoffer Baur(1336394)
     */
    public int getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize the fileSize to set
     * @author Christoffer Baur(1336394)
     */
    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return the dateAddedToInventory
     * @author Michael McMahon(1330815)
     */
    public LocalDateTime getDateAddedToInventory() {
        return dateAddedToInventory;
    }

    /**
     * @param dateAddedToInventory the dateAddedToInventory to set
     * @author Michael McMahon(1330815)
     */
    public void setDateAddedToInventory(LocalDateTime dateAddedToInventory) {
        this.dateAddedToInventory = dateAddedToInventory;
    }
    
    /**
     * @return the dateRemoved
     * @author Christoffer Baur(1336394)
     */
    public LocalDateTime getDateRemoved() {
        return dateRemoved;
    }

    /**
     * @param dateRemoved the dateRemoved to set
     * @author Christoffer Baur(1336394)
     */
    public void setDateRemoved(LocalDateTime dateRemoved) {
        this.dateRemoved = dateRemoved;
    }

    @Override
    public String toString() {
        return "Book{" + "isbn=" + isbn + ", title=" + title + ", author=" + author + ", publisher=" + publisher + ", publishDate=" + publishDate + ", numOfPages=" + numOfPages + ", genre=" + genre + ", description=" + description + ", formats=" + formats + ", wholeSalePrice=" + wholeSalePrice + ", listPrice=" + listPrice + ", salePrice=" + salePrice + ", fileSize=" + fileSize + ", dateAddedToInventory=" + dateAddedToInventory + ", dateRemoved=" + dateRemoved + '}';
    }
    
    
}