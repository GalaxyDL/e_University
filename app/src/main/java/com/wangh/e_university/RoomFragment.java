package com.wangh.e_university;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.qfdqc.views.seattable.SeatTable;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by mxz on 2017/11/6.
 */

public class RoomFragment extends Fragment {
    private SeatTable seatTableView;
    private ArrayAdapter spinnerAdapter;
    private Spinner floorSpinner, roomSpinner;
    private int selectedFloor, selectedRoom;
    private ImageButton search;
    int row = 10;
    int column = 10;
    Room currentRoom = null;
    int[][] arr = new int[10][10];
    String resultID = "Z9bQI77I";
    SeatTable.SeatChecker checker;
    private Button check;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_room, container, false);
        search = (ImageButton) v.findViewById(R.id.search);
        floorSpinner = (Spinner) v.findViewById(R.id.floor);
        roomSpinner = (Spinner) v.findViewById(R.id.room);
        seatTableView = (SeatTable) v.findViewById(R.id.seatView);
        seatTableView.setScreenName("一层一书库");//设置屏幕名称
        seatTableView.setMaxSelected(1);//设置最多选中
        setFloorSpinner();
        setRoomSpinner();
        findRoom();
        check = (Button) v.findViewById(R.id.btn_check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("test", "" + arr[0][5]);

                final String s = currentRoom.getSeatInfo().replace("2", "1");
                Room room = new Room(currentRoom.getFloorNumber(), currentRoom.getRoomNumber(), s);
//                            object.setSeatInfo(setSeatInfo(arr));
                seatTableView.invalidate();

                room.update(resultID, new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(getActivity(), "成功预定座位", Toast.LENGTH_SHORT).show();
//                            seatTableView.setSeatChecker(checker);
                            seatTableView.invalidate();
                        } else {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "预定失败，请重试", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), selectedFloor+"     "+selectedRoom, Toast.LENGTH_SHORT).show();
                String select = selectedFloor + "" + selectedRoom;
                String name = ((selectedFloor + 1) + "层" + (selectedRoom + 1) + "书库").replace("1", "一").replace("2", "二").replace("3", "三").replace("4", "四").replace("5", "五");
                resultID = matchRoomTypeId(select);
                findRoom();
                seatTableView.setScreenName(name);
                seatTableView.invalidate();
//                seatTableView.setSeatChecker(checker);
//                Toast.makeText(getActivity(), resultID, Toast.LENGTH_SHORT).show();
//                Log.d("bmob", resultID);
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        checker = new SeatTable.SeatChecker() {
//                        arr = getSeatInfo(object.getSeatInfo());

            @Override
            public boolean isValidSeat(int row, int column) {
                if (currentRoom != null) {
                    arr = getSeatInfo(currentRoom.getSeatInfo());
                }

                return true;
            }

            @Override
            public boolean isSold(int row, int column) {

                if (arr[row][column] == 1) {
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                arr[row][column] = 2;
                currentRoom.setSeatInfo(setSeatInfo(arr));
                Log.d("test", "checked" + arr[row][column]);
            }

            @Override
            public void unCheck(int row, int column) {
                arr[row][column] = 0;
                currentRoom.setSeatInfo(setSeatInfo(arr));
                Log.d("test", "unCheck" + arr[row][column]);

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }
        };
        seatTableView.setSeatChecker(checker);
        seatTableView.setData(10, 10);
    }

    public void setFloorSpinner() {
        spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.floors, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorSpinner.setAdapter(spinnerAdapter);
        floorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedFloor = i;
                Log.d("select", "onItemSelected: ----------------floor" + i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void setRoomSpinner() {
        spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.rooms, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(spinnerAdapter);
        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRoom = i;
                Log.d("select", "onItemSelected: -------------------------room" + i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private int[][] getSeatInfo(String s) {
        if (s == null) {
            return null;
        } else {
            int[][] arr = new int[row][column];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    arr[i][j] = Integer.parseInt(s.charAt(i * column + j) + "");
                }
            }
            return arr;
        }
    }

    private String setSeatInfo(int[][] arr) {
        String s = "";
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                s += arr[i][j] + "";
            }
        }
        return s;
    }


    public String matchRoomTypeId(String room) {
        switch (room) {
            case "00":
                return Constans.ONE_ONE_ROOM_OBJECTID;
            case "01":
                return Constans.ONE_TWO_ROOM_OBJECTID;
            case "02":
                return Constans.ONE_THREE_ROOM_OBJECTID;
            case "03":
                return Constans.ONE_FOUR_ROOM_OBJECTID;

            case "10":
                return Constans.TWO_ONE_ROOM_OBJECTID;
            case "11":
                return Constans.TWO_TWO_ROOM_OBJECTID;
            case "12":
                return Constans.TWO_THREE_ROOM_OBJECTID;
            case "13":
                return Constans.TWO_FOUR_ROOM_OBJECTID;

            case "20":
                return Constans.THREE_ONE_ROOM_OBJECTID;
            case "21":
                return Constans.THREE_TWO_ROOM_OBJECTID;
            case "22":
                return Constans.THREE_THREE_ROOM_OBJECTID;
            case "23":
                return Constans.THREE_FOUR_ROOM_OBJECTID;

            case "30":
                return Constans.FOUR_ONE_ROOM_OBJECTID;
            case "31":
                return Constans.FOUR_TWO_ROOM_OBJECTID;
            case "32":
                return Constans.FOUR_THREE_ROOM_OBJECTID;
            case "33":
                return Constans.FOUR_FOUR_ROOM_OBJECTID;

            case "40":
                return Constans.FIVE_ONE_ROOM_OBJECTID;
            case "41":
                return Constans.FIVE_TWO_ROOM_OBJECTID;
            case "42":
                return Constans.FIVE_THREE_ROOM_OBJECTID;
            case "43":
                return Constans.FIVE_FOUR_ROOM_OBJECTID;
        }
        return null;
    }

    private void findRoom() {
        BmobQuery<Room> query = new BmobQuery<Room>();
        query.getObject(resultID, new QueryListener<Room>() {

            @Override
            public void done(final Room object, final BmobException e) {
                if (e == null) {
                    currentRoom = object;
                    arr = getSeatInfo(object.getSeatInfo());
//                    seatTableView.setSeatChecker(checker);
//                    seatTableView.invalidate();

                } else {
                    e.printStackTrace();
                }
            }

        });
    }
}
