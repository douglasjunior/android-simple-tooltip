/*
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2016 Douglas Nassif Roma Junior
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.douglasjunior.androidSimpleTooltip;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;


/**
 * <p class='pt'>Um tooltip que pode ser utilizado para exibição de dicas.</p>
 * <p class='en'>A tooltip that can be used to display tips on the screen.</p>
 *
 * @author Created by douglas on 05/05/16.
 * @see android.widget.PopupWindow
 */
@SuppressWarnings("SameParameterValue")
public class SimpleTooltip implements PopupWindow.OnDismissListener {

    private static final String TAG = SimpleTooltip.class.getSimpleName();

    // Default Resources
    private static final int mDefaultPopupWindowStyleRes = android.R.attr.popupWindowStyle;
    private static final int mDefaultTextAppearanceRes = R.style.simpletooltip_default;
    private static final int mDefaultBackgroundColorRes = R.color.simpletooltip_background;
    private static final int mDefaultTextColorRes = R.color.simpletooltip_text;
    private static final int mDefaultArrowColorRes = R.color.simpletooltip_arrow;
    private static final int mDefaultMarginRes = R.dimen.simpletooltip_margin;
    private static final int mDefaultPaddingRes = R.dimen.simpletooltip_padding;
    private static final int mDefaultAnimationPaddingRes = R.dimen.simpletooltip_animation_padding;
    private static final int mDefaultAnimationDurationRes = R.integer.simpletooltip_animation_duration;
    private static final int mDefaultArrowWidthRes = R.dimen.simpletooltip_arrow_width;
    private static final int mDefaultArrowHeightRes = R.dimen.simpletooltip_arrow_height;
    private static final int mDefaultOverlayOffsetRes = R.dimen.simpletooltip_overlay_offset;

    private final Context mContext;
    private OnDismissListener mOnDismissListener;
    private OnShowListener mOnShowListener;
    private PopupWindow mPopupWindow;
    private final int mGravity;
    private final int mArrowDirection;
    private final boolean mDismissOnInsideTouch;
    private final boolean mDismissOnOutsideTouch;
    private final boolean mModal;
    private final View mContentView;
    private View mContentLayout;
    @IdRes
    private final int mTextViewId;
    private final int mOverlayWindowBackgroundColor;
    private final CharSequence mText;
    private final View mAnchorView;
    private final boolean mTransparentOverlay;
    private final float mOverlayOffset;
    private final boolean mOverlayMatchParent;
    private final float mMaxWidth;
    private View mOverlay;
    private ViewGroup mRootView;
    private final boolean mShowArrow;
    private ImageView mArrowView;
    private final Drawable mArrowDrawable;
    private final boolean mAnimated;
    private AnimatorSet mAnimator;
    private final float mMargin;
    private final float mPadding;
    private final float mAnimationPadding;
    private final long mAnimationDuration;
    private final float mArrowWidth;
    private final float mArrowHeight;
    private final boolean mFocusable;
    private boolean dismissed = false;
    private int mHighlightShape;
    private int width;
    private int height;
    private boolean mIgnoreOverlay;
    private float cornerRadius;


    private SimpleTooltip(Builder builder) {
        mContext = builder.context;
        mGravity = builder.gravity;
        mOverlayWindowBackgroundColor=builder.overlayWindowBackgroundColor;
        mArrowDirection = builder.arrowDirection;
        mDismissOnInsideTouch = builder.dismissOnInsideTouch;
        mDismissOnOutsideTouch = builder.dismissOnOutsideTouch;
        mModal = builder.modal;
        mContentView = builder.contentView;
        mTextViewId = builder.textViewId;
        mText = builder.text;
        mAnchorView = builder.anchorView;
        mTransparentOverlay = builder.transparentOverlay;
        mOverlayOffset = builder.overlayOffset;
        mOverlayMatchParent = builder.overlayMatchParent;
        mMaxWidth = builder.maxWidth;
        mShowArrow = builder.showArrow;
        mArrowWidth = builder.arrowWidth;
        mArrowHeight = builder.arrowHeight;
        mArrowDrawable = builder.arrowDrawable;
        mAnimated = builder.animated;
        mMargin = builder.margin;
        mPadding = builder.padding;
        mAnimationPadding = builder.animationPadding;
        mAnimationDuration = builder.animationDuration;
        mOnDismissListener = builder.onDismissListener;
        mOnShowListener = builder.onShowListener;
        mFocusable = builder.focusable;
        mRootView = SimpleTooltipUtils.findFrameLayout(mAnchorView);
        mHighlightShape = builder.highlightShape;
        mIgnoreOverlay = builder.ignoreOverlay;
        this.width = builder.width;
        this.height = builder.height;
        this.cornerRadius = builder.cornerRadius;
        init();
    }

    private void init() {
        configPopupWindow();
        configContentView();
    }

    private void configPopupWindow() {
        mPopupWindow = new PopupWindow(mContext, null, mDefaultPopupWindowStyleRes);
        mPopupWindow.setOnDismissListener(this);
        mPopupWindow.setWidth(width);
        mPopupWindow.setHeight(height);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int x = (int) event.getX();
                final int y = (int) event.getY();

                if (!mDismissOnOutsideTouch && (event.getAction() == MotionEvent.ACTION_DOWN)
                        && ((x < 0) || (x >= mContentLayout.getMeasuredWidth()) || (y < 0) || (y >= mContentLayout.getMeasuredHeight()))) {
                    return true;
                } else if (!mDismissOnOutsideTouch && event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    return true;
                } else if ((event.getAction() == MotionEvent.ACTION_DOWN) && mDismissOnInsideTouch) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        mPopupWindow.setClippingEnabled(false);
        mPopupWindow.setFocusable(mFocusable);
    }


