package com.example.crud_pedro_arriola_20188

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.crud_pedro_arriola_20188.databinding.ActivityDetailBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DetailActivity : AppCompatActivity() {
    private lateinit var bindingActivityDetail: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivityDetail = ActivityDetailBinding.inflate(layoutInflater)
        val view = bindingActivityDetail.root
        setContentView(view)

        val key = intent.getStringExtra("key")
        val database = Firebase.database
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS") val myRef = database.getReference("game").child(
            key.toString()
        )

        myRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val mVideoGame:VideoGame? = dataSnapshot.getValue(VideoGame::class.java)
                if (mVideoGame != null) {
                    bindingActivityDetail.nameTextView.text = mVideoGame.name.toString()
                    bindingActivityDetail.dateTextView.text = mVideoGame.date.toString()
                    bindingActivityDetail.priceTextView.text = mVideoGame.price.toString() + " $"
                    bindingActivityDetail.descriptionTextView.text = mVideoGame.description.toString()
                    Glide.with(view)
                        .load(mVideoGame.url.toString())
                        .into(bindingActivityDetail.posterImgeView)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }
}