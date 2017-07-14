package com.hellomazid.interviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class ParseHtmlActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<HashMap<String, String>> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_html);
        setTitle("Parse HTML");

        listView = (ListView) findViewById(R.id.list_html);
        messageList = new ArrayList<>();

        parseData(readHtmlAssets("sample.html"));

        ListAdapter adapter = new SimpleAdapter(this, messageList,
                R.layout.list_item, new String[]{"title", "message"},
                new int[]{R.id.msg_title, R.id.msg_message});
        listView.setAdapter(adapter);
    }

    private Document readHtmlAssets(String fileName) {
        Document document = null;
        try {
            InputStream inputStream = this.getAssets().open(fileName);
            document = Jsoup.parse(inputStream, "UTF-8", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    private void parseData(Document document) {
        Elements tables = document.getElementsByTag("table");

        for(Element table : tables) {
            ArrayList<String> tableHeaders = new ArrayList<>();

            Element thead = table.getElementsByTag("thead").first();
            Element tr = thead.getElementsByTag("tr").first();
            Elements ths = tr.getElementsByTag("th");

            for(Element th : ths) {
                tableHeaders.add(th.text());
            }

            Element tbody = table.getElementsByTag("tbody").first();
            Elements trs = tbody.getElementsByTag("tr");

            for(Element trow : trs) {
                Elements tds = trow.getElementsByTag("td");

                HashMap<String, String> tempHashMap = new HashMap<>();

                for(int i=0; i<tableHeaders.size(); i++) {
                    Element td = tds.eq(i).first();

                    if(tableHeaders.get(i).equalsIgnoreCase("title")) {
                        String msgTitle = td.text();
                        tempHashMap.put("title", msgTitle);
                    }
                    else if(tableHeaders.get(i).equalsIgnoreCase("message")) {
                        String msgMessage = td.text();
                        tempHashMap.put("message", msgMessage);
                    }

                    if(tempHashMap.get("title")!=null && tempHashMap.get("message")!=null) {
                        messageList.add(tempHashMap);
                        tempHashMap = new HashMap<>();
                    }
                }
            }
        }
    }
}
