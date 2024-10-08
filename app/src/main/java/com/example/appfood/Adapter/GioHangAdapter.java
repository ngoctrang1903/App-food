package com.example.appfood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.example.appfood.Model.SanPhamModels;
import com.example.appfood.Presenter.SetOnItemClick;
import com.example.appfood.R;

import java.text.NumberFormat;
import java.util.ArrayList;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHodler> {
    private Context context;
    private ArrayList<SanPhamModels> arrayList;
    private  int type = 0;

    public GioHangAdapter(Context context, ArrayList<SanPhamModels> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    public GioHangAdapter(Context context, ArrayList<SanPhamModels> arrayList, int type) {
        this.context = context;
        this.arrayList = arrayList;
        this.type= type;
    }

    @NonNull
    @Override
    public GioHangAdapter.ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(type==0){
             view = LayoutInflater.from(context).inflate(R.layout.dong_sanpham,parent,false);
        }else if(type ==2){
            view = LayoutInflater.from(context).inflate(R.layout.dong_sanpham_noibat,parent,false);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.dong_giohang,parent,false);
        }
        return new ViewHodler(view);
    }
    @Override
    public void onBindViewHolder(@NonNull GioHangAdapter.ViewHodler holder, int position) {
        SanPhamModels sanPhamModels = arrayList.get(position);
        holder.txttensp.setText(sanPhamModels.getTensp());
        holder.txtgiasp.setText(NumberFormat.getInstance().format(sanPhamModels.getGiatien())+" Đ");
        Picasso.get().load(sanPhamModels.getHinhanh()).into(holder.hinhanh);
        holder.SetOnItem(new SetOnItemClick() {
            @Override
            //chi tiet san phẩm
            public void SetItemClick(View view, int pos) {
            }
        });
        if(type==1){
            // Truy xuất intQuantity từ SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences("AppFood", Context.MODE_PRIVATE);
            int intQuantity = sharedPreferences.getInt("intQuantity", 0);

            // Tính toán tổng giá trị
            long totalGiatien = sanPhamModels.getGiatien() * intQuantity;

            holder.txtbaohanh.setText(sanPhamModels.getTrongluong());
            holder.txtsoluong.setText("x" + intQuantity); // Sử dụng số lượng
            holder.txtgiasp.setText(NumberFormat.getInstance().format(totalGiatien) + " Đ"); // Sử dụng tổng giá trị
            Log.d(String.valueOf(intQuantity), "aaa: " + intQuantity);
        }
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class ViewHodler extends RecyclerView.ViewHolder implements  View.OnClickListener {
        TextView txttensp,txtgiasp,txtbaohanh,txtsoluong;
        ImageView hinhanh;
        SetOnItemClick itemClick;
        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            txtgiasp= itemView.findViewById(R.id.txtgiatien);
            txttensp= itemView.findViewById(R.id.txttensp);
            hinhanh= itemView.findViewById(R.id.hinhanh);
            if(type==1){
                txtbaohanh = itemView.findViewById(R.id.txtbaohanh);
                txtsoluong = itemView.findViewById(R.id.txtsoluong);
            }
            itemView.setOnClickListener(this);
        }
        public  void  SetOnItem(SetOnItemClick itemClick){
            this.itemClick = itemClick;
        }
        @Override
        public void onClick(View v) {
            itemClick.SetItemClick(v,getAdapterPosition());
        }
    }
}
