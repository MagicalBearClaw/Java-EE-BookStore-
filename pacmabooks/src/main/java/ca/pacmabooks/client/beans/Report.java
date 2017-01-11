
package ca.pacmabooks.client.beans;

import ca.pacmabooks.client.business.DateComparator;
import ca.pacmabooks.client.business.PriceComparator;
import ca.pacmabooks.client.persistence.DaoImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Andrew
 */
@Named("report")
@ViewScoped
public class Report implements Serializable {

    /**
     * Creates a new instance of Report
     */
    @Inject
    private DaoImpl dao;
    private ArrayList<Reports> reports;
    private ArrayList<?> autofill;
    private ArrayList<String> allTypes;
    private boolean showTable = false;
    private boolean clientVisible = false;
    private boolean topSellersVisible = false;
    private boolean totalVisible = false;
    private boolean authorVisible = false;
    private boolean topClientsVisible = false;
    private boolean zeroVisible = false;
    private boolean publisherVisible = false;
    private String searchValue;
    private Date date1;
    private Date date2;
    private Date date3;
    private Date date4;
    private Date date5;
    private Date date6;
    private Date date7;
    private Date date8;
    private Date date9;
    private Date date10;
    private Date date11;

    private String type;
    private Timestamp fromDate;
    private Timestamp toDate;
    
    @PostConstruct
    public void init(){
        
        start();
        showForm();
        showTable();
        //retrieveBookReport();
        //retrieveAuthorReport("Colin F. Barnes");
        /*try{
            Client c = dao.findClientById(2);
            retrieveClientReport(c);
        }
        catch(SQLException se){
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, se);
        }*/
        //retrieveTopSellerReport();
        //retrieveTopClientsReport();
        //retrieveZeroSalesReport();
    }
    
    public void start(){
            FacesContext context = FacesContext.getCurrentInstance();
            String type = context.getExternalContext().getRequestParameterMap().get("type_string");
            String table = context.getExternalContext().getRequestParameterMap().get("submitted_val");
            String value = context.getExternalContext().getRequestParameterMap().get("type_value");
            if(type != null){
                setType(type);
            }
            if(table != null){
                setShowTable(true);
            }
            if(value != null){
                System.out.println("BBBBBBS"+value);
                setSearchValue(value);
            }
    }
    
    public void showTable(){
        
            if(showTable){
                switch(type){
                    case "total":
                        retrieveBookReport();
                        break;
                    case "author":
                        retrieveAuthorReport(searchValue);
                        break;
                    case "client":
                        retrieveClientReport(Integer.parseInt(searchValue));
                        break;
                   case "publisher":
                        break;
                    case "bestSellers":
                        retrieveTopSellerReport();
                        break;
                    case "bestClients":
                        retrieveTopClientsReport();
                        break;
                    case "zero":
                        retrieveZeroSalesReport();
                        break;
                    default:
                        break;
                }
            }
    }
    
