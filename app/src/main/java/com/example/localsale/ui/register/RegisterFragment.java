package com.example.localsale.ui.register;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.localsale.R;
import com.example.localsale.data.LocalDatabase.Database;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterFragment extends Fragment {
    private RegisterViewModel mModel;
    private Database mDatabase;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText registerCode;
    private Button mRegisterButton;
    private Button mBtnHuoQu;
    String phoneNumber;
    String password;
    String phonecode;

    private CountDownTimer timer;
    private EventHandler handler;

    public static RegisterFragment newInstance(Context context) {
        return new RegisterFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = Database.getDatabase(getActivity());
        //initSMSSDK();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_regist, container, false);
        mModel = ViewModelProviders.of(getActivity(), new RegisterViewModelFactory()).get(RegisterViewModel.class);
        mUsernameEditText = view.findViewById(R.id.register_email);
        mPasswordEditText = view.findViewById(R.id.register_password);
        mRegisterButton = view.findViewById(R.id.register_button);
        //mBtnHuoQu = view.findViewById(R.id.btnSendValidCode);
        //registerCode = view.findViewById(R.id.editTextValidCode);

        mModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
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
                mModel.registerTextChanged(mUsernameEditText.getText().toString(),
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
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneNumber = mUsernameEditText.getText().toString();
                //phonecode = registerCode.getText().toString();
                password = mPasswordEditText .getText().toString();
                new PHPAsyncTask().execute();


                //Log.d("RegisterFragment", "ppppppppppp"+phoneNumber);
                //Log.d("RegisterFragment", "ppppppppppp"+phonecode);
                //SMSSDK.submitVerificationCode("86", phoneNumber, phonecode);


            }
        });


        //上传手机号进行获取验证码
        /*mBtnHuoQu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = mUsernameEditText.getText().toString();
                if (!phoneNumber.matches(phoneRegex)) {

                    Toast.makeText(getActivity(), "手机号码格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }   else   if (!TextUtils.isEmpty(phoneNumber)) {
                    SMSSDK.getVerificationCode("86", phoneNumber);
                    timer();

                }  else {
                    Toast.makeText(getActivity(), "请输入手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });*/


        return view;
    }

    //进行回调等操作来进行验证码等逻辑处理
    private void initSMSSDK() {
        handler = new EventHandler() {
            @Override
            public void afterEvent(final int event, final int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mModel.registerUser(mDatabase, mUsernameEditText.getText().toString(),
                                        mPasswordEditText.getText().toString());
                                getActivity().finish();
                            }
                        });

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "验证码已发送", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        Toast.makeText(getActivity(), "验证码发送失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    try {
                        //来对比验证码是否正确
                        JSONObject obj = new JSONObject(throwable.getMessage());
                        final String des = obj.optString("detail");
                        if (!TextUtils.isEmpty(des)) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "验证码不正确", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        //来通知handler操作
        SMSSDK.registerEventHandler(handler);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //销毁
        SMSSDK.unregisterEventHandler(handler);
    }

    private void timer() {

        timer = new CountDownTimer(61000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                mBtnHuoQu.setEnabled(false);
                mBtnHuoQu.setText("发送验证码" + "(" + millisUntilFinished / 1000 + ")");
                mBtnHuoQu.setBackgroundColor(Color.GRAY);

            }

            @Override
            public void onFinish() {
                mBtnHuoQu.setEnabled(true);
                mBtnHuoQu.setText("发送验证码");
                mBtnHuoQu.setTextColor(getActivity().getResources().getColor(R.color.smssdk_white));
                mBtnHuoQu.setBackground(getActivity().getDrawable(R.drawable.shape_login_red));
            }
        }.start();

    }
    public class PHPAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        /*
         * 子线程中读取数据库中的数据
         * */
        @Override
        protected Integer doInBackground(Integer... voids) {
            mModel.registerUser(mDatabase,phoneNumber,password);
            return 0;
        }

        /*
         * 该方法在子线程执行完毕后执行，在主线程中执行，用来设置数据显示
         * */
        @Override
        protected void onPostExecute(Integer integer) {

            Toast.makeText(getActivity(),"注册成功",Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }


}
