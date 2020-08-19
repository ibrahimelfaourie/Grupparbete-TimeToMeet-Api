package com.iot.timetomeet.components;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.iot.timetomeet.R;
import com.iot.timetomeet.conferenceroom.ConferenceRoom;

import java.util.List;

public class RoomDetailListView extends ArrayAdapter<String> {

    private List<ConferenceRoom> conferenceRooms;

    private Activity context;

    public RoomDetailListView(Activity context, List<ConferenceRoom> conferenceRooms) {
        super(context, R.layout.plant_details_listview_layout, new String[conferenceRooms.size()]);

        this.context = context;
        this.conferenceRooms = conferenceRooms;
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        ViewHolder viewHolder;

        if(view == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.conference_room_details_listview_layout, null, true);

            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ConferenceRoom conferenceRoom = conferenceRooms.get(position);

        viewHolder.textTitle.setText(conferenceRoom.getRoomName());
        viewHolder.textPersons.setText(String.valueOf(conferenceRoom.getMaxSeats()));

        if (!conferenceRoom.isHasMorningSlot()) {
            viewHolder.rbtMorningPrice.setEnabled(false);
        }

        viewHolder.rbtMorningPrice.setText(conferenceRoom.getMorningSlotPrice());
        viewHolder.rbtMorningPrice.setTag(conferenceRoom.getMorningSlotId());

        if (!conferenceRoom.isHasAfternoonSlot()) {
            viewHolder.rbtAfternoonPrice.setEnabled(false);
        }
        viewHolder.rbtAfternoonPrice.setText(conferenceRoom.getAfternoonSlotPrice());
        viewHolder.rbtAfternoonPrice.setTag(conferenceRoom.getAfternoonSlotId());

        String composedId = "77777" + (position + 1);
        viewHolder.rbtFulldayPrice.setText(conferenceRoom.getFulldaySlotPrice());

        if (!conferenceRoom.isHasMorningSlot() || !conferenceRoom.isHasAfternoonSlot()) {
            viewHolder.rbtFulldayPrice.setEnabled(false);
        }

        String fulldayId = conferenceRoom.getMorningSlotId() + "_" + conferenceRoom.getAfternoonSlotId();
        viewHolder.rbtFulldayPrice.setTag(fulldayId);
        viewHolder.rbtPriceGroup.setId(Integer.parseInt(composedId));

        viewHolder.btnReserve.setTag(new String[]{composedId, conferenceRoom.getConferenceRoomId()});

        return view;
    }

    class ViewHolder {
        TextView textTitle;
        TextView textPersons;
        RadioButton rbtMorningPrice;
        RadioButton rbtAfternoonPrice;
        RadioButton rbtFulldayPrice;
        RadioGroup rbtPriceGroup;
        Button btnReserve;

        ViewHolder(View view) {
            textTitle = view.findViewById(R.id.txt_room_details_title);
            textPersons = view.findViewById(R.id.txt_room_details_persons);
            rbtMorningPrice = view.findViewById(R.id.rbt_morning_price);
            rbtAfternoonPrice = view.findViewById(R.id.rbt_afternoon_price);
            rbtFulldayPrice = view.findViewById(R.id.rbt_fullday_price);
            rbtPriceGroup = view.findViewById(R.id.rbg_prices);
            btnReserve = view.findViewById(R.id.btn_reserve_room);
        }
    }
}
