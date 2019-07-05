package com.mrrun.lib.androidstudioprojects.view.episode;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mrrun.lib.androidstudioprojects.R;

/**
 * 剧集选项View
 *
 * @author lipin
 * @version 1.0
 * @date 2019.06.29
 */
public class EpisodeItemView extends FrameLayout {

    private ViewGroup mRootView;
    /**
     * 第多少集
     */
    private TextView mTextNumView;
    /**
     * VIP资源标识
     */
    private View mTextVipView;
    /**
     * 预告资源标识>VIP资源标识
     */
    private View mTextAdvanceView;
    /**
     * 本地资源标识
     */
    private View mTextLocalView;
    /**
     * 是否正在播放标识
     */
    private View mIvPlayingView;

    public EpisodeItemView showNumMark(String text) {
        mTextNumView.setText(text);
        return this;
    }

    public EpisodeItemView showLoaclMark(boolean isShow) {
        mTextLocalView.setVisibility(isShow ? VISIBLE : GONE);
        return this;
    }

    public EpisodeItemView showVipMark(boolean isShow) {
        mTextVipView.setVisibility(isShow ? VISIBLE : GONE);
        return this;
    }

    public EpisodeItemView showPlayingMark(boolean isShow) {
        mIvPlayingView.setVisibility(isShow ? VISIBLE : GONE);
        return this;
    }

    /**
     * 与vip互斥
     *
     * @param isShow
     * @return
     */
    public EpisodeItemView showAdvanceMark(boolean isShow) {
        mTextAdvanceView.setVisibility(isShow ? VISIBLE : GONE);
        return this;
    }

    public EpisodeItemView(@NonNull Context context) {
        this(context, null);
    }

    public EpisodeItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EpisodeItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        this.mRootView = (ViewGroup) View.inflate(getContext(), R.layout.episodeitemview, null);
        this.mTextNumView = this.mRootView.findViewById(R.id.text_num);
        this.mTextVipView = this.mRootView.findViewById(R.id.text_vip);
        this.mTextAdvanceView = this.mRootView.findViewById(R.id.text_advance);
        this.mTextLocalView = this.mRootView.findViewById(R.id.text_local);
        this.mIvPlayingView = this.mRootView.findViewById(R.id.iv_selected_playing);
        this.addView(mRootView);
    }
}
