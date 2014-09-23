package com.devtee.olxtest.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Result implements Serializable {

	private static final long serialVersionUID = -559428104442398123L;
	private static final String KEY_ITEMS = "items";
	private static final String KEY_HAS_MORE = "has_more";
	private static final String KEY_QUOTA_MAX = "quota_max";
	private static final String KEY_QUOTA_REMAINING = "quota_remaining";

	public ArrayList<Items> items = new ArrayList<Items>();
	public String has_more, quota_max, quota_remaining;
	
	public Result(JSONObject json) {
		try {
			JSONArray itemArr = json.getJSONArray(KEY_ITEMS);
			for(int i = 0; i < itemArr.length(); i++){
				Items newItem = new Items(itemArr.getJSONObject(i));
				items.add(newItem);
			}
			
			has_more = json.getString(KEY_HAS_MORE);
			quota_max = json.getString(KEY_QUOTA_MAX);
			quota_remaining = json.getString(KEY_QUOTA_REMAINING);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
