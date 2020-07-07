package com.example.localsale.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 * 处理登录时输入框中格式是否符合的状态变量
 */
public class LoginFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    private boolean isTextValid;

    public LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isTextValid = false;
    }

    public LoginFormState(boolean isTextValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isTextValid = isTextValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isTextValid() {
        return isTextValid;
    }
}
