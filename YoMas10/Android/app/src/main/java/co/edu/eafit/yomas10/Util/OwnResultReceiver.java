package co.edu.eafit.yomas10.Util;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.os.Handler;

/**
 * Created by jalvar53 on 11/12/15.
 */
public class OwnResultReceiver extends ResultReceiver {

    private Receiver mReceiver;

    public OwnResultReceiver(Handler handler){
        super(handler);
    }

    public void setmReceiver(Receiver receiver){
        mReceiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData){
         if(mReceiver != null){
            mReceiver.onReceiveResult(resultCode, resultData);
         }
     }
}
