
package ca.pacmabooks.client.beans;

import javax.inject.Named;
import java.io.Serializable;
import java.util.Objects;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Micahel Mcmahon alexandre
 */
@Named
@SessionScoped
public class Client implements Serializable {

    private int id;
    private String title;
    private String firstName;
    private String lastName;
    private String companyName;
    private String address1;
    private String address2;
    private String city;
    private String province;
    private String country;
    private String postalCode;
    private String cellTelephoneNumber;
    private String homeTelephoneNumber;
    private String email;
    private String password;
    private boolean isManager;

    /**
     * Creates a new instance of Client
     */
    public Client() {
    }

    public Client(int id, String title, String firstName, String lastName, String companyName, String address1, String address2, String city, String province, String country, String postalCode, String cellTelephoneNumber, String homeTelephoneNumber, String email, String password, boolean isManager) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalCode = postalCode;
        this.cellTelephoneNumber = cellTelephoneNumber;
        this.homeTelephoneNumber = homeTelephoneNumber;
        this.email = email;
        this.password = password;
        this.isManager = isManager;
        System.out.println("Je créer le client avec le constructeur perso");
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.title);
        hash = 67 * hash + Objects.hashCode(this.firstName);
        hash = 67 * hash + Objects.hashCode(this.lastName);
        hash = 67 * hash + Objects.hashCode(this.companyName);
        hash = 67 * hash + Objects.hashCode(this.address1);
        hash = 67 * hash + Objects.hashCode(this.address2);
        hash = 67 * hash + Objects.hashCode(this.city);
        hash = 67 * hash + Objects.hashCode(this.province);
        hash = 67 * hash + Objects.hashCode(this.country);
        hash = 67 * hash + Objects.hashCode(this.postalCode);
        hash = 67 * hash + Objects.hashCode(this.cellTelephoneNumber);
        hash = 67 * hash + Objects.hashCode(this.homeTelephoneNumber);
        hash = 67 * hash + Objects.hashCode(this.email);
        hash = 67 * hash + Objects.hashCode(this.password);
        hash = 67 * hash + (this.isManager ? 1 : 0);
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
        final Client other = (Client) obj;
        if (this.isManager != other.isManager) {
            System.out.println("Manager  " + this.isManager + "  " + other.isManager);
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            System.out.println("title  " + this.title + "  " + other.title);
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            System.out.println("firstName  " + this.firstName + "  " + other.firstName);
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            System.out.println("lastName  " + this.lastName + "  " + other.lastName);
            return false;
        }
        if (!Objects.equals(this.companyName, other.companyName)) {
            System.out.println("companyName  " + this.companyName + "  " + other.companyName);
            return false;
        }
        if (!Objects.equals(this.address1, other.address1)) {
            System.out.println("address1  " + this.address1 + "  " + other.address1);
            return false;
        }
        if (!Objects.equals(this.address2, other.address2)) {
            System.out.println("address2  " + this.address2 + "  " + other.address2);
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            System.out.println("city  " + this.city + "  " + other.city);
            return false;
        }
        if (!Objects.equals(this.province, other.province)) {
            System.out.println("province  " + this.province + "  " + other.province);
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            System.out.println("country  " + this.country + "  " + other.country);
            return false;
        }
        if (!Objects.equals(this.postalCode, other.postalCode)) {
            System.out.println("postalCode  " + this.postalCode + "  " + other.postalCode);
            return false;
        }
        if (!Objects.equals(this.cellTelephoneNumber, other.cellTelephoneNumber)) {
            System.out.println("cellTelephoneNumber  " + this.cellTelephoneNumber + "  " + other.cellTelephoneNumber);
            return false;
        }
        if (!Objects.equals(this.homeTelephoneNumber, other.homeTelephoneNumber)) {
            System.out.println("homeTelephoneNumber  " + this.homeTelephoneNumber + "  " + other.homeTelephoneNumber);
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            System.out.println("email  " + this.email + "  " + other.email);
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            System.out.println("password  " + this.password + "  " + other.password);
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        if(address2.equals(""))
            this.address2 = address2;
        else
            this.address2 = "";
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCellTelephoneNumber() {
        return cellTelephoneNumber;
    }

    public void setCellTelephoneNumber(String cellTelephoneNumber) {
        if(cellTelephoneNumber.equals(""))
            this.cellTelephoneNumber = cellTelephoneNumber;
        else
            this.cellTelephoneNumber = "";
    }

    public String getHomeTelephoneNumber() {
        return homeTelephoneNumber;
    }

    public void setHomeTelephoneNumber(String homeTelephoneNumber) {
        if(homeTelephoneNumber.equals(""))
            this.homeTelephoneNumber = homeTelephoneNumber;
        else
            this.homeTelephoneNumber = "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    /**
     * For test purpose
     *
     * @return
     */
    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", title=" + title + ", firstName=" + firstName + ", lastName=" + lastName + ", companyName=" + companyName + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", province=" + province + ", country=" + country + ", postalCode=" + postalCode + ", cellTelephoneNumber=" + cellTelephoneNumber + ", homeTelephoneNumber=" + homeTelephoneNumber + ", email=" + email + ", password=" + password + ", isManager=" + isManager + '}';
    }

}
