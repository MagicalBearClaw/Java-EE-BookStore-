/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.pacmabooks.client.backingbeans;

import ca.pacmabooks.client.beans.Book;
import ca.pacmabooks.client.beans.Client;
import ca.pacmabooks.client.beans.Invoice;
import ca.pacmabooks.client.beans.InvoiceDetails;
import ca.pacmabooks.client.beans.Reports;
import ca.pacmabooks.client.beans.Search;
import ca.pacmabooks.client.business.DateComparator;
import ca.pacmabooks.client.business.PriceComparator;
import ca.pacmabooks.client.persistence.DaoImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Andrew
 * 
 * Backing bean that handles the sales reports page
 * 
 */
@Named(value = "sales")
@SessionScoped
public class SalesBackingBean implements Serializable {
    
    @Inject
    private DaoImpl dao;
    private List<?> itemList;
    private boolean visible;
    private List<Reports> reports;
    private List<?> typesList;     
    private String reportType;
    private String reportValue;
    private Date date1;
    private Date date2;
    
    @PostConstruct
    public void init() {
        //showForm();
        //makeReports();
    }
    
    /**
     * method to handle an an event
     * 
     * @param event the event specified on the component
     */
    public void update(ActionEvent event){		
            makeReports();
    }
    
    /**
     * Action upon submitting form
     * 
     * @return 
     */
    public String submit(){
           System.out.println("Andrew");
        return "SalesReports.xhtml?faces-redirect=true";
    }
    
