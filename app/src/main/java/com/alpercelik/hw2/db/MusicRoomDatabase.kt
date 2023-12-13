package com.alpercelik.hw2.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Music::class], version = 4)
abstract class MusicRoomDatabase : RoomDatabase() {
    abstract fun MusicDAO(): MusicDAO
}