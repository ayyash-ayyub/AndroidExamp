package seamolec.org.app.udjseamolec;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Krisnasw on 9/10/2016.
 */
public class MapelController extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private ArrayList<JSONResponse> data;
    private SQLiteHandler db;
    private SessionManager session;
    private DataAdapter adapter;

    Toolbar toolbar;
    public String tamvan;

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapel_new);
        navigationView = (NavigationView) findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar = (Toolbar) findViewById(R.id.tbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("List Tugas");
        initViews();
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        View header = navigationView.getHeaderView(0);
        TextView txt_jeneng = (TextView)header.findViewById(R.id.jeneng);

    }
    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }
    private void loadJSON(){
        SharedPreferences sps = getSharedPreferences("", MODE_PRIVATE);
        String ntapz = sps.getString("IPnya", "");
        tamvan = ntapz;
        System.out.println("dasdad"+tamvan);
        //IP Dinamis -Muhammad Muslim Rifai-
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+tamvan+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("ip ne : "+tamvan);
        TugasAPI request = retrofit.create(TugasAPI.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.ngHasil()));
                adapter = new DataAdapter(data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_menu1){
            new SweetAlertDialog(MapelController.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Maintenance!")
                    .setContentText("Under Construction")
                    .show();
//            Intent intent = new Intent(Mapel.this,Profile.class);
//            startActivity(intent);
//            finish();
        }
        else if (id == R.id.nav_menu2){
            new SweetAlertDialog(MapelController.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Maintenance!")
                    .setContentText("Under Construction")
                    .show();
//            Intent intent = new Intent(Mapel.this,Score.class);
//            startActivity(intent);
//            finish();
        }
        else if(id == R.id.nav_log_out){
            logoutUser();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.openDrawer(Gravity.LEFT);
        }
        return true;
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MapelController.this, Login.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
