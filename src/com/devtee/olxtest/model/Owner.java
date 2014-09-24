package com.devtee.olxtest.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Owner implements Serializable{

	private static final long serialVersionUID = 370472116586083034L;
	private static final String KEY_REPUTATION = "reputation";
	private static final String KEY_USER_ID = "user_id";
	private static final String KEY_USER_TYPE = "user_type";
	private static final String KEY_ACCEPT_RATE = "accept_rate";
	private static final String KEY_PROFILE_IMAGE = "profile_image";
	private static final String KEY_DISPLAY_NAME = "display_name";
	private static final String KEY_LINK = "link";
	
	public String reputation, user_id, user_type, accept_rate, profile_image, display_name, link;
	
	public Owner(JSONObject json){
		try {
			reputation = json.getString(KEY_REPUTATION);
			user_id = json.getString(KEY_USER_ID);
			user_type = json.getString(KEY_USER_TYPE);
			
			if(json.has(KEY_ACCEPT_RATE)){
				accept_rate = json.getString(KEY_ACCEPT_RATE);
			}
		
			profile_image = json.getString(KEY_PROFILE_IMAGE);
			display_name = json.getString(KEY_DISPLAY_NAME);
			link = json.getString(KEY_LINK);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
