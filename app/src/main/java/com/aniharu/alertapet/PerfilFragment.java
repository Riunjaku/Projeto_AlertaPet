package com.aniharu.alertapet;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.aniharu.alertapet.Classes.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    User user = new User();
    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil_usuario, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            user = (User) bundle.get("LoggedUser");
        }

        final ImageView mImageView = (ImageView) view.findViewById(R.id.imageview_default_picture);
        final EditText mId = (EditText) view.findViewById(R.id.editId);
        final EditText mNome = (EditText) view.findViewById(R.id.editNome);
        final EditText mDtNascimento = (EditText) view.findViewById(R.id.editDataNascimento);
        final EditText mCelular = (EditText) view.findViewById(R.id.editCelular);
        final EditText mEmail = (EditText) view.findViewById(R.id.editEmail);
        final Button mBtnEditar = (Button) view.findViewById(R.id.btnEditar);

        mNome.setText(user.name);
       // mImageView.setImageURI(Uri.parse(user.imageUrl));
        mDtNascimento.setText(user.dt_nascimento);
        mCelular.setText(user.telefone);
        mEmail.setText(user.email);

        //ao clicar no icone de editar deve-se trocar o
        //android:enabled="false" para true
        //android:visibility="invisible" e visible
    }



}
