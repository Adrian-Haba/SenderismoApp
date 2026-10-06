package com.example.senderismoapp

sealed class Screen(val route: String) {

    data object Home : Screen("home")

    data object Zone : Screen("zone")

    data object Routes : Screen("routes")

    data object RouteDetail : Screen("route_detail")
}