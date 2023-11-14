package com.example.ph32302_mobile.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ph32302_mobile.Dao.ThuThuDao;
import com.example.ph32302_mobile.MainActivity;
import com.example.ph32302_mobile.Mode.ThuThuMode;
import com.example.ph32302_mobile.R;
import com.google.android.material.textfield.TextInputEditText;


public class FragmentTaoAcc extends Fragment {


    public FragmentTaoAcc() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        // Đặt tiêu đề trên Toolbar khi Fragment này được hiển thị
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setActionBarTitle("Tạo tài khoản");
        }
    }

// ... Các phương thức khác của Fragment

         TextInputEditText txtUse,txtName,txtPassword;
    Button add,canel;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_tao_acc, container, false);
        txtUse=view.findViewById(R.id.txtUseName);
        txtName=view.findViewById(R.id.txtten);
        txtPassword=view.findViewById(R.id.txtPass);
        add=view.findViewById(R.id.btnaddtt);
        canel=view.findViewById(R.id.btncanel);


        // xử lý ấn bấm vào nút thêm
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Use = txtUse.getText().toString();
                String name=txtName.getText().toString();
                String pass=txtPassword.getText().toString();
                if(Use.isEmpty()){
                    Toast.makeText(getActivity(),"Không để trống UseName",Toast.LENGTH_LONG).show();
                } else if (name.isEmpty()) {
                    Toast.makeText(getActivity(),"Không để trống Họ tên",Toast.LENGTH_LONG).show();
                } else if (pass.isEmpty()) {
                    Toast.makeText(getActivity(),"Không để trống password",Toast.LENGTH_LONG).show();
                }else{
                        // xử lý dữ liệu thêm
                    try {
                        ThuThuDao thuThuDao=new ThuThuDao(getActivity());
                        ThuThuMode thuThuMode= new ThuThuMode(Use,name,pass,0);
                        if(!thuThuDao.checkTaiKoan(Use)){
                            if (thuThuDao.addThuThu(thuThuMode)){
                                txtName.setText("");
                                txtUse.setText("");
                                txtPassword.setText("");
                                Toast.makeText(getActivity(),"Bạn vừa thêm 1 Thủ Thư mới",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getActivity(),"Thêm Thất bại",Toast.LENGTH_LONG).show();
                                Toast.makeText(getActivity(),"Use đã tồn tại",Toast.LENGTH_LONG).show();
                            }
                        }else { Toast.makeText(getActivity(),"Use đã tồn tại",Toast.LENGTH_LONG).show();}

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        // xử lý khi bấm canel
        canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtName.setText("");
                txtUse.setText("");
                txtPassword.setText("");
            }
        });
        return view;

    }
}