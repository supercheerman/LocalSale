package com.example.localsale.data.LocalDatabase;

import android.content.ClipData;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.example.localsale.data.CloudDatabase.DBCHelper;
import com.example.localsale.data.LocalDatabase.DbSchema.UserInfoTable;
import com.example.localsale.ui.shoppingPlesk.ItemCategories;

public class ItemInfoCursorWrapper extends CursorWrapper {
    public ItemInfoCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public ItemCategories.Item getLocalDBItem(){//getString之类方法应该是其内置从库中获取数据的方法，而且这个方法会记录访问的位置
        //可以遍历以获取所有行。
        String name =getString(getColumnIndex(DbSchema.ItemInfoTable.Cols.NAME));
        String description=getString(getColumnIndex(DbSchema.ItemInfoTable.Cols.DESCRIPTION));
        float price = getFloat(getColumnIndex(DbSchema.ItemInfoTable.Cols.PRICE));
        return DBCHelper.createItem(name,price,description);
    }

    public String getLocalDBCategory(){

        return getString(getColumnIndex(DbSchema.ItemInfoTable.Cols.CATEGORY));

    }
    /*
    * 通过数据库获取查询结果，保存在UserInfoCursorWrapper中
    *
    * */
    public static ItemInfoCursorWrapper queryCrimes(SQLiteDatabase database, String whereClause, String[] whereArgs){
        Cursor cursor=database.query(
                DbSchema.ItemInfoTable.NAME,
                null,//null代表选择所有行
                whereClause,
                whereArgs,
                null,
                null,
                null,
                null
        );
        return new ItemInfoCursorWrapper(cursor);
    }

}
/*Copyright [yyyy] [name of copyright owner]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/