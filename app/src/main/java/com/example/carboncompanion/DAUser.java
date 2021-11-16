package com.example.carboncompanion;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DAUser {
    private DatabaseReference mDatabase;
    FirebaseDatabase db;

    public DAUser(){
        db = FirebaseDatabase.getInstance();
        mDatabase = db.getReference(User.class.getSimpleName());
    }

    // Uid is the unique user id assigned by firebase. Can be seen on the authentication page of the firebase website
    public void add(String Uid, User user) {
        DatabaseReference userRef = mDatabase.child(Uid);
        userRef.setValue(user);

        //        DatabaseReference rootRef =  db.getReference();
        //        DatabaseReference userRef = rootRef.child("User");
        //        emailRef.setValue(user);
        // return mDatabase.push().setValue(user);
        //        DatabaseReference myRef = mDatabase.child("profiles/" + user.getEmail());
        //        myRef.setValue(user);
    }

    // this method is supposed to query the database and find the total number of activities associated with this email
    public int getActivities(String email) {
        return 0;
    }

    /**
    public void writeNewUser(String email, int numActivities, int carbonSaved) {
        User user = new User(email, numActivities, carbonSaved);

        mDatabase.child("users").child(email).setValue(user);
    }

    public void addActivity(String email, int carbon) {
        mDatabase.child("users").child(email).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }

    public int getActivities(String email) {
        final User[] user = new User[1];
        Query phoneQuery = mDatabase.equalTo(email);
        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    user[0] = singleSnapshot.getValue(User.class);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
        return user[0].getNumActivities();
    }
     */
}
