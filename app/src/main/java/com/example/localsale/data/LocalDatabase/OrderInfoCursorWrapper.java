package com.example.localsale.data.LocalDatabase;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.example.localsale.ui.addressPlesk.AddressList;
import com.example.localsale.ui.orderPlesk.ItemInOrderList;

public class OrderInfoCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public OrderInfoCursorWrapper(Cursor cursor) {
        super(cursor);
    }


    public ItemInOrderList getLocalOrderList(){
        ItemInOrderList item = new ItemInOrderList();
        item.setOrderID(getString(1));
        item.setDate(getString(getColumnIndex(DbSchema.OlderInfoTable.Cols.OrderTime)));
        item.setDate(getString(getColumnIndex(DbSchema.OlderInfoTable.Cols.DeliverTime)));
        AddressList.AddressInfo addressInfo = new AddressList.AddressInfo();
        addressInfo.setDormitory(getString(getColumnIndex(DbSchema.OlderInfoTable.Cols.Dormitory)));
        addressInfo.setRoomNumber(getString(getColumnIndex(DbSchema.OlderInfoTable.Cols.RoomNumber)));
        addressInfo.setName(getString(getColumnIndex(DbSchema.OlderInfoTable.Cols.Name)));
        addressInfo.setPhoneNumber(getString(getColumnIndex(DbSchema.OlderInfoTable.Cols.PhoneNumber)));
        item.setAddressInfo(addressInfo);
        for(int i=8;i<getColumnCount();i++){
            item.DBToItemInOrderList(Integer.parseInt(getColumnName(i).replace("Item","")),getInt(i));
        }

        return item;
    }
    public static OrderInfoCursorWrapper queryOrders(SQLiteDatabase database, String whereClause, String[] whereArgs){
        Cursor cursor=database.query(
                DbSchema.OlderInfoTable.NAME,
                null,//null代表选择所有行
                whereClause,
                whereArgs,
                null,
                null,
                null,
                null
        );
        return new OrderInfoCursorWrapper(cursor);
    }
}
