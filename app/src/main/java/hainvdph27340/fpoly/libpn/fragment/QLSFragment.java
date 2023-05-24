package hainvdph27340.fpoly.libpn.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

import hainvdph27340.fpoly.libpn.R;
import hainvdph27340.fpoly.libpn.adapter.SachAdapter;
import hainvdph27340.fpoly.libpn.dao.LoaiSachDAO;
import hainvdph27340.fpoly.libpn.dao.SachDAO;
import hainvdph27340.fpoly.libpn.model.LoaiSach;
import hainvdph27340.fpoly.libpn.model.Sach;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QLSFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QLSFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    SachDAO sachDAO;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QLSFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QLSFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QLSFragment newInstance(String param1, String param2) {
        QLSFragment fragment = new QLSFragment();
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
        View view = inflater.inflate(R.layout.fragment_q_l_s, container, false);
        recyclerView = view.findViewById(R.id.id_recyclerview_qls);
        fabAdd = view.findViewById(R.id.fab_add_sach);
        sachDAO = new SachDAO(getContext());
        loadData();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return view;
    }

    public void loadData() {
        ArrayList<Sach> list = sachDAO.getDSDauSach();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        SachAdapter adapter = new SachAdapter(getContext(), list, getDSLoaiSach(), sachDAO);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_dialog_sach, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText edtTenSach = view.findViewById(R.id.edt_add_tensach);
        EditText edtGiaThue = view.findViewById(R.id.edt_add_giathue);
        AppCompatSpinner spnTenLoai = view.findViewById(R.id.spn_tenloai);
        AppCompatButton btnThem = view.findViewById(R.id.btn_add_sach);
        AppCompatButton btnHuy = view.findViewById(R.id.btn_cancel_add_sach);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTenSach.setText("");
                edtGiaThue.setText("");
                dialog.dismiss();
            }
        });

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getDSLoaiSach(),
                android.R.layout.simple_list_item_1,
                new String[]{"TENLOAI"},
                new int[] {android.R.id.text1}
        );
        spnTenLoai.setAdapter(simpleAdapter);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edtTenSach.getText().toString();
                int giaThue = Integer.parseInt(edtGiaThue.getText().toString());
                HashMap<String, Object> hs = (HashMap<String, Object>) spnTenLoai.getSelectedItem();
                int maLoai = (int) hs.get("MALOAI");

                if (tenSach.isEmpty() || String.valueOf(giaThue).isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = sachDAO.themSach(tenSach, giaThue, maLoai);
                    if (check) {
                        Toast.makeText(getContext(), "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm sách thất bại", Toast.LENGTH_SHORT).show();
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



    private ArrayList<HashMap<String, Object>> getDSLoaiSach() {
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> list = loaiSachDAO.getDSLoaiSach();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for (LoaiSach loaiSach : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("MALOAI", loaiSach.getMALOAI());
            hs.put("TENLOAI", loaiSach.getTENLOAI());
            listHM.add(hs);
        }

        return listHM;

    }
}