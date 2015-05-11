package mortgage.android.scu.edu.mortgagecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    EditText text;
    double interest;
    TextView showInterestRate;
    double mortgageValue;
    TextView disAmt;
    CheckBox taxes;
    RadioButton year15;
    RadioButton year20;
    RadioButton year30;
    String resultLabel = "Monthly Payment :";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting handles for all the UI component

        text = (EditText) findViewById(R.id.PrincipalAmountET);

        interest = 10;
        showInterestRate = (TextView) findViewById(R.id.displayInterestTV);
        showInterestRate.setText(String.format("%.02f", interest) + "%");

        SeekBar interestRateSkBar = (SeekBar) findViewById(R.id.interestSeekBar);
        interestRateSkBar.setOnSeekBarChangeListener(mySeekBarListener);

        year15 = (RadioButton) findViewById(R.id.term15Rb);
        year20 = (RadioButton) findViewById(R.id.term20Rb);
        year30 = (RadioButton) findViewById(R.id.term30Rb);

        taxes = (CheckBox) findViewById(R.id.TaxChkBox);

        disAmt = (TextView) findViewById(R.id.monthlyPayTV);

    }

    //SeekBar Listener
    private SeekBar.OnSeekBarChangeListener mySeekBarListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            interest = (seekBar.getProgress() / 100.0);
            showInterestRate.setText(String.format("%.02f", interest) + "%");


        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    // this method is called at button click because we assigned "onClick" method to the "OnClick property" of the button
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.calculateBtn:

                //Validates if no input in text box
                if (text.getText().length() == 0) {
                    Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_LONG).show();
                    disAmt.setText(resultLabel + "");
                    return;
                }


                double inputValue = Double.parseDouble(text.getText().toString());

                //Checks for the term of loan
                int numOfMonths;
                if (year15.isChecked()) {
                    numOfMonths = (15 * 12);

                } else if (year20.isChecked()) {
                    numOfMonths = (20 * 12);

                } else
                    numOfMonths = (30 * 12);


                double taxAmt = 0.0;

                //Checks if tax and insurance are to be included
                if (taxes.isChecked()) {
                    taxAmt = (inputValue) / 1000;
                }
                //Called when interest = 0.0%
                if (interest == 0.0) {
                    mortgageValue = CalculatorUtil.calculateMortgageWithoutInterest(inputValue, numOfMonths, taxAmt);
                //For all other values of interest
                } else {
                    double monthlyInterest = interest / 1200;
                    mortgageValue = CalculatorUtil.calculateMortgageWithInterest(inputValue, monthlyInterest, numOfMonths, taxAmt);
                }
                // formatting to 2 decimal precision
                String mv = String.format("%.2f", mortgageValue);
                disAmt.setText(resultLabel + mv);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
