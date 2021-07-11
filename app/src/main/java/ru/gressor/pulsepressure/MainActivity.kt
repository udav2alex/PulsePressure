package ru.gressor.pulsepressure

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import ru.gressor.pulsepressure.databinding.ActivityMainBinding
import ru.gressor.pulsepressure.entities.Record
import ru.gressor.pulsepressure.ui.editor.EditorFragment
import ru.gressor.pulsepressure.ui.editor.SubmitRecordListener
import ru.gressor.pulsepressure.ui.main.FabListener
import ru.gressor.pulsepressure.ui.main.MainFragment


class MainActivity : AppCompatActivity(), FabListener, SubmitRecordListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment(), TAG_MAIN_FRAGMENT)
                .commit()
        }
    }

    override fun fabClicked() {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, EditorFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun submitRecord(record: Record) {
        var fragment = supportFragmentManager.findFragmentById(binding.container.id)
        if (fragment is EditorFragment) {
            supportFragmentManager.popBackStack()
        }
        fragment = supportFragmentManager.findFragmentByTag(TAG_MAIN_FRAGMENT)
        (fragment as? MainFragment)?.saveRecord(record)

        hideKeyboardFrom(this, binding.root)
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        private const val TAG_MAIN_FRAGMENT = "TAG_MAIN_FRAGMENT"
    }
}