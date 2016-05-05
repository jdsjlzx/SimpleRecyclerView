package com.cundong.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by lizhixian on 16/1/4.
 */
public class CustRecyclerView extends RecyclerView{
    private View emptyView;
    public CustRecyclerView(Context context) {
        super(context);
    }

    public CustRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            Adapter adapter =  getAdapter();

            if(adapter instanceof HeaderAndFooterRecyclerViewAdapter){
                HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) adapter;
                if(headerAndFooterAdapter.getInnerAdapter() != null && emptyView != null) {
                    int count = headerAndFooterAdapter.getInnerAdapter().getItemCount();
                    if(count == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                        CustRecyclerView.this.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        CustRecyclerView.this.setVisibility(View.VISIBLE);
                    }
                }
            }else {
                if(adapter != null && emptyView != null) {
                    if(adapter.getItemCount() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                        CustRecyclerView.this.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        CustRecyclerView.this.setVisibility(View.VISIBLE);
                    }
                }
            }

        }
    };

    public void setAdapter(Adapter adapter) {
        Adapter oldAdapter = getAdapter();
        if(oldAdapter != null && emptyObserver != null){
            oldAdapter.unregisterAdapterDataObserver(emptyObserver);
        }
        super.setAdapter(adapter);

        if(adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }
        emptyObserver.onChanged();
    }

    /**
     * set view when no content item
     * @param emptyView  visiable view when items is empty
     */
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    /**
     * 配置显示图片，需要设置这几个参数，快速滑动时，暂停图片加载
     *
     * @param imageLoader	ImageLoader实例对象
     * @param pauseOnScroll
     * @param pauseOnFling
     */
    public void setOnPauseListenerParams(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling) {

        addOnScrollListener(new NewScrollListener(imageLoader, pauseOnScroll, pauseOnFling));

    }

    /**
     * 滑动自动加载监听器
     */
    private class NewScrollListener extends RecyclerView.OnScrollListener {

        private ImageLoader imageLoader;
        private final boolean pauseOnScroll;
        private final boolean pauseOnFling;

        public NewScrollListener(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling) {
            super();
            this.pauseOnScroll = pauseOnScroll;
            this.pauseOnFling = pauseOnFling;
            this.imageLoader = imageLoader;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            if (imageLoader != null) {
                switch (newState) {
                    case 0:
                        imageLoader.resume();
                        break;

                    case 1:
                        if (pauseOnScroll) {
                            imageLoader.pause();
                        } else {
                            imageLoader.resume();
                        }
                        break;

                    case 2:
                        if (pauseOnFling) {
                            imageLoader.pause();
                        } else {
                            imageLoader.resume();
                        }
                        break;
                }
            }

        }
    }
}
