package hainvdph27340.fpoly.libpn.fragment;

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

import java.util.ArrayList;

import hainvdph27340.fpoly.libpn.R;
import hainvdph27340.fpoly.libpn.adapter.LoaiSachAdapter;
import hainvdph27340.fpoly.libpn.dao.LoaiSachDAO;
import hainvdph27340.fpoly.libpn.model.ItemClick;
import hainvdph27340.fpoly.libpn.model.LoaiSach;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QLLSFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QLLSFragment extends Fragment {

    private RecyclerView recyclerView;
    EditText edtTenLoai;
    AppCompatButton btnThem, btnSua;
    LoaiSachDAO loaiSachDAO;
    int maLoai;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QLLSFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QLLSFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QLLSFragment newInstance(String param1, String param2) {
        QLLSFragment fragment = new QLLSFragment();
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
        View view = inflater.inflate(R.layout.fragment_q_l_l_s, container, false);

        recyclerView = view.findViewById(R.id.id_recyclerview_qlls);
        loaiSachDAO = new LoaiSachDAO(getContext());
        edtTenLoai = view.findViewById(R.id.edt_tenloai);
        btnThem = view.findViewById(R.id.btn_add_ls);
        btnSua = view.findViewById(R.id.btn_edit_ls);
        loadData();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edtTenLoai.getText().toString();
                if (tenLoai.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = loaiSachDAO.themLoaiSach(tenLoai);
                    if (check) {
                        Toast.makeText(getContext(), "Thêm loại sách thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                    } else {
                        Toast.makeText(getContext(), "Thêm loại sách thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edtTenLoai.getText().toString();
                LoaiSach loaiSach = new LoaiSach(maLoai, tenLoai);
                if (loaiSachDAO.capNhatLoaiSach(loaiSach)) {
                    Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    edtTenLoai.setText("");
                } else {
                    Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public void loadData() {
        ArrayList<LoaiSach> list = loaiSachDAO.getDSLoaiSach();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        LoaiSachAdapter adapter = new LoaiSachAdapter(getContext(), list, loaiSachDAO, new ItemClick() {
            @Override
            public void onClick(LoaiSach loaiSach) {
                edtTenLoai.setText(loaiSach.getTENLOAI());
                maLoai = loaiSach.getMALOAI();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}