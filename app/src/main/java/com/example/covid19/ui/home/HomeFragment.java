package com.example.covid19.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid19.R;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {


    private TextView TotalConfirmed,TotalRecovered,TotalDeaths;
    private ProgressBar progressBar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        TotalConfirmed =root.findViewById(R.id.tvTotalconfirmed);
        TotalDeaths=root.findViewById(R.id.tvTotaldeaths);
        TotalRecovered=root.findViewById(R.id.tvTotalrecovered);
        progressBar=root.findViewById(R.id.progress_circular_home);

        getData();
        return root;
    }

    private void getData() {
        RequestQueue queue= Volley.newRequestQueue(getActivity());
        String url="https://corona.lmao.ninja/v2/all";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    TotalConfirmed.setText(jsonObject.getString("cases"));
                    TotalDeaths.setText(jsonObject.getString("deaths"));
                    TotalRecovered.setText(jsonObject.getString("recovered"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                Log.d("Error Response",error.toString());
            }

    });
        queue.add(stringRequest);
    }
}