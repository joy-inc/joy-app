package com.joy.app.activity.plan;

import com.joy.app.bean.plan.PlanListItem;
import com.android.library.activity.BaseHttpRvActivity;
import com.android.library.httptask.ObjectRequest;

/**
 * @author litong  <br>
 * @Description 用户行程规划    <br>
 */
public class UserPlanListActivity extends BaseHttpRvActivity<PlanListItem> {

    @Override
    protected ObjectRequest<PlanListItem> getObjectRequest() {
        return null;
    }
}
