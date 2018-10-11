package vn.backshop.github.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author phuongtnm
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * Get resource layout from child
     *
     * @return R.layout.x (integer)
     */
    public abstract int layoutResource();

    /**
     * This is case run onCreate
     *
     * @param savedInstanceState
     */
    public abstract void createView(Bundle savedInstanceState);

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResource());

        unbinder = ButterKnife.bind(this);
        createView(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != unbinder) {
            unbinder.unbind();
        }
    }

}
