# RecyclingView

**A high‑performance, fully custom scrolling container built entirely from scratch on the pure Android SDK.**

No `RecyclerView`. No Jetpack. No external libraries.  
Just clean, efficient Java that gives you a powerful, flexible list with a tiny footprint.

---

## ✨ Features

### Core scrolling engine
- 🧩 **100 % standalone** – extends `ViewGroup` directly; zero dependencies.
- ⚡ **Smart view recycling** – re‑uses item views as they scroll off‑screen to minimise memory and layout inflation.
- 📐 **Vertical & horizontal** – switch orientation with a single attribute or method call.
- 🔄 **Reverse layout** – adapter position `0` at the trailing edge; perfect for chat and timelines.
- 🎯 **Snap‑to‑item** – flings snap to the nearest item boundary, giving a page‑like experience.
- 🚀 **Fling with velocity tracking** – smooth, physics‑based fling via `Scroller`.
- 🌈 **Edge effect** – glow at the leading and trailing edges on supported devices.

### Visual polish
- 🖌️ **Custom scrollbar** – show/hide the indicator bar and change its colour.
- 📏 **Uniform item spacing** – add spacing between items without manual item decorations.
- ✨ **Rich animations** – add, remove, move, change, and **continuous ID‑safe pulse** animations.
- 🎨 **Item decorations** – draw behind or on top of items, or add offsets.

### Interaction
- 🖐️ **Touch interceptors** – pluggable `OnItemTouchListener` pipeline for custom gestures.
- 🏷️ **Stable ID support** – look up items by stable ID even after recycling.
- 💓 **Persistent pulse** – continuously breathe an item (e.g. currently playing song) without breaking when it scrolls off‑screen.

### Helpers (included, fully customisable)
- ↕️ **Drag‑and‑drop reorder** – long‑press to drag, live room‑making animation, auto‑scroll near edges.
- 👆 **Swipe actions** – swipe left/right to reveal configurable actions (delete, favourite, play, etc.), with optional confirmation dialogs.

---

## 📦 Installation

### Option 1 – JitPack (recommended)

Add the JitPack repository to your root `build.gradle` (or `settings.gradle`):

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Then add the dependency:

```gradle
dependencies {
    implementation 'com.github.dav24wils:RecyclingView:Tag'
}
```

Replace Tag with the latest release tag (e.g., v1.0.0).

Option 2 – Copy the source files

The entire library is contained in a few Java files. Copy them directly into your project – no additional configuration required.

---

🚀 Quick Start

1. Add RecyclingView to your layout

```xml
<com.ikechi.studio.recyclingview.view.RecyclingView
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/recycler"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:rv_orientation="vertical"
    app:rv_reverseLayout="false"
    app:rv_snapToPosition="false"
    app:rv_scrollBarEnabled="true"
    app:rv_scrollBarColor="#FF0000"
    app:rv_itemSpacing="8dp" />
```

2. Create an adapter

Extend RecyclingAdapter (included in the library):

```java
public class MyAdapter extends RecyclingAdapter {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView tv = holder.itemView.findViewById(R.id.tv_text);
        tv.setText("Item " + position);
    }

    @Override
    public int getItemCount() {
        return 50;
    }
}
```

3. Set the adapter

```java
RecyclingView recycler = findViewById(R.id.recycler);
recycler.setAdapter(new MyAdapter());
```

---

⚙️ XML Attributes

Attribute Type Default Description
rv_orientation enum vertical vertical (0) – items go top‑to‑bottom; horizontal (1) – items go left‑to‑right.
rv_reverseLayout boolean false If true, adapter position 0 is placed at the trailing edge (bottom in vertical, right in horizontal).
rv_snapToPosition boolean false If true, the list snaps to the nearest item after a fling or finger release.
rv_scrollBarEnabled boolean true Show or hide the thin scroll‑position indicator bar.
rv_scrollBarColor color #99000000 Colour of the scroll indicator bar (accepts any colour format).
rv_itemSpacing dimension 0dp Uniform spacing inserted between items (not before the first or after the last).

