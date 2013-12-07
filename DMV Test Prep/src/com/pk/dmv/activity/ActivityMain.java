package com.pk.dmv.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.pk.dmv.R;
import com.pk.dmv.adapter.MenuAdapter;
import com.pk.dmv.adapter.MenuAdapter.OnItemClickListener;
import com.pk.dmv.util.AlphaForegroundColorSpan;
import com.pk.dmv.view.KenBurnsView;

public class ActivityMain extends Activity {
    
	// Menu list
	private ListView mList;
	private List<Integer> mItems;
	private MenuAdapter mAdapter;
	
	// Fancy action bar variables and whatnot
    private int mActionBarTitleColor;
    private int mActionBarHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
	
	private KenBurnsView mHeaderPicture;
    private ImageView mHeaderLogo;
    private View mHeader;
    private View mPlaceHolderView;
    private AccelerateDecelerateInterpolator mSmoothInterpolator;
    
    private RectF mRect1 = new RectF();
    private RectF mRect2 = new RectF();

    private AlphaForegroundColorSpan mAlphaForegroundColorSpan;
    private SpannableString mSpannableString;
    private SpannableString mSpannableString2;

    private TypedValue mTypedValue = new TypedValue();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mSmoothInterpolator = new AccelerateDecelerateInterpolator();
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mHeaderHeight + getActionBarHeight();
		
		setContentView(R.layout.activity_main);
		
		initViews();
		mActionBarTitleColor = getResources().getColor(R.color.white);
	    mSpannableString = new SpannableString(getString(R.string.app_name));
	    mSpannableString2 = new SpannableString("California");
	    mAlphaForegroundColorSpan = new AlphaForegroundColorSpan(mActionBarTitleColor);
	    setupActionBar();
		initMenu();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_info:
				// TODO Open information dialog
				Toast.makeText(this, "Information stuff goes here.", Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void initViews() {
		mList = (ListView) findViewById(R.id.ListView);
        mHeader = findViewById(R.id.header);
        mHeaderPicture = (KenBurnsView) findViewById(R.id.header_picture);
        mHeaderPicture.setResourceIds(R.drawable.pic0, R.drawable.pic1, R.drawable.pic2);
        mHeaderLogo = (ImageView) findViewById(R.id.header_logo);
	}
	
	private void initMenu() {
		mItems = new ArrayList<Integer>();

		mItems.add(MenuAdapter.PRACTICE_TEST);
		mItems.add(MenuAdapter.TEST_OVERVIEW);
		mItems.add(MenuAdapter.TEST_HISTORY);
		mItems.add(MenuAdapter.FLASH_CARDS);
		mItems.add(MenuAdapter.SIGNS);
		mItems.add(MenuAdapter.HANDBOOK);
		mItems.add(MenuAdapter.FIND_LOCAL);
		mItems.add(MenuAdapter.CONTRIBUTE);
		mItems.add(MenuAdapter.SETTINGS);
		
		mAdapter = new MenuAdapter(this, mItems, getMenuListener());
		mPlaceHolderView = getLayoutInflater().inflate(R.layout.view_header_placeholder, mList, false);
        mList.addHeaderView(mPlaceHolderView);
		mList.setAdapter(mAdapter);
		
		// Listeners
        mList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int scrollY = getScrollY();
                // Sticky actionbar
                mHeader.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation));
                // Header_logo --> actionbar icon
                float ratio = clamp(mHeader.getTranslationY() / mMinHeaderTranslation, 0.0f, 1.0f);
                interpolate(mHeaderLogo, getActionBarIconView(), mSmoothInterpolator.getInterpolation(ratio));
                setTitleAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));
            }
        });
	}
	
	private OnItemClickListener getMenuListener()
	{
		return new OnItemClickListener()
		{
			@Override
			public void onItemClick(int position, View view)
			{
				Intent intent = null;
				switch(position)
				{
					case MenuAdapter.PRACTICE_TEST:
						intent = new Intent(ActivityMain.this, ActivityPracticeTest.class);
						break;
					case MenuAdapter.TEST_OVERVIEW:
						intent = new Intent(ActivityMain.this, ActivityTestOverview.class);
						break;
					case MenuAdapter.TEST_HISTORY:
						intent = new Intent(ActivityMain.this, ActivityTestHistory.class);
						break;
					case MenuAdapter.FLASH_CARDS:
						intent = new Intent(ActivityMain.this, ActivityFlashCards.class);
						break;
					case MenuAdapter.SIGNS:
						intent = new Intent(ActivityMain.this, ActivitySigns.class);
						break;
					case MenuAdapter.HANDBOOK:
						intent = new Intent(ActivityMain.this, ActivityHandbook.class);
						break;
					case MenuAdapter.FIND_LOCAL:
						intent = new Intent(ActivityMain.this, ActivityFindLocal.class);
						break;
					case MenuAdapter.CONTRIBUTE:
						intent = new Intent(ActivityMain.this, ActivityContribute.class);
						break;
					case MenuAdapter.SETTINGS:
						intent = new Intent(ActivityMain.this, ActivitySettings.class);
						break;
					default:
						Toast.makeText(ActivityMain.this, getResources().getString(R.string.invalid_selection), Toast.LENGTH_LONG).show();
						break;
				}
				
				if(intent != null) {
					startActivity(intent);
					overridePendingTransition(R.anim.fslide_right_in, R.anim.fslide_left_out);
				}
			}

			@Override
			public boolean onItemLongClick(int position, View view) 
			{
				return false;
			}
		};
	}
	
	/** Everything below this is for the fancy action bar. **/
    private void setTitleAlpha(float alpha) {
        mAlphaForegroundColorSpan.setAlpha(alpha);
        mSpannableString.setSpan(mAlphaForegroundColorSpan, 0, mSpannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSpannableString2.setSpan(mAlphaForegroundColorSpan, 0, mSpannableString2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getActionBar().setTitle(mSpannableString);
        getActionBar().setSubtitle(mSpannableString2);
    }

    public static float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, min), max);
    }

    private void interpolate(View view1, View view2, float interpolation) {
        getOnScreenRect(mRect1, view1);
        getOnScreenRect(mRect2, view2);

        float scaleX = 1.0F + interpolation * (mRect2.width() / mRect1.width() - 1.0F);
        float scaleY = 1.0F + interpolation * (mRect2.height() / mRect1.height() - 1.0F);
        float translationX = 0.5F * (interpolation * (mRect2.left + mRect2.right - mRect1.left - mRect1.right));
        float translationY = 0.5F * (interpolation * (mRect2.top + mRect2.bottom - mRect1.top - mRect1.bottom));

        view1.setTranslationX(translationX);
        view1.setTranslationY(translationY - mHeader.getTranslationY());
        view1.setScaleX(scaleX);
        view1.setScaleY(scaleY);
    }

    private RectF getOnScreenRect(RectF rect, View view) {
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        return rect;
    }

    public int getScrollY() {
        View c = mList.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = mList.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mPlaceHolderView.getHeight();
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    private void setupActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.ic_transparent);
    }

    private ImageView getActionBarIconView() {
        return (ImageView) findViewById(android.R.id.home);
    }

    public int getActionBarHeight() {
        if (mActionBarHeight != 0) {
            return mActionBarHeight;
        }
        getTheme().resolveAttribute(android.R.attr.actionBarSize, mTypedValue, true);
        mActionBarHeight = TypedValue.complexToDimensionPixelSize(mTypedValue.data, getResources().getDisplayMetrics());
        return mActionBarHeight;
    }
}
