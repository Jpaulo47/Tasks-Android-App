package com.joaorodrigues.tasks.service.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.joaorodrigues.tasks.service.model.PriorityModel

@Dao
interface PriorityDAO {

    @Insert
    fun save(list: List<PriorityModel>)

    @Query("SELECT * FROM priority")
    fun list(): List<PriorityModel>

    @Query("DELETE FROM priority")
    fun clear()

    @Query("SELECT description FROM priority WHERE id = :id")
    fun getDescription(id: Int): String

}