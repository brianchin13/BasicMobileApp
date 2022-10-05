package com.utar.edu.mobileindividualassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class Homepage extends AppCompatActivity {

    ImageButton imgButtonAddSpending;
    TextView tvDisplayTotalSpend, tvDisplayTotalCashBack, cashBackPetrol, cashBackGroceries, cashBackEWalet, cashBackOther;
    ListView lvDisplaySpendEntries;
    ArrayAdapter arrayAdapter;
    DataBaseHelper dataBaseHelper;
    double totalSpend, totalCashBack, tempCashBack, totalPetrolCB, totalGroceriesCB, totalEwalletCB, totalOtherCB;
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //create button event listeners
        imgButtonAddSpending = findViewById(R.id.imgButtonAddEntry);
        lvDisplaySpendEntries = findViewById(R.id.lvSpendEntries);
        tvDisplayTotalSpend = findViewById(R.id.displayTotalAmountSpent);
        tvDisplayTotalCashBack = findViewById(R.id.displayTotalCashBack);
        cashBackPetrol = findViewById(R.id.displayCashBackPetrolSpend);
        cashBackGroceries = findViewById(R.id.displayCashBackGroceries);
        cashBackEWalet = findViewById(R.id.displayCashBackEWallet);
        cashBackOther = findViewById(R.id.displayCashBackOther);

        imgButtonAddSpending.setOnClickListener(e -> {
            startActivity(new Intent(Homepage.this, AddEntry.class));
        });

        //this is to display all the data in the database when loaded
        dataBaseHelper = new DataBaseHelper(Homepage.this);
        List<EntryObject> entryData = dataBaseHelper.getAll();
        displayToList(dataBaseHelper);

        //to display Total Amount spend and Total cash back earned
        for (int i = 0; i < lvDisplaySpendEntries.getCount(); i++) {
            EntryObject entryObject = (EntryObject) lvDisplaySpendEntries.getItemAtPosition(i);
            totalSpend = totalSpend + entryObject.getSpendAmount();
        }
        tvDisplayTotalSpend.setText(String.valueOf("TOTAL SPEND: RM " + decimalFormat.format(totalSpend)));
        tvDisplayTotalSpend.setTextColor(Color.parseColor("#FFA500"));

        //to calculate
        if (totalSpend > 2000) {
            for (int i = 0; i < lvDisplaySpendEntries.getCount(); i++) {
                EntryObject entryObject = (EntryObject) lvDisplaySpendEntries.getItemAtPosition(i);

                if (entryObject.getSpendCategory().equals("Petrol Spend")) {
                    if (entryObject.getSpendAmount() * 0.08 > 15) {
                        tempCashBack = 15;
                    } else
                        tempCashBack = entryObject.getSpendAmount() * 0.08;
                    totalPetrolCB +=tempCashBack;
                } else if (entryObject.getSpendCategory().equals("Groceries Spend")) {
                    double checkCap, checkCap2;

                    if (entryObject.getSpendAmount() * 0.078 > 15) {
                        checkCap = 15;
                    } else
                        checkCap = entryObject.getSpendAmount() * 0.078;

                    if (entryObject.getSpendAmount() * 0.002 > 15) {
                        checkCap2 = 15;
                    } else
                        checkCap2 = entryObject.getSpendAmount() * 0.002;

                    //accumulate both checkCap
                    tempCashBack = checkCap + checkCap2;
                    totalGroceriesCB += tempCashBack;
                } else if (entryObject.getSpendCategory().equals("eWallet Transactions")) {
                    double checkCap, checkCap2;

                    if (entryObject.getSpendAmount() * 0.078 > 15) {
                        checkCap = 15;
                    } else
                        checkCap = entryObject.getSpendAmount() * 0.078;

                    if (entryObject.getSpendAmount() * 0.002 > 15) {
                        checkCap2 = 15;
                    } else
                        checkCap2 = entryObject.getSpendAmount() * 0.002;

                    //accumulate both checkCap
                    tempCashBack = checkCap + checkCap2;
                    totalEwalletCB += tempCashBack;
                } else if (entryObject.getSpendCategory().equals("All other Eligible Spend")) {
                    if (entryObject.getSpendAmount() * 0.002 > 15) {
                        tempCashBack = 15;
                    } else
                        tempCashBack = entryObject.getSpendAmount() * 0.002;
                    totalOtherCB += tempCashBack;
                }
                // total up all cashback
                totalCashBack = totalCashBack + tempCashBack;

            }

        } else if (totalSpend < 2000) {
            for (int i = 0; i < lvDisplaySpendEntries.getCount(); i++) {
                EntryObject entryObject = (EntryObject) lvDisplaySpendEntries.getItemAtPosition(i);

                if (entryObject.getSpendCategory().equals("Petrol Spend")) {
                    tempCashBack = entryObject.getSpendAmount() * 0.01;
                    totalPetrolCB += tempCashBack;
                } else if (entryObject.getSpendCategory().equals("Groceries Spend")) {
                    tempCashBack = (entryObject.getSpendAmount() * 0.008) + (entryObject.getSpendAmount() * 0.002);
                    totalGroceriesCB += tempCashBack;
                } else if (entryObject.getSpendCategory().equals("eWallet Transactions")) {
                    tempCashBack = (entryObject.getSpendAmount() * 0.008) + (entryObject.getSpendAmount() * 0.002);
                    totalEwalletCB += tempCashBack;
                } else if (entryObject.getSpendCategory().equals("All other Eligible Spend")) {
                    tempCashBack = entryObject.getSpendAmount() * 0.002;
                    totalOtherCB += tempCashBack;
                }
                //total up all cashback
                totalCashBack = totalCashBack + tempCashBack;
            }
        }//end if

        //update to textview
        cashBackPetrol.setText(String.valueOf("PETROL CASHBACK: RM " + decimalFormat.format(totalPetrolCB)));
        cashBackGroceries.setText(String.valueOf("GROCERIES CASHBACK: RM " + decimalFormat.format(totalGroceriesCB)));
        cashBackEWalet.setText(String.valueOf("E-WALLET CASHBACK: RM " + decimalFormat.format(totalEwalletCB)));
        cashBackOther.setText(String.valueOf("OTHERS SPEND CASHBACK: RM " + decimalFormat.format(totalOtherCB)));
        tvDisplayTotalCashBack.setText(String.valueOf("TOTAL EARNED: RM " + decimalFormat.format(totalCashBack)));
        tvDisplayTotalCashBack.setTextColor(Color.parseColor("#FFA500"));

        lvDisplaySpendEntries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                EntryObject entryObject = (EntryObject) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(Homepage.this, EditEntry.class);
                intent.putExtra("sendID", String.valueOf(entryObject.getSpendID()));
                intent.putExtra("entryObject", (EntryObject) adapterView.getItemAtPosition(i));

                startActivity(new Intent(intent));
            }
        });

    }//end onCreate

    private void displayToList(DataBaseHelper dataBaseHelper) {
        arrayAdapter = new ArrayAdapter<EntryObject>(Homepage.this, android.R.layout.simple_list_item_1, dataBaseHelper.getAll());
        lvDisplaySpendEntries.setAdapter(arrayAdapter);
    }


}