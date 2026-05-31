# RecyclingView

**A high‑performance, fully custom scrolling container built entirely from scratch on the pure Android SDK.**

No `RecyclerView`. No Jetpack. No external libraries.  
Just clean, efficient Java that gives you a powerful, flexible list with a tiny footprint.

---

## ✨ Features

### Core scrolling engine
- 🧩 **100 % standalone** – extends `ViewGroup` directly; zero dependencies.
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

### Option 1 – JitPack (recommended)

Add the JitPack repository to your root `build.gradle` (or `settings.gradle`):

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

# Note: This project is currently not available on Maven or jit.io, so just copy the files from here or download them as a zip file, then add them to your project, but don't forget to give me some credit. Happy coding.
