package com.ikechi.studio.recyclingview.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ikechi.studio.recyclingview.adapter.SectionedItemAdapter;
import com.ikechi.studio.recyclingview.listener.OnItemClickListener;
import com.ikechi.studio.recyclingview.model.Item;
import com.ikechi.studio.recyclingview.model.ListRow;
import com.ikechi.studio.recyclingview.animator.DefaultItemAnimator;
import com.ikechi.studio.recyclingview.helper.DragDropHelper;
import com.ikechi.studio.recyclingview.view.RecyclingView;
import com.ikechi.studio.recyclingview.decoration.SpacingDecoration;
import com.ikechi.studio.recyclingview.helper.SwipeActionHelper;

import java.util.ArrayList;
import java.util.List;

import com.ikechi.studio.example.R;


/**
 * Demo Activity
 *
 * <p>Demonstrates every engine feature including orientation switching.
 */
public class MainActivity extends Activity {

    // ── Views ─────────────────────────────────────────────────────────────────
    private RecyclingView        mList;
    private Button               mBtnAdd;
    private Button               mBtnClear;
    private Button               mBtnToggleOrientation;
    private TextView             mTvScrollState;

    // ── Adapter ───────────────────────────────────────────────────────────────
    private SectionedItemAdapter mAdapter;
    private int                  mNextId = 100;

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setupList();
        setupButtons();
    }

    // ── View binding ──────────────────────────────────────────────────────────

    private void bindViews() {
        mList                  = (RecyclingView) findViewById(R.id.recycling_view);
        mBtnAdd                = (Button)        findViewById(R.id.btn_add_item);
        mBtnClear              = (Button)        findViewById(R.id.btn_clear_all);
        mBtnToggleOrientation  = (Button)        findViewById(R.id.btn_toggle_orientation);
        mTvScrollState         = (TextView)      findViewById(R.id.tv_scroll_state);
    }

    // ── RecyclingView setup ───────────────────────────────────────────────────

    private void setupList() {
        mAdapter = new SectionedItemAdapter(this, buildSeedRows());

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(Item item, int position) {
					toast("Clicked: " + item.getTitle());
				}
				@Override
				public boolean onItemLongClick(Item item, int position) {
					toast("Long-press: " + item.getTitle());
					return true;
				}
				@Override
				public void onFavoriteClick(Item item, int position, boolean isFavorite) {
					toast((isFavorite ? "★ " : "☆ ") + item.getTitle());
				}
			});

        mList.setItemAnimator(new DefaultItemAnimator());
        mList.addItemDecoration(new SpacingDecoration(dp(4), true, 0x18000000));

        // Swipe helper (works on VERTICAL lists).
        new SwipeActionHelper(mList, new SwipeActionHelper.SwipeCallback() {
				@Override
				public void onSwipeLeft(int position) {
					ListRow row = mAdapter.removeItemAt(position);
					if (row != null && !row.isHeader()) {
						toast("Deleted: " + row.getItem().getTitle());
					}
				}
				@Override
				public void onSwipeRight(int position) {
					List<ListRow> snap = mAdapter.getRows();
					if (position < snap.size()) {
						ListRow row = snap.get(position);
						if (!row.isHeader()) {
							Item item = row.getItem();
							item.setFavorite(!item.isFavorite());
							mAdapter.updateItemAt(position, item);
							toast((item.isFavorite() ? "★ " : "☆ ") + item.getTitle());
						}
					}
				}
			});

        // Drag-drop helper (works on both orientations).
        new DragDropHelper(mList, new DragDropHelper.DragCallback() {
				@Override
				public void onItemMoved(int fromPosition, int toPosition) {
					mAdapter.moveItem(fromPosition, toPosition);
					toast("Moved " + fromPosition + " → " + toPosition);
				}
			});

        mList.addOnScrollListener(new RecyclingView.OnScrollListener() {
				@Override
				public void onScrollStateChanged(RecyclingView view, int newState) {
					String label;
					switch (newState) {
						case RecyclingView.SCROLL_STATE_DRAGGING: label = "Dragging"; break;
						case RecyclingView.SCROLL_STATE_SETTLING: label = "Settling"; break;
						default:                                  label = "Idle";     break;
					}
					mTvScrollState.setText("Scroll: " + label);
				}
				@Override
				public void onScrolled(RecyclingView view, int dOffset) { /* no-op */ }
			});

        mList.setAdapter(mAdapter);
    }

    // ── Buttons ───────────────────────────────────────────────────────────────

    private void setupButtons() {
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mNextId++;
					Item item = new Item(mNextId,
										 "Item #" + mNextId,
										 "Swipe left = delete  ·  Swipe right = fav  ·  Long-press to drag",
										 "Dynamic", false);
					mAdapter.addItem(item);
					mList.smoothScrollToPosition(mAdapter.getItemCount() - 1);
				}
			});

        mBtnClear.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mAdapter.clearAll();
					toast("Cleared.");
				}
			});

        mBtnToggleOrientation.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mList.getOrientation() == RecyclingView.VERTICAL) {
						mList.setOrientation(RecyclingView.HORIZONTAL);
						mBtnToggleOrientation.setText("Vertical");
					} else {
						mList.setOrientation(RecyclingView.VERTICAL);
						mBtnToggleOrientation.setText("Horizontal");
					}
				}
			});
    }

    // ── Seed data ─────────────────────────────────────────────────────────────

    private List<ListRow> buildSeedRows() {
        List<ListRow> rows = new ArrayList<ListRow>();

        rows.add(ListRow.header("★  Core Engine"));
        rows.add(ListRow.item(new Item(1,  "Custom ViewGroup",        "Extends ViewGroup.",             "Widget",       true)));
        rows.add(ListRow.item(new Item(2,  "View Recycling",          "Per-type scrap pool via SparseArray.",          "Optimisation", false)));
        rows.add(ListRow.item(new Item(3,  "Horizontal + Vertical",   "Swap orientation at any time.",                 "Feature",      true)));
        rows.add(ListRow.item(new Item(4,  "Binary-Search Layout",    "O(log n) visible-range detection.",             "Algorithms",   false)));

        rows.add(ListRow.header("✦  Animation"));
        rows.add(ListRow.item(new Item(5,  "Add Animation",           "Slide-in + fade + overshoot, 380 ms.",         "Animation",    true)));
        rows.add(ListRow.item(new Item(6,  "Remove Animation",        "Fly-right + fade-out, 280 ms.",                "Animation",    false)));
        rows.add(ListRow.item(new Item(7,  "Move Animation",          "translateY/X from old → new, 260 ms.",         "Animation",    false)));
        rows.add(ListRow.item(new Item(8,  "Change Animation",        "Scale-pulse + alpha flash, 220 ms.",           "Animation",    false)));

        rows.add(ListRow.header("⟺  Interactions"));
        rows.add(ListRow.item(new Item(9,  "Swipe Left → Delete",     "Canvas-drawn red action + dismiss.",           "Gesture",      false)));
        rows.add(ListRow.item(new Item(10, "Swipe Right → Favourite", "Canvas-drawn green action + spring-back.",     "Gesture",      false)));
        rows.add(ListRow.item(new Item(11, "Drag & Drop Reorder",     "Bitmap shadow, both orientations.",            "Gesture",      false)));
        rows.add(ListRow.item(new Item(12, "Edge Glow",               "android.widget.EdgeEffect, API 14+.",          "Polish",       true)));

        rows.add(ListRow.header("⚙  Library Architecture"));
        rows.add(ListRow.item(new Item(13, "RecyclingAdapter",        "Abstract base — implement to build adapters.",  "API",          false)));
        rows.add(ListRow.item(new Item(14, "BaseListAdapter<T>",      "Generic base for single-model lists.",          "API",          true)));
        rows.add(ListRow.item(new Item(15, "ItemAnimator",            "Pluggable — swap DefaultItemAnimator anytime.", "API",          false)));
        rows.add(ListRow.item(new Item(16, "ItemDecoration",          "Draw before/after; inject spacing offsets.",    "API",          false)));
        rows.add(ListRow.item(new Item(17, "OnItemTouchListener",     "Intercept pipeline for swipe + drag helpers.",  "API",          false)));

        return rows;
    }

    // ── Util ──────────────────────────────────────────────────────────────────

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private int dp(int v) {
        return Math.round(v * getResources().getDisplayMetrics().density);
    }
}

