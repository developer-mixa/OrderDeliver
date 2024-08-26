package com.example.orderdeliver.presentation.mappers.base

interface ToViewItemMapper<Item, ListItem> {

    fun map(item: Item) : ListItem

}