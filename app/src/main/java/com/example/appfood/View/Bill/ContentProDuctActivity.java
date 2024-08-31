package com.example.appfood.View.Bill;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appfood.Adapter.GioHangAdapter;
import com.squareup.picasso.Picasso;
import com.example.appfood.Model.SanPhamModels;
import com.example.appfood.Presenter.GioHangPreSenter;
import com.example.appfood.Presenter.GioHangView;
import com.example.appfood.R;
import com.example.appfood.View.HomeActivity;

import java.text.NumberFormat;

public class

ContentProDuctActivity extends AppCompatActivity implements GioHangView {
    private Intent intent;
    private SanPhamModels sanPhamModels;
    private TextView txttensp, txtgiatien, txtmota, txtnsx, txtbaohanh, tvQuantity;
    private Toolbar toolbar;
    private ImageView hinhanh, subQuantity, sumQuantity;
    private Button btndathang;
    private GioHangPreSenter gioHangPreSenter;

    public int intQuantity = 0;

    public GioHangAdapter gioHangAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_product);
        InitWidget();
        Init();
    }

    private void Init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chi tiết sản phẩm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intent=getIntent();
        sanPhamModels = (SanPhamModels) intent.getSerializableExtra("SP");
        txtnsx.setText("Hạn sử dụng: "+sanPhamModels.getHansudung());
        txtmota.setText("Mô tả: "+sanPhamModels.getMota());
        txtbaohanh.setText("Trọng lượng: "+sanPhamModels.getTrongluong());
        txttensp.setText("Tên sản phẩm: "+sanPhamModels.getTensp());
        txtgiatien.setText("Giá tiền: "+NumberFormat.getNumberInstance().format(sanPhamModels.getGiatien()));
        tvQuantity = (TextView) findViewById(R.id.tvQuantityCart);
        subQuantity = (ImageView) findViewById(R.id.subQuantity);
        sumQuantity = (ImageView) findViewById(R.id.sumQuantity);
        Picasso.get().load(sanPhamModels.getHinhanh()).into(hinhanh);
        gioHangPreSenter = new GioHangPreSenter(this);
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gioHangPreSenter.AddCart(sanPhamModels.getId());
            }
        });

    }

    private void InitWidget() {
        toolbar = findViewById(R.id.toolbar);
        txtbaohanh = findViewById(R.id.txtbaohanh);
        txtgiatien = findViewById(R.id.txtgiatien);
        txtmota=findViewById(R.id.txtmota);
        txtnsx=findViewById(R.id.txtthuonghieu);
        txtbaohanh=findViewById(R.id.txtbaohanh);
        txttensp=findViewById(R.id.txttensp);
        hinhanh=findViewById(R.id.image_product);
        btndathang=findViewById(R.id.btndathang);
        tvQuantity = findViewById(R.id.tvQuantityCart);
        sumQuantity = findViewById(R.id.sumQuantity);
        subQuantity = findViewById(R.id.subQuantity);

    }

    @Override
    public void OnSucess() {
        Toast.makeText(this, "Thêm sản phẩm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent( ContentProDuctActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void OnFail() {
        Toast.makeText(this, "Thất Bại ! Lỗi hệ thống bảo trì", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDataSanPham(String id, String idsp, String tensp, Long giatien, String hinhanh, String loaisp, Long soluong, String hansudung, Long type, String trongluong) {

    }
    public void SumQuantityOnclick(View view) {
        long tongtien = sanPhamModels.getGiatien() * intQuantity + sanPhamModels.getGiatien();
        String quantity = tvQuantity.getText().toString();
        intQuantity = Integer.parseInt(quantity);
        intQuantity++;
        tvQuantity.setText(String.valueOf(intQuantity));
        SharedPreferences sharedPreferences = getSharedPreferences("AppFood", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("intQuantity", intQuantity);
        editor.putLong("tongtien", tongtien);
        editor.apply();
    }

    public void SubQuantityOnclick(View view) {
        String quantity = tvQuantity.getText().toString();
        int intQuantity = Integer.parseInt(quantity);
        if(intQuantity != 1){
            intQuantity--;
            tvQuantity.setText(String.valueOf(intQuantity));
        }
    }
}
