一. RecyclerView的四级缓存
    1.mChangeScrap和mAttachScrap : 用来缓存还在屏幕内的ViewHolder
    2.mCacheViews: 用来缓存移除屏幕之外的ViewHolder
    3.mViewCacheExtension: 用户自定义的扩展缓存，需要用户自己管理View的创建和缓存
    4.RecycledViewPool: ViewHolder缓存池
