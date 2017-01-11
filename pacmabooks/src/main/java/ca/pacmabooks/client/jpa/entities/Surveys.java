package ca.pacmabooks.client.jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PHILIP
 */
@Entity
@Table(name = "surveys")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Surveys.findAll", query = "SELECT s FROM Surveys s"),
    @NamedQuery(name = "Surveys.findById", query = "SELECT s FROM Surveys s WHERE s.id = :id"),
    @NamedQuery(name = "Surveys.findByEnabled", query = "SELECT s FROM Surveys s WHERE s.enabled = :enabled"),
    @NamedQuery(name = "Surveys.findByQuestion", query = "SELECT s FROM Surveys s WHERE s.question = :question"),
    @NamedQuery(name = "Surveys.findByAnswer1", query = "SELECT s FROM Surveys s WHERE s.answer1 = :answer1"),
    @NamedQuery(name = "Surveys.findByAnswer2", query = "SELECT s FROM Surveys s WHERE s.answer2 = :answer2"),
    @NamedQuery(name = "Surveys.findByAnswer3", query = "SELECT s FROM Surveys s WHERE s.answer3 = :answer3"),
    @NamedQuery(name = "Surveys.findByAnswer1Count", query = "SELECT s FROM Surveys s WHERE s.answer1Count = :answer1Count"),
    @NamedQuery(name = "Surveys.findByAnswer2Count", query = "SELECT s FROM Surveys s WHERE s.answer2Count = :answer2Count"),
    @NamedQuery(name = "Surveys.findByAnswer3Count", query = "SELECT s FROM Surveys s WHERE s.answer3Count = :answer3Count")})
public class Surveys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "enabled")
    private boolean enabled;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "question")
    private String question;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "answer1")
    private String answer1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "answer2")
    private String answer2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "answer3")
    private String answer3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "answer1_count")
    private int answer1Count;
    @Basic(optional = false)
    @NotNull
    @Column(name = "answer2_count")
    private int answer2Count;
    @Basic(optional = false)
    @NotNull
    @Column(name = "answer3_count")
    private int answer3Count;

    public Surveys() {
    }

    public Surveys(Integer id) {
        this.id = id;
    }

    public Surveys(Integer id, boolean enabled, String question, String answer1, String answer2, String answer3, int answer1Count, int answer2Count, int answer3Count) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public int getAnswer1Count() {
        return answer1Count;
    }

    public void setAnswer1Count(int answer1Count) {
        this.answer1Count = answer1Count;
    }

    public int getAnswer2Count() {
        return answer2Count;
    }

    public void setAnswer2Count(int answer2Count) {
        this.answer2Count = answer2Count;
    }

    public int getAnswer3Count() {
        return answer3Count;
    }

    public void setAnswer3Count(int answer3Count) {
        this.answer3Count = answer3Count;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Surveys)) {
            return false;
        }
        Surveys other = (Surveys) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ca.pacmabooks.client.jpa.entities.Surveys[ id=" + id + " ]";
    }
    
}
