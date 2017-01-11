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
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.ItemSelectEvent;

/**
 *
 * @author Christoffer Baur 1336394
 */
@Named
@SessionScoped
public class SurveySessionBackingBean implements Serializable{

    @Inject
    private DaoImpl dao;

    @Inject
    private AuthenticationBackingBean login;
    
    private int answer;
    private boolean isAvailable;
    private boolean finishedSurvey;
    private Survey currentSurvey;
    private ArrayList<Survey> allSurveys;
    
    /**
     * Returns the answer number/row
     * 
     * @return answer
     */
    public int getAnswer(){
        return answer;
    }
    
    /**
     * Sets the answer number/row
     * @param answer 
     */
    public void setAnswer(int answer){
        this.answer = answer;
    }
    /**
     * Returns a boolean indicating if a survey is currently selected
     * 
     * @author Christoffer Baur 1336394
     * @return boolean survey currently selected
     */
    public boolean isSurveySelected(){
        if(currentSurvey != null)
            return true;
        return false;
    }
    
    /**
     * Returns the current Survey
     * 
     * @author Christoffer Baur 1336394
     * @return Survey the current Survey
     */
    public Survey getCurrentSurvey(){
        return currentSurvey;
    }
    
    /**
     * Returns the current Survey
     * 
     * @author Christoffer Baur 1336394
     * @return Survey the current Survey
     */
    public void setCurrentSurvey(Survey survey){
        System.out.println("Session setCurrentSurvey - set the survey. survey id = " + survey.getId());
        currentSurvey = survey;
        // setup other variables
//        displaySurvey(currentSurvey);
        finishedSurvey = false;
        answer = 0;
    }
    
    /**
     * Returns the isAvailable boolean
     * 
     * @author Christoffer Baur 1336394
     * @return boolean if any surveys are available
     */
    public boolean getIsAvailable(){
        return isAvailable;
    }

    /**
     * Returns all of the surveys
     * 
     * @author Christoffer Baur 1336394
     * @return ArrayList of all surveys
     */
    public ArrayList<Survey> getAllSurveys(){
        return allSurveys;
    }
    
    /**
     * Returns the finishedSurvey boolean
     * 
     * @author Christoffer Baur 1336394
     * @return boolean if the survey was finished
     */
    public boolean getFinishedSurvey(){
        return finishedSurvey;
    }
    
    /**
     * sets the finishedSurvey boolean
     * 
     * @param finished 
     * @author Christoffer Baur 1336394
     */
    public void setFinishedSurvey(boolean finished){
        finishedSurvey = finished;
    }
    
    /**
     * Removes a survey from the ones currently available
     * Then checks to see if there are any surveys left/available
     * 
     * @param survey 
     */
    public void removeSurvey(Survey survey){
        allSurveys.remove(survey);
        
        if(allSurveys.isEmpty())
            isAvailable = false;
    }
    
    /**
     * Constructor to initialize class variables. Gets params that are passed to
     * it in order to change what is rendered on the survey page
     * 
     * @author Christoffer Baur 1336394
     */
    @PostConstruct
    private void init() {
        System.out.println("Session init: Im in it");
        isAvailable = false;
        currentSurvey = null;
        allSurveys = null;
        finishedSurvey = false;
        try {
            allSurveys = dao.findAllSurveysEnabled();
            if(!allSurveys.isEmpty())
                isAvailable = true;
            System.out.println("Session init: available = " + isAvailable);
        }catch (SQLException ex) {
                Logger.getLogger(ReviewBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Submits the answers provided by the user to the db
     * 
     * @author Christoffer Baur 1336394
     */
    public void submit(){
        boolean allValid = true;
        finishedSurvey = true;
        removeSurvey(currentSurvey);
        System.out.println("Session submit: submit method - removed current survey from avaialble surveys in SESSION");
        if(currentSurvey == null)
            System.out.println("Session submit: currentSurvey is  null now");
        else
            System.out.println("Session submit: currentSurvey is  NOT null aftre removing");

        switch(answer){
            case 1:
                currentSurvey.setAnswer1Count(currentSurvey.getAnswer1Count() + 1);
            break;
            case 2:
                currentSurvey.setAnswer2Count(currentSurvey.getAnswer2Count() + 1);
                break;
            case 3:
                currentSurvey.setAnswer3Count(currentSurvey.getAnswer3Count() + 1);
                break;
            default:
                break;
        }
        try {
            dao.updateSurvey(currentSurvey);
        } catch (SQLException ex) {
            Logger.getLogger(SurveySessionBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentSurvey = null;
    }
}