package com.app.roomdatabase.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.app.roomdatabase.models.Users;

import java.util.List;

@Dao
public interface UsersDAO {

    @Insert
    long insertUser(Users user);

    @Update
    int updateUser(Users user);

    @Delete
    int deleteUser(Users user);

    @Query("SELECT * FROM users ORDER BY id DESC")
    List<Users> getUsers();

}
