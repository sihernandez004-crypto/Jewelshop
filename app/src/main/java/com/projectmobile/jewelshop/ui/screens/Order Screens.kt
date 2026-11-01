package com.projectmobile.jewelshop.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projectmobile.jewelshop.model.Paso
import com.projectmobile.jewelshop.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.text.font.FontWeight
import com.projectmobile.jewelshop.model.PedidoUiState
import com.projectmobile.jewelshop.model.Relleno
import com.projectmobile.jewelshop.model.Sabor
import com.projectmobile.jewelshop.model.Topping
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
@Preview(showBackground =true)
private fun PantallaPaso(
    paso: Paso = Paso.CANTIDAD,
    ilustracion: Int = R.drawable.muffin_relleno,
    descripcion: String = "Descripción ejemplo",
    instrucciones: String = "Instrucciones de ejemplo",
    contenido: @Composable ColumnScope.() -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 12.dp),

        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        Text(
            text = "Paso ${paso.ordinal + 1} de 6",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge,
        )
        LinearProgressIndicator(
            progress = { (paso.ordinal +1) / 6f},
            modifier = Modifier.fillMaxWidth()
        )
        Image(
            painter = painterResource(id = ilustracion),
            contentDescription = descripcion,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth().height(180.dp)
        )
        Text(
            text = instrucciones,
            style = MaterialTheme.typography.bodyLarge
        )
        contenido()
        Spacer(modifier = Modifier.height(12.dp))

    }
}
@Preview(showBackground = true)
@Composable
private fun OpcionSimple(
    etiqueta: String = "mi opción",
    detalle: String = "mis detalles",
    seleccionada: Boolean = true,
    onClick: () -> Unit = {}
){
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(
            width = if (seleccionada) 2.dp else 1.dp,
            color = if (seleccionada) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.outlineVariant
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (seleccionada) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        )
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = seleccionada, onClick = null)
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(etiqueta, fontWeight = FontWeight.SemiBold)
                Text(detalle, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SaborScreen(
    state: PedidoUiState = PedidoUiState (),
    onSeleccionar: (Sabor) -> Unit = {}
){
    PantallaPaso(
        paso = Paso.SABOR,
        ilustracion = R.drawable.muffin_sabor,
        descripcion = "Tres muffins de sabores diferentes",
        instrucciones = "Selecciona el sabor de tu muffin. Solo puedes elegir uno."
    ) {
        Sabor.entries.forEach { sabor ->
            OpcionSimple(
                etiqueta = sabor.nombre,
                detalle = "$${sabor.precio} MXN por muffin",
                seleccionada = state.sabor == sabor,
                onClick = { onSeleccionar(sabor) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RellenoScreen(
    state: PedidoUiState = PedidoUiState(),
    onSeleccionar: (Relleno) -> Unit = {}
){
    PantallaPaso(
        paso = Paso.RELLENO,
        ilustracion = R.drawable.muffin_relleno,
        descripcion = "Muffin abierto con opciones de relleno",
        instrucciones = "Elige un relleno. También puedes indicar que lo prefieres sin relleno."
    ) {
        Relleno.entries.forEach { relleno ->
            OpcionSimple(
                etiqueta = relleno.nombre,
                detalle = if (relleno.extra == 0) "Sin costo adicional" else "+$${relleno.extra} MXN",
                seleccionada = state.relleno == relleno,
                onClick = { onSeleccionar(relleno) }
            )
        }
    }

}
@Preview(showBackground = true)
@Composable
fun ToppingScreen(
    state: PedidoUiState = PedidoUiState(),
    onAlternar: (Topping) -> Unit = {},
){
    PantallaPaso(
        paso = Paso.TOPPINGS,
        ilustracion = R.drawable.muffin_toppings,
        descripcion = "Muffin rodeado de coberturas",
        instrucciones = "Puedes elegir varios toppings. <<Sin toppings>> es una opción exclusiva."
    ){
        Topping.entries.forEach { topping ->
            val seleccionado = topping in state.toppings
            Card(
                onClick = {onAlternar(topping)},
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(
                    width = if (seleccionado) 2.dp else 1.dp,
                    color = if (seleccionado) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outlineVariant
                ),
                colors = CardDefaults.cardColors(
                    containerColor = if (seleccionado) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surface
                )
            ){
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Checkbox(checked = seleccionado, onCheckedChange = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(topping.nombre, modifier = Modifier.weight (1f))
                    Text(if (topping.extra == 0) "$0" else "+$${topping.extra}")
                }
            }
        }
        if (state.toppings.isEmpty()) {
            Text(
                text = "Selecciona al menos una opción para continuar.",
                color = MaterialTheme.colorScheme.error)
        }
    }
}
@Preview (showBackground = true)
@Composable
fun CantidadScreen(
    state: PedidoUiState = PedidoUiState (),
    onCantidadChange: (Int) -> Unit = {}
) {
    PantallaPaso(
        paso = Paso.CANTIDAD,
        ilustracion = R.drawable.muffin_cantidad,
        descripcion = "Muffins y control para modificar la cantidad",
        instrucciones = "Indica cuántos muffins iguales deseas pedir (entre 1 y 12)."
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { onCantidadChange(state.cantidad -1) },
                enabled = state.cantidad <1
            ) {Text("-", style = MaterialTheme.typography.headlineSmall)}

            Text (
                text = state.cantidad.toString(),
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(96.dp)
            )
            Button(
                onClick = { onCantidadChange(state.cantidad + 1) },
                enabled = state.cantidad < 12
            ) { Text("+", style = MaterialTheme.typography.headlineSmall)}
        }
        Text(
            "Subtotal: $${state.total} MXN",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
@Preview(showBackground = true)
@Composable
fun ClienteScreen(
    state: PedidoUiState = PedidoUiState (),
    onNombreChange: (String) -> Unit = {},
    onTelefonoChange: (String) -> Unit = {},
    onCorreoChange: (String) -> Unit = {}
){
    PantallaPaso(
        paso = Paso.CLIENTE,
        ilustracion = R.drawable.muffin_cliente,
        descripcion = "Formulario de nombre, teléfono y correo para comprar muffins",
        instrucciones = "Escribe tus datos para preparar la orden. El botón Siguiente se activará cuando sean válidos"
    ) {
        OutlinedTextField(
            value = state.nombre,
            onValueChange = onNombreChange,
            label = { Text("Nombre del cliente") },
            singleLine = true,
            isError = state.nombre.isNotBlank() && !state.nombreValido,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Mínimo 2 caracteres") }
        )
        OutlinedTextField(
            value = state.telefono,
            onValueChange = onTelefonoChange,
            label = { Text("Telefono") },
            placeholder = { Text("10 dígitos") },
            singleLine = true,
            isError = state.telefono.isNotBlank() && !state.telefonoValido,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Solo números; 10 dígitos") }
        )
        OutlinedTextField(
            value = state.correo,
            onValueChange = onCorreoChange,
            label = { Text("Correo electrónico") },
            singleLine = true,
            isError = state.correo.isNotBlank() && !state.correoValido,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Ingresa una dirección de correo válida") }
        )
    }
}
@Preview(showBackground = true)
@Composable
private fun LineaResumen (etiqueta:String = "Mi etiqueta", valor: String = "Mi valor") {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(etiqueta, style = MaterialTheme.typography.bodyMedium)
        Text(valor, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.End)
    }
}

@Preview(showBackground = true)
@Composable
fun ResumenScreen(state:PedidoUiState = PedidoUiState()) {
    PantallaPaso(
        paso = Paso.RESUMEN,
        ilustracion = R.drawable.muffin_resumen,
        descripcion = "Tarjeta de resumen de una orden de muffins",
        instrucciones = "Revisa tus elecciones.Usa Atrás para corregir o Enviar orden para final"
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(all =16.dp),
                verticalArrangement = Arrangement.spacedBy (12.dp)
            ) {
                LineaResumen(etiqueta = "Sabor", valor = state.sabor?.nombre ?: "No elegido")
                LineaResumen(etiqueta = "Relleno", valor = state.relleno?.nombre ?: "No elegido")
                LineaResumen(etiqueta = "Toppings", valor = state.toppings.joinToString { it.nombre})
                LineaResumen(etiqueta = "Cantidad", valor = state.cantidad.toString())
                LineaResumen(etiqueta = "Precio Unitario", valor = "$${state.precioUnitario} MXN")
                HorizontalDivider ()
                LineaResumen(etiqueta = "TOTAL",valor = "$${state.total} MXN")
                LineaResumen(etiqueta = "Cliente", valor = state.nombre.trim())
                LineaResumen(etiqueta = "Teléfono", valor = state.telefono)
                LineaResumen(etiqueta = "Correo", valor = state.correo.trim())

            }
        }
        Text(
            "Actividad didáctica: no se hará ningún pago ni se enviarán datos a una tienda real",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
@Preview(showBackground = true)
@Composable
fun ExitoScreen(state: PedidoUiState = PedidoUiState (), onNuevoPedido: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(state = rememberScrollState()).padding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
       /* Image(
            painter = painterResource(id = R.drawable.muffin_exito),
            contentDescription = "Muffin acompañado de un símbolo de confirmación",
            modifier = Modifier.fillMaxWidth().height(245.dp),
            contentScale = ContentScale.Fit
        )*/

        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes (R.raw.check)
        )

        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
        LottieAnimation(
            composition = composition,
            progress = {progress},
            modifier= Modifier.fillMaxWidth().height(250.dp)
        )

        Spacer(Modifier.height(16.dp))
        Text(
            "Orden enviada",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(10.dp))
        Text(
            "¡Gracias, ${state.nombre.trim()}! Tu pedido de ${state.cantidad} muffins fue registrado en esta simulación",
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(10.dp))
        Text("Total simulado: $${state.total} MXN", fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(24.dp))
        Button(onClick = onNuevoPedido, modifier = Modifier.fillMaxWidth()) {
            Text("Crear otro pedido")
        }
    }
}
