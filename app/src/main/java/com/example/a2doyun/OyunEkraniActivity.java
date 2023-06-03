package com.example.a2doyun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class OyunEkraniActivity extends AppCompatActivity {
    private ConstraintLayout cl;
    private TextView textViewSkor,textViewOyunaBasla;
    private ImageView anakarakter,sariDaire,pembeUcgen,siyahKarakter;
    //Pozisyonlar
    private int anakarakterX;
    private int anakarakterY;
    private int sariDaireX;
    private int sariDaireY;
    private int pembeUcgenX;
    private int pembeUcgenY;
    private int siyahKarakterX;
    private int siyahKarakterY;
    //Kontroller
    private boolean dokunmaKontrol = false;
    private boolean baslangicKontrol = false;
    //Boyutlar
    private int ekranGenisligi;
    private int ekranYuksekligi;
    private int anakarakterGenisligi;
    private int anakarakterYuksekligi;
    //Hızlar
    private int siyahKarakterHız;
    private int pembeUcgenHız;
    private int sariDaireHız;
    private int anakarakterHız;
    //skor
    private int skor;
    private Timer timer = new Timer();
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyun_ekrani);

        cl = findViewById(R.id.cl);
        textViewSkor = findViewById(R.id.textViewSkor);
        textViewOyunaBasla = findViewById(R.id.textViewOyunaBasla);
        anakarakter = findViewById(R.id.anakarakter);
        sariDaire = findViewById(R.id.sariDaire);
        pembeUcgen = findViewById(R.id.pembeUcgen);
        siyahKarakter = findViewById(R.id.siyahKarakter);

        // Cisimleri ekranın dışına çıkarma
        siyahKarakter.setX(-80);
        siyahKarakter.setY(-80);
        pembeUcgen.setX(-80);
        pembeUcgen.setY(-80);
        sariDaire.setX(-80);
        sariDaire.setY(-80);

        textViewOyunaBasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OyunEkraniActivity.this,SonucEkraniActivity.class));
                finish();
            }
        });

        cl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(baslangicKontrol){
                    if (event.getAction() == MotionEvent.ACTION_DOWN){
                        Log.e("Event","Ekrana dokunuldu");
                        dokunmaKontrol = true;
                    }
                    if(event.getAction() == MotionEvent.ACTION_UP){
                        Log.e("MotionEvent","Ekranı Bıraktı");
                        dokunmaKontrol = false;
                    }
                }else {
                    baslangicKontrol = true;

                    textViewOyunaBasla.setVisibility(View.INVISIBLE);

                    anakarakterX = (int) anakarakter.getX();
                    anakarakterY = (int) anakarakter.getY();

                    anakarakterGenisligi = anakarakter.getWidth();
                    anakarakterYuksekligi = anakarakter.getHeight();
                    ekranGenisligi = cl.getWidth();
                    ekranYuksekligi = cl.getHeight();

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    anakarakterHareketEttirme();
                                    cisimleriHareketEttir();
                                    carpismaKontrol();
                                }
                            });
                        }
                    },0,20);
                }
                return true;
            }
        });
    }
    public void anakarakterHareketEttirme(){
        anakarakterHız = Math.round(ekranYuksekligi/60); // 1280 / 60 = 20

        if(dokunmaKontrol){
            anakarakterY-=anakarakterHız;
        }else{
            anakarakterY+=anakarakterHız;
        }
        if(anakarakterY <= 0){
            anakarakterY = 0;
        }
        if(anakarakterY >= ekranYuksekligi - anakarakterYuksekligi){
            anakarakterY = ekranYuksekligi - anakarakterYuksekligi;
        }
        anakarakter.setY(anakarakterY);
    }

    public void cisimleriHareketEttir(){
        siyahKarakterHız = Math.round(ekranGenisligi/60); // 760 / 60 = 13
        pembeUcgenHız = Math.round(ekranGenisligi/30); // 760 / 30 = 25
        sariDaireHız = Math.round(ekranGenisligi/60); // 760 / 60 = 13

        siyahKarakterX-=siyahKarakterHız;
        if (siyahKarakterX < 0 ){
            siyahKarakterX = ekranGenisligi + 20;
            siyahKarakterY = (int) Math.floor(Math.random() * ekranYuksekligi);
        }

        siyahKarakter.setX(siyahKarakterX);
        siyahKarakter.setY(siyahKarakterY);

        pembeUcgenX-=pembeUcgenHız;
        if (pembeUcgenX < 0 ){
            pembeUcgenX = ekranGenisligi + 20;
            pembeUcgenY = (int) Math.floor(Math.random() * ekranYuksekligi);
        }

        pembeUcgen.setX(pembeUcgenX);
        pembeUcgen.setY(pembeUcgenY);

        sariDaireX-=sariDaireHız;
        if (sariDaireX < 0 ){
            sariDaireX = ekranGenisligi + 20;
            sariDaireY = (int) Math.floor(Math.random() * ekranYuksekligi);
        }

        sariDaire.setX(sariDaireX);
        sariDaire.setY(sariDaireY);

    }

    public void carpismaKontrol(){

        int saridaireMerkezX = sariDaireX + sariDaire.getWidth()/2;
        int saridaireMerkezY = sariDaireY + sariDaire.getWidth()/2;

        if(0 <= saridaireMerkezX && saridaireMerkezX <= anakarakterGenisligi
                && anakarakterY <= saridaireMerkezY && saridaireMerkezY <= anakarakterY+anakarakterYuksekligi){
            skor+=20;
            sariDaireX = -10;
        }

        int pembeUcgenMerkezX = pembeUcgenX + pembeUcgen.getWidth()/2;
        int pembeUcgenMerkezY = pembeUcgenY + pembeUcgen.getWidth()/2;

        if(0 <= pembeUcgenMerkezX && pembeUcgenMerkezX <= anakarakterGenisligi
                && anakarakterY <= pembeUcgenMerkezY && pembeUcgenMerkezY <= anakarakterY+anakarakterYuksekligi){
            skor+=50;
            pembeUcgenX = -10;
        }

        int siyahKarakterMerkezX = siyahKarakterX + siyahKarakter.getWidth()/2;
        int siyahKarakterMerkezY = siyahKarakterY + siyahKarakter.getWidth()/2;

        if(0 <= siyahKarakterMerkezX && siyahKarakterMerkezX <= anakarakterGenisligi
                && anakarakterY <= siyahKarakterMerkezY && siyahKarakterMerkezY <= anakarakterY+anakarakterYuksekligi){
            siyahKarakterX = -10;

            //Timer durdur
            timer.cancel();
            timer=null;

            Intent intent = new Intent(OyunEkraniActivity.this,SonucEkraniActivity.class);
            intent.putExtra("skor",skor);
            startActivity(intent);
        }

        textViewSkor.setText(String.valueOf(skor));
    }
}