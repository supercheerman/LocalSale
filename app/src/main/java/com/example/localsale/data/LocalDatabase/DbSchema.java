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

}
