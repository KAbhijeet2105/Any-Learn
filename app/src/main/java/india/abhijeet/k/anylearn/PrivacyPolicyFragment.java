package india.abhijeet.k.anylearn;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PrivacyPolicyFragment extends Fragment {

    TextView pripolicy;
    TextView terms;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_privacy_policy,container,false);


        pripolicy=v.findViewById(R.id.txt_pripolicy_go_online);
        terms=v.findViewById(R.id.txt_termsCondi_go_online);


        pripolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://anylearnblazegroup.blogspot.com/2019/01/privacy-policy-any-learn.html"));
                     startActivity(intent);



                }catch (Exception eiss)
                {



                }
            }
        });


        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://anylearnblazegroup.blogspot.com/2019/01/terms-and-conditions-any-learn.html"));
                    startActivity(intent);



                }catch (Exception eiss)
                {



                }



            }
        });


        return v;
    }
}
