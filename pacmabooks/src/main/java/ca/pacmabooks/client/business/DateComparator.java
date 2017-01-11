/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.pacmabooks.client.business;

import ca.pacmabooks.client.beans.Reports;
import java.util.Comparator;

/**
 * To sort comparators by date ascending
 * @author Andrew
 */
public class DateComparator implements Comparator<Reports> {

    @Override
    public int compare(Reports o1, Reports o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
    
}
