package com.rudigo.android.maths;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created by user on 9/4/2017.
 */


public class BlcTest4Fragment extends Fragment {

    //connectivity manager instance
    private ConnectivityManager mConnMgr;


    private boolean isNetworkAvailable;


    private NetworkInfo activeNetwork;

    //Broadcast Receiver instance
    public NetworkReceiver mReceiver;

    //These variables are used to display the fragments
    Fragment frag;
    FragmentTransaction fragTransaction;

    private final int REQUEST_SPEECH_CODE = 143;


    private ArrayAdapter<Integer> numberAdapter;

    private GridView gridView;
    private ArrayList<Integer> numbers;

    private ArrayList<String> numberText;

    public BlcTest4Fragment() {
    }


    //This method is used to inflate the fragment layout when it's called
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View rootView = inflater.inflate(R.layout.blc_test_fragment, container, false);

        final Button repeatButton = rootView.findViewById(R.id.repeatLesson1Btn);
        final Button continueButton = rootView.findViewById(R.id.blcTestContinueBtn);

        repeatButton.setText("Repeat Lesson 4");

        mConnMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        activeNetwork = mConnMgr.getActiveNetworkInfo();
//        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        //instantiates the Network Events Broadcast Receiver
        mReceiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        //Register the broadcast receiver with Intent action CONNECTIVITY_ACTION
        //Broadcast receiver's onReceive will be called once a network happens
        getActivity().registerReceiver(mReceiver, filter);


        numbers = new ArrayList<>();
        for (int i = 51; i <= 100; ++i) {
            numbers.add(i);
        }

        numberText = new ArrayList<>();
        numberText.add("fifty-one");
        numberText.add("fifty-two");
        numberText.add("fifty-three");
        numberText.add("fifty-four");
        numberText.add("fifty-five");
        numberText.add("fifty-six");
        numberText.add("fifty-seven");
        numberText.add("fifty-eight");
        numberText.add("fifty-nine");
        numberText.add("sixty");
        numberText.add("sixty-one"); //"\"sixty-one\""
        numberText.add("sixty-two");
        numberText.add("sixty-three");
        numberText.add("sixty-four");
        numberText.add("sixty-five");
        numberText.add("sixty-six");
        numberText.add("sixty-seven");
        numberText.add("sixty-eigth");
        numberText.add("sixty-nine");
        numberText.add("seventy");
        numberText.add("seventy-one");
        numberText.add("seventy-two");
        numberText.add("seventy-three");
        numberText.add("seventy-four");
        numberText.add("seventy-five");
        numberText.add("seventy-six");
        numberText.add("seventy-seven");
        numberText.add("seventy-eight");
        numberText.add("seventy-nine");
        numberText.add("eighty");
        numberText.add("eighty-one");
        numberText.add("eighty-two");
        numberText.add("eighty-three");
        numberText.add("eighty-four");
        numberText.add("eighty-five");
        numberText.add("eighty-six");
        numberText.add("eighty-seven");
        numberText.add("eighty-eight");
        numberText.add("eighty-nine");
        numberText.add("ninety");
        numberText.add("ninety-one");
        numberText.add("ninety-two");
        numberText.add("ninety-three");
        numberText.add("ninety-four");
        numberText.add("ninety-five");
        numberText.add("ninety-six");
        numberText.add("ninety-seven");
        numberText.add("ninety-eight");
        numberText.add("ninety-nine");
        numberText.add("hundred");

        numberAdapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_list_item_1, numbers);
        gridView = rootView.findViewById(R.id.test_gridView);
        gridView.setAdapter(numberAdapter);

        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //repeat lesson 4b
                Intent intent = new Intent(getActivity(), BlcLesson4bActivity.class);
                startActivity(intent);

            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragTransaction = getFragmentManager().beginTransaction();
                fragTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                frag = new BlcLesson4cFragment();
                fragTransaction.replace(R.id.main_container, frag);
                fragTransaction.addToBackStack(null);
                fragTransaction.commit();

            }
        });
        return rootView;
    }

    private void recordSpeech() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak the number clicked");

        try {

            startActivityForResult(intent, REQUEST_SPEECH_CODE);

        } catch (ActivityNotFoundException tim) {
            Toast.makeText(getContext(), "You device does not support speech to text", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_SPEECH_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> voiceInText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Toast.makeText(getContext(), voiceInText.get(0), Toast.LENGTH_SHORT).show();

                }
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unRegisters the broadcast receiver whent the activity goes to the background
        if (mReceiver != null) {
            getActivity().unregisterReceiver(mReceiver);
        }
    }

    //Broadcast receiver whose onReceive will be called whenever a network event such as
    //network disconnected or network connected takes place
    //The Broadcast Receiver is registered in the onCreate with Intent action CONNECTIVITY_ACTION
    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //get active network info structure

            isNetworkAvailable = activeNetwork != null && (
                    mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting() ||
                            mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting());

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (isNetworkAvailable) {
                        Toast.makeText(getContext(), "Network Available", Toast.LENGTH_SHORT).show();
                        recordSpeech();
                        // Toast.makeText(getContext(), numberText.get(position), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Network not available", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}


