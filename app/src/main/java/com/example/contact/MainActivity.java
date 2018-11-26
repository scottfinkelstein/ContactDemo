package com.example.contact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Button mButton;


    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mButton = (Button)findViewById(R.id.submit);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String to = ((EditText)findViewById(R.id.to)).getText().toString();
                String subject = ((EditText)findViewById(R.id.subject)).getText().toString();
                String message = ((EditText)findViewById(R.id.message)).getText().toString();

                insertMessage(UUID.randomUUID().toString(), to, subject, message);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Message message1 = dataSnapshot.getValue(Message.class);
                        Log.d("MainActivity", "Message sent to: " + message1.to);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

//                Intent mail = new Intent(Intent.ACTION_SEND);
//                mail.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
//                mail.putExtra(Intent.EXTRA_SUBJECT, subject);
//                mail.putExtra(Intent.EXTRA_TEXT, message);
//                mail.setType("message/rfc822");
//                startActivity(Intent.createChooser(mail, "Send Mail Via:"));
            }
        });
    }

    private void insertMessage(String key, String to, String subject, String message) {
        Message newMessage = new Message(to, subject, message);


        ref.child("messages").child(key).setValue(newMessage);
    }
}
