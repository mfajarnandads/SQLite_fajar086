package com.example.sqlite_fajar086

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class entry_matakuliah : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_matakuliah)
        val modeEdit = intent.hasExtra("kode") && intent.hasExtra("nama") &&
                intent.hasExtra("sks") && intent.hasExtra("sifat")
        title = if(modeEdit) "Edit Data Mata Kuliah" else "Entri Data Mata Kuliah"

        val etKdMatkul = findViewById<EditText>(R.id.etKdMAtkul)
        val etNmMatkul = findViewById<EditText>(R.id.etNmMatkul)
        val spnSks = findViewById<Spinner>(R.id.spnSks)
        val rdWajib = findViewById<RadioButton>(R.id.rdWajib)
        val rdPilihan = findViewById<RadioButton>(R.id.rdPilihan)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val sks = arrayOf(2, 3, 4, 6)
        val adpSks = ArrayAdapter(
            this@entry_matakuliah,
            android.R.layout.simple_spinner_dropdown_item,
            sks
        )
        spnSks.adapter = adpSks

        if(modeEdit) {
            val kode = intent.getStringExtra("kode")
            val nama = intent.getStringExtra("nama")
            val nilaiSks = intent.getIntExtra("sks", 0)
            val sifat = intent.getStringExtra("sifat")

            etKdMatkul.setText(kode)
            etNmMatkul.setText(nama)
            spnSks.setSelection(sks.indexOf(nilaiSks))
            if(sifat == "wajib") rdWajib.isChecked = true else rdPilihan.isChecked = true
        }
        etKdMatkul.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if("${etKdMatkul.text}".isNotEmpty() && "${etNmMatkul.text}".isNotEmpty() &&
                (rdWajib.isChecked || rdPilihan.isChecked)) {
                val db = DbHelper(this@entry_matakuliah)
                db.kdMatkul = "${etKdMatkul.text}"
                db.nmMatkul = "${etNmMatkul.text}"
                db.sks = spnSks.selectedItem as Int
                db.sifat = if(rdWajib.isChecked) "Wajib" else "Pilihan"
                if(if(!modeEdit) db.simpan() else db.ubah("${etKdMatkul.text}")) {
                    Toast.makeText(
                        this@entry_matakuliah,
                        "Data mata kuliah berhasil disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }else
                    Toast.makeText(
                        this@entry_matakuliah,
                        "Data mata kuliah gagal disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
            }else
                Toast.makeText(
                    this@entry_matakuliah,
                    "Data mata kuliah belum lengkap",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}
