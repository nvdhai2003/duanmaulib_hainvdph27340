package hainvdph27340.fpoly.libpn.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import hainvdph27340.fpoly.libpn.LoginActivity;
import hainvdph27340.fpoly.libpn.R;
import hainvdph27340.fpoly.libpn.dao.ThuThuDAO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoiMatKhauFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoiMatKhauFragment extends Fragment {
    EditText edtMatKhauCu, edtMatKhauMoi, edtNhapLaiMk;
    AppCompatButton btnDoiMk;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoiMatKhauFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoiMatKhauFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoiMatKhauFragment newInstance(String param1, String param2) {
        DoiMatKhauFragment fragment = new DoiMatKhauFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
        edtMatKhauCu = view.findViewById(R.id.edt_old_pass);
        edtMatKhauMoi = view.findViewById(R.id.edt_new_pass);
        edtNhapLaiMk = view.findViewById(R.id.edt_repass);
        btnDoiMk = view.findViewById(R.id.btn_doi_mk);
        ThuThuDAO dao = new ThuThuDAO(getContext());

        btnDoiMk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matKhauCu = edtMatKhauCu.getText().toString();
                String matKhauMoi = edtMatKhauMoi.getText().toString();
                String nhapLaiMk = edtNhapLaiMk.getText().toString();
                if (matKhauCu.isEmpty() || matKhauMoi.isEmpty() || nhapLaiMk.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!nhapLaiMk.equals(matKhauMoi)) {
                    Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
                    String MATT = sharedPreferences.getString("MATT", "");
                    boolean check = dao.doiMatKhau(MATT, matKhauCu, matKhauMoi);
                    if (check) {
                        Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }
}