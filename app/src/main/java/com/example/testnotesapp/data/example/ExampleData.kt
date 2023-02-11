package com.example.testnotesapp.data.example

import androidx.compose.ui.graphics.Color
import com.example.testnotesapp.data.structures.Note

object ExampleData {
    var notesList:MutableList<Note> = mutableListOf(
        Note("Lorem Ipsum","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent a tempor nisl, eget blandit est. Fusce scelerisque iaculis euismod. Suspendisse elit massa, volutpat ut tincidunt id, fermentum nec orci. Nunc non mauris sed leo luctus hendrerit vel pharetra quam. Pellentesque ut dolor egestas, ultrices dui et, elementum massa. Quisque gravida nunc et sagittis pharetra. Curabitur imperdiet massa neque, sit amet aliquet purus mollis sit amet. Integer dapibus nec ligula id interdum. Donec condimentum nunc sed ex malesuada volutpat.", Color.Cyan),
        Note("2","desc",Color.White),
        Note("3","desc",Color.LightGray),
        Note("4","desc",Color.Magenta),
        Note("5","desc",Color.Yellow),
        Note("6","desc",Color.Cyan),
        Note("7","desc",Color.White),
        Note("8","desc",Color.DarkGray)
    )
}