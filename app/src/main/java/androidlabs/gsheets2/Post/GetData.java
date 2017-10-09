package androidlabs.gsheets2.Post;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import androidlabs.gsheets2.R;
import androidlabs.gsheets2.model.Employees;


public class GetData extends AppCompatActivity {
ArrayAdapter<String> aa;
    ListView listView;
  public   List<String> al= new ArrayList<String>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);

        new SendRequest().execute();

        final List<String> li = new ArrayList<String>();

        li.add("9746285380");
        li.add("9747878127");

              al.add("Employee Name");

        final ListView mListView = (ListView) findViewById(R.id.lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                al.toArray(new String[al.size()]));
        mListView.setAdapter(adapter);


//        listView= (ListView) findViewById(R.id.lv);
//        al.add("hai");
//        String[] s={"hai"};
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,s);
//        listView.setAdapter(adapter);

    }




    public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{
                //Change your web app deployed URL or or u can use this for attributes (name, country)
                URL url = new URL("https://script.google.com/macros/s/AKfycbxPFYNZHik-q9-8J_krLrvf-ekdundjDTzKFJ2k3fgRLbC3e-Q/exec");
                // URL url = new URL("http://192.168.56.1:8084/AnroidServiceProvider/Login");

                JSONObject postDataParams = new JSONObject();

                //int i;
                //for(i=1;i<=70;i++)


                //    String usn = Integer.toString(i);

                String id="1PE383yxn21PcaEJSnCr1zyCoYWUnTKAgJuddrIYpuVs";

//                postDataParams.put("name",name);
                postDataParams.put("sheet","sheet1");
                postDataParams.put("id",id);


                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        Log.e(" buffer1:",sb.toString());
                        break;
                    }
                    String json =sb.toString();


                    JSONObject jObject = new JSONObject(json);
                    JSONArray jArray = jObject.getJSONArray("records");
                    ArrayList<Employees> emplist=new ArrayList<>();
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObj = jArray.getJSONObject(i);
                        Log.e("id:",jObj.getString("id"));
                        Log.e("name:",jObj.getString("name"));
                       // emplist.add(new Employees(jObj.getString("id"),jObj.getString("name")));
                        al.add("jdk");
                        System.out.println(i + " id : " + jObj.getString("id"));
                        System.out.println(i + " name : " + jObj.getString("name"));
                      //  System.out.println(i + " att2 : " + jObj.getBoolean("att2"));
                    }

                    Log.e("Strig buffer",sb.toString());
                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();

        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        Log.e("jsonobject",params.toString());
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");
            Log.e("result1:",result.toString());
            result.append(URLEncoder.encode(key, "UTF-8"));
            Log.e("result1:",result.toString());
            result.append("=");
            Log.e("result2:",result.toString());
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
            Log.e("result3:",result.toString());
        }
        return result.toString();
    }
}
