package com.example.jakdangmodok

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.example.jakdangmodok.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            isbn = result.data?.getStringExtra("isbn")
            val title = result.data?.getStringExtra("title")
            val author = result.data?.getStringExtra("author")
            val categoryName = result.data?.getStringExtra("categoryName")
            val cover = result.data?.getStringExtra("cover")
            setBookInfo(title, author, categoryName, cover)
        }
    }
    private var isbn: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initButton()
    }

    private fun initView() {
        // toolbar
        setSupportActionBar(binding.toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // fee
        binding.edittextGroupFee.addTextChangedListener(PriceTextWatcher(binding.edittextGroupFee))

        // genre button
        binding.recyclerviewGenreAdd.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerviewGenreAdd.adapter = GenreAdapter()
    }

    private fun initButton() {
        // 책 검색 액티비티로 이동
        binding.searchBookAdd.setOnClickListener {
            val intent = Intent(this, BookSearchActivity::class.java)
            resultLauncher.launch(intent)
        }

        // 장소 검색 액티비티로 이동
        binding.searchPlaceAdd.setOnClickListener {
            val intent = Intent(this, PlaceSearchActivity::class.java)
            startActivity(intent)
        }

        // 인원 수 조절 버튼
        binding.buttonMinus.setOnClickListener() {
            var num = binding.groupMemberCount.text.toString().toInt()
            if (num > 1) {
                num -= 1
                binding.groupMemberCount.text = num.toString()
            }
        }
        binding.buttonPlus.setOnClickListener() {
            var num = binding.groupMemberCount.text.toString().toInt()
            if (num < 20) {
                num += 1
                binding.groupMemberCount.text = num.toString()
            }
        }

        // 등록 버튼
        binding.buttonAdd.setOnClickListener() {
            val intent = Intent(this, GroupDetailsActivity::class.java)
            intent.putExtra("groupName", binding.edittextGroupName.text.toString())
            intent.putExtra("bookInfo", isbn!!)
            intent.putExtra("date", binding.datepickerAdd.dayOfMonth.toString() + "일")
            //intent.putExtra("place", binding.searchPlaceAdd.query.toString())
            intent.putExtra("memberCount", binding.groupMemberCount.text.toString())
            intent.putExtra("introduction", binding.edittextGroupIntro.text.toString())
            intent.putExtra("fee", binding.edittextGroupFee.text.toString())
            startActivity(intent)
            finish()
        }
    }

    private fun setBookInfo(title: String?, author: String?, categoryName: String?, cover: String?) {
        binding.bookTitleAdd.text = title
        binding.bookAuthorAdd.text = author
        binding.bookGenreAdd.text = categoryName
        Glide.with(binding.root.context)
            .load(cover)
            .into(binding.bookCoverAdd)
        binding.bookContainer.isVisible = true
    }

    // 뒤로가기 버튼
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}