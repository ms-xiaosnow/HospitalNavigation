package com.hospitalnavigation.fregment;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.hospitalnavigation.R;
import com.hospitalnavigation.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

public class RegisterFragment extends BaseFragment {
    private Spinner sp_service, sp_hospital, sp_department, sp_doctor, sp_disease;
    private ArrayAdapter<String> arr_adapter;
    private TextView tv_time;
    private EditText ev_dsp;

    @Override
    public int bindLayout() {
        return R.layout.fg_register;
    }

    @Override
    public void initView(View view) {
        sp_service = view.findViewById(R.id.sp_service);
        sp_hospital = view.findViewById(R.id.sp_hospital);
        sp_department = view.findViewById(R.id.sp_department);
        sp_doctor = view.findViewById(R.id.sp_doctor);
        sp_disease = view.findViewById(R.id.sp_disease);
        tv_time = view.findViewById(R.id.tv_time);
        ev_dsp = view.findViewById(R.id.ev_dsp);
        service();
        hospital();
        department();
        doctor();
        disease();
        view.findViewById(R.id.tv_sc_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .create();
                dialog.show();
                DatePicker picker = new DatePicker(getContext());
                picker.setDate(2019, 5);
                picker.setMode(DPMode.SINGLE);
                picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
                    @Override
                    public void onDatePicked(final String date) {
                        dialog.dismiss();
                        final TimePickerDialog timePickerDialog = new TimePickerDialog
                                (getContext()
                                        , new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                        tv_time.setText(
                                                date + "\t" +
                                                        (hourOfDay < 10 ? "0" + hourOfDay : hourOfDay) +
                                                        ":" +
                                                        (minute < 10 ? "0" + minute :
                                                                minute));
                                    }
                                }, 6, 0, true);
                        timePickerDialog.show();
                    }
                });
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setContentView(picker, params);
                dialog.getWindow().setGravity(Gravity.CENTER);


            }
        });

        view.findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tv_time.getText().toString())) {
                    ToastUtils.show(getContext(), "请选择时间");
                    return;
                }
                if (ev_dsp.getText().toString().length() < 5) {
                    ToastUtils.show(getContext(), "病情描述不少于5个字");
                    return;
                }
                AVObject product = new AVObject("UserRegistration");
                product.put("service", sp_service.getSelectedItem().toString());
                product.put("hospital", sp_hospital.getSelectedItem().toString());
                product.put("department", sp_department.getSelectedItem().toString());
                product.put("doctor", sp_doctor.getSelectedItem().toString());
                product.put("disease", sp_disease.getSelectedItem().toString());
                product.put("describe", ev_dsp.getText().toString());
                product.put("time", tv_time.getText().toString());
                product.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            ToastUtils.show(getContext(), "提交成功");
                        } else {
                            ToastUtils.show(getContext(), e.getMessage());
                        }
                    }
                });
            }
        });
    }

    private void service() {
        //数据
        List<String> data_list = new ArrayList<String>();
        data_list.add("特需号");
        data_list.add("专家号");
        data_list.add("普通号");
        //适配器
        arr_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        sp_service.setAdapter(arr_adapter);
    }

    private void hospital() {
        //数据
        List<String> data_list = new ArrayList<String>();
        data_list.add("淮安市第一人民医院");
        data_list.add("淮安市同仁医院");
        data_list.add("北京协和医院");
        data_list.add("清华大学一附院");
        data_list.add("北京同仁医院");

        //适配器
        arr_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        sp_hospital.setAdapter(arr_adapter);
    }

    private void department() {
        //数据
        List<String> data_list = new ArrayList<String>();
        data_list.add("内科");
        data_list.add("外科");
        data_list.add("儿科");
        data_list.add("眼科");
        data_list.add("其他");

        //适配器
        arr_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        sp_department.setAdapter(arr_adapter);
    }

    private void doctor() {
        //数据
        List<String> data_list = new ArrayList<String>();
        data_list.add("任意主治医师");
        data_list.add("王林杰主治医师");
        data_list.add("付勇主治医师");
        data_list.add("于淼主治医师");
        data_list.add("胡明明主治医师");
        //适配器
        arr_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        sp_doctor.setAdapter(arr_adapter);
    }

    private void disease() {
        //数据
        List<String> data_list = new ArrayList<String>();
        data_list.add("发热");
        data_list.add("糖尿病");
        data_list.add("骨软骨病");
        data_list.add("高血压");
        data_list.add("肺炎");

        //适配器
        arr_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        sp_disease.setAdapter(arr_adapter);
    }

    @Override
    public void initData(View view) {

    }
}
