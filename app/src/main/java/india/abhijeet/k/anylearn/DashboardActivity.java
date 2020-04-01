package india.abhijeet.k.anylearn;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

  private DrawerLayout drawer;
    private FirebaseAuth firebaseAuth;
         public TextView usermail;
    int STORAGE_PERMISSION_CODE=1;
         String usrdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        firebaseAuth= FirebaseAuth.getInstance();
        usermail = findViewById(R.id.txt_user_welcome);

        FirebaseUser user = firebaseAuth.getCurrentUser();


        usrdata= user.getEmail();

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawer,toolbar,

                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if (savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                    new ProfileFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_myProfile);}




        if (firebaseAuth.getCurrentUser()==null)
        {
              finish();
            startActivity(new Intent(this,LoginPage.class));

        }

      //  FirebaseUser user = firebaseAuth.getCurrentUser();



    }



 /*   public void logout(View v)
    {

         firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this,LoginPage.class));

    }*/


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);

        }


       else {
            super.onBackPressed();
        }

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode==STORAGE_PERMISSION_CODE){

            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {

                 Toast.makeText(this,"Permission Granted!!  ",Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(this,"Permission Not Granted!! Please Allow the permission  ",Toast.LENGTH_SHORT).show();


            }
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId())
        {

         case R.id.nav_camera:
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                 new CaptureFragment()).commit();

//check for permission ,request for permission
             if (ContextCompat.checkSelfPermission(this,
                     Manifest.permission.CAMERA)==getPackageManager().PERMISSION_GRANTED){
                   Toast.makeText(this,"Permission Granted ",Toast.LENGTH_SHORT).show();

                 Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                 startActivity(intent);

             }
             else {
                 reqPermi();

             }


            break;

            case R.id.nav_lectures:
             getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                  new LectureFragment()).commit();

                break;

            case R.id.nav_policy:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                      new PrivacyPolicyFragment()).commit();
                break;

            case R.id.nav_about:
              getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                  new AboutThisFragment()).commit();
            break;


            case R.id.nav_myProfile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                       new ProfileFragment()).commit();
                break;



            case R.id.nav_logout:

                firebaseAuth.signOut();
                startActivity(new Intent(this,LoginPage.class));
                finish();
                break;


            case R.id.nav_upload_lecture:

              getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                       new Upload_LectureFragment()).commit();


                break;



        }


         drawer.closeDrawer(GravityCompat.START);
        return true;


    }



    //permission request method
    public void reqPermi()
    {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this , Manifest.permission.CAMERA)){

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed!!")
                    .setMessage("Permission require for recording the video!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ActivityCompat.requestPermissions(DashboardActivity.this,new String[]{Manifest.permission.CAMERA},STORAGE_PERMISSION_CODE);

                        }
                    }).create().show();
        }
        else{

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},STORAGE_PERMISSION_CODE);

        }

    }



}
