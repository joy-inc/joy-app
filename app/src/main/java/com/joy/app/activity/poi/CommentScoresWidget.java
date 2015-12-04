package com.joy.app.activity.poi;

import android.app.Activity;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.library.utils.MathUtil;
import com.android.library.utils.TextUtil;
import com.android.library.view.ExLayoutWidget;
import com.joy.app.R;
import com.joy.app.bean.poi.CommentScores;

/**
 * 五星评分
 * Created by xiaoyu.chen on 15/11/24.
 */
public class CommentScoresWidget extends ExLayoutWidget {

    private AppCompatRatingBar mAcRatingBar;
    private TextView mTvPoiCommentNum;
    private TextView tvCommentLevel;

    private ProgressBar score5;
    private ProgressBar score4;
    private ProgressBar score3;
    private ProgressBar score2;
    private ProgressBar score1;

    private TextView tv5Count;
    private TextView tv4Count;
    private TextView tv3Count;
    private TextView tv2Count;
    private TextView tv1Count;


    public CommentScoresWidget(Activity activity) {

        super(activity);
    }

    @Override
    protected View onCreateView(Activity activity, Object... args) {

        View contentView = activity.getLayoutInflater().inflate(R.layout.view_poi_comment_score, null);

        mAcRatingBar = (AppCompatRatingBar) contentView.findViewById(R.id.acRatingBar);
        mTvPoiCommentNum = (TextView) contentView.findViewById(R.id.tvPoiCommentNum);
        tvCommentLevel = (TextView) contentView.findViewById(R.id.tvCommentLevel);

        score5 = (ProgressBar) contentView.findViewById(R.id.score5);
        score4 = (ProgressBar) contentView.findViewById(R.id.score4);
        score3 = (ProgressBar) contentView.findViewById(R.id.score3);
        score2 = (ProgressBar) contentView.findViewById(R.id.score2);
        score1 = (ProgressBar) contentView.findViewById(R.id.score1);

        tv5Count = (TextView) contentView.findViewById(R.id.tv5Count);
        tv4Count = (TextView) contentView.findViewById(R.id.tv4Count);
        tv3Count = (TextView) contentView.findViewById(R.id.tv3Count);
        tv2Count = (TextView) contentView.findViewById(R.id.tv2Count);
        tv1Count = (TextView) contentView.findViewById(R.id.tv1Count);

        return contentView;
    }

    public void invalidate(final CommentScores data) {

        if (data == null)
            return;

        mAcRatingBar.setRating(MathUtil.parseFloat(data.getComment_level(), 0));
        mTvPoiCommentNum.setText(getActivity().getResources().getString(R.string.kuohao, data.getComment_num()));
        tvCommentLevel.setText(data.getComment_level());

        setMaxValue(data.getComment_num());
        score5.setProgress(MathUtil.parseInt(data.getFive(), 0));
        score4.setProgress(MathUtil.parseInt(data.getFour(), 0));
        score3.setProgress(MathUtil.parseInt(data.getThree(), 0));
        score2.setProgress(MathUtil.parseInt(data.getTwo(), 0));
        score1.setProgress(MathUtil.parseInt(data.getOne(), 0));

//        tv5Count.setText(MathUtil.parseInt(data.getFive(), 0));
//        tv4Count.setText(MathUtil.parseInt(data.getFour(), 0));
//        tv3Count.setText(MathUtil.parseInt(data.getThree(), 0));
//        tv2Count.setText(MathUtil.parseInt(data.getTwo(), 0));
//        tv1Count.setText(MathUtil.parseInt(data.getOne(), 0));
        // todo why 资源找不到？
    }

    private void setMaxValue(String commentCount) {

        int maxValue = 0;

        if (!TextUtil.isEmpty(commentCount))
            maxValue = MathUtil.parseInt(commentCount, 0);

        score5.setMax(maxValue);
        score4.setMax(maxValue);
        score3.setMax(maxValue);
        score2.setMax(maxValue);
        score1.setMax(maxValue);
    }
}