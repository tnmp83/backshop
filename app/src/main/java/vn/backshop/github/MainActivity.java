package vn.backshop.github;

import android.os.Bundle;

import vn.backshop.github.base.BaseActivity;
import vn.backshop.github.helper.Transport;
import vn.backshop.github.modules.users.UserFragment;

/**
 * @author phuongtnm
 */
public class MainActivity extends BaseActivity {

    @Override
    public int layoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void createView(Bundle savedInstanceState) {
        Transport.getInstance().addFragment(getSupportFragmentManager(), new UserFragment(), R.id.wrap_content, false);
        //Transport.getInstance().addFragment(getSupportFragmentManager(), DetailFragment.newInstance("macournoyer"), R.id.wrap_content, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO Check internet connection
    }
}
