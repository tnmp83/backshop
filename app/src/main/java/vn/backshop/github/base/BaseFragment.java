package vn.backshop.github.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author phuongtnm
 */
public abstract class BaseFragment extends Fragment {
    /**
     * Get resource layout from child
     * @return R.layout.x (integer)
     */
    public abstract int layoutResource();

    /**
     * This is case run onCreate
     * @param savedInstanceState
     */
    public abstract void createView(Bundle savedInstanceState);

    private Unbinder unbinder;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(layoutResource(), container, false);

        // Handle touch on previous fragment
        mView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        unbinder = ButterKnife.bind(this, mView);
        createView(savedInstanceState);

        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(null != unbinder) {
            unbinder.unbind();
        }
    }
}
