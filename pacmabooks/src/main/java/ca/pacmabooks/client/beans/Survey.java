package ca.pacmabooks.client.beans;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a Survey from the database
 * 
 * @author Christoffer Baur
 */
@Named(value = "survey")
@RequestScoped
public class Survey implements Serializable 
{
    //variables
    
    private int id;
    private boolean enabled;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private int answer1Count;
    private int answer2Count;
    private int answer3Count;

    /**
     * Default constructor
     */
    public Survey() {
    }
    
    /**
     * Constructor with all the fields
     * 
     * @param id
     * @param name
     * @param enabled
     * @param question
     * @param answer1
     * @param answer2
     * @param answer3
     * @param answer1Count
     * @param answer2Count
     * @param answer3Count 
     */
    public Survey(int id, boolean enabled, String question, String answer1, String answer2, String answer3, int answer1Count, int answer2Count, int answer3Count) {
        this.id = id;
        this.enabled = enabled;
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer1Count = answer1Count;
        this.answer2Count = answer2Count;
        this.answer3Count = answer3Count;
    }

    /**
     * Returns the id
     * 
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id
     * 
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the enabled boolean
     * 
     * @return enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled boolean
     * 
     * @param enabled 
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns the question
     * 
     * @return question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the question
     * 
     * @param question 
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Returns the answer1
     * 
     * @return answer1
     */
    public String getAnswer1() {
        return answer1;
    }

    /**
     * Sets the answer1
     * 
     * @param answer1 
     */
    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    /**
     * Returns the answer2
     * 
     * @return answer2
     */
    public String getAnswer2() {
        return answer2;
    }

    /**
     * Sets the answer2
     * 
     * @param answer2 
     */
    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    /**
     * Returns the answer3
     * 
     * @return answer3
     */
    public String getAnswer3() {
        return answer3;
    }

    /**
     * Sets the answer3
     * 
     * @param answer3 
     */
    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    /**
     * Returns the answer1Count
     * 
     * @return answer1Count
     */
    public int getAnswer1Count() {
        return answer1Count;
    }

    /**
     * Sets the answer1Count
     * 
     * @param answer1Count 
     */
    public void setAnswer1Count(int answer1Count) {
        this.answer1Count = answer1Count;
    }

    /**
     * Returns the answer2Count
     * 
     * @return answer2Count
     */
    public int getAnswer2Count() {
        return answer2Count;
    }

    /**
     * Sets the answer2Count
     * 
     * @param answer2Count 
     */
    public void setAnswer2Count(int answer2Count) {
        this.answer2Count = answer2Count;
    }

    /**
     * Returns the answer3Count
     * 
     * @return answer3Count
     */
    public int getAnswer3Count() {
        return answer3Count;
    }

    /**
     * Sets the answer3Count
     * 
     * @param answer3Count 
     */
    public void setAnswer3Count(int answer3Count) {
        this.answer3Count = answer3Count;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        hash = 97 * hash + (this.enabled ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.question);
        hash = 97 * hash + Objects.hashCode(this.answer1);
        hash = 97 * hash + Objects.hashCode(this.answer2);
        hash = 97 * hash + Objects.hashCode(this.answer3);
        hash = 97 * hash + this.answer1Count;
        hash = 97 * hash + this.answer2Count;
        hash = 97 * hash + this.answer3Count;
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
        final Survey other = (Survey) obj;
        if (this.id != other.id) {
            System.out.println(this.id + "   " + other.id);
            return false;
        }
        if (this.enabled != other.enabled) {
            System.out.println(this.enabled + "   " + other.enabled);
            return false;
        }
        if (this.answer1Count != other.answer1Count) {
            System.out.println(this.answer1Count + "   " + other.answer1Count);
            return false;
        }
        if (this.answer2Count != other.answer2Count) {
            System.out.println(this.answer2Count + "   " + other.answer2Count);
            return false;
        }
        if (this.answer3Count != other.answer3Count) {
            System.out.println(this.answer3Count + "   " + other.answer3Count);
            return false;
        }
        if (!Objects.equals(this.question, other.question)) {
            System.out.println(this.question + "   " + other.question);
            return false;
        }
        if (!Objects.equals(this.answer1, other.answer1)) {
            System.out.println(this.answer1 + "   " + other.answer1);
            return false;
        }
        if (!Objects.equals(this.answer2, other.answer2)) {
            System.out.println(this.answer2 + "   " + other.answer2);
            return false;
        }
        if (!Objects.equals(this.answer3, other.answer3)) {
            System.out.println(this.answer3 + "   " + other.answer3);
            return false;
        }
        return true;
    }
}
