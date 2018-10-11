package vn.backshop.github.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;

public class Transport {

    private static Transport instance;
    public static Transport getInstance() {
        if (instance == null) {
            synchronized (Transport.class) {
                if (instance == null) {
                    instance = new Transport();
                }
            }
        }
        return instance;
    }
    private Transport() {

    }

    public void addFragment(FragmentManager fragmentManager, Fragment fragment, int containerId, boolean hasBackStack) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        String tag = getTagByFragment(fragment);
        try {
            if (TextUtils.isEmpty(tag)) {
                // This case is very infrequent, because contentId is view but not fragment view (declare in xml)
                ft.add(containerId, fragment);
            } else {
                ft.add(containerId, fragment, tag);
            }
            commitTransaction(ft, tag, hasBackStack);
        }catch (IllegalStateException e){
            Log.e("transit", e.getMessage());
        }
    }

    private void commitTransaction(FragmentTransaction ft, String tag, boolean hasBackStack) {
        if(hasBackStack) {
            ft.addToBackStack(tag);
        }
        // TODO Handle replace fragment when run in background
        ft.commit();
    }

    private String getTagByFragment(Fragment fr) {
        if (null != fr) {
            return fr.getClass().getName();
        }
        return null;
    }
}
