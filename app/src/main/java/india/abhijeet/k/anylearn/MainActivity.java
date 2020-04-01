package india.abhijeet.k.anylearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private EditText email;
    private EditText passwd;
    private Button register;
    ProgressDialog prb;
private FirebaseAuth firebaseAuth;
    String eid,pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.txt_email);
        passwd = findViewById(R.id.txt_Passwd);
        register = findViewById(R.id.btn_register);
        prb = new ProgressDialog(this);

       firebaseAuth= FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!=null)
        {

            finish();
            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));

            // get into App

        }

    }
        public void register (View v)
        {

            eid = email.getText().toString().trim();
            pass = passwd.getText().toString().trim();

            if (eid == null || pass == null) {

                Toast.makeText(this, "Please Enter the correct email and password!", Toast.LENGTH_SHORT).show();

            } else if (eid.indexOf('@') != -1) {

                prb.setMessage("Registering User...");
                prb.show();


                firebaseAuth.createUserWithEmailAndPassword(eid,pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (task.isSuccessful())
                                {
                                        finish();
                                        startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                                    Toast.makeText(MainActivity.this,"Registered Successfully!",Toast.LENGTH_SHORT).show();
                                    prb.dismiss();
                                }

                                else
                                {
                                    Toast.makeText(MainActivity.this," Error!!Something went wrong,PLease Try again...",Toast.LENGTH_SHORT).show();


                                }

                                }
                        });



            }
            else {
                Toast.makeText(this, "Please Enter the correct email and password!", Toast.LENGTH_SHORT).show();


            }

        }


        public void GoLogin(View v)
        {

            finish();
            Intent go =new Intent(MainActivity.this,LoginPage.class);
            startActivity(go);
        }



    @Override
    public void onBackPressed() {


        finish();
       // super.onBackPressed();


    }




}
