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
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class EdituserActivity extends AppCompatActivity implements OnClickListener {

    private Button edituser_face;
    private Button edituser_date;
    private Button edituser_save;
    private EditText edituser_name;
    private TextView edituser_date_show;
    private Button edituser_backbtn;
    private TextView edituser_backview;

    Bitmap face = null;
    String name;
    int myear, mmonth, mday;

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
        setContentView(R.layout.activity_edituser);

        edituser_face = (Button) findViewById(R.id.user_face);
        edituser_date = (Button) findViewById(R.id.user_date);
        edituser_save = (Button) findViewById(R.id.save_user);
        edituser_backbtn = (Button) findViewById(R.id.edituser_back);
        edituser_backview = (TextView) findViewById(R.id.edituser_backt);
        edituser_name = (EditText) findViewById(R.id.user_name);
        edituser_date_show = (TextView) findViewById(R.id.edituser_date_show);

        edituser_face.setOnClickListener(this);
        edituser_date.setOnClickListener(this);
        edituser_save.setOnClickListener(this);
        edituser_backbtn.setOnClickListener(this);
        edituser_backview.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_face:
                choose();
                break;
            case R.id.user_date:
                choosedate();
                break;
            case R.id.save_user:
                submititem();
                break;
            case R.id.edituser_back:
                finish();
                break;
            case R.id.edituser_backt:
                finish();
                break;
        }
    }

    public void choosedate() {
        calendar = Calendar.getInstance();
        dialog = new DatePickerDialog(EdituserActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        System.out.println("年-->" + year + "月-->" + monthOfYear + "日-->" + dayOfMonth);
                        edituser_date_show.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                        myear = year;
                        mmonth = monthOfYear + 1;
                        mday = dayOfMonth;
                    }
                }, calendar.get(Calendar.YEAR), calendar
                .get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH));
        dialog.show();

    }

    public void submititem() {
        name = edituser_name.getText().toString();
        if (name.equals("")) {
            Toast.makeText(getApplicationContext(), "姓名不能为空！", Toast.LENGTH_SHORT).show();
        } else if (myear == 0) {
            Toast.makeText(getApplicationContext(), "生日尚未填写！", Toast.LENGTH_SHORT).show();
        } else {
            context = getApplicationContext();
            db = new DBhelper(context).getWritableDatabase();
            if (face == null) {
                String birthday = myear + "年" + mmonth + "月" + mday + "日";
                ContentValues cv = new ContentValues();
                cv.put("name", name);
                cv.put("birthday", birthday);
                db.update("user_info", cv, null, null);
                //Toast.makeText(getApplicationContext(), "已保存信息", Toast.LENGTH_SHORT).show();
                db.close();
                finish();
            } else {
                String birthday = myear + "年" + mmonth + "月" + mday + "日";
                ContentValues cv = new ContentValues();
                cv.put("name", name);
                cv.put("birthday", birthday);
                cv.put("face", img);
                cv.put("status", 0);
                db.update("user_info", cv, null, null);
                Toast.makeText(getApplicationContext(), "已保存信息", Toast.LENGTH_SHORT).show();
                db.close();
                finish();
            }
            //Toast.makeText(getApplicationContext(), "已保存信息", Toast.LENGTH_SHORT).show();
            db.close();
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
                edituser_face.setBackground(new BitmapDrawable(face));

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                face.compress(Bitmap.CompressFormat.PNG, 100, baos);
                img = baos.toByteArray();
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
}
