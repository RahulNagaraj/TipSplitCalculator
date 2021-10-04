package com.rahulnagaraj.tipsplitcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText billTotalWithTax;
    EditText numberOfPeople;
    TextView tipAmount;
    TextView totalWithTip;
    RadioGroup tipPercent;
    TextView totalPerPerson;
    TextView overage;
    ConstraintLayout constraintLayout;

    double billTotalTax;
    double tips;
    double perPerson;
    double billTotal;
    double over;
    double percent;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeAllInputFieldReference();
    }

    public void initializeAllInputFieldReference() {
        billTotalWithTax = findViewById(R.id.billTotalWithTax);
        numberOfPeople = findViewById(R.id.numberOfPeople);
        tipAmount = findViewById(R.id.tipAmount);
        totalWithTip = findViewById(R.id.totalWithTip);
        numberOfPeople = findViewById(R.id.numberOfPeople);
        totalPerPerson = findViewById(R.id.totalPerPerson);
        overage = findViewById(R.id.overage);
        tipPercent = findViewById(R.id.radioTip);
        constraintLayout = findViewById(R.id.constraintLayout);
    }

    public void onRadioButtonClicked(View v) {
        String billTotalWithTaxText = billTotalWithTax.getText().toString();
        if (billTotalWithTaxText.equals("")) {
            tipPercent.clearCheck();
            Toast.makeText(this, "Bill Total with Tax cannot be empty.", Toast.LENGTH_LONG).show();
        } else {
            billTotalTax = Double.parseDouble(billTotalWithTaxText);
            switch (v.getId()) {
                case R.id.radio_12:
                    percent = 0.12;
                    break;
                case R.id.radio_15:
                    percent = 0.15;
                    break;
                case R.id.radio_18:
                    percent = 0.18;
                    break;
                case R.id.radio_20:
                    percent = 0.2;
                    break;
                default:
                    break;
            }
            tips = billTotalTax * percent;
            String tipsString = String.format("%.2f", tips);
            tipAmount.setText("$" + tipsString);
            billTotal = tips + billTotalTax;
            totalWithTip.setText("$" + String.format("%.2f", billTotal));


            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(constraintLayout.getWindowToken(), 0);
        }
    }

    public void onSplitBillButtonClick(View v)  {
        String numOfPeopleText = numberOfPeople.getText().toString();
        if (numOfPeopleText.equals("")) {
            Toast.makeText(this, "Number of People cannot be empty.", Toast.LENGTH_LONG).show();
            numberOfPeople.setText("");
            totalPerPerson.setText("");
            overage.setText("");
            return;
        }
        if (numOfPeopleText.equals("0")) {
            Toast.makeText(this, "Number of People cannot be 0.", Toast.LENGTH_LONG).show();
            numberOfPeople.setText("");
            totalPerPerson.setText("");
            overage.setText("");
            return;
        }
        if (totalWithTip.getText().toString().equals("")) {
            Toast.makeText(this, "Total with Tip cannot be empty", Toast.LENGTH_LONG).show();
            numberOfPeople.setText("");
            totalPerPerson.setText("");
            overage.setText("");
            return;
        }
        int numPeople = Integer.parseInt(numOfPeopleText);
        perPerson = billTotal / numPeople;
        perPerson = Math.ceil(perPerson * 100.0)/100.0;
        totalPerPerson.setText("$" + String.format("%.2f", perPerson));

        double pPerson = perPerson * numPeople;
        over = pPerson - billTotal;
        overage.setText("$" +  String.format("%.2f", over));


        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(constraintLayout.getWindowToken(), 0);
    }

    public void onClearButtonClick(View v) {
        initializeData();

        numberOfPeople.clearFocus();
    }

    private void initializeData() {
        billTotalWithTax.setText("");
        tipPercent.clearCheck();
        tipAmount.setText("");
        totalWithTip.setText("");
        numberOfPeople.setText("");
        totalPerPerson.setText("");
        overage.setText("");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("tipAmount", tipAmount.getText().toString());
        outState.putString("totalWithTip", totalWithTip.getText().toString());
        outState.putString("totalPerPerson", totalPerPerson.getText().toString());
        outState.putString("overage", overage.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        tipAmount.setText(savedInstanceState.getString("tipAmount"));
        totalWithTip.setText(savedInstanceState.getString("totalWithTip"));
        totalPerPerson.setText(savedInstanceState.getString("totalPerPerson"));
        overage.setText(savedInstanceState.getString("overage"));
    }
}