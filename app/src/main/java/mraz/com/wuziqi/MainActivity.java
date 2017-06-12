package mraz.com.wuziqi;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnGameOverListener,
        View.OnClickListener {

    ContentView contentView;
    int whitePieceResId, blackPieceResId;
    ImageView WhiteImage, BlackImage;
    ArrayList<Integer> mIconList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentView = (ContentView) findViewById(R.id.contentview);
        contentView.setOnGameOverListener(this);
        whitePieceResId = R.drawable.e24;
        blackPieceResId = R.drawable.e31;


        WhiteImage = (ImageView) findViewById(R.id.iv_white);
        BlackImage = (ImageView) findViewById(R.id.iv_black);

        WhiteImage.setOnClickListener(this);
        BlackImage.setOnClickListener(this);

        mIconList = new ArrayList<>();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.restart:
                restartGame();
                break;
            case R.id.exit:
                showExit();
                break;
        }
        return true;
    }

    private void showExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("退出游戏");
        Drawable drawable = getResources().getDrawable(R.drawable.confirm_exit);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable.setTint(Color.BLUE);
        }
        builder.setIcon(drawable);
        builder
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        builder.show();

    }

    private void restartGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("重新开始");
        Drawable drawable = getResources().getDrawable(R.drawable.confirm_reset);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable.setTint(Color.BLUE);
        }
        builder.setIcon(drawable);
        builder
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        contentView.restart();
                    }
                });
        builder.show();

    }

    @Override
    public void showGameOverDialog(boolean isWhiteWinner) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String result = isWhiteWinner ? "白棋胜利" : "黑棋胜利";
        if (isWhiteWinner) {
            builder.setIcon(whitePieceResId);
        } else {
            builder.setIcon(blackPieceResId);
        }

        builder.setTitle(result);
        builder
                .setPositiveButton("再来一局", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        restartGame();
                    }
                })
                .setNegativeButton("退出游戏", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        builder.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_black:
                showIconPicker(whitePieceResId);
                break;

            case R.id.iv_white:
                showIconPicker(blackPieceResId);
                break;

        }
    }

    private void showIconPicker(final int decludeResId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        View view = getLayoutInflater().inflate(R.layout.icon_picker, null, false);

        RecyclerView recycleView = (RecyclerView) view.findViewById(R.id.rv_icon_picker);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 5);
        IconAdapter iconAdapter = new IconAdapter();

        mIconList.clear();

        for (int i = 0; i < Utils.mIconList.length; i++) {
            System.out.println(
                    "showIconPicker  list[" + i + "]" + Utils.mIconList[i] + " decludeResId = "
                            + decludeResId);
            if (Utils.mIconList[i] == decludeResId) continue;

            mIconList.add(Utils.mIconList[i]);
        }

        iconAdapter.setSourceList(mIconList);

        recycleView.setAdapter(iconAdapter);
        recycleView.setLayoutManager(layoutManager);

        builder.setView(recycleView, 20, 20, 20, 20);

        final AlertDialog alert = builder.create();
        alert.show();

        ImageSetListener imageSetListener = new ImageSetListener() {
            @Override
            public void callback(int position) {
                if (decludeResId == whitePieceResId) {
                    blackPieceResId = mIconList.get(position);
                    BlackImage.setImageResource(blackPieceResId);
                    alert.dismiss();
                } else {
                    whitePieceResId = mIconList.get(position);
                    WhiteImage.setImageResource(whitePieceResId);
                    alert.dismiss();
                }
                contentView.setWhiteAndBlackResId(whitePieceResId, blackPieceResId);
            }
        };
        iconAdapter.setImageSetListener(imageSetListener);

    }

}
