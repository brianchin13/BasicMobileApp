package com.utar.edu.mobileindividualassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditEntry extends AppCompatActivity {

    Button btnBackFromEdit, btnEditSpending, btnDeleteSpending;
    Spinner spnSelectedCat;
    EditText spendDescription, spendAmount;
    TextView tvShowID;
    ListView lvData;
    DataBaseHelper dataBaseHelper;
    int ID;

    //object pass from homepage which user selected
    EntryObject selectedEntryObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        btnBackFromEdit = findViewById(R.id.btnBackFromEdit);
        btnEditSpending = findViewById(R.id.btnEditSpending);
        btnDeleteSpending = findViewById(R.id.btnDeleteSpending);
        spnSelectedCat = findViewById(R.id.spnSelectedCategory);
        spendDescription = findViewById(R.id.enteredSpendDescription);
        spendAmount = findViewById(R.id.enteredSpendAmount);
        tvShowID = findViewById(R.id.tvShowID);
        lvData = findViewById(R.id.lvSpendEntries);

        ID = Integer.parseInt(this.getIntent().getStringExtra("sendID"));
        selectedEntryObject = (EntryObject) this.getIntent().getSerializableExtra("entryObject");
        dataBaseHelper = new DataBaseHelper(EditEntry.this);

        //to set the spend id;
        if (this.getIntent().getStringExtra("sendID") != null) {
            tvShowID.setText("SELECTED SPEND ID: " + ID);
        } else
            Toast.makeText(EditEntry.this, "No ID is sent", Toast.LENGTH_LONG).show();


        //set all the form into selected data details
        setSelectedCategory(spnSelectedCat, selectedEntryObject.getSpendCategory());
        spendDescription.setText(selectedEntryObject.getSpendDescription());
        spendAmount.setText(String.valueOf(selectedEntryObject.getSpendAmount()));

        //update data
        btnEditSpending.setOnClickListener(e -> {
            try {
                if (dataBaseHelper.updateEntry(ID, spnSelectedCat.getSelectedItem().toString(), spendDescription.getText().toString(), Double.parseDouble(spendAmount.getText().toString())) == 1) {
                    Toast.makeText(EditEntry.this, "SUCCESSFULLY UPDATE", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(EditEntry.this, Homepage.class));
                } else
                    Toast.makeText(EditEntry.this, "ERROR UPDATE", Toast.LENGTH_LONG).show();

            } catch (Exception ex) {
                Toast.makeText(EditEntry.this, "ERROR UPDATING", Toast.LENGTH_LONG).show();
            }
        });

        //delete data
        btnDeleteSpending.setOnClickListener(e -> {
            try {
                dataBaseHelper.deleteEntry(selectedEntryObject);
                Toast.makeText(EditEntry.this, "SPENDING ID: " + ID + " \nDELETED SUCCESSFULLY ", Toast.LENGTH_LONG).show();
                startActivity(new Intent(EditEntry.this, Homepage.class));

            } catch (Exception error) {
                Toast.makeText(EditEntry.this, "Error DELETING", Toast.LENGTH_LONG).show();
            }
        });

        btnBackFromEdit.setOnClickListener(e -> {
            startActivity(new Intent(EditEntry.this, Homepage.class));
        });
    }//end on cr8

    //to set the Spinner to the value in object value
    private void setSelectedCategory(Spinner spinner, Object value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

}

