package com.example.radiobutton;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RadioButton rpython, rjava, rhtml, rphp;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find RadioButtons
        rpython = findViewById(R.id.radioButton3);
        rjava = findViewById(R.id.radioButton);
        rhtml = findViewById(R.id.radioButton2);
        rphp = findViewById(R.id.radioButton4);

        // Find RadioGroup
        radioGroup = findViewById(R.id.radioGroup);

        // Listener for RadioGroup
        radioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        RadioButton selectedButton =
                                findViewById(checkedId);

                        String language =
                                selectedButton.getText().toString();

                        showToast(language + " Selected");
                    }
                }
        );
    }

    private void showToast(String s) {
        Toast.makeText(
                MainActivity.this,
                s,
                Toast.LENGTH_SHORT
        ).show();
    }
}
