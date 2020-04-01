package india.abhijeet.k.anylearn;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>{

  private Context mcontext;
  private List<Uploading> mUploding;


    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef,sDataref;

    private FirebaseAuth firebaseAuth;

    String VdVws;
    int vidviews;
    String userid;

   String downloadTitle;
   String downloadUrl;
   String strVideoViews;

    public VideoAdapter(Context context,List<Uploading> upload)
  {
      mcontext =context;
      mUploding=upload;

  }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

       View v= LayoutInflater.from(mcontext).inflate(R.layout.video_item, viewGroup,false );

       firebaseAuth =firebaseAuth.getInstance();

        mDatabaseRef= FirebaseDatabase.getInstance().getReference("Uploaded Data");


        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder videoViewHolder, int i) {


      Uploading uploadingCurrent =mUploding.get(i);
      videoViewHolder.videoTitle.setText(uploadingCurrent.getName());
       videoViewHolder.videoViews.setText(uploadingCurrent.getviews());

      //downloadTitle=uploadingCurrent.getName().trim();

    videoViewHolder.lecturesVideos.setVideoURI(Uri.parse(uploadingCurrent.getvVideoUrl()));
    try {


        videoViewHolder.url_conti.setText(Uri.parse(uploadingCurrent.getvVideoUrl()).toString().trim());
     }
       catch (Exception imsp)

       {
           Toast.makeText(mcontext,"Problem in Binder"+imsp,Toast.LENGTH_LONG).show();
       }

        //downloadUrl=uploadingCurrent.getvVideoUrl().trim();
       videoViewHolder.lecturesVideos.requestFocus();
      //  videoViewHolder.lecturesVideos.start();// Video automatically get started

    }

    @Override
    public int getItemCount() {
        return mUploding.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder
    {


        public TextView videoTitle;
        public TextView videoViews;

        public ProgressBar buffer_bar;
        public Button downloadButton;
        public VideoView lecturesVideos;
        public EditText url_conti;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

               videoTitle=itemView.findViewById(R.id.txt_video_title_name_download);
               lecturesVideos=itemView.findViewById(R.id.videoView_lecture_video_download);
               buffer_bar =itemView.findViewById(R.id.progressBar_buffer);
                downloadButton=itemView.findViewById(R.id.btn_download_lectures);
           url_conti=itemView.findViewById(R.id.txt_url_container);

           videoViews=itemView.findViewById(R.id.lbl_video_views);

            //   lecturesVideos.setVideoURI(Uri.parse(mstorage.getDownloadUrl().toString()));

           //lecturesVideos.setVideoURI(Uri.parse(Uploading.getvVideoUrl()));


            MediaController mediaController =new MediaController(mcontext);
            mediaController.setAnchorView(lecturesVideos);
            lecturesVideos.setMediaController(mediaController);

             // lecturesVideos.start();

            mediaController.setMediaPlayer(lecturesVideos);

            //////Code for increasing views
//
//            if(mediaController.isShowing())
//            {
//
//                lecturesVideos.pause();
//                addViews();
//            }

            //lecturesVideos.requestFocus();
          //  lecturesVideos.start();


            downloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(mcontext,"Downloading",Toast.LENGTH_SHORT).show();

                    try {


                        downloadTitle = videoTitle.getText().toString().trim();
                        downloadUrl = url_conti.getText().toString().trim();

                        download_video(mcontext, downloadTitle, DIRECTORY_DOWNLOADS, downloadUrl);
                    }catch (Exception esp)
                    {
                           Toast.makeText(mcontext,"Sorry Cant download"+esp,Toast.LENGTH_LONG).show();

                    }


                }
            });


        }
    }





     public void addViews()
     {


         userid=firebaseAuth.getUid().trim();
               sDataref= FirebaseDatabase.getInstance().getReference("Uploaded Data").child(userid);

                   sDataref.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Uploading uploading=dataSnapshot.getValue(Uploading.class);

                        VdVws=uploading.getviews().trim();
                          vidviews= Integer.parseInt(VdVws);

                          vidviews=vidviews+1;
                           sDataref =FirebaseDatabase.getInstance().getReference("Uploaded Data").child(userid);
                           sDataref.child("views").setValue(""+vidviews);



                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });


     }




       public void download_video(Context context,String fileName,String destinationDirectory,String urims)
       {
           addViews();

           DownloadManager downloadManager =(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
           String uriString;
           Uri urim= Uri.parse(urims);

           DownloadManager.Request request =new DownloadManager.Request(urim);

           request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
           request.setDestinationInExternalFilesDir(context,destinationDirectory,fileName);
           downloadManager.enqueue(request);
           Toast.makeText(mcontext,"Your video is downloaded!!",Toast.LENGTH_SHORT).show();

       }



}
