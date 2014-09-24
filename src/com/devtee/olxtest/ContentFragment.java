package com.devtee.olxtest;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.devtee.olxtest.model.Items;
import com.devtee.olxtest.model.Result;

public class ContentFragment extends Fragment{
	
	private Context context;
	private ListView listView;
	private ProgressBar progressBar;
	private RequestQueue queue;
	private Result result;
	private String url;
	
	public ContentFragment newInstance(String url) {
		ContentFragment fragment = new ContentFragment();
		fragment.url = url;
		return fragment;
	}	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		queue = Volley.newRequestQueue(context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_content, container, false);
		listView = (ListView) view.findViewById(R.id.listView);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		getJSONFromUrl();
	}

	private void getJSONFromUrl() {
		JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						
						progressBar.setVisibility(View.GONE);
						result = new Result(response);
						
						ListContentAdapter adapter = new ListContentAdapter(result.items);
						listView.setAdapter(adapter);
						listView.setOnItemClickListener(new OnListItemClick());
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						
					}				
				});
		
		queue.add(getRequest);
	}
	
	private class ListContentAdapter extends ArrayAdapter<Items> {

		private List<Items> itemList;
		
		public ListContentAdapter(List<Items> objects) {
			super(context, 0, objects);
			this.itemList = objects;
		}

		@Override
		public int getCount() {
			return itemList.size();
		}

		@Override
		public Items getItem(int position) {
			return itemList.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = convertView;
			ViewHolder holder;
			
			if(view == null) {
				view = inflater.inflate(R.layout.layout_listview_item, parent, false);
				holder = new ViewHolder();
				
				holder.textVotes = (TextView) view.findViewById(R.id.textVotes);
				holder.textAnswers = (TextView) view.findViewById(R.id.textAnswers);
				holder.textTitle = (TextView) view.findViewById(R.id.textTitle);
				holder.textTag = (TextView) view.findViewById(R.id.textTag);
				holder.textOwner = (TextView) view.findViewById(R.id.textOwner);
				
				view.setTag(holder);
			} else {
				
				holder = (ViewHolder) view.getTag();
			}			
			
			holder.textVotes.setText(itemList.get(position).score);
			holder.textAnswers.setText(itemList.get(position).answer_count);
			holder.textTitle.setText(itemList.get(position).title);
			
			String tag = "";
			for(String str : itemList.get(position).tags){
				tag = tag + str + " ,";
			}
			
			holder.textTag.setText(tag);
			holder.textOwner.setText(itemList.get(position).owner.display_name);
			
			return view;
		}
		
		class ViewHolder {
			TextView textVotes, textAnswers, textTitle, textTag, textOwner;
		}
	} 
	
	private class OnListItemClick implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			Intent intent = new Intent(context, ContentActivity.class);
			intent.putExtra(ContentActivity.TAG_URL, result.items.get(position).link);
			intent.putExtra(ContentActivity.TAG_TITLE, result.items.get(position).title);
			startActivity(intent);
		}
	}
	
}
