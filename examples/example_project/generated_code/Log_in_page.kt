import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlin.Unit

@Composable
public fun Log_in_page(): Unit {
  Column(verticalArrangement=Arrangement.SpaceAround, horizontalAlignment=Alignment.CenterHorizontally) {
    Text(text = "Log In", modifier = Modifier.size(width=60.17.dp, height=20.75.dp),
        fontSize = 20.sp, color = Color(red=0.00f, green=0.00f, blue=0.00f, alpha=1.00f))
    OutlinedTextField(value = "", onValueChange = {}, label = { Text("Label") }, supportingText = {
        Text("Supporting text") }, modifier = Modifier.size(width=294.00.dp, height=76.00.dp))
    OutlinedTextField(value = "", onValueChange = {}, label = { Text("Label") }, supportingText = {
        Text("Supporting text") }, modifier = Modifier.size(width=294.00.dp, height=76.00.dp))
    Row(modifier=Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceAround,verticalAlignment=Alignment.CenterVertically) {
      Text(text = "Remember
          credentials?", modifier = Modifier.size(width=155.10.dp, height=10.64.dp),
          fontSize = 12.sp, color = Color(red=0.00f, green=0.00f, blue=0.00f, alpha=1.00f))
      Checkbox(checked=true,onCheckedChange={})
    }
    FilledTonalButton(onClick = {}, 
    modifier = Modifier.size(width=87.00.dp, height=40.00.dp),
    colors = ButtonDefaults.buttonColors(containerColor=Color(red=0.40f, green=0.31f, blue=0.64f, alpha=1.00f))) {
      Text(text = "Log in", modifier = Modifier.size(width=36.95.dp, height=13.13.dp),
          fontSize = 12.sp, color = Color(red=1.00f, green=1.00f, blue=1.00f, alpha=1.00f))
    }
    Text(text = "Forgot your password?", modifier = Modifier.size(width=143.42.dp, height=13.49.dp),
        fontSize = 12.sp, color = Color(red=0.00f, green=0.00f, blue=0.00f, alpha=1.00f))
  }
}
