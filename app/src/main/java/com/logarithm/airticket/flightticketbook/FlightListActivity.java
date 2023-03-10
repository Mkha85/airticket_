package com.logarithm.airticket.flightticketbook;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.logarithm.airticket.flightticketbook.Adapter.RecyclerAdapter_Flight;
import com.logarithm.airticket.flightticketbook.ModelClass.DeleteFlight.Message;
import com.logarithm.airticket.flightticketbook.ModelClass.Flight;
import com.logarithm.airticket.flightticketbook.ModelClass.RecyclerGet;
import com.logarithm.airticket.flightticketbook.ModelClass.RecyclerMessage;
import com.logarithm.airticket.flightticketbook.ParametersClass.GetSpecFlight;
import com.logarithm.airticket.flightticketbook.RestAPI.APIClient;
import com.logarithm.airticket.flightticketbook.RestAPI.APIInterface;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.logarithm.airticket.flightticketbook.Login.TOKEN_ID;
import static com.logarithm.airticket.flightticketbook.LoginAdmin.TOKEN_ID_ADMIN;

public class FlightListActivity extends AppCompatActivity {

    private TextView txt;

//    RecyclerView recyclerView;
//    FlightAdapter adapter;
//    List<Flight>productlist;



    public String Source,Destination;
    public AlertDialog alertDialog = null;
    List<RecyclerMessage> tripList;

    TextView SourceToolbar,DestinationToolbar;

    private ArrayList<RecyclerMessage> flights;
    private RecyclerView recyclerView;
    private RecyclerAdapter_Flight mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_list_view);
        Source=getIntent().getStringExtra("FROM");
        Destination=getIntent().getStringExtra("TO");
        SourceToolbar=findViewById(R.id.SourceToolbar);
        SourceToolbar.setText(Source);
        DestinationToolbar=findViewById(R.id.DestinationToolbar);
        DestinationToolbar.setText(Destination);

        try {
            // call the constructor of CustomAdapter to send the reference and data to Adapter
            alertDialog = new AlertDialog.Builder(FlightListActivity.this).create();
            alertDialog.setMessage("L???y th??ng tin c??c chuy???n bay...");
            alertDialog.show();
            final APIInterface apiService = APIClient.getClient().create(APIInterface.class);
            GetSpecFlight getSpecFlight=new GetSpecFlight(Source,Destination);
            Call<RecyclerGet> call2 = apiService.getAllSpecflights(TOKEN_ID,getSpecFlight);
            call2.enqueue(new Callback<RecyclerGet>() {
                @Override
                public void onResponse(Call<RecyclerGet> call, Response<RecyclerGet> response) {
                    try {
                        alertDialog.dismiss();
                        Log.i("JSON", response.body().getSuccess().toString());
                        if (response.body().getSuccess()) {
                            alertDialog.dismiss();
                            tripList = response.body().getMessage();
                            if (tripList.size() == 0) {
                                Toast.makeText(FlightListActivity.this, "Kh??ng c?? chuy???n bay c?? s???n !", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(FlightListActivity.this, tripList.get(0).getName(), Toast.LENGTH_SHORT).show();


                               flights = new ArrayList<>();
                                recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
                                mAdapter = new RecyclerAdapter_Flight(FlightListActivity.this,tripList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(FlightListActivity.this);
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setAdapter(mAdapter);
                            }
                        } else {
                            Log.i("TEST", response.body().getMessage().toString());
                        }
                    } catch (Exception e) {
                        alertDialog.dismiss();
                        Toast.makeText(FlightListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
                @Override
                public void onFailure(Call<RecyclerGet> call, Throwable t) {
                    alertDialog.dismiss();
                    t.printStackTrace();
                    Toast.makeText(FlightListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
