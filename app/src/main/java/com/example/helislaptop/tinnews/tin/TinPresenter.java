package com.example.helislaptop.tinnews.tin;

import com.example.helislaptop.tinnews.profile.CountryEvent;
import com.example.helislaptop.tinnews.retrofit.response.News;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class TinPresenter implements TinContract.Presenter {
    //hold the reference
    private TinContract.View view;
    private final static String DEFAULT_COUNTRY = "us";
    //link the model with presenter
    private TinContract.Model model;

    public TinPresenter() {
        this.model = new TinModel();
        //link the model with presenter
        this.model.setPresenter(this);
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CountryEvent countryEvent) {
        if (this.view != null) {
            this.model.fetchData(countryEvent.country);
        }
    }

    @Override
    public void onViewAttached(TinContract.View view) {
        //hold the reference
        this.view = view;
        this.model.fetchData(DEFAULT_COUNTRY);
    }

    @Override
    public void onViewDetached() {
        //clear the reference
        this.view = null;
    }

    @Override
    public void showNewsCard(List<News> newsList) {
        if (view != null) {
            view.showNewsCard(newsList);
        }
    }

    @Override
    public void saveFavoriteNews(News news) {
        model.saveFavoriteNews(news);
    }

    @Override
    public void onError() {
        if (view != null) {
            view.onError();
        }
    }
}
