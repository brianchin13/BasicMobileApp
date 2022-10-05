package com.utar.edu.mobileindividualassignment;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddEntry extends AppCompatActivity {

    Spinner spnCategory;
    EditText spendDescription, spendAmount;
    Button btnAddEntry, btnBack;
    DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        spnCategory = findViewById(R.id.spnCategory);
        spendDescription = findViewById(R.id.spendDescription);
        spendAmount = findViewById(R.id.spendAmount);
        btnAddEntry = findViewById(R.id.btnAddSpending);
        btnBack = findViewById(R.id.btnBack);


        btnAddEntry.setOnClickListener(e -> {
            EntryObject entryObject;

            try {
                entryObject = new EntryObject(-1, spnCategory.getSelectedItem().toString(), spendDescription.getText().toString(), Double.parseDouble(spendAmount.getText().toString()));
                Toast.makeText(AddEntry.this, "SUCCESSFULLY ADDED", Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Toast.makeText(AddEntry.this, "Error creating spend", Toast.LENGTH_LONG).show();
                entryObject = new EntryObject(-1, "error", "error", 0);
            }
            dataBaseHelper = new DataBaseHelper(AddEntry.this);
            dataBaseHelper.addEntry(entryObject);
        });

        btnBack.setOnClickListener(e -> {
            startActivity(new Intent(AddEntry.this, Homepage.class));
        });
    }
}