package hainvdph27340.fpoly.libpn.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hainvdph27340.fpoly.libpn.R;
import hainvdph27340.fpoly.libpn.dao.LoaiSachDAO;
import hainvdph27340.fpoly.libpn.model.ItemClick;
import hainvdph27340.fpoly.libpn.model.LoaiSach;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LoaiSach> list;
    LoaiSachDAO loaiSachDAO;
    ItemClick itemClick;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list, LoaiSachDAO loaiSachDAO, ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.loaiSachDAO = loaiSachDAO;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_qlls, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiSach loaiSach = list.get(position);
        holder.txtMaLoai.setText("MÃ LOẠI: " + loaiSach.getMALOAI());
        holder.txtTenLoai.setText("TÊN LOẠI: " + loaiSach.getTENLOAI());
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                v = inflater.inflate(R.layout.dialog_delete, null);
                builder.setView(v);
                AlertDialog dialog = builder.create();

                AppCompatButton btnXoa = v.findViewById(R.id.btn_xoa_thanhvien);
                AppCompatButton btnHuy = v.findViewById(R.id.btn_huy_thanhvien);
                TextView txtXoa = v.findViewById(R.id.txt_xoa);
                TextView txtTitle = v.findViewById(R.id.txt_title);

                txtTitle.setText("Xóa loại sách");
                txtXoa.setText("Bạn chắc chắn muốn xóa loại sách này không?");

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int check = loaiSachDAO.xoaLoaiSach(list.get(holder.getAdapterPosition()).getMALOAI());
                        switch (check) {
                            case 1:
                                Toast.makeText(context, "Xóa thành viên thành công", Toast.LENGTH_SHORT).show();
                                loadData();
                                dialog.dismiss();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa thành viên thất bại", Toast.LENGTH_SHORT).show();
                                break;
//                            case -1:
//                                Toast.makeText(context, "Thành viên tồn tại trong phiếu mượn, không được phép xóa", Toast.LENGTH_SHORT).show();
//                                break;
                            default:
                                break;
                        }
                    }
                });
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                    }
                });
                dialog.show();
            }
        });
    }

    private void loadData() {
        list.clear();
        list = loaiSachDAO.getDSLoaiSach();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaLoai, txtTenLoai;
        ImageView ivEdit, ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaLoai = itemView.findViewById(R.id.txt_maloai);
            txtTenLoai = itemView.findViewById(R.id.txt_tenloai);
            ivEdit = itemView.findViewById(R.id.iv_edit_ls);
            ivDelete = itemView.findViewById(R.id.iv_delete_ls);
        }
    }
}
