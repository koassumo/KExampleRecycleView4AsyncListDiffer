package com.example.kexamplerecycleview4asynclistdiffer.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kexamplerecycleview4asynclistdiffer.R
import com.example.kexamplerecycleview4asynclistdiffer.databinding.ActivityMainBinding
import com.example.kexamplerecycleview4asynclistdiffer.model.repo.fakerepo.FakeNotesRepository

class MainActivity : AppCompatActivity() {
    // 1. ADAPTER создаем
    private val mRvAdapter: NotesRvAdapter by lazy { NotesRvAdapter() }

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(getLayoutInflater())
        val view = binding.getRoot()
        setContentView(view)

        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        // 2. На "rv в xml" накладываем layout ( можно Grid или любой)
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        // 3. На "rv в xml" накладываем созданный в п.1 адаптер
        binding.rvNotes.adapter = mRvAdapter


        // ВАРИАНТ 1. передаем данные (которые забрали в моделе) из сохраненных
        mRvAdapter.updateNote(FakeNotesRepository.getNotes())


    }
}