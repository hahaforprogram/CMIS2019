package com.haha.cmis.barscanreciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Created by hah on 2018/3/27.    BarScanListener  BarScanListener
 */

public class barScanReciever extends BroadcastReceiver {
    private static final String lachesisBarScancode = "lachesis_barcode_value_notice_broadcast";
    private static final String lachesisBarScancodeExtra = "lachesis_barcode_value_notice_broadcast_data_string";
    private BarScanInteraction mBarScanInteraction;

    @Override
    public void onReceive(Context context, @NonNull Intent intent) {
        String strBarcode = "";
        String action = intent.getAction();
        if (action.equals(lachesisBarScancode)) {
            strBarcode = intent.getStringExtra(lachesisBarScancodeExtra);
        }
        if (TextUtils.isEmpty(strBarcode)) {
            return;
        }
        mBarScanInteraction.onBarScanEvent(strBarcode);
    }

    public interface BarScanInteraction {
        void onBarScanEvent(String strBarCode);
    }

    public void BarScanInteractionListener(BarScanInteraction brInteraction) {
        this.mBarScanInteraction = brInteraction;
    }
}