    public void show() {
        verifyDismissed();

        mContentLayout.getViewTreeObserver().addOnGlobalLayoutListener(mLocationLayoutListener);
        mContentLayout.getViewTreeObserver().addOnGlobalLayoutListener(mAutoDismissLayoutListener);

        mRootView.post(new Runnable() {
            @Override
            public void run() {
                if (mRootView.isShown()) {
                    mPopupWindow.showAtLocation(mRootView, Gravity.NO_GRAVITY, mRootView.getWidth(), mRootView.getHeight());
                    if (mFocusable)
                        mContentLayout.requestFocus();
                }
                else {
                    Log.e(TAG, "Tooltip cannot be shown, root view is invalid or has been closed.");
                }
            }
        });
    }

    private void verifyDismissed() {
        if (dismissed) {
            throw new IllegalArgumentException("Tooltip has been dismissed.");
        }
    }

    private void createOverlay() {
        if (mIgnoreOverlay) {
            return;
        }
        mOverlay = mTransparentOverlay ? new View(mContext) : new OverlayView(mContext, mAnchorView, mHighlightShape, mOverlayOffset,mOverlayWindowBackgroundColor, cornerRadius);
        if (mOverlayMatchParent)
            mOverlay.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        else
            mOverlay.setLayoutParams(new ViewGroup.LayoutParams(mRootView.getWidth(), mRootView.getHeight()));
        mOverlay.setOnTouchListener(mOverlayTouchListener);
        mRootView.addView(mOverlay);
    }

    private PointF calculePopupLocation() {
        PointF location = new PointF();

        final RectF anchorRect = SimpleTooltipUtils.calculeRectInWindow(mAnchorView);
        final PointF anchorCenter = new PointF(anchorRect.centerX(), anchorRect.centerY());

        switch (mGravity) {
            case Gravity.START:
                location.x = anchorRect.left - mPopupWindow.getContentView().getWidth() - mMargin;
                location.y = anchorCenter.y - mPopupWindow.getContentView().getHeight() / 2f;
                break;
            case Gravity.END:
                location.x = anchorRect.right + mMargin;
                location.y = anchorCenter.y - mPopupWindow.getContentView().getHeight() / 2f;
                break;
            case Gravity.TOP:
                location.x = anchorCenter.x - mPopupWindow.getContentView().getWidth() / 2f;
                location.y = anchorRect.top - mPopupWindow.getContentView().getHeight() - mMargin;
                break;
            case Gravity.BOTTOM:
                location.x = anchorCenter.x - mPopupWindow.getContentView().getWidth() / 2f;
                location.y = anchorRect.bottom + mMargin;
                break;
            case Gravity.CENTER:
                location.x = anchorCenter.x - mPopupWindow.getContentView().getWidth() / 2f;
                location.y = anchorCenter.y - mPopupWindow.getContentView().getHeight() / 2f;
                break;
            default:
                throw new IllegalArgumentException("Gravity must have be CENTER, START, END, TOP or BOTTOM.");
        }

        return location;
    }

