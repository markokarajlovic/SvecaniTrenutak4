package com.ephoenixdev.svecanitrenutak;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ephoenixdev.svecanitrenutak.lists.ListOfAdsAdapter;
import com.ephoenixdev.svecanitrenutak.lists.ListOfAdsProfileAdapter;
import com.ephoenixdev.svecanitrenutak.models.AdModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListOfAdsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;

    private RecyclerView recyclerView;
    private ListOfAdsAdapter listOfAdsAdapter;
    private List<AdModel> adModelList;

    public Menu navMenus;
    public View headerLayout;
    private ProgressBar progressBar;
    String categoryName;

    long idCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_ads);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        progressBar = findViewById(R.id.progressBarListOfAds);

        navMenus = navigationView.getMenu();
        headerLayout = navigationView.getHeaderView(0);

        mAuth = FirebaseAuth.getInstance();

        createList();


    }

    @Override
    protected void onResume() {
        super.onResume();
        createList();
    }

    private void createList() {

        recyclerView = findViewById(R.id.recyclerViewListOfAds);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adModelList = new ArrayList<>();
        listOfAdsAdapter = new ListOfAdsAdapter(this,adModelList);
        recyclerView.setAdapter(listOfAdsAdapter);


        // preuzimamo id kategorije
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            idCategory = bundle.getInt("idCategory");
        }

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adModelList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        AdModel artist = snapshot.getValue(AdModel.class);
                        adModelList.add(artist);
                    }
                    listOfAdsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        if (idCategory == 1) {

            // Muzika - Bendovi/Orkestri
            categoryName = getResources().getString(R.string.K_1);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 2) {

            // Muzika - DJ-evi
            categoryName = getResources().getString(R.string.K_2);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 3) {

            // Muzika - Pevači
            categoryName = getResources().getString(R.string.K_3);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 4) {

            // Muzika - Tamburaši/Akustičari
            categoryName = getResources().getString(R.string.K_4);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 5) {

            // Muzika - Trubači
            categoryName = getResources().getString(R.string.K_5);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 6) {

            // Svečane sale/Restorani
            categoryName = getResources().getString(R.string.K_6);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 7) {

            // Venčanice/Svečane haljine
            categoryName = getResources().getString(R.string.K_7);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 8) {

            // Dekoracija i specijalni efekti
            categoryName = getResources().getString(R.string.K_8);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 9) {

            // Torte/Slatkiši
            categoryName = getResources().getString(R.string.K_9);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 10) {

            // Foto/Video
            categoryName = getResources().getString(R.string.K_10);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 11) {

            // Šatori/Paviljoni
            categoryName = getResources().getString(R.string.K_11);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 12) {

            // Pozivnice/Štampa
            categoryName = getResources().getString(R.string.K_12);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 13) {

            // Lepota - Frizura
            categoryName = getResources().getString(R.string.K_13);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 14) {

            // Lepota - Makeup
            categoryName = getResources().getString(R.string.K_14);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 15) {

            // Lepota - Nokti
            categoryName = getResources().getString(R.string.K_15);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 16) {

            // Lepota - Studio lepote
            categoryName = getResources().getString(R.string.K_16);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 17) {

            // Prevoz
            categoryName = getResources().getString(R.string.K_17);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 18) {

            // Osoblje
            categoryName = getResources().getString(R.string.K_18);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 19) {

            // Prenoćište za goste
            categoryName = getResources().getString(R.string.K_19);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 20) {

            // Burme/Nakit
            categoryName = getResources().getString(R.string.K_20);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 21) {

            // Aksesoar
            categoryName = getResources().getString(R.string.K_21);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else if (idCategory == 22) {

            // Ketering
            categoryName = getResources().getString(R.string.K_22);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        }else if (idCategory == 23) {

            // Poklončići
            categoryName = getResources().getString(R.string.K_23);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        }else if (idCategory == 24) {

            // Pića
            categoryName = getResources().getString(R.string.K_24);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        }else if (idCategory == 25) {

            // Igraonice
            categoryName = getResources().getString(R.string.K_25);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        }else if (idCategory == 26) {

            // Muška i dečija odela
            categoryName = getResources().getString(R.string.K_26);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        }else if (idCategory == 27) {

            // Venčanice/Svečane haljine
            categoryName = getResources().getString(R.string.K_27);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        }else if (idCategory == 28) {

            // Obuća
            categoryName = getResources().getString(R.string.K_28);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        }else if (idCategory == 29) {

            // Animacija
            categoryName = getResources().getString(R.string.K_29);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        }else if (idCategory == 30) {

            // Organizatori proslave
            categoryName = getResources().getString(R.string.K_30);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        }else if (idCategory == 31) {

            // Ostalo
            categoryName = getResources().getString(R.string.K_31);
            getSupportActionBar().setTitle(categoryName);
            Query query = FirebaseDatabase.getInstance().getReference("Ad").orderByChild("category").equalTo(categoryName);
            query.addListenerForSingleValueEvent(valueEventListener);

        }

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){

        }else {
            updateUI(currentUser);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
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
            Toast.makeText(ListOfAdsActivity.this, "Izlogovani ste", Toast.LENGTH_SHORT).show();
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

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
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

    private void hideProgressDialog() {

        progressBar.setVisibility(View.GONE);
    }

    private void showProgressDialog() {

        progressBar.setVisibility(View.VISIBLE);
    }
}
