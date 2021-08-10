package com.example.payphone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText txtCountryCode, txtTelefono, txtCedula, txtReferencia,
            txtTotal, txtConTarifa, txtSinTarifa, txtTarifa;
    Button btnPago,btnCalcular;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCountryCode = findViewById(R.id.txtCountryCode);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCedula = findViewById(R.id.txtCedula);
        txtReferencia = findViewById(R.id.txtReferencia);
        txtTotal = findViewById(R.id.txtTotal);
        txtConTarifa = findViewById(R.id.txtConTarifa);
        txtSinTarifa = findViewById(R.id.txtSinTarifa);
        txtTarifa = findViewById(R.id.txtTarifa);
        btnCalcular=findViewById(R.id.btnCalcular);
        btnPago = findViewById(R.id.btnPago);
        requestQueue = Volley.newRequestQueue(this);
    }

    public void generartransaccion(View view) {
        jsonArrayRequest();
        btnPago.setEnabled(false);
    }
    public void calcular(View view){
        int total,conImpuesto,sinImpuesto,tarifa;
        total=Integer.parseInt(txtTotal.getText().toString());
        tarifa=Integer.parseInt(txtTarifa.getText().toString());
        conImpuesto=total-tarifa;
        txtConTarifa.setText(conImpuesto+"");
        txtSinTarifa.setText("0");
        btnPago.setEnabled(true);
    }

    private void jsonArrayRequest() {

        String strjson = "{" +
                "\"phoneNumber\":\"" + txtTelefono.getText() + "\"," +
                "\"countryCode\":\"" + txtCountryCode.getText() + "\"," +
                "\"clientUserId\":\"" + txtCedula.getText() + "\"," +
                "\"reference\":\"" + txtReferencia.getText() + "\"," +
                "\"amount\":\"" + txtTotal.getText() + "\"," +
                "\"amountWithTax\":\"" + txtConTarifa.getText() + "\"," +
                "\"amountWithoutTax\":\"" + txtSinTarifa.getText() + "\"," +
                "\"tax\":\"" + txtTarifa.getText() + "\"," +
                "\"clientTransactionId\":\"" + new Random().nextInt() + "\"" +
                "}";

        System.out.println(strjson);
        try {
            JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                    "https://pay.payphonetodoesposible.com/api/Sale", new JSONObject(strjson),
                    new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(MainActivity.this, "Su transacción se ha realizado con éxito!",Toast.LENGTH_LONG).show();
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Override
                public HashMap<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> cabecera = new HashMap<>();
                    cabecera.put("Content-Type", "application/json");
                    cabecera.put("Accept", "application/json");
                    cabecera.put("Authorization", "Bearer UMjc89kSZwZIAA8RGqhd36efIqtMUviCp8V-PImRFwADvqMetHJ3-iHOUgXzAi7gsCLx1-JAWc5qlnkaE8bd--tvIbzhzLtoA9a59IolkOWpjSiRjCTexL-8e-WTstelPY8u68szRjjIuFSX8-mHPndeXpOBuBJi5yUHsCSVYR-PcvF_AYgYytyNM1MtTw41t6JOkGL5ytc99yje5cEWuMPAj2tJiHvaHbO-S2NB5qdBhffmn-ph42t9xbUod4xTMbINziV6PtkKuhyNZsuNb04e1o8aRNJeYCpi7vAqlzz2Z6Ja8WN3KyPZspmDkZFIJHdaZzZhFKqz6wB8BmYoH9QbeSE");
                    return cabecera;
                }
            };requestQueue.add(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}










