package instructor.tcss450.uw.edu.asynctaskdemo;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClickMeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ClickMeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private WaitAsyncTask mTask;

    public ClickMeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootOfLayout =
                inflater.inflate(R.layout.fragment_click_me, container, false);

        rootOfLayout.findViewById(R.id.button_click_me_click)
                .setOnClickListener(this::onButtonPressed);

        return rootOfLayout;
    }

    public void onButtonPressed(final View buttonClicked) {


        if (mListener != null) {
            mTask = new WaitAsyncTask();
            mTask.execute();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void cancel() {
        if (mTask != null) {
            mTask.cancel(true);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener extends WaitFragment.OnFragmentInteractionListener {
        void onClickMeFragmentInteraction();
    }

    private class WaitAsyncTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            getActivity().findViewById(R.id.button_click_me_click).setEnabled(false);
            mListener.onWaitFragmentInteractionShow();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isCancelled()) {
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            getActivity().findViewById(R.id.button_click_me_click).setEnabled(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getActivity().findViewById(R.id.button_click_me_click).setEnabled(true);
            mListener.onWaitFragmentInteractionHide();
            mListener.onClickMeFragmentInteraction();
        }
    }
}
