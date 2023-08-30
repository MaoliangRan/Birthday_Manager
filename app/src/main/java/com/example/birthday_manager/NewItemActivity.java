package com.example.birthday_manager;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class NewItemActivity extends AppCompatActivity implements OnClickListener {

    private Button newitem_back;
    private TextView newitem_backt;
    private TextView newitem_date_show;
    private Button newitem_face;
    private Button newitem_date;
    private Button newitem_save;
    private EditText newitem_name;
    private EditText newitem_remark;
    private RadioGroup newitem_remind_time;
    private RadioGroup newitem_sex;
    private RadioButton male, female, advance8, advance12, intraday8, intraday12;
    private int sex_choice = 0, remind_time_choice = 2;

    private String remark;
    private String name;
    int myear = 0, mmonth = 0, mday = 0;

    private Bitmap face;
    Context context;
    SQLiteDatabase db;
    Cursor cursor = null;
    byte[] img;

    // 定义显示时间控件
    private Calendar calendar;// 用来装日期的
    private DatePickerDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        newitem_back = (Button) findViewById(R.id.newitem_back);
        newitem_backt = (TextView) findViewById(R.id.newitem_backt);
        newitem_date_show = (TextView) findViewById(R.id.newitem_date_show);
        newitem_face = (Button) findViewById(R.id.newitem_face);
        newitem_date = (Button) findViewById(R.id.newitem_date);
        newitem_save = (Button) findViewById(R.id.newitem_save);
        newitem_name = (EditText) findViewById(R.id.newitem_name);
        newitem_remark = (EditText) findViewById(R.id.newitem_remark);
        newitem_remind_time = (RadioGroup) findViewById(R.id.newitem_remind_time);
        newitem_sex = (RadioGroup) findViewById(R.id.newitem_sex);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        advance8 = (RadioButton) findViewById(R.id.advance8);
        advance12 = (RadioButton) findViewById(R.id.advance12);
        intraday8 = (RadioButton) findViewById(R.id.intraday8);
        intraday12 = (RadioButton) findViewById(R.id.intraday12);


        newitem_back.setOnClickListener(this);
        newitem_backt.setOnClickListener(this);
        newitem_face.setOnClickListener(this);
        newitem_date.setOnClickListener(this);
        newitem_save.setOnClickListener(this);
        male.setOnClickListener(this);
        female.setOnClickListener(this);
        //设置默认Radiobutton
        newitem_remind_time.check(R.id.intraday8);
        newitem_sex.check(R.id.male);
        sex_choice = 0;
        remind_time_choice = 2;

        newitem_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male)
                    sex_choice = 0;
                else if (checkedId == R.id.female)
                    sex_choice = 1;

                //Toast.makeText(getApplicationContext(), "gg", Toast.LENGTH_SHORT).show();
            }
        });
        newitem_remind_time.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.advance8)
                    remind_time_choice = 0;
                else if (checkedId == R.id.advance12)
                    remind_time_choice = 1;
                else if (checkedId == R.id.intraday8)
                    remind_time_choice = 2;
                else if (checkedId == R.id.intraday12)
                    remind_time_choice = 3;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newitem_back:
                finish();
                break;
            case R.id.newitem_backt:
                finish();
                break;
            case R.id.newitem_face:
                choose();
                break;
            case R.id.newitem_date:
                choosedate();
                break;
            case R.id.newitem_save:
                submititem();
                break;
            case R.id.male:
                newitem_face.setBackgroundResource(R.drawable.head_male);
                break;
            case R.id.female:
                newitem_face.setBackgroundResource(R.drawable.head_female);
                break;
        }
    }

    public void submititem() {
        remark = newitem_remark.getText().toString();
        name = newitem_name.getText().toString();

        calendar = Calendar.getInstance();
        if (calendar.get(Calendar.YEAR) < myear) {
            myear = 1;
        } else if (calendar.get(Calendar.YEAR) - 0 == myear && calendar.get(Calendar.MONTH) < mmonth) {
            myear = 1;
        } else if (calendar.get(Calendar.YEAR) - 0 == myear && calendar.get(Calendar.MONTH) - 0 == mmonth && calendar.get(Calendar.DAY_OF_MONTH) < mday) {
            myear = 1;
        }


        if (name.equals("")) {
            Toast.makeText(getApplicationContext(), "姓名不能为空！", Toast.LENGTH_SHORT).show();
        } else if (myear == 1) {
            Toast.makeText(getApplicationContext(), "生日不能大于今天！", Toast.LENGTH_SHORT).show();
        } else if (myear == 0) {
            Toast.makeText(getApplicationContext(), "请选择生日！", Toast.LENGTH_SHORT).show();
        } else {
            context = this.getApplicationContext();
            db = new DBhelper(context).getWritableDatabase();

            String birthday = myear + "年" + mmonth + "月" + mday + "日";

            if (face == null) {
                ContentValues cv = new ContentValues();
                cv.put("remind_time", remind_time_choice);
                cv.put("name", name);
                cv.put("birthday", birthday);
                cv.put("year", myear);
                cv.put("month", mmonth);
                cv.put("day", mday);
                cv.put("sex", sex_choice);
                cv.put("remarkinfo", remark);
                db.insert("person", null, cv);
                Toast.makeText(getApplicationContext(), "已保存信息", Toast.LENGTH_SHORT).show();
                db.close();
                finish();
            } else {
                ContentValues cv = new ContentValues();
                cv.put("remind_time", remind_time_choice);
                cv.put("name", name);
                cv.put("birthday", birthday);
                cv.put("year", myear);
                cv.put("month", mmonth);
                cv.put("day", mday);
                cv.put("sex", sex_choice);
                cv.put("remarkinfo", remark);
                cv.put("face", img);
                db.insert("person", null, cv);
                Toast.makeText(getApplicationContext(), "已保存信息", Toast.LENGTH_SHORT).show();
                db.close();
                finish();
            }

        }
    }

    //选择图片
    public void choose() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 66);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 66 && resultCode == RESULT_OK && null != data) {

            Uri selectedImage0 = data.getData();
            crop(selectedImage0);
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//            newitem_face.setBackground(new BitmapDrawable(getRoundedCornerBitmap(BitmapFactory.decodeFile(picturePath))));
        }
        if (requestCode == 88 && resultCode == RESULT_OK && null != data) {

            if (resultCode == RESULT_OK) {
                // 拿到剪切数据
                Bitmap bmap = data.getParcelableExtra("data");
                face = getRoundedCornerBitmap(bmap);
                newitem_face.setBackground(new BitmapDrawable(face));

                img = tobytearr(face);
            }
        }
    }

    //获取圆形图片
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        /* 去锯齿 */
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        // 保证是方形，并且从中心画
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int w;
        int deltaX = 0;
        int deltaY = 0;
        if (width <= height) {
            w = width;
            deltaY = height - w;
        } else {
            w = height;
            deltaX = width - w;
        }
        final Rect rect = new Rect(deltaX, deltaY, w, w);
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        // 圆形，所有只用一个
        int radius = (int) (Math.sqrt(w * w * 2.0d) / 2);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    //裁剪图片
    public void crop(Uri mUri) {

        Intent intent = new Intent();

        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(mUri, "image/*");// mUri是已经选择的图片Uri
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);// 输出图片大小
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 88);
    }

    public byte[] tobytearr(Bitmap face) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        face.compress(Bitmap.CompressFormat.PNG, 100, baos);
        //Object[] args = new Object[] {baos.toByteArray() };
        return baos.toByteArray();
    }

    public void choosedate() {
        calendar = Calendar.getInstance();
        dialog = new DatePickerDialog(NewItemActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //System.out.println("年-->" + year + "月-->" + monthOfYear + "日-->" + dayOfMonth);
                        newitem_date_show.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                        myear = year;
                        mmonth = monthOfYear + 1;
                        mday = dayOfMonth;
                    }
                }, calendar.get(Calendar.YEAR), calendar
                .get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH));
        dialog.show();

    }
}