    public void showForm() {
        try{
            if(type != null){
                switch(type){
                    case "total":
                        setTotalVisible(true);
                        break;
                    case "author":
                        ArrayList<String> authors = dao.findAllBookAuthors();
                        setAutofill(authors);
                        setAuthorVisible(true);
                        break;
                    case "client":
                        ArrayList<Integer> clients = dao.findAllClientsAsId();
                        Collections.sort(clients);
                        setAutofill(clients);
                        setClientVisible(true);
                        break;
                    case "publisher":
                        ArrayList<String> publishers = dao.findAllBookPublishers();
                        setAutofill(publishers);
                        setPublisherVisible(true);
                        break;
                    case "bestSellers":
                        setTopSellersVisible(true);
                        break;
                    case "bestClients":
                        setTopClientsVisible(true);
                        break;
                    case "zero":
                        setZeroVisible(true);
                        break;
                    default:
                        break;
                }
            }  
        }
        catch(SQLException ex){
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void submit(String value){
        setSearchValue(value);
             if(showTable){
                switch(type){
                    case "total":
                        retrieveBookReport();
                        break;
                    case "author":
                        retrieveAuthorReport(getSearchValue());
                        break;
                    case "client":
                        Random gen = new Random();
                        retrieveClientReport(gen.nextInt()+1);
                        break;
                   case "publisher":
                        break;
                    case "bestSellers":
                        retrieveTopSellerReport();
                        break;
                    case "bestClients":
                        retrieveTopClientsReport();
                        break;
                    case "zero":
                        retrieveZeroSalesReport();
                        break;
                    default:
                        break;
                }
            }
    }
    
    public void submitCount(){
        setTopSellersVisible(false);
        setClientVisible(true);
    }
    
         
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
     
    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
         
        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
    }
    
        public boolean isShowTable() {
        return showTable;
    }

    public void setShowTable(boolean showTable) {
        this.showTable = showTable;
    }

    public boolean isTopClientsVisible() {
        return topClientsVisible;
    }

    public void setTopClientsVisible(boolean topClientsVisible) {
        this.topClientsVisible = topClientsVisible;
    }

    public boolean isZeroVisible() {
        return zeroVisible;
    }

    public void setZeroVisible(boolean zeroVisible) {
        this.zeroVisible = zeroVisible;
    }

    public boolean isPublisherVisible() {
        return publisherVisible;
    }

    public void setPublisherVisible(boolean publisherVisible) {
        this.publisherVisible = publisherVisible;
    }
    
    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
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
 
    public Date getDate3() {
        return date3;
    }
 
    public void setDate3(Date date3) {
        this.date3 = date3;
    }
 
    public Date getDate4() {
        return date4;
    }
 
    public void setDate4(Date date4) {
        this.date4 = date4;
    }
 
    public Date getDate5() {
        return date5;
    }
 
    public void setDate5(Date date5) {
        this.date5 = date5;
    }
 
    public Date getDate6() {
        return date6;
    }
 
    public void setDate6(Date date6) {
        this.date6 = date6;
    }
 
    public Date getDate7() {
        return date7;
    }
 
    public void setDate7(Date date7) {
        this.date7 = date7;
    }
 
    public Date getDate8() {
        return date8;
    }
 
    public void setDate8(Date date8) {
        this.date8 = date8;
    } 
 
    public Date getDate9() {
        return date9;
    }
 
    public void setDate9(Date date9) {
        this.date9 = date9;
    }
 
    public Date getDate10() {
        return date10;
    }
 
    public void setDate10(Date date10) {
        this.date10 = date10;
    }
 
    public Date getDate11() {
        return date11;
    }
 
    public void setDate11(Date date11) {
        this.date11 = date11;
    }
    
    public ArrayList<String> getAllTypes() {
        return allTypes;
    }

    public void setAllTypes(ArrayList<String> allTypes) {
        this.allTypes = allTypes;
    }
    
    public ArrayList<Reports> getReports() {
        return reports;
    }

    public void setReports(ArrayList<Reports> reports) {
        this.reports = reports;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }

    public boolean isTotalVisible() {
        return totalVisible;
    }

    public void setTotalVisible(boolean totalVisible) {
        this.totalVisible = totalVisible;
    }

    public boolean isAuthorVisible() {
        return authorVisible;
    }

    public void setAuthorVisible(boolean authorVisible) {
        this.authorVisible = authorVisible;
    }

    public boolean isTopSellersVisible() {
        return topSellersVisible;
    }

    public void setTopSellersVisible(boolean topSellersVisible) {
        this.topSellersVisible = topSellersVisible;
    }
    
    public boolean isClientVisible() {
        return clientVisible;
    }

    public void setClientVisible(boolean clientVisible) {
        this.clientVisible = clientVisible;
    }

    public ArrayList<?> getAutofill() {
        return autofill;
    }

    public void setAutofill(ArrayList<?> autofill) {
        this.autofill = autofill;
    }
    
    public void retrieveReport(ActionEvent event){
        this.setClientVisible(true);
                retrieveBookReport();
    }
    
    public void retrieveBookReport(){
            try{
                ArrayList<Reports> reps = new ArrayList<Reports>();
                Reports report;
                //get all books
                ArrayList<Book> books = dao.findAllBooks();
                ArrayList<InvoiceDetails> details;
                Invoice invoice = new Invoice();
                Book book;
                //for each book, find details associated with it
                if(!books.isEmpty()){
                    for(int i = 0; i < books.size() ; i++){
                        details = dao.findDetailsByIsbn(books.get(i).getIsbn());
                        if(!details.isEmpty()){
                            for(int j = 0; j < details.size(); j++){
                                invoice = dao.findInvoiceById(details.get(j).getInvoiceId());
                                book = dao.findBookByIsbn(details.get(j).getIsbn());
                                report = new Reports(book.getTitle(),details.get(j).getBookPrice(),"N/A",invoice.getSaleDate());
                                reps.add(report);
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
    
        public void retrieveAuthorReport(String author){
            try{
                ArrayList<Reports> reps = new ArrayList<Reports>();
                ArrayList<Book> books = dao.findBookByAuthor(author);
                ArrayList<InvoiceDetails> details;
                LocalDateTime date;
                Invoice invoice;
                Book book;
                Reports report;
                if(!books.isEmpty()){
                    for(int i = 0; i < books.size() ; i++){
                        details = dao.findDetailsByIsbn(books.get(i).getIsbn());
                        if(!details.isEmpty()){
                            for(int j = 0; j < details.size(); j++){
                                invoice = dao.findInvoiceById(details.get(j).getInvoiceId());
                                book = dao.findBookByIsbn(details.get(j).getIsbn());
                                report = new Reports(book.getTitle(),details.get(j).getBookPrice(),"N/A",invoice.getSaleDate());
                                reps.add(report);
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
        
        public void retrieveClientReport(int id){
            try{
                ArrayList<Reports> reps = new ArrayList<Reports>();
                Reports report;
                ArrayList<Invoice> invoices = dao.findInvoiceByClientId(id);
                InvoiceDetails details;
                Book book;
                for(int i = 0; i < invoices.size() ; i++){
                    details = dao.findDetailsByInvoiceId(invoices.get(i).getId()).get(0);
                    book = dao.findBookByIsbn(details.getIsbn());
                    report = new Reports(book.getTitle(), details.getBookPrice(),"N/A",invoices.get(i).getSaleDate());
                    reps.add(report);
                }
                Collections.sort(reps, new DateComparator());
                setReports(reps);
            }
            catch(SQLException ex){
                Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
        
        public void retrieveTopSellerReport(){
            try{
                ArrayList<Reports> reps = new ArrayList<Reports>();
                Reports report;
                //get all books
                ArrayList<Book> books = dao.findAllBooks();
                ArrayList<InvoiceDetails> details;
                Invoice invoice = new Invoice();
                Book book;
                //for each book, find details associated with it
                if(!books.isEmpty()){
                    for(int i = 0; i < books.size() ; i++){
                        details = dao.findDetailsByIsbn(books.get(i).getIsbn());
                        if(!details.isEmpty()){
                            for(int j = 0; j < details.size(); j++){
                                invoice = dao.findInvoiceById(details.get(j).getInvoiceId());
                                book = dao.findBookByIsbn(details.get(j).getIsbn());
                                report = new Reports(book.getTitle(),details.get(j).getBookPrice(),"N/A",invoice.getSaleDate());
                                reps.add(report);
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
        
        public void retrieveTopClientsReport(){
            try{
                ArrayList<Reports> reps = new ArrayList<Reports>();
                ArrayList<Client> clients = dao.findAllClients();
                ArrayList<Invoice> invoices;
                ArrayList<InvoiceDetails> details;
                Client client;
                Reports report;
                String name;
                int count;
                BigDecimal total;
                if(!clients.isEmpty()){
                    for(int i = 0; i < clients.size(); i++){
                        client = clients.get(i);
                        invoices = dao.findInvoiceByClientId(client.getId());
                        count = 0;
                        total = new BigDecimal("0");
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
                    Collections.sort(reps, new PriceComparator());
                    setReports(reps);
                }
            }
            catch(SQLException ex){
                 Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void retrieveZeroSalesReport(){
                try{                 
                    ArrayList<Reports> reps = new ArrayList<Reports>();
                    ArrayList<Client> clients;
                    ArrayList<InvoiceDetails> details;
                    ArrayList<Book> books = dao.findAllBooks();
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
            
}
