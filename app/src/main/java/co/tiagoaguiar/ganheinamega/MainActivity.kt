package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", "Nenhum registro salvo")

        /*
        if (result != null){
            txtResult.text = "Última aposta: $result" -Trocando o if pelo let para aprimoramento do codigo.-
        }*/

        result?.let{
            txtResult.text = "Última aposta: $it"
        }
        txtResult.text = "Ultima aposta $result"


        btnGenerate.setOnClickListener {
            val text = editText.text.toString()
            numberGenerator(text, txtResult)

        }

    }

    private fun numberGenerator(text: String, txtResult: TextView) {
        if (text.isEmpty()) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_SHORT).show()
            return
        }
        val qtd = text.toInt()

        if (qtd < 6 || qtd > 15) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_SHORT).show()
            return
        }
        val numbers = mutableSetOf<Int>()
        val random = java.util.Random()

        while (true){
            val number = random.nextInt(60) + 1
            numbers.add(number)

            if (numbers.size == qtd){
                break
            }
        }

        txtResult.text = numbers.sorted().joinToString(" - ")

        val editor = prefs.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply()
    }
}


