package com.logarithm.airticket.flightticketbook;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.logarithm.airticket.flightticketbook.R;

public class AdminDashboard extends AppCompatActivity {

    public AlertDialog alertDialog = null;
    SharedPreferences pref;
    SharedPreferences.Editor editor ;


    Button addFlight,deleteFlight,addAirport,deleteAirport , viewBookings;
    LinearLayout logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        logout=findViewById(R.id.logout);


        pref = getApplicationContext().getSharedPreferences("cred", 0); // 0 - for private mode
        editor = pref.edit();

        addAirport=findViewById(R.id.btn_addairport);
        deleteAirport=findViewById(R.id.btn_deleteairport);
        deleteFlight=findViewById(R.id.btn_deleteflight);
        addFlight=findViewById(R.id.btn_addflight);
        viewBookings=findViewById(R.id.btn_viewboookings);


        viewBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ViewAllBookingAdmin.class));
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog=new AlertDialog.Builder(AdminDashboard.this).create();
                alertDialog.setTitle("Đăng xuất");
                alertDialog.setMessage("Bạn có muốn đăng xuất");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                                editor.remove("TOKEN_ID_ADMIN");
                                editor.commit();
                                startActivity(new Intent(getApplicationContext(),RoleChoose.class));
                                finish();
                            }
                        });
                alertDialog.show();

            }

        });

        addFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddFlight.class));
            }
        });


        addAirport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddAirport.class));
            }
        });


        deleteFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DeleteFlight.class));
            }
        });
        deleteAirport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DeleteAirport.class));


            }
        });
    }
}
