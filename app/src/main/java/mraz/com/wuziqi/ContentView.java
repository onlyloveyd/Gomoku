package mraz.com.wuziqi;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Mraz on 2016/7/20.
 */
public class ContentView extends View {

    private static final String ISGAMEOVER = "isgameover";
    private static final String ISWHITE = "iswhite";
    private static final String WHITEARRAY = "whitearray";
    private static final String BLACKARRAY = "blackarray";
    private static final String SUPER = "super";
    OnGameOverListener onGameOverListener;
    private int mPanelWidth;
    private float mLineHeight;
    private int MAX_LINE = 10;
    private Paint mPaint = new Paint();
    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;
    private int mWhitePieceResId = R.drawable.e24;
    private int mBlackPieceResId = R.drawable.e31;
    private float radioPieceOfLineHeight = 3 * 1.0f / 4;
    private boolean mIsWhite = true;//先走白棋，或者当前轮到白棋先走
    private ArrayList<Point> mWhiteArray = new ArrayList<>();
    private ArrayList<Point> mBlackArray = new ArrayList<>();
    private boolean mIsGameOver = false;
    private boolean mIsWhiteWinner = false;


    public ContentView(Context context) {
        super(context);
    }

    public ContentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public ContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ContentView,
                defStyleAttr, R.style.AppTheme);
        MAX_LINE = ta.getInteger(R.styleable.ContentView_max_line, MAX_LINE);
        radioPieceOfLineHeight = ta.getFloat(R.styleable.ContentView_factor,
                radioPieceOfLineHeight);
        Utils.MAX_IN_LINE = ta.getInteger(R.styleable.ContentView_game_category, Utils.MAX_IN_LINE);

        System.out.println(
                "ContentView Contruct all MAX_LINE= " + MAX_LINE + " radioPieceOfLineHeight = "
                        + radioPieceOfLineHeight);
        ta.recycle();
    }

    private void init() {
        mPaint.setColor(0x88000000);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mWhitePiece = BitmapFactory.decodeResource(getResources(), mWhitePieceResId);
        mBlackPiece = BitmapFactory.decodeResource(getResources(), mBlackPieceResId);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize, heightSize);

        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
        drawPieces(canvas);
        checkGameOver();
    }

    private void checkGameOver() {
        boolean whiteWin = Utils.checkFiveInLine(mWhiteArray);
        boolean blackWin = Utils.checkFiveInLine(mBlackArray);

        if (whiteWin || blackWin) {
            mIsGameOver = true;
            mIsWhiteWinner = whiteWin;

            //Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
            onGameOverListener.showGameOverDialog(mIsWhiteWinner);
        }

    }


    private void drawPieces(Canvas canvas) {
        for (int i = 0, n = mWhiteArray.size(); i < n; i++) {
            Point whitePoint = mWhiteArray.get(i);
            canvas.drawBitmap(mWhitePiece,
                    (whitePoint.x + (1 - radioPieceOfLineHeight) / 2) * mLineHeight,
                    (whitePoint.y + (1 - radioPieceOfLineHeight) / 2) * mLineHeight, null);
        }

        for (int i = 0, n = mBlackArray.size(); i < n; i++) {
            Point blackPoint = mBlackArray.get(i);
            canvas.drawBitmap(mBlackPiece,
                    (blackPoint.x + (1 - radioPieceOfLineHeight) / 2) * mLineHeight,
                    (blackPoint.y + (1 - radioPieceOfLineHeight) / 2) * mLineHeight, null);
        }
    }

    private void drawBoard(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHeight;
        for (int i = 0; i < MAX_LINE; i++) {
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);

            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, endX, y, mPaint);
            canvas.drawLine(y, startX, y, endX, mPaint);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPanelWidth = w;
        mLineHeight = mPanelWidth * 1.0f / MAX_LINE;

        int piecewidth = (int) (mLineHeight * radioPieceOfLineHeight);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, piecewidth, piecewidth, false);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece, piecewidth, piecewidth, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mIsGameOver) return false;
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            Point p = getValidPoint(x, y);

            if (mWhiteArray.contains(p) || mBlackArray.contains(p)) {
                return false;
            }
            if (mIsWhite) {
                mWhiteArray.add(p);
            } else {
                mBlackArray.add(p);
            }

            invalidate();
            mIsWhite = !mIsWhite;
            return true;
        }
        return true;
    }

    private Point getValidPoint(int x, int y) {
        return new Point((int) (x / mLineHeight), (int) (y / mLineHeight));

    }

    public void restart() {
        mIsGameOver = false;
        mIsWhite = true;
        mWhiteArray.clear();
        mBlackArray.clear();
        invalidate();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SUPER, super.onSaveInstanceState());
        bundle.putBoolean(ISGAMEOVER, mIsGameOver);
        bundle.putBoolean(ISWHITE, mIsWhite);
        bundle.putParcelableArrayList(WHITEARRAY, mWhiteArray);
        bundle.putParcelableArrayList(BLACKARRAY, mBlackArray);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mIsWhite = bundle.getBoolean(ISWHITE);
            mIsGameOver = bundle.getBoolean(ISGAMEOVER);
            mBlackArray = bundle.getParcelableArrayList(BLACKARRAY);
            mWhiteArray = bundle.getParcelableArrayList(WHITEARRAY);
            Parcelable parcelable = bundle.getParcelable(SUPER);
            super.onRestoreInstanceState(parcelable);
        } else {
            super.onRestoreInstanceState(state);
        }

    }

    public void setOnGameOverListener(OnGameOverListener onGameOverListener) {
        this.onGameOverListener = onGameOverListener;
    }

    public void setWhiteAndBlackResId(int white, int black) {
        mWhitePieceResId = white;
        mBlackPieceResId = black;

        mWhitePiece = BitmapFactory.decodeResource(getResources(), mWhitePieceResId);
        mBlackPiece = BitmapFactory.decodeResource(getResources(), mBlackPieceResId);
    }
}
