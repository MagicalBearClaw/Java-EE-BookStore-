package ca.pacmabooks.client.backingbeans;

import ca.pacmabooks.client.beans.Survey;
import ca.pacmabooks.client.persistence.DaoImpl;
import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.PieChartModel;

/**
 * Survey backing bean for the survey.
 * Request scoped for displaying pie chart and results
 * 
 * @author Christoffer Baur 1336394
 */
@Named
@RequestScoped
public class SurveyBackingBean {

    @Inject
    private DaoImpl dao;

    @Inject
    private AuthenticationBackingBean login;
    
    @Inject
    private SurveySessionBackingBean surveySession;
    
    private PieChartModel pieModel1;
    private Survey currentSurvey;
//    private int totalResults;

    /**
     * Constructor to initialize class variables. Gets params that are passed to
     * it in order to change what is rendered on the survey page
     * 
     * @author Christoffer Baur 1336394
     */
    @PostConstruct
    private void blah() {
        System.out.println("init: method");
    }
    
    /**
     * Submits the answers provided by the user to the db
     * 
     * @author Christoffer Baur 1336394
     */
    public void submit(){
        System.out.println("init: submit method");
        currentSurvey = surveySession.getCurrentSurvey();
        surveySession.submit();
        if(currentSurvey == null)
            System.out.println("init: current survey is null");
        else{
            System.out.println("init: current survey is NOT null");
            createPieModel();
        }
    }
    
    /**
     * Gets the current survey
     * 
     * @return Survey
     */
    public Survey getCurrentSurvey(){
        return currentSurvey;
    }
    
    /**
     * Gets the percentage for the results
     * 
     * @param answer
     * @return 
     */
    public double percentage(int answer){
        double totalResults = currentSurvey.getAnswer1Count() + currentSurvey.getAnswer2Count() + currentSurvey.getAnswer3Count();
        double result = 0.00;
        switch(answer){
            case 1:
                result = currentSurvey.getAnswer1Count() / totalResults * 100;
            break;
            case 2:
                result = currentSurvey.getAnswer2Count() / totalResults * 100;
                break;
            case 3:
                result = currentSurvey.getAnswer3Count() / totalResults * 100;
                break;
            default:
                break;
        }
        return Math.round(result);
    }
    
    /**
     * Gets the pie chart
     * @return PieChartModel
     */
    public PieChartModel getPieModel1() {
        return pieModel1;
    }
    
    /**
     * Returns true if there is a current survey 
     * and there should be a chart to display the results
     * 
     * @return boolean
     */
    public boolean showChart(){
        if(currentSurvey != null){
            return true;
        }
        else
            return false;
    }
    
    /**
     * Creates the pie model to show the results of th survey
     * 
     */
    private void createPieModel() {
        pieModel1 = new PieChartModel();
         
        pieModel1.set(currentSurvey.getAnswer1() + ": " + percentage(1) + "%", currentSurvey.getAnswer1Count());
        pieModel1.set(currentSurvey.getAnswer2() + ": " + percentage(2) + "%", currentSurvey.getAnswer2Count());
        pieModel1.set(currentSurvey.getAnswer3() + ": " + percentage(3) + "%", currentSurvey.getAnswer3Count());
         
        pieModel1.setTitle("Results");
        pieModel1.setLegendPosition("w");
    }
//    
//    public void itemSelect(ItemSelectEvent event) {
//        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
//                        "Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());
//         
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }
}