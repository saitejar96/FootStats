package com.example.saiteja.myfootballapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CompareFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CompareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompareFragment extends Fragment implements AdapterView.OnItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String p = "Select a player";

    ArrayAdapter<String> adapter;
    ArrayList<String> products = new  ArrayList<String>();
    ArrayList<String> fnames = new  ArrayList<String>();
    TextView tv1;
    TextView tv2;
    ArrayList<String> urls = new  ArrayList<String>();
    ArrayList<String> points = new  ArrayList<String>();
    ArrayList<JSONObject> jsonObjects = new ArrayList<>();
    Intent intent;

    private OnFragmentInteractionListener mListener;
    private ProgressBar spinner;
    EditText inputSearch;
    Button compare;
    private ListView lv;

    public CompareFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompareFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompareFragment newInstance(String param1, String param2) {
        CompareFragment fragment = new CompareFragment();
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
        View v =  inflater.inflate(R.layout.fragment_compare, container, false);
        final View mView = v;
        compare = (Button)mView.findViewById(R.id.cmpbutton);
        compare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity().getBaseContext(),MainActivity.class);
                intent.putExtra("player1",tv1.getText().toString());
                intent.putExtra("player2",tv2.getText().toString());
                intent.putExtra("flag",1);
                getActivity().startActivity(intent);
            }
        });
        AsyncHttpClient client = new AsyncHttpClient();
        spinner=(ProgressBar)mView.findViewById(R.id.progressBarD);
        spinner.setVisibility(View.VISIBLE);
        client.get("https://fantasy.premierleague.com/drf/bootstrap-static", new AsyncHttpResponseHandler(){
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
                        jsonObjects.add(elements.getJSONObject(i));
                        products.add(elements.getJSONObject(i).getString("web_name")+"("+elements.getJSONObject(i).getString("first_name")+")");
                        fnames.add(elements.getJSONObject(i).getString("first_name"));
                        points.add(elements.getJSONObject(i).getString("event_points")+"");
                        urls.add("https://platform-static-files.s3.amazonaws.com/premierleague/photos/players/110x140/p"+elements.getJSONObject(i).getString("photo").substring(0,elements.getJSONObject(i).getString("photo").length()-4)+".png");
                    }
                    spinner.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

        lv = (ListView) mView.findViewById(R.id.list_view);
        inputSearch = (EditText) mView.findViewById(R.id.inputSearch1);
        tv1 = (TextView) mView.findViewById(R.id.tv1);
        tv2 = (TextView) mView.findViewById(R.id.tv2);

        adapter = new ArrayAdapter<String>(getContext(), R.layout.list_item, R.id.player_name, products);
        lv.setAdapter(adapter);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                CompareFragment.this.adapter.getFilter().filter(cs);
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
        lv.setOnItemClickListener(this);
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
        getActivity().startActivity(intent);
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView t = (TextView) view.findViewById(R.id.player_name) ;
        p = t.getText().toString();
        System.out.println(p);
        if(!tv1.getText().toString().contentEquals("Player 1 :"))   tv2.setText(p);
        else    tv1.setText(p);
        inputSearch = (EditText) getView().findViewById(R.id.inputSearch1);
        inputSearch.setText("");
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
