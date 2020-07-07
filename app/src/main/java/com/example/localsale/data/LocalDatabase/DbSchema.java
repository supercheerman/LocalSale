package com.example.localsale.data.LocalDatabase;

public class DbSchema{

    public static final class UserInfoTable{
        public static final String NAME ="pictures";
        public static final class Cols{
            public static final String USERNAME = "username";
            public static final String USER_PASSWORD = "userPassword";
        }
    }

    public static final class ItemInfoTable{
        public static final String NAME ="foodtable";
        public static final class Cols{
            public static final String ID ="id";
            public static final String CATEGORY = "category";
            public static final String NAME = "name";
            public static final String PRICE = "price";
            public static final String DESCRIPTION = "description";

        }
    }
    public static final class OlderInfoTable{
        public static final String NAME ="orderlist";
        public static final int NUMBER = 57;
        public static final class Cols{
            public static final String OrderID ="olderId";
            public static final String OrderTime = "orderTime";
            public static final String DeliverTime = "deliverTime";
            public static final String Dormitory ="dormitory";
            public static final String RoomNumber ="roomNumber";
            public static final String Name ="name";
            public static final String PhoneNumber ="phoneNumber";
        }
    }

    public static final class addressInfotable{
        public static final String NAME ="addresslist";
        public static final class Cols{
            public static final String Dormitory ="dormitory";
            public static final String RoomNumber ="roomNumber";
            public static final String Name ="name";
            public static final String PhoneNumber ="phoneNumber";
        }
    }

}
