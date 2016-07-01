package com.udacity.gradle.builditbigger;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import gj.udacity.builditbigger.backend.myApi.MyApi;
import gj.udacity.project1.androidlib.LibraryJokeDisplay;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment {


    public MainActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        Button jokeButton = (Button) root.findViewById(R.id.jokeButton);

        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EndpointsAsyncTask().execute(getContext());
            }
        });

        return root;
    }
    class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
        private MyApi myApiService = null;
        private Context context;
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Loading");
            dialog.setMessage("Please Wait While we fetch a Joke for you!");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        @Override
        protected String doInBackground(Context... params) {
            if(myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://striped-graph-135623.appspot.com/_ah/api/");
            /*
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            */

                myApiService = builder.build();
            }

            context = params[0];

            try {
                return myApiService.giveJoke().execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.cancel();
            Intent joke = new Intent(context, LibraryJokeDisplay.class);
            joke.putExtra("Joke",result);
            context.startActivity(joke);
        }
    }

}
