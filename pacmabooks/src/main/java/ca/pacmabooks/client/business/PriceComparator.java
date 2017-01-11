/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.pacmabooks.client.business;

import ca.pacmabooks.client.beans.Reports;
import java.util.Comparator;

/**
 *
 * @author Andrew
 * 
 * to sort reports by price descending
 */
public class PriceComparator implements Comparator<Reports> {

    @Override
    public int compare(Reports o1, Reports o2) {
        return -o1.getTotal().compareTo(o2.getTotal());
    }  
}
