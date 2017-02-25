package com.example.saiteja.myfootballapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FixtureFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FixtureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FixtureFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressBar spinner;

    private OnFragmentInteractionListener mListener;
    private ArrayList fixtureList = new ArrayList();

    public FixtureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FixtureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FixtureFragment newInstance(String param1, String param2) {
        FixtureFragment fragment = new FixtureFragment();
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
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://fantasy.premierleague.com/drf/bootstrap-static", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                //System.out.println("Started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                String str= "";
                JSONObject json;
                JSONArray phases;
                JSONArray elements;
                JSONArray teams;
                JSONArray element_types;
                JSONArray events;
                ArrayList<Integer> teamBool = new ArrayList<Integer>();
                for(int i=0;i<21;i+=1){
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
                    for(int i=0;i<teams.length();i+=1){
                        Fixture f = new Fixture();
                        JSONObject team = teams.getJSONObject(i);
                        String t1_id = team.getString("id");
                        if(teamBool.get(Integer.parseInt(t1_id))!=1) {
                            teamBool.set(Integer.parseInt(t1_id), 1);
                            //System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                            JSONArray opp = team.getJSONArray("next_event_fixture");
                            if(opp.length()!=0){
                                //opp = team.getJSONArray("current_event_fixture");
                                System.out.println(opp);
                                String t2_id = opp.getJSONObject(0).getString("opponent");
                                teamBool.set(Integer.parseInt(t2_id), 1);
                                if(opp.getJSONObject(0).getBoolean("is_home")){
                                    f.setHomeT(team.getString("short_name"));
                                    f.setHomeI("https://platform-static-files.s3.amazonaws.com/premierleague/badges/t"+team.getString("code")+".png");
                                    f.setAwayT(teams.getJSONObject(Integer.parseInt(t2_id)-1).getString("short_name"));
                                    f.setAwayI("https://platform-static-files.s3.amazonaws.com/premierleague/badges/t"+teams.getJSONObject(Integer.parseInt(t2_id)-1).getString("code")+".png");
                                }
                                else{
                                    f.setAwayT(team.getString("short_name"));
                                    f.setAwayI("https://platform-static-files.s3.amazonaws.com/premierleague/badges/t"+team.getString("code")+".png");
                                    f.setHomeT(teams.getJSONObject(Integer.parseInt(t2_id)-1).getString("short_name"));
                                    f.setHomeI("https://platform-static-files.s3.amazonaws.com/premierleague/badges/t"+teams.getJSONObject(Integer.parseInt(t2_id)-1).getString("code")+".png");
                                }
                                fixtureList.add(f);
                            }
                        }
                    }
                    GridView gridView = (GridView) getView().findViewById(R.id.gridView);
                    //System.out.println(fixtureList.size());
                    ImageView img1 = (ImageView) getView().findViewById(R.id.premlogo);
                    spinner=(ProgressBar)getView().findViewById(R.id.progressBar);
                    spinner.setVisibility(View.VISIBLE);
                    Picasso.with(getContext()).load("https://ismdj.scdn5.secure.raxcdn.com/static/libsass/plfpl/dist/img/pl-long@2x.png").into(img1);
                    GridActivity gridAdapter = new GridActivity(getContext(), R.layout.grid_item_layout, fixtureList);
                    gridView.setAdapter(gridAdapter);
                    spinner.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                //System.out.println("Failed");
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
                //System.out.println("Retrying  "+retryNo);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fixture, container, false);
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