---

🛠️ Programmatic API

```java
// Orientation
recycler.setOrientation(RecyclingView.VERTICAL);       // or HORIZONTAL

// Reverse layout
recycler.setReverseLayout(true);

// Snap behaviour
recycler.setSnapToPosition(true);

// Scrollbar
recycler.setScrollBarEnabled(false);
recycler.setScrollBarColor(0xFFFF0000);

// Item spacing (pixels)
recycler.setItemSpacing(dpToPx(8));

// Scroll to position
recycler.scrollToPosition(10);
recycler.smoothScrollToPosition(20);

// Look up items
RecyclingAdapter.ViewHolder holder = recycler.findViewHolderForAdapterPosition(5);
RecyclingAdapter.ViewHolder byId = recycler.findViewHolderForItemId(itemId);

// ID-safe persistent pulse
recycler.pulseItemId(trackId);          // start breathing
recycler.stopPulseItemId(trackId);      // stop one
recycler.stopAllPulses();               // stop all

// Scroll state
recycler.addOnScrollListener(new RecyclingView.OnScrollListener() {
    @Override public void onScrollStateChanged(RecyclingView view, int newState) { }
    @Override public void onScrolled(RecyclingView view, int dOffset) { }
});

// Item decorations
recycler.addItemDecoration(new SpacingDecoration(8, true, 0xFFE0E0E0));

// Item animator
recycler.setItemAnimator(new DefaultItemAnimator());
```

---

🧱 Architecture & Extensibility

RecyclingView was designed to be fully extensible. The core widget handles scrolling, recycling, and layout. Everything else is pluggable:

Component Base Class What it does
Adapter RecyclingAdapter Provides views and data. Extend it to create your own adapters.
ViewHolder RecyclingAdapter.ViewHolder Holds item views. Create subclasses for your layouts.
Item Animator ItemAnimator Controls add/remove/move/change/pulse animations. DefaultItemAnimator included.
Item Decoration ItemDecoration Draws behind/on top of items, adds offsets. SpacingDecoration included.
Touch Listener OnItemTouchListener Intercepts touch events before RecyclingView. Used by helpers.

🔧 Customisation examples from real projects

The library ships with concrete adapters and helpers that demonstrate how to build on top of the framework:

· SectionedItemAdapter – supports headers and data items in one flat list.
· AudioSectionedAdapter – powers a music track list with favourites, multi‑select, and contextual menus.
· MediaListAdapter – unified adapter for video and audio libraries.
· DragDropHelper – long‑press to drag items, with live room‑making animations and auto‑scroll.
· SwipeActionHelper – swipe left/right to reveal delete/favourite actions.
· MediaSwipeActionHelper – configurable swipe actions with confirmation dialogs and orientation‑aware painting.

All of these are standalone examples – you can copy, modify, or replace them entirely. The core library imposes no restrictions on your adapter, ViewHolder, or decoration design.

---

🎨 Included Animations (DefaultItemAnimator)

Animation Duration Description
ADD 380 ms Slide + fade‑in + scale, overshoot settle.
REMOVE 280 ms Slide + fade‑out, accelerate.
MOVE 260 ms Translate along primary axis.
CHANGE 220 ms Scale pulse + alpha flash (ID‑safe – survives recycling).
PULSE 1100 ms/cycle Continuous breathing loop, ID‑safe, indefinite.

---

📄 License

```
MIT License

Copyright (c) 2025 David Wilson Okere

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

👤 Author

David Wilson Okere

· GitHub: dav24wils
· Repository: RecyclingView

---

⭐ Show your support

If this project helped you, please consider giving it a ⭐ on GitHub – it really helps!

# Note: This project is currently not available on Maven or jit.io, so just copy the files from here or download them as a zip file, then add them to your project, but don't forget to give me some credit. Happy coding.
