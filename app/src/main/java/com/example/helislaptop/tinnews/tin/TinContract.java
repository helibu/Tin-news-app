package com.example.helislaptop.tinnews.tin;

import com.example.helislaptop.tinnews.mvp.MvpContract;
import com.example.helislaptop.tinnews.retrofit.response.News;

import java.util.List;

public interface TinContract {


    interface View extends MvpContract.View<Presenter> {
        //5.6 add showNewsCard
        void showNewsCard(List<News> newsList);
        void onError();
    }

    interface Presenter extends MvpContract.Presenter<View, Model> {
        //5.6 add showNewsCard
        void showNewsCard(List<News> newsList);
        void saveFavoriteNews(News news);
        void onError();
    }

    interface Model extends MvpContract.Model<Presenter> {
        //add fetchData here
        void fetchData(String country);
        void saveFavoriteNews(News news);
    }
}

