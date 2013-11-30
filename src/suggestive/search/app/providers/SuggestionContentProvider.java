package suggestive.search.app.providers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

public class SuggestionContentProvider extends ContentProvider {

	public static final String AUTHORITY = "suggestive.search.app.providers.SuggestionContentProvider";

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * This function puts the suggestion in a Cursor
	 * 
	 * @return Cursor
	 */
	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		String suggestions = getSuggestion(arg0.getLastPathSegment());
		String[] columnNames = { "_id", "suggest_text_1" };
		MatrixCursor matrixCursor = new MatrixCursor(columnNames);
		try {
			JSONObject jsonObject = new JSONObject(suggestions);
			JSONArray jsonArray = jsonObject.getJSONArray("tags");
			Log.d("yeah", jsonArray.toString());
			for (int i = 0; i < jsonArray.length(); i++) {
				matrixCursor.addRow(new Object[] { i, jsonArray.getString(i) });
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return matrixCursor;
	}

	/**
	 * This function fetches the suggestions from the server
	 * 
	 * @return String
	 */
	private String getSuggestion(String query) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(
				"http://ec2-54-213-110-181.us-west-2.compute.amazonaws.com:8080/offers/tags/?q="
						+ query);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e("Error", "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}
