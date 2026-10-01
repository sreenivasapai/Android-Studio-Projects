package com.example.checkbox;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.checkbox.R;

public class MainActivity extends AppCompatActivity {
    CheckBox cpython,cjava,chtml,cphp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        cpython=findViewById(R.id.checkBox3);
        cjava=findViewById(R.id.checkBox);
        chtml=findViewById(R.id.checkBox2);
        cphp=findViewById(R.id.checkBox4);
        CompoundButton.OnCheckedChangeListener checkBoxListner = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton ButtonView, boolean b) {
                String lang=ButtonView.getText().toString();
                if(b){
                    showToast(lang+" Selected");
                }
                else{
                    showToast(lang+" Deselected");
                }
            }
        };
        cpython.setOnCheckedChangeListener(checkBoxListner);
        cjava.setOnCheckedChangeListener(checkBoxListner);
        chtml.setOnCheckedChangeListener(checkBoxListner);
        cphp.setOnCheckedChangeListener(checkBoxListner);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showToast(String s) {
        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();

    }
}