package com.midterm.educationmanagement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.midterm.educationmanagement.MainActivity;
import com.midterm.educationmanagement.database.DBHeplper;
import com.midterm.educationmanagement.model.SinhVien;

import java.text.ParseException;
import java.util.ArrayList;

public class SinhVienDao {
    DBHeplper dbHelper;
    public SinhVienDao(Context context) {
        dbHelper = new DBHeplper(context);
    }
    public ArrayList<SinhVien> getALL() {
        ArrayList<SinhVien> ds = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM SINHVIEN", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            try {
                String ma = cursor.getString(0);
                String ten = cursor.getString(1);
                String email = cursor.getString(2);
                String hinh=cursor.getString(3);
                String maLop = cursor.getString(4);
                SinhVien sinhVien = new SinhVien(ma, ten, email,hinh, maLop);
                ds.add(sinhVien);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cursor.moveToNext();
        }
        cursor.close();
        return ds;
    }

    public boolean insert(SinhVien s) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maSv", s.getMaSv());
        contentValues.put("tenSV", s.getTenSv());
        contentValues.put("email",s.getEmail());
        contentValues.put("hinh",s.getHinh());
        contentValues.put("maLop", s.getMaLop());
        long r = db.insert("SINHVIEN", null, contentValues);
        if (r <= 0) {
            return false;
        }
        return true;
    }

    public boolean update(SinhVien s) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("maSv", s.getMaSv());
        contentValues.put("tenSV", s.getTenSv());
        contentValues.put("email",s.getEmail());
        contentValues.put("hinh",s.getHinh());
        contentValues.put("maLop", s.getMaLop());

        long r = db.update("SINHVIEN", contentValues, "maSv=?", new String[]{s.getMaSv()});

        if (r <= 0) {
            return false;
        }
        return true;
    }

    public boolean delete(SinhVien s) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int r = db.delete("SINHVIEN", "maSv=?", new String[]{s.getMaSv()});
        if (r <= 0) {
            return false;
        }
        return true;
    }

}
