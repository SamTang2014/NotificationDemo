package com.example.notificationdemo

import android.app.*
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.example.notificationdemo.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val channel =
            NotificationChannel("Testing_ID", "Channel", NotificationManager.IMPORTANCE_HIGH)
        val notificationManager =
            requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager


        val channelGroup = NotificationChannelGroup("groupd1", "group")
        notificationManager.createNotificationChannelGroup(channelGroup)
        channel.group = "groupd1"
        notificationManager.createNotificationChannel(channel)

        val messageGroup = "SAM"
        binding.buttonFirst.setOnClickListener {
            val pushCount = _binding?.etPushCount?.text.toString().toIntOrNull() ?: 40
            for (i in 0..pushCount) {
                Thread.sleep(1000)
                val notification = NotificationCompat.Builder(requireContext(), "Testing_ID")
                    .setContentTitle("My notification : $i")
                    .setContentText("Text : $i")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setGroup(messageGroup)
                    .build()
                notificationManager.notify(i, notification)
            }

            val user = Person.Builder().setName("Testing").build()
            val person =  androidx.core.app.Person.fromAndroidPerson(user)
            val style = NotificationCompat.MessagingStyle(person)
                .addMessage(
                    "Testing",
                    System.currentTimeMillis(),
                    person
                )
                .addMessage(
                    "Testing2",
                    System.currentTimeMillis(),
                    person
                )
                .addMessage(
                    "Testing3",
                    System.currentTimeMillis(),
                    person
                )
            val summary = NotificationCompat.Builder(requireContext(), "Testing_ID")
                .setContentTitle("Summary")
                .setContentText("Summary Text")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setGroup(messageGroup)
                .setGroupSummary(true)
                .setStyle(
                    style
                )
                .build()

            notificationManager.notify(10000, summary)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}