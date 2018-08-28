package com.ephoenixdev.svecanitrenutak;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ephoenixdev.svecanitrenutak.models.AdModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class NewAdActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnItemSelectedListener {

    private FirebaseAuth mAuth;

    public Menu navMenus;
    public View headerLayout;

    private StorageReference mStorageRef;
    private StorageTask mUploadTask;

    private DatabaseReference databaseAd;
    private EditText EditTitleOfAd;
    private Spinner spinnerCategories;
    private EditText EditDiscriptionOfAd;
    private EditText EditCityOfAd;
    private EditText EditAddressOfAd;
    private EditText EditYoutubeLinkOfAd;
    private EditText EditInstagramOfAd;
    private EditText EditFacebookLinkOfAd;
    private EditText EditWebsiteOfAd;
    private Button btnOK;
    private ImageView imageView;

    private Uri mImageUri;

    private String idAd;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ad);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        databaseAd = FirebaseDatabase.getInstance().getReference("Ad");

        btnOK = findViewById(R.id.buttonNewAdAdd);
        EditTitleOfAd = findViewById(R.id.editTextNewAdTitle);
        spinnerCategories = findViewById(R.id.spinnerNewAdCategory);
        EditDiscriptionOfAd = findViewById(R.id.editTextNewAdDiscription);
        EditCityOfAd = findViewById(R.id.editTextNewAdCity);
        EditAddressOfAd = findViewById(R.id.editTextNewAdAdress);
        EditYoutubeLinkOfAd = findViewById(R.id.editTextNewAdYouTube);
        EditInstagramOfAd = findViewById(R.id.editTextNewAdInstagram);
        EditFacebookLinkOfAd = findViewById(R.id.editTextNewAdFacebook);
        EditWebsiteOfAd = findViewById(R.id.editTextNewAdWebSite);
        imageView = findViewById(R.id.imageViewNewAd);

        mStorageRef = FirebaseStorage.getInstance().getReference("AdImages");

        ArrayAdapter<CharSequence> spinerAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);

        spinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(spinerAdapter);

        spinnerCategories.setOnItemSelectedListener(this);

        btnOK.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addAd();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(imageView);

        }
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(idAd+"/ad_image_1." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                }
                            }, 500);

                            Toast.makeText(NewAdActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewAdActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void addAd() {

        boolean postavljen = false;
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            Toast.makeText(NewAdActivity.this, "Ulogujte se prvo!", Toast.LENGTH_SHORT).show();
        }else {

            idAd = databaseAd.push().getKey();
            String idUser = currentUser.getUid().toString();
            String titleOfAd = EditTitleOfAd.getText().toString().trim();
            String imageOfAd;
            if(mImageUri != null){
                imageOfAd = "ad_image_1." + getFileExtension(mImageUri);
            }else
            {
                imageOfAd = "";
            }
            String categoryOfAd = spinnerCategories.getSelectedItem().toString();
            String discriptionOfAd = EditDiscriptionOfAd.getText().toString().trim();
            String cityOfAd = EditCityOfAd.getText().toString().trim();
            String addressOfAd = EditAddressOfAd.getText().toString().trim();
            String youtubeLinkOfAd = EditYoutubeLinkOfAd.getText().toString().trim();
            String instagramOfAd = EditInstagramOfAd.getText().toString().trim();
            String facebookOfAd = EditFacebookLinkOfAd.getText().toString().trim();
            String websiteOfAd = EditWebsiteOfAd.getText().toString().trim();

            AdModel am = new AdModel(
                    idAd,
                    idUser,
                    titleOfAd,
                    imageOfAd,
                    categoryOfAd,
                    discriptionOfAd,
                    cityOfAd,
                    addressOfAd,
                    youtubeLinkOfAd,
                    facebookOfAd,
                    instagramOfAd,
                    websiteOfAd);

            databaseAd.child(idAd).setValue(am);

            // upload img
            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(NewAdActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadFile();
            }

            postavljen = true;
        }

        if (postavljen){
            Toast.makeText(NewAdActivity.this, "Oglas postavljen!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(NewAdActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(NewAdActivity.this, "Neuspesno postavljanje oglasa!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Toast.makeText(this,"Morate se ulogovati pre postavke oglasa",Toast.LENGTH_LONG).show();
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
        if(id==android.R.id.home){
            finish();
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
            Toast.makeText(NewAdActivity.this, "Izlogovani ste", Toast.LENGTH_SHORT).show();
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
        final ImageView imageView = headerLayout.findViewById(R.id.nav_header_imageView);

        text.setText(currentUser.getEmail().toString());

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("ProfileImages/" + currentUser.getUid() + "/profileImage.jpg");

        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
