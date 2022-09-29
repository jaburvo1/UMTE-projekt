package com.example.umte_projekt;

import static com.google.firebase.crashlytics.buildtools.reloc.com.google.common.net.HttpHeaders.USER_AGENT;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Header;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.entity.UrlEncodedFormEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class SkladService extends AppCompatActivity {
    private String urlHlavin = "http://imitgw.uhk.cz:59748";
    private String rezultStatus;
    private Header header;
    private String countPartString;


    public SkladService() {

    }

    public String removeItemPiece(String namePart, int countPart) {
        countPartString =String.valueOf(countPart);
        String url = urlHlavin+"/removeItemPiece";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("nazevdilu", namePart));
        urlParameters.add(new BasicNameValuePair("pocetKusu", countPartString));


        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpResponse response = null;
        try {
            response = client.execute(post);


        } catch (IOException e) {
            //e.printStackTrace();
            response.getStatusLine();
        }
        catch (Exception e){
            response.getStatusLine();
        }
        System.out.println("Sending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        String result ="";
        if(response.getStatusLine().getStatusCode()==200){
            result ="Dil vyskladnen";
        }
/*
        BufferedReader rd = null;
        try {
            rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer result = new StringBuffer();
        String line = "";
        while (true) {
            try {
                if (!((line = rd.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            result.append(line);
        }*/

        //System.out.println(String.valueOf(result));
        return result;  //result.toString();


    }

    public String addItemPiece(String namePart, int countPart){
        countPartString =String.valueOf(countPart);
        String url = urlHlavin+"/addItemPiece";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("nazevdilu", namePart));
        urlParameters.add(new BasicNameValuePair("pocetKusu", countPartString));//int


        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpResponse response = null;
        try {
            response = client.execute(post);


        } catch (IOException e) {
            //e.printStackTrace();
            response.getStatusLine();
        }
        catch (Exception e){
            response.getStatusLine();
        }
        System.out.println("Sending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        String result ="";
        if(response.getStatusLine().getStatusCode()==200){
            result ="Dil naskladnen";
        }
        /*
        BufferedReader rd = null;
        try {
            rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer result = new StringBuffer();
        String line = "";
        while (true) {
            try {
                if (!((line = rd.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            result.append(line);
        }
        */


        //System.out.println(String.valueOf(result));
        return result; //result.toString();

    }


    public String newItem(String namePart, String typePart, String subtypePart, String parametrsPart, String manufacturePart, int countPart) {
        countPartString =String.valueOf(countPart);
        String url = urlHlavin+"/addItem";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("nazevdilu", namePart));
        urlParameters.add(new BasicNameValuePair("druhDilu", typePart));
        urlParameters.add(new BasicNameValuePair("typDilu", subtypePart));
        urlParameters.add(new BasicNameValuePair("vyrobceDilu", manufacturePart));
        urlParameters.add(new BasicNameValuePair("parametryDilu", parametrsPart));
        urlParameters.add(new BasicNameValuePair("pocetKusu", countPartString));


        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpResponse response = null;
        try {
            response = client.execute(post);


        } catch (IOException e) {
            //e.printStackTrace();
            response.getStatusLine();
        }
        catch (Exception e){
            response.getStatusLine();
        }
        System.out.println("Sending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        String result ="";
       if(response.getStatusLine().getStatusCode()==200){
           result ="Dil pridan do skladu";
       }

        /* BufferedReader rd = null;
        try {
            rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //StringBuffer result = new StringBuffer();
        //String line = "";
        /*while (true) {
            try {
                if (!((line = rd.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            result.append(line);
        }
*/
        //System.out.println(String.valueOf(result));
        return result;


    }

    public String getItems() {

        String url = urlHlavin+"/addItemPiece";

        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);

        // add header
        get.setHeader("User-Agent", USER_AGENT);


        HttpResponse response = null;
        try {
            response = client.execute(get);


        } catch (IOException e) {
            //e.printStackTrace();
            response.getStatusLine();
        }
        catch (Exception e){
            response.getStatusLine();
        }

        response.getStatusLine().getStatusCode();

        BufferedReader rd = null;
        try {
            rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer result = new StringBuffer();
        String line = "";
        while (true) {
            try {
                if (!((line = rd.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            result.append(line);
        }


        return result.toString();
    }
}