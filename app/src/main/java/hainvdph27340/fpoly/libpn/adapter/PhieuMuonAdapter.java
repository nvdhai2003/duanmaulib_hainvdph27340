package hainvdph27340.fpoly.libpn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hainvdph27340.fpoly.libpn.R;
import hainvdph27340.fpoly.libpn.dao.PhieuMuonDAO;
import hainvdph27340.fpoly.libpn.model.PhieuMuon;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PhieuMuon> list;
    PhieuMuonDAO phieuMuonDAO;

    public PhieuMuonAdapter(Context context, ArrayList<PhieuMuon> list, PhieuMuonDAO phieuMuonDAO) {
        this.context = context;
        this.list = list;
        this.phieuMuonDAO = phieuMuonDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_pm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhieuMuon phieuMuon = list.get(position);
        holder.txtMaPM.setText("MÃ PM: " + phieuMuon.getMAPM());
        holder.txtMaTT.setText("MÃ TT: " + phieuMuon.getMATT());
        holder.txtTenTT.setText("TÊN TT: " + phieuMuon.getTENTT());
        holder.txtMaTV.setText("MÃ TV: " + phieuMuon.getMATV());
        holder.txtTenTV.setText("TÊN TV: " + phieuMuon.getTENTV());
        holder.txtMaSach.setText("MÃ SÁCH: " + phieuMuon.getMASACH());
        holder.txtTenSach.setText("TÊN SÁCH: " + phieuMuon.getTENSACH());
        holder.txtNgay.setText("NGÀY: " + phieuMuon.getNGAY());
        String trangThai = "";
        if (phieuMuon.getTRASACH() == 1) {
            trangThai = "ĐÃ TRẢ SÁCH";
            holder.btnTraSach.setVisibility(View.GONE);
        } else {
            trangThai = "CHƯA TRẢ SÁCH";
            holder.btnTraSach.setVisibility(View.VISIBLE);
        }
        holder.txtTrangThai.setText("TRẠNG THÁI: " + trangThai);
        holder.txtTienThue.setText("TIỀN THUÊ: " + phieuMuon.getTIENTHUE());

        holder.btnTraSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = phieuMuonDAO.thayDoiTrangThai(list.get(holder.getAdapterPosition()).getMAPM());
                if (check) {
                    loadData();
                } else {
                    Toast.makeText(context, "Thay đổi trạng thái thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void loadData() {
        list.clear();
        list = phieuMuonDAO.getDSPhieuMuon();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaPM, txtMaTT, txtTenTT, txtMaTV, txtTenTV, txtMaSach, txtTenSach, txtNgay, txtTienThue, txtTrangThai;
        AppCompatButton btnTraSach;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaPM = itemView.findViewById(R.id.txt_mapm);
            txtMaTT = itemView.findViewById(R.id.txt_matt_pm);
            txtTenTT = itemView.findViewById(R.id.txt_tentt_pm);
            txtMaTV = itemView.findViewById(R.id.txt_matv_pm);
            txtTenTV = itemView.findViewById(R.id.txt_tentv_pm);
            txtMaSach = itemView.findViewById(R.id.txt_masach_pm);
            txtTenSach = itemView.findViewById(R.id.txt_tensach_pm);
            txtNgay = itemView.findViewById(R.id.txt_ngay_pm);
            txtTienThue = itemView.findViewById(R.id.txt_tienthue);
            txtTrangThai = itemView.findViewById(R.id.txt_trangthai);
            btnTraSach = itemView.findViewById(R.id.btn_trasach);

        }
    }
}
