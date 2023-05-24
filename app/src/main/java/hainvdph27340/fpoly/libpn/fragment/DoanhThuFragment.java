package hainvdph27340.fpoly.libpn.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import hainvdph27340.fpoly.libpn.R;
import hainvdph27340.fpoly.libpn.dao.ThongKeDAO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoanhThuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoanhThuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoanhThuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoanhThuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoanhThuFragment newInstance(String param1, String param2) {
        DoanhThuFragment fragment = new DoanhThuFragment();
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
        View view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);

        EditText edtNgayBD = view.findViewById(R.id.edt_tungay);
        EditText edtNgayKT = view.findViewById(R.id.edt_denngay);
        AppCompatButton btnDoanhThu = view.findViewById(R.id.btn_doanhthu);
        TextView txtKetQua = view.findViewById(R.id.txt_ketqua);

        Calendar calendar = Calendar.getInstance();

        edtNgayBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int i, int i1, int i2) {
                                String ngay = "";
                                String thang = "";
                                if (i2 < 10) {
                                    ngay = "0" + i2;
                                } else {
                                    ngay = String.valueOf(i2);
                                }

                                if ((i1 + 1) < 10) {
                                    thang = "0" + (i1 + 1);
                                } else {
                                    thang = String.valueOf(i1 + 1);
                                }

                                edtNgayBD.setText(i + "/" + thang + "/" + ngay);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                datePickerDialog.show();
            }
        });

        edtNgayKT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int i, int i1, int i2) {
                                String ngay = "";
                                String thang = "";
                                if (i2 < 10) {
                                    ngay = "0" + i2;
                                } else {
                                    ngay = String.valueOf(i2);
                                }

                                if ((i1 + 1) < 10) {
                                    thang = "0" + (i1 + 1);
                                } else {
                                    thang = String.valueOf(i1 + 1);
                                }

                                edtNgayKT.setText(i + "/" + thang + "/" + ngay);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                datePickerDialog.show();
            }
        });

        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
                String ngayBD = edtNgayBD.getText().toString();
                String ngayKT = edtNgayKT.getText().toString();
                int doanhThu = thongKeDAO.getDoanhThu(ngayBD, ngayKT);
                txtKetQua.setText("DOANH THU: " + doanhThu + " VNĐ");
                txtKetQua.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}