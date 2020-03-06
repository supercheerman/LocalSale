package com.example.localsale.ui.register;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.localsale.R;
import com.example.localsale.data.database.Database;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mModel;
    private Database mDatabase;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mRegisterButton;


    public static RegisterFragment newInstance(Context context){
        return new RegisterFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mDatabase = Database.getDatabase(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_regist,container,false);
        mModel = ViewModelProviders.of(getActivity(), new RegisterViewModelFactory()).get(RegisterViewModel.class);
        mUsernameEditText = view .findViewById(R.id.register_email);
        mPasswordEditText = view.findViewById(R.id.register_password);
        mRegisterButton = view.findViewById(R.id.register_button);

        mModel.getRegisterFormState().observe(this,new Observer<RegisterFormState>() {
            @Override
            public void onChanged(RegisterFormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }
                mRegisterButton.setEnabled(registerFormState.isDataValid());
                if (registerFormState.getUsernameError() != null) {
                    mUsernameEditText.setError(getString(registerFormState.getUsernameError()));
                }
                if (registerFormState.getPasswordError() != null) {
                    mPasswordEditText.setError(getString(registerFormState.getPasswordError()));
                }
            }
        });
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                //输入框内容变化时调用LoginDataChange方法改变LoginFormState的结果
                mModel.loginDataChanged(mUsernameEditText.getText().toString(),
                        mPasswordEditText.getText().toString());
            }
        };
        mUsernameEditText.addTextChangedListener(afterTextChangedListener);
        mPasswordEditText.addTextChangedListener(afterTextChangedListener);
        mPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                }
                return false;//返回true，保留软键盘。false，隐藏软键盘
            }
        });
        mRegisterButton .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mModel.registerUser(mDatabase,mUsernameEditText.getText().toString(),
                        mPasswordEditText.getText().toString());
            }
        });

      



        return view;
    }
}
