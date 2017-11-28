package com.aniharu.alertapet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigFragment extends Fragment {


    public ConfigFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_config, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button mSobre = view.findViewById(R.id.button2);
        Button mTermos = view.findViewById(R.id.button3);


        mSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConfigSobreFragment fragment = new ConfigSobreFragment();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment,"Sobre")
                        .commit();
            }
        });

        mTermos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConfigTermosDeUsoFragment fragment = new ConfigTermosDeUsoFragment();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment,"TermosDeUso")
                        .commit();

            }
        });
    }

}