    private void configContentView() {
        if (mContentView instanceof TextView) {
            TextView tv = (TextView) mContentView;
            tv.setText(mText);
        } else {
            TextView tv = (TextView) mContentView.findViewById(mTextViewId);
            if (tv != null)
                tv.setText(mText);
        }

        mContentView.setPadding((int) mPadding, (int) mPadding, (int) mPadding, (int) mPadding);

        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(mArrowDirection == ArrowDrawable.LEFT || mArrowDirection == ArrowDrawable.RIGHT ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);
        int layoutPadding = (int) (mAnimated ? mAnimationPadding : 0);
        linearLayout.setPadding(layoutPadding, layoutPadding, layoutPadding, layoutPadding);

        if (mShowArrow) {
            mArrowView = new ImageView(mContext);
            mArrowView.setImageDrawable(mArrowDrawable);
            LinearLayout.LayoutParams arrowLayoutParams;

            if (mArrowDirection == ArrowDrawable.TOP || mArrowDirection == ArrowDrawable.BOTTOM) {
                arrowLayoutParams = new LinearLayout.LayoutParams((int) mArrowWidth, (int) mArrowHeight, 0);
            } else {
                arrowLayoutParams = new LinearLayout.LayoutParams((int) mArrowHeight, (int) mArrowWidth, 0);
            }

            arrowLayoutParams.gravity = Gravity.CENTER;
            mArrowView.setLayoutParams(arrowLayoutParams);

            if (mArrowDirection == ArrowDrawable.BOTTOM || mArrowDirection == ArrowDrawable.RIGHT) {
                linearLayout.addView(mContentView);
                linearLayout.addView(mArrowView);
            } else {
                linearLayout.addView(mArrowView);
                linearLayout.addView(mContentView);
            }
        } else {
            linearLayout.addView(mContentView);
        }

        LinearLayout.LayoutParams contentViewParams = new LinearLayout.LayoutParams(width, height, 0);
        contentViewParams.gravity = Gravity.CENTER;
        mContentView.setLayoutParams(contentViewParams);

        mContentLayout = linearLayout;
        mContentLayout.setVisibility(View.INVISIBLE);

        if (mFocusable)
        {
            mContentLayout.setFocusableInTouchMode(true);
            mContentLayout.setOnKeyListener(new View.OnKeyListener()
            {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event)
                {
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        switch (keyCode) {
                            // The following are taken from the KeyEvent.isConfirmKey() function
                            case KeyEvent.KEYCODE_DPAD_CENTER:
                            case KeyEvent.KEYCODE_ENTER:
                            case KeyEvent.KEYCODE_SPACE:
                            case KeyEvent.KEYCODE_NUMPAD_ENTER:
                                dismiss();
                                return true;
                        }
                    }
                    return false;
                }
            });
        }

        mPopupWindow.setContentView(mContentLayout);
    }

    public void dismiss() {
        if (dismissed)
            return;

        dismissed = true;
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * <div class="pt">Indica se o tooltip está sendo exibido na tela.</div>
     * <div class=en">Indicate whether this tooltip is showing on screen.</div>
     *
     * @return <div class="pt"><tt>true</tt> se o tooltip estiver sendo exibido, <tt>false</tt> caso contrário</div>
     * <div class="en"><tt>true</tt> if the popup is showing, <tt>false</tt> otherwise</div>
     */
    public boolean isShowing() {
        return mPopupWindow != null && mPopupWindow.isShowing();
    }

    public <T extends View> T findViewById(int id) {
        //noinspection unchecked
        return (T) mContentLayout.findViewById(id);
    }

    @Override
    public void onDismiss() {
        dismissed = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (mAnimator != null) {
                mAnimator.removeAllListeners();
                mAnimator.end();
                mAnimator.cancel();
                mAnimator = null;
            }
        }

        if (mRootView != null && mOverlay != null) {
            mRootView.removeView(mOverlay);
        }
        mRootView = null;
        mOverlay = null;

        if (mOnDismissListener != null)
            mOnDismissListener.onDismiss(this);
        mOnDismissListener = null;

        SimpleTooltipUtils.removeOnGlobalLayoutListener(mPopupWindow.getContentView(), mLocationLayoutListener);
        SimpleTooltipUtils.removeOnGlobalLayoutListener(mPopupWindow.getContentView(), mArrowLayoutListener);
        SimpleTooltipUtils.removeOnGlobalLayoutListener(mPopupWindow.getContentView(), mShowLayoutListener);
        SimpleTooltipUtils.removeOnGlobalLayoutListener(mPopupWindow.getContentView(), mAnimationLayoutListener);
        SimpleTooltipUtils.removeOnGlobalLayoutListener(mPopupWindow.getContentView(), mAutoDismissLayoutListener);

        mPopupWindow = null;
    }

    private final View.OnTouchListener mOverlayTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return mModal;
        }
    };

    private final ViewTreeObserver.OnGlobalLayoutListener mLocationLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            final PopupWindow popup = mPopupWindow;
            if (popup == null || dismissed) return;

            if (mMaxWidth > 0 && mContentView.getWidth() > mMaxWidth) {
                SimpleTooltipUtils.setWidth(mContentView, mMaxWidth);
                popup.update(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                return;
            }

            SimpleTooltipUtils.removeOnGlobalLayoutListener(popup.getContentView(), this);
            popup.getContentView().getViewTreeObserver().addOnGlobalLayoutListener(mArrowLayoutListener);
            PointF location = calculePopupLocation();
            popup.setClippingEnabled(true);
            popup.update((int) location.x, (int) location.y, popup.getWidth(), popup.getHeight());
            popup.getContentView().requestLayout();
            createOverlay();
        }
    };

    private final ViewTreeObserver.OnGlobalLayoutListener mArrowLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            final PopupWindow popup = mPopupWindow;
            if (popup == null || dismissed) return;

            SimpleTooltipUtils.removeOnGlobalLayoutListener(popup.getContentView(), this);

            popup.getContentView().getViewTreeObserver().addOnGlobalLayoutListener(mAnimationLayoutListener);
            popup.getContentView().getViewTreeObserver().addOnGlobalLayoutListener(mShowLayoutListener);
            if (mShowArrow) {
                RectF achorRect = SimpleTooltipUtils.calculeRectOnScreen(mAnchorView);
                RectF contentViewRect = SimpleTooltipUtils.calculeRectOnScreen(mContentLayout);
                float x, y;
                if (mArrowDirection == ArrowDrawable.TOP || mArrowDirection == ArrowDrawable.BOTTOM) {
                    x = mContentLayout.getPaddingLeft() + SimpleTooltipUtils.pxFromDp(2);
                    float centerX = (contentViewRect.width() / 2f) - (mArrowView.getWidth() / 2f);
                    float newX = centerX - (contentViewRect.centerX() - achorRect.centerX());
                    if (newX > x) {
                        if (newX + mArrowView.getWidth() + x > contentViewRect.width()) {
                            x = contentViewRect.width() - mArrowView.getWidth() - x;
                        } else {
                            x = newX;
                        }
                    }
                    y = mArrowView.getTop();
                    y = y + (mArrowDirection == ArrowDrawable.BOTTOM ? -1 : +1);
                } else {
                    y = mContentLayout.getPaddingTop() + SimpleTooltipUtils.pxFromDp(2);
                    float centerY = (contentViewRect.height() / 2f) - (mArrowView.getHeight() / 2f);
                    float newY = centerY - (contentViewRect.centerY() - achorRect.centerY());
                    if (newY > y) {
                        if (newY + mArrowView.getHeight() + y > contentViewRect.height()) {
                            y = contentViewRect.height() - mArrowView.getHeight() - y;
                        } else {
                            y = newY;
                        }
                    }
                    x = mArrowView.getLeft();
                    x = x + (mArrowDirection == ArrowDrawable.RIGHT ? -1 : +1);
                }
                SimpleTooltipUtils.setX(mArrowView, (int) x);
                SimpleTooltipUtils.setY(mArrowView, (int) y);
            }
            popup.getContentView().requestLayout();
        }
    };

    private final ViewTreeObserver.OnGlobalLayoutListener mShowLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            final PopupWindow popup = mPopupWindow;
            if (popup == null || dismissed) return;

            SimpleTooltipUtils.removeOnGlobalLayoutListener(popup.getContentView(), this);

            if (mOnShowListener != null)
                mOnShowListener.onShow(SimpleTooltip.this);
            mOnShowListener = null;

            mContentLayout.setVisibility(View.VISIBLE);
        }
    };

    private final ViewTreeObserver.OnGlobalLayoutListener mAnimationLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            final PopupWindow popup = mPopupWindow;
            if (popup == null || dismissed) return;

            SimpleTooltipUtils.removeOnGlobalLayoutListener(popup.getContentView(), this);

            if (mAnimated) startAnimation();

            popup.getContentView().requestLayout();
        }
    };

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void startAnimation() {
        final String property = mGravity == Gravity.TOP || mGravity == Gravity.BOTTOM ? "translationY" : "translationX";

        final ObjectAnimator anim1 = ObjectAnimator.ofFloat(mContentLayout, property, -mAnimationPadding, mAnimationPadding);
        anim1.setDuration(mAnimationDuration);
        anim1.setInterpolator(new AccelerateDecelerateInterpolator());

        final ObjectAnimator anim2 = ObjectAnimator.ofFloat(mContentLayout, property, mAnimationPadding, -mAnimationPadding);
        anim2.setDuration(mAnimationDuration);
        anim2.setInterpolator(new AccelerateDecelerateInterpolator());

        mAnimator = new AnimatorSet();
        mAnimator.playSequentially(anim1, anim2);
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!dismissed && isShowing()) {
                    animation.start();
                }
            }
        });
        mAnimator.start();
    }

    /**
     * <div class="pt">Listener utilizado para chamar o <tt>SimpleTooltip#dismiss()</tt> quando a <tt>View</tt> root é encerrada sem que a tooltip seja fechada.
     * Pode ocorrer quando a tooltip é utilizada dentro de Dialogs.</div>
     */
    private final ViewTreeObserver.OnGlobalLayoutListener mAutoDismissLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            final PopupWindow popup = mPopupWindow;
            if (popup == null || dismissed) return;

            if (!mRootView.isShown()) dismiss();
        }
    };

    public interface OnDismissListener {
        void onDismiss(SimpleTooltip tooltip);
    }

    public interface OnShowListener {
        void onShow(SimpleTooltip tooltip);
    }

    /**
     * <div class="pt">Classe responsável por facilitar a criação do objeto <tt>SimpleTooltip</tt>.</div>
     * <div class="en">Class responsible for making it easier to build the object <tt>SimpleTooltip</tt>.</div>
     *
     * @author Created by douglas on 05/05/16.
     */
    @SuppressWarnings({"SameParameterValue", "unused"})
    public static class Builder {

        private final Context context;
        private boolean dismissOnInsideTouch = true;
        private boolean dismissOnOutsideTouch = true;
        private boolean modal = false;
        private View contentView;
        @IdRes
        private int textViewId = android.R.id.text1;
        private CharSequence text = "";
        private View anchorView;
        private int arrowDirection = ArrowDrawable.AUTO;
        private int gravity = Gravity.BOTTOM;
        private boolean transparentOverlay = true;
        private float overlayOffset = -1;
        private boolean overlayMatchParent = true;
        private float maxWidth;
        private boolean showArrow = true;
        private Drawable arrowDrawable;
        private boolean animated = false;
        private float margin = -1;
        private float padding = -1;
        private float animationPadding = -1;
        private OnDismissListener onDismissListener;
        private OnShowListener onShowListener;
        private long animationDuration;
        private int backgroundColor;
        private int textColor;
        private int arrowColor;
        private float arrowHeight;
        private float arrowWidth;
        private boolean focusable;
        private float cornerRadius;
        private int highlightShape = OverlayView.HIGHLIGHT_SHAPE_OVAL;
        private int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        private int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        private boolean ignoreOverlay = false;
        private int overlayWindowBackgroundColor=0;

        public Builder(Context context) {
            this.context = context;
            this.focusable = !(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN));
        }

        public SimpleTooltip build() throws IllegalArgumentException {
            validateArguments();
            if (backgroundColor == 0) {
                backgroundColor = SimpleTooltipUtils.getColor(context, mDefaultBackgroundColorRes);
            }

            if (overlayWindowBackgroundColor == 0) {
                overlayWindowBackgroundColor = Color.BLACK;
            }

            if (textColor == 0) {
                textColor = SimpleTooltipUtils.getColor(context, mDefaultTextColorRes);
            }
            if (contentView == null) {
                TextView tv = new TextView(context);
                SimpleTooltipUtils.setTextAppearance(tv, mDefaultTextAppearanceRes);
                tv.setBackgroundColor(backgroundColor);
                tv.setTextColor(textColor);
                contentView = tv;
            }
            if (arrowColor == 0) {
                arrowColor = SimpleTooltipUtils.getColor(context, mDefaultArrowColorRes);
            }
            if (margin < 0) {
                margin = context.getResources().getDimension(mDefaultMarginRes);
            }
            if (padding < 0) {
                padding = context.getResources().getDimension(mDefaultPaddingRes);
            }
            if (animationPadding < 0) {
                animationPadding = context.getResources().getDimension(mDefaultAnimationPaddingRes);
            }
            if (animationDuration == 0) {
                animationDuration = context.getResources().getInteger(mDefaultAnimationDurationRes);
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                animated = false;
            }
            if (showArrow) {
                if (arrowDirection == ArrowDrawable.AUTO)
                    arrowDirection = SimpleTooltipUtils.tooltipGravityToArrowDirection(gravity);
                if (arrowDrawable == null)
                    arrowDrawable = new ArrowDrawable(arrowColor, arrowDirection);
                if (arrowWidth == 0)
                    arrowWidth = context.getResources().getDimension(mDefaultArrowWidthRes);
                if (arrowHeight == 0)
                    arrowHeight = context.getResources().getDimension(mDefaultArrowHeightRes);
            }
            if (highlightShape < 0 || highlightShape > OverlayView.HIGHLIGHT_SHAPE_RECTANGULAR_ROUNDED) {
                highlightShape = OverlayView.HIGHLIGHT_SHAPE_OVAL;
            }
            if (overlayOffset < 0) {
                overlayOffset = context.getResources().getDimension(mDefaultOverlayOffsetRes);
            }
            return new SimpleTooltip(this);
        }

        private void validateArguments() throws IllegalArgumentException {
            if (context == null) {
                throw new IllegalArgumentException("Context not specified.");
            }
            if (anchorView == null) {
                throw new IllegalArgumentException("Anchor view not specified.");
            }
        }

        public Builder setWidth(int width){
            this.width = width;
            return this;
        }

        public Builder setHeight(int height){
            this.height = height;
            return this;
        }

        /**
         * <div class="pt">Define um novo conteúdo customizado para o tooltip.</div>
         *
         * @param textView <div class="pt">novo conteúdo para o tooltip.</div>
         * @return this
         * @see Builder#contentView(int, int)
         * @see Builder#contentView(View, int)
         * @see Builder#contentView(int)
         */
        public Builder contentView(TextView textView) {
            this.contentView = textView;
            this.textViewId = 0;
            return this;
        }

        /**
         * <div class="pt">Define um novo conteúdo customizado para o tooltip.</div>
         *
         * @param contentView <div class="pt">novo conteúdo para o tooltip, pode ser um <tt>{@link ViewGroup}</tt> ou qualquer componente customizado.</div>
         * @param textViewId  <div class="pt">resId para o <tt>{@link TextView}</tt> existente dentro do <tt>{@link Builder#contentView}</tt>. Padrão é <tt>android.R.id.text1</tt>.</div>
         * @return this
         * @see Builder#contentView(int, int)
         * @see Builder#contentView(TextView)
         * @see Builder#contentView(int)
         */
        public Builder contentView(View contentView, @IdRes int textViewId) {
            this.contentView = contentView;
            this.textViewId = textViewId;
            return this;
        }

        /**
         * <div class="pt">Define um novo conteúdo customizado para o tooltip.</div>
         *
         * @param contentViewId <div class="pt">layoutId que será inflado como o novo conteúdo para o tooltip.</div>
         * @param textViewId    <div class="pt">resId para o <tt>{@link TextView}</tt> existente dentro do <tt>{@link Builder#contentView}</tt>. Padrão é <tt>android.R.id.text1</tt>.</div>
         * @return this
         * @see Builder#contentView(View, int)
         * @see Builder#contentView(TextView)
         * @see Builder#contentView(int)
         */
        public Builder contentView(@LayoutRes int contentViewId, @IdRes int textViewId) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.contentView = inflater.inflate(contentViewId, null, false);
            this.textViewId = textViewId;
            return this;
        }

        /**
         * <div class="pt">Define um novo conteúdo customizado para o tooltip.</div>
         *
         * @param contentViewId <div class="pt">layoutId que será inflado como o novo conteúdo para o tooltip.</div>
         * @return this
         * @see Builder#contentView(View, int)
         * @see Builder#contentView(TextView)
         * @see Builder#contentView(int, int)
         */
        public Builder contentView(@LayoutRes int contentViewId) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.contentView = inflater.inflate(contentViewId, null, false);
            this.textViewId = 0;
            return this;
        }

        /**
         * <div class="pt">Define se o tooltip será fechado quando receber um clique dentro de sua área. Padrão é <tt>true</tt>.</div>
         *
         * @param dismissOnInsideTouch <div class="pt"><tt>true</tt> para fechar quando receber o click dentro, <tt>false</tt> caso contrário.</div>
         * @return this
         * @see Builder#dismissOnOutsideTouch(boolean)
         */
        public Builder dismissOnInsideTouch(boolean dismissOnInsideTouch) {
            this.dismissOnInsideTouch = dismissOnInsideTouch;
            return this;
        }

        /**
         * <div class="pt">Define se o tooltip será fechado quando receber um clique fora de sua área. Padrão é <tt>true</tt>.</div>
         *
         * @param dismissOnOutsideTouch <div class="pt"><tt>true</tt> para fechar quando receber o click fora, <tt>false</tt> caso contrário.</div>
         * @return this
         * @see Builder#dismissOnInsideTouch(boolean)
         */
        public Builder dismissOnOutsideTouch(boolean dismissOnOutsideTouch) {
            this.dismissOnOutsideTouch = dismissOnOutsideTouch;
            return this;
        }

        /**
         * <div class="pt">Define se a tela fiacrá bloqueada enquanto o tooltip estiver aberto.
         * Esse parâmetro deve ser combinado com <tt>{@link Builder#dismissOnInsideTouch(boolean)}</tt> e <tt>{@link Builder#dismissOnOutsideTouch(boolean)}</tt>.
         * Padrão é <tt>false</tt>.</div>
         *
         * @param modal <div class="pt"><tt>true</tt> para bloquear a tela, <tt>false</tt> caso contrário.</div>
         * @return this
         * @see Builder#dismissOnInsideTouch(boolean)
         * @see Builder#dismissOnOutsideTouch(boolean)
         */
        public Builder modal(boolean modal) {
            this.modal = modal;
            return this;
        }

        /**
         * <div class="pt">Define o texto que sera exibido no <tt>{@link TextView}</tt> dentro do tooltip.</div>
         *
         * @param text <div class="pt">texto que sera exibido.</div>
         * @return this
         */
        public Builder text(CharSequence text) {
            this.text = text;
            return this;
        }

        /**
         * <div class="pt">Define o texto que sera exibido no <tt>{@link TextView}</tt> dentro do tooltip.</div>
         *
         * @param textRes <div class="pt">id do resource da String.</div>
         * @return this
         */
        public Builder text(@StringRes int textRes) {
            this.text = context.getString(textRes);
            return this;
        }

        /**
         * <div class="pt">Define para qual <tt>{@link View}</tt> o tooltip deve apontar. Importante ter certeza que esta <tt>{@link View}</tt> já esteja pronta e exibida na tela.</div>
         * <div class="en">Set the target <tt>{@link View}</tt> that the tooltip will point. Make sure that the anchor <tt>{@link View}</tt> shold be showing in the screen.</div>
         *
         * @param anchorView <div class="pt"><tt>View</tt> para qual o tooltip deve apontar</div>
         *                   <div class="en"><tt>View</tt> that the tooltip will point</div>
         * @return this
         */
        public Builder anchorView(View anchorView) {
            this.anchorView = anchorView;
            return this;
        }

        /**
         * <div class="pt">Define a para qual lado o tooltip será posicionado em relação ao <tt>anchorView</tt>.
         * As opções existentes são <tt>{@link Gravity#START}</tt>, <tt>{@link Gravity#END}</tt>, <tt>{@link Gravity#TOP}</tt> e <tt>{@link Gravity#BOTTOM}</tt>.
         * O padrão é <tt>{@link Gravity#BOTTOM}</tt>.</div>
         *
         * @param gravity <div class="pt">lado para qual o tooltip será posicionado.</div>
         * @return this
         */
        public Builder gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        /**
         * <div class="pt">Define a direção em que a seta será criada.
         * As opções existentes são <tt>{@link ArrowDrawable#LEFT}</tt>, <tt>{@link ArrowDrawable#TOP}</tt>, <tt>{@link ArrowDrawable#RIGHT}</tt>,
         * <tt>{@link ArrowDrawable#BOTTOM}</tt> e <tt>{@link ArrowDrawable#AUTO}</tt>.
         * O padrão é <tt>{@link ArrowDrawable#AUTO}</tt>. <br>
         * Esta opção deve ser utilizada em conjunto com  <tt>Builder#showArrow(true)</tt>.</div>
         *
         * @param arrowDirection <div class="pt">direção em que a seta será criada.</div>
         * @return this
         */
        public Builder arrowDirection(int arrowDirection) {
            this.arrowDirection = arrowDirection;
            return this;
        }

        /**
         * <div class="pt">Define se o fundo da tela será escurecido ou transparente enquanto o tooltip estiver aberto. Padrão é <tt>true</tt>.</div>
         *
         * @param transparentOverlay <div class="pt"><tt>true</tt> para o fundo transparente, <tt>false</tt> para escurecido.</div>
         * @return this
         */
        public Builder transparentOverlay(boolean transparentOverlay) {
            this.transparentOverlay = transparentOverlay;
            return this;
        }

        /**
         * <div class="pt">Define a largura máxima do Tooltip.</div>
         *
         * @param maxWidthRes <div class="pt">resId da largura máxima.</div>
         * @return <tt>this</tt>
         * @see Builder#maxWidth(float)
         */
        public Builder maxWidth(@DimenRes int maxWidthRes) {
            this.maxWidth = context.getResources().getDimension(maxWidthRes);
            return this;
        }

        /**
         * <div class="pt">Define a largura máxima do Tooltip. Padrão é <tt>0</tt> (sem limite).</div>
         *
         * @param maxWidth <div class="pt">largura máxima em pixels.</div>
         * @return <tt>this</tt>
         * @see Builder#maxWidth(int)
         */
        public Builder maxWidth(float maxWidth) {
            this.maxWidth = maxWidth;
            return this;
        }

        /**
         * <div class="pt">Define se o tooltip será animado enquanto estiver aberto. Disponível a partir do Android API 11. Padrão é <tt>false</tt>.</div>
         *
         * @param animated <div class="pt"><tt>true</tt> para tooltip animado, <tt>false</tt> caso contrário.</div>
         * @return this
         */
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public Builder animated(boolean animated) {
            this.animated = animated;
            return this;
        }

        /**
         * <div class="pt">Define o tamanho do deslocamento durante a animação. Padrão é o valor de <tt>{@link R.dimen.simpletooltip_animation_padding}</tt>.</div>
         *
         * @param animationPadding <div class="pt">tamanho do deslocamento em pixels.</div>
         * @return <tt>this</tt>
         * @see Builder#animationPadding(int)
         */
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public Builder animationPadding(float animationPadding) {
            this.animationPadding = animationPadding;
            return this;
        }

        /**
         * <div class="pt">Define o tamanho do deslocamento durante a animação. Padrão é <tt>{@link R.dimen.simpletooltip_animation_padding}</tt>.</div>
         *
         * @param animationPaddingRes <div class="pt">resId do tamanho do deslocamento.</div>
         * @return <tt>this</tt>
         * @see Builder#animationPadding(float)
         */
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public Builder animationPadding(@DimenRes int animationPaddingRes) {
            this.animationPadding = context.getResources().getDimension(animationPaddingRes);
            return this;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public Builder animationDuration(long animationDuration) {
            this.animationDuration = animationDuration;
            return this;
        }

        /**
         * <div class="pt">Define o padding entre a borda do Tooltip e seu conteúdo. Padrão é o valor <tt>{@link R.dimen.simpletooltip_padding}</tt>.</div>
         *
         * @param padding <div class="pt">tamanho do padding em pixels.</div>
         * @return <tt>this</tt>
         * @see Builder#padding(int)
         */
        public Builder padding(float padding) {
            this.padding = padding;
            return this;
        }

        /**
         * <div class="pt">Define o padding entre a borda do Tooltip e seu conteúdo. Padrão é <tt>{@link R.dimen.simpletooltip_padding}</tt>.</div>
         *
         * @param paddingRes <div class="pt">resId do tamanho do padding.</div>
         * @return <tt>this</tt>
         * @see Builder#padding(float)
         */
        public Builder padding(@DimenRes int paddingRes) {
            this.padding = context.getResources().getDimension(paddingRes);
            return this;
        }


        /**
         * <div class="pt">Define a margem entre o Tooltip e o <tt>anchorView</tt>. Padrão é o valor de <tt>{@link R.dimen.simpletooltip_margin}</tt>.</div>
         *
         * @param margin <div class="pt">tamanho da margem em pixels.</div>
         * @return <tt>this</tt>
         * @see Builder#margin(int)
         */
        public Builder margin(float margin) {
            this.margin = margin;
            return this;
        }

        /**
         * <div class="pt">Define a margem entre o Tooltip e o <tt>anchorView</tt>. Padrão é <tt>{@link R.dimen.simpletooltip_margin}</tt>.</div>
         *
         * @param marginRes <div class="pt">resId do tamanho da margem.</div>
         * @return <tt>this</tt>
         * @see Builder#margin(float)
         */
        public Builder margin(@DimenRes int marginRes) {
            this.margin = context.getResources().getDimension(marginRes);
            return this;
        }

        public Builder textColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder backgroundColor(@ColorInt int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder overlayWindowBackgroundColor(@ColorInt int overlayWindowBackgroundColor) {
            this.overlayWindowBackgroundColor = overlayWindowBackgroundColor;
            return this;
        }

        /**
         * <div class="pt">Indica se deve ser gerada a seta indicativa. Padrão é <tt>true</tt>.</div>
         * <div class="en">Indicates whether to be generated indicative arrow. Default is <tt>true</tt>.</div>
         *
         * @param showArrow <div class="pt"><tt>true</tt> para exibir a seta, <tt>false</tt> caso contrário.</div>
         *                  <div class="en"><tt>true</tt> to show arrow, <tt>false</tt> otherwise.</div>
         * @return this
         */
        public Builder showArrow(boolean showArrow) {
            this.showArrow = showArrow;
            return this;
        }

        public Builder arrowDrawable(Drawable arrowDrawable) {
            this.arrowDrawable = arrowDrawable;
            return this;
        }

        public Builder arrowDrawable(@DrawableRes int drawableRes) {
            this.arrowDrawable = SimpleTooltipUtils.getDrawable(context, drawableRes);
            return this;
        }

        public Builder arrowColor(@ColorInt int arrowColor) {
            this.arrowColor = arrowColor;
            return this;
        }

        /**
         * <div class="pt">Altura da seta indicativa. Esse valor é automaticamente definido em Largura ou Altura conforme a <tt>{@link android.view.Gravity}</tt> configurada.
         * Este valor sobrescreve <tt>{@link R.dimen.simpletooltip_arrow_height}</tt></div>
         * <div class="en">Height of the arrow. This value is automatically set in the Width or Height as the <tt>{@link android.view.Gravity}</tt>.</div>
         *
         * @param arrowHeight <div class="pt">Altura em pixels.</div>
         *                    <div class="en">Height in pixels.</div>
         * @return this
         * @see Builder#arrowWidth(float)
         */
        public Builder arrowHeight(float arrowHeight) {
            this.arrowHeight = arrowHeight;
            return this;
        }

        /**
         * <div class="pt">Largura da seta indicativa. Esse valor é automaticamente definido em Largura ou Altura conforme a <tt>{@link android.view.Gravity}</tt> configurada.
         * Este valor sobrescreve <tt>{@link R.dimen.simpletooltip_arrow_width}</tt></div>
         * <div class="en">Width of the arrow. This value is automatically set in the Width or Height as the <tt>{@link android.view.Gravity}</tt>.</div>
         *
         * @param arrowWidth <div class="pt">Largura em pixels.</div>
         *                   <div class="en">Width in pixels.</div>
         * @return this
         */
        public Builder arrowWidth(float arrowWidth) {
            this.arrowWidth = arrowWidth;
            return this;
        }

        public Builder onDismissListener(OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        public Builder onShowListener(OnShowListener onShowListener) {
            this.onShowListener = onShowListener;
            return this;
        }

        /**
         * <div class="pt">Habilita o foco no conteúdo da tooltip. Padrão é <tt>false</tt> em dispositivos sensíveis ao toque e <tt>true</tt> em dispositivos não sensíveis ao toque.</div>
         * <div class="en">Enables focus in the tooltip content. Default is <tt>false</tt> on touch devices, and <tt>true</tt> on non-touch devices.</div>
         *
         * @param focusable <div class="pt">Pode receber o foco.</div>
         *                  <div class="en">Can receive focus.</div>
         * @return this
         */
        public Builder focusable(boolean focusable) {
            this.focusable = focusable;
            return this;
        }

        /**
         * <div class="pt">Configura o formato do Shape em destaque. <br/>
         * <tt>{@link OverlayView#HIGHLIGHT_SHAPE_OVAL}</tt> - Destaque oval (padrão). <br/>
         * <tt>{@link OverlayView#HIGHLIGHT_SHAPE_RECTANGULAR}</tt> - Destaque retangular. <br/>
         * </div>
         * <p>
         * <div class="en">Configure the the Shape type. <br/>
         * <tt>{@link OverlayView#HIGHLIGHT_SHAPE_OVAL}</tt> - Shape oval (default). <br/>
         * <tt>{@link OverlayView#HIGHLIGHT_SHAPE_RECTANGULAR}</tt> - Shape rectangular. <br/>
         * </div>
         *
         * @param highlightShape <div class="pt">Formato do Shape.</div>
         *                       <div class="en">Shape type.</div>
         * @return this
         * @see OverlayView#HIGHLIGHT_SHAPE_OVAL
         * @see OverlayView#HIGHLIGHT_SHAPE_RECTANGULAR
         * @see Builder#transparentOverlay(boolean)
         */
        public Builder highlightShape(int highlightShape) {
            this.highlightShape = highlightShape;
            return this;
        }

        public Builder cornerRadius(float radius) {
            this.cornerRadius = radius;
            return this;
        }

        /**
         * <div class="pt">Tamanho da margem entre {@link Builder#anchorView(View)} e a borda do Shape de destaque.
         * Este valor sobrescreve <tt>{@link R.dimen.simpletooltip_overlay_offset}</tt></div>
         * <div class="en">Margin between {@link Builder#anchorView(View)} and highlight Shape border.
         * This value override <tt>{@link R.dimen.simpletooltip_overlay_offset}</tt></div>
         *
         * @param overlayOffset <div class="pt">Tamanho em pixels.</div>
         *                      <div class="en">Size in pixels.</div>
         * @return this
         * @see Builder#anchorView(View)
         * @see Builder#transparentOverlay(boolean)
         */
        public Builder overlayOffset(@Dimension float overlayOffset) {
            this.overlayOffset = overlayOffset;
            return this;
        }

        /**
         * <div class="pt">Define o comportamento do overlay. Utilizado para casos onde a view de Overlay não pode ser MATCH_PARENT.
         * Como em uma Dialog ou DialogFragment.</div>
         * <div class="en">Sets the behavior of the overlay view. Used for cases where the Overlay view can not be MATCH_PARENT.
         * Like in a Dialog or DialogFragment.</div>
         *
         * @param overlayMatchParent <div class="pt">True se o overlay deve ser MATCH_PARENT. False se ele deve obter o mesmo tamanho do pai.</div>
         *                           <div class="en">True if the overlay should be MATCH_PARENT. False if it should get the same size as the parent.</div>
         * @return this
         */
        public Builder overlayMatchParent(boolean overlayMatchParent) {
            this.overlayMatchParent = overlayMatchParent;
            return this;
        }

        /**
         * As some dialogs have a problem when displaying tooltip (like expand/subtract) container, this ignores overlay adding altogether.
         * @param ignoreOverlay flag to ignore overlay adding
         * @return this
         */
        public Builder ignoreOverlay(boolean ignoreOverlay) {
            this.ignoreOverlay = ignoreOverlay;
            return this;
        }
    }
}
