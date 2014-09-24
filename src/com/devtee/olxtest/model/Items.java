package com.devtee.olxtest.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Items implements Serializable{

	private static final long serialVersionUID = -5016211648992026651L;
	private static final String KEY_TAGS = "tags";
	private static final String KEY_OWNER = "owner";
	private static final String KEY_IS_ANSWERED = "is_answered";
	private static final String KEY_VIEW_COUNT = "view_count";
	private static final String KEY_ANSWER_COUNT = "answer_count";
	private static final String KEY_SCORE = "score";
	private static final String KEY_LAST_ACTIVITY_DATE = "last_activity_date";
	private static final String KEY_CREATION_DATE = "creation_date";
	private static final String KEY_LAST_EDIT_DATE = "last_edit_date";
	private static final String KEY_QUESTION_ID = "question_id";
	private static final String KEY_LINK = "link";
	private static final String KEY_TITLE = "title";
	
	public ArrayList<String> tags = new ArrayList<String>();
	public Owner owner;
	public String is_answered, view_count, answer_count, score, last_activity_date, creation_date,
		last_edit_date, question_id, link, title;
	
	public Items(JSONObject json) {
		try {
			JSONArray tagArr = json.getJSONArray(KEY_TAGS);
			for(int i = 0; i < tagArr.length(); i++){
				String tag = tagArr.getString(i);
				tags.add(tag);
			}
			
			owner = new Owner(json.getJSONObject(KEY_OWNER));
			
			is_answered = json.getString(KEY_IS_ANSWERED);
			view_count = json.getString(KEY_VIEW_COUNT);
			answer_count = json.getString(KEY_ANSWER_COUNT);
			score = json.getString(KEY_SCORE);
			last_activity_date = json.getString(KEY_LAST_ACTIVITY_DATE);
			creation_date = json.getString(KEY_CREATION_DATE);
			
			if(json.has(KEY_LAST_EDIT_DATE)){
				last_edit_date = json.getString(KEY_LAST_EDIT_DATE);				
			}

			question_id = json.getString(KEY_QUESTION_ID);
			link = json.getString(KEY_LINK);
			title = json.getString(KEY_TITLE);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
