package com.sohu.auto.treasure.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.utils.DeviceInfo;

/**
 * Created by aiyou on 2019/4/11.
 */

public class InputPasswordDialog extends Dialog {

    private EditText edtPassword;
    private TextView btnCancel;
    private TextView btnConfirm;

    public InputPasswordDialog(@NonNull Context context) {
        super(context, R.style.CustomDialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initContentView();
    }

    private void initContentView() {
        View contentView = getLayoutInflater().inflate(R.layout.dialog_input_password, null);
        edtPassword = contentView.findViewById(R.id.edt_password);
        btnCancel = contentView.findViewById(R.id.btn_cancel);
        btnConfirm = contentView.findViewById(R.id.btn_confirm);
        setContentView(contentView);

        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = DeviceInfo.dip2Px(getContext(), 250);
        getWindow().setAttributes(params);
    }

    public InputPasswordDialog withConfirmClickListener(OnClickListener l) {
        btnConfirm.setOnClickListener(v -> {
            l.onClick(v, edtPassword.getText().toString());
        });
        return this;
    }

    public InputPasswordDialog withCancelClickListener(OnClickListener l) {
        btnCancel.setOnClickListener(v -> {
            l.onClick(v, edtPassword.getText().toString());
        });
        return this;
    }

    public interface OnClickListener {
        void onClick(View v, String inputText);
    }
}
