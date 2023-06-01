package com.example.redbook.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.redbook.R;


/**
 * @authors: 唐辉
 * @date:
 * @description:自定义我的界面条目点击事件
 **/
public class MenuOptionsView extends FrameLayout implements View.OnClickListener{

    private RelativeLayout rlContent;
    private ImageView leftImg;
    private ImageView rightImg;
    private TextView leftText;
    private TextView rightText;
    private TextView centerText;
    private View contentView;

    private Context context;
    private String leftTitle = null;
    private String rightTitle = null;
    private String centerTitle = null;
    private int leftImgIds;
    private int rightImgIds;
    private float imgLpadding;
    private float imgRpadding;
    private float imgLmargin;
    private float imgRmargin;
    private float leftTvSize;
    private float rightTvSize;
    private float centerTvSize;

    private MenuOptionsLImgClickListener menuOptionsLImgClickListener;
    private MenuOptionsRImgClickListener menuOptionsRImgClickListener;
    private MenuOptionsLTvClickListener menuOptionsLTvClickListener;
    private MenuOptionsRTvClickListener menuOptionsRTvClickListener;
    private MenuOptionsCTvClickListener menuOptionsCTvClickListener;
    private MenuOptionsClickListener menuOptionsClickListener;

    public MenuOptionsView(Context context) {
        super(context);
        this.context = context;
    }

