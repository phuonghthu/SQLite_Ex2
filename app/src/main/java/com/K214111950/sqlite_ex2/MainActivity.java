package com.K214111950.sqlite_ex2;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.K214111950.adapters.ProductAdapter;
import com.K214111950.model.Product;
import com.K214111950.sqlite_ex2.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    Database db; //gọi tới db

    ProductAdapter adapter;

    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createDb();
        loadData();

    }

    private void createDb() {
        db = new Database(MainActivity.this);
        db.createSampleData();
        Toast.makeText(this, "Insert sample data successful", Toast.LENGTH_SHORT).show();

    }

    private void loadData() {
        products = new ArrayList<>();
        Cursor cursor = db.queryData("SELECT * FROM " + Database.TBL_NAME);

        while (cursor.moveToNext()){
            products.add(new Product(cursor.getInt(0),cursor.getString(1),
                    cursor.getDouble(2)));
        }
        cursor.close();
        // Truy vấn dữ liệu từ db
        adapter = new ProductAdapter(MainActivity.this, R.layout.item_list, products);
        binding.lvProduct.setAdapter(adapter);
    }

    // ===================== MENU =========================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);

        // Chưa hiên qua theme chỉnh
    }
}