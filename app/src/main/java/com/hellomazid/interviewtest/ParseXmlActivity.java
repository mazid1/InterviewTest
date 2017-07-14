package com.hellomazid.interviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ParseXmlActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<HashMap<String, String>> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_xml);
        setTitle("Parse XML");

        listView = (ListView) findViewById(R.id.list_xml);
        messageList = new ArrayList<>();

        parseData(readXmlAssets("sample.xml"));

        ListAdapter adapter = new SimpleAdapter(this, messageList,
                R.layout.list_item, new String[]{"title", "message"},
                new int[]{R.id.msg_title, R.id.msg_message});
        listView.setAdapter(adapter);
    }

    private Element readXmlAssets(String fileName) {
        Element element = null;
        try {
            InputStream inputStream = this.getAssets().open(fileName);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);

            element = doc.getDocumentElement();
            element.normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return element;
    }

    private void parseData(Element element) {
        String msgTitle = element.getElementsByTagName("title").item(0).getTextContent();
        String msgMessage = element.getElementsByTagName("message").item(0).getTextContent();

        HashMap<String, String> tempHashMap = new HashMap<>();
        tempHashMap.put("title", msgTitle);
        tempHashMap.put("message", msgMessage);
        messageList.add(tempHashMap);

        NodeList nodeList = element.getElementsByTagName("GlossDiv");

        for(int i=0; i<nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element currentElement = (Element) node;
                msgTitle = currentElement.getElementsByTagName("title").item(0).getTextContent();
                msgMessage = currentElement.getElementsByTagName("message").item(0).getTextContent();
                tempHashMap = new HashMap<>();
                tempHashMap.put("title", msgTitle);
                tempHashMap.put("message", msgMessage);
                messageList.add(tempHashMap);
            }
        }
    }

}
