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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hainvdph27340.fpoly.libpn.R;
import hainvdph27340.fpoly.libpn.dao.ThanhVienDAO;
import hainvdph27340.fpoly.libpn.model.ThanhVien;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ThanhVien> list;
    ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list, ThanhVienDAO thanhVienDAO) {
        this.context = context;
        this.list = list;
        this.thanhVienDAO = thanhVienDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_qltv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThanhVien thanhVien = list.get(position);
        holder.txtMaTv.setText("MÃ THÀNH VIÊN: " + thanhVien.getMATV());
        holder.txtTenTv.setText("HỌ TÊN: " + thanhVien.getHOTEN());
        holder.txtNamSinh.setText("NĂM SINH: " + thanhVien.getNAMSINH());
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogEdit(list.get(holder.getAdapterPosition()));
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

                txtXoa.setText("Bạn chắc chắn muốn xóa thành viên này không?");

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int check = thanhVienDAO.xoaThanhVien(list.get(holder.getAdapterPosition()).getMATV());
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
        list = thanhVienDAO.getDSThanhVien();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaTv, txtTenTv, txtNamSinh;
        ImageView ivEdit, ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaTv = itemView.findViewById(R.id.txt_matv);
            txtTenTv = itemView.findViewById(R.id.txt_tentv);
            txtNamSinh = itemView.findViewById(R.id.txt_namsinh);
            ivEdit = itemView.findViewById(R.id.iv_edit_tv);
            ivDelete = itemView.findViewById(R.id.iv_delete_tv);
        }
    }

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    public void showDialogEdit(ThanhVien thanhVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.edit_dialog_thanhvien, null);
        builder.setView(view);

        TextView txtMatv = view.findViewById(R.id.txt_edit_matv);
        EditText edtEditHoTen = view.findViewById(R.id.edt_edt_hoten);
        EditText edtEditNamSinh = view.findViewById(R.id.edt_edt_namsinh);
        AppCompatButton btnLuu = view.findViewById(R.id.btn_save_thanhvien);
        AppCompatButton btnHuy = view.findViewById(R.id.btn_cancel_thanhvien);

        txtMatv.setText("MÃ THÀNH VIÊN: " + String.valueOf(thanhVien.getMATV()));
        edtEditHoTen.setText(thanhVien.getHOTEN());
        edtEditNamSinh.setText(thanhVien.getNAMSINH());

        AlertDialog dialog = builder.create();
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = edtEditHoTen.getText().toString();
                String namsinh = edtEditNamSinh.getText().toString();
                int matv = thanhVien.getMATV();

                boolean check = thanhVienDAO.capNhatThanhVien(matv, hoten, namsinh);
                if (hoten.isEmpty() || namsinh.isEmpty()) {
                    Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (check) {
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                    } else {
                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                dialog.dismiss();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtEditHoTen.setText("");
                edtEditNamSinh.setText("");
                dialog.dismiss();
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
}
