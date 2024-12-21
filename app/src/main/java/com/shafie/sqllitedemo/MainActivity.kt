package com.shafie.sqllitedemo

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shafie.sqllitedemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mUserData: EventDataSqlHelper  // Initialize mUserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserData = EventDataSqlHelper(this) // Proper initialization

        binding.saveBtn.setOnClickListener {
            enterData(
                binding.nameEditText.text.toString(),
                binding.emailEditText.text.toString(),
                binding.phoneEditText.text.toString()
            )
            binding.retrieveBtn.isEnabled = true
        }

        binding.retrieveBtn.setOnClickListener {
            val cursor = getEvents()
            cursor?.let {
                showData(it)
                it.close()  // Close the cursor when done
            }
        }
    }

    private fun enterData(name: String, email: String, phone: String) {
        val db = mUserData.writableDatabase // No need for null-check here as mUserData is non-nullable
        val values = ContentValues().apply {
            put(EventDataSqlHelper.COL_NAME, name)
            put(EventDataSqlHelper.COL_EMAIL, email)
            put(EventDataSqlHelper.COL_PHONE, phone)
        }
        db.insert(EventDataSqlHelper.TABLE_NAME, null, values)
    }

    private fun getEvents(): Cursor? {
        val db = mUserData.readableDatabase
        return db.query(
            EventDataSqlHelper.TABLE_NAME,
            null,   // Fetch all columns
            null,   // No WHERE clause
            null,   // No WHERE arguments
            null,   // No GROUP BY
            null,   // No HAVING
            null    // No ORDER BY
        )
    }

    private fun showData(cursor: Cursor) {
        val ret = StringBuilder()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(EventDataSqlHelper.COL_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(EventDataSqlHelper.COL_NAME))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(EventDataSqlHelper.COL_EMAIL))
            val phone = cursor.getString(cursor.getColumnIndexOrThrow(EventDataSqlHelper.COL_PHONE))

            ret.append("Id: $id, Name: $name, Email: $email, Phone: $phone\n")
        }
        // Show the result in the TextView
        binding.textView.text = ret.toString()  // This assumes the TextView exists in your XML
    }
}
