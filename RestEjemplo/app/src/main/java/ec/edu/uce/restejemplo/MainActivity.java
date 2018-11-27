package ec.edu.uce.restejemplo;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    TextView sal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sal = (TextView)findViewById(R.id.salida);
        getData();
    }

    public void getData(){

        String sql = "https://thereportoftheweek-api.herokuapp.com/reports";
        //Permite el acceso a algunos dispositivos Android
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection conn;

        try {
            url = new URL(sql);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            String Json = "";
            while((inputLine = in.readLine()) !=  null){

                response.append(inputLine);
            }
            Json = response.toString();
            JSONArray jsonArr  = null;
            jsonArr = new JSONArray(Json);
            String mensaje = "";
            for(int i=0; i<jsonArr.length(); i++){
                JSONObject jsonObj = jsonArr.getJSONObject(i);

                Log.d("salida", jsonObj.getString("product"));
                mensaje += "PRODUCTO: "+i+ " "+ jsonObj.getString("product")+ "\n";
            }
            sal.setText(mensaje);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
