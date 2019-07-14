/**----------------------------------------------------------------------------------
 * Microsoft Developer & Platform Evangelism
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 *
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND,
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 *----------------------------------------------------------------------------------
 * The example companies, organizations, products, domain names,
 * e-mail addresses, logos, people, places, and events depicted
 * herein are fictitious.  No association with any real company,
 * organization, product, domain name, email address, logo, person,
 * places, or events is intended or should be inferred.
 *----------------------------------------------------------------------------------
 **/

package com.example.smarthome;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Listweimages extends ListActivity {

    String[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Handler handler = new Handler();

        Thread th = new Thread(new Runnable() {
            public void run() {

                try {

                    final String[] images = ImageManager_2.ListImages();

                    handler.post(new Runnable() {

                        public void run() {
                            Listweimages.this.images = images;

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Listweimages.this, R.layout.activity_listweimages, images);
                            setListAdapter(adapter);
                        }
                    });
                }
                catch(Exception ex) {
                    final String exceptionMessage = ex.getMessage();
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(Listweimages.this, exceptionMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }});
        th.start();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getBaseContext(), ImageActivity.class);
        intent.putExtra("image", images[position]);

        startActivity(intent);
    }

}