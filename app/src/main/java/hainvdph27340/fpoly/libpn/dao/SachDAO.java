package hainvdph27340.fpoly.libpn.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hainvdph27340.fpoly.libpn.database.DbHelper;
import hainvdph27340.fpoly.libpn.model.Sach;

public class SachDAO {
    DbHelper dbHelper;

    public SachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Sach> getDSDauSach() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT sc.MASACH, sc.TENSACH, sc.GIATHUE, sc.MALOAI, ls.TENLOAI FROM SACH sc, LOAISACH ls WHERE sc.MALOAI = ls.MALOAI", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themSach(String TENSACH, int GIATHUE, int MALOAI) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENSACH", TENSACH);
        contentValues.put("GIATHUE", GIATHUE);
        contentValues.put("MALOAI", MALOAI);
        long check = db.insert("SACH", null, contentValues);
        if (check == -1) {
            return false;
        }
        return true;
    }

    public boolean capNhatSach(int MASACH, String TENSACH, int GIATHUE, int MALOAI) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENSACH", TENSACH);
        contentValues.put("GIATHUE", GIATHUE);
        contentValues.put("MALOAI", MALOAI);
        long check = db.update("SACH", contentValues, "MASACH = ?", new String[]{String.valueOf(MASACH)});
        if (check == -1) {
            return false;
        }
        return true;
    }

    public int xoaSach(int MASACH) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PHIEUMUON WHERE MASACH = ?", new String[]{String.valueOf(MASACH)});
        if (cursor.getCount() != 0) {
            return -1;
        }

        long check = db.delete("SACH", "MASACH = ?", new String[]{String.valueOf(MASACH)});
        if (check == -1) {
            return 0;
        }
        return 1;
    }
}
