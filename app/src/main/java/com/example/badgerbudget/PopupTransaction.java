package com.example.badgerbudget;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class PopupTransaction extends AppCompatActivity{


    String sMonth;
    String sYear;
    String eMonth;
    String eYear;
    ArrayList<String[]> transInRange;
    String passable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popuptransactionwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout( (int) (width*.8), (int)(height*.6));

        Bundle un = getIntent().getExtras();
        sMonth = un.getString("startMonth");
        sYear = un.getString("startYear");
        eMonth = un.getString("endMonth");
        eYear = un.getString("endYear");
        passable = un.getString("userID");

        Transaction transaction = new Transaction(passable);

        // call transaction query here (if month/year are valid)
        transInRange =  (ArrayList<String[]>)
                (transaction.setListInRange(sMonth, sYear, eMonth, eYear, "all").clone());

        ArrayList<Map<String, String>> transList = buildTrans();

        String[] from = new String[] {"title", "subTit"};
        int[] to = new int[]{android.R.id.text1,android.R.id.text2};
        SimpleAdapter adapter = new SimpleAdapter(this, transList, android.R.layout.simple_list_item_2,from,to);

        ListView listview = (ListView) findViewById(R.id.transactionListView);
        listview.setAdapter(adapter);

    }


    public ArrayList<Map<String, String>> buildTrans(){
        // orderID [0]
        // type [1]
        // amount[2]
        // date [3]
        // month [4]
        // year [5]
        // category [6]
        // note [7]

        ArrayList<Map<String, String>> transList = new ArrayList<Map<String, String>>();

        for(String[] eachTrans: transInRange){

            String title = "[" + eachTrans[0] + "] " + eachTrans[1] + " - " + eachTrans[2];
            String subTit = eachTrans[3] + ": " + eachTrans[6];

            if(!(eachTrans[7].equals("NULL"))){
                subTit = subTit + " *Note: " + eachTrans[7];
            }

            transList.add(putData(title, subTit));

        }
        return transList;

    }


    private LinkedHashMap<String, String> putData(String title, String subTit) {
        LinkedHashMap<String, String> item = new LinkedHashMap<String, String>();
        item.put("title", title);
        item.put("subTit", subTit);
        return item;
    }
}
