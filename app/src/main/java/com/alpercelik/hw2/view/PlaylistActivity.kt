package com.alpercelik.hw2.view

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.alpercelik.hw2.R
import com.alpercelik.hw2.adapter.CustomRecyclerViewAdapter
import com.alpercelik.hw2.adapter.CustomRecyclerViewAdapter.*
import com.alpercelik.hw2.databinding.ActivityPlaylistBinding
import com.alpercelik.hw2.db.Music
import com.alpercelik.hw2.db.MusicRoomDatabase
import com.alpercelik.hw2.utils.Constants
import com.google.android.material.snackbar.Snackbar
import java.util.Collections

class PlaylistActivity : AppCompatActivity(), RecyclerAdapterInterface {

    lateinit var binding: ActivityPlaylistBinding

    var adapter: CustomRecyclerViewAdapter?=null

    // db creation
    private val kanyeplaylistDB: MusicRoomDatabase by lazy {
        Room.databaseBuilder(this, MusicRoomDatabase::class.java, Constants.DATABASENAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    private val musicList = mutableListOf(
        //Music("Bound 2", "Yeezus", "3:49", R.drawable.d),
        Music("Donda (Not Released)", "Donda", "3:31", R.drawable.a.toInt()),
        Music("POWER", "My Beautiful Dark Twisted Fantasy", "5:58", R.drawable.c.toInt()),
        Music("Off The Grid", "Donda", "5:33", R.drawable.a.toInt()),
        Music("Flashing Lights", "Graduation", "3:58", R.drawable.b.toInt()),
        Music("Stronger", "Graduation", "5:12", R.drawable.b.toInt()),
        Music("Homecoming", "Graduation", "3:23", R.drawable.b.toInt()),
        Music("Father Stretch My Hands", "The Life Of Pablo", "2:16", R.drawable.d.toInt()),
        Music("H.A.M.", "Watch The Throne", "4:46", R.drawable.e.toInt()),
        Music("Dark Fantasy", "My Beautiful Dark Twisted Fantasy", "4:41", R.drawable.c.toInt())

    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        supportActionBar?.hide() // Hiding title bar
        setContentView(binding.root)

        @Suppress("DEPRECATION") // for using deprecated codes without any highlights or errors
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) // Hiding the status bar
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) // Locking the orientation to Portrait

        binding.recyclerMusic.setLayoutManager(LinearLayoutManager(this))

        getData()


        binding.apply {

            fabAdd.setOnClickListener {
                if (currentIndex < musicList.size) {
                    val musicToAdd = musicList[currentIndex]
                    kanyeplaylistDB.MusicDAO().insertMusic(musicToAdd)
                    getData()
                    currentIndex++
                    Snackbar.make(it, "${musicToAdd.name} added to playlist!", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(it, "No more songs to add!", Snackbar.LENGTH_LONG).show()
                }
            }

            fabDelete.setOnClickListener{
                val lastAddedMusic = kanyeplaylistDB.MusicDAO().getLastAddedMusic()

                if (lastAddedMusic != null) {
                    Snackbar.make(it, "${lastAddedMusic.name} is deleted", Snackbar.LENGTH_LONG).show()
                    kanyeplaylistDB.MusicDAO().deleteMusic(lastAddedMusic)
                    getData()

                } else {
                    Snackbar.make(it, "No music to delete", Snackbar.LENGTH_LONG).show()
                }
            }
        }


        binding.fabDeleteAll.setOnClickListener{
            kanyeplaylistDB.MusicDAO().deleteAllMusics()
            getData()
            Snackbar.make(it, "All musics are deleted into playlist", Snackbar.LENGTH_LONG).show()
        }
        binding.fabAddAll.setOnClickListener {
            prepareData()
            getData()
            Snackbar.make(it, "Musics are added into playlist", Snackbar.LENGTH_LONG).show()
        }


        binding.fabUpd.setOnClickListener {
            val lastAddedMusic = kanyeplaylistDB.MusicDAO().getLastAddedMusic()

            if (lastAddedMusic != null) {
                val olderName = lastAddedMusic.name
                val updatedName = "Updated version of $olderName"

                // Update the name using the custom update method
                kanyeplaylistDB.MusicDAO().updateMusicName(olderName, updatedName)

                getData()
                Snackbar.make(it, "$olderName has been updated as $updatedName", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(it, "No music to update", Snackbar.LENGTH_LONG).show()
            }
        }



        // turn back to starting
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // finishing the app
        binding.btnExit.setOnClickListener {
            finishAffinity() // Close all activities and exit the app
        }    }



    private fun displayMusics(musics: MutableList<Music>) {
        adapter = CustomRecyclerViewAdapter(this, musics)
        binding.recyclerMusic.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    //getData() sayesinde database'deki datalar alınıp recycleview'e koyulur
    fun getData(){
        if(kanyeplaylistDB.MusicDAO().getAllMusics().isNotEmpty()){
            displayMusics(kanyeplaylistDB.MusicDAO().getAllMusics())
        }
        else{
            binding.recyclerMusic.adapter = null
        }
    }

    fun prepareData(){
        var musics = ArrayList<Music>()
        Collections.addAll(musics,
            Music("Donda (Not Released)", "Donda", "3:31", R.drawable.a.toInt()),
            Music("Saint Pablo", "The Life Of Pablo", "6:12", R.drawable.d.toInt()),
            Music("Famous", "The Life Of Pablo", "3:16", R.drawable.d.toInt()),
            Music("Ni**as In Paris", "Watch The Throne", "3:39", R.drawable.e.toInt()),
            Music("POWER", "My Beautiful Dark Twisted Fantasy", "5:58", R.drawable.c.toInt()),
            Music("Off The Grid", "Donda", "5:33", R.drawable.a.toInt()),
            Music("Flashing Lights", "Graduation", "3:58", R.drawable.b.toInt()),
            Music("Stronger", "Graduation", "5:12", R.drawable.b.toInt()),
            Music("Homecoming", "Graduation", "3:23", R.drawable.b.toInt()),
            Music("Father Stretch My Hands", "The Life Of Pablo", "2:16", R.drawable.d.toInt()),
            Music("H.A.M.", "Watch The Throne", "4:46", R.drawable.e.toInt()),
            Music("Dark Fantasy", "My Beautiful Dark Twisted Fantasy", "4:41", R.drawable.c.toInt()))

        kanyeplaylistDB.MusicDAO().insertAllMusics(musics)

    }

    override fun displayItem(sc: Music) {
        // ilyaaağğğsss
    }

}