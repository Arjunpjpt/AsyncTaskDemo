package instructor.tcss450.uw.edu.asynctaskdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements
    ClickMeFragment.OnFragmentInteractionListener {

    private ClickMeFragment mClickFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            if (findViewById(R.id.frame_main_fragment_container) != null) {
                mClickFrag = new ClickMeFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame_main_fragment_container, mClickFrag, "CLICK")
                        .commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (mClickFrag != null) {
            mClickFrag.cancel();
        }
    }

    @Override
    public void onClickMeFragmentInteraction() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_main_fragment_container, new DoneFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onWaitFragmentInteractionShow() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_main_fragment_container, new WaitFragment(), "WAIT")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onWaitFragmentInteractionHide() {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(getSupportFragmentManager().findFragmentByTag("WAIT"))
                .commit();
    }
}
