package hainvdph27340.fpoly.libpn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import hainvdph27340.fpoly.libpn.fragment.DoanhThuFragment;
import hainvdph27340.fpoly.libpn.fragment.DoiMatKhauFragment;
import hainvdph27340.fpoly.libpn.fragment.QLLSFragment;
import hainvdph27340.fpoly.libpn.fragment.QLPMFragment;
import hainvdph27340.fpoly.libpn.fragment.QLSFragment;
import hainvdph27340.fpoly.libpn.fragment.QLTVFragment;
import hainvdph27340.fpoly.libpn.fragment.ThemNguoiDungFragment;
import hainvdph27340.fpoly.libpn.fragment.Top10Fragment;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    TextView txtTitle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_color));
        }

        drawerLayout = findViewById(R.id.id_drawer_layout);
        toolbar = findViewById(R.id.id_toolbar);
        navigationView = findViewById(R.id.id_navigation_view);
        View headerLayout = navigationView.getHeaderView(0);
        TextView txtTen = headerLayout.findViewById(R.id.txt_ten);
        txtTitle = findViewById(R.id.txt_title_main);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.openDrawer, R.string.closeDrawer);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        loadFragment(new QLPMFragment());
        txtTitle.setText("Quản lý phiếu mượn");

        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
        String loaiTk = sharedPreferences.getString("LOAITAIKHOAN", "");
        if (!loaiTk.equals("Admin")) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.nav_doanhthu).setVisible(false);
            menu.findItem(R.id.nav_top10).setVisible(false);
            menu.findItem(R.id.nav_them_nguoudung).setVisible(false);
        }
        String hoTen = sharedPreferences.getString("HOTEN", "");
        txtTen.setText("Xin chào, " + hoTen);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_qlpm) {
            loadFragment(new QLPMFragment());
            txtTitle.setText("Quản lý phiếu mượn");
        } else if (id == R.id.nav_qlls) {
            loadFragment(new QLLSFragment());
            txtTitle.setText("Quản lý loại sách");
        } else if (id == R.id.nav_qls) {
            loadFragment(new QLSFragment());
            txtTitle.setText("Quản lý sách");
        } else if (id == R.id.nav_qltv) {
            loadFragment(new QLTVFragment());
            txtTitle.setText("Quản lý thành viên");
        } else if (id == R.id.nav_top10) {
            loadFragment(new Top10Fragment());
            txtTitle.setText("Top 10 sách mượn nhiều nhất");
        } else if (id == R.id.nav_doanhthu) {
            loadFragment(new DoanhThuFragment());
            txtTitle.setText("Doanh thu");
        } else if (id == R.id.nav_them_nguoudung) {
            loadFragment(new ThemNguoiDungFragment());
            txtTitle.setText("Thêm người dùng");
        } else if (id == R.id.nav_doi_matkhau) {
            loadFragment(new DoiMatKhauFragment());
            txtTitle.setText("Đổi mật khẩu");
        } else if (id == R.id.nav_dangxuat) {
            showDialog();
        }
        drawerLayout.closeDrawer(navigationView);
        return true;
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_delete, null);
        builder.setView(v);
        AlertDialog dialog = builder.create();

        AppCompatButton btnXoa = v.findViewById(R.id.btn_xoa_thanhvien);
        AppCompatButton btnHuy = v.findViewById(R.id.btn_huy_thanhvien);
        TextView txtXoa = v.findViewById(R.id.txt_xoa);
        TextView txtTitle = v.findViewById(R.id.txt_title);

        txtTitle.setText("Đăng xuất");
        txtXoa.setText("Bạn chắc chắn muốn đăng xuất không?");

        btnXoa.setText("Đăng xuất");

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
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