package az.elixir.vin.utils;
import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
public class SendRequest {


    //
    public String sendRequest(String endPoint)  {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.3.18", 3128));
        disableSslVerification();
        String result=null;
        try {
            URL obj = new URL("http://198.199.90.224/api/v1" + endPoint);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection(proxy);
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            result = response.toString();
            System.out.println(response.toString());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return result;

    }
    public int check(String endPoint)  {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.3.18", 3128));
        disableSslVerification();
        int result=0;
        try {
            URL objj = new URL("http://198.199.90.224/api/v1" + endPoint);
            HttpURLConnection conn = (HttpURLConnection) objj.openConnection(proxy);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = conn.getResponseCode();
            result=responseCode;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return result;

    }
    private static void disableSslVerification() {
        try
        {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };
            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
