package com.yorku.grewal.jaskarn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String unitsSelected;

    EditText weight;
    EditText height;
    EditText height2;

    Spinner units;

    TextView bmiCalculated;
    TextView statusTextView;

    Button calculateBmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weight = findViewById(R.id.userWeightEditText);
        height = findViewById(R.id.userHeightEditText);
        height2 = findViewById(R.id.userHeightEditText2);
        height2.setVisibility(View.INVISIBLE);

        units = findViewById(R.id.unitsSpinner);
        units.setOnItemSelectedListener(this);

        bmiCalculated = findViewById(R.id.calculatedBmiTextView);
        statusTextView = findViewById(R.id.levelTextView);

        calculateBmi = findViewById(R.id.calculateBtn);


        calculateBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((weight.getText().toString().equals("")) || (height.getText().toString().equals("")) || ((height2.getText().toString().equals("")))&& (unitsSelected.equals("Imperial"))) {
                    bmiCalculated.setText("Please Fill in All of The Fields");
                    statusTextView.setText("");
                }
                else {
                    double userWeight = Double.parseDouble(weight.getText().toString());
                    double userHeight = Double.parseDouble(height.getText().toString());

                    if (unitsSelected.equals("Imperial")){
                        double userHeight2 = Double.parseDouble(height2.getText().toString());
                        double calcBmi = CalculateBmiImperial((userWeight), (userHeight), (userHeight2));
                        String result = Double.toString(calcBmi);
                        bmiCalculated.setText(result);
                        check(calcBmi);
                    }
                    else{
                        double calcBmi = CalculateBmiMetric((userWeight), (userHeight));
                        String result = Double.toString(calcBmi);
                        bmiCalculated.setText(result);
                        check(calcBmi);
                    }
                }

            }
       });
    }
    private double CalculateBmiImperial(double weight, double inches, double feet) {

        double inchess= (feet*12)+inches;
        double  bmi = (703 * weight)/(Math.pow(inchess,2));
        return Rounded(bmi);

    }

        private double CalculateBmiMetric(double weight, double height) {

        double bmi = weight / Math.pow(height/100, 2);
        return Rounded(bmi);

   }

        private String check(double bmi) {

            if (bmi < 18.5) {
                statusTextView.setText("You are Under Weight");
            } else if (bmi >= 18.5 && bmi <= 24.9) {
                statusTextView.setText("You are Normal Weight");
            } else if (bmi >= 25 && bmi <= 29.9) {
                statusTextView.setText("You are Over Weight");
            } else {
                statusTextView.setText("You are Obese");
            }
            return "";
        }

        private static double Rounded(double value) {

            double result = Math.round(value * 10.0) / 10.0;

            return result;

        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        unitsSelected = parent.getItemAtPosition(position).toString();
         if (unitsSelected.equals("Imperial")){
           weight.setText("");
           height.setText("");
           height2.setText("");
           bmiCalculated.setText("");
           statusTextView.setText("");
           weight.setHint("Enter Weight (lbs)");
           height.setHint("Enter Height (in)");
           height2.setVisibility(View.VISIBLE);
           height2.setHint("Enter Height (ft)");
        }
         else{
             weight.setText("");
             height.setText("");
             bmiCalculated.setText("");
             statusTextView.setText("");
             weight.setHint("Enter Weight (kg)");
             height.setHint("Enter Height (cm)");
             height2.setText("");
             height2.setVisibility(View.INVISIBLE);
         }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

