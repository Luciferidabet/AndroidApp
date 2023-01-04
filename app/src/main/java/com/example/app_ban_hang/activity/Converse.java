package com.example.app_ban_hang.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.R;
import com.example.app_ban_hang.Utils.utils;
import com.example.app_ban_hang.adapter.Converse_adapter;
import com.example.app_ban_hang.model.NewProduct;
import com.example.app_ban_hang.retrofit.APIBanHang;
import com.example.app_ban_hang.retrofit.retrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Converse extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    APIBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 0;
    int Type;
    Converse_adapter converse_adapter;
    List<NewProduct> newProductList;
    DrawerLayout drawerLayout;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converse);
        apiBanHang = retrofitClient.getInstance(utils.BASE_URL).create(APIBanHang.class);
        Type = getIntent().getIntExtra("loai", 1);
        AnhXa();
        ActionToolBar();
        GetData(page);
        addEventLoad();

    }

    private void addEventLoad() {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (isLoading == false)
                    {
                        if ( linearLayoutManager.findLastCompletelyVisibleItemPosition() == newProductList.size()-1)
                        {
                            isLoading = true;
                            loadMore();
                        }
                    }
                }
            });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //add null
                newProductList.add(null);
                converse_adapter.notifyItemInserted(newProductList.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //remove null
                newProductList.remove(newProductList.size()-1);
                converse_adapter.notifyItemRemoved(newProductList.size());
                page = page + 1;
                GetData(page);
                converse_adapter.notifyDataSetChanged();
                isLoading = false;
            }
        },2000);
    }

    private void GetData(int page ) {
        compositeDisposable.add(apiBanHang.getProduct(page,Type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        NewProductModel-> {
                             if(NewProductModel.isSuccess()) {
                                 if (converse_adapter == null) {
                                     newProductList = NewProductModel.getResult();
                                     converse_adapter = new Converse_adapter(getApplicationContext(), newProductList);
                                     recyclerView.setAdapter(converse_adapter);
                                 } else {
                                     int vitri = newProductList.size() - 1;
                                     int soluongadd = NewProductModel.getResult().size();
                                     for (int i = 0; i < soluongadd; i++) {
                                         newProductList.add(NewProductModel.getResult().get(i));
                                     }
                                     converse_adapter.notifyItemRangeInserted(vitri, soluongadd);

                                 }
                             }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"khong the ket noi", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.Converse);
        recyclerView = findViewById(R.id.recycleviewConverse);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        newProductList = new ArrayList<>();
    }

    private void ActionToolBar() {
        // i don't know why it wrong!!!

        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        // dung lamda
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
    }
    protected void onDestroy()
    {
        compositeDisposable.clear();
        super.onDestroy();
    }
}