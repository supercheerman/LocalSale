package com.example.localsale.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.localsale.API.UserInfoAPI;
import com.example.localsale.R;
import com.example.localsale.data.UserInfo.LoginUser;
import com.example.localsale.ui.Navigation.MainActivity;
import com.example.localsale.ui.register.RegisterActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private EditText usernameEditText ;
    private EditText passwordEditText ;
    private Button loginButton ;
    private Button registerButton;
    private ProgressBar loadingProgressBar ;

    private String userName;
    private String password;

    public static LoginFragment newInstance(Context context){
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        loginViewModel = ViewModelProviders.of(getActivity(), new LoginViewModelFactory())
                .get(LoginViewModel.class);//ViewModel的生命周期比activity长，因为每次旋转产生新的activity，但是可以通过该方法获取同一个ViewModel，通过指定activity来获取同一个ViewModel

        usernameEditText = v.findViewById(R.id.username);
        passwordEditText = v.findViewById(R.id.password);
        loginButton = v.findViewById(R.id.login);
        registerButton = v.findViewById(R.id.register);
        loadingProgressBar = v.findViewById(R.id.loading);
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                //目前改变后并不存在为空情况
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isTextValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        final TextWatcher afterTextChangedListener = new TextWatcher() {
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
                loginViewModel.loginTextChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    v.setText("fucnk");
                }
                return false;//返回true，保留软键盘。false，隐藏软键盘
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                loadingProgressBar.setVisibility(View.VISIBLE);//登录按钮点击后到判断结果出来之前显示ProcessBar
                new PHPAsyncTask().execute();


            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = RegisterActivity.newIntent(getContext());
                startActivity(intent);
            }
        });

        return v;
    }
    public class PHPAsyncTask extends AsyncTask<Integer, Integer, Boolean > {

        /*
         * 子线程中读取数据库中的数据
         * */
        @Override
        protected Boolean  doInBackground(Integer... voids) {
            return UserInfoAPI.ValidUser(userName,password);

        }

        /*
         * 该方法在子线程执行完毕后执行，在主线程中执行，用来设置数据显示
         * */
        @Override
        protected void onPostExecute(Boolean  bool) {
            loadingProgressBar.setVisibility(View.GONE);
            if(bool){
                LoginUser.getUseInfo().setUserPassword(password);
                LoginUser.getUseInfo().setUserName(userName);
                Toast.makeText(getActivity(),"登陆成功",Toast.LENGTH_SHORT).show();
                getActivity().finish();
                startActivity(MainActivity.newIntent(getContext()));
            }else{
                Toast.makeText(getActivity(),"用户名或密码错误",Toast.LENGTH_SHORT).show();
            }

        }
    }

}
