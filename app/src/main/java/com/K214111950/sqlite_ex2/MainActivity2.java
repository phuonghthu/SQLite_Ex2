package com.K214111950.sqlite_ex2;

import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.K214111950.adapters.ProductRvAdapter;
import com.K214111950.model.Product;
import com.K214111950.sqlite_ex2.databinding.ActivityMain2Binding;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    ActivityMain2Binding binding;
    ProductRvAdapter adapter;
    ArrayList<Product> products;

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new Database(MainActivity2.this);
        db.createSampleData();

        loadData();
    }

    private void loadData() {
        // Set hướng
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rcvProduct.setLayoutManager(layoutManager);


        // Load data
        products = new ArrayList<>();
        Cursor cursor = db.queryData("SELECT * FROM " + Database.TBL_NAME);
        while (cursor.moveToNext()){
            products.add(new Product(cursor.getInt(0),
                                    cursor.getString(1),
                                    cursor.getDouble(2)));
        }
        cursor.close();

        // setAdapter
        adapter = new ProductRvAdapter(MainActivity2.this, products);
        binding.rcvProduct.setAdapter(adapter);
    }
}