
package ca.pacmabooks.client.beans;

import java.math.BigDecimal;

/**
 * This is a bean data for a product in the shopping cart
 * @author michael
 */
public class CartProduct
{
    private Book book;

    private int quantity;
    /*
     * @author Micahel Mcmahon
    */
    public CartProduct(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }
    /*
     * @author Micahel Mcmahon
    */
    public CartProduct() {
    }
    /*
     * @author Micahel Mcmahon
    */
    public Book getBook() {
        return book;
    }
    /*
     * @author Micahel Mcmahon
    */
    public int getQuantity() {
        return quantity;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setBook(Book book) {
        this.book = book;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setQuantity(int quantity)
    {
        if(quantity > 0)
            this.quantity = quantity;
        else
            this.quantity = 1;
    }

    /**
     * returns the correct price, this is determined if there is
     * a sale price or not.
     * @author Michael mcmahon
     * @return 
     */
    public BigDecimal getPriceProduct() 
    {
        return book.getSalePrice().signum() == 0 ? book.getListPrice() : book.getSalePrice();
    }
}