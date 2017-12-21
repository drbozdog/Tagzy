package com.example.drbozdog.tagzy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.drbozdog.tagzy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatusActivity extends AppCompatActivity {

    public static String EXTRA_SUCCESSFUL = "extra_successful";
    public static String EXTRA_ERRORS = "extra_errors";

    @BindView(R.id.txt_tagged)
    TextView mTxtTaggedCount;

    @BindView(R.id.txt_errors)
    TextView mTxtErrors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        int uploadSuccessfulCount = getIntent().getIntExtra(EXTRA_SUCCESSFUL, 0);
        int uploadErrorsCount = getIntent().getIntExtra(EXTRA_ERRORS, 0);

        ButterKnife.bind(this);

        mTxtTaggedCount.setText("Successful uploaded tagged records: " + String.valueOf(uploadSuccessfulCount));
        mTxtErrors.setText("Errors when uploading tagged records: " + String.valueOf(uploadErrorsCount));
    }
}
