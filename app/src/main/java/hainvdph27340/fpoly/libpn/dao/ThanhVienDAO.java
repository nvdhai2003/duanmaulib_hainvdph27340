package hainvdph27340.fpoly.libpn.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hainvdph27340.fpoly.libpn.database.DbHelper;
import hainvdph27340.fpoly.libpn.model.ThanhVien;

public class ThanhVienDAO {
    DbHelper dbHelper;

    public ThanhVienDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<ThanhVien> getDSThanhVien() {
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THANHVIEN", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new ThanhVien(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themThanhVien(String HOTEN, String NAMSINH, String GIOITINH) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("HOTEN", HOTEN);
        contentValues.put("NAMSINH", NAMSINH);
        contentValues.put("GIOITINH", GIOITINH);
        long check = db.insert("THANHVIEN", null, contentValues);
        if (check == -1) {
            return false;
        }
        return true;
    }

    public int xoaThanhVien(int MATV) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THANHVIEN WHERE MATV = ?", new String[]{String.valueOf(MATV)});
//        if (cursor.getCount() != 0) {
//            return -1;
//        }
        long check = db.delete("THANHVIEN", "MATV = ?", new String[]{String.valueOf(MATV)});
        if (check == -1) {
            return 0;
        }
        return 1;
    }

    public boolean capNhatThanhVien(int MATV, String HOTEN, String NAMSINH) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("HOTEN", HOTEN);
        contentValues.put("NAMSINH", NAMSINH);
        long check = db.update("THANHVIEN", contentValues, "MATV = ?", new String[]{String.valueOf(MATV)});
        if (check == -1) {
            return false;
        }
        return true;
    }
}
