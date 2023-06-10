package com.example.business_card_app.dao

import androidx.room.*
import com.example.business_card_app.models.Note

@Dao
interface NoteDao {

    @Insert
    fun insert( note: Note) : Long

    @Query("select * from note")
    fun getAll() : List<Note>

    @Query("select * from note where nid =:nid")
    fun findById(nid: Int) : Note

    @Delete
    fun delete(note: Note)

    @Update
    fun update( note: Note)

    @Query("DELETE FROM note WHERE isim = :isim")
    fun deleteById(isim: String)


}