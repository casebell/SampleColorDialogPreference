/**
 * Copyright CMW Mobile.com, 2011. 
 */
package com.cmwmobile.android.samples;

import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.TypefaceSpan;

/**
 * The SampleColorDialogPreferenceSettings is responsible for the handling of
 * this sample settings.
 * @author Casper Wakkers
 */
public class SampleColorDialogPreferenceSettings extends
		PreferenceActivity  {		
	/**
	 * {@inheritDoc}
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		bindPreferenceSummaryToValue(findPreference("color"));
		bindPreferenceSummaryToValue(findPreference("font"));
	}
	/**
	 * {@inheritDoc}
	 */
	protected void onDestroy() {
		super.onDestroy();
	}
	/**
	 * {@inheritDoc}
	 */
	protected void onResume() {
		super.onResume();
	}
	
		
	/**
	 * A preference value change listener that updates the preference's summary
	 * to reflect its new value.
	 */
	private Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = 
			new Preference.OnPreferenceChangeListener() {
		@Override
		public boolean onPreferenceChange(Preference preference, Object value) {
			String key = preference.getKey();
			String stringValue = value.toString();
			Object summary = preference.getSummary();
			
			if( key.equals("color")){
				summary = preference.getSummary();
				SpannableStringBuilder sb =
						new SpannableStringBuilder (summary.toString());
				sb.setSpan(
						new BackgroundColorSpan( Integer.parseInt(stringValue) ), 
						0, 
						sb.length(),
						Spanned.SPAN_INCLUSIVE_EXCLUSIVE );
				
				preference.setSummary(sb);
				
				//preference.setSummary(
					//	Html.fromHtml("<font color=\"#B0C4DE\">This is content</font>"));
			}
			else if ( key.equals("font")) {
				TypefaceSpan tfaceSpan = new TypefaceSpan(stringValue);
				Typeface tface = null;
						
				String fontPath = PreferenceManager
									.getDefaultSharedPreferences(preference.getContext())
									.getString(stringValue, null);
				
				if( fontPath != null ) {
					tface = Typeface.createFromFile( fontPath );
				}
				
				SpannableStringBuilder sb =
						new SpannableStringBuilder (summary.toString());
				sb.setSpan(
						new CustomTypefaceSpan(stringValue, tface),
						0, 
						sb.length(),
						Spanned.SPAN_INCLUSIVE_EXCLUSIVE );
				
				preference.setSummary(sb);
			}
			
			
			return true;
		}
	};

	/**
	 * Binds a preference's summary to its value. More specifically, when the
	 * preference's value is changed, its summary (line of text below the
	 * preference title) is updated to reflect the value. The summary is also
	 * immediately updated upon calling this method. The exact display format is
	 * dependent on the type of preference.
	 * 
	 * @see #sBindPreferenceSummaryToValueListener
	 */
	private void bindPreferenceSummaryToValue(Preference preference) {
		// Set the listener to watch for value changes.
		preference
				.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

		// Trigger the listener immediately with the preference's
		// current value.
		sBindPreferenceSummaryToValueListener.onPreferenceChange(
				preference,
				PreferenceManager.getDefaultSharedPreferences(
						preference.getContext()).getString(preference.getKey(),
						""));
	}
}
