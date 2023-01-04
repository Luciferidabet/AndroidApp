package com.example.app_ban_hang.activity;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.Utils.utils;
import com.example.app_ban_hang.adapter.NewProduct_adapter;
import com.example.app_ban_hang.adapter.TypeProduct_adapter;
import com.example.app_ban_hang.model.NewProduct;
import com.example.app_ban_hang.model.ProductType;
import com.example.app_ban_hang.retrofit.APIBanHang;
import com.example.app_ban_hang.retrofit.retrofitClient;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewMainActivity;
    NavigationView navigationView;
    ListView listViewMain;
    DrawerLayout drawerLayout;
    TypeProduct_adapter typeProduct_adapter;
    List<ProductType> ListproductTypes;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    APIBanHang apiBanHang;
    List<NewProduct> ListnewProduct;
    NewProduct_adapter newProduct_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang = retrofitClient.getInstance(utils.BASE_URL).create(APIBanHang.class);


        LayoutMain(); // ánh xạ
        Actionbar();

        if (isConnected(this)){
            ActionViewFlipper();
            getProductType();
            getNewProduct();

            getEventClick();



        }
        else
        {
            Toast.makeText(getApplicationContext(),"khong co internet",Toast.LENGTH_LONG).show();
        }
    }

    private void getEventClick() {
        listViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent converse = new Intent(getApplicationContext(), Converse.class);
                        converse.putExtra("loai",1);
                        startActivity(converse);
                        break;
                    case 2:
                        Intent nike = new Intent(getApplicationContext(), Nike.class);
                        startActivity(nike);
                        break;
                    case 3:
                        Intent supreme = new Intent(getApplicationContext(),Supreme.class);
                        startActivity(supreme);
                        break;
                    case 4:
                        Intent vans = new Intent(getApplicationContext(),Vans.class);
                        startActivity(vans);
                        break;

                        
                }
        }
        });
    }

    private void getNewProduct() {
        compositeDisposable.add(apiBanHang.getNewProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        newProductModel -> {
                            if (newProductModel.isSuccess()) {
                                ListnewProduct = newProductModel.getResult();
                                newProduct_adapter = new NewProduct_adapter(getApplicationContext(), ListnewProduct);
                                recyclerViewMainActivity.setAdapter(newProduct_adapter);



                            }
                            },

                           throwable -> Toast.makeText(getApplicationContext(), "khong the ket noi"+ throwable.getMessage(), Toast.LENGTH_LONG ).show()

                ));
    }

    private void getProductType()
    {

        compositeDisposable.add(apiBanHang.getProductType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productTypeModel -> {
                            if(productTypeModel.isSuccess()){
                                ListproductTypes = productTypeModel.getResult();
                                typeProduct_adapter = new TypeProduct_adapter(getApplicationContext(),ListproductTypes);
                                listViewMain.setAdapter(typeProduct_adapter);

                            }

                        }
                ));

    }
    private void ActionViewFlipper(){
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png");
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png");
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg");
        mangquangcao.add("http://genk.mediacdn.vn/2016/dien-may-xanh-1481020960407.png");
        for (int i=0;i<mangquangcao.size();i++)
        {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);

    }
    private void Actionbar() {
        // i don't know why it wrong!!!
/*
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
*/

        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        // dung lamda
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));




    }

    private void LayoutMain() {
        toolbar = findViewById(R.id.ToolbarMain);
        viewFlipper = findViewById(R.id.viewplipper);
        recyclerViewMainActivity = findViewById(R.id.recycleview);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2 );
        recyclerViewMainActivity.setLayoutManager(layoutManager);
        recyclerViewMainActivity.setHasFixedSize(true);




        listViewMain =  findViewById(R.id.listViewMain);
        navigationView =  findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerlayout);
        ///khoi tao list
        ListproductTypes = new ArrayList<>();
        ListnewProduct = new ArrayList<>();
/*
        /// khoi tao adapter
        typeProduct_adapter = new TypeProduct_adapter(getApplicationContext(),ListproductTypes);
        listViewMain.setAdapter(typeProduct_adapter);

 */


    }
    private boolean isConnected (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected()));
        /*
            if( (wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected()) )
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    */
    }
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }

}