package com.example.saiteja.myfootballapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CPFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CPFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<JSONObject> comparingPlayers = new ArrayList<JSONObject>();
    private ProgressBar spinner;

    private OnFragmentInteractionListener mListener;

    public CPFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CPFragment newInstance(String param1, String param2) {
        CPFragment fragment = new CPFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_cp, container, false);
        final View mView = v;
        AsyncHttpClient client = new AsyncHttpClient();
        spinner=(ProgressBar)mView.findViewById(R.id.progressBarC);
        spinner.setVisibility(View.VISIBLE);
        client.get("https://fantasy.premierleague.com/drf/bootstrap-static", new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                String str = "";
                JSONObject json;
                JSONArray phases;
                JSONArray elements;
                JSONArray teams;
                JSONArray element_types;
                JSONArray events;
                int nogw=0;
                ArrayList<Integer> teamBool = new ArrayList<Integer>();
                for (int i = 0; i < 21; i += 1) {
                    teamBool.add(0);
                }
                try {
                    str = new String(response, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //System.out.println(str);
                try {
                    json = new JSONObject(str);
                    phases = json.getJSONArray("phases");
                    elements = json.getJSONArray("elements");
                    teams = json.getJSONArray("teams");
                    element_types = json.getJSONArray("element_types");
                    events = json.getJSONArray("events");
                    JSONObject p1 = null;
                    JSONObject p2 = null;
                    for(int i=0;i<elements.length();i+=1){
                        if((elements.getJSONObject(i).getString("web_name")+"("+elements.getJSONObject(i).getString("first_name")+")").equals(mParam1)){
                            p1 = elements.getJSONObject(i);
                            comparingPlayers.add(p1);
                            if(comparingPlayers.size()==1)
                                comparingPlayers.add(null);
                            if(comparingPlayers.size()==3)
                                break;
                        }
                        else if((elements.getJSONObject(i).getString("web_name")+"("+elements.getJSONObject(i).getString("first_name")+")").equals(mParam2)){
                            p2 = elements.getJSONObject(i);
                            comparingPlayers.add(p2);
                            if(comparingPlayers.size()==1)
                                comparingPlayers.add(null);
                            if(comparingPlayers.size()==3)
                                break;
                        }
                    }
                    int exiter=0;
                    for(int i=0;i<events.length();i++){
                        if(events.getJSONObject(i).getInt("average_entry_score")==0) {
                            exiter = i;
                            break;
                        }
                    }
                    nogw=exiter;

                    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+nogw);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                GridView gridView = (GridView) mView.findViewById(R.id.compareGrid);
                GridActivity5 gridAdapter = new GridActivity5(getContext(), R.layout.compare_player, comparingPlayers,nogw);
                gridView.setAdapter(gridAdapter);
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
            }

            @Override
            public void onStart() {
                super.onStart();
            }
        });
        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
