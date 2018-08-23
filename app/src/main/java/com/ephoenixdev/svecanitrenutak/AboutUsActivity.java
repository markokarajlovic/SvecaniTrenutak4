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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AboutUsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;

    public Menu navMenus;
    public View headerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
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
            // Handle the camera action
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
            Toast.makeText(AboutUsActivity.this, "Izlogovani ste", Toast.LENGTH_SHORT).show();

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

        navMenus.findItem(R.id.nav_profile).setVisible(false);
        navMenus.findItem(R.id.nav_log_in).setVisible(true);
        navMenus.findItem(R.id.nav_new_account).setVisible(true);
        navMenus.findItem(R.id.nav_log_out).setVisible(false);

        TextView text = (TextView) headerLayout.findViewById(R.id.nav_header_name);
        text.setText(R.string.nav_header_title);

    }

    private void updateUI(FirebaseUser currentUser) {

        navMenus.findItem(R.id.nav_profile).setVisible(true);
        navMenus.findItem(R.id.nav_log_in).setVisible(false);
        navMenus.findItem(R.id.nav_new_account).setVisible(false);
        navMenus.findItem(R.id.nav_log_out).setVisible(true);

        TextView text = (TextView) headerLayout.findViewById(R.id.nav_header_name);
        text.setText(currentUser.getEmail().toString());

    }
}
