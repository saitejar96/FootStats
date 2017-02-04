package com.example.saiteja.myfootballapp;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GPFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GPFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    GoldenPlayer gpx ;
    private ListView lv;
    ArrayList<String> products = new  ArrayList<String>();
    ArrayList<String> urls = new  ArrayList<String>();
    ArrayList<String> points = new  ArrayList<String>();

    // Listview Adapter
    ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;
    ProgressBar pg;


    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;

    private OnFragmentInteractionListener mListener;

    public GPFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GPFragment newInstance(String param1, String param2) {
        GPFragment fragment = new GPFragment();
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
        View v =  inflater.inflate(R.layout.fragment_gp, container, false);
        final View mView = v;
        pg = (ProgressBar)mView.findViewById(R.id.progressBarX);
        pg.setVisibility(View.INVISIBLE);pg.setVisibility(View.VISIBLE);

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
                String str = "";
                JSONObject json;
                JSONArray phases;
                JSONArray elements;
                JSONArray teams;
                JSONArray element_types;
                JSONArray events;
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
                    for(int i=0;i<elements.length();i+=1){
                        products.add(elements.getJSONObject(i).getString("web_name"));
                        points.add(elements.getJSONObject(i).getString("event_points")+"");
                        urls.add("https://platform-static-files.s3.amazonaws.com/premierleague/photos/players/110x140/p"+elements.getJSONObject(i).getString("photo").substring(0,elements.getJSONObject(i).getString("photo").length()-4)+".png");
                    }
                    pg.setVisibility(View.INVISIBLE);
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
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                pg.setVisibility(View.VISIBLE);
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    GoldenPlayer note = noteDataSnapshot.getValue(GoldenPlayer.class);
                    System.out.println("#################################");
                    System.out.println(note);
                    if(note.getUser().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        gpx=note;
                        if(note.getPlayer().length()>0) {

                            TextView tx1 = (TextView) mView.findViewById(R.id.xyz);
                            tx1.setText("You Chose:");
                            TextView tx = (TextView) mView.findViewById(R.id.gpTxt);
                            tx.setText(note.getPlayer());
                            ImageView i = (ImageView) mView.findViewById(R.id.gpImg);
                            Picasso.with(getContext()).load(note.getImg()).into(i);

                            pg.setVisibility(View.INVISIBLE);
                        }
                        else{
                            TextView tx = (TextView) mView.findViewById(R.id.xyz);
                            tx.setText("You have not chosen anybody.Please choose from the below!");

                            pg.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });


        lv = (ListView) mView.findViewById(R.id.list_view);
        inputSearch = (EditText) mView.findViewById(R.id.inputSearch);

        // Adding items to listview
        adapter = new ArrayAdapter<String>(getContext(), R.layout.list_item, R.id.player_name, products);
        lv.setAdapter(adapter);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                GPFragment.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        ListView txtw = (ListView) mView.findViewById(R.id.list_view);
        txtw.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pg.setVisibility(View.VISIBLE);
        TextView t = (TextView) view.findViewById(R.id.player_name) ;
        String p = t.getText().toString();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        gpx.setPlayer(p);
        gpx.setImg(urls.get(products.indexOf(p)));
        gpx.setTstamp(new Date());
        gpx.setPoints(points.get(products.indexOf(p)));
        database.child("notes").child(gpx.getUser()).setValue(gpx);
        pg.setVisibility(View.INVISIBLE);
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
