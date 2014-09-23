package com.devtee.olxtest;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.devtee.olxtest.model.Result;

public class MainActivity extends Activity{

	private Context context;
	private RequestQueue queue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = MainActivity.this;
		
		queue = Volley.newRequestQueue(context);
		
		getJSONFromUrl();
	}
	
	private void getJSONFromUrl() {
		String url = "https://api.stackexchange.com/2.2/questions?order=desc&sort=activity&site=stackoverflow";
	
		JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						
						Result result = new Result(response);
						
						Log.v("DEV", "" + result.items.size());
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						
					}				
				});
		
		queue.add(getRequest);
	}
	
}
