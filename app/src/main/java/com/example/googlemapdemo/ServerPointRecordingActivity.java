package com.example.googlemapdemo;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ServerPointRecordingActivity extends AppCompatActivity {

EditText etLatitude, etLongitude, etRecID, etDate, etTime, etVoteType, etVoteParty, etComment;

TextView tvData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_point_recording);

        etLatitude = findViewById(R.id.etLati);
        etLongitude = findViewById(R.id.etLong);
        etRecID = findViewById(R.id.etRecID);
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        etVoteType = findViewById(R.id.etVote);
        etVoteParty = findViewById(R.id.etParty);
        etComment = findViewById(R.id.etComment);

        //get latitude and longitude value from map activity
        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra("latitude",0);
        double longitude = intent.getDoubleExtra("longitude",0);

        etLatitude.setText(latitude +"");
        etLongitude.setText(longitude +"");
    }

    public void btnBackClick(View v){ // if click back button then go to the google map activity
        Intent i = new Intent();
        setResult(RESULT_OK);
        finish();
    }

    public void btnSaveClick(View v){
        // user fill the form and click save button then data will save in database
        int recID = Integer.parseInt(etRecID.getText().toString().trim());
        Double latitude = Double.parseDouble(etLatitude.getText().toString().trim());
        Double longitude = Double.parseDouble(etLongitude.getText().toString().trim());
        String Date = etDate.getText().toString().trim();
        String Time = etTime.getText().toString().trim();
        String votetype = etVoteType.getText().toString().trim();
        String voteparty = etVoteParty.getText().toString().trim();
        String comment = etComment.getText().toString().trim();

        try {
            SessionDB db = new SessionDB(this);
            db.open();
            Long recId = db.createEntry(recID, latitude, longitude, Date, Time, votetype, voteparty, comment);
            db.close();
            Toast.makeText(ServerPointRecordingActivity.this, recId + " Successfully Saved", Toast.LENGTH_LONG).show();
        } catch (SQLException ex) {
            Toast.makeText(ServerPointRecordingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void btnViewClick(View v) {
        // to get records in the database and display in the textview
        try {
            SessionDB db = new SessionDB(this);
            db.open();
            tvData = findViewById(R.id.tvData);
            tvData.setText(db.getData());
            db.close();
            Toast.makeText(this, "Successfully Displayed", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}