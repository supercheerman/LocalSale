package com.example.localsale.ui.register;

import com.example.localsale.R;
import com.example.localsale.ui.login.LoginFormState;
import com.example.localsale.ui.login.LoginViewModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegisterFormStateTest {
    private RegisterFormState mRegisterFormState1;
    private RegisterFormState mRegisterFormState2;
    private RegisterFormState mRegisterFormState3;
    private RegisterFormState mRegisterFormState4;
    private RegisterFormState mRegisterFormState5;

    @Before
    public void setUp(){
        mRegisterFormState1 = new RegisterFormState(null,null);
        mRegisterFormState2 = new RegisterFormState(R.string.invalid_username,null);
        mRegisterFormState3 = new RegisterFormState(null,R.string.invalid_password);
        mRegisterFormState4 = new RegisterFormState(true);
        mRegisterFormState5 = new RegisterFormState(false);

    }

    @Test
    public void getUsernameError() {
        assertEquals(mRegisterFormState1.getUsernameError(),null);
        assertEquals((Integer)mRegisterFormState2.getUsernameError(),(Integer)R.string.invalid_username);
        assertEquals(mRegisterFormState3.getUsernameError(),null);
        assertEquals(mRegisterFormState4.getUsernameError(),null);
        assertEquals(mRegisterFormState5.getUsernameError(),null);
    }

    @Test
    public void getPasswordError() {
        assertEquals(mRegisterFormState1.getPasswordError(),null);
        assertEquals(mRegisterFormState2.getPasswordError(),null);
        assertEquals((Integer)mRegisterFormState3.getPasswordError(),(Integer) R.string.invalid_password);
        assertEquals(mRegisterFormState4.getPasswordError(),null);
        assertEquals(mRegisterFormState5.getPasswordError(),null);
    }

    @Test
    public void isDataValid() {
        assertEquals(mRegisterFormState1.isDataValid(),false);
        assertEquals(mRegisterFormState2.isDataValid(),false);
        assertEquals(mRegisterFormState3.isDataValid(),false);
        assertEquals(mRegisterFormState4.isDataValid(),true);
        assertEquals(mRegisterFormState5.isDataValid(),false);
    }
}