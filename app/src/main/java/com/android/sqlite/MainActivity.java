package com.android.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Database database;
    ListView lvCongViec;
    ArrayList<CongViec> arrayCongViec;
    CongViecAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCongViec = (ListView) findViewById(R.id.LvCongViec);
        arrayCongViec = new ArrayList<>();
        adapter = new CongViecAdapter(this,R.layout.dong_cong_viec,arrayCongViec);
        lvCongViec.setAdapter(adapter);

        //tạo database ghi chú
        database = new Database(this,"ghichu.sqlite",null,1);

        //tạo bảng CongViec
        //tạo bảng nếu ko tồn tại
        //AUTOINCREMENT TỰ ĐỘNG tăng dần
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(200))");

        //THÊM DỮ liệu vào
//        database.QueryData("INSERT INTO CongViec VALUES(null,'Làm bài tập Android')");
//        database.QueryData("INSERT INTO CongViec VALUES(null,'Viết ứng dụng ghi chú')");

        //Lây dữ liệu
//        Cursor dataCongViec = database.getData("SELECT * FROM CongViec");
//        while (dataCongViec.moveToNext()){
//            int id = dataCongViec.getInt(0);
//            String ten = dataCongViec.getString(1);
//            arrayCongViec.add(new CongViec(id,ten));
//        }
//        adapter.notifyDataSetChanged();

        getDataCongViec();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.Add){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }
    private void DialogThem(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_cong_viec);

        EditText edtName = dialog.findViewById(R.id.editTenCV);
        Button btnAdd = dialog.findViewById(R.id.btnAdd);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenCV =edtName.getText().toString();
                if(tenCV.isEmpty()){
                    Toast.makeText(MainActivity.this,"Vui lòng nhập tên công việc",Toast.LENGTH_SHORT).show();
                }
                else{
                    database.QueryData("INSERT INTO CongViec VALUES(null,'"+tenCV+"')");
                    Toast.makeText(MainActivity.this,"Đã thêm",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDataCongViec();

                }

            }
        });
        dialog.show();
    }
    private  void getDataCongViec(){
        Cursor dataCongViec = database.getData("SELECT * FROM CongViec");
        //xoá mảng trước khi add để cập nhật lại dữ liệu mới thôi tránh lặp lại cái cũ
        arrayCongViec.clear();
        while (dataCongViec.moveToNext()){
            int id = dataCongViec.getInt(0);
            String ten = dataCongViec.getString(1);
            arrayCongViec.add(new CongViec(id,ten));
        }
        adapter.notifyDataSetChanged();
    }
    public void DialogSuaCongViec(String ten, int id){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua_cong_viec);
        dialog.show();
        EditText edtTenCV = dialog.findViewById(R.id.editTenCVEdit);
        Button btnXacNhan = dialog.findViewById(R.id.btnXacNhan);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        edtTenCV.setText(ten);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMoi = edtTenCV.getText().toString().trim();
                database.QueryData("UPDATE CongViec SET TenCV='"+tenMoi+"' WHERE Id='"+id+"'");
                Toast.makeText(MainActivity.this,"Đã cập nhật",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getDataCongViec();
            }
        });
    }
    public void DialogXoaCongViec(String ten,int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xoá công việc này "+ten+" không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               database.QueryData("DELETE FROM CongViec WHERE Id='"+id+"'");
               Toast.makeText(MainActivity.this,"Đã xoá công việc"+ten+ " thành công",Toast.LENGTH_SHORT).show();
               getDataCongViec();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa.show();

    }
}