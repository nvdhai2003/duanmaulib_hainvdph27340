package hainvdph27340.fpoly.libpn.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import hainvdph27340.fpoly.libpn.R;
import hainvdph27340.fpoly.libpn.dao.SachDAO;
import hainvdph27340.fpoly.libpn.model.Sach;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Sach> list;
    ArrayList<HashMap<String, Object>> lisHM;
    SachDAO sachDAO;

    public SachAdapter(Context context, ArrayList<Sach> list, ArrayList<HashMap<String, Object>> lisHM, SachDAO sachDAO) {
        this.context = context;
        this.list = list;
        this.lisHM = lisHM;
        this.sachDAO = sachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_sach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sach sach = list.get(position);
        holder.txtMaSach.setText("MÃ SÁCH: " + sach.getMASACH());
        holder.txtTenSach.setText("TÊN SÁCH: " + sach.getTENSACH());
        holder.txtGiaThue.setText("GIÁ THUÊ: " + sach.getGIATHUE());
        holder.txtMaLoai.setText("MÃ LOẠI: " + sach.getMALOAI());
        holder.txtTenLoai.setText("TÊN LOẠI: " + sach.getTENLOAI());
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

                txtTitle.setText("Xóa sách");
                txtXoa.setText("Bạn chắc chắn muốn xóa sách này không?");

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int check = sachDAO.xoaSach(list.get(holder.getAdapterPosition()).getMASACH());
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
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(list.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaSach, txtTenSach, txtGiaThue, txtMaLoai, txtTenLoai;
        ImageView ivEdit, ivDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaSach = itemView.findViewById(R.id.txt_masach);
            txtTenSach = itemView.findViewById(R.id.txt_tensach);
            txtGiaThue = itemView.findViewById(R.id.txt_giathue);
            txtMaLoai = itemView.findViewById(R.id.txt_maloai_sach);
            txtTenLoai = itemView.findViewById(R.id.txt_tenloai_sach);
            ivEdit = itemView.findViewById(R.id.iv_edit_sach);
            ivDelete = itemView.findViewById(R.id.iv_delete_sach);

        }
    }

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    public void showDialog(Sach sach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_dialog_sach, null);
        builder.setView(view);

        EditText edtTenSach = view.findViewById(R.id.edt_edit_tensach);
        EditText edtGiaThue = view.findViewById(R.id.edt_edit_giathue);
        AppCompatSpinner spnTenLoai = view.findViewById(R.id.spn_edit_tenloai);
        TextView txtMaSach = view.findViewById(R.id.txt_edit_masach);
        AppCompatButton btnLuu = view.findViewById(R.id.btn_edit_sach);
        AppCompatButton btnHuy = view.findViewById(R.id.btn_cancel_edit_sach);

        txtMaSach.setText("MÃ SÁCH: " + sach.getMASACH());
        edtTenSach.setText(sach.getTENSACH());
        edtGiaThue.setText(String.valueOf(sach.getGIATHUE()));

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                lisHM,
                android.R.layout.simple_list_item_1,
                new String[]{"TENLOAI"},
                new int[]{android.R.id.text1}
        );
        spnTenLoai.setAdapter(simpleAdapter);

        int index = 0;
        int position = -1;

        for (HashMap<String, Object> item: lisHM) {
            if ((int)item.get("MALOAI") == sach.getMALOAI()) {
                position = index;
            }
            index++;
        }
        spnTenLoai.setSelection(position);
        AlertDialog dialog = builder.create();

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTenSach.setText("");
                edtGiaThue.setText("");
                dialog.dismiss();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edtTenSach.getText().toString();
                int giaThue = Integer.parseInt(edtGiaThue.getText().toString());
                HashMap<String, Object> hs = (HashMap<String, Object>) spnTenLoai.getSelectedItem();
                int maLoai = (int) hs.get("MALOAI");

                if (tenSach.isEmpty() || String.valueOf(giaThue).isEmpty()) {
                    Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = sachDAO.capNhatSach(sach.getMASACH(), tenSach, giaThue, maLoai);
                    if (check) {
                        Toast.makeText(context, "Cập nhật sách thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Cập nhật sách thất bại", Toast.LENGTH_SHORT).show();
                    }
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
    private void loadData() {
        list.clear();
        list = sachDAO.getDSDauSach();
        notifyDataSetChanged();
    }
}