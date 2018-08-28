package com.ephoenixdev.svecanitrenutak;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ViewAdActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private StorageReference mStorageRef;

    private DatabaseReference databaseAd;
    FirebaseUser currentUser;
    public Menu navMenus;
    public View headerLayout;
    private TextView viewTitle, viewDiscription;
    private ImageView imageView;
    private Button btnInstagram, btnYoutube, btnFacebook, btnReport, btnCity, btnCall, btnWebsite, btnShare, btnDelete;
    private FirebaseAuth mAuth;
    private String userId, adId, title, city, discription, fbURL, instgramURL, youtubeURL, webSite, adress, category, imageOfAd, phone;
    private static final int REQUEST_CALL = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ad);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navMenus = navigationView.getMenu();
        headerLayout = navigationView.getHeaderView(0);

        mAuth = FirebaseAuth.getInstance();

        btnDelete = findViewById(R.id.buttonViewAdDelete);

        viewTitle = findViewById(R.id.textViewViewAdTitle);
        viewDiscription = findViewById(R.id.textViewViewAdDiscription);
        btnInstagram = findViewById(R.id.buttonViewAdInstagram);
        btnYoutube = findViewById(R.id.buttonViewAdYoutube);
        btnFacebook = findViewById(R.id.buttonViewAdFacebook);
        btnReport = findViewById(R.id.buttonViewAdReport);
        btnCity = findViewById(R.id.buttonViewAdCity);
        btnCall = findViewById(R.id.buttonViewAdCall);
        btnWebsite = findViewById(R.id.buttonViewAdWebsite);
        btnShare = findViewById(R.id.buttonViewAdShare);
        imageView = findViewById(R.id.imageViewViewAd);

        userId = getIntent().getStringExtra("userId");
        adId = getIntent().getStringExtra("adId");
        title = getIntent().getStringExtra("title");
        city = getIntent().getStringExtra("city");
        discription = getIntent().getStringExtra("discription");
        fbURL = getIntent().getStringExtra("fbURL");
        instgramURL = getIntent().getStringExtra("instgramURL");
        youtubeURL = getIntent().getStringExtra("youtubeURL");
        webSite = getIntent().getStringExtra("webSite");
        adress = getIntent().getStringExtra("adress");
        category = getIntent().getStringExtra("category");
        imageOfAd = getIntent().getStringExtra("imageOfAd");

        viewTitle.setText(title);
        viewDiscription.setText(discription);


        setImageOfAd (adId, imageOfAd);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO

                deleteAd(adId,userId);
            }
        });

        btnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });

        btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void deleteAd(String adId, String userId) {

        if(currentUser.getUid().toString().equals(userId)) {

            mStorageRef = FirebaseStorage.getInstance().getReference("AdImages/" + adId + "/" + imageOfAd);
            databaseAd = FirebaseDatabase.getInstance().getReference("Ad");

            databaseAd.child(adId).removeValue();
            mStorageRef.delete();

            finish();

        }
    }

    private void makePhoneCall() {
            String number = "0643609543";
            if (number.trim().length() > 0) {

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                } else {
                    String dial = "tel:" + number;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                }

            } else {
                Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
            }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setImageOfAd(String adId, String imageOfAd) {

        mStorageRef = FirebaseStorage.getInstance().getReference("AdImages/" + adId + "/" +imageOfAd);


        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ViewAdActivity.this, "Greska pri ucitavanju slike", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();

        if(currentUser.getUid().toString().equals(userId)){
            btnDelete.setVisibility(View.VISIBLE);

        }else btnDelete.setVisibility(View.INVISIBLE);

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
            Toast.makeText(ViewAdActivity.this, "Izlogovani ste", Toast.LENGTH_SHORT).show();
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



}