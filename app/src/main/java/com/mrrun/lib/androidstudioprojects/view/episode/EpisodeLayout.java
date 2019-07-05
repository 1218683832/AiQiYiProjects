package com.mrrun.lib.androidstudioprojects.view.episode;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

/**
 * 仿爱奇艺剧集列表
 * 手指滑动，横向滚动;
 * 用户直接设置跳转剧集位置;
 * 滑动效果，滑到中间位置;
 * 设置VIP资源标识、预告资源标识、本地资源标识、是否正在播放标识;
 *
 * @author lipin
 * @version 1.0
 * @date 2019.06.29
 */
public class EpisodeLayout extends RecyclerView {

    private static final String TAG = EpisodeLayout.class.getSimpleName();

    /**
     * 当前播放小节的view位置
     */
    private int mCurViewPostion = -1;

    private int mScreenWidth;

    private LinearLayoutManager mLayoutManager;

    private EpisodeAdapter mAdapter;

    public EpisodeLayout(@NonNull Context context) {
        this(context, null);
    }

    public EpisodeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EpisodeLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScreenWidth = getScreenWidth();
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        mAdapter = (EpisodeAdapter) adapter;
    }

    @Override
    public void setLayoutManager(@Nullable LayoutManager layout) {
        super.setLayoutManager(layout);
        mLayoutManager = (LinearLayoutManager) layout;
    }

    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    private int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowMgr = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowMgr.getDefaultDisplay().getRealMetrics(dm);
        // 获取宽度
        return dm.widthPixels;
    }

    /**
     * 刷新选中剧集UI
     */
    protected void updateEpisodesUI(int position) {
        mCurViewPostion = position;
        /**自动移动到播放的小节中端*/
        move2Middle();

        /**修改bean数据*/
        List datas = mAdapter.getDatas();
        EpisodeEntity preEntity = (EpisodeEntity) datas.get(mAdapter.getCurPlayingPostion());
        EpisodeEntity nowEntity = (EpisodeEntity) datas.get(position);
        preEntity.playingFlag = false;
        nowEntity.playingFlag = true;

        /**更新这2个地方的UI*/
        mAdapter.notifyItemChanged(mAdapter.getCurPlayingPostion());
        mAdapter.notifyItemChanged(position);
    }

    private void move2Middle() {
        Log.d(TAG, "mCurViewPostion; = " + mCurViewPostion);
        // 说明设置的位置在可见范围内
        if (mLayoutManager.findFirstVisibleItemPosition() <= mCurViewPostion && mCurViewPostion <= mLayoutManager.findLastVisibleItemPosition()) {
            realMove();
            // 说明设置的位置在不可见范围内且小于第一个可见元素
        } else if (mCurViewPostion < mLayoutManager.findFirstVisibleItemPosition()) {
            this.smoothScrollToPosition(mCurViewPostion);
            runMove(mCurViewPostion, 1);
            // 说明设置的位置在不可见范围内且大于最后一个可见元素
        } else if (mCurViewPostion > mLayoutManager.findLastVisibleItemPosition()) {
            this.smoothScrollToPosition(mCurViewPostion);
            runMove(mCurViewPostion, 0);
        }
    }

    private void runMove(final int position, final int code) {
        int i = 0;
        switch (code) {
            case 0:
                i = mLayoutManager.findLastCompletelyVisibleItemPosition();
                break;
            case 1:
                i = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                break;
            default:
                break;
        }
        final boolean ss = i == position;
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ss) {
                    realMove();
                } else {
                    runMove(position, code);
                }

            }
        }, 100);
    }

    private void realMove() {
        int firstVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
        int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        Log.d(TAG, "firstVisibleItems; = " + firstVisibleItems);
        Log.d(TAG, "lastVisibleItem = " + lastVisibleItem);

        // 点击的元素
        View pView = this.getChildAt(mCurViewPostion - mLayoutManager.findFirstVisibleItemPosition());
        int p = (int) pView.getTag();
        Log.d(TAG, "tag postion = " + p);
        pView = this.getChildAt(mCurViewPostion - mLayoutManager.findFirstVisibleItemPosition() + Math.abs(mCurViewPostion - p));
        // 点击的元素中心点位置
        int pW = pView.getLeft() + pView.getMeasuredWidth() / 2;
        int dx;
        // 在水平中心点左边
        if (pW < mScreenWidth / 2) {
            dx = pW - this.getMeasuredWidth() / 2;
            this.smoothScrollBy(dx, 0);
        } else {
            dx = pW - this.getMeasuredWidth() / 2;
            this.smoothScrollBy(dx, 0);
        }
    }
}
