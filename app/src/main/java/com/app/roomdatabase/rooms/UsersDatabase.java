package com.app.roomdatabase.rooms;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.app.roomdatabase.dao.UsersDAO;
import com.app.roomdatabase.models.Users;

@Database(entities = Users.class, exportSchema = false, version = 1)
public abstract class UsersDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "users.db";
    private static UsersDatabase instance;

    public static synchronized UsersDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), UsersDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract UsersDAO usersDAO();

}
