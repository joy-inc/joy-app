package com.joy.app.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.library.utils.LogMgr;
import com.joy.library.share.ShareConstant;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-12-06
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        register(getIntent());
    }

    private void register(Intent intent) {

        if (api == null) {
            api = WXAPIFactory.createWXAPI(this, ShareConstant.getIns().getWeixinAppid(), false);
            api.registerApp(ShareConstant.getIns().getWeixinAppid());
        }
        api.handleIntent(intent, this);

    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        register(intent);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.d("WXEntryActivity"," onReq " + baseReq.toString());
        finish();
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.d("WXEntryActivity"," onResp " + baseResp.errStr + " " + baseResp.errCode);
        finish();

    }
}
