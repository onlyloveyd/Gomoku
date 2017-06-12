package mraz.com.wuziqi;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Mraz on 2016/7/25.
 */
public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconViewHolder> {

    ArrayList<Integer> mSourceList;
    ImageSetListener imageSetListener;

    @Override
    public IconViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView iv = new ImageView(parent.getContext());
        return new IconViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(IconViewHolder holder, final int position) {
        holder.getImage().setImageResource(mSourceList.get(position));
        holder.getImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSetListener.callback(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSourceList.size();
    }

    public void setSourceList(ArrayList source) {
        mSourceList = source;
    }

    public void setImageSetListener(ImageSetListener imageSetListener) {
        this.imageSetListener = imageSetListener;
    }

    public class IconViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public IconViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }

        public ImageView getImage() {
            return imageView;
        }

    }
}
