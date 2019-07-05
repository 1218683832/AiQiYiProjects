package com.mrrun.lib.androidstudioprojects.view.episode;

public class EpisodeEntity {

    public String numText;

    public boolean vipFlag;

    public boolean loaclFlag;

    public boolean advanceFlag;

    public boolean playingFlag;

    public EpisodeEntity(String numText, boolean playingFlag, boolean vipFlag, boolean loaclFlag, boolean advanceFlag) {
        this.numText = numText;
        this.playingFlag = playingFlag;
        this.vipFlag = vipFlag;
        this.loaclFlag = loaclFlag;
        this.advanceFlag = advanceFlag;
    }
}
