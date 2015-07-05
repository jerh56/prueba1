package com.example.opensysblog.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.io.IOException;

import org.ksoap2.SoapFault;

import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import android.os.AsyncTask;

import android.util.Log;

import android.app.Activity;



public class MainActivity extends ActionBarActivity {

    private static final String SOAP_ACTION = "http://187.160.239.179/wsxPortalMobile/wsXportalMobile.asmx/GetProjects";
    private static final String METHOD_NAME = "GetProjects";
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String URL = "http://187.160.239.179/wsxPortalMobile/wsXportalMobile.asmx?WSDL";
    private TextView tv;
    private String response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Button btn = (Button)findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast toast = Toast.makeText(getApplicationContext(), "Hola", Toast.LENGTH_LONG);
                //toast.show();
                //txt.setText("Fahrenheit value can not be empty.");

            }


        });*/

        tv= (TextView)findViewById(R.id.editText);
        myAsyncTask myRequest = new myAsyncTask();
        myRequest.execute();


    }

   private class myAsyncTask extends AsyncTask<Void, Void, Void>    {


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            tv.setText(response);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("IdUser", "1");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(URL);

            httpTransport.debug = true;
            try {
                httpTransport.call(SOAP_ACTION, envelope);
            } catch (HttpResponseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } //send request
            SoapObject result = null;
            try {
                result = (SoapObject)envelope.getResponse();
            } catch (SoapFault e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Log.d("App",""+result.getProperty(1).toString());
            response = result.getProperty(1).toString();
            return null;
        }
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


    //SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);


}


