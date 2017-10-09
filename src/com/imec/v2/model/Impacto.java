/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imec.v2.model;

/**
 *
 * @author aaraujo
 */
public class Impacto {
    private String problemaIncidente;
    private String solucaoIncidente;
    private String impactoIncidente;    
    private String criticidadeIncidente;

    public String getProblemaIncidente() {
        return problemaIncidente;
    }

    public void setProblemaIncidente(String problemaIncidente) {
        this.problemaIncidente = problemaIncidente;
    }

    public String getSolucaoIncidente() {
        return solucaoIncidente;
    }

    public void setSolucaoIncidente(String solucaoIncidente) {
        this.solucaoIncidente = solucaoIncidente;
    }

    public String getImpactoIncidente() {
        return impactoIncidente;
    }

    public void setImpactoIncidente(String impactoIncidente) {
        this.impactoIncidente = impactoIncidente;
    }

    public String getCriticidadeIncidente() {
        return criticidadeIncidente;
    }

    public void setCriticidadeIncidente(String criticidadeIncidente) {
        this.criticidadeIncidente = criticidadeIncidente;
    }    
}
