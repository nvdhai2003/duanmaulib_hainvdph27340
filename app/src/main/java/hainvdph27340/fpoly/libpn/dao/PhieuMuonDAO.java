package hainvdph27340.fpoly.libpn.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hainvdph27340.fpoly.libpn.database.DbHelper;
import hainvdph27340.fpoly.libpn.model.PhieuMuon;

public class PhieuMuonDAO {
    DbHelper dbHelper;

    public PhieuMuonDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    //    int MAPM, String MATT, int MATV, int MASACH, String NGAY, int TRASACH, int TIENTHUE, String TENTT, String TENTV, String TENSACH
    public ArrayList<PhieuMuon> getDSPhieuMuon() {
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT pm.MAPM, pm.MATT, tt.HOTEN, pm.MATV, tv.HOTEN,  pm.MASACH, sc.TENSACH, pm.NGAY, pm.TIENTHUE, pm.TRASACH\n" +
                "FROM PHIEUMUON pm, THUTHU tt, THANHVIEN tv, SACH sc \n" +
                "WHERE  pm.MATT = tt.MATT AND pm.MATV = tv.MATV AND pm.MASACH = sc.MASACH \n" +
                "ORDER BY pm.MAPM DESC", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new PhieuMuon(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean thayDoiTrangThai (int MAPM) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TRASACH", 1);
        long check = db.update("PHIEUMUON", contentValues, "MAPM = ?", new String[]{String.valueOf(MAPM)});
        if (check == -1) {
            return false;
        }
        return true;
    }

    public boolean themHoaDon (PhieuMuon phieuMuon) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MATT", phieuMuon.getMATT());
        contentValues.put("MATV", phieuMuon.getMATV());
        contentValues.put("MASACH", phieuMuon.getMASACH());
        contentValues.put("NGAY", phieuMuon.getNGAY());
        contentValues.put("TIENTHUE", phieuMuon.getTIENTHUE());
        contentValues.put("TRASACH", phieuMuon.getTRASACH());

        long check = db.insert("PHIEUMUON", null, contentValues);
        if (check == -1) {
            return false;
        }
        return true;
    }
}
