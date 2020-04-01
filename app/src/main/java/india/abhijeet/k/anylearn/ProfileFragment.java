package india.abhijeet.k.anylearn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    TextView userid;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

           userid=v.findViewById(R.id.txt_userProp_id);
        firebaseAuth= FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

    try
        {
           userid.setText(""+ user.getEmail());
        }
        catch (Exception es)
        {

          // Toast.makeText(this," "+es,Toast.LENGTH_LONG).show();
        }

        return v;
        }
}
