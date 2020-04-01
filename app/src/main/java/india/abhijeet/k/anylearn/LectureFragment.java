package india.abhijeet.k.anylearn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LectureFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private VideoAdapter mVideoAdapter;

    private DatabaseReference mDatabaseReference;
    private List<Uploading> mUploads;

private ProgressBar prb_cr;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
             View v= inflater.inflate(R.layout.fragment_lectures,container,false);


             prb_cr= v.findViewById(R.id.progress_recycler_buffer);

         mRecyclerView=v.findViewById(R.id.recycler_videos);
         mRecyclerView.setHasFixedSize(true);
         mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

         mUploads = new ArrayList<>();

         mDatabaseReference= FirebaseDatabase.getInstance().getReference("Uploaded Data");


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                 {

                     Uploading upload =postSnapshot.getValue(Uploading.class);

                     //upload.setvName();
                     //upload.setvVideoUrl();

                     mUploads.add(upload);
                     }

                 mVideoAdapter = new VideoAdapter(getContext(),mUploads);

                 mRecyclerView.setAdapter(mVideoAdapter);
                 prb_cr.setVisibility(View.GONE);

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

               //  Toast.makeText(getContext(),"Warning!! "+databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                 prb_cr.setVisibility(View.GONE);
             }
         });


        return v;
    }
}
