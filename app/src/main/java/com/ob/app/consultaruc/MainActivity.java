package com.ob.app.consultaruc;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    EditText txtBuscar = null;
    TextView lblResultado = null;
    ProgressDialog loading = null;
    TableLayout tblResultado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtBuscar = (EditText) findViewById(R.id.txtBuscar);
        Button btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(this);
        Button btnNuevo = (Button) findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(this);
        lblResultado = (TextView) findViewById(R.id.lblResultado);
        tblResultado = (TableLayout) findViewById(R.id.tblResultado);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuApi:
                //
                return true;
            case R.id.mnuAutor:
                alerta("Desarrollado por 0B");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        switch (btn.getId()){
            case R.id.btnNuevo:
                nuevo(btn);
                break;
            case R.id.btnBuscar:
                buscar(btn);
                break;
        }
    }

    private void getResultByRuc(String ruc) {
        loading = new ProgressDialog(this);
        loading.setMessage("Buscando RUC: ".concat(ruc).concat("..."));
        if(!loading.isShowing()){
            loading.show();
        }
        //new getData().execute(ruc);
        HttpUtil.get(ruc, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }
                try {
                    JSONObject data = Util.getJson(response);
                    if(data.getBoolean("error")){
                        Toast.makeText(getBaseContext(), data.getString("message"), Toast.LENGTH_SHORT).show();
                    }else{
                        tblResultado.setColumnShrinkable(1, true);
                        tblResultado.removeAllViews();
                        TableRow tblRow = null;
                        TextView lblTitulo = null, lblValor = null;
                        View v = new View(getBaseContext());
                        v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                        v.setBackgroundColor(Color.rgb(51, 51, 51));

                        tblRow = (TableRow) getLayoutInflater().inflate(R.layout.layout_resultado_row, null);
                        lblTitulo = (TextView) tblRow.findViewById(R.id.lblTitulo);
                        lblValor = (TextView) tblRow.findViewById(R.id.lblValor);
                        lblTitulo.setText("RUC");
                        lblValor.setText(data.getString("Ruc"));
                        tblResultado.addView(tblRow);
                        tblResultado.addView(getDivider());

                        tblRow = (TableRow) getLayoutInflater().inflate(R.layout.layout_resultado_row, null);
                        lblTitulo = (TextView) tblRow.findViewById(R.id.lblTitulo);
                        lblValor = (TextView) tblRow.findViewById(R.id.lblValor);
                        lblTitulo.setText("Razon Social");
                        lblValor.setText(data.getString("Nombre"));
                        tblResultado.addView(tblRow);
                        tblResultado.addView(getDivider());

                        tblRow = (TableRow) getLayoutInflater().inflate(R.layout.layout_resultado_row, null);
                        lblTitulo = (TextView) tblRow.findViewById(R.id.lblTitulo);
                        lblValor = (TextView) tblRow.findViewById(R.id.lblValor);
                        lblTitulo.setText("Nombre Comercial");
                        lblValor.setText(data.getString("NombreComercial"));
                        tblResultado.addView(tblRow);
                        tblResultado.addView(getDivider());

                        tblRow = (TableRow) getLayoutInflater().inflate(R.layout.layout_resultado_row, null);
                        lblTitulo = (TextView) tblRow.findViewById(R.id.lblTitulo);
                        lblValor = (TextView) tblRow.findViewById(R.id.lblValor);
                        lblTitulo.setText("Tipo de Contribuyente");
                        lblValor.setText(data.getString("TipoContribuyente"));
                        tblResultado.addView(tblRow);
                        tblResultado.addView(v);

                        tblRow = (TableRow) getLayoutInflater().inflate(R.layout.layout_resultado_row, null);
                        lblTitulo = (TextView) tblRow.findViewById(R.id.lblTitulo);
                        lblValor = (TextView) tblRow.findViewById(R.id.lblValor);
                        lblTitulo.setText("F. Inscripcion");
                        lblValor.setText(data.getString("FechaInscripcion"));
                        tblResultado.addView(tblRow);
                        tblResultado.addView(getDivider());

                        tblRow = (TableRow) getLayoutInflater().inflate(R.layout.layout_resultado_row, null);
                        lblTitulo = (TextView) tblRow.findViewById(R.id.lblTitulo);
                        lblValor = (TextView) tblRow.findViewById(R.id.lblValor);
                        lblTitulo.setText("Estado");
                        lblValor.setText(data.getString("EstadodelContribuyente"));
                        tblResultado.addView(tblRow);
                        tblResultado.addView(getDivider());

                        tblRow = (TableRow) getLayoutInflater().inflate(R.layout.layout_resultado_row, null);
                        lblTitulo = (TextView) tblRow.findViewById(R.id.lblTitulo);
                        lblValor = (TextView) tblRow.findViewById(R.id.lblValor);
                        lblTitulo.setText("Direccion");
                        lblValor.setText(data.getString("DireccionDelDomicilioFiscal"));
                        tblResultado.addView(tblRow);
                    }
                } catch (JSONException e1) {
                    Log.e("Resultado ERROR", e1.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }
                try {
                    JSONObject data = Util.getJson(errorResponse);
                    Log.i("ERROR", data.toString());
                } catch (JSONException e2) {
                    Log.e("ERROR ERROR", e2.getMessage());
                }
            }
        });
    }

    private View getDivider(){
        View v = new View(getBaseContext());
        v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
        v.setBackgroundColor(Color.rgb(51, 51, 51));
        return v;
    }

    private void nuevo(Button btn){
        //Toast.makeText(this, "Nuevo", Toast.LENGTH_SHORT).show();
        tblResultado.removeAllViews();
        lblResultado.setText("Resultado");
        txtBuscar.setText("");
        txtBuscar.requestFocus();
    }

    private void buscar(Button btn){
        //Toast.makeText(this, "Buscar", Toast.LENGTH_SHORT).show();
        String ruc = txtBuscar.getText().toString().trim();
        if(ruc.isEmpty()){
            lblResultado.setText("Debe ingresar un numero de RUC");
            txtBuscar.requestFocus();
        }else if(!isValidRuc(ruc)){
            lblResultado.setText("El numero de RUC es invalido");
            alerta("El numero de RUC es invalido");
        } else {
            lblResultado.setText("Buscando RUC: ".concat(ruc));
            Util.hideKeyboard(txtBuscar);
            getResultByRuc(ruc);
        }
    }

    private boolean isValidRuc(String ruc){
        return ruc.length()==11;
    }

    private void alerta(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
