package com.example.smarthome;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class fragmentmyhome extends Fragment {

    private Button alldevice, live, access;
    View view;
    public fragmentmyhome() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.myhome_fragment, container, false);

        alldevice = (Button) view.findViewById(R.id.button1);
        live=(Button) view.findViewById(R.id.button2);
        access = (Button) view.findViewById(R.id.button3);

        alldevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), devicenames.class);
                startActivity(i);
            }
        });
        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("http://ivideon.com/my"));
                startActivity(i);
            }
        });
        access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Main2Activity.class);
                startActivity(i);
            }
        });

        return view;
    }




}
