package uz.gita.dimanote.presentation.screen.addnote

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.button.MaterialButton
import uz.gita.dimanote.R
import uz.gita.dimanote.data.model.NoteData
import uz.gita.dimanote.data.source.local.*
import uz.gita.dimanote.data.source.local.converter.DataConverter
import uz.gita.dimanote.databinding.FragmentAddBinding
import uz.gita.dimanote.presentation.screen.addnote.viewmodel.AddViewModel
import uz.gita.dimanote.presentation.screen.addnote.viewmodel.impl.AddViewModelImpl
import uz.gita.dimanote.util.myApply
import java.util.*


class AddNoteFragment : Fragment(R.layout.fragment_add) {
    private val viewModel: AddViewModel by viewModels<AddViewModelImpl>()
    private val binding by viewBinding(FragmentAddBinding::bind)
    private lateinit var dialog: Dialog

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.closeAddNoteScreen.observe(this,closeAddNoteObserver)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        createNotificationChannel()
        addNoteBtn.setOnClickListener {
            if (saveNote()) {
                return@setOnClickListener
            }
            viewModel.closeAddNote()
        }

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.more_menu, menu)
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_edit -> {
                        if (binding.etTitle.editText?.text.toString().isEmpty()) {
                            Toast.makeText(
                                requireContext(),
                                "Title must not be empty",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            showReminderDialog()
                        }
                        true
                    }
                    else -> {
                        false
                    }
                }
            }

        }, viewLifecycleOwner)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveNote(): Boolean {
        val title = binding.etTitle.editText?.text.toString().trim()
        val content = binding.etContent.editText?.text.toString().trim()
        val time = DataConverter.getCurrentTime()

        if (title.isEmpty()) {
            binding.etTitle.error = "Title must not be empty"
            binding.etTitle.editText?.setTextColor(Color.RED)
            binding.etTitle.requestFocus()
            return true
        }

        if (content.isEmpty()) {
            binding.etContent.error = "Title must not be empty"
            binding.etContent.editText?.setTextColor(Color.RED)
            binding.etContent.requestFocus()
            return true
        }
        context?.let {

        }
        viewModel.addNote(NoteData(title = title, content = content, createdAt = time))
        return false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showReminderDialog() {
        dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.notify_item)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val yesBtn: MaterialButton = dialog.findViewById(R.id.btnAdd)

        yesBtn.setOnClickListener {
            scheduleNotification()
            dialog.dismiss()
        }

        dialog.create()
        dialog.show()
    }

    private fun scheduleNotification() {
        val intent = Intent(requireContext().applicationContext, Notification1::class.java)
        val title = binding.etTitle.editText?.text.toString()
        val message = binding.etContent.editText?.text.toString()
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext().applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(requireContext().applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(requireContext().applicationContext)

        Log.d("DDD","Date -> $date" +
                " DateFormat -> $dateFormat" +
                " TimeFormat -> $timeFormat")

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time,pendingIntent)
    }

    private fun getTime(): Long {
        val minute = dialog.findViewById<TimePicker>(R.id.timePicker).minute
        val hour = dialog.findViewById<TimePicker>(R.id.timePicker).hour

        val day = dialog.findViewById<DatePicker>(R.id.datePicker).dayOfMonth
        val month = dialog.findViewById<DatePicker>(R.id.datePicker).month
        val year = dialog.findViewById<DatePicker>(R.id.datePicker).year
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    private val closeAddNoteObserver = Observer<Unit> {
        findNavController().navigateUp()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Notification Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = requireContext().getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
