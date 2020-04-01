package india.abhijeet.k.anylearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {




    private EditText email_login;
    private EditText passwd_login;
    private Button register_login;
    ProgressDialog prb_login;
    private FirebaseAuth firebaseAuth;
    String eid_login,pass_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        email_login=findViewById(R.id.txt_email_login);
        passwd_login=findViewById(R.id.txt_Passwd_login);
        register_login=findViewById(R.id.btn_login);
        prb_login = new ProgressDialog(this);
        firebaseAuth= FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!=null)
        {

            finish();
            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));

            // get into App

        }


    }

    public void login_here(View view) {

        eid_login = email_login.getText().toString().trim();
        pass_login = passwd_login.getText().toString().trim();

        if (eid_login == null || pass_login == null) {

            Toast.makeText(this, "Please Enter the correct email and password!", Toast.LENGTH_SHORT).show();

        }

        else if (eid_login.indexOf('@') != -1) {

            prb_login.setMessage("Logging in...");
            prb_login.show();

            firebaseAuth.signInWithEmailAndPassword(eid_login,pass_login)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            prb_login.dismiss();
                            if (task.isSuccessful())
                            {
                                finish();
                                startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                                // Get into the app
                            }
                            else {

                                Toast.makeText(LoginPage.this, "An error occurred! Please try again", Toast.LENGTH_SHORT).show();



                            }

                        }
                    }); }

        else {

            Toast.makeText(this, "Please Enter the correct email and password!", Toast.LENGTH_SHORT).show();
        }

            Toast.makeText(this,"Sign up here",Toast.LENGTH_SHORT).show();

    }



    public void GoSign(View v)
    {

        finish();
        Intent go =new Intent(LoginPage.this,MainActivity.class);
        startActivity(go);
    }


    @Override
    public void onBackPressed() {


             finish();
        Intent go =new Intent(LoginPage.this,MainActivity.class);
        startActivity(go);
        super.onBackPressed();


    }
}
