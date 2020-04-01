package india.abhijeet.k.anylearn;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import india.abhijeet.k.anylearn.R;

public class CaptureFragment extends Fragment {

    private TextView opcam;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_captures,container,false);
     opcam=v.findViewById(R.id.txt_openCam);



     opcam.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

             try {
                 Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                 startActivity(intent);
             }catch (Exception ioes)
             {

                 Toast.makeText(getContext(),"Cannot open camera check for permissions",Toast.LENGTH_SHORT).show();

             }
         }
     });

        return v;

    }



}
