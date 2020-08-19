package com.iot.timetomeet.components;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.iot.timetomeet.R;
import com.iot.timetomeet.plants.PlantDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlantDetailListView extends ArrayAdapter<String> {

    private List<PlantDetails> plantDetailsList;

    private Activity context;

    public PlantDetailListView(Activity context, List<PlantDetails> plantDetailsList) {
        super(context, R.layout.plant_details_listview_layout, new String[plantDetailsList.size()]);

        this.context = context;
        this.plantDetailsList = plantDetailsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        ViewHolder viewHolder;

        if(view == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.plant_details_listview_layout, null, true);

            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textTitle.setText(plantDetailsList.get(position).getPlantName());
        viewHolder.textAddress.setText(plantDetailsList.get(position).getPlantAddress());
        viewHolder.textDescription.setText(plantDetailsList.get(position).getPlantFacts());
        viewHolder.textMinPrice.setText(plantDetailsList.get(position).getPlantPriceFrom() + " KR");
        viewHolder.textRoomsAvailable.setText(plantDetailsList.get(position).getRoomsAvailable() + " Rooms Available");

        viewHolder.btnView.setTag(plantDetailsList.get(position).getPlantId());

        if(plantDetailsList != null) {
            Picasso.get().load(plantDetailsList.get(position).getPlantImage()).into(viewHolder.imgRoomImage);
        }

        return view;
    }

    class ViewHolder {
        Button btnView;
        TextView textTitle;
        TextView textAddress;
        TextView textDescription;
        TextView textMinPrice;
        TextView textRoomsAvailable;
        ImageView imgRoomImage;

        ViewHolder(View view) {
            btnView = view.findViewById(R.id.btn_view_plant_detail);
            textTitle = view.findViewById(R.id.txt_title);
            textAddress = view.findViewById(R.id.txt_address);
            textDescription = view.findViewById(R.id.txt_description);
            textMinPrice = view.findViewById(R.id.txt_min_price);
            textRoomsAvailable = view.findViewById(R.id.txt_available_rooms);
            imgRoomImage = view.findViewById(R.id.img_room_picture);
        }
    }
}
