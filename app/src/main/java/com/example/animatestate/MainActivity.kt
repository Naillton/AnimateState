package com.example.animatestate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.animatestate.ui.theme.AnimateStateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimateStateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // RotationDemo()
                    // ColorChangeDemo()
                    MotionDemo()
                }
            }
        }
    }
}

/**
 * Projeto criado para criar estado de animacao state driven, animate dp as state
 */

@Composable
fun RotationDemo() {
    var rotated by remember { mutableStateOf(false) }

    // criando animate state float para animar o papeller
    val angle by animateFloatAsState(
        targetValue = if (rotated) 360f else 0f,
        animationSpec = tween(2500),
        label = ""
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {

        Image(
            painter = painterResource(id = R.drawable.propeller),
            contentDescription = "fan",
            modifier = Modifier
                .rotate(angle)
                .padding(10.dp)
                .size(300.dp)
        )

        Button(
            onClick = { rotated = !rotated },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Rotate Propeller")
        }

    }
}

@Composable
private fun ColorChangeDemo() {
    var colorState by remember { mutableStateOf(BoxColor.Red) }

    val animatedColor by animateColorAsState(
        targetValue = when (colorState) {
            BoxColor.Red -> Color.Magenta
            BoxColor.Magenta -> Color.Red
        },
        animationSpec = tween(4500),
        label = ""
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(20.dp)
                .size(200.dp)
                .background(animatedColor)
        )
        Button(
            onClick = {
                colorState = when (colorState) {
                    BoxColor.Red -> BoxColor.Magenta
                    BoxColor.Magenta -> BoxColor.Red
                }
            },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Change Color")
        }

    }
}

// Animacao na mudanca de posicao de um elemento
@Composable
private fun MotionDemo(){

    var positionState by remember { mutableStateOf(BoxPosition.Start) }
    val boxSideLenght = 70.dp

    // obtendo tamanho da tela do dispositivo atraves do localConfiguration
    val screenWidth = (LocalConfiguration.current.screenWidthDp.dp)

    val animatedPosition: Dp by animateDpAsState(
        // definindo target value para mudarmos o elemento da posicao inicial x ate a posicao final y
        targetValue = when (positionState) {
            BoxPosition.Start -> 0.dp
            BoxPosition.End -> screenWidth - boxSideLenght
        },
        // animacao com o spring
        // animationSpec = spring(dampingRatio = DampingRatioHighBouncy)
        // animationSpec = tween(4500),
        // animacao com o spring usando stiffness
        // animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessVeryLow),
        // animacao usando keyframes
        animationSpec = keyframes {
            durationMillis = 1000
            100.dp.at(10)
            110.dp.at(500)
            200.dp.at(700)
            },
        label = ""
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .offset(x = animatedPosition)
                .size(boxSideLenght)
                .background(Color.Red)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                positionState = when (positionState) {
                    BoxPosition.Start -> BoxPosition.End
                    BoxPosition.End -> BoxPosition.Start
                }
            },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Change Position")
        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    AnimateStateTheme {
        // RotationDemo()
        // ColorChangeDemo()
        MotionDemo()
    }
}