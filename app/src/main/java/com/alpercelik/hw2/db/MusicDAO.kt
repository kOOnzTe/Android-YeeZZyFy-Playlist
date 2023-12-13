package com.alpercelik.hw2.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alpercelik.hw2.utils.Constants

@Dao
interface MusicDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusic(music: Music)

    @Update
    fun updateMusic(music: Music)

    // Custom update method for updating only the name field
    @Query("UPDATE ${Constants.TABLENAME} SET name = :newName WHERE name = :oldName")
    fun updateMusicName(oldName: String, newName: String)

    @Delete
    fun deleteMusic(music: Music)

    // delete all
    @Query("DELETE FROM ${Constants.TABLENAME}")
    fun deleteAllMusics()

    @Query("SELECT * FROM ${Constants.TABLENAME} ORDER BY name DESC")
    fun getAllMusics() : MutableList<Music>

    @Query("SELECT * FROM ${Constants.TABLENAME} WHERE album LIKE :album")
    fun getMusicById(album:String) : Music

    @Query("SELECT * FROM ${Constants.TABLENAME} WHERE name LIKE :name")
    fun getMusicsByName(name:String) : MutableList<Music>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMusics(customers: ArrayList<Music>){
        customers.forEach{
            insertMusic(it)
        }
    }

    // to select last added music
    @Query("SELECT * FROM ${Constants.TABLENAME} ORDER BY name DESC LIMIT 1")
    fun getLastAddedMusic(): Music?
}