package india.abhijeet.k.anylearn;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class Upload_LectureFragment extends Fragment {

    private Button upload_video;
    private Button choose_upload_video;
    private EditText title_upload_video;
    private VideoView upload_videoView;
    private ProgressBar upload_video_progressBar;

    private Uri video_uri;
    String vurl=" no video";
    private FirebaseAuth firebaseAuth;

    private StorageReference vStorageReference;
    private DatabaseReference vDatabaseReference;

    boolean videoSelected =false;


    Uri download_uri;

    private StorageTask vUploadTask;

    private static final int PICK_VIDEO_REQUEST =1;
    private static final int RESULT_OK =1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View v = inflater.inflate(R.layout.fragment_upload_lectures,container,false);

        upload_video=v.findViewById(R.id.btn_upload_video);
                choose_upload_video=v.findViewById(R.id.btn_choose_upload_video);
        title_upload_video= v.findViewById(R.id.txt_upload_video_title);
                upload_videoView=v.findViewById(R.id.videoView_upload_video);
        upload_video_progressBar=v.findViewById(R.id.progress_upload_video);


           vStorageReference =FirebaseStorage.getInstance().getReference("Uploads");


           firebaseAuth=FirebaseAuth.getInstance();

      //  vStorageReference =FirebaseStorage.getInstance().getReference();
           vDatabaseReference =FirebaseDatabase.getInstance().getReference("Uploaded Data");
      //  vDatabaseReference =FirebaseDatabase.getInstance().getReference();

//      allow read, write: if request.auth != null;


        MediaController mediaController =new MediaController(this.getContext());
        mediaController.setAnchorView(upload_videoView);
              upload_videoView.setMediaController(mediaController);


        upload_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (vUploadTask!=null&& vUploadTask.isInProgress())
                {
                    Toast.makeText(getContext(),"Upload work is in progress,Wait for finish",Toast.LENGTH_SHORT).show();

                }
                if (title_upload_video.getText().toString()=="")
                {

                    Toast.makeText(getContext(),"Please give a Title for the video.",Toast.LENGTH_SHORT).show();


                }
                else {


                    uploadVideo();
                }
                //upload video here
            }
        });


       choose_upload_video.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // choose which video is going to upload
               openFileChooser();
           }
       });


    return v;
    }


    private void openFileChooser()
    {
        Intent intent =new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_VIDEO_REQUEST);

        }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     //   if ( //requestCode==PICK_VIDEO_REQUEST && resultCode == RESULT_OK)

        if( data !=  null && data.getData()!=null  )
        {

            video_uri= data.getData();

            upload_videoView.setVideoURI(video_uri);

            upload_videoView.requestFocus();
            upload_videoView.start();

        }

        else {

            Toast.makeText(this.getContext(),"something wrong here!!!!",Toast.LENGTH_LONG).show();

        }

    }



    private void uploadVideo()
    {

        if (video_uri!= null)
        {

          final StorageReference fileReference=vStorageReference.child(System.currentTimeMillis()+" "+title_upload_video.getText().toString().trim()+" video Lecture");

           /*  vUploadTask= fileReference.putFile(video_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
              @Override
              public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                  Handler handler =new Handler();
                  handler.postDelayed(new Runnable() {
                      @Override
                      public void run() {
                    upload_video_progressBar.setProgress(0);

                        Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) {
                            download_uri = uriTask.getResult();
                        }
                            vurl = download_uri.toString();
                          //Uri test= fileReference.getDownloadUrl().getResult();
                          //vurl= test.toString().trim();
                      }
                  },2500);

                  Toast.makeText(getContext(),"Upload Successfully",Toast.LENGTH_LONG).show();

                  try {
                      Uploading uploading = new Uploading(title_upload_video.getText().toString().trim(), vurl.toString().trim());

                      String uploadId = vDatabaseReference.push().getKey();
                      vDatabaseReference.child(uploadId).setValue(uploading);
                  }
                  catch (Exception es)
                  {

                      Toast.makeText(getContext(),"something bad happened",Toast.LENGTH_LONG).show();
                  }
                  ////try new code
              }



          }).addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {

             Toast.makeText( getContext()," "+e.getMessage(),Toast.LENGTH_SHORT).show();


              }
          }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
              @Override
              public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                  double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());

                   upload_video_progressBar.setProgress((int)progress);
              }
          });*/






            vUploadTask= fileReference.putFile(video_uri);

            vUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful())
                    {
                       throw task.getException();

                    }

                    upload_video_progressBar.setProgress(100);


                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()){

                          download_uri=task.getResult();
                        vurl= download_uri.toString().trim();
                        Toast.makeText(getContext(),"Upload Successfully",Toast.LENGTH_LONG).show();

                        Uploading uploading = new Uploading(title_upload_video.getText().toString().trim(), vurl.toString().trim(),"0");


                       // String uploadId = vDatabaseReference.push().getKey();
                        String uploadId = firebaseAuth.getCurrentUser().getUid();

                        vDatabaseReference.child(uploadId).setValue(uploading);


                        Handler handler =new Handler();
                        handler.postDelayed(
                                new Runnable() {
                            @Override
                            public void run() {
                                upload_video_progressBar.setProgress(0);

                            }
                        },2500);


                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText( getContext()," "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });





        }
        else{


            Toast.makeText(this.getContext(),"Please select video first",Toast.LENGTH_SHORT).show();
        }

    }


}
