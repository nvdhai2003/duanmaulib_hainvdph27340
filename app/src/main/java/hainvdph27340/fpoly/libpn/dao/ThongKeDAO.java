package hainvdph27340.fpoly.libpn.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hainvdph27340.fpoly.libpn.database.DbHelper;
import hainvdph27340.fpoly.libpn.model.Sach;

public class ThongKeDAO {
    DbHelper dbHelper;

    public ThongKeDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Sach> getTop10() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT pm.MASACH, sc.TENSACH, COUNT(pm.MASACH) \n" +
                "FROM PHIEUMUON pm, SACH sc WHERE pm.MASACH = sc.MASACH \n" +
                "GROUP BY pm.MASACH, sc.TENSACH ORDER BY COUNT(pm.MASACH) DESC LIMIT 10", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public int getDoanhThu (String ngaybatdau, String ngayketthuc) {
        ngaybatdau = ngaybatdau.replace("/", "");
        ngayketthuc = ngayketthuc.replace("/", "");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(TIENTHUE) \n" +
                "FROM PHIEUMUON \n" +
                "WHERE substr(ngay,7)||substr(ngay,4,2)||substr(ngay,1,2) between ? and ?", new String[]{ngaybatdau, ngayketthuc});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }
}
