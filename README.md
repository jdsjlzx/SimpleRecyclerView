# SimpleRecyclerView
基于 https://github.com/cundong/HeaderAndFooterRecyclerView 修改.

SimpleRecyclerView支持addHeaderView、 addFooterView、分页加载，同时解决了RecyclerView与SwipeRefreshLayout滑动冲突的问题。

解决RecyclerView与SwipeRefreshLayout滑动冲突：

mRecyclerView.addOnScrollListener(mOnScrollListener);
mOnScrollListener.setSwipeRefreshLayout(mSwipeRefreshLayout);


注意事项

如果已经使用RecyclerViewUtils.setHeaderView(mRecyclerView, view); 为RecyclerView添加了HeaderView，那么再调用ViewHolder类的getAdapterPosition()、getLayoutPosition()时返回的值就会因为增加了Header而受影响（返回的position等于真实的position+headerCounter）。

因此，这种情况下请使用RecyclerViewUtils.getAdapterPosition(mRecyclerView, ViewHolder.this)、RecyclerViewUtils.getLayoutPosition(mRecyclerView, ViewHolder.this) 两个方法来替代。

