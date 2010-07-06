package com.android.launcher;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.StateListDrawable;
import android.widget.ImageView;

public class IconHighlights {
	public IconHighlights(Context context) {
		// TODO Auto-generated constructor stub
	}
	private static Drawable newSelector(Context context){
		GradientDrawable mDrawPressed;
		GradientDrawable mDrawSelected;
		StateListDrawable drawable=new StateListDrawable();
		int selectedColor=AlmostNexusSettingsHelper.getHighlightsColorFocus(context);
		int pressedColor=AlmostNexusSettingsHelper.getHighlightsColor(context);
		int stateFocused = android.R.attr.state_focused;
		int statePressed = android.R.attr.state_pressed;
		int stateWindowFocused = android.R.attr.state_window_focused;
		 
		mDrawSelected = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
		        new int[] { 0x77FFFFFF, selectedColor,selectedColor,selectedColor,selectedColor, 0x77000000 });
		mDrawSelected.setShape(GradientDrawable.RECTANGLE);
		mDrawSelected.setGradientRadius((float)(Math.sqrt(2) * 60));
		mDrawSelected.setCornerRadius(8);
		mDrawPressed = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
				new int[] { 0x77FFFFFF, pressedColor,pressedColor,pressedColor,pressedColor, 0x77000000 });
		mDrawPressed.setShape(GradientDrawable.RECTANGLE);
		mDrawPressed.setGradientRadius((float)(Math.sqrt(2) * 60));
		mDrawPressed.setCornerRadius(8);

		drawable.addState(new int[]{ statePressed}, mDrawPressed);
		drawable.addState(new int[]{ stateFocused, stateWindowFocused}, mDrawSelected);
		drawable.addState(new int[]{stateFocused, -stateWindowFocused}, null);
		drawable.addState(new int[]{-stateFocused, stateWindowFocused}, null);
		drawable.addState(new int[]{-stateFocused, -stateWindowFocused}, null);
		return drawable;
	}
	private static Drawable oldSelector(Context context, boolean forDockbar){
		int selectedColor=AlmostNexusSettingsHelper.getHighlightsColorFocus(context);
		int pressedColor=AlmostNexusSettingsHelper.getHighlightsColor(context);
    	//ADW: Load the specified theme
    	String themePackage=AlmostNexusSettingsHelper.getThemePackageName(context, Launcher.THEME_DEFAULT);
    	Resources themeResources=null;
    	if(themePackage!=Launcher.THEME_DEFAULT){
        	PackageManager pm=context.getPackageManager();
	    	try {
				themeResources=pm.getResourcesForApplication(themePackage);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}else{
    		themeResources=context.getResources();
    	}
    	Drawable drawable=null;
		if(themeResources!=null){
			int resource_id=0;
			if(forDockbar){
				resource_id=themeResources.getIdentifier("dockbar_selector", "drawable", themePackage);
			}else{
				resource_id=themeResources.getIdentifier("shortcut_selector", "drawable", themePackage);
			}
			if(resource_id!=0){
				drawable=themeResources.getDrawable(resource_id);
			}else{
				if(forDockbar){
					drawable=themeResources.getDrawable(R.drawable.dockbar_selector);
				}else{
					drawable=themeResources.getDrawable(R.drawable.shortcut_selector);
				}
			}
		}
		drawable.setColorFilter(pressedColor, Mode.SRC_ATOP);
		return drawable;
	}
	public static Drawable getDrawable(Context context, boolean forDockbar){
		if(AlmostNexusSettingsHelper.getUINewSelectors(context)){
			return newSelector(context);
		}else{
			return oldSelector(context, forDockbar);
		}
	}
}
