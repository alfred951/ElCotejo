package co.edu.eafit.yomas10.Util;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by jalvar53 on 11/15/15.
 */
public class HttpBridge{

    public static Intent startWorking(Context ctx, HashMap<String, String> mapa, Receiver receiver, String type) throws UnsupportedEncodingException {
        OwnResultReceiver mReceiver = new OwnResultReceiver(new Handler());
        mReceiver.setmReceiver(receiver);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, ctx, Http.class);
        intent.putExtra("mReceiver", mReceiver);
        intent.putExtra("urlget", Http.getGetDataString(type, mapa));
        return intent;
    }
}
