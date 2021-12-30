package com.ucas.android.firebaseapp.base;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.ucas.android.firebaseapp.base.BaseActivity;
import com.ucas.android.firebaseapp.utils.AppContextWrapper;
import com.ucas.android.firebaseapp.utils.AppPreferences;

import java.util.Locale;

public class BaseFragment extends Fragment {

    public BaseActivity mContext;

    @Override
    public void onAttach(Context context) {
        String language = AppPreferences.getInstance(context).getStringPreferences(AppPreferences.SELECTED_LANGUAGE);
        super.onAttach(AppContextWrapper.wrap(context, new Locale(language)));
        if (context instanceof Activity) {
            this.mContext = (BaseActivity) context;
        }
    }
}
