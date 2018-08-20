package com.ephoenixdev.svecanitrenutak;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ephoenixdev.svecanitrenutak.models.AdModel;

public class NewAdActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnItemSelectedListener {

    private FirebaseAuth mAuth;

    public Menu navMenus;
    public View headerLayout;

    DatabaseReference databaseAd;
    EditText EditTitleOfAd;
    Spinner spinnerCategories;
    EditText EditDiscriptionOfAd;
    EditText EditCityOfAd;
    EditText EditAddressOfAd;
    EditText EditYoutubeLinkOfAd;
    EditText EditInstagramOfAd;
    EditText EditFacebookLinkOfAd;
    EditText EditWebsiteOfAd;
    Button btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navMenus = navigationView.getMenu();
        headerLayout = navigationView.getHeaderView(0);

        mAuth = FirebaseAuth.getInstance();

        databaseAd = FirebaseDatabase.getInstance().getReference("Ad");

        btnOK = (Button) findViewById(R.id.buttonNewAdAdd);
        EditTitleOfAd = (EditText) findViewById(R.id.editTextNewAdTitle);
        spinnerCategories = (Spinner) findViewById(R.id.spinnerNewAdCategory);
        EditDiscriptionOfAd = (EditText) findViewById(R.id.editTextNewAdDiscription);
        EditCityOfAd = (EditText) findViewById(R.id.editTextNewAdCity);
        EditAddressOfAd = (EditText) findViewById(R.id.editTextNewAdAdress);
        EditYoutubeLinkOfAd = (EditText) findViewById(R.id.editTextNewAdYouTube);
        EditInstagramOfAd = (EditText) findViewById(R.id.editTextNewAdInstagram);
        EditFacebookLinkOfAd = (EditText) findViewById(R.id.editTextNewAdFacebook);
        EditWebsiteOfAd = (EditText) findViewById(R.id.editTextNewAdWebSite);

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
    }

    private void addAd() {

        boolean postavljen = false;
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            Toast.makeText(NewAdActivity.this, "Ulogujte se prvo!", Toast.LENGTH_SHORT).show();
        }else {

            String idAd = databaseAd.push().getKey();
            String idUser = currentUser.getUid().toString();
            String titleOfAd = EditTitleOfAd.getText().toString().trim();
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
                    categoryOfAd,
                    discriptionOfAd,
                    cityOfAd,
                    addressOfAd,
                    youtubeLinkOfAd,
                    facebookOfAd,
                    instagramOfAd,
                    websiteOfAd);

            databaseAd.child(idAd).setValue(am);
            postavljen = true;
        }

        if (postavljen){
            Toast.makeText(NewAdActivity.this, "Oglas postavljen!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(NewAdActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(NewAdActivity.this, "Neuspesno!", Toast.LENGTH_SHORT).show();
        }
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void resetUI() {

        navMenus.findItem(R.id.nav_log_in).setVisible(true);
        navMenus.findItem(R.id.nav_new_account).setVisible(true);
        navMenus.findItem(R.id.nav_log_out).setVisible(false);

        TextView text = (TextView) headerLayout.findViewById(R.id.nav_header_name);
        text.setText(R.string.nav_header_title);

    }

    private void updateUI(FirebaseUser currentUser) {

        navMenus.findItem(R.id.nav_log_in).setVisible(false);
        navMenus.findItem(R.id.nav_new_account).setVisible(false);
        navMenus.findItem(R.id.nav_log_out).setVisible(true);

        TextView text = (TextView) headerLayout.findViewById(R.id.nav_header_name);
        text.setText(currentUser.getEmail().toString());

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
