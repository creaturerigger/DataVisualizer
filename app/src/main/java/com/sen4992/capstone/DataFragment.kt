package com.sen4992.capstone

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.sen4992.capstone.models.AccelerationData
import java.sql.Time
import java.sql.Timestamp
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*


private const val TAG = "DataFragment"

class DataFragment : Fragment() {


    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var btnData: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_data, container, false)
        btnData = view.findViewById(R.id.btn_data)
        btnData.setOnClickListener {
            database = FirebaseDatabase.getInstance().getReference("/sensor_data/acceleration_data")
            val time= System.currentTimeMillis()
            val accData: AccelerationData = AccelerationData(UUID.randomUUID(),12.5421,13.6546, 0.754512, Timestamp(time))
            database.setValue(accData)
                .addOnSuccessListener {
                    Toast.makeText(context, "User has been created successfully.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Log.d(TAG, "Operation failed! -> $it")
                }
            /*

            class AccelerationData (
    val id: UUID,
    val x_acc: Double,
    val y_acc: Double,
    val z_acc: Double,
    val timestamp: Timestamp
)
            *
            * */
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DataFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = DataFragment()
    }
}
