package suggestive.search.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class SuggestiveSearchBoxActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
	}

	/**
	 * This function is called when the button1 is clicked
	 */
	public void startSuggestiveSearch(View view) {
		onSearchRequested();
	}

}