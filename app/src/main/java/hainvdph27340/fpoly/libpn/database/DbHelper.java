package hainvdph27340.fpoly.libpn.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "LibPN";
    private static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Bảng Thủ Thư
        String dbTableThuThu = "CREATE TABLE THUTHU (MATT TEXT PRIMARY KEY, HOTEN TEXT NOT NULL, MATKHAU TEXT NOT NULL, LOAITAIKHOAN TEXT )";
        db.execSQL(dbTableThuThu);
        // Bảng Thành Viên
        String dbTableThanhVien = "CREATE TABLE THANHVIEN (MATV INTEGER PRIMARY KEY AUTOINCREMENT, HOTEN TEXT NOT NULL, NAMSINH TEXT NOT NULL, GIOITINH TEXT NOT NULL)";
        db.execSQL(dbTableThanhVien);
        // Bảng Loại Sách
        String dbTableLoaiSach = "CREATE TABLE LOAISACH (MALOAI INTEGER PRIMARY KEY AUTOINCREMENT, TENLOAI TEXT NOT NULL)";
        db.execSQL(dbTableLoaiSach);
        // Bảng Sách
        String dbTableSach = "CREATE TABLE SACH (MASACH INTEGER PRIMARY KEY AUTOINCREMENT, TENSACH TEXT NOT NULL," +
                "GIATHUE INTEGER NOT NULL, MALOAI INTEGER NOT NULL REFERENCES LOAISACH (MALOAI))";
        db.execSQL(dbTableSach);
        // Bảng Phiếu Mượn
        String dbTablePhieuMuon = "CREATE TABLE PHIEUMUON (MAPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MATT TEXT NOT NULL REFERENCES THUTHU (MATT), MATV INTEGER NOT NULL REFERENCES THANHVIEN (MATV)," +
                "MASACH INTEGER NOT NULL REFERENCES SACH (MASACH), NGAY TEXT NOT NULL," +
                "TRASACH INTEGER NOT NULL, TIENTHUE INTEGER NOT NULL)";
        db.execSQL(dbTablePhieuMuon);

        // Data mẫu
        db.execSQL("INSERT INTO THUTHU VALUES ('admin', 'Nguyễn Văn Hải', 'abc123', 'Admin'), ('thuthu01', 'Nguyễn Văn Trung', 'abc456', 'ThuThu')");
        db.execSQL("INSERT INTO THANHVIEN VALUES (1, 'Nguễn Văn Trà' , '2000', 'Nam'), (2, 'Nguyễn Thị Hồng', '1998', 'Nữ')");
        db.execSQL("INSERT INTO LOAISACH VALUES (1, 'Giáo khoa'), (2, 'CNTT')");
        db.execSQL("INSERT INTO SACH VALUES (1, 'Ngữ Văn', 3000, 1), (2, 'Lập trình Android', 5000, 2)");
        db.execSQL("INSERT INTO PHIEUMUON VALUES (1, 'thuthu01', 1, 1, '27/03/2019', 0, 3000), (2, 'thuthu01', 2, 2, '27/01/2017', 1, 5000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1) {
            db.execSQL("DROP TABLE IF EXISTS THUTHU");
            db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            db.execSQL("DROP TABLE IF EXISTS LOAISACH");
            db.execSQL("DROP TABLE IF EXISTS SACH");
            db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
        }
    }
}
