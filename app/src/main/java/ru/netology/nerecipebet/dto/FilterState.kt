package ru.netology.nerecipebet.dto

class FilterState(
    var europeanIsActive: Boolean = true,
    var asianIsActive:Boolean = true,
    var panasianIsActive:Boolean = true,
    var easternIsActive:Boolean = true,
    var americanIsActive:Boolean = true,
    var russianIsActive:Boolean = true,
    var mediterraneanIsActive:Boolean = true
) {
}