package com.example.helislaptop.tinnews.tin;

import android.annotation.SuppressLint;

import com.example.helislaptop.tinnews.TinApplication;
import com.example.helislaptop.tinnews.database.AppDatabase;
import com.example.helislaptop.tinnews.retrofit.NewsRequestApi;
import com.example.helislaptop.tinnews.retrofit.RetrofitClient;
import com.example.helislaptop.tinnews.retrofit.response.News;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TinModel implements TinContract.Model {

    //keep the reference of TinContract.Presenter
    private TinContract.Presenter presenter;
    private final AppDatabase db;
    @Override
    public void setPresenter(TinContract.Presenter presenter) {
        //assign the presenter
        this.presenter = presenter;
    }

    @SuppressLint("CheckResult")
    @Override
    public void fetchData(String country) {
        newsRequestApi.getNewsByCountry(country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(baseResponse -> baseResponse != null && baseResponse.articles != null)
                .subscribe(baseResponse -> {
                    presenter.showNewsCard(baseResponse.articles);
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void saveFavoriteNews(News news) {
        Completable.fromAction(() -> db.newsDao().insertNews(news)).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(() ->{

        }, error -> presenter.onError());

    }

    private final NewsRequestApi newsRequestApi;

    public TinModel() {
        newsRequestApi = RetrofitClient.getInstance().create(NewsRequestApi.class);
        db = TinApplication.getDataBase();
    }

}
