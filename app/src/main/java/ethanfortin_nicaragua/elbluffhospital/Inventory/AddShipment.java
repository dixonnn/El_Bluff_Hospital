package ethanfortin_nicaragua.elbluffhospital.Inventory;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class AddShipment extends AppCompatActivity {

    private EditText et_drugName;
    private EditText et_drugId;
    private EditText et_drugQuant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shipment);


    }

    public void createShipment(View v) {

        et_drugName = (EditText)findViewById(R.id.addName);
        et_drugId = (EditText)findViewById(R.id.addId);
        et_drugQuant = (EditText)findViewById(R.id.addQuantity);

        boolean cancel = false;
        View focusView = null;

        // Verify drug name
        final String s_drugName = et_drugName.getText().toString();
        if(TextUtils.isEmpty(s_drugName)) {
            et_drugName.setError("atención");
            focusView = et_drugName;
            cancel = true;
        }

        // Verify drug id
        final String s_drugId = et_drugId.getText().toString();
        if(TextUtils.isEmpty(s_drugId)) {
            et_drugId.setError("atención");
            focusView = et_drugId;
            cancel = true;
        }

        // Verify drug quantity
        final String s_drugQuant = et_drugQuant.getText().toString();
        if(TextUtils.isEmpty(s_drugQuant)) {
            et_drugQuant.setError("atención");
            focusView = et_drugQuant;
            cancel = true;
        }

        if(cancel) {
            focusView.requestFocus();
        }
        else {
            finish();
        }

        class addShipmentRow extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddShipment.this,"Añadiendo...","Espera, por favor",false,false);
            }

            // Once JSON received correctly, parse and display it
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                int duration = Toast.LENGTH_LONG;
                Context context = getApplicationContext();
                String text1 = "Medicina nueva archivada.";
                Toast toast1 = Toast.makeText(context, text1, duration);
                toast1.show();

            }

            // In here, split between argChoice Value (1 or 2)
            protected String doInBackground(Void... params) {

                RequestHandler reqHan = new RequestHandler();
                HashMap<String, String> map = new HashMap<>();

                map.put(ConnVars.TAG_SHIPMENT_DRUGID, s_drugId);
                map.put(ConnVars.TAG_SHIPMENT_DRUGNAME, s_drugName);
                map.put(ConnVars.TAG_SHIPMENT_SHIPQUANT, s_drugQuant);

                String res = reqHan.sendPostRequest(ConnVars.URL_ADD_SHIPMENT, map);
                return res;
            }
        }

        addShipmentRow asr = new addShipmentRow();
        asr.execute();
    }
}
