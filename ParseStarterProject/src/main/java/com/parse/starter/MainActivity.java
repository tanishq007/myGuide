/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class MainActivity extends AppCompatActivity {

  public void redirectActivity() {

    if (ParseUser.getCurrentUser().getString("touristOrGuide").equals("tourist")) {

        // a new function must be added to get the present location of the rider and the monument that he might be interested in.

        Intent intent = new Intent(getApplicationContext(), RiderActivity.class);
        startActivity(intent);

    } else {

      Intent intent = new Intent(getApplicationContext(), ViewRequestsActivity.class);
        startActivity(intent);


    }
  }

  public void getStarted(View view) {

    Switch userTypeSwitch = (Switch) findViewById(R.id.userTypeSwitch);

    Log.i("Switch value", String.valueOf(userTypeSwitch.isChecked()));

    String userType = "tourist";

    if (userTypeSwitch.isChecked()) {

      userType = "guide";

    }

    ParseUser.getCurrentUser().put("touristOrGuide", userType);

      ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
          @Override
          public void done(ParseException e) {

              redirectActivity();

          }
      });




  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    getSupportActionBar().hide();





    if (ParseUser.getCurrentUser() == null) {

      ParseAnonymousUtils.logIn(new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException e) {

          if (e == null) {

            Log.i("Info", "Anonymous login successful");

          } else {

            Log.i("Info", "Anonymous login failed");

          }


        }
      });

    } else {

      if (ParseUser.getCurrentUser().get("touristOrGuide") != null) {

        Log.i("Info", "Redirecting as " + ParseUser.getCurrentUser().get("touristOrGuide"));

        redirectActivity();

      }


    }


    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}