package com.example.umte_projekt;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Header;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class SkladService extends AppCompatActivity {
    private String namePart;
    private String urlHlavin = "http://localhost:8080";
    private String rezultStatus;
    private Header header;


    public SkladService() {

    }


    public void eventDepot(int eventMode, Dil dil) {
        if (eventMode == 1) {
            System.out.println("naskladneno");
            addItemPiece();


        } else {
            if (eventMode == 2) {
                System.out.println("vyskladneno");
                removeItemPiece();
            } else {
                newItem();

            }
        }

    }

    private void removeItemPiece() {
        RequestQueue requestQueue = Volley.newRequestQueue(SkladService.this);
        String url = urlHlavin + "/removeItemPiece";

    }

    private void addItemPiece(){
        String url = urlHlavin + "/removeItemPiece";
        /*OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("nazevdilu","CPU")
                .addFormDataPart("pocetKusu","10")
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:8080/addItemPiece")
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();*/
        /*Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8080/addItemPiece")
                .multiPartContent()
                .field("nazevdilu", "CPU")
                .field("pocetKusu", "10")
                .asString();*/


    }


    private void newItem() {


    }
}