package com.example.localsale.data.LocalDatabase;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.localsale.ui.addressPlesk.AddressList;

public class AddressInfoCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public AddressInfoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public AddressList.AddressInfo getLocalAddressList(){
        Log.i("TAG:Address!!",getString(0));
        String dormitory = getString(1);
        String RoomNumber = getString(2);
        String Name = getString(3);
        String PhoneNumber = getString(4);
        return AddressList.createAddressInfo(dormitory,RoomNumber,Name,PhoneNumber);
    }
    public static AddressInfoCursorWrapper queryOrders(SQLiteDatabase database, String whereClause, String[] whereArgs){
        Cursor cursor=database.query(
                DbSchema.addressInfotable.NAME,
                null,//null代表选择所有行
                whereClause,
                whereArgs,
                null,
                null,
                null,
                null
        );
        return new AddressInfoCursorWrapper(cursor);
    }
}
