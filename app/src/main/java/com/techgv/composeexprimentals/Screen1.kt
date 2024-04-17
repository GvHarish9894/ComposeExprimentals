package com.techgv.composeexprimentals


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlin.random.Random

@Composable
fun Screen1(modifier: Modifier) {

    val itemList = remember {
        mutableStateListOf<String>()
    }

    val items = listOf("Apple","Ball","Cat","Dog","Elephant","Horse","Goat")

    Column(modifier = modifier.fillMaxSize()) {
        Button(onClick = {
            itemList.add(items[Random.nextInt(0,items.size)])
        }) {
            Text("Click Me")
        }

        LazyColumn {
            itemsIndexed(items = itemList, key ={index, item ->  index}){ index, item ->
                Text(text =item,modifier = Modifier.fillMaxWidth())
            }
        }

    }

}