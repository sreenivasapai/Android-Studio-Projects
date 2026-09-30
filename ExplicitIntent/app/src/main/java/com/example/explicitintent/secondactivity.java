package com.example.explicitintent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class secondactivity extends AppCompatActivity {
    EditText set1;
    Button sbt1;
    Integer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_secondactivity);
        set1=findViewById(R.id.secondeditTextText);
        sbt1=findViewById(R.id.sbt1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sbt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent si=getIntent();
                int rn1;
                int rn2;
                rn1 = si.getIntExtra("key1", 0);
                rn2=si.getIntExtra("key2",0);
                result=rn1*rn2;
                //set1.setText(result);
                set1.setText(String.valueOf(result));

            }
        });
    }
}