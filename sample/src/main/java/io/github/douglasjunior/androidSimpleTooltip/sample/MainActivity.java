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

package io.github.douglasjunior.androidSimpleTooltip.sample;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltipUtils;

/**
 * MainActivity
 * Created by douglas on 09/05/16.
 */
@SuppressWarnings("ConstantConditions")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        findViewById(R.id.btn_simple).setOnClickListener(this);
        findViewById(R.id.btn_animated).setOnClickListener(this);
        findViewById(R.id.btn_overlay).setOnClickListener(this);
        findViewById(R.id.btn_maxwidth).setOnClickListener(this);
        findViewById(R.id.btn_outside).setOnClickListener(this);
        findViewById(R.id.btn_inside).setOnClickListener(this);
        findViewById(R.id.btn_inside_modal).setOnClickListener(this);
        findViewById(R.id.btn_modal_custom).setOnClickListener(this);
        findViewById(R.id.btn_no_arrow).setOnClickListener(this);
        findViewById(R.id.btn_custom_arrow).setOnClickListener(this);
        findViewById(R.id.btn_dialog).setOnClickListener(this);
        findViewById(R.id.btn_other_activity).setOnClickListener(this);

        NestedScrollView scroll = (NestedScrollView) findViewById(R.id.scroll);
        scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int diff = oldScrollY - scrollY;
                if (diff < 0) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }
        });
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.fab) {
            new SimpleTooltip.Builder(this)
                    .anchorView(v)
                    .text("Floating Action Button")
                    .gravity(Gravity.START)
                    .onDismissListener(new SimpleTooltip.OnDismissListener() {
                        @Override
                        public void onDismiss(SimpleTooltip tooltip) {
                            System.out.println("dismiss " + tooltip);
                        }
                    })
                    .onShowListener(new SimpleTooltip.OnShowListener() {
                        @Override
                        public void onShow(SimpleTooltip tooltip) {
                            System.out.println("show " + tooltip);
                        }
                    })
                    .build()
                    .show();

        } else if (v.getId() == R.id.btn_simple) {
            new SimpleTooltip.Builder(this)
                    .anchorView(v)
                    .text(getString(R.string.btn_simple))
                    .gravity(Gravity.END)
                    .build()
                    .show();

        } else if (v.getId() == R.id.btn_animated) {
            new SimpleTooltip.Builder(this)
                    .anchorView(v)
                    .text(getString(R.string.btn_animated))
                    .gravity(Gravity.TOP)
                    .animated(true)
                    .build()
                    .show();

        } else if (v.getId() == R.id.btn_overlay) {
            new SimpleTooltip.Builder(this)
                    .anchorView(v)
                    .text(getString(R.string.btn_overlay))
                    .gravity(Gravity.START)
                    .animated(true)
                    .transparentOverlay(false)
                    .build()
                    .show();

        } else if (v.getId() == R.id.btn_maxwidth) {
            new SimpleTooltip.Builder(this)
                    .anchorView(v)
                    .text(getString(R.string.btn_maxwidth) + getString(R.string.btn_maxwidth) + getString(R.string.btn_maxwidth) + getString(R.string.btn_maxwidth) + getString(R.string.btn_maxwidth))
                    .gravity(Gravity.END)
                    .maxWidth(R.dimen.simpletooltip_max_width)
                    .build()
                    .show();

        } else if (v.getId() == R.id.btn_outside) {
            new SimpleTooltip.Builder(this)
                    .anchorView(v)
                    .text(getString(R.string.btn_outside))
                    .gravity(Gravity.BOTTOM)
                    .dismissOnOutsideTouch(true)
                    .dismissOnInsideTouch(false)
                    .build()
                    .show();

        } else if (v.getId() == R.id.btn_inside) {
            new SimpleTooltip.Builder(this)
                    .anchorView(v)
                    .text(getString(R.string.btn_inside))
                    .gravity(Gravity.START)
                    .dismissOnOutsideTouch(false)
                    .dismissOnInsideTouch(true)
                    .build()
                    .show();

        } else if (v.getId() == R.id.btn_inside_modal) {
            new SimpleTooltip.Builder(this)
                    .anchorView(v)
                    .text(getString(R.string.btn_inside_modal))
                    .gravity(Gravity.END)
                    .dismissOnOutsideTouch(false)
                    .dismissOnInsideTouch(true)
                    .modal(true)
                    .build()
                    .show();

        } else if (v.getId() == R.id.btn_modal_custom) {
            final SimpleTooltip tooltip = new SimpleTooltip.Builder(this)
                    .anchorView(v)
                    .text(getString(R.string.btn_modal_custom))
                    .gravity(Gravity.TOP)
                    .dismissOnOutsideTouch(false)
                    .dismissOnInsideTouch(false)
                    .modal(true)
                    .animated(true)
                    .animationDuration(2000)
                    .animationPadding(SimpleTooltipUtils.pxFromDp(50))
                    .contentView(R.layout.tooltip_custom, R.id.tv_text)
                    .build();

            tooltip.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v2) {
                    if (tooltip.isShowing())
                        tooltip.dismiss();
                    new SimpleTooltip.Builder(v.getContext())
                            .anchorView(v)
                            .text(getString(R.string.btn_next))
                            .gravity(Gravity.BOTTOM)
                            .build()
                            .show();
                }
            });

            tooltip.show();
        } else if (v.getId() == R.id.btn_no_arrow) {
            new SimpleTooltip.Builder(this)
                    .anchorView(v)
                    .text(getString(R.string.btn_no_arrow))
                    .gravity(Gravity.START)
                    .showArrow(false)
                    .modal(true)
                    .animated(true)
                    .build()
                    .show();

        } else if (v.getId() == R.id.btn_custom_arrow) {
            new SimpleTooltip.Builder(this)
                    .anchorView(v)
                    .text(getString(R.string.btn_custom_arrow))
                    .gravity(Gravity.END)
                    .modal(true)
                    .arrowDrawable(android.R.drawable.ic_media_previous)
                    .arrowHeight((int) SimpleTooltipUtils.pxFromDp(50))
                    .arrowWidth((int) SimpleTooltipUtils.pxFromDp(50))
                    .build()
                    .show();

        } else if (v.getId() == R.id.btn_dialog) {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog);
            dialog.show();

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = (int) SimpleTooltipUtils.pxFromDp(300);
            lp.height = (int) SimpleTooltipUtils.pxFromDp(300);
            dialog.getWindow().setAttributes(lp);

            final Button btnInDialog = (Button) dialog.findViewById(R.id.btn_in_dialog);
            btnInDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SimpleTooltip.Builder(MainActivity.this)
                            .anchorView(btnInDialog)
                            .text(getString(R.string.btn_in_dialog))
                            .gravity(Gravity.BOTTOM)
                            .animated(true)
                            .transparentOverlay(false)
                            .build()
                            .show();
                }
            });
            final Button btnClose = (Button) dialog.findViewById(R.id.btn_close);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        } else if (v.getId() == R.id.btn_other_activity) {
            startActivity(new Intent(this, OtherActivity.class));
        }
    }
}