    /**
     * handler for a click event
     * Sets the item list on the page
     * 
     */
    public void handleClickEvent() {
        try{
            //Fills list depending on the type of list that needs to be retrieved
                switch(reportType){
                    case "author":
                        ArrayList<String> authors = dao.findAllBookAuthors();
                        Collections.sort(authors);
                        setItemList(authors);
                        setVisible(true);
                        break;
                    case "client":
                        ArrayList<Integer> clients = dao.findAllClientsAsId();
                        Collections.sort(clients);
                        setItemList(clients);
                        setVisible(true);
                        break;
                    case "publisher":
                        ArrayList<String> publishers = dao.findAllBookPublishers();
                        Collections.sort(publishers);
                        setItemList(publishers);
                        setVisible(true);
                        break;
                    default:
                        setVisible(false);
                        break;
                }
        }
        catch(SQLException ex){
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<?> getItemList() {
        return itemList;
    }

    public void setItemList(List<?> itemList) {
        this.itemList = itemList;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public List<Reports> getReports() {
        return reports;
    }

    public void setReports(List<Reports> reports) {
        this.reports = reports;
    }

    public List<?> getTypesList() {
        return typesList;
    }

    public void setTypesList(List<?> typesList) {
        this.typesList = typesList;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        System.out.println("Set Report:" + getReportType());
        this.reportType = reportType;
    }

    public String getReportValue() {
        return reportValue;
    }

    public void setReportValue(String reportValue) {
        this.reportValue = reportValue;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }
    
   /**
    * Depending on the type of report
    * will retrieve reports
    */        
   private void makeReports(){
           try{
            if(getReportType() != null){
                switch(reportType){
                    case "author":
                        retrieveAuthorReport(getReportValue());
                        break;
                    case "publisher":
                        retrievePublisherReport(getReportValue());
                        break;
                    case "client":
                        retrieveClientReport(Integer.parseInt(getReportValue()));
                        break;
                    case "sellers":
                        retrieveTopSellerReport();
                        break;
                    case "clients":
                        retrieveTopClientsReport();
                        break;
                    case "book":
                        retrieveBookReport();
                        break;
                    default:
                        ArrayList<String> default_list = dao.findAllBookAuthors();
                        setTypesList(default_list);
                        break;
                }
            }  
        }
        catch(SQLException ex){
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        /**
         * retrieve reports of total sales
         */
        public void retrieveBookReport(){
            try{
                List<Reports> reps = new ArrayList<Reports>();
                Reports report;
                //get all books
                List<Book> books = dao.findAllBooks();
                List<InvoiceDetails> details;
                List<Invoice> invoice = new ArrayList<Invoice>();
                Book book;
                if(!books.isEmpty()){
                    //for each book, find details associated with it
                    for(int i = 0; i < books.size() ; i++){
                        details = dao.findDetailsByIsbn(books.get(i).getIsbn());
                        if(!details.isEmpty()){
                            //for each details find the invoice
                            for(int j = 0; j < details.size(); j++){
                                invoice = dao.findInvoiceByIdWithDate(details.get(j).getInvoiceId(), utilToSql(date1), utilToSql(date2));
                                //this means that the purchase was not in range if it is empty
                                if(!invoice.isEmpty()){
                                    book = dao.findBookByIsbn(details.get(j).getIsbn());
                                    //create a report
                                    report = new Reports(book.getTitle(),details.get(j).getBookPrice(),"N/A",invoice.get(0).getSaleDate());
                                    //add it to the report list
                                    reps.add(report);
                                }
                            }
                        }
                    }
                    //sort list using comparator
                    Collections.sort(reps,new DateComparator());
                    //set reports list
                    setReports(reps);
                }
            }
            catch(SQLException ex){
                Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
        /**
         * retrieves the reports for the author
         * 
         * @param author 
         */
        public void retrieveAuthorReport(String author){
            try{
                List<Reports> reps = new ArrayList<Reports>();
                //get list of authors
                List<Book> books = dao.findBookByAuthor(author);
                List<InvoiceDetails> details;
                LocalDateTime date;
                List<Invoice> invoice = new ArrayList<Invoice>();
                Book book;
                Reports report;
                if(!books.isEmpty()){
                    //for each book find details by isbn
                    for(int i = 0; i < books.size() ; i++){
                        details = dao.findDetailsByIsbn(books.get(i).getIsbn());
                        //if details found
                        if(!details.isEmpty()){
                            //for each detail find its invoice within date range
                            for(int j = 0; j < details.size(); j++){
                                invoice = dao.findInvoiceByIdWithDate(details.get(j).getInvoiceId(), utilToSql(date1), utilToSql(date2));
                                //if invoice within date range
                                if(!invoice.isEmpty()){
                                    book = dao.findBookByIsbn(details.get(j).getIsbn());
                                    report = new Reports(book.getTitle(),details.get(j).getBookPrice(),"N/A",invoice.get(0).getSaleDate());
                                    reps.add(report);
                                }
                            }
                        }
                    }
                    Collections.sort(reps,new DateComparator());
                    setReports(reps);
                }
            }
            catch(SQLException ex){
                Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /**
         * retrieves the report for the publisher
         * 
         * @param publisher 
         */
        public void retrievePublisherReport(String publisher){
            try{
                List<Reports> reps = new ArrayList<Reports>();
                //get all boks by publisher
                List<Book> books = dao.findBookByPublisher(publisher);
                List<InvoiceDetails> details;
                LocalDateTime date;
                List<Invoice> invoice = new ArrayList<Invoice>();
                Book book;
                Reports report;
                if(!books.isEmpty()){
                    //for each book find its detail
                    for(int i = 0; i < books.size() ; i++){
                        details = dao.findDetailsByIsbn(books.get(i).getIsbn());
                        if(!details.isEmpty()){
                            //find invoice for each detail
                            for(int j = 0; j < details.size(); j++){
                                invoice = dao.findInvoiceByIdWithDate(details.get(j).getInvoiceId(), utilToSql(date1), utilToSql(date2));
                                if(!invoice.isEmpty()){
                                    book = dao.findBookByIsbn(details.get(j).getIsbn());
                                    report = new Reports(book.getTitle(),details.get(j).getBookPrice(),"N/A",invoice.get(0).getSaleDate());
                                    reps.add(report);
                                }
                            }
                        }
                    }
                    Collections.sort(reps,new DateComparator());
                    setReports(reps);
                }
            }
            catch(SQLException ex){
                Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /**
         * retrieves the report based on selected client
         * 
         * @param id the id of the client
         */
        public void retrieveClientReport(int id){
            try{
                List<Reports> reps = new ArrayList<Reports>();
                Reports report;
                //get invoice by client id and date
                List<Invoice> invoices = dao.findInvoiceByClientIdWithDate(id, utilToSql(date1), utilToSql(date2));
                InvoiceDetails details;
                Book book;
                for(int i = 0; i < invoices.size() ; i++){
                    details = dao.findDetailsByInvoiceId(invoices.get(i).getId()).get(0);
                    book = dao.findBookByIsbn(details.getIsbn());
                    report = new Reports(book.getTitle(), details.getBookPrice(),"N/A",invoices.get(i).getSaleDate());
                    reps.add(report);
                }
                //sort by date
                Collections.sort(reps, new DateComparator());
                setReports(reps);
            }
            catch(SQLException ex){
                Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
        
        /**
         * retrieves the most sold books
         * 
         */
        public void retrieveTopSellerReport(){
            try{
                List<Reports> reps = new ArrayList<Reports>();
                Reports report;
                //get all books
                List<Book> books = dao.findAllBooks();
                List<InvoiceDetails> details;
                List<Invoice> invoice = new ArrayList<Invoice>();
                Book book;
                //for each book, find details associated with it
                if(!books.isEmpty()){
                    for(int i = 0; i < books.size() ; i++){
                        //find its details
                        details = dao.findDetailsByIsbn(books.get(i).getIsbn());
                        if(!details.isEmpty()){
                            for(int j = 0; j < details.size(); j++){
                                //find details within date range by id
                                invoice = dao.findInvoiceByIdWithDate(details.get(j).getInvoiceId(), utilToSql(date1), utilToSql(date2));
                                if(!invoice.isEmpty()){
                                    book = dao.findBookByIsbn(details.get(j).getIsbn());
                                    report = new Reports(book.getTitle(),details.get(j).getBookPrice(),"N/A",invoice.get(0).getSaleDate());
                                    reps.add(report);
                                }
                            }
                        }
                    }
                    Collections.sort(reps,new PriceComparator());
                    setReports(reps);
                }
            }
            catch(SQLException ex){
                Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /**
         * retrieves the most valued client
         * 
         */
        public void retrieveTopClientsReport(){
            try{
                List<Reports> reps = new ArrayList<Reports>();
                //Get all clients
                List<Client> clients = dao.findAllClients();
                List<Invoice> invoices;
                List<InvoiceDetails> details;
                Client client;
                Reports report;
                String name;
                int count;
                BigDecimal total;
                if(!clients.isEmpty()){
                    //for each client find invoice
                    for(int i = 0; i < clients.size(); i++){
                        client = clients.get(i);
                        //find invoice within date range
                        invoices = dao.findInvoiceByClientIdWithDate(clients.get(i).getId(), utilToSql(date1), utilToSql(date2));
                        if(!invoices.isEmpty()){
                            count = 0;
                            total = new BigDecimal("0");
                            //for each invoice find details
                            if(!invoices.isEmpty()){
                                for(int j = 0; j < invoices.size(); j++){
                                    details = dao.findDetailsByInvoiceId(invoices.get(j).getId());
                                    for(int k = 0; k < details.size(); k++){
                                        count++;
                                        total = total.add(details.get(k).getBookPrice());
                                    }
                                }
                                name = client.getTitle() + ". " + client.getFirstName() + " " + client.getLastName();
                                report = new Reports(name,total,Integer.toString(count),null);
                                reps.add(report);
                            }
                        }
                    }
                    Collections.sort(reps, new PriceComparator());
                    setReports(reps);
                }
            }
            catch(SQLException ex){
                 Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /**
         * retrieves zero sales
         * 
         */
        public void retrieveZeroSalesReport(){
                try{                 
                    List<Reports> reps = new ArrayList<Reports>();
                    List<Client> clients;
                    List<InvoiceDetails> details;
                    List<Book> books = dao.findAllBooks();
                    Client client;
                    Reports report;
                    String name;
                    int count;
                    if(!books.isEmpty()){
                        for(int i = 0; i < books.size(); i++){
                            details = dao.findDetailsByIsbn(books.get(i).getIsbn());
                            if(details.isEmpty()){
                                report = new Reports(books.get(i).getTitle(),new BigDecimal("0"),"N/A",null);
                                reps.add(report);
                            }
                        }
                        setReports(reps);
                    }
                }
                catch(SQLException ex){
                    Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        
        private java.sql.Date utilToSql(java.util.Date utilDate){
            return  new java.sql.Date(utilDate.getTime());
        }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.reports);
        hash = 97 * hash + Objects.hashCode(this.typesList);
        hash = 97 * hash + Objects.hashCode(this.reportType);
        hash = 97 * hash + Objects.hashCode(this.reportValue);
        hash = 97 * hash + Objects.hashCode(this.date1);
        hash = 97 * hash + Objects.hashCode(this.date2);
        return hash;
    }

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
        final SalesBackingBean other = (SalesBackingBean) obj;
        if (!Objects.equals(this.reportType, other.reportType)) {
            return false;
        }
        if (!Objects.equals(this.reportValue, other.reportValue)) {
            return false;
        }
        if (!Objects.equals(this.reports, other.reports)) {
            return false;
        }
        if (!Objects.equals(this.typesList, other.typesList)) {
            return false;
        }
        if (!Objects.equals(this.date1, other.date1)) {
            return false;
        }
        if (!Objects.equals(this.date2, other.date2)) {
            return false;
        }
        return true;
    }

        
        
}
