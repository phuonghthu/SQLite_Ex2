package com.K214111950.sqlite_ex2;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.K214111950.adapters.ProductRvAdapter;
import com.K214111950.model.Product;
import com.K214111950.sqlite_ex2.databinding.ActivityMain2Binding;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    ActivityMain2Binding binding;
    ProductRvAdapter adapter;
    ArrayList<Product> products;

    Database db;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper
            .SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        // Khi item di chuyển (kéo lên xuống code ở đây)
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        // Kéo qua kéo lại
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.LEFT) {
                //Toast.makeText(MainActivity2.this, "To Left", Toast.LENGTH_SHORT).show();
                //Edit
            } else if (direction == ItemTouchHelper.RIGHT) {
                //Toast.makeText(MainActivity2.this, "To Right", Toast.LENGTH_SHORT).show();
                //Delete trong UI chưa xóa db
                //products.remove(viewHolder.getAdapterPosition());
                //adapter.notifyDataSetChanged();
                //Xóa db
                Product selectedProduct = products.get(viewHolder.getAdapterPosition());
                boolean deleted = db.execSql("DELETE FROM " + Database.TBL_NAME + " WHERE "
                        + Database.COL_CODE + "=" + selectedProduct.getProductCode());
                if (deleted) {
                    Toast.makeText(MainActivity2.this, "Success!", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(MainActivity2.this, "Fail!", Toast.LENGTH_SHORT).show();
                }

            }

        }
    };


    // load Swipe Layout
//    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull
//    RecyclerView.ViewHolder viewHolder.float dX, float fY, int actionState
//            , boolean isCurrentlyActive) {
//        // Load Swipe Layout
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//
//    }
//
//}
    ItemTouchHelper itemTouchHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new Database(MainActivity2.this);
        db.createSampleData();

        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.rcvProduct);

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