package com.example.examenjetpackcompose

//1º- Creo la data class q voy a usar

data class Ingrediente(
    val nombre : String,
    val precio: Double
)
data class ItemPedido(
    val producto: Producto,
    val cantidad: Int
)



//las otras ya no las uso pero mejor dejarlas como que no las uso a arriesgarme que necesie alg

data class Cliente(
    val nombre: String,
)
data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double
)

