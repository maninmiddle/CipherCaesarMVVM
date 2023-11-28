package com.example.myapp.presentation.main

import android.content.ClipData
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.ClipboardManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapp.R
import com.example.myapp.data.CipherModel
import com.example.myapp.data.LangEnum
import com.example.myapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.w3c.dom.Text

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    var counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        typeSpinnerSetUp()


        binding.encryptBtn.setOnClickListener {
            validateInputs()
        }

        binding.copyIv.setOnClickListener {
            copyToClipboard(binding.result.text.toString())
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.textStateFlow.collect { result ->
                    binding.result.text = result
                }
            }
        }


        hideErrorOnStartTyping()


    }

    private fun copyToClipboard(text: String) {
        val clipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager

        val clipData = ClipData.newPlainText("Text", text)

        clipboardManager.setPrimaryClip(clipData)

        Toast.makeText(this@MainActivity, "Text copied", Toast.LENGTH_SHORT).show()
    }

    private fun hideErrorOnStartTyping() {
        binding.shiftEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // n0t used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) {
                    binding.tiShiftLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // n0t used
            }

        })

        binding.textEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) {
                    binding.tiTextLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // not used
            }

        })
    }

    private fun validateInputs() {
        if (binding.textEt.text?.isEmpty() == true) {
            binding.tiTextLayout.error = getString(R.string.error_text_or_shift)
        } else if (binding.shiftEt.text?.isEmpty() == true) {
            binding.tiShiftLayout.error = getString(R.string.error_text_or_shift)

        } else if (binding.shiftEt.text.toString().toInt() >= 33) {
            binding.tiShiftLayout.error = getString(R.string.error_shift_exceeded_max_value)
        } else {
            chooseType()

        }
    }

    private fun chooseType() {
        if (binding.encryptBtn.text == getText(R.string.encrypt)) {
            viewModel.encryptText(
                CipherModel(
                    lang = if (binding.langSpinner.selectedItem.toString() == "English")
                        LangEnum.ENGLISH else LangEnum.RUSSIAN,
                    text = binding.textEt.text.toString(),
                    shift = binding.shiftEt.text.toString().toInt()
                )
            )
        } else if (binding.encryptBtn.text == getText(R.string.decrypt)) {
            viewModel.decryptText(
                CipherModel(
                    lang = if (binding.langSpinner.selectedItem.toString() == "English")
                        LangEnum.ENGLISH else LangEnum.RUSSIAN,
                    text = binding.textEt.text.toString(),
                    shift = binding.shiftEt.text.toString().toInt()
                )
            )
        }
    }

    private fun typeSpinnerSetUp() {
        binding.typeSpinner.setSelection(0)

        binding.typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()

                binding.encryptBtn.text = selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Can't be")
            }

        }
    }
}