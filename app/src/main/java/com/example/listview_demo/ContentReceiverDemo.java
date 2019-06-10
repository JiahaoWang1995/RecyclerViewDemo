package com.example.listview_demo;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ContentReceiverDemo extends AppCompatActivity {
    public static final Uri uri = Uri.parse("content://com.example.selflearning.MyContentProvider");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_receiver_demo);
        setTitle(getIntent().getStringExtra("title"));

        Cursor cursor = getContentResolver().query(uri, null, null,
                null, null);
        cursor.moveToFirst();
        for (int i = 0; i<cursor.getCount(); i++) {
            String value = cursor.getString(cursor.getColumnIndex("name"));
            Toast.makeText(ContentReceiverDemo.this, value,
                    Toast.LENGTH_SHORT).show();
            cursor.moveToNext();
        }

    }
}