    public MenuOptionsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }

    public MenuOptionsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs);
    }

    private void init(Context context,AttributeSet attrs)  {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.menu_options_view_style);
        leftTitle = typedArray.getString(R.styleable.menu_options_view_style_title_left);
        rightTitle = typedArray.getString(R.styleable.menu_options_view_style_title_right);
        centerTitle = typedArray.getString(R.styleable.menu_options_view_style_title_center);
        leftImgIds = typedArray.getResourceId(R.styleable.menu_options_view_style_img_left_src,-1);
        rightImgIds = typedArray.getResourceId(R.styleable.menu_options_view_style_img_right_src,-1);

        imgLpadding = typedArray.getDimension(R.styleable.menu_options_view_style_imgLpadding,0);
        imgRpadding = typedArray.getDimension(R.styleable.menu_options_view_style_imgRpadding,0);
        imgLmargin = typedArray.getDimension(R.styleable.menu_options_view_style_imgLmargin,0);
        imgRmargin = typedArray.getDimension(R.styleable.menu_options_view_style_imgRmargin,0);

        leftTvSize = typedArray.getDimension(R.styleable.menu_options_view_style_leftTvSize,0);
        rightTvSize = typedArray.getDimension(R.styleable.menu_options_view_style_rightTvSize,0);
        centerTvSize = typedArray.getDimension(R.styleable.menu_options_view_style_centerTvSize,0);
        typedArray.recycle();
        initView();
    }

    private void initView(){
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_menu_options,null);
        this.addView(contentView);

        leftImg = (ImageView) contentView.findViewById(R.id.iv_left);
        rightImg = (ImageView) contentView.findViewById(R.id.iv_right);
        leftText = (TextView) contentView.findViewById(R.id.tv_title_left);
        rightText = (TextView) contentView.findViewById(R.id.tv_title_right);
        centerText = (TextView) contentView.findViewById(R.id.tv_title_center);
        rlContent = (RelativeLayout) contentView.findViewById(R.id.rl_content);

        tvStatChange();

        leftImg.setImageResource(leftImgIds);
        rightImg.setImageResource(rightImgIds);
        leftText.setText(leftTitle);
        rightText.setText(rightTitle);
        centerText.setText(centerTitle);

        leftText.setTextSize(TypedValue.COMPLEX_UNIT_PX,leftTvSize+1);
        rightText.setTextSize(TypedValue.COMPLEX_UNIT_PX,rightTvSize+1);
        centerText.setTextSize(TypedValue.COMPLEX_UNIT_PX,centerTvSize+1);

        leftImg.setPadding((int)imgLpadding,(int)imgLpadding,(int)imgLmargin,(int)imgLpadding);
        rightImg.setPadding((int)imgRmargin,(int)imgRpadding,(int)imgRpadding,(int)imgRpadding);

        leftImg.setOnClickListener(this);
        rightImg.setOnClickListener(this);
        leftText.setOnClickListener(this);
        rightText.setOnClickListener(this);
        centerText.setOnClickListener(this);
        rlContent.setOnClickListener(this);
    }

    private int dp2px(float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                if (menuOptionsClickListener != null){
                    menuOptionsClickListener.layoutClick();
                    break;
                }
                if (menuOptionsLImgClickListener != null){
                    menuOptionsLImgClickListener.leftImgClick();
                }
                break;
            case R.id.iv_right:
                if (menuOptionsClickListener != null){
                    menuOptionsClickListener.layoutClick();
                    break;
                }
                if (menuOptionsRImgClickListener != null){
                    menuOptionsRImgClickListener.rightImgClick();
                }
                break;
            case R.id.tv_title_left:
                if (menuOptionsClickListener != null){
                    menuOptionsClickListener.layoutClick();
                    break;
                }
                if (menuOptionsLTvClickListener != null){
                    menuOptionsLTvClickListener.leftTvClick();
                }
                break;
            case R.id.tv_title_right:
                if (menuOptionsClickListener != null){
                    menuOptionsClickListener.layoutClick();
                    break;
                }
                if (menuOptionsRTvClickListener != null){
                    menuOptionsRTvClickListener.rightTvClick();
                }
                break;
            case R.id.tv_title_center:
                if (menuOptionsClickListener != null){
                    menuOptionsClickListener.layoutClick();
                    break;
                }
                if (menuOptionsCTvClickListener != null){
                    menuOptionsCTvClickListener.centerClick();
                }
                break;
            case R.id.rl_content:
                if (menuOptionsClickListener != null){
                    menuOptionsClickListener.layoutClick();
                }
                break;
        }
    }

    public int sp2px(float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     *  动态设置3个TextView的显示和隐藏
     */
    public void tvStatChange(){
        int k = 0;
        centerText.setVisibility(GONE);

        if ((centerTitle == null)||(centerTitle.equals(""))){
            centerText.setVisibility(GONE);
        }else {
            // 显示中间的内容时候，左右就隐藏
            centerText.setVisibility(VISIBLE);
            leftText.setVisibility(GONE);
            rightText.setVisibility(GONE);
            return;
        }

        if ((leftTitle == null)||(leftTitle.equals(""))){
            leftText.setVisibility(GONE);
        }else {
            leftText.setVisibility(VISIBLE);
            k++;
        }

        if ((rightTitle == null)||(rightTitle.equals(""))){
            rightText.setVisibility(GONE);
        }else {
            rightText.setVisibility(VISIBLE);
            k++;
        }
        // 如果显示两边的情况下，让中间分割
        if (k == 2){
            centerText.setVisibility(VISIBLE);
        }

    }

    public void setLeftText(String str){
        leftTitle = str;
        leftText.setText(leftTitle);
        tvStatChange();
    }

    public void setRightText(String str){
        rightTitle = str;
        rightText.setText(rightTitle);
        tvStatChange();
    }

    public void setCenterText(String str){
        centerTitle = str;
        centerText.setText(centerTitle);
        tvStatChange();
    }

    public String getLeftTitle() {
        return leftTitle;
    }

    public String getRightTitle() {
        return rightTitle;
    }

    public String getCenterTitle() {
        return centerTitle;
    }

    public void setLeftImgSrc(int Ids){
        leftImg.setImageResource(Ids);
    }

    public void setRightImgSrc(int Ids){
        rightImg.setImageResource(Ids);
    }

    public interface MenuOptionsLImgClickListener{
        void leftImgClick();
    }

    public interface MenuOptionsRImgClickListener{
        void rightImgClick();
    }

    public interface MenuOptionsLTvClickListener{
        void leftTvClick();
    }

    public interface MenuOptionsRTvClickListener{
        void rightTvClick();
    }

    public interface MenuOptionsCTvClickListener{
        void centerClick();
    }

    public interface MenuOptionsClickListener{
        void layoutClick();
    }

    public void setMenuOptionsLImgClickListener(MenuOptionsLImgClickListener menuOptionsLImgClickListener) {
        this.menuOptionsLImgClickListener = menuOptionsLImgClickListener;
    }

    public void setMenuOptionsRImgClickListener(MenuOptionsRImgClickListener menuOptionsRImgClickListener) {
        this.menuOptionsRImgClickListener = menuOptionsRImgClickListener;
    }

    public void setMenuOptionsLTvClickListener(MenuOptionsLTvClickListener menuOptionsLTvClickListener) {
        this.menuOptionsLTvClickListener = menuOptionsLTvClickListener;
    }

    public void setMenuOptionsRTvClickListener(MenuOptionsRTvClickListener menuOptionsRTvClickListener) {
        this.menuOptionsRTvClickListener = menuOptionsRTvClickListener;
    }

    public void setMenuOptionsCTvClickListener(MenuOptionsCTvClickListener menuOptionsCTvClickListener) {
        this.menuOptionsCTvClickListener = menuOptionsCTvClickListener;
    }

    public void setMenuOptionsClickListener(MenuOptionsClickListener menuOptionsClickListener) {
        this.menuOptionsClickListener = menuOptionsClickListener;
    }
}
