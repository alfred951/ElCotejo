package co.edu.eafit.yomas10.Util;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by alejandro on 27/10/15.
 */
public class HttpRequestSender {
    HttpURLConnection httpURLConnection = new HttpURLConnection(null) {
        @Override
        public void disconnect() {
            
        }

        @Override
        public boolean usingProxy() {
            return false;
        }

        @Override
        public void connect() throws IOException {

        }

    };
}
