package hainvdph27340.fpoly.libpn.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hainvdph27340.fpoly.libpn.database.DbHelper;
import hainvdph27340.fpoly.libpn.model.LoaiSach;

public class LoaiSachDAO {
    DbHelper dbHelper;

    public LoaiSachDAO (Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<LoaiSach> getDSLoaiSach() {
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM LOAISACH", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themLoaiSach (String TENLOAI) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENLOAI", TENLOAI);
        long check = db.insert("LOAISACH", null, contentValues);
        if (check == -1) {
            return false;
        }
        return true;
    }

    public int xoaLoaiSach (int MALOAI) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM LOAISACH WHERE MALOAI = ?", new String[]{String.valueOf(MALOAI)});
//        if (cursor.getCount() != 0) {
//            return -1;
//        }
        long check = db.delete("LOAISACH", "MALOAI = ?", new String[]{String.valueOf(MALOAI)});
        if (check == -1) {
            return 0;
        }
        return 1;
    }

    public boolean capNhatLoaiSach(LoaiSach loaiSach) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENLOAI", loaiSach.getTENLOAI());
        long check = db.update("LOAISACH", contentValues, "MALOAI = ?", new String[]{String.valueOf(loaiSach.getMALOAI())});
        if (check == -1) {
            return false;
        }
        return true;
    }
}
