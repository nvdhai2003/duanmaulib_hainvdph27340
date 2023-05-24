package hainvdph27340.fpoly.libpn.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import hainvdph27340.fpoly.libpn.R;
import hainvdph27340.fpoly.libpn.adapter.ThanhVienAdapter;
import hainvdph27340.fpoly.libpn.dao.ThanhVienDAO;
import hainvdph27340.fpoly.libpn.model.ThanhVien;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QLTVFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QLTVFragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddThanhVien;
    ThanhVienDAO thanhVienDAO;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QLTVFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QLTVFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QLTVFragment newInstance(String param1, String param2) {
        QLTVFragment fragment = new QLTVFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_l_t_v, container, false);

        recyclerView = view.findViewById(R.id.id_recyclerview_qltv);
        fabAddThanhVien = view.findViewById(R.id.fab_addTV);
        thanhVienDAO = new ThanhVienDAO(getContext());
        loadData();

        fabAddThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return view;
    }

    public void loadData() {
        ArrayList<ThanhVien> list = thanhVienDAO.getDSThanhVien();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ThanhVienAdapter adapter = new ThanhVienAdapter(getContext(), list, thanhVienDAO);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_dialog_thanhvien, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText edtThemNamSinh = view.findViewById(R.id.edt_add_namsinh);
        EditText edtThemHoTen = view.findViewById(R.id.edt_add_hoten);
        AppCompatButton btnAdd = view.findViewById(R.id.btn_add_thanhvien);
        AppCompatButton btnHuy = view.findViewById(R.id.btn_cancel_add_thanhvien);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtThemHoTen.setText("");
                edtThemNamSinh.setText("");
                dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String themHoTen = edtThemHoTen.getText().toString();
                String themNamSinh = edtThemNamSinh.getText().toString();
                if (themNamSinh.isEmpty() || themHoTen.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = thanhVienDAO.themThanhVien(themHoTen, themNamSinh);
                    if (check) {
                        Toast.makeText(getContext(), "Thêm thành viên thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm thành viên thất bại", Toast.LENGTH_SHORT).show();
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
}