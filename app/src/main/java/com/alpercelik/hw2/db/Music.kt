package com.alpercelik.hw2.db


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alpercelik.hw2.utils.Constants

@Entity(tableName = Constants.TABLENAME)
class Music (
    @PrimaryKey(autoGenerate = false)
    var name : String,
    var album: String,
    var duration: String,
    var imgID: Int = 0)

{
        override fun toString(): String {
            return "Kanye's Song: {" +
                    " name = " + name +
                    ", album = " + album +
                    ", duration = " + duration +
                    '}'
        }
}