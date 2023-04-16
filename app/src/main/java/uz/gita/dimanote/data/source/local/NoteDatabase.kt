package uz.gita.dimanote.data.source.local

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import uz.gita.dimanote.data.source.local.converter.DataConverter
import uz.gita.dimanote.data.source.local.dao.NoteDao
import uz.gita.dimanote.data.source.local.entity.NoteEntity

@Database(entities = [NoteEntity::class], exportSchema = true, version = 2)
@TypeConverters(DataConverter::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object {
        private lateinit var instance: NoteDatabase

        private const val DATABASE_NAE = "Note.db"

        fun init(context: Context) {
            if (!(::instance.isInitialized)) {
                instance = Room.databaseBuilder(context, NoteDatabase::class.java, DATABASE_NAE)
                    .allowMainThreadQueries().build()
            }
        }
        fun getInstance() = instance

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Your migration strategy here
                database.execSQL("ALTER TABLE Notes ADD COLUMN user_score INTEGER")
            }
        }
    }
}