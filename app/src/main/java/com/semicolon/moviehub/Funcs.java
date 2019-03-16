package com.semicolon.moviehub;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;

public class Funcs extends Activity {
    public ProgressDialog mProgressDialog;
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading..");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {

//            findViewById(R.id.buttonFacebookLogin).setVisibility(View.GONE);
//            findViewById(R.id.buttonFacebookSignout).setVisibility(View.VISIBLE);
        }
        else {
//            mStatusTextView.setText(R.string.signed_out);
//            mDetailTextView.setText(null);

//            findViewById(R.id.buttonFacebookLogin).setVisibility(View.VISIBLE);
//            findViewById(R.id.buttonFacebookSignout).setVisibility(View.GONE);
        }
    }

}
