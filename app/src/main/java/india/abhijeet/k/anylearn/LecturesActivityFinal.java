package india.abhijeet.k.anylearn;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LecturesActivityFinal extends AppCompatActivity {

    private RecyclerView mrecyclerView;
     private VideoAdapter mAdapter;

     private DatabaseReference mDatabaseRef;
     private List<Uploading> mUploads;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectures_final);

            mrecyclerView =findViewById(R.id.recycler_videos_in_activity);

           mrecyclerView.setLayoutManager(new LinearLayoutManager(this));


           mUploads =new ArrayList<>();

           mDatabaseRef = FirebaseDatabase.getInstance().getReference("Uploaded Data");

           mDatabaseRef.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   for (DataSnapshot postSnapshot :dataSnapshot.getChildren() )
                   {
                       Uploading upload =postSnapshot.getValue(Uploading.class);
                       mUploads.add(upload);


                   }

                   mAdapter =new VideoAdapter(LecturesActivityFinal.this,mUploads);
                   mrecyclerView.setAdapter(mAdapter);





               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

                   Toast.makeText(LecturesActivityFinal.this,"Something wrong"+databaseError.getMessage(),Toast.LENGTH_SHORT).show();

               }
           });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
