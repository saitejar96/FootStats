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
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeamFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ProgressBar spinner = null;
    private ArrayList<BestPlayerX> bestPlayers = new ArrayList<BestPlayerX>();
    private ArrayList<BestPlayerX> goalkeepers = new ArrayList<BestPlayerX>();
    private ArrayList<BestPlayerX> defenders = new ArrayList<BestPlayerX>();
    private ArrayList<BestPlayerX> midfielders = new ArrayList<BestPlayerX>();
    private ArrayList<BestPlayerX> forwards = new ArrayList<BestPlayerX>();

    public TeamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamFragment newInstance(String param1, String param2) {
        TeamFragment fragment = new TeamFragment();
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
        View v = inflater.inflate(R.layout.fragment_team, container, false);
        final View mView = v;
        AsyncHttpClient client = new AsyncHttpClient();

        spinner=(ProgressBar)mView.findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);
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
                    JSONObject team1 = null;
                    JSONObject team2 = null;
                    boolean isHome = false;
                    ArrayList<BestPlayerX> whole = new ArrayList<BestPlayerX>();

                    for(int i =0;i<elements.length();i+=1){
                        BestPlayerX b = new BestPlayerX();
                        JSONObject e = elements.getJSONObject(i);
                        int x1 = 0;
                        int x2 = 0;
                        String wUrl = "https://ussouthcentral.services.azureml.net/workspaces/f72c8841eb144ce29adf73eadf159097/services/8ae5b00cd09e4828b2fd86eb3a51b347/execute?api-version=2.0&details=true";
                        System.out.println(e.getString("web_name"));
                        if(e.getString("news").length()==0) {
                            if (e.getString("element_type").equals("4")) {
                                b.setC_img("https://platform-static-files.s3.amazonaws.com/premierleague/badges/t" + elements.getJSONObject(i).getString("team_code") + ".png");
                                b.setP_img("https://platform-static-files.s3.amazonaws.com/premierleague/photos/players/110x140/p" + elements.getJSONObject(i).getString("photo").substring(0, elements.getJSONObject(i).getString("photo").length() - 4) + ".png");
                                b.setP_name(elements.getJSONObject(i).getString("web_name"));
                                b.setP_form(elements.getJSONObject(i).getString("form"));
                                b.setP_ppg(elements.getJSONObject(i).getString("points_per_game"));
                                if(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getJSONArray("next_event_fixture").getJSONObject(0).getBoolean("is_home")){
                                    x1 = Integer.parseInt(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getString("strength_overall_home"));
                                    x2 = Integer.parseInt(teams.getJSONObject(Integer.parseInt(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getJSONArray("next_event_fixture").getJSONObject(0).getString("opponent"))-1).getString("strength_overall_away"));
                                }
                                else{
                                    x1 = Integer.parseInt(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getString("strength_overall_away"));
                                    x2 = Integer.parseInt(teams.getJSONObject(Integer.parseInt(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getJSONArray("next_event_fixture").getJSONObject(0).getString("opponent"))-1).getString("strength_overall_home"));
                                }
                                b.setP_teamW(x1+"");
                                b.setP_oppW(x2+"");
                                AsyncHttpClient clientX = new AsyncHttpClient();
                                RequestParams params = new RequestParams();
                                float x_0 = Float.parseFloat(e.getString("ict_index"))/25;
                                float f = x1/x2;
                                float x_1 = f ;
                                int x_2 = Math.round(Float.parseFloat(e.getString("selected_by_percent"))/100*4408355);
                                String jx = "{\n" +
                                        "  \"Inputs\": {\n" +
                                        "    \"input1\": {\n" +
                                        "      \"ColumnNames\": [\n" +
                                        "        \"0\",\n" +
                                        "        \"1\",\n" +
                                        "        \"2\"\n" +
                                        "      ],\n" +
                                        "      \"Values\": [\n" +
                                        "        [\n" +
                                        "          \""+x_0+"\",\n" +
                                        "          \""+x_1+"\",\n" +
                                        "          \""+x_2+"\"\n" +
                                        "        ]\n" +
                                        "      ]\n" +
                                        "    }\n" +
                                        "  },\n" +
                                        "  \"GlobalParameters\": {}\n" +
                                        "}";
                                JSONObject jsonObject = new JSONObject(jx);
                                ByteArrayEntity entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
                                entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                                clientX.addHeader("Authorization","Bearer UywSeIkD0sXM+2NXBKWi46BEnTHF3ibtixnHHj2mLJriIT/1y7ugWzO0xplkTPLQacyLODfAKPZZ9WdpPUbEEA==");
                                clientX.post(getContext(),wUrl,entity, "application/json", new JsonHttpResponseHandler(){
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject obj) {
                                        // called when response HTTP status is "200 OK"
                                        System.out.println("rest"+obj+"##########");
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                                        System.out.println("#########"+errorResponse.toString()+"##########");
                                    }
                                } );
                                forwards.add(b);
                            }
                            else if (e.getString("element_type").equals("3")) {
                                b.setC_img("https://platform-static-files.s3.amazonaws.com/premierleague/badges/t" + elements.getJSONObject(i).getString("team_code") + ".png");
                                b.setP_img("https://platform-static-files.s3.amazonaws.com/premierleague/photos/players/110x140/p" + elements.getJSONObject(i).getString("photo").substring(0, elements.getJSONObject(i).getString("photo").length() - 4) + ".png");
                                b.setP_name(elements.getJSONObject(i).getString("web_name"));
                                b.setP_form(elements.getJSONObject(i).getString("form"));
                                b.setP_ppg(elements.getJSONObject(i).getString("points_per_game"));
                                if(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getJSONArray("next_event_fixture").getJSONObject(0).getBoolean("is_home")){
                                    x1 = Integer.parseInt(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getString("strength_overall_home"));
                                    x2 = Integer.parseInt(teams.getJSONObject(Integer.parseInt(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getJSONArray("next_event_fixture").getJSONObject(0).getString("opponent"))-1).getString("strength_overall_away"));
                                }
                                else{
                                    x1 = Integer.parseInt(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getString("strength_overall_away"));
                                    x2 = Integer.parseInt(teams.getJSONObject(Integer.parseInt(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getJSONArray("next_event_fixture").getJSONObject(0).getString("opponent"))-1).getString("strength_overall_home"));
                                }
                                b.setP_teamW(x1+"");
                                b.setP_oppW(x2+"");
                                midfielders.add(b);
                            }
                            else if (e.getString("element_type").equals("2")) {
                                b.setC_img("https://platform-static-files.s3.amazonaws.com/premierleague/badges/t" + elements.getJSONObject(i).getString("team_code") + ".png");
                                b.setP_img("https://platform-static-files.s3.amazonaws.com/premierleague/photos/players/110x140/p" + elements.getJSONObject(i).getString("photo").substring(0, elements.getJSONObject(i).getString("photo").length() - 4) + ".png");
                                b.setP_name(elements.getJSONObject(i).getString("web_name"));
                                b.setP_form(elements.getJSONObject(i).getString("form"));
                                b.setP_ppg(elements.getJSONObject(i).getString("points_per_game"));
                                if(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getJSONArray("next_event_fixture").getJSONObject(0).getBoolean("is_home")){
                                    x1 = Integer.parseInt(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getString("strength_overall_home"));
                                    x2 = Integer.parseInt(teams.getJSONObject(Integer.parseInt(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getJSONArray("next_event_fixture").getJSONObject(0).getString("opponent"))-1).getString("strength_overall_away"));
                                }
                                else{
                                    x1 = Integer.parseInt(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getString("strength_overall_away"));
                                    x2 = Integer.parseInt(teams.getJSONObject(Integer.parseInt(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getJSONArray("next_event_fixture").getJSONObject(0).getString("opponent"))-1).getString("strength_overall_home"));
                                }
                                b.setP_teamW(x1+"");
                                b.setP_oppW(x2+"");
                                defenders.add(b);
                            }
                            else if (e.getString("element_type").equals("1")) {
                                b.setC_img("https://platform-static-files.s3.amazonaws.com/premierleague/badges/t" + elements.getJSONObject(i).getString("team_code") + ".png");
                                b.setP_img("https://platform-static-files.s3.amazonaws.com/premierleague/photos/players/110x140/p" + elements.getJSONObject(i).getString("photo").substring(0, elements.getJSONObject(i).getString("photo").length() - 4) + ".png");
                                b.setP_name(elements.getJSONObject(i).getString("web_name"));
                                b.setP_form(elements.getJSONObject(i).getString("form"));
                                b.setP_ppg(elements.getJSONObject(i).getString("points_per_game"));
                                if(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getJSONArray("next_event_fixture").getJSONObject(0).getBoolean("is_home")){
                                    x1 = Integer.parseInt(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getString("strength_overall_home"));
                                    x2 = Integer.parseInt(teams.getJSONObject(Integer.parseInt(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getJSONArray("next_event_fixture").getJSONObject(0).getString("opponent"))-1).getString("strength_overall_away"));
                                }
                                else{
                                    x1 = Integer.parseInt(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getString("strength_overall_away"));
                                    x2 = Integer.parseInt(teams.getJSONObject(Integer.parseInt(teams.getJSONObject(Integer.parseInt(e.getString("team"))-1).getJSONArray("next_event_fixture").getJSONObject(0).getString("opponent"))-1).getString("strength_overall_home"));
                                }
                                b.setP_teamW(x1+"");
                                b.setP_oppW(x2+"");
                                System.out.println(b.getP_name());
                                goalkeepers.add(b);
                            }
                        }
                    }
                    System.out.println(goalkeepers.size()+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                    System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
                    Collections.sort(goalkeepers);
                    Collections.sort(defenders);
                    Collections.sort(midfielders);
                    Collections.sort(forwards);
                    for(int i = 0;i<2;i+=1){
                        bestPlayers.add(goalkeepers.get(i));
                    }
                    GridView gridView = (GridView) mView.findViewById(R.id.gridView1);
                    GridActivity3 gridAdapter = new GridActivity3(getContext(), R.layout.must_have_layout, bestPlayers);
                    gridView.setAdapter(gridAdapter);
                    bestPlayers = new ArrayList<BestPlayerX>();
                    for(int i = 0;i<5;i+=1){
                        bestPlayers.add(defenders.get(i));
                    }
                    GridView gridView2 = (GridView) mView.findViewById(R.id.gridView2);
                    GridActivity3 gridAdapter2 = new GridActivity3(getContext(), R.layout.must_have_layout, bestPlayers);
                    gridView2.setAdapter(gridAdapter2);
                    bestPlayers = new ArrayList<BestPlayerX>();
                    for(int i = 0;i<5;i+=1){
                        bestPlayers.add(midfielders.get(i));
                    }
                    GridView gridView3 = (GridView) mView.findViewById(R.id.gridView3);
                    GridActivity3 gridAdapter3 = new GridActivity3(getContext(), R.layout.must_have_layout, bestPlayers);
                    gridView3.setAdapter(gridAdapter3);
                    bestPlayers = new ArrayList<BestPlayerX>();
                    for(int i = 0;i<3;i+=1){
                        bestPlayers.add(forwards.get(i));
                    }
                    GridView gridView4 = (GridView) mView.findViewById(R.id.gridView4);
                    GridActivity3 gridAdapter4 = new GridActivity3(getContext(), R.layout.must_have_layout, bestPlayers);
                    gridView4.setAdapter(gridAdapter4);
                    spinner.setVisibility(View.GONE);
                } catch (JSONException | UnsupportedEncodingException e) {
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
        return v;
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
