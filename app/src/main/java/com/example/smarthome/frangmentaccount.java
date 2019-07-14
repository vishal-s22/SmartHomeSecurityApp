package com.example.smarthome;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class frangmentaccount extends Fragment  {

    private Button register , profileb;
    String str;
    View view;
    public frangmentaccount() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.account_layout,container,false);
        //return view;

        register = (Button) view.findViewById(R.id.button4);
        profileb= (Button) view.findViewById(R.id.button5);
        str=((MainActivity)getActivity()).getstr();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(str.equals("true")) {
                    Intent i = new Intent(getActivity(), registeruser.class);
                    startActivity(i);
                }
                else
                    Toast.makeText(getActivity(),"You are not authorized for this function!!",Toast.LENGTH_LONG).show();

            }
        });
        profileb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //str=((MainActivity)getActivity()).getstr();
               /* if(str.equals("true"))
                {
                    Intent i=new Intent(getActivity(),adminprofile.class);
                    startActivity(i);
                }
                else {*/
                    Intent i = new Intent(getActivity(), profile.class);
                    startActivity(i);
               // }
            }
        });
        return view;
        }


}
