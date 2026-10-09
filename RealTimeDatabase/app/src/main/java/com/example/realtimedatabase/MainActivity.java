package com.example.realtimedatabase;

import static android.app.ProgressDialog.show;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private person person;

    EditText Et1,Et2;
    Button B1,B2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Et1=findViewById(R.id.editTextText);
        Et2=findViewById(R.id.editTextText2);
        B1=findViewById(R.id.Bsave);
        B2=findViewById(R.id.Bretrive);

        //get instance of Firebase Database
        firebaseDatabase =FirebaseDatabase.getInstance();
        //set Reference of the DataBase
        databaseReference = firebaseDatabase.getReference("personData");
        //initializing person object
        person = new person();

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Et1.getText().toString();
                String phone = Et2.getText().toString();
                if(name.isEmpty() && phone.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Please Add Some Data. ",Toast.LENGTH_SHORT).show();
            }
                else {
                    addDataToFirebase(name,phone);
                }
            }
        });
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retriveFirstDataFromFirebase();
            }
        });




    }

    private void addDataToFirebase(String name, String phone) {
        person.setName(name);
        person.setNumber(phone);
        //use push() tocreate a unique key for each person
        databaseReference.push().setValue(person)
                .addOnSuccessListener((Void aVoid) -> Toast.makeText(MainActivity.this,"Data Addd Successfully", Toast.LENGTH_SHORT).show())

                .addOnFailureListener(error ->
                        Toast.makeText(MainActivity.this,"Failed to Add Data : "+ error.getMessage(),Toast.LENGTH_SHORT).show()
                );
    }
    private void retriveFirstDataFromFirebase(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    person person = dataSnapshot.getValue(person.class);
                    if (person != null){
                        Toast.makeText(getApplicationContext(), "Name ="+person.getName()+"\n"+
                                "phone number = " + person.getNumber(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
