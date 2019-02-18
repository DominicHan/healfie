package com.fn.healfie.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.component.dialog.GenderChoiceDialog;
import com.fn.healfie.component.dialog.WheelSelectDialog;
import com.fn.healfie.component.pickdatetime.DatePickDialog;
import com.fn.healfie.component.pickdatetime.OnSureListener;
import com.fn.healfie.component.pickdatetime.bean.DateParams;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.model.BaseBean;
import com.fn.healfie.model.NewsListBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;
import com.google.zxing.common.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class EditCareActivity extends BaseActivity implements View.OnClickListener,GenderChoiceDialog.DialogClick,WheelSelectDialog.DialogClick{

    private Activity activity = this;

    private ImageView backIv;
    private TextView finishTv;
    private RelativeLayout allergyRl;
    private TextView allergyTv;
    private RelativeLayout sicknessRl;
    private TextView sicknessTv;

    private RelativeLayout sexRl;
    private TextView sexTv;
    private RelativeLayout ageRl;
    private TextView ageTv;
    private RelativeLayout heightRl;
    private TextView heightTv;
    private RelativeLayout weightRl;
    private TextView weightTv;
    private RelativeLayout bloodRl;
    private TextView bloodTv;

    private EditText nameEt;
    private String name;
    private String dateOfBirth;

    private GenderChoiceDialog genderChoiceDialog;
    private WheelSelectDialog wheelSelectDialog;

    private int type = -1;
    private String[] bloodTypes = new String[]{"A+","A-","B+","B-","AB+","AB-","O+","O-"};
    private String[] heights = new String[]{"0cm","1cm","2cm","3cm","4cm","5cm","6cm","7cm","8cm",
            "9cm","10cm","11cm","12cm","13cm","14cm","15cm","16cm","17cm","18cm","19cm","20cm",
            "21cm","22cm","23cm","24cm","25cm","26cm","27cm","28cm","29cm","30cm","31cm","32cm",
            "33cm","34cm","35cm","36cm","37cm","38cm","39cm","40cm","41cm","42cm","43cm","44cm",
            "45cm","46cm","47cm","48cm","49cm","50cm","51cm","52cm","53cm","54cm","55cm","56cm",
            "57cm","58cm","59cm","60cm","61cm","62cm","63cm","64cm","65cm","66cm",
            "67cm","68cm","69cm","70cm","71cm","72cm","73cm","74cm","75cm","76cm","77cm","78cm",
            "79cm","80cm","81cm","82cm","83cm","84cm","85cm","86cm","87cm","88cm","89cm","90cm",
            "91cm","92cm","93cm","94cm","95cm","96cm","97cm","98cm","99cm","100cm","101cm","102cm",
            "103cm","104cm","105cm","106cm","107cm","108cm", "109cm","110cm","111cm","112cm",
            "113cm","114cm","115cm","116cm","117cm","118cm","119cm","120cm", "121cm","122cm",
            "123cm","124cm","125cm","126cm","127cm","128cm","129cm","130cm","131cm","132cm",
            "133cm","134cm","135cm","136cm","137cm","138cm","139cm","140cm","141cm","142cm",
            "143cm","144cm", "145cm","146cm","147cm","148cm","149cm","150cm", "151cm","152cm",
            "153cm","154cm","155cm","156cm", "157cm","158cm","159cm","160cm", "161cm","162cm",
            "163cm","164cm","165cm","166cm", "167cm","168cm","169cm","170cm","171cm","172cm",
            "173cm","174cm","175cm","176cm","177cm","178cm", "179cm","180cm","181cm","182cm",
            "183cm","184cm","185cm","186cm","187cm","188cm","189cm","190cm", "191cm","192cm",
            "193cm","194cm","195cm","196cm","197cm","198cm","199cm","200cm","201cm","202cm",
            "203cm","204cm","205cm","206cm","207cm","208cm", "209cm","210cm","211cm","212cm",
            "213cm","214cm","215cm","216cm","217cm","218cm","219cm","220cm", "221cm","222cm",
            "223cm","224cm","225cm","226cm","227cm","228cm","229cm","230cm"};
    private String[] weights = new String[]{"0kg","1kg","2kg","3kg","4kg","5kg","6kg","7kg","8kg",
            "9kg","10kg","11kg","12kg","13kg","14kg","15kg","16kg","17kg","18kg","19kg","20kg",
            "21kg","22kg","23kg","24kg","25kg","26kg","27kg","28kg","29kg","30kg","31kg","32kg",
            "33kg","34kg","35kg","36kg","37kg","38kg","39kg","40kg","41kg","42kg","43kg","44kg",
            "45kg","46kg","47kg","48kg","49kg","50kg","51kg","52kg","53kg","54kg","55kg","56kg",
            "57kg","58kg","59kg","60kg","61kg","62kg","63kg","64kg","65kg","66kg",
            "67kg","68kg","69kg","70kg","71kg","72kg","73kg","74kg","75kg","76kg","77kg","78kg",
            "79kg","80kg","81kg","82kg","83kg","84kg","85kg","86kg","87kg","88kg","89kg","90kg",
            "91kg","92kg","93kg","94kg","95kg","96kg","97kg","98kg","99kg","100kg","101kg","102kg",
            "103kg","104kg","105kg","106kg","107kg","108kg", "109kg","110kg","111kg","112kg",
            "113kg","114kg","115kg","116kg","117kg","118kg","119kg","120kg", "121kg","122kg",
            "123kg","124kg","125kg","126kg","127kg","128kg","129kg","130kg","131kg","132kg",
            "133kg","134kg","135kg","136kg","137kg","138kg","139kg","140kg","141kg","142kg",
            "143kg","144kg", "145kg","146kg","147kg","148kg","149kg","150kg", "151kg","152kg",
            "153kg","154kg","155kg","156kg", "157kg","158kg","159kg","160kg", "161kg","162kg",
            "163kg","164kg","165kg","166kg", "167kg","168kg","169kg","170kg","171kg","172kg",
            "173kg","174kg","175kg","176kg","177kg","178kg", "179kg","180kg","181kg","182kg",
            "183kg","184kg","185kg","186kg","187kg","188kg","189kg","190kg", "191kg","192kg",
            "193kg","194kg","195kg","196kg","197kg","198kg","199kg","200kg","201kg","202kg",
            "203kg","204kg","205kg","206kg","207kg","208kg", "209kg","210kg","211kg","212kg",
            "213kg","214kg","215kg","216kg","217kg","218kg","219kg","220kg", "221kg","222kg",
            "223kg","224kg","225kg","226kg","227kg","228kg","229kg","230kg"};



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.edit_care_activity);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);
        finishTv = findViewById(R.id.finish_tv);
        finishTv.setOnClickListener(this);

        allergyRl = findViewById(R.id.allergy_rl);
        allergyRl.setOnClickListener(this);
        allergyTv = findViewById(R.id.allergy_tv);

        sicknessRl = findViewById(R.id.sickness_rl);
        sicknessRl.setOnClickListener(this);
        sicknessTv = findViewById(R.id.sickness_tv);

        nameEt = findViewById(R.id.name_et);

        sexRl = findViewById(R.id.sex_rl);
        sexRl.setOnClickListener(this);
        sexTv = findViewById(R.id.sex_tv);

        ageRl = findViewById(R.id.age_rl);
        ageRl.setOnClickListener(this);
        ageTv = findViewById(R.id.age_tv);

        heightRl = findViewById(R.id.height_rl);
        heightRl.setOnClickListener(this);
        heightTv = findViewById(R.id.height_tv);

        weightRl = findViewById(R.id.weight_rl);
        weightRl.setOnClickListener(this);
        weightTv = findViewById(R.id.weight_tv);

        bloodRl = findViewById(R.id.blood_rl);
        bloodRl.setOnClickListener(this);
        bloodTv = findViewById(R.id.blood_tv);

        name = getIntent().getStringExtra("name");
        nameEt.setText(name);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back){
            finish();
        }
        if(view.getId() == R.id.finish_tv){
            putData();
        }
        if(view.getId() == R.id.allergy_rl){
            Intent intent = new Intent(activity,AddAllergyActivity.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.sickness_rl){
            Intent intent = new Intent(activity,AddSicknessActivity.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.sex_rl){
            showGenderDialog();
        }
        if(view.getId() == R.id.age_rl){
            openYear(DateParams.TYPE_YEAR, DateParams.TYPE_MONTH, DateParams.TYPE_DAY);
        }
        if(view.getId() == R.id.height_rl){
            type = 0;
            showWheelDialog();
        }
        if(view.getId() == R.id.weight_rl){
            type = 1;
            showWheelDialog();
        }
        if(view.getId() == R.id.blood_rl){
            type = 2;
            showWheelDialog();
        }

    }

    private void putData(){
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        map.put("height", getStringOrEmpty(heightTv.getText().toString().replace("cm","")));
        map.put("weight", getStringOrEmpty(weightTv.getText().toString().replace("kg","")));
        map.put("bloodType", getStringOrEmpty(bloodTv.getText().toString()));
        map.put("dateOfBirth", dateOfBirth);
        connect.getData(MyUrl.MESSAGELIST, map, new ConnectBack() {
            @Override
            public void success(String json) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = json;
                myHandler.sendMessage(msg);
                hideDialog();
            }

            @Override
            public void error(String json) {
                hideDialog();
                ToastUtil.errorToast(activity, "-1022");
            }
        });
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    BaseBean bean = JsonUtil.getBean(msg.obj.toString(), BaseBean.class);
                    loge(msg.obj.toString()+"");
                    if (bean.getResultCode().equals("200")) {
                        finish();
                    } else if (bean.getResultCode().equals("-10010")) {
                        showDialog();
                        sendLogin(new ConnectLoginBack() {
                            @Override
                            public void success(String json, String header) {
                                hideDialog();
                                RegisterBean registerBean = JsonUtil.getBean(json, RegisterBean.class);
                                if(registerBean.getResultCode().equals("200")){
                                    PrefeUtil.saveString(activity, PrefeKey.TOKEN, registerBean.getItem().getAuthorization());
                                    myHandler.sendEmptyMessage(2);

                                }else{
                                    Intent intent = new Intent(activity, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void error(String json) {
                                hideDialog();
                                Intent intent = new Intent(activity, LoginActivity.class);
                                startActivity(intent);
                            }
                        });
                    } else {
                        ToastUtil.errorToast(activity, bean.getResultCode());
                    }
                    break;
                case 2:
                    putData();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private String getStringOrEmpty(String str){
        return TextUtils.isEmpty(str)?"":str;
    }

    private void showGenderDialog() {
        if (genderChoiceDialog != null)
            getFragmentManager().beginTransaction().remove(genderChoiceDialog);
        genderChoiceDialog = new GenderChoiceDialog();
        genderChoiceDialog.setDialogClick(this);
        genderChoiceDialog.show(getFragmentManager(), "");
    }

    private void showWheelDialog() {
        if (wheelSelectDialog != null)
            getFragmentManager().beginTransaction().remove(wheelSelectDialog);
        wheelSelectDialog = new WheelSelectDialog();
        wheelSelectDialog.setDialogClick(this);
        Bundle bundle = new Bundle();
        if(type == 0){
            bundle.putStringArrayList("data",new ArrayList<>(Arrays.asList(heights)));
        }
        if(type == 1){
            bundle.putStringArrayList("data",new ArrayList<>(Arrays.asList(weights)));
        }
        if(type == 2){
            bundle.putStringArrayList("data",new ArrayList<>(Arrays.asList(bloodTypes)));
        }
        wheelSelectDialog.setArguments(bundle);
        wheelSelectDialog.show(getFragmentManager(), "");
    }


    @Override
    public void onSureClick(int index, String item) {
        if(type == 2){
            bloodTv.setText(item);
        }
        if(type == 0){
            heightTv.setText(item);
        }
        if(type == 1){
            weightTv.setText(item);
        }
    }

    @Override
    public void onGenderClick(int index, String gender) {
        sexTv.setText(gender);
    }

    public void openYear(int first,int center, int last){
        try {
            final Calendar todayCal = Calendar.getInstance();
            Calendar endCal = Calendar.getInstance();
            endCal.add(Calendar.YEAR,1);
            loge(endCal.getTime().toString());
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1870-00-00 00:00:00");
            new DatePickDialog.Builder()
                    .setTypes(first,center,last)
                    .setCurrentDate(todayCal.getTime())
                    .setStartDate(date)
                    .setEndDate(endCal.getTime())
                    .setOnSureListener(new OnSureListener() {
                        @Override
                        public void onSure(Date date) {
                            dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").format(date);
                            Calendar temp = Calendar.getInstance();
                            temp.setTime(date);
                            String age = (todayCal.get(Calendar.YEAR) - temp.get(Calendar.YEAR))+"歲";
                            ageTv.setText(age);

                        }
                    })
                    .show(this);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
