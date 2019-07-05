package com.mrrun.lib.androidstudioprojects.view.episode;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrrun.lib.androidstudioprojects.R;

import java.util.List;

/**
 * 创建Adapter
 */
public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.VH> {

    /**
     * 创建ViewHolder
     */
    public static class VH extends RecyclerView.ViewHolder {

        public final EpisodeItemView itemView;

        public VH(View v) {
            super(v);
            itemView = v.findViewById(R.id.item_view);
        }
    }

    /**
     * 利用接口给RecyclerView设置点击事件
     */
    private ItemClickListener mItemClickListener;

    public interface ItemClickListener {
        /**
         * Item在可见区域内用户点击事件
         */
        void onItemUserClick(View itemView, int position);

        /**
         * Item在可见/不可见区域内用户直接跳转到某个Position事件
         */
        void onItemUserSet(int position);
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    private EpisodeLayout mEpisodeLayout;

    private List<EpisodeEntity> mDatas;

    /**
     * 当前正在播放位置
     */
    private int mCurPlayingPostion;

    public EpisodeAdapter(EpisodeLayout episodeLayout, List<EpisodeEntity> data) {
        this.mEpisodeLayout = episodeLayout;
        this.mDatas = data;
    }

    /**
     * 在Adapter中实现3个方法
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        if (!checkDataValid(mDatas)) {
            return;
        }
        EpisodeEntity episodeEntity = mDatas.get(position);
        holder.itemView.setTag(position);
        holder.itemView.showNumMark(String.format("%d", position));
        holder.itemView.showLoaclMark(episodeEntity.loaclFlag);
        holder.itemView.showVipMark((episodeEntity.vipFlag));
        holder.itemView.showAdvanceMark(episodeEntity.advanceFlag);
        holder.itemView.showPlayingMark(episodeEntity.playingFlag);
        if (episodeEntity.playingFlag) {
            mCurPlayingPostion = position;
        }
        /**
         * 响应user点击剧集Item
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**刷新选中剧集UI*/
                mEpisodeLayout.updateEpisodesUI(position);
                /**item 点击事件*/
                if (mItemClickListener != null) {
                    mItemClickListener.onItemUserClick(holder.itemView, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return checkDataValid(mDatas) ? mDatas.size() : 0;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        // LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_item_episode, parent, false);
        return new VH(v);
    }

    /**
     * 用户直接设置跳转剧集位置
     *
     * @param position
     */
    public void userSet2EpisodesPosition(int position) {
        if (position < 0 || position > getItemCount()) {
            throw new IndexOutOfBoundsException("position is out index!");
        }
        userSet2EpisodesPosition(position);
        /**item 设置事件*/
        if (mItemClickListener != null) {
            mItemClickListener.onItemUserSet(position);
        }
    }

    private boolean checkDataValid(List data) {
        if (data == null) {
            return false;
        }
        if (data.size() <= 0) {
            return false;
        }
        return true;
    }

    public List<EpisodeEntity> getDatas() {
        return mDatas;
    }

    public int getCurPlayingPostion() {
        return mCurPlayingPostion;
    }
}
