package com.example.hamidreza.modeldata.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hamidreza.modeldata.Adapter.DataItemAdapter;
import com.example.hamidreza.modeldata.Adapter.LoginDataBaseAdapter;
import com.example.hamidreza.modeldata.Model.DataItem;
import com.example.hamidreza.modeldata.R;
import com.example.hamidreza.modeldata.Sample.JSONHelper;
import com.example.hamidreza.modeldata.Sample.SampleDataProvider;
import com.example.hamidreza.modeldata.database.DataSource;

import java.util.List;


public class MainActivity extends ActionBarActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int SIGNIN_REQUEST = 1001;
    public static final String MY_GLOBAL_PREFERENCES = "my_global_preferences";
    //    private TextView tvOut ;
    String email, name;
    LoginDataBaseAdapter loginDataBaseAdapter;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    ActionBarDrawerToggle toggle;
    DataSource mDataSource;
    SQLiteOpenHelper helper;
    //    String[] mCategories;
    SQLiteDatabase sqLiteDatabase;
    List<DataItem> listFromDB;
    DataItemAdapter mItemAdapter;
    String[] mCategories;
    RecyclerView mRecyclerView;
    private String TAG = "Import Json";
    private List<DataItem> dataItemList = SampleDataProvider.dataItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                startActivity(intent);
            }
        });
        Toast.makeText(this, "Database acquired!", Toast.LENGTH_SHORT).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.opned,
                R.string.close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationView = (NavigationView) findViewById(R.id.nv);
        mNavigationView.setNavigationItemSelectedListener(this);

        mDataSource = new DataSource(this);
        mDataSource.open();
        mDataSource.seedDatabase(dataItemList);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycleListView);

        displayDataItems(null);
//        list.setAdapter(adapter);
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

    private void displayDataItems(String category) {
        listFromDB = mDataSource.getALLCOLUMN(category);
        mItemAdapter = new DataItemAdapter(this, listFromDB);
        mRecyclerView.setAdapter(mItemAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
    }

    //
    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_signin:
                Intent intent = new Intent(this, SigninActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_export:
                boolean result = JSONHelper.exportToJSON(this, dataItemList);
                if (result) {
                    Toast.makeText(this, "داده های صادر شده..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "صادر نشد", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.action_import:
                List<DataItem> list = JSONHelper.importFromJSON(this);
                if (list != null) {
                    for (DataItem dataItem : dataItemList) {
                        Log.v(TAG, "Import Json" + dataItem.getItemName());
                    }
                }
                return true;
//            case R.id.action_all_items:
//                // display all items
//                displayDataItems(null);
//                return true;
//            case R.id.action_choose_category:
//                //open the drawer
//                mDrawerLayout.openDrawer(mDrawerList);
//                return true;

        }

//        if(TextUtils.isEmpty(RegisterActivity.EMAIL_KEY)){

//        }


        return super.onOptionsItemSelected(item);
    }

    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.salad) {
            Toast.makeText(this, "سالاد", Toast.LENGTH_SHORT).show();
            displayDataItems("Salads");
        } else if (id == R.id.qaza) {
            Toast.makeText(this, "غذاها", Toast.LENGTH_SHORT).show();
            displayDataItems("Entrees");
        } else if (id == R.id.pish) {
            Toast.makeText(this, "پیش غذا", Toast.LENGTH_SHORT).show();
            displayDataItems("Starters");
        } else if (id == R.id.noshidani) {
            Toast.makeText(this, "نوشیدنی ها", Toast.LENGTH_SHORT).show();
            displayDataItems("Drinks");
        } else if (id == R.id.dser) {
            Toast.makeText(this, "دسرها", Toast.LENGTH_SHORT).show();
            displayDataItems("Desserts");
        } else if (id == R.id.kolemenu) {
            Toast.makeText(this, "همه منو", Toast.LENGTH_SHORT).show();
            displayDataItems(null);
        } else if (id == R.id.about) {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


