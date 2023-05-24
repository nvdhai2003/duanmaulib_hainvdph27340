package hainvdph27340.fpoly.libpn.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hainvdph27340.fpoly.libpn.database.DbHelper;
import hainvdph27340.fpoly.libpn.model.ThuThu;

public class ThuThuDAO {
    DbHelper dbHelper;
    SharedPreferences sharedPreferences;

    public ThuThuDAO (Context context) {
        dbHelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
    }

    public boolean checkLogin (String MATT, String MATKHAU) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THUTHU WHERE MATT = ? AND MATKHAU = ?", new String[]{MATT, MATKHAU});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("MATT", cursor.getString(0));
            editor.putString("LOAITAIKHOAN", cursor.getString(3));
            editor.putString("HOTEN", cursor.getString(1));
            editor.commit();
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<ThuThu> getDSThuThu() {
        ArrayList<ThuThu> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THUTHU", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new ThuThu(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themThuThu (String MATT, String HOTEN, String MATKHAU) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MATT", MATT);
        contentValues.put("HOTEN", HOTEN);
        contentValues.put("MATKHAU", MATKHAU);
        long check = db.insert("THUTHU", null, contentValues);
        if (check == -1) {
            return false;
        }
        return true;
    }

    public boolean doiMatKhau (String MATT, String MATKHAUCU, String MATKHAUMOI) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THUTHU WHERE MATT = ? AND MATKHAU = ?", new String[]{MATT, MATKHAUCU});
        if (cursor.getCount() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("MATKHAU", MATKHAUMOI);
            long check = db.update("THUTHU", contentValues, "MATT = ?", new String[]{MATT});
            if (check == -1) {
                return false;
            }
            return true;
        }
        return false;
    }
}
