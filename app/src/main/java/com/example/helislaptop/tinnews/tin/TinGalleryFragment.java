package com.example.helislaptop.tinnews.tin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.helislaptop.tinnews.R;
import com.example.helislaptop.tinnews.common.TinBasicFragment;
import com.example.helislaptop.tinnews.mvp.MvpFragment;
import com.example.helislaptop.tinnews.retrofit.NewsRequestApi;
import com.example.helislaptop.tinnews.retrofit.RetrofitClient;
import com.example.helislaptop.tinnews.retrofit.response.News;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class TinGalleryFragment extends MvpFragment<TinContract.Presenter> implements TinNewsCard.OnSwipeListener, TinContract.View {
    //SwipePlaceHolderView
    private SwipePlaceHolderView mSwipeView;

    public static TinGalleryFragment newInstance() {

        Bundle args = new Bundle();

        TinGalleryFragment fragment = new TinGalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tin_gallery, container, false);

        mSwipeView = view.findViewById(R.id.swipeView);

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tin_news_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tin_news_swipe_out_msg_view));

        view.findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        view.findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });



        return view;
    }

    //below onCreateView



    public void showNewsCard(List<News> newsList) {
        mSwipeView.removeAllViews();
        for (News news : newsList) {
            TinNewsCard tinNewsCard = new TinNewsCard(news, mSwipeView, this);
            mSwipeView.addView(tinNewsCard);
        }
    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), "News has been inserted before", Toast.LENGTH_SHORT).show();
    }

    //on top of onLike

    @Override
    public void onLike(News news) {
        presenter.saveFavoriteNews(news);
    }


    //make sure you have implement it
    @Override
    public TinContract.Presenter getPresenter() {
        return new TinPresenter();
    }
}

