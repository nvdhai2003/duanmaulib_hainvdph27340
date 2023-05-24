package hainvdph27340.fpoly.libpn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hainvdph27340.fpoly.libpn.R;
import hainvdph27340.fpoly.libpn.model.Sach;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.ViewHolder>{
    private Context context;
    ArrayList<Sach> list;

    public Top10Adapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_top10, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sach sach = list.get(position);
        holder.txtMaSach.setText("MÃ SÁCH: " + sach.getMASACH());
        holder.txtTenSach.setText("TÊN SÁCH: " + sach.getTENSACH());
        holder.txtSoLuongMuon.setText("SỐ LƯỢNG MƯỢN SÁCH: " + sach.getSOLUONGMUON());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaSach, txtTenSach, txtSoLuongMuon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaSach = itemView.findViewById(R.id.txt_masach_top10);
            txtTenSach = itemView.findViewById(R.id.txt_tensach_top10);
            txtSoLuongMuon = itemView.findViewById(R.id.txt_soluong_muon);
        }
    }
}