package com.K214111950.sqlite_ex2;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
//        loadData();

    }

    @Override
    protected void onResume() {
        super.onResume();
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

    // Tạo bên main đối số nhận vào là tên model, khai thác db để thực hiện cập nhật sản phẩm
    public void openDialogEdit(Product p){
        Dialog dialog = new Dialog(MainActivity.this);
        // nạp giao diện
        dialog.setContentView(R.layout.dialog_edit);

        EditText edtName = dialog.findViewById(R.id.edtName);
        // gán dữ liệu bằng dữ liệu sản phẩm truyền từ Adapter qua
        edtName.setText(p.getProductName());

        EditText edtPrice = dialog.findViewById(R.id.edtPrice);
        edtPrice.setText(String.valueOf(p.getProductPrice()));

        // Button Save
        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update data
                boolean updated = db.execSql("UPDATE " + Database.TBL_NAME + " SET " +
                        Database.COL_NAME + "='" + edtName.getText().toString() + "', "
                        + Database.COL_PRICE + "=" + edtPrice.getText().toString()
                        + " WHERE " + Database.COL_CODE + "=" + p.getProductCode());

                //UPDATE product SET ProductName='Test' , ProductPrice = 12000 WHERE ProductCode=2



                if(updated){
                    Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Fail!", Toast.LENGTH_SHORT).show();
                }
                loadData();
                dialog.dismiss();
            }
        });


        // Button Cancel
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public  void openDialogDelete(Product p){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Xác nhận Xóa!");
        builder.setIcon(android.R.drawable.ic_input_delete);
        builder.setMessage("Bạn có chắc xóa sp '" +p.getProductName() + "'?");

        // Ok
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                // Delete
                boolean deleted = db.execSql("DELETE FROM " + Database.TBL_NAME +
                        " WHERE " + Database.COL_CODE + "=" + p.getProductCode());

                if(deleted){
                    Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Fail!", Toast.LENGTH_SHORT).show();
                }
                // Cập nhật lại dữ liệu
                loadData();
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    // ===================== MENU =========================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
        // Chưa hiên qua theme chỉnh
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnAdd){

            // Nạp giao diện
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.dialog_add);

            EditText edtName, edtPrice;
            edtName = dialog.findViewById(R.id.edtName);
            edtPrice = dialog.findViewById(R.id.edtPrice);
            // Tham chiếu các nút, lấy dữ liệu lưu xuống db

            Button btnSave = dialog.findViewById(R.id.btnSave);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Insert data
                    boolean inserted = db.execSql("INSERT INTO " + Database.TBL_NAME + " VALUES(null, '"
                            + edtName.getText().toString() + "', "
                            + edtPrice.getText().toString() + ")");

                    // Show status
                    if(inserted) {
                        Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Fail!", Toast.LENGTH_SHORT).show();
                    }
                    loadData();
                    dialog.dismiss();
                }
            });

            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    dialog.dismiss();
                }
            });
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}