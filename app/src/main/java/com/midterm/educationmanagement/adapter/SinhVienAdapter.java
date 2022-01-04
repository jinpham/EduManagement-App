package com.midterm.educationmanagement.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.midterm.educationmanagement.R;
import com.midterm.educationmanagement.ThemSinhVienActivity;
import com.midterm.educationmanagement.dao.LopDao;
import com.midterm.educationmanagement.dao.SinhVienDao;
import com.midterm.educationmanagement.model.Lop;
import com.midterm.educationmanagement.model.SinhVien;


import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class SinhVienAdapter extends BaseAdapter implements Filterable {
    Context context;

    ArrayList<SinhVien>  ds= new ArrayList<>();
    ArrayList<SinhVien> dsSortSinhVien;
    private Filter svFilter;
    LopDao lopDao;
    SinhVienDao sinhVienDao;
    ArrayList<Lop> dsmaLop = new ArrayList<>();
    public SinhVienAdapter(Context context, ArrayList<SinhVien> ds) {
        this.context = context;
        this.ds = ds;
        sinhVienDao = new SinhVienDao(context);
        this.dsSortSinhVien = ds;

    }

    @Override
    public int getCount() {
        return ds.size();
    }

    @Override
    public Object getItem(int position) {
        return ds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataSet(ArrayList<SinhVien> dsl) {
        this.ds = dsl;
        notifyDataSetChanged();
    }

    public void resetData() {
        this.ds = dsSortSinhVien;
    }

    @Override
    public Filter getFilter() {
        if (svFilter == null) {
            svFilter = new CustomFilter();
        }
        return svFilter;
    }

    public class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = dsSortSinhVien;
                results.count = dsSortSinhVien.size();
            } else {
                ArrayList<SinhVien> dssv = new ArrayList<SinhVien>();
                for (SinhVien sv : ds) {
                    if (sv.getTenSv().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        dssv.add(sv);
                    }
                }
                results.values = dssv;
                results.count = ds.size();
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                notifyDataSetInvalidated();
            } else {
                ds = (ArrayList<SinhVien>) results.values;
                notifyDataSetChanged();
            }
        }
    }

    private class ViewHolder extends AppCompatActivity {
        TextView txtTensv, txtMasv, txtemail, txtMalop;
        CircleImageView imageView_Avatalist;
        ImageView ivDelete, ivEdit;
        LinearLayout linearLayout;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.dong_sinhvien, null);
            holder = new ViewHolder();
            holder.txtMasv = convertView.findViewById(R.id.tvmasv);
            holder.txtTensv = convertView.findViewById(R.id.tvtensv);
            holder.txtemail = convertView.findViewById(R.id.tvemail);
            holder.txtMalop = convertView.findViewById(R.id.tvmalop);
            holder.linearLayout = convertView.findViewById(R.id.linearLayoutSuaLop);
            holder.ivDelete = convertView.findViewById(R.id.imageViewDelete);
            holder.imageView_Avatalist = convertView.findViewById(R.id.imageViewHinh);
            holder.ivEdit = convertView.findViewById(R.id.imageViewedit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final SinhVien s = ds.get(position);
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lopDao = new LopDao(context);
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.sua_sinhvien);

                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (dialog != null && dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }

                final EditText etMasv = dialog.findViewById(R.id.edMaSvedit);
                final EditText etTensv = dialog.findViewById(R.id.edTenSvedit);
                final EditText etemail = dialog.findViewById(R.id.edemail);
                final EditText ethinh = dialog.findViewById(R.id.edhinh);
                final CircleImageView imgAvata = dialog.findViewById(R.id.imageView_avata_edit);
                final Spinner spMalop = dialog.findViewById(R.id.spEdmalop);
                Button btnReview = dialog.findViewById(R.id.btnReviewSuaSV);
                final Button btnSua = dialog.findViewById(R.id.btnEdSua);
                final Button btnLai = dialog.findViewById(R.id.btnNhapLai);
                final Button btnHuy = dialog.findViewById(R.id.btnHuyeditSV);
                dsmaLop = lopDao.getAll();
                final ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, dsmaLop);
                spMalop.setAdapter(adapter);
                //Đổ dữ liệu
                etMasv.setText(s.getMaSv());
                etTensv.setText(s.getTenSv());
                etemail.setText(s.getEmail());
                ethinh.setText(s.getHinh());
                int id_hinh = ((Activity) context).getResources().getIdentifier(s.getHinh(), "drawable", ((Activity) context).getPackageName());
                imgAvata.setImageResource(id_hinh);


                int giatri = -1;
                for (int i = 0; i < dsmaLop.size(); i++) {
                    if (dsmaLop.get(i).toString().equalsIgnoreCase(s.getMaLop())) {
                        giatri = i;
                        break;
                    }
                }
                spMalop.setSelection(giatri);


                //Sửa dữ liệu
                btnReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ethinh.getText().toString().equalsIgnoreCase("")){
                            imgAvata.setImageResource(R.drawable.avatamacdinh);
                        }else if(ethinh.getText().toString()!=""){
                            int id_hinh = ((Activity)context).getResources().getIdentifier(ethinh.getText().toString(), "drawable", ((Activity) context).getPackageName());
                            imgAvata.setImageResource(id_hinh);
                        }
                    }
                });
                btnSua.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        try {
                            String maSv = etMasv.getText().toString();
                            String tenSv = etTensv.getText().toString();
                            String email = etemail.getText().toString();
                            Lop lop = (Lop) spMalop.getSelectedItem();
                            String maLop = lop.getMaLop();
                            String hinh = ethinh.getText().toString();
                            if (maSv.equals("")) {
                                Toast.makeText(context, "You need to add a student code", Toast.LENGTH_LONG).show();
                            } else if (tenSv.equals("")) {
                                Toast.makeText(context, "You need to add student name", Toast.LENGTH_LONG).show();
                            } else if (email.equals("")) {
                                Toast.makeText(context, "You need more student email", Toast.LENGTH_LONG).show();
                            } else if (hinh.equals("")) {
                                ethinh.setText("avatamacdinh");
                            } else {
                                SinhVien sinhVien = new SinhVien(maSv, tenSv, email, hinh, maLop);
                                //Update vào sql
                                if (sinhVienDao.update(sinhVien)) {
                                    Toast.makeText(context, "Edit success!", Toast.LENGTH_LONG).show();
                                    ds.clear();
                                    ds.addAll(sinhVienDao.getALL());
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(context, "Edit success!", Toast.LENGTH_LONG).show();

                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                btnLai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etMasv.setText(s.getMaSv());
                        etTensv.setText(s.getTenSv());
                        etemail.setText(s.getEmail());
                        int giatri = -1;
                        for (int i = 0; i < dsmaLop.size(); i++) {
                            if (dsmaLop.get(i).toString().equalsIgnoreCase(s.getMaSv())) {
                                giatri = i;
                                break;
                            }
                        }
                        spMalop.setSelection(giatri);

                    }
                });


                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();


            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sinhVienDao=new SinhVienDao(context);
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.dialog_xoa);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;

                if (dialog != null && dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }

                final TextView txt_Massage = dialog.findViewById(R.id.txt_Titleconfirm);
                Button btnXoa=dialog.findViewById(R.id.btn_yes);
                Button btnHuy=dialog.findViewById(R.id.btn_no);
                final ProgressBar progressBar = dialog.findViewById(R.id.progress_loadconfirm);
                progressBar.setVisibility(View.INVISIBLE);
                txt_Massage.setText("Are you sure to delete "+ s.getMaSv()+"? ");
                final SinhVien sinhVien = ds.get(position);
                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sinhVienDao.delete(sinhVien) == true) {
                            txt_Massage.setText("");
                            progressBar.setVisibility(View.VISIBLE);
                            progressBar.getIndeterminateDrawable().setColorFilter(0xFFFF478B, android.graphics.PorterDuff.Mode.MULTIPLY);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ds.clear();
                                    ds.addAll(sinhVienDao.getALL());
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Delete success", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }, 2000);
                        }
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        holder.txtMasv.setText(s.getMaSv());
        holder.txtTensv.setText(s.getTenSv());
        holder.txtemail.setText(s.getEmail());
        holder.txtMalop.setText(s.getMaLop());
        int id_hinh = ((Activity) context).getResources().getIdentifier(ds.get(position).getHinh(), "drawable", ((Activity) context).getPackageName());
        if (ds.get(position).getHinh().length() + "" == null) {
            holder.imageView_Avatalist.setImageResource(R.drawable.avatamacdinh);
        } else {
            holder.imageView_Avatalist.setImageResource(id_hinh);

        }

        return convertView;

    }


}

