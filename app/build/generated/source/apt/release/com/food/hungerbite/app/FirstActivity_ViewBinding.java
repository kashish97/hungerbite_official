// Generated code from Butter Knife. Do not modify!
package com.food.hungerbite.app;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FirstActivity_ViewBinding implements Unbinder {
  private FirstActivity target;

  @UiThread
  public FirstActivity_ViewBinding(FirstActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FirstActivity_ViewBinding(FirstActivity target, View source) {
    this.target = target;

    target.r1 = Utils.findRequiredViewAsType(source, R.id.r1, "field 'r1'", FrameLayout.class);
    target.root = Utils.findRequiredViewAsType(source, R.id.root, "field 'root'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FirstActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.r1 = null;
    target.root = null;
  }
}
