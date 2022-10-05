package com.utar.edu.mobileindividualassignment;

import java.io.Serializable;

public class EntryObject implements Serializable {

    //this class is to hold Spend Entries as an object
    String SpendCategory, SpendDescription;
    double SpendAmount;
    int spendID;

    public EntryObject(int ID, String spendCategory, String spendDescription, double spendAmount) {
        spendID = ID;
        SpendCategory = spendCategory;
        SpendDescription = spendDescription;
        SpendAmount = spendAmount;
    }

    @Override
    public String toString() {

        return "Spend Details for ID   : " + spendID + "\n" +
                "Spend Category        : " + SpendCategory + "\n" +
                "Spend Description     : " + SpendDescription + "\n" +
                "Spend Amount          : " + SpendAmount;

    }

    public int getSpendID() {
        return spendID;
    }

    public void setSpendID(int spendID) {
        this.spendID = spendID;
    }

    public String getSpendCategory() {
        return SpendCategory;
    }

    public void setSpendCategory(String spendCategory) {
        SpendCategory = spendCategory;
    }

    public String getSpendDescription() {
        return SpendDescription;
    }

    public void setSpendDescription(String spendDescription) {
        SpendDescription = spendDescription;
    }

    public double getSpendAmount() {
        return SpendAmount;
    }

    public void setSpendAmount(double spendAmount) {
        SpendAmount = spendAmount;
    }


}
