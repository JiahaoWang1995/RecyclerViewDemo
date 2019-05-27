package com.example.listview_demo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements ListviewDemoDialogFragment.NoticeDialogListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> myTitleSet = new ArrayList<>();
    private List<String> myInfoSet = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchDialog();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(myTitleSet, myInfoSet);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void showSearchDialog() {
        DialogFragment newDialog = new ListviewDemoDialogFragment();
        newDialog.show(getSupportFragmentManager(), "Search in Dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(String search_content) {
//        Context context = getApplicationContext();
//        int duration = Toast.LENGTH_SHORT;
//        Toast toast = Toast.makeText(context, search_content, duration);
//        toast.show();
        new GetSearchResultTask().execute("https://search.bilibili.com/all?keyword=" + search_content);

    }

    private class GetSearchResultTask extends AsyncTask<String, Void, List<String[]>> {

        @Override
        protected List<String[]> doInBackground(String... url) {
            String encoding = "UTF-8";
            return getPageSource(url[0], encoding);
        }

        protected void onPostExecute(List<String[]> result) {
            myTitleSet.clear();
            myInfoSet.clear();
            for (int i=0;i<result.size();i++) {
                myTitleSet.add(result.get(i)[0]);
                myInfoSet.add(result.get(i)[1]);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    private List<String[]> getPageSource(String pageUrl, String encoding) {
        List<String[]> mString = new ArrayList<>();
        try {
            Document pageSource = Jsoup.connect(pageUrl).get();
            Elements videoGroup = pageSource.getElementsByClass("video matrix");
            Iterator iterator = videoGroup.iterator();
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                mString.add(new String[]{
                        element.select("a").attr("title").trim(),
                        element.getElementsByClass("des hide").first().text()});
//                Log.d("TEST", element.getElementsByClass("des hide").first().text());
//                mString.add(element.select("a").attr("title").trim());
            }

        } catch (Exception ex) {
            Log.d("TEST", ex.toString());
        }
        return mString;
    }
}
