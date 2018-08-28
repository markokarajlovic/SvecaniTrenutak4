package com.ephoenixdev.svecanitrenutak;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    StorageReference mStorageRef;
    StorageTask mUploadTask;

    public Menu navMenus;
    public View headerLayout;
    private ImageView imageView;
    private Button btnOk, btnOKPic, btnAds;
    private TextInputLayout textInputLayout;
    private EditText editTextPassword;



    private static final int PICK_IMAGE_REQUEST = 1;
    private boolean pictureChanged = false;
    FirebaseUser currentUser;
    private Uri mImageUri;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navMenus = navigationView.getMenu();
        headerLayout = navigationView.getHeaderView(0);

        mAuth = FirebaseAuth.getInstance();

        btnOk = findViewById(R.id.buttonProfileOK);
        btnOKPic = findViewById(R.id.buttonProfileOKPicture);
        btnAds = findViewById(R.id.buttonProfileUserAds);
        textInputLayout = findViewById(R.id.inputLayoutProfilePassword);
        editTextPassword = findViewById(R.id.editTextProfilePassword);
        imageView = findViewById(R.id.imageViewProfileImg);

        btnAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, UserAdsActivity.class);
                userId = currentUser.getUid().toString();
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });



        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword(editTextPassword.getText().toString().trim());
            }
        });

        btnOKPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePicture(pictureChanged);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();

            }
        });


    }

    private void changePassword(final String newPassword) {

        if (!validateForm()) {
            return;
        }
        currentUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ProfileActivity.this,"Uspesna promena", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateForm() {

        boolean valid = true;

        String password = editTextPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Obavezno polje.");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }

        return valid;
    }

    private void changePicture(boolean pictureChanged) {

        if(pictureChanged == true){
            if (mImageUri != null) {
                StorageReference fileReference = mStorageRef.child(currentUser.getUid()+"/profileImage." + getFileExtension(mImageUri));

                    mUploadTask = fileReference.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(ProfileActivity.this, "Slika promenjena", Toast.LENGTH_LONG).show();
                            currentUser = mAuth.getCurrentUser();

                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } else {
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            }
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(imageView);

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();

        if(currentUser == null){

        }else {
            updateUI(currentUser);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_new_ad) {
            Intent intent = new Intent(this,NewAdActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_categories) {
            Intent intent = new Intent(this, CategoriesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_log_in) {
            Intent intent = new Intent(this,LogInActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_new_account) {
            Intent intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_log_out) {
            mAuth.signOut();
            resetUI();
            Toast.makeText(ProfileActivity.this, "Izlogovani ste", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));

        } else if (id == R.id.nav_about_us) {
            Intent intent = new Intent(this,AboutUsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String text = getResources().getString(R.string.share_text);
            intent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            intent.putExtra(Intent.EXTRA_TEXT, text);

            startActivity(Intent.createChooser(intent, "Izaberite"));

        } else if (id == R.id.nav_follow_us) {
            Intent intent = new Intent(this,FollowActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(this,ContactActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void resetUI() {

        navMenus.findItem(R.id.nav_profile).setVisible(false);
        navMenus.findItem(R.id.nav_log_in).setVisible(true);
        navMenus.findItem(R.id.nav_new_account).setVisible(true);
        navMenus.findItem(R.id.nav_log_out).setVisible(false);

        TextView text = headerLayout.findViewById(R.id.nav_header_name);
        ImageView imageView = headerLayout.findViewById(R.id.nav_header_imageView);

        text.setText(R.string.nav_header_title);
        imageView.setImageResource(R.mipmap.ic_launcher_round);

    }

    private void updateUI(FirebaseUser currentUser) {

        navMenus.findItem(R.id.nav_profile).setVisible(true);
        navMenus.findItem(R.id.nav_log_in).setVisible(false);
        navMenus.findItem(R.id.nav_new_account).setVisible(false);
        navMenus.findItem(R.id.nav_log_out).setVisible(true);

        TextView text = headerLayout.findViewById(R.id.nav_header_name);
        final ImageView imageViewHeader = headerLayout.findViewById(R.id.nav_header_imageView);

        text.setText(currentUser.getEmail().toString());

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("ProfileImages/" + currentUser.getUid() + "/profileImage.jpg");

        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(imageViewHeader);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}

