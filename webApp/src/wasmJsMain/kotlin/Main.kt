import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.example.App
import com.example.viewmodel.KiosquitoViewModel

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val viewModel = KiosquitoViewModel()
    ComposeViewport(viewportContainerId = "compose-receiver") {
        App(viewModel = viewModel)
    }
}
