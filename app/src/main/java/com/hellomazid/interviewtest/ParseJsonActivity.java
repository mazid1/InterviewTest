package com.hellomazid.interviewtest;

import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ParseJsonActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<HashMap<String, String>> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_json);
        setTitle("Parse JSON");

        listView = (ListView) findViewById(R.id.list_json);
        messageList = new ArrayList<>();

        parseData(readJsonAssets("sample.json"));

        ListAdapter adapter = new SimpleAdapter(this, messageList,
                R.layout.list_item, new String[]{"title", "message"},
                new int[]{R.id.msg_title, R.id.msg_message});
        listView.setAdapter(adapter);
    }

    private JSONObject readJsonAssets(String fileName) {
        JSONObject jsonObject = null;
        try {
            String json;
            InputStream inputStream = this.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer);
            jsonObject = new JSONObject(json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void parseData(JSONObject jsonObject) {
        try {
            JSONObject glossary = jsonObject.getJSONObject("glossary");

            String msgTitle = glossary.getString("title");
            String msgMessage = glossary.getString("message");

            HashMap<String, String> tempHashMap = new HashMap<>();
            tempHashMap.put("title", msgTitle);
            tempHashMap.put("message", msgMessage);
            messageList.add(tempHashMap);

            JSONArray glossDiv = glossary.getJSONArray("GlossDiv");

            for(int i=0; i<glossDiv.length(); i++) {
                msgTitle = glossDiv.getJSONObject(i).getString("title");
                msgMessage = glossDiv.getJSONObject(i).getString("message");
                tempHashMap = new HashMap<>();
                tempHashMap.put("title", msgTitle);
                tempHashMap.put("message", msgMessage);
                messageList.add(tempHashMap);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
