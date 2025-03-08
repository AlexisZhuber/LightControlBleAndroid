package com.example.lightcontrol.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lightcontrol.R
import com.example.lightcontrol.model.ItemsLateralMenu
import com.example.lightcontrol.ui.theme.selectedColorDrawer
import kotlinx.coroutines.launch

/**
 * Composable function for the lateral menu (navigation drawer).
 *
 * @param navController The navigation controller.
 * @param drawerState The drawer state.
 * @param content The content to display.
 */
@Composable
fun LateralMenu(
    navController: NavController,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    val menuItems = listOf(
        ItemsLateralMenu.ItemLateralMenu1,
        ItemsLateralMenu.ItemLateralMenu2,
        ItemsLateralMenu.ItemLateralMenu3,
        ItemsLateralMenu.ItemLateralMenu4
    )

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false, // Disable swipe gestures
        drawerContent = {
            ModalDrawerSheet {
                Image(
                    modifier = Modifier.padding(15.dp),
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null
                )
                menuItems.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.title) },
                        selected = currentRoute(navController) == item.ruta,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            navController.navigate(item.ruta)
                        },
                        icon = {
                            Image(
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp),
                                painter = painterResource(id = item.icon),
                                contentDescription = null
                            )
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = selectedColorDrawer
                        )
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "By Academia de Tecnologìa",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp, bottom = 10.dp),
                    textAlign = TextAlign.End,
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                )
            }
        }
    ) {
        content()
    }
}

/**
 * Retrieves the current navigation route.
 *
 * @param navController The navigation controller.
 * @return The current route as a String.
 */
@Composable
fun currentRoute(navController: NavController): String? =
    navController.currentBackStackEntryAsState().value?.destination?.route
