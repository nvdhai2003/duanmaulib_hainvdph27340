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
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import hainvdph27340.fpoly.libpn.R;
import hainvdph27340.fpoly.libpn.adapter.PhieuMuonAdapter;
import hainvdph27340.fpoly.libpn.dao.PhieuMuonDAO;
import hainvdph27340.fpoly.libpn.dao.SachDAO;
import hainvdph27340.fpoly.libpn.dao.ThanhVienDAO;
import hainvdph27340.fpoly.libpn.dao.ThuThuDAO;
import hainvdph27340.fpoly.libpn.model.PhieuMuon;
import hainvdph27340.fpoly.libpn.model.Sach;
import hainvdph27340.fpoly.libpn.model.ThanhVien;
import hainvdph27340.fpoly.libpn.model.ThuThu;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QLPMFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QLPMFragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    PhieuMuonDAO phieuMuonDAO;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QLPMFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QLPMFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QLPMFragment newInstance(String param1, String param2) {
        QLPMFragment fragment = new QLPMFragment();
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
        View view = inflater.inflate(R.layout.fragment_q_l_p_m, container, false);
        recyclerView = view.findViewById(R.id.id_recyclerview_qlpm);
        fabAdd = view.findViewById(R.id.fab_add_pm);
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        loadData();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        return view;
    }

    public void loadData() {
        ArrayList<PhieuMuon> list = phieuMuonDAO.getDSPhieuMuon();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        PhieuMuonAdapter adapter = new PhieuMuonAdapter(getContext(), list, phieuMuonDAO);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    public void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_dialog_pm, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        AppCompatSpinner spnThuThu = view.findViewById(R.id.spn_thuthu);
        AppCompatSpinner spnThanhVien = view.findViewById(R.id.spn_thanhvien);
        AppCompatSpinner spnSach = view.findViewById(R.id.spn_sach);
        AppCompatButton btnThem = view.findViewById(R.id.btn_add_pm);
        AppCompatButton btnHuy = view.findViewById(R.id.btn_cancel_add_pm);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> hsTT = (HashMap<String, Object>) spnThuThu.getSelectedItem();
                String matt = (String) hsTT.get("MATT");

                HashMap<String, Object> hsTV = (HashMap<String, Object>) spnThanhVien.getSelectedItem();
                int matv = (int) hsTV.get("MATV");

                HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                int masach = (int) hsSach.get("MASACH");

                int tien = (int) hsSach.get("GIATHUE");
                themPhieuMuon(matt, matv, masach, tien);
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

        getDataThuThu(spnThuThu);
        getDataThanhVien(spnThanhVien);
        getDataSach(spnSach);
    }

    private void getDataThuThu(AppCompatSpinner spnThuThu) {
        ThuThuDAO thuThuDAO = new ThuThuDAO(getContext());
        ArrayList<ThuThu> list = thuThuDAO.getDSThuThu();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (ThuThu thuThu : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("MATT", thuThu.getMATT());
            hs.put("HOTEN", thuThu.getHOTEN());
            listHM.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"HOTEN"},
                new int[]{android.R.id.text1}
        );
        spnThuThu.setAdapter(simpleAdapter);
    }

    private void getDataThanhVien(AppCompatSpinner spnThanhVien) {
        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(getContext());
        ArrayList<ThanhVien> list = thanhVienDAO.getDSThanhVien();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (ThanhVien thanhVien : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("MATV", thanhVien.getMATV());
            hs.put("HOTEN", thanhVien.getHOTEN());
            listHM.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"HOTEN"},
                new int[]{android.R.id.text1}
        );
        spnThanhVien.setAdapter(simpleAdapter);
    }

    private void getDataSach(AppCompatSpinner spnSach) {
        SachDAO sachDAO = new SachDAO(getContext());
        ArrayList<Sach> list = sachDAO.getDSDauSach();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Sach sach : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("MASACH", sach.getMASACH());
            hs.put("TENSACH", sach.getTENSACH());
            hs.put("GIATHUE", sach.getGIATHUE());
            listHM.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"TENSACH"},
                new int[]{android.R.id.text1}
        );
        spnSach.setAdapter(simpleAdapter);
    }

    private void themPhieuMuon(String MATT, int MATV, int MASACH, int tien) {
        Date curentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(curentTime);
        PhieuMuon phieuMuon = new PhieuMuon(MATT, MATV, MASACH, ngay, 0, tien);
        boolean check = phieuMuonDAO.themHoaDon(phieuMuon);
        if (check) {
            Toast.makeText(getContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
            loadData();
        } else {
            Toast.makeText(getContext(), "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}